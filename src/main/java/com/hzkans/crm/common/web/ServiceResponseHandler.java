package com.hzkans.crm.common.web;

import com.hzkans.crm.common.constant.ResponseEnum;
import com.hzkans.crm.common.service.ServiceException;
import com.hzkans.crm.common.utils.ResponseUtils;
import com.hzkans.crm.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceResponseHandler {
	
	private static Logger logger = LoggerFactory.getLogger(ServiceResponseHandler.class);
	
	/**
	 * 如果是服务端没有返回成功，统一处理异常情况部分信息不显示给用户，可能屏蔽部分信息部显示给用户
	 * @param e
	 * @param callback
	 * @return
	 */
	public static String serviceExceptionHandler(String callback, ServiceException e){
		logger.error("service error code = {}, msg = {}", e.getCode(), e.getServiceMessage());
		String response = ResponseUtils.getFailApiResponseStr(e.getCode(), e.getServiceMessage());
		return StringUtils.isBlank(callback) ? response : callback + "(" + response + ")";

	}

    public static ApiResponse serviceExceptionHandler(ServiceException e){
		logger.error("service error code = {}, msg = {}", e.getCode(), e.getServiceMessage());
        ApiResponse apiResponse = new ApiResponse(e.getCode(), e.getMessage());
		return  apiResponse;

	}

	/**
	 * 如果服务端返回成功，则返回成功数据
	 * @param callback
	 * @param module
	 * */
	public static String serviceSuccessHandler(String callback, Object module) {
		String response = ResponseUtils.getSuccessApiResponseStr(module);
		return StringUtils.isBlank(callback) ? response : callback + "(" + response + ")";
	}
    public static ApiResponse  serviceSuccessHandler( Object module) {
        ApiResponse apiResponse = new ApiResponse(ResponseEnum.REQUEST_SUCESS);
        apiResponse.setData(module);
        return apiResponse;
    }
}
