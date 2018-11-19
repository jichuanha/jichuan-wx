package com.hzkans.crm.common.web;

/**
 * 针对需要分页时候的对象的封装 总条数  和 数据的列表
 * @author cwr
 *
 * @param <T>
 */
public class PageDTO<T> {
	
	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public Long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}

	private T data;//数据列表
	
	private Long totalCount;//总页数
	
}
