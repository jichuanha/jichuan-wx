package com.hzkans.crm.modules.wechat.web;

import com.hzkans.crm.common.constant.ResponseEnum;
import com.hzkans.crm.common.utils.JsonUtil;
import com.hzkans.crm.common.utils.RequestUtils;
import com.hzkans.crm.common.utils.ResponseUtils;
import com.hzkans.crm.modules.wechat.entity.MemberAssociation;
import com.hzkans.crm.modules.wechat.entity.WechatPlatfrom;
import com.hzkans.crm.modules.wechat.service.MemberAssociationService;
import com.hzkans.crm.modules.wechat.service.WechatInfoService;
import com.hzkans.crm.modules.wechat.service.WechatPlatfromService;
import com.hzkans.crm.modules.wechat.utils.MessageUtil;
import com.hzkans.crm.modules.wechat.utils.WechatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * @author jc
 * @description 业务整合微信接口
 * @create 2018/12/5
 */
@Controller
@RequestMapping("/wechat")
public class AcceptEventPushController{

    private static Logger logger = LoggerFactory.getLogger(AcceptEventPushController.class);

    @Autowired
    private WechatPlatfromService wechatPlatfromService;
    @Autowired
    private WechatInfoService wechatInfoService;
    @Autowired
    private MemberAssociationService memberAssociationService;


    @RequestMapping(value="/api.do",method = RequestMethod.GET)
    @ResponseBody
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // 微信加密签名
            String signature = request.getParameter("signature");
            // 时间戳
            String timestamp = request.getParameter("timestamp");
            // 随机数
            String nonce = request.getParameter("nonce");
            // 随机字符串
            String echostr = request.getParameter("echostr");
            logger.info("[{}] echostr:{}",echostr);
            PrintWriter out = response.getWriter();

            WechatPlatfrom wechatPlatformById = wechatPlatfromService.getWechatPlatformById(12L);
            // 通过检验signature对请求进行校验，若校成功则原样返回echostr，表示接入成功，否则接入失败
            if (WechatUtils.checkSignature(signature, timestamp, nonce,wechatPlatformById.getToken())) {
                out.print(echostr);
            }

            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @RequestMapping(value="/api.do",method = RequestMethod.POST)
    @ResponseBody
    public void responseInfo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Map<String, String> requestMap = MessageUtil.parseXml(request);
            String result = wechatInfoService.messageDeal(requestMap);
            PrintWriter writer = response.getWriter();
            writer.print(result);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 根据code获取用户信息
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/getUserInfo")
    @ResponseBody
    public String getWxUserInfo(HttpServletRequest request, HttpServletResponse response) {
        String code = RequestUtils.getString(request, "code", "code is null");
        String appId = RequestUtils.getString(request, "appid", "app_id is null");
        Long actId = RequestUtils.getLong(request, "act_id", "act_id is null");
        try {
            //根据appid找到对应的微信信息
            WechatPlatfrom platfrom = new WechatPlatfrom();
            platfrom.setAppId(appId);
            WechatPlatfrom wechatPlatform = wechatPlatfromService.getWechatPlatform(platfrom);
            //调取微信接口
            Map<String, Object> userInfo = wechatInfoService.getUserInfo(code, appId, wechatPlatform.getAppSecret());
            logger.info("userInfo {}", JsonUtil.toJson(userInfo));
            //将信息更新到数据库
            MemberAssociation association = new MemberAssociation();
            association.setHeadUrl((String) userInfo.get("headimgurl"));
            association.setNickName((String) userInfo.get("nickname"));
            association.setSex((Integer) userInfo.get("sex"));
            association.setUnionId((String) userInfo.get("unionid"));
            memberAssociationService.save(association);
            return ResponseUtils.getSuccessApiResponseStr(userInfo);
        } catch (Exception e) {
            logger.error("getWxUserInfo error",e);
            return ResponseUtils.getFailApiResponseStr(ResponseEnum.S_E_SERVICE_ERROR);
        }

    }

}
