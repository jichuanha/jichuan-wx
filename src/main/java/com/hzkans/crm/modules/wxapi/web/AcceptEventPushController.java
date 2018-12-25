package com.hzkans.crm.modules.wxapi.web;

import com.hzkans.crm.common.constant.ResponseEnum;
import com.hzkans.crm.common.utils.*;
import com.hzkans.crm.modules.trade.constants.NeedCodeEnum;
import com.hzkans.crm.modules.wechat.entity.MemberAssociation;
import com.hzkans.crm.modules.wechat.entity.WechatPlatfrom;
import com.hzkans.crm.modules.wechat.service.MemberAssociationService;
import com.hzkans.crm.modules.wechat.service.WechatPlatfromService;
import com.hzkans.crm.modules.wxapi.service.WxApiObserver;
import com.hzkans.crm.modules.wxapi.utils.MessageUtils;
import com.hzkans.crm.modules.wxapi.utils.WechatUtils;
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
import java.util.List;
import java.util.Map;

/**
 * @author jc
 * @description 业务整合微信接口
 * @create 2018/12/5
 */
@Controller
@RequestMapping("/wechat")
public class AcceptEventPushController {

    private static Logger logger = LoggerFactory.getLogger(AcceptEventPushController.class);

    @Autowired
    private WechatPlatfromService wechatPlatfromService;

    @Autowired
    private MemberAssociationService memberAssociationService;

    @Autowired
    private WxApiObserver wxApiObserver;


    /**
     * 微信验证服务器地址是否可以访问
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping(value = "/api.do", method = RequestMethod.GET)
    @ResponseBody
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        try {
            // 微信加密签名
            String signature = request.getParameter("signature");
            // 时间戳
            String timestamp = request.getParameter("timestamp");
            // 随机数
            String nonce = request.getParameter("nonce");
            // 随机字符串
            String echostr = request.getParameter("echostr");
            PrintWriter out = response.getWriter();

            // 通过检验signature对请求进行校验，若校成功则原样返回echostr，表示接入成功，否则接入失败
            if (WechatUtils.checkSignature(signature, timestamp, nonce)) {
                out.print(echostr);
            }

            out.close();
        } catch (Exception e) {
            logger.info("verification is fail：", e);
        } finally {
            //最后回复空串
            PrintWriter writer = response.getWriter();
            writer.print("");
            writer.flush();
            writer.close();

        }
    }


    /**
     * 接受微信消息
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping(value = "/api.do", method = RequestMethod.POST)
    @ResponseBody
    public void responseInfo(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        try {
            Map<String, String> requestMap = MessageUtils.parseXml(request);
            String result = wxApiObserver.dealWxMsg(requestMap);
            PrintWriter writer = response.getWriter();
            writer.print(result);
            writer.close();
        } catch (Exception e) {
            logger.info("accept wx fail：", e);
        } finally {
            PrintWriter writer = response.getWriter();
            writer.print("");
            writer.flush();
            writer.close();

        }

    }

    /**
     * 根据code获取用户信息
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/getUserInfo")
    @ResponseBody
    public String getWxUserInfo(HttpServletRequest request, HttpServletResponse response) {
        String code = RequestUtils.getString(request, "code", "code is null");
        String appId = RequestUtils.getString(request, "appid", "app_id is null");

        try {
            //根据appid找到对应的微信信息
            WechatPlatfrom platfrom = new WechatPlatfrom();
            platfrom.setAppId(appId);
            WechatPlatfrom wechatPlatform = wechatPlatfromService.getWechatPlatform(platfrom);

            //调取微信接口
            Map<String, Object> userInfo = wxApiObserver.getUserInfo(code, appId, wechatPlatform.getAppSecret());
            String openId = (String) userInfo.get("openid");
            //判断需不需要验证码
            boolean codeFlg = false; //默认不需要验证码
            Integer currentNum = (Integer) CacheUtils.get(openId+WechatUtils.MAX_NUM);
            if(null == currentNum) {
                currentNum = 0;
            }
            logger.info("currentNum {}",currentNum);
            if(currentNum >= WechatUtils.MAX_NUM) {
                codeFlg = true;
            }
            //判断有没有绑定
            boolean boundFlg = false; //默认没有绑定
            MemberAssociation ass = new MemberAssociation();
            ass.setOpenId(openId);
            List<MemberAssociation> messageAttentionInfo = memberAssociationService.getMessageAttentionInfo(ass);
            String mobile = messageAttentionInfo.get(0).getMobile();
            if(!StringUtils.isEmpty(mobile)) {
                boundFlg = true;
            }
            userInfo.put("code_flg", codeFlg);
            userInfo.put("bound_flg", boundFlg);
            userInfo.put("mobile", mobile);
            logger.info("userInfo {}", JsonUtil.toJson(userInfo));
            //将信息更新到数据库
            MemberAssociation association = new MemberAssociation();
            association.setHeadUrl((String) userInfo.get("headimgurl"));
            association.setNickName((String) userInfo.get("nickname"));
            association.setSex((Integer) userInfo.get("sex"));
            association.setUnionId((String) userInfo.get("unionid"));
            association.setOpenId(openId);
            memberAssociationService.save(association);
            return ResponseUtils.getSuccessApiResponseStr(userInfo);
        } catch (Exception e) {
            logger.error("getWxUserInfo error", e);
            return ResponseUtils.getFailApiResponseStr(ResponseEnum.S_E_SERVICE_ERROR);
        }

    }

}
