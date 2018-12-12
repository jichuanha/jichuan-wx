/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.hzkans.crm.modules.activity.entity;

import javax.validation.constraints.NotNull;

import com.hzkans.crm.common.persistence.DataEntity;

/**
 * 活动公众号关系Entity
 * @author wsh
 * @version 2018-12-06
 */
public class ActivityWechat extends DataEntity<ActivityWechat> {
	
	private static final long serialVersionUID = 1L;
	private Long activityId;		// 活动id
	private Long wechatPlatformId;		// 公众号id
	
	public ActivityWechat() {
		super();
	}

	public ActivityWechat(String id){
		super(id);
	}

	@NotNull(message="活动id不能为空")
	public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}
	
	@NotNull(message="公众号id不能为空")
	public Long getWechatPlatformId() {
		return wechatPlatformId;
	}

	public void setWechatPlatformId(Long wechatPlatformId) {
		this.wechatPlatformId = wechatPlatformId;
	}
	
}