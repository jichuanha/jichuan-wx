package com.hzkans.crm.common.utils;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hzkans.crm.common.constant.ResponseEnum;
import com.hzkans.crm.common.service.ServiceException;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

/**
 * 请求参数工具类
 */
public class RequestUtils {

    private final static Gson gson = new GsonBuilder().setFieldNamingPolicy(
            FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

    public static List<?> json2List(String src, Type type) {
        List<?> objList = null;
        objList = gson.fromJson(src, type);
        return objList;
    }

    public static <T> T parse(String jsonStr, Class<T> classOfT) {
        return gson.fromJson(jsonStr, classOfT);
    }

    public static <T> T parse(String jsonStr, Type typeOfT) {
        return gson.fromJson(jsonStr, typeOfT);
    }

    public static String getString(HttpServletRequest request, String paramName) {
        String value = request.getParameter(paramName);
        return value;
    }

    /**
     * 如果是为空则抛出异常
     *
     * @param request
     * @param paramName
     * @param message
     * @return
     * @throws ServiceException
     */
    public static String getString(HttpServletRequest request, String paramName, String message)
            throws ServiceException {
//        String v = request.getParameter(paramName);
//        if (StringUtils.isBlank(v)) {
//            throw new ServiceException(ResponseEnum.P_E_PARAM_INVALID.getCode(), message);
//        }
//        return v.trim();
        return getString(request, false, paramName, message);
    }

    /**
     * 允许参数为空的情况,来接收
     *
     * @param request
     * @param allowNull
     * @param paramName
     * @param message
     * @return
     * @throws ServiceException
     */
    public static String getString(HttpServletRequest request, boolean allowNull, String paramName, String message) throws ServiceException {
        String v = request.getParameter(paramName);
        if (allowNull) {
            if (StringUtils.isBlank(v)) {
                return null;
            }
            return v.trim();
        } else {
            if (StringUtils.isBlank(v)) {
                throw new ServiceException(ResponseEnum.P_E_PARAM_INVALID.getCode(), message);
            }
            return v.trim();
        }
    }

    /**
     * @param request
     * @param paramName
     * @param formatMessage
     * @return 从request获取参数对应长整型值
     * @throw ServiceException
     */
    public static Long getLong(HttpServletRequest request, String paramName, String formatMessage)
            throws ServiceException {
        String value = request.getParameter(paramName);
        try {
            if (!StringUtils.isBlank(value)) {
                return Long.parseLong(value.trim());
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new ServiceException(ResponseEnum.P_E_PARAM_INVALID.getCode(), formatMessage);
        }
    }

    /**
     * @param request
     * @param paramName
     * @param message
     * @param formatMessage
     * @return
     * @throws ServiceException
     */
    public static Long getLong(HttpServletRequest request, String paramName, String message, String formatMessage)
            throws ServiceException {
        String v = request.getParameter(paramName);
        if (StringUtils.isBlank(v)) {
            throw new ServiceException(ResponseEnum.P_E_PARAM_INVALID.getCode(), message);
        }
        try {
            return Long.valueOf(v.trim());
        } catch (Exception e) {
            throw new ServiceException(ResponseEnum.P_E_PARAM_INVALID.getCode(), formatMessage);
        }
    }

    /**
     * @param request
     * @param paramName
     * @param formatMessage
     * @return 从request获取参数对应整型值
     * @throws ServiceException
     */
    public static Integer getInt(HttpServletRequest request, String paramName, String formatMessage)
            throws ServiceException {
        String value = request.getParameter(paramName);
        try {
            if (!StringUtils.isBlank(value)) {
                return Integer.parseInt(value);
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new ServiceException(ResponseEnum.P_E_PARAM_INVALID.getCode(), formatMessage);
        }
    }

    /**
     * @param request
     * @param paramName
     * @param message
     * @param formatMessage
     * @return
     * @throws ServiceException
     */
    public static Integer getInt(HttpServletRequest request, String paramName, String message, String formatMessage)
            throws ServiceException {
        String v = request.getParameter(paramName);
        if (StringUtils.isBlank(v)) {
            throw new ServiceException(ResponseEnum.P_E_PARAM_INVALID.getCode(), message);
        }
        try {
            return Integer.valueOf(v.trim());
        } catch (Exception e) {
            throw new ServiceException(ResponseEnum.P_E_PARAM_INVALID.getCode(), formatMessage);
        }
    }

    public static Integer getInt(HttpServletRequest request, String paramName, boolean allowNull, String message, String formatMessage) throws ServiceException {
        String v = request.getParameter(paramName);
        if (allowNull) {
            try {
                if (StringUtils.isBlank(v)) {
                    return null;
                }
                return Integer.valueOf(v.trim());
            } catch (Exception e) {
                throw new ServiceException(ResponseEnum.P_E_PARAM_INVALID.getCode(), formatMessage);
            }
        } else {
            if (StringUtils.isBlank(v)) {
                throw new ServiceException(ResponseEnum.P_E_PARAM_INVALID.getCode(), message);
            } else {
                try {
                    return Integer.valueOf(v.trim());
                } catch (Exception e) {
                    throw new ServiceException(ResponseEnum.P_E_PARAM_INVALID.getCode(), formatMessage);
                }
            }
        }
    }


    /**
     * 解析Double数据格式
     */
    public static Double getDouble(HttpServletRequest request, String paramName, String formatMessage)
            throws ServiceException {
        String v = request.getParameter(paramName);
        try {
            if (!StringUtils.isBlank(v)) {
                return Double.parseDouble(v.trim());
            } else {
                return null;
            }
        } catch (NumberFormatException e) {
            throw new ServiceException(ResponseEnum.P_E_PARAM_INVALID.getCode(), formatMessage);
        }
    }

    /**
     * 解析Object数据格式
     */
    public static <T> T  getObject(HttpServletRequest request,Class<T> tClass,String paramName, String formatMessage)
            throws ServiceException {
        String v = request.getParameter(paramName);
        try {
            if (!StringUtils.isBlank(v)) {
                return gson.fromJson(v, tClass);
            } else {
                return null;
            }
        } catch (NumberFormatException e) {
            throw new ServiceException(ResponseEnum.P_E_PARAM_INVALID.getCode(), formatMessage);
        }
    }

    /**
     * 解析Object数据格式
     */
    public static <T> T  getObject(HttpServletRequest request,TypeToken<T> tClass,String paramName, String formatMessage)
            throws ServiceException {
        String v = request.getParameter(paramName);
        try {
            if (!StringUtils.isBlank(v)) {
                return gson.fromJson(v,tClass.getType());
            } else {
                return null;
            }
        } catch (NumberFormatException e) {
            throw new ServiceException(ResponseEnum.P_E_PARAM_INVALID.getCode(), formatMessage);
        }
    }

    /**
     * 解析Double数据格式
     */
    public static Double getDouble(HttpServletRequest request, String paramName, String message, String formatMessage)
            throws ServiceException {
        String v = request.getParameter(paramName);
        try {
            if (StringUtils.isBlank(v)) {
                throw new ServiceException(ResponseEnum.P_E_PARAM_INVALID.getCode(), message);
            }
            return Double.parseDouble(v.trim());
        } catch (NumberFormatException e) {
            throw new ServiceException(ResponseEnum.P_E_PARAM_INVALID.getCode(), formatMessage);
        }
    }

    public static Float getFloat(HttpServletRequest request, String paramName, String formatMessage)
            throws ServiceException {
        String v = request.getParameter(paramName);
        try {
            if (!StringUtils.isBlank(v)) {
                return Float.parseFloat(v.trim());
            } else {
                return null;
            }
        } catch (NumberFormatException e) {
            throw new ServiceException(ResponseEnum.P_E_PARAM_INVALID.getCode(), formatMessage);
        }
    }

    /**
     * 解析Float数据格式
     */
    public static Float getFloat(HttpServletRequest request, String paramName, String message, String formatMessage)
            throws ServiceException {
        String v = request.getParameter(paramName);
        try {
            if (StringUtils.isBlank(v)) {
                throw new ServiceException(ResponseEnum.P_E_PARAM_INVALID.getCode(), message);
            }
            return Float.parseFloat(v.trim());
        } catch (NumberFormatException e) {
            throw new ServiceException(ResponseEnum.P_E_PARAM_INVALID.getCode(), formatMessage);
        }
    }


    /**
     * 获取特定规格的日期类型
     *
     * @param request
     * @param paramName
     * @param dateFormats
     * @param formatMessage
     * @return
     * @throws ServiceException
     */
    public static Date getFormatDate(HttpServletRequest request, String paramName, String[] dateFormats, String formatMessage)
            throws ServiceException {
        String value = request.getParameter(paramName);
        if (StringUtils.isBlank(value)) {
            return null;
        }
        try {
            return DateUtils.parseDate(request.getParameter(paramName), dateFormats);
        } catch (Exception e) {
            throw new ServiceException(ResponseEnum.P_E_PARAM_INVALID.getCode(), formatMessage);
        }
    }

    /**
     * 解析long型数据格式
     *
     * @param src
     * @param formatMessage
     * @return
     * @throws ServiceException 抛出统一异常 便于统一处理
     */
    public static Long parseLong(String src, String formatMessage) throws ServiceException {
        try {
            return Long.parseLong(src.trim());
        } catch (NumberFormatException e) {
            throw new ServiceException(ResponseEnum.P_E_PARAM_INVALID.getCode(), formatMessage);
        }
    }

    /**
     * 解析long型数据格式
     *
     * @param src
     * @param message
     * @param formatMessage
     * @return
     * @throws ServiceException 抛出统一异常 便于统一处理
     */
    public static Long parseLong(String src, String message, String formatMessage)
            throws ServiceException {
        if (StringUtils.isBlank(src)) {
            throw new ServiceException(ResponseEnum.P_E_PARAM_INVALID.getCode(), message);
        }
        try {
            return Long.parseLong(src.trim());
        } catch (NumberFormatException e) {
            throw new ServiceException(ResponseEnum.P_E_PARAM_INVALID.getCode(), formatMessage);
        }
    }

    /**
     * 解析Int数据格式
     *
     * @param src
     * @param formatMessage
     * @return
     * @throws ServiceException 抛出统一异常 便于统一处理
     */
    public static Integer parseInteger(String src, String formatMessage) throws ServiceException {
        try {
            if (!StringUtils.isBlank(src.trim())) {
                return Integer.parseInt(src.trim());
            } else {
                return null;
            }
        } catch (NumberFormatException e) {
            throw new ServiceException(ResponseEnum.P_E_PARAM_INVALID.getCode(), formatMessage);
        }
    }

    /**
     * 解析Int数据格式
     *
     * @param src
     * @param message
     * @param formatMessage
     * @return
     * @throws ServiceException 抛出统一异常 便于统一处理
     */
    public static Integer parseInteger(String src, String message, String formatMessage)
            throws ServiceException {
        if (StringUtils.isBlank(src)) {
            throw new ServiceException(ResponseEnum.P_E_PARAM_INVALID.getCode(), message);
        }
        try {
            return Integer.parseInt(src.trim());
        } catch (NumberFormatException e) {
            throw new ServiceException(ResponseEnum.P_E_PARAM_INVALID.getCode(), formatMessage);
        }
    }

    public static Long getLong(HttpServletRequest request, String paramName, boolean allowNull, String message, String formatMessage) throws ServiceException {
        String v = request.getParameter(paramName);
        if (allowNull) {
            try {
                if (v == null || v.trim().equals("")) {
                    return null;
                }
                return Long.valueOf(v.trim());
            } catch (Exception e) {
                throw new ServiceException(ResponseEnum.P_E_PARAM_INVALID.getCode(), formatMessage);
            }
        } else {
            if (StringUtils.isBlank(v)) {
                throw new ServiceException(ResponseEnum.P_E_PARAM_INVALID.getCode(), message);
            } else {
                try {
                    return Long.valueOf(v.trim());
                } catch (Exception e) {
                    throw new ServiceException(ResponseEnum.P_E_PARAM_INVALID.getCode(), formatMessage);
                }
            }
        }
    }

}
