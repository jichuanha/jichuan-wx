/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.hzkans.crm.modules.wechat.dao;

import com.hzkans.crm.common.persistence.CrudDao;
import com.hzkans.crm.common.persistence.annotation.MyBatisDao;
import com.hzkans.crm.modules.wechat.entity.WechatReplyNew;

import java.util.List;

/**
 * 微信自动回复规则DAO接口
 * @author dtm
 * @version 2018-12-10
 */
@MyBatisDao
public interface WechatReplyRuleDao extends CrudDao<WechatReplyNew> {
    List<WechatReplyNew> listReply(WechatReplyNew wechatReplyNew);

}