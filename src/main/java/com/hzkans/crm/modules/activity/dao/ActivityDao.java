/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.hzkans.crm.modules.activity.dao;

import com.hzkans.crm.common.persistence.CrudDao;
import com.hzkans.crm.common.persistence.annotation.MyBatisDao;
import com.hzkans.crm.modules.activity.entity.Activity;

import java.util.Map;

/**
 * 活动管理DAO接口
 * @author wsh
 * @version 2018-11-26
 */
@MyBatisDao
public interface ActivityDao extends CrudDao<Activity> {
    Activity getActivity(Map map);
}