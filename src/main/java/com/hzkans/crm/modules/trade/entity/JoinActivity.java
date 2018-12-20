package com.hzkans.crm.modules.trade.entity;

import javax.validation.constraints.NotNull;

import com.hzkans.crm.common.persistence.DataEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * 活动订单管理Entity
 * @author jc
 * @version 2018-12-01
 */
public class JoinActivity extends DataEntity<JoinActivity> {
	
	private static final long serialVersionUID = 1L;
	private Long orderId;		// 订单id
	private String orderSn;		// 订单编号
	private Integer actId;		// 活动id
	private Integer actType;		// 活动类型
	private String actName;		// 活动名称
	private Integer status;		// status
	private String statusStr;
	private String message;		// 原因
	private String pictureUrl;		// 图片地址
	private Long auditor;		// auditor
	private Date payData;		// 下单时间
	private Integer platformType;
	private Integer shopNo;
	private Integer actStatus;
	private Date startDate;
	private Date endDate;
	private Integer attentionType; //绑定状态
	private String attentionStr;
	private String platformName;
	private String shopName;
	private String memberName;
	private String actMoneyStr;
	private Long actMoney;
	private String mobile;
	private List<String> ids = new ArrayList<>();
	private String award;
	private Integer awardGrantStatus;
	private String wechatNo;
	private Integer rebateType; //返利类型
	private Long wechatId;
	private String openId;


	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public Long getWechatId() {
		return wechatId;
	}

	public void setWechatId(Long wechatId) {
		this.wechatId = wechatId;
	}

	public Integer getRebateType() {
		return rebateType;
	}

	public void setRebateType(Integer rebateType) {
		this.rebateType = rebateType;
	}

	public String getWechatNo() {
		return wechatNo;
	}

	public void setWechatNo(String wechatNo) {
		this.wechatNo = wechatNo;
	}

	public String getAward() {
		return award;
	}

	public void setAward(String award) {
		this.award = award;
	}

	public Integer getAwardGrantStatus() {
		return awardGrantStatus;
	}

	public void setAwardGrantStatus(Integer awardGrantStatus) {
		this.awardGrantStatus = awardGrantStatus;
	}

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	public String getAttentionStr() {
		return attentionStr;
	}

	public void setAttentionStr(String attentionStr) {
		this.attentionStr = attentionStr;
	}

	public String getStatusStr() {
		return statusStr;
	}

	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}

	public String getActMoneyStr() {
		return actMoneyStr;
	}

	public void setActMoneyStr(String actMoneyStr) {
		this.actMoneyStr = actMoneyStr;
	}

	public void setActMoney(Long actMoney) {
		this.actMoney = actMoney;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getAttentionType() {
		return attentionType;
	}

	public void setAttentionType(Integer attentionType) {
		this.attentionType = attentionType;
	}

	public String getPlatformName() {
		return platformName;
	}

	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public Long getActMoney() {
		return actMoney;
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

	public Integer getActStatus() {
		return actStatus;
	}

	public void setActStatus(Integer actStatus) {
		this.actStatus = actStatus;
	}

	public Integer getPlatformType() {
		return platformType;
	}

	public void setPlatformType(Integer platformType) {
		this.platformType = platformType;
	}

	public Integer getShopNo() {
		return shopNo;
	}

	public void setShopNo(Integer shopNo) {
		this.shopNo = shopNo;
	}

	public JoinActivity() {
		super();
	}

	public JoinActivity(String id){
		super(id);
	}

	@NotNull(message="订单id不能为空")
	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

	public Integer getActId() {
		return actId;
	}

	public void setActId(Integer actId) {
		this.actId = actId;
	}

	public Integer getActType() {
		return actType;
	}

	public void setActType(Integer actType) {
		this.actType = actType;
	}

	public String getActName() {
		return actName;
	}

	public void setActName(String actName) {
		this.actName = actName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}
	
	public Long getAuditor() {
		return auditor;
	}

	public void setAuditor(Long auditor) {
		this.auditor = auditor;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="下单时间不能为空")
	public Date getPayData() {
		return payData;
	}

	public void setPayData(Date payData) {
		this.payData = payData;
	}
	
}