package com.hzkans.crm.modules.wxthird.service;


import com.hzkans.crm.modules.wxthird.constants.WxThridAuthPara;
import com.hzkans.crm.modules.wxthird.constants.WxUserInfo;

public interface WxThirdApiService {

    /**
     * 微信第三方授权
     * @param wxThirdParame
     * @return
     */
    WxUserInfo WxThridAuth(WxThridAuthPara wxThirdParame) ;
}
