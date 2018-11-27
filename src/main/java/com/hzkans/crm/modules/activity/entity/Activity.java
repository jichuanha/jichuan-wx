/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.hzkans.crm.modules.activity.entity;

import com.hzkans.crm.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;


/**
 * 活动管理Entity
 * @author wsh
 * @version 2018-11-26
 */
public class Activity extends DataEntity<Activity> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 活动名称
	private Date effectiveStartTime;		// 活动有效开始时间
	private Date effectiveEndTime;		// 活动有效结束时间
	private Date orderAgingStartTime;		// 订单时效开始时间
	private Date orderAgingEndTime;		// 订单时效结束时间
	private String url;		// url
	private Integer attentionType;		// 关注类型  0 -需要 1-不需要
	private Integer rebateType;		// 返利类型 ： 1-固定金额
	private Integer rebateChannel;		// 返利渠道 1-红包领取
	private Long singleAmount;		// 单笔金额 单位：分
	private String singleAmountStr;		// 单笔金额 单位：分
	private Integer totalOrder;		// 订单总数
	private Long totalRebate;		// 返利总额 单位分
	private String totalRebateStr;		// 返利总额 单位分
	private Integer isAudit;		// 是否需要人工审核 ： 0 -不需要 1-需要
	private String shopName;		// 店铺名称
	private String shopNo;		// 店铺编号
	private String templateLink;		// 模板url
	private Integer status;		// 活动状态 ：0- 未开始  1-进行中 2-已结束
	
	public Activity() {
		super();
	}

	public Activity(String id){
		super(id);
	}

	@Length(min=1, max=255, message="活动名称长度必须介于 1 和 255 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="活动有效时间不能为空")
	public Date getEffectiveStartTime() {
		return effectiveStartTime;
	}

	public void setEffectiveStartTime(Date effectiveStartTime) {
		this.effectiveStartTime = effectiveStartTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="订单时效时间不能为空")
	public Date getOrderAgingStartTime() {
		return orderAgingStartTime;
	}

	public void setOrderAgingStartTime(Date orderAgingStartTime) {
		this.orderAgingStartTime = orderAgingStartTime;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="活动有效时间不能为空")
	public Date getEffectiveEndTime() {
		return effectiveEndTime;
	}

	public void setEffectiveEndTime(Date effectiveEndTime) {
		this.effectiveEndTime = effectiveEndTime;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="订单时效时间不能为空")
	public Date getOrderAgingEndTime() {
		return orderAgingEndTime;
	}

	public void setOrderAgingEndTime(Date orderAgingEndTime) {
		this.orderAgingEndTime = orderAgingEndTime;
	}
	
	@Length(min=1, max=255, message="url长度必须介于 1 和 255 之间")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	@Length(min=1, max=1, message="关注类型  0 -需要 1-不需要长度必须介于 1 和 1 之间")
	public Integer getAttentionType() {
		return attentionType;
	}

	public void setAttentionType(Integer attentionType) {
		this.attentionType = attentionType;
	}
	
	@Length(min=1, max=4, message="返利类型 ： 1-固定金额长度必须介于 1 和 4 之间")
	public Integer getRebateType() {
		return rebateType;
	}

	public void setRebateType(Integer rebateType) {
		this.rebateType = rebateType;
	}
	
	@Length(min=1, max=4, message="返利渠道 1-红包领取长度必须介于 1 和 4 之间")
	public Integer getRebateChannel() {
		return rebateChannel;
	}

	public void setRebateChannel(Integer rebateChannel) {
		this.rebateChannel = rebateChannel;
	}
	
	@NotNull(message="单笔金额 单位：分不能为空")
	public Long getSingleAmount() {
		return singleAmount;
	}

	public void setSingleAmount(Long singleAmount) {
		this.singleAmount = singleAmount;
	}
	
	@Length(min=0, max=8, message="订单总数长度必须介于 0 和 8 之间")
	public Integer getTotalOrder() {
		return totalOrder;
	}

	public void setTotalOrder(Integer totalOrder) {
		this.totalOrder = totalOrder;
	}
	
	public Long getTotalRebate() {
		return totalRebate;
	}

	public void setTotalRebate(Long totalRebate) {
		this.totalRebate = totalRebate;
	}
	
	@Length(min=1, max=1, message="是否需要人工审核 ： 0 -不需要 1-需要长度必须介于 1 和 1 之间")
	public Integer getIsAudit() {
		return isAudit;
	}

	public void setIsAudit(Integer isAudit) {
		this.isAudit = isAudit;
	}
	
	@Length(min=1, max=64, message="店铺名称长度必须介于 1 和 64 之间")
	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	
	@Length(min=1, max=64, message="店铺编号长度必须介于 1 和 64 之间")
	public String getShopNo() {
		return shopNo;
	}

	public void setShopNo(String shopNo) {
		this.shopNo = shopNo;
	}
	
	@Length(min=1, max=255, message="模板url长度必须介于 1 和 255 之间")
	public String getTemplateLink() {
		return templateLink;
	}

	public void setTemplateLink(String templateLink) {
		this.templateLink = templateLink;
	}
	
	@Length(min=0, max=4, message="活动状态 ：0- 未开始  1-进行中 2-已结束长度必须介于 0 和 4 之间")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getSingleAmountStr() {
		return singleAmountStr;
	}

	public void setSingleAmountStr(String singleAmountStr) {
		this.singleAmountStr = singleAmountStr;
	}

	public String getTotalRebateStr() {
		return totalRebateStr;
	}

	public void setTotalRebateStr(String totalRebateStr) {
		this.totalRebateStr = totalRebateStr;
	}
}