package com.hzkans.crm.modules.wxapi.constants;

import org.apache.http.client.HttpClient;

/**
 * @author jc
 * @description
 * @create 2018/12/10
 */
public class WechatCofig {

    public static final String EHCACHE = "wechatCache";
    public static final String FIELD_SIGN = "sign";
    public static final String FIELD_SIGN_TYPE = "sign_type";

    public static final String WXPAYSDK_VERSION = "WXPaySDK/3.0.9";
    public static final String USER_AGENT = WXPAYSDK_VERSION +
            " (" + System.getProperty("os.arch") + " " + System.getProperty("os.name") + " " + System.getProperty("os.version") +
            ") Java/" + System.getProperty("java.version") + " HttpClient/" + HttpClient.class.getPackage().getImplementationVersion();

    public static final String HMACSHA256 = "HMAC-SHA256";
    public static final String MD5 = "MD5";

    //key
    public static final String WECHAT_H5_PARTNER_KEY = "ADSZJC64UOiopasiljfiuadklkdj7569";
    //证书路径 E:\cert   /deploy/certs/wechatpay/
    public static final String KEYSTORE_FILE = "E:/cert/";
    //商户号 1504534011
    public static final String MCH_ID="1247278501";

    /** 获取access_token*/
    public static final String GET_ACCESS_TOKEN =
            "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    /** 创建公众号菜单*/
    public static final String CREATE_MENU = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

    /** 上传永久素材(不包括图文)*/
    public static final String UPLOAD_MEDIA =
            "https://api.weixin.qq.com/cgi-bin/material/add_material?access_token=ACCESS_TOKEN&type=TYPE";

    /** 删除永久素材*/
    public static final String DEL_MEDIA =
            "https://api.weixin.qq.com/cgi-bin/material/del_material?access_token=ACCESS_TOKEN";

    /** 获取永久素材*/
    public static final String GET_MEDIA =
            "https://api.weixin.qq.com/cgi-bin/material/get_material?access_token=ACCESS_TOKEN";

    /** 根据前端返回的code换取token,然后拉取用户信息的三个接口*/

    /** 根据code获取access_token*/
    public static final String GET_CODE_ACCESS_TOKEN =
            "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";

    /** 校验access_token的有效性*/
    public static final String CHECK_ACCESS_TOKEN =
            "https://api.weixin.qq.com/sns/auth?access_token=ACCESS_TOKEN&openid=OPENID";


    /** 拉取用户信息*/
    public static final String GET_USER_INFO =
            "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

    /** 发红包接口(需要证书)*/
    public static final String SEND_READ_PACK =
            "https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack";

    /** 企业付款到余额(需要证书)*/
    public static final String COMPANY_PAY =
            "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";

    /** 企业付款结果查询*/
    public static final String QUERY_COMPANY_PAY =
            "https://api.mch.weixin.qq.com/mmpaymkttransfers/gettransferinfo";

}
