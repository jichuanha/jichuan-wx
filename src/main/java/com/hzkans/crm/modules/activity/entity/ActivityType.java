/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.hzkans.crm.modules.activity.entity;

import com.hzkans.crm.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;


/**
 * 活动类型Entity
 * @author wsh
 * @version 2018-12-05
 */
public class ActivityType extends DataEntity<ActivityType> {
	
	private static final long serialVersionUID = 1L;
	private String typeName;		// 活动类型名称

	public ActivityType() {
		super();
	}

	public ActivityType(String id){
		super(id);
	}

	@Length(min=1, max=255, message="活动类型名称长度必须介于 1 和 255 之间")
	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
}