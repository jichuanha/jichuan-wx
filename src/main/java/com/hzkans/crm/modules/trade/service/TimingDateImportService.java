package com.hzkans.crm.modules.trade.service;

import com.hzkans.crm.common.utils.JedisUtils;
import com.hzkans.crm.common.utils.JsonUtil;
import com.hzkans.crm.common.utils.StringUtils;
import com.hzkans.crm.modules.trade.constants.TableFlowStatusEnum;
import com.hzkans.crm.modules.trade.constants.TableFlowTypeEnum;
import com.hzkans.crm.modules.trade.dao.OrderMemberDao;
import com.hzkans.crm.modules.trade.dao.TableFlowDao;
import com.hzkans.crm.modules.trade.entity.OrderMember;
import com.hzkans.crm.modules.trade.entity.TableFlow;
import com.hzkans.crm.modules.trade.utils.TradeUtil;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
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
import java.util.Date;
import java.util.List;

/**
 * @author jc
 * @description 定时器
 * @create 2018/11/26
 */
@Service
@Transactional
public class TimingDateImportService {

    Logger logger = LoggerFactory.getLogger(TimingDateImportService.class);

    @Autowired
    private TableFlowService tableFlowService;
    @Autowired
    private OrderMemberDao orderMemberDao;
    @Autowired
    private TableFlowDao tableFlowDao;

    /**
     * 将保存到系统中表格的数据同步到数据库
     */
    public void import2DataBase() throws Exception{
        long start = System.currentTimeMillis();
        logger.info("定时任务开启");
        //先读取顾客表(先做单线程,后面添加多线程分配)
        try {
            TableFlow tableFlow = new TableFlow();
            tableFlow.setType(TableFlowTypeEnum.CUSTOMER.getCode());
            tableFlow.setStatus(TableFlowStatusEnum.IMPORT_SYSTEM_SUCCESS.getCode());
            TableFlow table = tableFlowService.getTable(tableFlow);
            if(table == null) {
                return;
            }
            logger.info("[{}] table:{}", JsonUtil.toJson(table));
            String tableName = table.getTableName();
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
            Sheet sheetAt = workBook.getSheetAt(0);
            int result = saveMessage(sheetAt);
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
            logger.info("定时导入"+tableName+"到数据库,共耗时"+(end - start)/1000);
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

    private int saveMessage(Sheet sheetAt){
        try {
            //获取总行数
            int lastRowNum = sheetAt.getLastRowNum();
            int result = 1;
            logger.info("[{}]lastRowNum :{}",lastRowNum);
            //遍历数据
            for (int rowNum = 1; rowNum <= lastRowNum; rowNum++) {
                Row row = sheetAt.getRow(rowNum);
                //判断该
                String memberName = row.getCell(1).getStringCellValue();
                //加入缓存
                String bizCode = JedisUtils.get(memberName);
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

}
