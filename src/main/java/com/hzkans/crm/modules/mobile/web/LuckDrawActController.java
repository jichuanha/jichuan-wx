package com.hzkans.crm.modules.mobile.web;

import com.hzkans.crm.common.constant.ResponseEnum;
import com.hzkans.crm.common.service.ServiceException;
import com.hzkans.crm.common.utils.*;
import com.hzkans.crm.common.web.BaseController;
import com.hzkans.crm.modules.activity.entity.Activity;
import com.hzkans.crm.modules.activity.entity.ActivityLottery;
import com.hzkans.crm.modules.activity.service.ActivityLotteryService;
import com.hzkans.crm.modules.activity.service.ActivityService;
import com.hzkans.crm.modules.trade.constants.JoinActivityStatusEnum;
import com.hzkans.crm.modules.trade.entity.JoinActivity;
import com.hzkans.crm.modules.trade.entity.QueryResult;
import com.hzkans.crm.modules.trade.service.JoinActivityService;
import com.hzkans.crm.modules.trade.utils.TradeUtil;
import com.hzkans.crm.modules.wechat.entity.MemberAssociation;
import com.hzkans.crm.modules.wechat.entity.WechatPlatfrom;
import com.hzkans.crm.modules.wechat.service.MemberAssociationService;
import com.hzkans.crm.modules.wechat.service.WechatPlatfromService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author jc
 * @description
 * @create 2018/12/20
 */
@Controller
@RequestMapping("${frontPath}/luckAct")
public class LuckDrawActController extends BaseController {

    private static final Integer MAX_NUM = 4;

    @Autowired
    private ActivityService activityService;
    @Autowired
    private WechatPlatfromService wechatPlatfromService;
    @Autowired
    private MemberAssociationService memberAssociationService;
    @Autowired
    private JoinActivityService joinActivityService;
    @Autowired
    private ActivityLotteryService activityLotteryService;

    @RequestMapping("/lotteryPage")
    public String intoLottery() {
        return "modules/mobile/lottery";
    }

    /**
     * 根据手机号确定抽奖次数
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/drawNum")
    @ResponseBody
    public String ensureDrawNum(HttpServletRequest request, HttpServletResponse response) {
        Long actId = RequestUtils.getLong(request, "act_id", "","act_id is null");
        Integer actType = RequestUtils.getInt(request, "act_type", "","act_type is null");
        String mobile = RequestUtils.getString(request, "mobile", "mobile is null");
        String openId = RequestUtils.getString(request, "open_id", "open_id is null");
        String appId = RequestUtils.getString(request, "app_id", "app_id is null");
        String messageCode = RequestUtils.getString(request, "message_code");
        //如果有验证码,验证验证码的有效性  TODO 测试先去掉
        /*if(!StringUtils.isEmpty(messageCode)) {
            String values = (String) CacheUtils.get("wechatCache", mobile);
            logger.info(" code values {}",values);
            if(values == null || !messageCode.equals(values)) {
                return ResponseUtils.getFailApiResponseStr(ResponseEnum.B_E_MOBILE_VERIFY_CODE_ERROR);
            }
        }*/

