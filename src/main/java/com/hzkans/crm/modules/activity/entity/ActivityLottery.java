/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.hzkans.crm.modules.activity.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.hzkans.crm.common.persistence.DataEntity;

/**
 * 幸运抽奖活动Entity
 * @author wsh
 * @version 2018-12-11
 */
public class ActivityLottery extends DataEntity<ActivityLottery> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 活动名称
	private Integer activityType;		// 活动类型
	private Integer status;		// 活动状态 ：0- 未开始  1-进行中 2-暂停 3-已结束
	private Date activeDate;		// 活动有效开始时间
	private Date inactiveDate;		// 活动失效时间
	private Date orderActiveDate;		// 订单时效时间
	private Date orderInactiveDate;		// 订单时效时间
	private Long wechatPlatformId;		// 公众号id
	private String url;		// url
	private Integer isFollow;		// 关注类型  0 -需要 1-不需要
	private Integer auditType;		// 验证方式 ： 1-手机号
	private Integer textAuditType;		// 短信校验 0：否  1：是
	private Integer totalOrder;		// 限制订单总数量 0表示不限制
	private Long version;		// 版本号
	private String shopName;		// 店铺名称
	private String shopNo;		// 店铺编号
	private String templateLink;		// 模板url
	private List<ActivityPrize> activityPrizeList;
	public ActivityLottery() {
		super();
	}

	public ActivityLottery(String id){
		super(id);
	}

	public List<ActivityPrize> getActivityPrizeList() {
		return activityPrizeList;
	}

	public void setActivityPrizeList(List<ActivityPrize> activityPrizeList) {
		this.activityPrizeList = activityPrizeList;
	}

	@Length(min=1, max=255, message="活动名称长度必须介于 1 和 255 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@NotNull(message="活动类型不能为空")
	public Integer getActivityType() {
		return activityType;
	}

	public void setActivityType(Integer activityType) {
		this.activityType = activityType;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="活动有效开始时间不能为空")
	public Date getActiveDate() {
		return activeDate;
	}

	public void setActiveDate(Date activeDate) {
		this.activeDate = activeDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="活动失效时间不能为空")
	public Date getInactiveDate() {
		return inactiveDate;
	}

	public void setInactiveDate(Date inactiveDate) {
		this.inactiveDate = inactiveDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="订单时效时间不能为空")
	public Date getOrderActiveDate() {
		return orderActiveDate;
	}

	public void setOrderActiveDate(Date orderActiveDate) {
		this.orderActiveDate = orderActiveDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="订单时效时间不能为空")
	public Date getOrderInactiveDate() {
		return orderInactiveDate;
	}

	public void setOrderInactiveDate(Date orderInactiveDate) {
		this.orderInactiveDate = orderInactiveDate;
	}
	
	@NotNull(message="公众号id不能为空")
	public Long getWechatPlatformId() {
		return wechatPlatformId;
	}

	public void setWechatPlatformId(Long wechatPlatformId) {
		this.wechatPlatformId = wechatPlatformId;
	}
	
	@Length(min=0, max=255, message="url长度必须介于 0 和 255 之间")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	@NotNull(message="关注类型  0 -需要 1-不需要不能为空")
	public Integer getIsFollow() {
		return isFollow;
	}

	public void setIsFollow(Integer isFollow) {
		this.isFollow = isFollow;
	}
	
	@NotNull(message="验证方式 ： 1-手机号不能为空")
	public Integer getAuditType() {
		return auditType;
	}

	public void setAuditType(Integer auditType) {
		this.auditType = auditType;
	}
	
	@NotNull(message="短信校验 0：否  1：是不能为空")
	public Integer getTextAuditType() {
		return textAuditType;
	}

	public void setTextAuditType(Integer textAuditType) {
		this.textAuditType = textAuditType;
	}
	
	public Integer getTotalOrder() {
		return totalOrder;
	}

	public void setTotalOrder(Integer totalOrder) {
		this.totalOrder = totalOrder;
	}
	
	@NotNull(message="版本号不能为空")
	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}
	
	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	
	@Length(min=1, max=255, message="店铺编号长度必须介于 1 和 255 之间")
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

	@Override
	public String toString() {
		return "ActivityLottery{" +
				"name='" + name + '\'' +
				", activityType=" + activityType +
				", status=" + status +
				", activeDate=" + activeDate +
				", inactiveDate=" + inactiveDate +
				", orderActiveDate=" + orderActiveDate +
				", orderInactiveDate=" + orderInactiveDate +
				", wechatPlatformId=" + wechatPlatformId +
				", url='" + url + '\'' +
				", isFollow=" + isFollow +
				", auditType=" + auditType +
				", textAuditType=" + textAuditType +
				", totalOrder=" + totalOrder +
				", version=" + version +
				", shopName='" + shopName + '\'' +
				", shopNo='" + shopNo + '\'' +
				", templateLink='" + templateLink + '\'' +
				", activityPrizeList=" + activityPrizeList +
				'}';
	}
}