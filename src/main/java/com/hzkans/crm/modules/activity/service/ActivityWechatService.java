/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.hzkans.crm.modules.activity.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hzkans.crm.common.persistence.Page;
import com.hzkans.crm.common.service.CrudService;
import com.hzkans.crm.modules.activity.entity.ActivityWechat;
import com.hzkans.crm.modules.activity.dao.ActivityWechatDao;

/**
 * 活动公众号关系Service
 * @author wsh
 * @version 2018-12-06
 */
@Service
@Transactional(readOnly = true)
public class ActivityWechatService extends CrudService<ActivityWechatDao, ActivityWechat> {

	@Override
	public ActivityWechat get(String id) {
		return super.get(id);
	}

	@Override
	public List<ActivityWechat> findList(ActivityWechat activityWechat) {
		return super.findList(activityWechat);
	}

	@Override
	public Page<ActivityWechat> findPage(Page<ActivityWechat> page, ActivityWechat activityWechat) {
		return super.findPage(page, activityWechat);
	}

	@Override
	@Transactional(readOnly = false)
	public void save(ActivityWechat activityWechat) {
		super.save(activityWechat);
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(ActivityWechat activityWechat) {
		super.delete(activityWechat);
	}
	
}