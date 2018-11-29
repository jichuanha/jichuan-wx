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
    WechatPlatfromDO selectWechatPlatformById(Integer id) throws Exception;

    int insertWechatPlatform(WechatPlatfromDO wechatPlatfromDO) throws Exception;

    void updateWechatPlatform(WechatPlatfromDO wechatPlatfromDO) throws Exception;

    void deleteWechatPlatform(Integer id) throws Exception;

    List<WechatPlatfromDO> selectAllWechatPlatform() throws Exception;
}
