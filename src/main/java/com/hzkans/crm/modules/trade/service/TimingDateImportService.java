package com.hzkans.crm.modules.trade.service;

import com.hzkans.crm.common.utils.JedisUtils;
import com.hzkans.crm.common.utils.JsonUtil;
import com.hzkans.crm.common.utils.StringUtils;
import com.hzkans.crm.modules.trade.Thread.ImportOrderTableThread;
import com.hzkans.crm.modules.trade.constants.TableFlowStatusEnum;
import com.hzkans.crm.modules.trade.constants.TableFlowTypeEnum;
import com.hzkans.crm.modules.trade.dao.OrderDao;
import com.hzkans.crm.modules.trade.dao.OrderMemberDao;
import com.hzkans.crm.modules.trade.dao.TableFlowDao;
import com.hzkans.crm.modules.trade.entity.OrderMember;
import com.hzkans.crm.modules.trade.entity.TableFlow;
import com.hzkans.crm.modules.trade.utils.TradeUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author jc
 * @description 定时器
 * @create 2018/11/26
 */
@Service
public class TimingDateImportService {

    private Logger logger = LoggerFactory.getLogger(TimingDateImportService.class);
    private static final Integer TRANSFORM_AMOUNT = Integer.valueOf(100);

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    @Autowired
    private TableFlowService tableFlowService;
    @Autowired
    private TableTimeErrorService tableTimeErrorService;
    @Autowired
    private OrderMemberDao orderMemberDao;
    @Autowired
    private TableFlowDao tableFlowDao;
    @Autowired
    private OrderDao orderDao;

