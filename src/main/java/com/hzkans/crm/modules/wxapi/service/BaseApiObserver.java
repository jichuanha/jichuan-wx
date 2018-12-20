package com.hzkans.crm.modules.wxapi.service;


import java.util.Map;

/**
 * @author lizg
 * @description
 * @create 2018/12/17
 */
public  interface BaseApiObserver {

    /**
     * 消息处理公用接口
     * @param requestMap
     * @return
     */
    String executeStep (Map<String, String> requestMap);

}
