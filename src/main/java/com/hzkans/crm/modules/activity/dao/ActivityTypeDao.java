/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.hzkans.crm.modules.activity.dao;

import com.hzkans.crm.common.persistence.CrudDao;
import com.hzkans.crm.common.persistence.annotation.MyBatisDao;
import com.hzkans.crm.modules.activity.entity.ActivityType;

/**
 * 活动类型DAO接口
 * @author wsh
 * @version 2018-12-05
 */
@MyBatisDao
public interface ActivityTypeDao extends CrudDao<ActivityType> {
	
}