package com.hzkans.crm.modules.wxapi.web;

import com.hzkans.crm.common.constant.ResponseEnum;
import com.hzkans.crm.common.utils.JsonUtil;
import com.hzkans.crm.common.utils.RequestUtils;
import com.hzkans.crm.common.utils.ResponseUtils;
import com.hzkans.crm.modules.wechat.service.WechatRedPackService;
import com.hzkans.crm.modules.wxapi.utils.MobileUtil;
import com.hzkans.crm.modules.wxapi.utils.WXRedPackUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jc
 * @description 测试微信支付(劲猴)
 * @create 2019/1/14
 */
@Controller
@RequestMapping("/pay")
public class WxPayController {
    private static Logger logger = LoggerFactory.getLogger(WxPayController.class);

    @Resource
    private WechatRedPackService wechatRedPackService;

    @RequestMapping("/jspayPage")
    public String wxJsPayPage() {
        return "modules/mobile/wechatPay";
    }

    @RequestMapping("/jspay")
    @ResponseBody
    public String wxJsPay(HttpServletRequest request, HttpServletResponse response) {
        try {
            String ip = WXRedPackUtils.getRemoteIp(request);
            String openId = RequestUtils.getString(request, "open_id");
            logger.info(" openId {}",openId);
            Map<String, String> map = wechatRedPackService.payOrder(ip, openId);
            String returnCode = map.get("return_code");
            String resultCode = map.get("result_code");
            String prepayId = map.get("prepay_id");
            String nonceStr = map.get("nonce_str");
            if(returnCode != null && resultCode != null
                   && "SUCCESS".equals(resultCode) && "SUCCESS".equals(resultCode)) {

            }
            Map<String, String> clientPrepayMap = WXRedPackUtils.getClientPrepayMap(prepayId, nonceStr);
            clientPrepayMap.put("prepay_id", prepayId);
            return ResponseUtils.getSuccessApiResponseStr(clientPrepayMap);
        } catch (Exception e) {
            return ResponseUtils.getFailApiResponseStr(ResponseEnum.S_E_SERVICE_ERROR);
        }
    }


    @RequestMapping("/callback")
    @ResponseBody
    public void wxCallBack(HttpServletRequest request, HttpServletResponse response) {
        //解析XML
        String xml = "";
        try {
            Map<String, String> xmlDataMap = MobileUtil.parseXml(request);
            logger.info("[{}] xmlDataMap:{}", JsonUtil.toJson(xmlDataMap));
            BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
            Map<String, String> map = new HashMap<>();
            map.put("return_code", "SUCCESS");
            map.put("return_msg", "OK");
            xml = WXRedPackUtils.mapToXml(map);
            logger.info(" xml {}",xml);
            out.write(xml.getBytes());
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
