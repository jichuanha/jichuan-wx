package com.hzkans.crm.modules.wxapi.service;


import java.util.Map;

/**
 * @author jc
 * @description
 * @create 2018/12/17
 */

public interface WxApiObserver{

    /**
     * 处理接受到的微信消息
     * @param requestMap
     * @return
     */
    String dealWxMsg( Map<String, String> requestMap);

}
