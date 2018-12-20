package com.hzkans.crm.modules.mobile.web;

import com.hzkans.crm.common.constant.ResponseEnum;
import com.hzkans.crm.common.utils.CacheUtils;
import com.hzkans.crm.common.utils.RequestUtils;
import com.hzkans.crm.common.utils.ResponseUtils;
import com.hzkans.crm.common.utils.StringUtils;
import com.hzkans.crm.common.web.BaseController;
import com.hzkans.crm.modules.activity.entity.Activity;
import com.hzkans.crm.modules.activity.service.ActivityService;
import com.hzkans.crm.modules.trade.constants.NeedCodeEnum;
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
        String mobile = RequestUtils.getString(request, "mobile", "mobile is null");
        String openId = RequestUtils.getString(request, "open_id", "open_id is null");
        String appId = RequestUtils.getString(request, "app_id", "app_id is null");
        String messageCode = RequestUtils.getString(request, "message_code");
        //如果有验证码,验证验证码的有效性
        if(!StringUtils.isEmpty(messageCode)) {
            String[] values = (String[]) CacheUtils.get("wechatCache", mobile);
            if(values == null || !messageCode.equals(values[0])) {
                return ResponseUtils.getFailApiResponseStr(ResponseEnum.B_E_MOBILE_VERIFY_CODE_ERROR);
            }
        }

        QueryResult queryResult = new QueryResult();
        //校验活动和手机号
        Activity activity = activityService.get(actId.toString());
        String message = checkActivity(activity, mobile, appId);
        if(null != message) {
            logger.info("message {}", message);
            return ResponseUtils.getFailApiResponseStr(ResponseEnum.S_E_SERVICE_ERROR, message);
        }
        //获取根据不同手机号查询的次数
        JoinActivity joinActivity = new JoinActivity();
        joinActivity.setOpenId(openId);
        joinActivity.setMobile(mobile);
        int count = joinActivityService.queryImportMobileNum(joinActivity);
        if(count > MAX_NUM) {
            //当数量大于4次时,会返回提示走验证码,当带着验证码来的时候,还会再走这个方法,防止出现重复返回问题
            String cache = (String) CacheUtils.get("wechatCache", mobile+MAX_NUM);
            if(StringUtils.isEmpty(cache)) {
                queryResult.setNeedCode(NeedCodeEnum.NEED.getCode());
                CacheUtils.put("wechatCache", mobile+MAX_NUM, mobile);
                return ResponseUtils.getSuccessApiResponseStr(queryResult);
            }

        }
        //参加活动的业务逻辑


        return null;
    }

    /**
     * 参数校验
     * @param activity
     * @param mobile
     * @param appId
     * @return
     */
    private String checkActivity(Activity activity, String mobile, String appId) {
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
