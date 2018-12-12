/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.hzkans.crm.modules.activity.service;

import java.util.List;

import com.hzkans.crm.modules.activity.dao.ActivityPrizeDao;
import com.hzkans.crm.modules.activity.entity.ActivityPrize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hzkans.crm.common.persistence.Page;
import com.hzkans.crm.common.service.CrudService;
import com.hzkans.crm.modules.activity.entity.ActivityLottery;
import com.hzkans.crm.modules.activity.dao.ActivityLotteryDao;

/**
 * 幸运抽奖活动Service
 * @author wsh
 * @version 2018-12-11
 */
@Service
@Transactional(readOnly = false)
public class ActivityLotteryService extends CrudService<ActivityLotteryDao, ActivityLottery> {


	@Autowired
	private ActivityPrizeDao activityPrizeDao;

	@Override
	public ActivityLottery get(String id) {
		return super.get(id);
	}

	@Override
	public List<ActivityLottery> findList(ActivityLottery activityLottery) {
		return super.findList(activityLottery);
	}

	@Override
	public Page<ActivityLottery> findPage(Page<ActivityLottery> page, ActivityLottery activityLottery) {
		return super.findPage(page, activityLottery);
	}

	@Override
	@Transactional(readOnly = false)
	public void save(ActivityLottery activityLottery) {
		super.save(activityLottery);
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(ActivityLottery activityLottery) {
		super.delete(activityLottery);
	}

}