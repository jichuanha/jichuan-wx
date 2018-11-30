package com.hzkans.crm.modules.trade.service;

import com.hzkans.crm.common.utils.DateUtil;
import com.hzkans.crm.common.utils.JedisUtils;
import com.hzkans.crm.common.utils.JsonUtil;
import com.hzkans.crm.common.utils.StringUtils;
import com.hzkans.crm.modules.trade.constants.TableFlowStatusEnum;
import com.hzkans.crm.modules.trade.constants.TableFlowTypeEnum;
import com.hzkans.crm.modules.trade.dao.OrderDao;
import com.hzkans.crm.modules.trade.dao.OrderMemberDao;
import com.hzkans.crm.modules.trade.dao.TableFlowDao;
import com.hzkans.crm.modules.trade.entity.Order;
import com.hzkans.crm.modules.trade.entity.OrderMember;
import com.hzkans.crm.modules.trade.entity.TableFlow;
import com.hzkans.crm.modules.trade.utils.TradeUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author jc
 * @description 定时器
 * @create 2018/11/26
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TimingDateImportService {

    private Logger logger = LoggerFactory.getLogger(TimingDateImportService.class);
    private static final Integer TRANSFORM_AMOUNT = Integer.valueOf(100);

    @Autowired
    private TableFlowService tableFlowService;
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
            //读取表格
            String name = TradeUtil.UPLOAD_ADDRESS+tableName;
            File file = new File(name);
            if(!file.exists()) {
                logger.info("文件"+ name + "不存在!");
                return;
            }
            FileInputStream fis = new FileInputStream(file);
            Workbook workBook = getWorkBook(name, fis);
            //保存表格数据到数据库
            int result = 0;
            Sheet sheetAt = workBook.getSheetAt(0);
            if(type.equals(TableFlowTypeEnum.CUSTOMER.getCode())) {
                //保存顾客信息表
                result  = saveCustomerMessage(sheetAt);
            }else if(type.equals(TableFlowTypeEnum.ORDER_INFO.getCode())) {
                //保存订单信息
                result = saveOrderMessage(sheetAt);
            }else if(type.equals(TableFlowTypeEnum.EVALUATE.getCode())) {
                //保存评价信息
            }

            tableFlow.setTableName(tableName);
            tableFlow.setTimingDate(new Date());
            if(result > 0) {
                tableFlow.setStatus(TableFlowStatusEnum.TIMING_SUCCESS.getCode());
                tableFlow.setErrorMessage("");
                tableFlowDao.update(tableFlow);
            }else {
                tableFlow.setStatus(TableFlowStatusEnum.TIMING_FAIL.getCode());
                tableFlow.setErrorMessage("定时任务失败");
                tableFlowDao.update(tableFlow);
            }
            long end = System.currentTimeMillis();
            logger.info("定时导入"+tableName+"到数据库,共耗时"+(end - start)+"毫秒");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除已经成功导入数据库的表
     */
    public void deleteHasImportTable() throws Exception{
        try {
            logger.info("删除任务启动====");
            TableFlow tableFlow = new TableFlow();
            tableFlow.setStatus(TableFlowStatusEnum.TIMING_SUCCESS.getCode());
            List<TableFlow> tables = tableFlowService.getTables(tableFlow);
            if(!CollectionUtils.isEmpty(tables)) {
                for (TableFlow table : tables) {
                    File file = new File(TradeUtil.UPLOAD_ADDRESS+table.getTableName());
                    if(file.exists()) {
                        file.delete();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private Workbook getWorkBook(String name, FileInputStream fis) throws Exception{
        Workbook workbook = null;
        if(name.endsWith(TradeUtil.XLS)) {
            workbook = new HSSFWorkbook(fis);
        }else if(name.endsWith(TradeUtil.XLSX)) {
            workbook = new XSSFWorkbook(fis);
        }
        return workbook;
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

    private int saveOrderMessage(Sheet sheetAt) {
        try {
            //获取总行数
            int lastRowNum = sheetAt.getLastRowNum();
            int result = 1;
            logger.info("[{}]lastRowNum :{}",lastRowNum);
            //遍历数据
            for (int rowNum = 1; rowNum <= lastRowNum; rowNum++) {
                Row row = sheetAt.getRow(rowNum);
                String payTime = row.getCell(5).getStringCellValue();
                if(payTime.equals("-")) {
                    continue;
                }
                Order order = new Order();
                order.setOrderSn(row.getCell(0).getStringCellValue());
                //查询该订单是否已经导入
                Order order1 = orderDao.get(order);
                if(order1 != null) {
                    continue;
                }
                order.setBuyerName(row.getCell(1).getStringCellValue());
                order.setMobile(row.getCell(2).getStringCellValue());
                order.setOrderTime(DateUtil.parse(row.getCell(4).
                        getStringCellValue(),DateUtil.NORMAL_DATETIME_PATTERN));
                order.setPayTime(DateUtil.parse(row.getCell(5).
                        getStringCellValue(),DateUtil.NORMAL_DATETIME_PATTERN));
                order.setItemName(row.getCell(7).getStringCellValue());
                order.setItemNo(row.getCell(8).getStringCellValue());
                order.setUnitPrice(priceY2F(row.getCell(9).getStringCellValue()));
                order.setPayableAmmount(priceY2F(row.getCell(10).getStringCellValue()));
                order.setPayAmount(priceY2F(row.getCell(11).getStringCellValue()));
                order.setProvinceName(row.getCell(17).getStringCellValue());
                order.setCityName(row.getCell(18).getStringCellValue());
                order.setAreaName(row.getCell(19).getStringCellValue());
                order.setAddress(row.getCell(20).getStringCellValue());
                order.setConsignee(row.getCell(21).getStringCellValue());
                //TODO  一下三个数据没有给出,现在先写死
                order.setShopNo("1");
                order.setOwnShop("1");
                order.setPlatformType(1);
                int insert = orderDao.insert(order);
                if(insert < 0) {
                    result = -1;
                }
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    private static Long parseYuan2Fen(Double amount) {
        if (amount == null) {
            return null;
        } else {
            BigDecimal bd = new BigDecimal(amount);
            return  bd.multiply(new BigDecimal(TRANSFORM_AMOUNT)).setScale(2, 4).longValue();
        }
    }

    private Long priceY2F(String price) {
        double v = Double.parseDouble(price);
        return parseYuan2Fen(v);
    }

}
