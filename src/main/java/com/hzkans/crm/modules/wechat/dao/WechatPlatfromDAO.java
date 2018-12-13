/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.hzkans.crm.modules.wechat.dao;

import com.hzkans.crm.common.persistence.CrudDao;
import com.hzkans.crm.common.persistence.annotation.MyBatisDao;

import com.hzkans.crm.modules.wechat.entity.WechatPlatfrom;

import java.util.List;

/**
 * 微信公众号DAO接口
 * @author dtm
 * @version 2018-11-26
 */
@MyBatisDao
public interface WechatPlatfromDAO extends CrudDao<WechatPlatfrom> {
    WechatPlatfrom getWechatPlatformById(Long id);

    int insertWechatPlatform(WechatPlatfrom wechatPlatfrom);

    void updateWechatPlatform(WechatPlatfrom wechatPlatfrom);

    void removeWechatPlatform(Long id);

    List<WechatPlatfrom> getWechatPlatforms(WechatPlatfrom wechatPlatfrom);

    WechatPlatfrom selectWechatPlatform(WechatPlatfrom wechatPlatfrom);

}