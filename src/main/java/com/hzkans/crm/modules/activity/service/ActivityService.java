/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.hzkans.crm.modules.activity.service;

import java.util.List;

import com.hzkans.crm.common.persistence.Page;
import com.hzkans.crm.common.service.CrudService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hzkans.crm.modules.activity.entity.Activity;
import com.hzkans.crm.modules.activity.dao.ActivityDao;

/**
 * 活动管理Service
 * @author wsh
 * @version 2018-11-26
 */
@Service
@Transactional(readOnly = true)
public class ActivityService extends CrudService<ActivityDao, Activity> {

	public Activity get(String id) {
		return super.get(id);
	}
	
	public List<Activity> findList(Activity activity) {
		return super.findList(activity);
	}
	
	public Page<Activity> findPage(Page<Activity> page, Activity activity) {
		return super.findPage(page, activity);
	}
	
	@Transactional(readOnly = false)
	public void save(Activity activity) {
		super.save(activity);
	}
	
	@Transactional(readOnly = false)
	public void delete(Activity activity) {
		super.delete(activity);
	}

	@Transactional(readOnly = false)
	public void update(Activity activity) {
		super.update(activity);
	}
	
}