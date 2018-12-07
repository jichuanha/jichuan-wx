/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.hzkans.crm.modules.activity.entity;

import com.hzkans.crm.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.util.Date;


/**
 * 活动管理Entity
 * @author wsh
 * @version 2018-11-26
 */
public class Activity extends DataEntity<Activity> {
	
	private static final long serialVersionUID = 1L;
	private Integer activityType;
	private String name;		// 活动名称
	private Date activeDate;		// 活动有效开始时间
	private Date inactiveDate;		// 活动有效结束时间
	private Date orderActiveDate;		// 订单时效开始时间
	private Date orderInactiveDate;		// 订单时效结束时间
	private Long wechatPlatformId;
	private String wechatPlatformName;
	private String url;		// url
	private Integer isFollow;		// 关注类型  0 -需要 1-不需要
	private Integer rebateType;		// 返利类型 ： 1-固定金额
	private Integer rebateChannel;		// 返利渠道 1-红包领取
	private Long perAmount;		// 单笔金额 单位：分
	private String perAmountStr;		// 单笔金额 单位：分
	private Integer maxOrderLimit;		// 订单总数
	private Integer orderCount;
	private Long version;
	private Long totalAmount;		// 返利总额 单位分
	private String totalAmountStr;		// 返利总额 单位分
	private Integer isAudit;		// 是否需要人工审核 ： 0 -不需要 1-需要
	private String shopName;		// 店铺名称
	private String shopNo;		// 店铺编号
	private String templateLink;		// 模板url
	private Integer status;		// 活动状态 ：0- 未开始  1-进行中 2-已结束
	private String statusStr;


	public String getStatusStr() {
		return statusStr;
	}

	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}

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

	public Integer getActivityType() {
		return activityType;
	}

	public void setActivityType(Integer activityType) {
		this.activityType = activityType;
	}

	public Date getActiveDate() {
		return activeDate;
	}

	public void setActiveDate(Date activeDate) {
		this.activeDate = activeDate;
	}

	public Date getInactiveDate() {
		return inactiveDate;
	}

	public void setInactiveDate(Date inactiveDate) {
		this.inactiveDate = inactiveDate;
	}

	public Date getOrderActiveDate() {
		return orderActiveDate;
	}

	public void setOrderActiveDate(Date orderActiveDate) {
		this.orderActiveDate = orderActiveDate;
	}

	public Date getOrderInactiveDate() {
		return orderInactiveDate;
	}

	public void setOrderInactiveDate(Date orderInactiveDate) {
		this.orderInactiveDate = orderInactiveDate;
	}

	public Long getWechatPlatformId() {
		return wechatPlatformId;
	}

	public void setWechatPlatformId(Long wechatPlatformId) {
		this.wechatPlatformId = wechatPlatformId;
	}

	public String getWechatPlatformName() {
		return wechatPlatformName;
	}

	public void setWechatPlatformName(String wechatPlatformName) {
		this.wechatPlatformName = wechatPlatformName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getIsFollow() {
		return isFollow;
	}

	public void setIsFollow(Integer isFollow) {
		this.isFollow = isFollow;
	}

	public Integer getRebateType() {
		return rebateType;
	}

	public void setRebateType(Integer rebateType) {
		this.rebateType = rebateType;
	}

	public Integer getRebateChannel() {
		return rebateChannel;
	}

	public void setRebateChannel(Integer rebateChannel) {
		this.rebateChannel = rebateChannel;
	}

	public Long getPerAmount() {
		return perAmount;
	}

	public void setPerAmount(Long perAmount) {
		this.perAmount = perAmount;
	}

	public String getPerAmountStr() {
		return perAmountStr;
	}

	public void setPerAmountStr(String perAmountStr) {
		this.perAmountStr = perAmountStr;
	}

	public Integer getMaxOrderLimit() {
		return maxOrderLimit;
	}

	public void setMaxOrderLimit(Integer maxOrderLimit) {
		this.maxOrderLimit = maxOrderLimit;
	}

	public Integer getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(Integer orderCount) {
		this.orderCount = orderCount;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public Long getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Long totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getTotalAmountStr() {
		return totalAmountStr;
	}

	public void setTotalAmountStr(String totalAmountStr) {
		this.totalAmountStr = totalAmountStr;
	}

	public Integer getIsAudit() {
		return isAudit;
	}

	public void setIsAudit(Integer isAudit) {
		this.isAudit = isAudit;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getShopNo() {
		return shopNo;
	}

	public void setShopNo(String shopNo) {
		this.shopNo = shopNo;
	}

	public String getTemplateLink() {
		return templateLink;
	}

	public void setTemplateLink(String templateLink) {
		this.templateLink = templateLink;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Activity{" +
				"activityType=" + activityType +
				", name='" + name + '\'' +
				", activeDate=" + activeDate +
				", inactiveDate=" + inactiveDate +
				", orderActiveDate=" + orderActiveDate +
				", orderInactiveDate=" + orderInactiveDate +
				", url='" + url + '\'' +
				", isFollow=" + isFollow +
				", rebateType=" + rebateType +
				", rebateChannel=" + rebateChannel +
				", perAmount=" + perAmount +
				", perAmountStr='" + perAmountStr + '\'' +
				", maxOrderLimit=" + maxOrderLimit +
				", orderCount=" + orderCount +
				", version=" + version +
				", totalAmount=" + totalAmount +
				", totalAmountStr='" + totalAmountStr + '\'' +
				", isAudit=" + isAudit +
				", shopName='" + shopName + '\'' +
				", shopNo='" + shopNo + '\'' +
				", templateLink='" + templateLink + '\'' +
				", status=" + status +
				'}';
	}
}