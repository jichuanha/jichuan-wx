/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.hzkans.crm.modules.wechat.dao;

import com.hzkans.crm.common.persistence.CrudDao;
import com.hzkans.crm.common.persistence.annotation.MyBatisDao;
import com.hzkans.crm.modules.wechat.entity.WechatReply;

import java.util.Map;

/**
 * 微信公众号自动回复DAO接口
 * @author dtm
 * @version 2018-12-05
 */
@MyBatisDao
public interface WechatReplyDao extends CrudDao<WechatReply> {
    WechatReply getReplyByKeywords(Map map);

    void saveReply(WechatReply wechatReply);

    void updateReply(WechatReply wechatReply);
}