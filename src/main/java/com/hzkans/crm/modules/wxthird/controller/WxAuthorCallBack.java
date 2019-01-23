package com.hzkans.crm.modules.wxthird.controller;

import com.hzkans.crm.common.utils.CacheUtils;
import com.hzkans.crm.common.utils.JsonUtil;
import com.hzkans.crm.modules.wxapi.constants.MessageEncrypt;
import com.hzkans.crm.modules.wxthird.constants.CacheEnum;
import com.hzkans.crm.modules.wxthird.constants.WxThirdParame;
import com.hzkans.crm.modules.wxthird.utils.CryptMessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author jc
 * @description 授权第三方平台后的操作
 * @create 2019/1/7
 */
@Controller
@RequestMapping("/wxThird")
public class WxAuthorCallBack {

    private static Logger logger = LoggerFactory.getLogger(WxAuthorCallBack.class);

    /**
     * 接收component_verify_ticket协议
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/callBack")
    @ResponseBody
    public void responseInfo(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String result = "success";
        PrintWriter writer = response.getWriter();
        try {
            MessageEncrypt encryptMessage = CryptMessageUtil.getEncryptMessage(WxThirdParame.token,
                    WxThirdParame.key, WxThirdParame.haiyn_appid, request);
            logger.info(" encryptMessage {}", JsonUtil.toJson(encryptMessage));
            String appId = encryptMessage.getAppId();
            String ticket = encryptMessage.getComponentVerifyTicket();
            CacheUtils.put("wechatCache", CacheEnum.TICKET+appId, ticket);
        } catch (Exception e) {
            logger.error("accept wx fail：", e);
        } finally {
            writer.print(result);
            writer.close();
        }
    }


    @RequestMapping("/webAuth")
    @ResponseBody
    public String webAuthInfo(HttpServletRequest request, HttpServletResponse response) {



        return null;
    }


}
