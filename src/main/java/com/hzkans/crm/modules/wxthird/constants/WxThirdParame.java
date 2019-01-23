package com.hzkans.crm.modules.wxthird.constants;

/**
 * @author jc
 * @description
 * @create 2019/1/7
 */
public class WxThirdParame {

    /** 嗨云微集账号信息*/
    public static final String haiyn_appid = "wxed2d8601cdd49b39";
    public static final String haiyn_appsecret = "abed9070afc728685125d32700ae66c4";

    /** 消息校验Token*/
    public static final String token = "crmtestjklv632356";
    /** 消息加解密Key*/
    public static final String key = "bgBJpr6t2OGo0DuIP9zmPjq2JfoKUPK5rZ9NHVutKjc";








    /** 获取第三方平台component_access_token 用ticket获取*/
    public static final String COMPONENT_ACCESS_TOKEN =
            "https://api.weixin.qq.com/cgi-bin/component/api_component_token";

    /** 第三方平台 通过code换取access_token*/
    public static final String WEB_AUTH_ACCESS_TOKEN =
            "https://api.weixin.qq.com/sns/oauth2/component/access_token?appid=APPID" +
                    "&code=CODE&grant_type=authorization_code&component_appid=COMPONENT_APPID" +
                    "&component_access_token=COMPONENT_ACCESS_TOKEN";

    public static final String WEB_AUTH_GET_USERINFO =
            "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
}
