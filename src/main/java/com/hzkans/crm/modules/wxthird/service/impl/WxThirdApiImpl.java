package com.hzkans.crm.modules.wxthird.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.hzkans.crm.common.utils.JsonUtil;
import com.hzkans.crm.modules.wxapi.utils.WXRedPackUtils;
import com.hzkans.crm.modules.wxthird.constants.WxThirdParame;
import com.hzkans.crm.modules.wxthird.constants.WxThridAuthPara;
import com.hzkans.crm.modules.wxthird.constants.WxUserInfo;
import com.hzkans.crm.modules.wxthird.service.WxThirdApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class WxThirdApiImpl implements WxThirdApiService {
    private static Logger logger = LoggerFactory.getLogger(WxThirdApiImpl.class);

    @Override
    public WxUserInfo WxThridAuth(WxThridAuthPara wxThirdParame) {
        logger.info("wxThirdParame : {}", JsonUtil.toJson(wxThirdParame));
        String newUrl = WxThirdParame.WEB_AUTH_ACCESS_TOKEN.replace("APPID", wxThirdParame.getAppid())
                .replace("CODE", wxThirdParame.getCode())
                .replace("COMPONENT_APPID", wxThirdParame.getComponent_appid())
                .replace("COMPONENT_ACCESS_TOKEN", wxThirdParame.getComponent_access_token());
        WxUserInfo WxUserInfo = null;

        try {
            //获取accessToken
            String getAccessToken = WXRedPackUtils.reqeustOnceNotUserCert(newUrl, "", "", 0, "false");
            logger.info("getAccessToken : {}",getAccessToken);
            if(getAccessToken.contains("errcode")) {
                logger.error("getAccessToken error");
                throw new Exception("getAccessToken error");
            }
            JSONObject jsonObject = JSONObject.parseObject(getAccessToken);
            String accessToken = jsonObject.getString("access_token");
            String openid = jsonObject.getString("openid");
            //根据accessToken获取用户信息
            String userInfoUrl = WxThirdParame.WEB_AUTH_GET_USERINFO.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openid);
            if(userInfoUrl.contains("errcode")){
                logger.error("getAccessToken error", userInfoUrl);
                throw new Exception("getuserInfoUrl error");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
