package com.hzkans.crm.modules.trade.utils;

import com.hzkans.crm.common.constant.ResponseEnum;
import com.hzkans.crm.common.persistence.BaseEntity;
import com.hzkans.crm.common.service.ServiceException;
import com.hzkans.crm.modules.trade.entity.Order;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;

import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author jc
 * @description
 * @create 2018/11/26
 */
public class TradeUtil {

    public static final String REGEX_MOBILE = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
    public static final String UPLOAD_ADDRESS = "/deploy/data/www/static/upload/";
    /*public static final String UPLOAD_ADDRESS = "E:/youxi/";*/

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

    public static String getRealName(String name) {
        String[] split = name.split("\\.");
        String bizCode = split[0];
        return bizCode.substring(0 ,bizCode.length() - 14);
    }


    /**
     * 对象转换
     * @param source
     * @param target
     * @param <T>
     * @return
     */
    public static <T,V> V  twoObjectTransforms(T source,Class<V> target) throws Exception{
        isNull(source);
        V c = target.newInstance();
        BeanUtils.copyProperties(source,c);
        return c;
    }

    /**
     * 转换集合
     * @param t
     * @param v
     * @param <T>
     * @param <V>
     * @return
     */
    public static  <T,V> List<V> coverList(List<T> t, Class<V> v) throws Exception{
        List<V> list = new ArrayList<V>();
        for (T temp : t) {
            V c = v.newInstance();
            BeanUtils.copyProperties(temp,c);
            list.add(c);
        }
        return list;
    }

    /**
     * 校验手机号
     * @param mobile
     * @return
     */
    public static Boolean checkMobile(String mobile) {
        return Pattern.matches(REGEX_MOBILE,mobile);
    }

    public static  List<Long> getIds(List<BaseEntity> list) {
        List<Long> ids = new ArrayList<>();
        try {
            for (BaseEntity t : list) {
                Class<?> aClass = t.getClass();
                Field id = aClass.getDeclaredField("id");
                id.setAccessible(true);
                ids.add((Long) id.get(t));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ids;
    }

}
