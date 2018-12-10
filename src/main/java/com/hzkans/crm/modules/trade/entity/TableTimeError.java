package com.hzkans.crm.modules.trade.entity;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import com.hzkans.crm.common.persistence.DataEntity;

/**
 * 错误信息展示Entity
 * @author jc
 * @version 2018-12-07
 */
public class TableTimeError extends DataEntity<TableTimeError> {
	
	private static final long serialVersionUID = 1L;
	private Long tableId;		// 表格id
	private Integer errorNum;		// error_num
	private String errorMessage;		// 失败原因
	
	public TableTimeError() {
		super();
	}

	public TableTimeError(String id){
		super(id);
	}

	@NotNull(message="表格id不能为空")
	public Long getTableId() {
		return tableId;
	}

	public void setTableId(Long tableId) {
		this.tableId = tableId;
	}

	public Integer getErrorNum() {
		return errorNum;
	}

	public void setErrorNum(Integer errorNum) {
		this.errorNum = errorNum;
	}

	@Length(min=1, max=256, message="失败原因长度必须介于 1 和 256 之间")
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
}