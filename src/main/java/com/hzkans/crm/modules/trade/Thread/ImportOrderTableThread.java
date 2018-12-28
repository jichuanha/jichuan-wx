package com.hzkans.crm.modules.trade.Thread;

import com.hzkans.crm.common.utils.DateUtil;
import com.hzkans.crm.modules.trade.dao.OrderDao;
import com.hzkans.crm.modules.trade.entity.Order;
import com.hzkans.crm.modules.trade.entity.TableFlow;
import com.hzkans.crm.modules.trade.entity.TableTimeError;
import com.hzkans.crm.modules.trade.service.TableTimeErrorService;
import com.hzkans.crm.modules.trade.service.TimingDateImportService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author jc
 * @description 订单导入数据库
 * @create 2018/12/26
 */
public class ImportOrderTableThread implements Runnable {

    private Logger logger = LoggerFactory.getLogger(TimingDateImportService.class);
    private static final Integer TRANSFORM_AMOUNT = Integer.valueOf(100);

    private Integer rowNum;
    private String id;
    private Sheet sheetAt;
    private OrderDao orderDao;
    private TableFlow table;
    private TableTimeErrorService tableTimeErrorService;

    @Override
    public void run() {
        try {
            Row row = sheetAt.getRow(rowNum);
            String payTime = row.getCell(5).getStringCellValue();
            if(payTime.equals("-")) {
                return ;
            }
            Order order = new Order();
            order.setTableId(Long.parseLong(id));
            order.setOrderSn(row.getCell(0).getStringCellValue());
            //查询该订单是否已经导入
            Order order1 = orderDao.get(order);
            if(order1 != null) {
                return ;
            }
            order.setBuyerName(row.getCell(1).getStringCellValue());
            Cell cell2 = row.getCell(2);
            if(cell2 != null) {
                cell2.setCellType(Cell.CELL_TYPE_STRING);
                order.setMobile(cell2.getStringCellValue());
            }
            order.setOrderTime(DateUtil.parse(row.getCell(4).
                    getStringCellValue(),DateUtil.NORMAL_DATETIME_PATTERN));
            order.setPayTime(DateUtil.parse(row.getCell(5).
                    getStringCellValue(),DateUtil.NORMAL_DATETIME_PATTERN));
            order.setItemName(row.getCell(7).getStringCellValue());
            order.setItemNo(row.getCell(8).getStringCellValue());
            Cell cell9 = row.getCell(9);
            if(cell9 != null) {
                cell9.setCellType(Cell.CELL_TYPE_STRING);
                order.setUnitPrice(priceY2F(cell9.getStringCellValue()));
            }
            Cell cell10 = row.getCell(10);
            if(cell10 != null) {
                cell10.setCellType(Cell.CELL_TYPE_STRING);
                order.setPayableAmmount(priceY2F(cell10.getStringCellValue()));
            }
            Cell cell11 = row.getCell(11);
            if(cell11 != null) {
                cell11.setCellType(Cell.CELL_TYPE_STRING);
                order.setPayAmount(priceY2F(cell11.getStringCellValue()));
            }
            /*String stringCellValue = row.getCell(17).getStringCellValue();
            String province = "";
            String city = "";
            String area = "";
            if(StringUtils.isNotBlank(stringCellValue)) {
                String[] split = stringCellValue.split(" ");
                int length = split.length;
                province = split[0];
                city = split[1];
                if(length == 2) {
                    area = split[1];
                }else {
                    area = split[2];
                }

            }*/
            order.setProvinceName(row.getCell(17).getStringCellValue());
            order.setCityName(row.getCell(18).getStringCellValue());
            order.setAreaName(row.getCell(19).getStringCellValue());
            order.setAddress(row.getCell(20).getStringCellValue());
            order.setConsignee(row.getCell(21).getStringCellValue());
            order.setShopNo(table.getShopNo().toString());
            order.setOwnShop(table.getShopNo().toString());
            order.setPlatformType(table.getPlatformType());
            int insert = orderDao.insert(order);

        } catch (Exception e) {
            logger.error("saveOrderMessage error",e);
            TableTimeError tableTimeError  = new TableTimeError();
            tableTimeError.setErrorMessage(e.getMessage());
            tableTimeError.setErrorNum(rowNum);
            tableTimeError.setTableId(Long.parseLong(id));
            tableTimeError.setCreateDate(new Date());
            tableTimeErrorService.save(tableTimeError);
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

    private Long priceY2F(String price) {
        double v = Double.parseDouble(price);
        return parseYuan2Fen(v);
    }


    public Integer getRowNum() {
        return rowNum;
    }

    public void setRowNum(Integer rowNum) {
        this.rowNum = rowNum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Sheet getSheetAt() {
        return sheetAt;
    }

    public void setSheetAt(Sheet sheetAt) {
        this.sheetAt = sheetAt;
    }

    public OrderDao getOrderDao() {
        return orderDao;
    }

    public void setOrderDao(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    public TableFlow getTable() {
        return table;
    }

    public void setTable(TableFlow table) {
        this.table = table;
    }

    public TableTimeErrorService getTableTimeErrorService() {
        return tableTimeErrorService;
    }

    public void setTableTimeErrorService(TableTimeErrorService tableTimeErrorService) {
        this.tableTimeErrorService = tableTimeErrorService;
    }

}
