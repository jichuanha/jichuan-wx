/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.hzkans.crm.modules.activity.dao;

import com.hzkans.crm.common.persistence.CrudDao;
import com.hzkans.crm.common.persistence.annotation.MyBatisDao;
import com.hzkans.crm.modules.activity.entity.ActivityLottery;

/**
 * 幸运抽奖活动DAO接口
 * @author wsh
 * @version 2018-12-11
 */
@MyBatisDao
public interface ActivityLotteryDao extends CrudDao<ActivityLottery> {
    ActivityLottery getActivityLottery(ActivityLottery activityLottery);
}