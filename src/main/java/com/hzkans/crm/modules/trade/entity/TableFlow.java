
package com.hzkans.crm.modules.trade.entity;

import com.hzkans.crm.common.persistence.DataEntity;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;



/**
 * 表格流程Entity
 * @author chuan
 * @version 2018-11-26
 */
public class TableFlow extends DataEntity<TableFlow> {
	
	private static final long serialVersionUID = 1L;
	private String tableName;		// 表格名称
	private Integer status;		// 1:导入系统成功. 2:导入系统失败. 3:定时导入数据库成功. 4:定时导入数据库失败
	private Integer type;
	private String errorMessage;		// 失败原因
	private Date importDate;		// 表格导入时间
	private Date timingDate;		// 定时导入数据库时间
	private Integer shopNo;
	private Integer platformType;
	private String statusStr;
	private Date startDate;
	private Date endDate;
	private Long totalNum;
	private Long successNum;


	public Long getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Long totalNum) {
		this.totalNum = totalNum;
	}

	public Long getSuccessNum() {
		return successNum;
	}

	public void setSuccessNum(Long successNum) {
		this.successNum = successNum;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getStatusStr() {
		return statusStr;
	}

	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}

	public Integer getShopNo() {
		return shopNo;
	}

	public void setShopNo(Integer shopNo) {
		this.shopNo = shopNo;
	}

	public Integer getPlatformType() {
		return platformType;
	}

	public void setPlatformType(Integer platformType) {
		this.platformType = platformType;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public TableFlow() {
		super();
	}

	public TableFlow(String id){
		super(id);
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getImportDate() {
		return importDate;
	}

	public void setImportDate(Date importDate) {
		this.importDate = importDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getTimingDate() {
		return timingDate;
	}

	public void setTimingDate(Date timingDate) {
		this.timingDate = timingDate;
	}
	
}