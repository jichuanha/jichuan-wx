package com.hzkans.crm.modules.wechat.web;

import com.hzkans.crm.modules.wechat.entity.WechatPlatfromDO;
import com.hzkans.crm.modules.wechat.service.WechatPlatfromService;
import com.hzkans.crm.modules.wechat.utils.WechatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author jc
 * @description 接收事件推送
 * @create 2018/12/5
 */
@Controller
@RequestMapping("/wechat")
public class AcceptEventPushController{

    private static Logger logger = LoggerFactory.getLogger(AcceptEventPushController.class);

    @Autowired
    private WechatPlatfromService wechatPlatfromService;


    @RequestMapping(value="/api.do")
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

            WechatPlatfromDO wechatPlatformById = wechatPlatfromService.getWechatPlatformById(12);
            // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
            if (WechatUtil.checkSignature(signature, timestamp, nonce,wechatPlatformById.getToken())) {
                out.print(echostr);
            }

            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
