package com.hzkans.crm.modules.wechat.service;

import com.hzkans.crm.common.service.ServiceException;
import com.hzkans.crm.modules.wechat.entity.WechatPlatfrom;

import java.util.List;

/**
 * Created with IDEA
 * author:dengtm
 * Date:2018/11/27
 * Time:17:47
 */
public interface WechatPlatfromService {

    WechatPlatfrom getWechatPlatformById(Long id) throws Exception;

    void addWechatPlatform(WechatPlatfrom wechatPlatfrom) throws Exception;

    void updateWechatPlatform(WechatPlatfrom wechatPlatfrom) throws Exception;

    void removeWechatPlatform(Long id) throws Exception;

    List<WechatPlatfrom> listWechatPlatform(WechatPlatfrom wechatPlatfrom) throws Exception;

    WechatPlatfrom getWechatPlatform(WechatPlatfrom wechatPlatfrom) throws ServiceException;

}
