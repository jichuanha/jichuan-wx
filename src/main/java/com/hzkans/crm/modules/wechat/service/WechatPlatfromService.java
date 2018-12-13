package com.hzkans.crm.modules.wechat.service;

import com.hzkans.crm.modules.wechat.entity.WechatPlatfromDO;

import java.util.List;

/**
 * Created with IDEA
 * author:dengtm
 * Date:2018/11/27
 * Time:17:47
 */
public interface WechatPlatfromService {
    WechatPlatfromDO getWechatPlatformById(Long id) throws Exception;

    void addWechatPlatform(WechatPlatfromDO wechatPlatfromDO) throws Exception;

    void updateWechatPlatform(WechatPlatfromDO wechatPlatfromDO) throws Exception;

    void removeWechatPlatform(Long id) throws Exception;

    List<WechatPlatfromDO> listWechatPlatform(WechatPlatfromDO wechatPlatfromDO) throws Exception;

}
