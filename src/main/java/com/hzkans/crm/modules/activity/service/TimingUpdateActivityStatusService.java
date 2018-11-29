package com.hzkans.crm.modules.activity.service;

import com.hzkans.crm.common.persistence.Page;
import com.hzkans.crm.modules.activity.constants.ActivityStatusEnum;
import com.hzkans.crm.modules.activity.entity.Activity;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Schedules;
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
    private static final Integer TRANSFORM_AMOUNT = Integer.valueOf(100);

    @Autowired
    private  ActivityService activityService;


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
                    //现在时间在活动时间内，且状态不为暂停和进行中的才做状态更新
                    if (now.getTime() > activeDate.getTime() && now.getTime() < inactiveDate.getTime()){
                        if ((!status.equals(ActivityStatusEnum.ACTIVING.getCode())
                                && !status.equals(ActivityStatusEnum.PAUSE.getCode()))){
                            activity1.setStatus(ActivityStatusEnum.ACTIVING.getCode());
                        }

                        //已过活动时间，且状态不为已结束的才做状态更新
                    }else if (now.getTime() > inactiveDate.getTime()
                            && !status.equals(ActivityStatusEnum.ENDED.getCode())){
                        activity1.setStatus(ActivityStatusEnum.ENDED.getCode());
                    }
                    activityService.update(activity1);
                    logger.info("已更改活动"+_activity.getName()+"的状态");
                }
            }
        } catch (Exception e) {
            logger.error("update status is error",e);
        }
    }
}