        QueryResult queryResult = new QueryResult();
        //校验活动和手机号
        ActivityLottery activityLottery = new ActivityLottery();
        activityLottery.setId(actId.toString());
        activityLottery.setActivityType(actType);
        ActivityLottery activity = activityLotteryService.getActivityLottery(activityLottery);
        logger.info(" activity {}", JsonUtil.toJson(activity));
        String message = checkActivity(activity, mobile, appId);
        if(null != message) {
            logger.info("message {}", message);
            return ResponseUtils.getFailApiResponseStr(ResponseEnum.S_E_SERVICE_ERROR, message);
        }
        //获取根据不同手机号查询的次数
        Integer currentNum = (Integer) CacheUtils.get(openId+MAX_NUM);
        logger.info("currentNum {}",currentNum);
        if(currentNum == null) {
            currentNum = 0;
        }
        if(currentNum >= MAX_NUM) {
            //当数量大于4次时,会返回提示走验证码,当带着验证码来的时候,还会再走这个方法,防止出现重复返回问题
            queryResult.setCodeFlg(true);
            String cache = (String) CacheUtils.get("wechatCache", mobile+MAX_NUM);
            if(StringUtils.isEmpty(cache)) {
                CacheUtils.put("wechatCache", mobile+MAX_NUM, mobile);
                return ResponseUtils.getSuccessApiResponseStr(queryResult);
            }

        }else {
            CacheUtils.put(openId+MAX_NUM, currentNum+1);
        }
        //参加活动的业务逻辑
        try {
            queryResult.setActId(actId);
            queryResult.setActType(actType);
            queryResult.setMobile(mobile);
            queryResult.setOpenId(openId);
            queryResult.setAppId(appId);
            QueryResult result = joinActivityService.joinActivity(queryResult, activity);
            logger.info(" result {}",result);
            return ResponseUtils.getSuccessApiResponseStr(result);
        } catch (Exception e) {
            logger.error("ensureDrawNum con error",e);
            return ResponseUtils.getFailApiResponseStr(ResponseEnum.S_E_SERVICE_ERROR);
        }

    }

    @RequestMapping("/drawPageInfo")
    @ResponseBody
    public String drawPageInfo(HttpServletRequest request, HttpServletResponse response) {
        //有两种情况 1.绑定手机号了就不需要传入手机号,2.没有绑定,就需要传入手机号
        String mobile = RequestUtils.getString(request, "mobile");
        String openId = RequestUtils.getString(request, "open_id", "open_id is null");
        Long actId = RequestUtils.getLong(request, "act_id", "" ,"act_id is null");
        Integer actType = RequestUtils.getInt(request, "act_type", "" ,"act_type is null");
        String appId = RequestUtils.getString(request, "app_id", "app_id is null");
        try {
            if(mobile == null) {
                //绑定手机号情况
                MemberAssociation association = new MemberAssociation();
                association.setOpenId(openId);
                List<MemberAssociation> messageAttentionInfo = memberAssociationService.getMessageAttentionInfo(association);
                logger.info("messageAttentionInfo {}",JsonUtil.toJson(messageAttentionInfo));
                if(CollectionUtils.isEmpty(messageAttentionInfo)) {
                    return ResponseUtils.getFailApiResponseStr(ResponseEnum.S_E_SERVICE_ERROR);
                }
                mobile = messageAttentionInfo.get(0).getMobile();
                //如果通过个人中心绑定的手机号,就不会走参加活动这个步骤,所以需要重新走
                ActivityLottery activityLottery = new ActivityLottery();
                activityLottery.setId(actId.toString());
                activityLottery.setActivityType(actType);
                ActivityLottery activity = activityLotteryService.getActivityLottery(activityLottery);
                QueryResult queryResult = new QueryResult();
                queryResult.setCodeFlg(false);
                queryResult.setActId(actId);
                queryResult.setActType(actType);
                queryResult.setMobile(mobile);
                queryResult.setOpenId(openId);
                queryResult.setAppId(appId);
                joinActivityService.joinActivity(queryResult, activity);
            }

            //根据手机号查询抽奖次数
            JoinActivity joinActivity = new JoinActivity();
            joinActivity.setActId(actId);
            joinActivity.setActType(actType);
            joinActivity.setMobile(mobile);
            //本次活动不需要审核
            joinActivity.setStatus(JoinActivityStatusEnum.UN_AUDIT.getCode());
            logger.info("joinActivity {}",JsonUtil.toJson(joinActivity));
            return ResponseUtils.getSuccessApiResponseStr(joinActivityService.getDrawNum(joinActivity));
        } catch (ServiceException e) {
            logger.error("drawPageInfo controller error",e);
            return ResponseUtils.getFailApiResponseStr(ResponseEnum.S_E_SERVICE_ERROR);
        }
    }





    /**
     * 参数校验
     * @param activity
     * @param mobile
     * @param appId
     * @return
     */
    private String checkActivity(ActivityLottery activity, String mobile, String appId) {
        //校验手机号
        Boolean checkMobile = TradeUtil.checkMobile(mobile);
        if(!checkMobile) {
            return "手机号码格式有误";
        }
        WechatPlatfrom platfrom = (WechatPlatfrom) CacheUtils.get("wechatCache", appId);
        if(null == platfrom) {
            WechatPlatfrom plat = new WechatPlatfrom();
            plat.setAppId(appId);
            platfrom = wechatPlatfromService.getWechatPlatform(plat);
        }
        MemberAssociation association = new MemberAssociation();
        association.setWechatId(platfrom.getWechatNo());
        association.setMobile(mobile);
        List<MemberAssociation> messageAttentionInfo = memberAssociationService.getMessageAttentionInfo(association);
        if(CollectionUtils.isNotEmpty(messageAttentionInfo)) {
            return "号码已经被绑定";
        }
        if(null == activity) {
            return "活动已结束";
        }
        if(!activity.getStatus().equals(1)) {
            return "活动已结束";
        }
        return null;

    }

}
