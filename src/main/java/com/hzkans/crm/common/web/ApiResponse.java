package com.hzkans.crm.common.web;


import com.hzkans.crm.common.constant.ResponseEnum;

/**
 * ajax请求响应类
 * @author cwr
 *
 * @param <T>
 */
public class ApiResponse<T> {
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	
	public ApiResponse(int code, String msg){
		this.code = code;
		this.msg = msg;
	}
	
	public ApiResponse(ResponseEnum responseEnum){
		this.code = responseEnum.getCode();
		this.msg = responseEnum.getMsg();
	}
	
	private int code;
	private String msg;
	private T data;
}