    /**
     * 将保存到系统中表格的数据同步到数据库
     */
    public void import2DataBase() throws Exception{
        long start = System.currentTimeMillis();
        logger.info("定时任务开启");
        //先读取顾客表(先做单线程,后面添加多线程分配)
        try {
            //先获取顾客信息表将数据导入数据库
            TableFlow tableFlow = new TableFlow();
            tableFlow.setType(TableFlowTypeEnum.CUSTOMER.getCode());
            tableFlow.setStatus(TableFlowStatusEnum.IMPORT_SYSTEM_SUCCESS.getCode());
            TableFlow table = tableFlowService.getTable(tableFlow);
            //如果顾客信息表全部导入完成,继续导入订单表
            if(table == null) {
                tableFlow.setType(TableFlowTypeEnum.ORDER_INFO.getCode());
                table = tableFlowService.getTable(tableFlow);
                //如果订单表格全部导入完成,继续导入用户评价表
                if(table == null) {
                    tableFlow.setType(TableFlowTypeEnum.EVALUATE.getCode());
                    table = tableFlowService.getTable(tableFlow);
                }
                //如果订单评价表全部导入完成,就结束此次任务
                if(table == null) {
                    return;
                }
            }

            logger.info("[{}] table:{}", JsonUtil.toJson(table));
            String tableName = table.getTableName();
            Integer type = table.getType();
            String id = table.getId();
            //读取表格
            String name = TradeUtil.UPLOAD_ADDRESS+tableName;
            File file = new File(name);
            if(!file.exists()) {
                logger.info("文件"+ name + "不存在!");
                return;
            }
            FileInputStream fis = new FileInputStream(file);
            Workbook workBook = TradeUtil.getWorkBook(name, fis);
            //保存表格数据到数据库

            Sheet sheetAt = workBook.getSheetAt(0);
            if(type.equals(TableFlowTypeEnum.CUSTOMER.getCode())) {
                //格式校验
                //保存顾客信息表
                saveCustomerMessage(sheetAt);
            }else if(type.equals(TableFlowTypeEnum.ORDER_INFO.getCode())) {
                //格式校验
                //保存订单信息
                saveOrderMessage(sheetAt, id, table);
            }else if(type.equals(TableFlowTypeEnum.EVALUATE.getCode())) {
                //保存评价信息
            }

            tableFlow.setId(id);
            tableFlow.setTimingDate(new Date());
            tableFlow.setSuccessNum((long)sheetAt.getLastRowNum());
            tableFlow.setStatus(TableFlowStatusEnum.TIMING_SUCCESS.getCode());
            tableFlowDao.update(tableFlow);
            long end = System.currentTimeMillis();
            logger.info("定时导入"+tableName+"到数据库,共耗时"+(end - start)+"毫秒");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private int saveCustomerMessage(Sheet sheetAt){
        try {
            //获取总行数
            int lastRowNum = sheetAt.getLastRowNum();
            int result = 1;
            logger.info("[{}]lastRowNum :{}",lastRowNum);
            //遍历数据
            for (int rowNum = 1; rowNum <= lastRowNum; rowNum++) {
                Row row = sheetAt.getRow(rowNum);
                String memberName = row.getCell(1).getStringCellValue();
                //加入缓存
                String bizCode = JedisUtils.get(memberName);
                if(!StringUtils.isEmpty(bizCode)) {
                    continue;
                }
                logger.info("[{}] bizCode :{}", bizCode);
                OrderMember orderMember = new OrderMember();
                orderMember.setNickName(row.getCell(0).getStringCellValue());
                orderMember.setMemberName(row.getCell(1).getStringCellValue());
                orderMember.setMobile(row.getCell(2).getStringCellValue());
                orderMember.setMembershipLevel(row.getCell(4).getStringCellValue());
                //广东省 佛山市 顺德区 格式
                String stringCellValue = row.getCell(5).getStringCellValue();
                String[] split = stringCellValue.split(" ");
                //因为有的地址没有区或者县 所以这个地方要注意
                if(split.length == 3) {
                    orderMember.setProvinceName(split[0]);
                    orderMember.setCityName(split[1]);
                    orderMember.setAreaName(split[2]);
                }else if(split.length ==2) {
                    orderMember.setProvinceName(split[0]);
                    orderMember.setCityName(split[1]);
                    orderMember.setAreaName(split[1]);
                }
                orderMember.setAddress(row.getCell(6).getStringCellValue());
                if(row.getCell(7) != null) {
                    orderMember.setEmail(row.getCell(7).getStringCellValue());
                }
                orderMember.setCreateDate(new Date());
                orderMember.setUpdateDate(new Date());
                int insert = orderMemberDao.insert(orderMember);
                if(insert < 0) {
                    result = -1;
                }else {
                    JedisUtils.set(memberName, memberName, 0);
                }
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private void saveOrderMessage(Sheet sheetAt, String id, TableFlow table) {
        //获取总行数
        try {
            int lastRowNum = sheetAt.getLastRowNum();
            logger.info("[{}]lastRowNum :{}",lastRowNum);
            //遍历数据
            for (int rowNum = 1; rowNum <= lastRowNum; rowNum++) {
                importInfoExecute(sheetAt, id, table, rowNum);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private static Long parseYuan2Fen(Double amount) {
        if (amount == null) {
            return null;
        } else {
            BigDecimal bd = new BigDecimal(amount);
            return  bd.multiply(new BigDecimal(TRANSFORM_AMOUNT)).setScale(2, 4).longValue();
        }
    }

    private void importInfoExecute(Sheet sheetAt, String id, TableFlow table,
                                      Integer rowNum) throws Exception{
        try {
            ImportOrderTableThread importOrderTableThread = new ImportOrderTableThread();
            importOrderTableThread.setId(id);
            importOrderTableThread.setSheetAt(sheetAt);
            importOrderTableThread.setTable(table);
            importOrderTableThread.setRowNum(rowNum);
            importOrderTableThread.setOrderDao(orderDao);
            importOrderTableThread.setTableTimeErrorService(tableTimeErrorService);
            threadPoolTaskExecutor.execute(importOrderTableThread);
        } catch (Exception e) {
            logger.error("importInfoExecute error ",e);
            throw new Exception(e);
        }
    }



}
