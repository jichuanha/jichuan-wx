/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.hzkans.crm.common.service;

import com.hzkans.crm.common.constant.ResponseEnum;

/**
 * Service层公用的Exception, 从由Spring管理事务的函数中抛出时会触发事务回滚.
 * @author ThinkGem
 */
public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private int code;
    private String serviceMessage;

	public ServiceException() {
		super();
	}
    public ServiceException(ResponseEnum responseEnum) {
        this.code = responseEnum.getCode();
        this.serviceMessage = responseEnum.getMsg();
    }

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

    public String getServiceMessage() {
        return serviceMessage;
    }

    public void setServiceMessage(String serviceMessage) {
        this.serviceMessage = serviceMessage;
    }

	public ServiceException(int code,String message){
		super();
		this.code =code;
		this.serviceMessage =message;
	}


}
