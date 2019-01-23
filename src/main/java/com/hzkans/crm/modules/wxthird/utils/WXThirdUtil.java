package com.hzkans.crm.modules.wxthird.utils;

import com.alibaba.fastjson.JSONObject;
import com.hzkans.crm.common.utils.CacheUtils;
import com.hzkans.crm.common.utils.StringUtils;
import com.hzkans.crm.modules.wxapi.utils.HttpRequestUtil;
import com.hzkans.crm.modules.wxthird.constants.CacheEnum;
import com.hzkans.crm.modules.wxthird.constants.WxThirdParame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jc
 * @description
 * @create 2019/1/17
 */
public class WXThirdUtil {

    private static Logger logger = LoggerFactory.getLogger(WXThirdUtil.class);

    private String appId;
    private String appSecret;
    private String ticket;


    WXThirdUtil(String appId, String appSecret, String ticket) {
        this.appId = appId;
        this.appSecret = appSecret;
        this.ticket = ticket;
    }

    /**
     * 获取第三方平台component_access_token
     * @return
     */
    public String getAccessToken() throws Exception{

        String accessToken = (String) CacheUtils.get("wechatCache", CacheEnum.TOKEN + appId );
        if(StringUtils.isEmpty(accessToken)) {
            Map<String, String> map = new HashMap<>();
            map.put("component_appid", appId);
            map.put("component_appsecret", appSecret);
            map.put("component_verify_ticket", ticket);
            String result = HttpRequestUtil.HttpsDefaultExecute(HttpRequestUtil.POST_METHOD, WxThirdParame.COMPONENT_ACCESS_TOKEN,
                    map, "", "", 0, "false");
            logger.info("getAccessToken {}", result);
            if(result != null && !result.contains("component_access_token")) {
                throw new Exception("get accessToken error");
            }
            JSONObject js = JSONObject.parseObject(result);
            accessToken = js.getString("component_access_token");
            CacheUtils.put("wechatCache", CacheEnum.TOKEN + appId, accessToken);
        }

        return accessToken;
    }

    public static void main(String[] args) {
        System.out.println(CacheEnum.TOKEN + "waerwer");
    }

}
