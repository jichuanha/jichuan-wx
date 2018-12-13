/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.hzkans.crm.modules.wechat.dao;

import com.hzkans.crm.common.persistence.CrudDao;
import com.hzkans.crm.common.persistence.annotation.MyBatisDao;

import com.hzkans.crm.modules.wechat.entity.WechatPlatfromDO;

import java.util.List;

/**
 * 微信公众号DAO接口
 * @author dtm
 * @version 2018-11-26
 */
@MyBatisDao
public interface WechatPlatfromDAO extends CrudDao<WechatPlatfromDO> {
    WechatPlatfromDO getWechatPlatformById(Long id);

    int insertWechatPlatform(WechatPlatfromDO wechatPlatfromDO);

    void updateWechatPlatform(WechatPlatfromDO wechatPlatfromDO);

    void removeWechatPlatform(Long id);

    List<WechatPlatfromDO> getWechatPlatforms(WechatPlatfromDO wechatPlatfromDO);

}