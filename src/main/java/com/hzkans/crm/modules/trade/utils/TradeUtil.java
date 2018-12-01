package com.hzkans.crm.modules.trade.utils;

import com.hzkans.crm.common.constant.ResponseEnum;
import com.hzkans.crm.common.service.ServiceException;

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

}
