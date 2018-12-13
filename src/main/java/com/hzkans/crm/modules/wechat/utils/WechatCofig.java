package com.hzkans.crm.modules.wechat.utils;

/**
 * @author jc
 * @description
 * @create 2018/12/10
 */
public class WechatCofig {

    public static final String EHCACHE = "wechatCache";
    public static final String FIELD_SIGN = "sign";
    public static final String FIELD_SIGN_TYPE = "sign_type";


    public static final String HMACSHA256 = "HMAC-SHA256";
    public static final String MD5 = "MD5";

    public static final String APPID = "wxef5cb80081c4dd13";
    public static final String APPSECRET = "53ae0b6b1cb95774c3165fab695c2ecb";

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





}
