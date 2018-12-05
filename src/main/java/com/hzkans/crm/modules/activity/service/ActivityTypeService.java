/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.hzkans.crm.modules.activity.service;

import java.util.List;

import com.hzkans.crm.common.persistence.Page;
import com.hzkans.crm.common.service.CrudService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hzkans.crm.modules.activity.entity.ActivityType;
import com.hzkans.crm.modules.activity.dao.ActivityTypeDao;

/**
 * 活动类型Service
 * @author wsh
 * @version 2018-12-05
 */
@Service
@Transactional(readOnly = true)
public class ActivityTypeService extends CrudService<ActivityTypeDao, ActivityType> {

	@Override
	public ActivityType get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<ActivityType> findList(ActivityType ActivityType) {
		return super.findList(ActivityType);
	}

	@Override
	public Page<ActivityType> findPage(Page<ActivityType> page, ActivityType ActivityType) {
		return super.findPage(page, ActivityType);
	}

	@Override
	@Transactional(readOnly = false)
	public void save(ActivityType ActivityType) {
		super.save(ActivityType);
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(ActivityType ActivityType) {
		super.delete(ActivityType);
	}
	
}