/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.hzkans.crm.modules.wechat.dao;

import com.hzkans.crm.common.persistence.CrudDao;
import com.hzkans.crm.common.persistence.annotation.MyBatisDao;
import com.hzkans.crm.modules.wechat.entity.WechatMaterial;

/**
 * 微信公众号素材库DAO接口
 * @author dtm
 * @version 2018-12-04
 */
@MyBatisDao
public interface WechatMaterialDao extends CrudDao<WechatMaterial> {
	
}