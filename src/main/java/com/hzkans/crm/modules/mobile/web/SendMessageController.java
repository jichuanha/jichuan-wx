package com.hzkans.crm.modules.mobile.web;

import com.hzkans.crm.common.constant.ResponseEnum;
import com.hzkans.crm.common.utils.CacheUtils;
import com.hzkans.crm.common.utils.JsonUtil;
import com.hzkans.crm.common.utils.RequestUtils;
import com.hzkans.crm.common.utils.ResponseUtils;
import com.hzkans.crm.common.web.BaseController;
import com.hzkans.crm.modules.mobile.service.iface.SmsService;
import com.hzkans.crm.modules.trade.utils.TradeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author jc
 * @description
 * @create 2018/12/20
 */
@Controller
@RequestMapping("${frontPath}/message")
public class SendMessageController extends BaseController{

    private final static Logger logger = LoggerFactory.getLogger(SendMessageController.class);
    private final int minute = 10; /* 超时时间，单位分钟 */
    private final String tempSn = "mobile_verify";/* 手机验证码模板编号 */
    @Resource
    private SmsService smsService;

    @RequestMapping("/secondsTickSendSms")
    @ResponseBody
    protected String secondsTickSendSms(HttpServletRequest request) throws Exception {

        String mobile = RequestUtils.getString(request, "mobile", "手机号码不能为空");

        // 验证手机号码
        if(!TradeUtil.checkMobile(mobile)) {
            return ResponseUtils.getFailApiResponseStr(ResponseEnum.B_E_MOBILE_FORMAT_ERROR);
        }

		/* 生成验证码 */
        long verifyCode = System.currentTimeMillis() % 1000000;
        String verifyCodeStr = String.format("%06d", verifyCode);

		/* 短信动态内容 */
        String[] values = {verifyCodeStr, String.valueOf(minute)};
        logger.info(" set in cache key : " + mobile  + " value : " + verifyCodeStr);

		/* 验证码信息缓存，10分钟，按秒计算，key为mobile+handleType */
        try {

            Boolean resultRtn = smsService.sendSecondsTick(mobile, tempSn, values);
            String rtnStr = "短信发送失败";
            if (resultRtn) {
                CacheUtils.put("wechatCache",mobile,  verifyCodeStr);

                logger.info(" cache set success key : " + mobile  + " value : "
                        + CacheUtils.get("wechatCache", mobile));
                rtnStr = "短信已发送";
            }
            logger.info("rtnStr:{}", rtnStr);
            return ResponseUtils.getSuccessResponseStr(rtnStr);

        } catch (Exception e) {
            logger.error("secondsTickSendSms error",e);
            return ResponseUtils.getFailApiResponseStr(ResponseEnum.S_E_SERVICE_ERROR, e.getMessage());
        }

    }
}
