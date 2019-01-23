package com.hzkans.crm.modules.wxthird.utils;

import com.alibaba.fastjson.JSONArray;
import com.hzkans.crm.common.utils.JsonUtil;
import com.hzkans.crm.common.utils.RequestUtils;
import com.hzkans.crm.common.utils.XmlUtil;
import com.hzkans.crm.modules.wxapi.constants.MessageEncrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.Map;

/**
 * @author jc
 * @description
 * @create 2019/1/7
 */
public class CryptMessageUtil {

    private static Logger logger = LoggerFactory.getLogger(CryptMessageUtil.class);
    public static MessageEncrypt getEncryptMessage(String token, String encodingAesKey, String appid,
                                                   HttpServletRequest request) throws Exception{
        // 从request中取得输入流
        String msgSignature = RequestUtils.getString(request, "msg_signature");
        String timestamp = RequestUtils.getString(request, "timestamp");
        String nonce = RequestUtils.getString(request, "nonce");

        InputStream inputStream = request.getInputStream();
        WXBizMsgCrypt pc = new WXBizMsgCrypt(token, encodingAesKey, appid);
        // 第三方收到公众号平台发送的消息
        String result2 = pc.decryptMsg(msgSignature, timestamp, nonce, inputStream);
        System.out.println("result2 ++" + result2);
        Map<String, String> map = XmlUtil.xmlStr2Map(result2);
        return JSONArray.parseObject(JsonUtil.toJson(map), MessageEncrypt.class);

    }
}
