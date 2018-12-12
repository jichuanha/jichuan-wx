/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.hzkans.crm.modules.activity.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hzkans.crm.common.persistence.Page;
import com.hzkans.crm.common.service.CrudService;
import com.hzkans.crm.modules.activity.entity.ActivityPrize;
import com.hzkans.crm.modules.activity.dao.ActivityPrizeDao;

/**
 * 奖品类型Service
 * @author wsh
 * @version 2018-12-11
 */
@Service
@Transactional(readOnly = true)
public class ActivityPrizeService extends CrudService<ActivityPrizeDao, ActivityPrize> {

	@Override
	public ActivityPrize get(String id) {
		return super.get(id);
	}

	@Override
	public List<ActivityPrize> findList(ActivityPrize activityPrize) {
		return super.findList(activityPrize);
	}

	@Override
	public Page<ActivityPrize> findPage(Page<ActivityPrize> page, ActivityPrize activityPrize) {
		return super.findPage(page, activityPrize);
	}

	@Override
	@Transactional(readOnly = false)
	public void save(ActivityPrize activityPrize) {
		super.save(activityPrize);
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(ActivityPrize activityPrize) {
		super.delete(activityPrize);
	}
	
}