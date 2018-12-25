/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.hzkans.crm.modules.activity.service;

import com.hzkans.crm.common.persistence.Page;
import com.hzkans.crm.common.service.CrudService;
import com.hzkans.crm.common.service.ServiceException;
import com.hzkans.crm.common.utils.JsonUtil;
import com.hzkans.crm.modules.activity.dao.ActivityLotteryDao;
import com.hzkans.crm.modules.activity.dao.ActivityPrizeDao;
import com.hzkans.crm.modules.activity.entity.ActivityLottery;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

	@Autowired
	private ActivityLotteryDao activityLotteryDao;

	@Override
	public ActivityLottery get(String id) {
		return super.get(id);
	}

	/**
	 * 获取幸运抽奖活动详情
	 * @param id
	 * @return
	 */
	public ActivityLottery getLottery(String id) {
		try {
			ActivityLottery activityLottery = get(id);

			if (null != activityLottery){
				String lotteryId = activityLottery.getId();
				ActivityLottery.LotteryPrize lotteryPrize = new ActivityLottery.LotteryPrize();
				lotteryPrize.setLotteryId(Long.valueOf(lotteryId));
				List<ActivityLottery.LotteryPrize> lotteryPrizeList = activityPrizeDao.findList(lotteryPrize);
				activityLottery.setLotteryPrizeList(lotteryPrizeList);
            }
			return activityLottery;
		} catch (NumberFormatException e) {
			logger.error("getLottery error",e);
			throw e;
		}
	}

	@Override
	public List<ActivityLottery> findList(ActivityLottery activityLottery) {
		return super.findList(activityLottery);
	}

	@Override
	public Page<ActivityLottery> findPage(Page<ActivityLottery> page, ActivityLottery activityLottery) {
		return super.findPage(page, activityLottery);
	}

	/**
	 * 获取幸运抽奖活动列表
	 * @param page
	 * @param activityLottery
	 * @return
	 */
	public Page<ActivityLottery> findPageLottery(Page<ActivityLottery> page, ActivityLottery activityLottery) {
		Page<ActivityLottery> activityLotteryPage = findPage(page, activityLottery);
		if (CollectionUtils.isNotEmpty(activityLotteryPage.getList())){
			for (ActivityLottery activityLottery1 : activityLotteryPage.getList()){
				String lotteryId = activityLottery1.getId();
				ActivityLottery.LotteryPrize lotteryPrize = new ActivityLottery.LotteryPrize();
				lotteryPrize.setLotteryId(Long.valueOf(lotteryId));
				List<ActivityLottery.LotteryPrize> lotteryPrizeList = activityPrizeDao.findList(lotteryPrize);
				activityLottery1.setLotteryPrizeList(lotteryPrizeList);
			}
		}
		return activityLotteryPage;
	}

	/**
	 * 新增幸运抽奖活动
	 * @param activityLottery
	 */
	@Transactional(rollbackFor = ServiceException.class)
	public void saveLottery(ActivityLottery activityLottery) {
		save(activityLottery);
		List<ActivityLottery.LotteryPrize> lotteryPrizeList = activityLottery.getLotteryPrizeList();
		logger.info("[{}]lotteryPrizeList:{}", JsonUtil.toJson(lotteryPrizeList));
		String lotteryId = activityLottery.getId();
		if (CollectionUtils.isNotEmpty(lotteryPrizeList)) {
			for (ActivityLottery.LotteryPrize lotteryPrize : lotteryPrizeList) {
				lotteryPrize.setLotteryId(Long.valueOf(lotteryId));
				activityPrizeDao.insert(lotteryPrize);
			}
		}
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(ActivityLottery activityLottery) {
		super.delete(activityLottery);
	}

	/**
	 * 根据活动id和活动类型获取活动
	 * @param activityLottery
	 * @return
	 */
	public ActivityLottery getActivityLottery(ActivityLottery activityLottery){
		activityLottery = activityLotteryDao.getActivityLottery(activityLottery);
		return activityLottery;
	}
}