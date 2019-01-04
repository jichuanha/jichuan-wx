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


    /**
     * 获取用户信息(根据code)
     * @param code
     * @param appId
     * @param appSecret
     * @return
     */
    Map<String, Object> getUserInfo(String code, String appId, String appSecret) throws Exception;

    /**
     * 微信发送红包
     * @param objectMap
     * @return
     * @throws Exception
     */
    String sendWxRedPack(Map<String, Object> objectMap) throws Exception;

    /**
     * 企业付款到余额
     * @param objectMap
     * @return
     * @throws Exception
     */
    String companyPay(Map<String, Object> objectMap) throws Exception;

    /**
     * 查询企业付款结果
     * @param objectMap
     * @return
     * @throws Exception
     */
    String queryConpanyPay(Map<String, Object> objectMap) throws Exception;

}
