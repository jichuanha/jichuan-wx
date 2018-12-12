package com.hzkans.crm.modules.activity.service;

import com.hzkans.crm.modules.activity.constants.ActivityStatusEnum;
import com.hzkans.crm.modules.activity.entity.Activity;
import com.hzkans.crm.modules.activity.entity.ActivityLottery;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 定时更新活动状态
 * @author wsh
 * @description 定时器
 * @create 2018/11/29
 */
@Service
@Transactional
public class TimingUpdateActivityStatusService {

    Logger logger = LoggerFactory.getLogger(TimingUpdateActivityStatusService.class);

    @Autowired
    private  ActivityService activityService;

    @Autowired
    private  ActivityLotteryService activityLotteryService;

    /**
     * 根据活动时间更新活动状态
     */
    public void updateStatus() throws Exception{

        try {
            Activity activity = new Activity();
            //查询所有活动列表
            logger.info("更改活动状态定时任务开启");
            List<Activity> activityList = activityService.findList(activity);
            if (CollectionUtils.isNotEmpty(activityList)){
                for (Activity _activity : activityList){
                    Activity activity1 = new Activity();
                    Date now = new Date();
                    Integer status = _activity.getStatus();
                    Date activeDate = _activity.getActiveDate();
                    Date inactiveDate = _activity.getInactiveDate();
                    activity1.setId(_activity.getId());
                    //现在时间在活动时间内，且状态不为暂停、进行中和已结束的才做状态更新
                    if (now.getTime() > activeDate.getTime() && now.getTime() < inactiveDate.getTime()){
                        if (!ActivityStatusEnum.ACTIVING.getCode().equals(status)
                                && !ActivityStatusEnum.PAUSE.getCode().equals(status)
                                && !ActivityStatusEnum.ENDED.getCode().equals(status)){
                            activity1.setStatus(ActivityStatusEnum.ACTIVING.getCode());
                            activityService.update(activity1);
                            logger.info("已更改活动"+_activity.getName()+"的状态");
                        }
                        //已过活动时间，且状态不为已结束的才做状态更新
                    }else if (now.getTime() > inactiveDate.getTime()
                            && !ActivityStatusEnum.ENDED.getCode().equals(status)){
                        activity1.setStatus(ActivityStatusEnum.ENDED.getCode());
                        activityService.update(activity1);
                        logger.info("已更改活动"+_activity.getName()+"的状态");
                    }else if (now.getTime() < activeDate.getTime()
                            && !ActivityStatusEnum.NOT_START.getCode().equals(status)){
                        activity1.setStatus(ActivityStatusEnum.NOT_START.getCode());
                        activityService.update(activity1);
                        logger.info("已更改活动"+_activity.getName()+"的状态");
                    }

                    Long perAmount = _activity.getPerAmount();
                    Integer maxOrderLimit = _activity.getMaxOrderLimit();
                    Integer orderCount = _activity.getOrderCount();
                    Long totalAmount = _activity.getTotalAmount();

                    Boolean boo = ((0 != maxOrderLimit && maxOrderLimit < orderCount)
                            || (0L != totalAmount && totalAmount < (perAmount * orderCount)))
                            && ActivityStatusEnum.ACTIVING.getCode().equals(status);
                    if (boo){
                        activity1.setStatus(ActivityStatusEnum.ENDED.getCode());
                        activityService.update(activity1);
                        logger.info("因金额或数量已过限制，则迫使"+_activity.getName()+"活动结束");
                    }
                }
            }


            ActivityLottery activityLottery = new ActivityLottery();
            //查询所有活动列表
            List<ActivityLottery> activityLotteryList = activityLotteryService.findList(activityLottery);
            if (CollectionUtils.isNotEmpty(activityList)){
                for (ActivityLottery _activitylottery : activityLotteryList){
                    ActivityLottery activityLottery1 = new ActivityLottery();
                    Date now = new Date();
                    Integer status = _activitylottery.getStatus();
                    Date activeDate = _activitylottery.getActiveDate();
                    Date inactiveDate = _activitylottery.getInactiveDate();
                    activityLottery1.setId(_activitylottery.getId());
                    //现在时间在活动时间内，且状态不为暂停、进行中和已结束的才做状态更新
                    if (now.getTime() > activeDate.getTime() && now.getTime() < inactiveDate.getTime()){
                        if (!ActivityStatusEnum.ACTIVING.getCode().equals(status)
                                && !ActivityStatusEnum.PAUSE.getCode().equals(status)
                                && !ActivityStatusEnum.ENDED.getCode().equals(status)){
                            activityLottery1.setStatus(ActivityStatusEnum.ACTIVING.getCode());
                            activityLotteryService.update(activityLottery1);
                            logger.info("已更改幸运抽奖活动"+_activitylottery.getName()+"的状态");
                        }
                        //已过活动时间，且状态不为已结束的才做状态更新
                    }else if (now.getTime() > inactiveDate.getTime()
                            && !ActivityStatusEnum.ENDED.getCode().equals(status)){
                        activityLottery1.setStatus(ActivityStatusEnum.ENDED.getCode());
                        activityLotteryService.update(activityLottery1);
                        logger.info("已更改幸运抽奖活动"+_activitylottery.getName()+"的状态");
                    }else if (now.getTime() < activeDate.getTime()
                            && !ActivityStatusEnum.NOT_START.getCode().equals(status)){
                        activityLottery1.setStatus(ActivityStatusEnum.NOT_START.getCode());
                        activityLotteryService.update(activityLottery1);
                        logger.info("已更改幸运抽奖活动"+_activitylottery.getName()+"的状态");
                    }
                }
            }
        } catch (Exception e) {
            logger.error("update status is error",e);
        }
    }
}
