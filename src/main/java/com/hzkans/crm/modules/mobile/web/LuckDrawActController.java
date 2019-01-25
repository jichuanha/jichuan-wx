package com.hzkans.crm.modules.mobile.web;

import com.google.common.base.Strings;
import com.hzkans.crm.common.constant.ResponseEnum;
import com.hzkans.crm.common.service.ServiceException;
import com.hzkans.crm.common.utils.*;
import com.hzkans.crm.common.web.BaseController;
import com.hzkans.crm.modules.activity.entity.ActivityLottery;
import com.hzkans.crm.modules.activity.service.ActivityLotteryService;
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

    private static final Integer MAX_NUM = 3;

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
     * 根据手机号确定抽奖次数()
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/drawNum")
    @ResponseBody
    @Deprecated
    public String ensureDrawNum(HttpServletRequest request, HttpServletResponse response) {
        Long actId = RequestUtils.getLong(request, "act_id", "","act_id is null");
        Integer actType = RequestUtils.getInt(request, "act_type", "","act_type is null");
        String openId = RequestUtils.getString(request, "open_id", "open_id is null");
        String appId = RequestUtils.getString(request, "app_id", "app_id is null");
        String orderSn = RequestUtils.getString(request, "order_sn", "order_sn is null");
        String messageCode = RequestUtils.getString(request, "message_code");
        String mobile = RequestUtils.getString(request, "mobile");
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
        //获取根据不同订单号查询的次数
        Integer currentNum = (Integer) CacheUtils.get(openId+MAX_NUM);
        logger.info("currentNum {}",currentNum);
        if(currentNum == null) {
            currentNum = 0;
        }
        if(currentNum.equals(MAX_NUM)) {
            //当数量为第4次时,需要绑定手机号
            queryResult.setCodeFlg(true);
            if(Strings.isNullOrEmpty(mobile)) {
                return ResponseUtils.getFailApiResponseStr(ResponseEnum.B_E_MOBILE_IS_NULL);
            }
        }
        //参加活动次数加1
        CacheUtils.put(openId+MAX_NUM, currentNum+1);
        //参加活动的业务逻辑
        try {
            queryResult.setActId(actId);
            queryResult.setActType(actType);
            queryResult.setMobile(mobile);
            queryResult.setOpenId(openId);
            queryResult.setAppId(appId);
            queryResult.setOrderSn(orderSn);
            QueryResult result = joinActivityService.joinActivity(queryResult, activity);
            logger.info(" result {}",result);
            return ResponseUtils.getSuccessApiResponseStr(result);
        } catch (Exception e) {
            logger.error("ensureDrawNum con error",e);
            return ResponseUtils.getFailApiResponseStr(ResponseEnum.S_E_SERVICE_ERROR, e.getMessage());
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
        WechatPlatfrom platfrom = (WechatPlatfrom) CacheUtils.get("wechatCache", appId);
        if(null == platfrom) {
            WechatPlatfrom plat = new WechatPlatfrom();
            plat.setAppId(appId);
            platfrom = wechatPlatfromService.getWechatPlatform(plat);
        }
        if(!Strings.isNullOrEmpty(mobile)){
            MemberAssociation association = new MemberAssociation();
            association.setWechatId(platfrom.getWechatNo());
            association.setMobile(mobile);
            List<MemberAssociation> messageAttentionInfo = memberAssociationService.getMessageAttentionInfo(association);
            if(CollectionUtils.isNotEmpty(messageAttentionInfo)) {
                return "号码已经被绑定";
            }
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
