/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.hzkans.crm.modules.activity.service;

import com.hzkans.crm.common.persistence.Page;
import com.hzkans.crm.common.service.CrudService;
import com.hzkans.crm.modules.activity.dao.ActivityDao;
import com.hzkans.crm.modules.activity.entity.Activity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 活动管理Service
 * @author wsh
 * @version 2018-11-26
 */
@Service
@Transactional(readOnly = true)
public class ActivityService extends CrudService<ActivityDao, Activity> {

	@Override
	public Activity get(String id) {
		return super.get(id);
	}

	@Override
	public List<Activity> findList(Activity activity) {
		return super.findList(activity);
	}

	@Override
	public Page<Activity> findPage(Page<Activity> page, Activity activity) {
		return super.findPage(page, activity);
	}

	@Override
	@Transactional(readOnly = false)
	public void save(Activity activity) {
		super.save(activity);
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(Activity activity) {
		super.delete(activity);
	}

	@Override
	@Transactional(readOnly = false)
	public void update(Activity activity) {
		super.update(activity);
	}
	
}