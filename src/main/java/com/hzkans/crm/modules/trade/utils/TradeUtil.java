package com.hzkans.crm.modules.trade.utils;

import com.hzkans.crm.common.constant.ResponseEnum;
import com.hzkans.crm.common.service.ServiceException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @author jc
 * @description
 * @create 2018/11/26
 */
public class TradeUtil {

    /*private static final String UPLOAD_ADDRESS = "/deploy/data/www/static/upload/";*/
    public static final String UPLOAD_ADDRESS = "E:/youxi/";

    public static final String XLS = "xls";
    public static final String XLSX = "xlsx";


    /**
     * 非空判断
     * @param objects
     * @throws ServiceException
     */
    public static void isAllNull(Object... objects) throws ServiceException {
        for (Object obj : objects) {
            isNull(obj);
        }
    }

    private static void isNull(Object obj) throws ServiceException{
        if(null == obj) {
            throw new ServiceException(ResponseEnum.P_E_PARAM_INVALID);
        }
    }

    /**
     * 获取poi文件对象
     * @param name
     * @param fis
     * @return
     * @throws Exception
     */
    public static Workbook getWorkBook(String name, InputStream fis) throws Exception{
        Workbook workbook = null;
        if(name.endsWith(TradeUtil.XLS)) {
            workbook = new HSSFWorkbook(fis);
        }else if(name.endsWith(TradeUtil.XLSX)) {
            workbook = new XSSFWorkbook(fis);
        }
        return workbook;
    }

}
