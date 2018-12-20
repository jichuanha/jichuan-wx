package com.hzkans.crm.modules.wxapi.web;

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
import java.util.Map;

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
    private WxApiObserver wxApiObserver;

    /**
     * 微信验证服务器地址是否可以访问
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping(value="/api.do",method = RequestMethod.GET)
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
            logger.info("verification is fail：",e);
        }finally{
            //最后回复空串
            PrintWriter writer = response.getWriter();
            writer.print("");
            writer.flush();
            writer.close();

        }
    }


    /**
     * 接受微信消息
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping(value="/api.do",method = RequestMethod.POST)
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
            logger.info("accept wx fail：",e);
        }finally{
            PrintWriter writer = response.getWriter();
            writer.print("");
            writer.flush();
            writer.close();

        }

    }
}
