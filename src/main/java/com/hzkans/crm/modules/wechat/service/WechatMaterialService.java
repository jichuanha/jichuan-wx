/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.hzkans.crm.modules.wechat.service;

import java.util.List;

import com.hzkans.crm.common.constant.ResponseEnum;
import com.hzkans.crm.common.persistence.Page;
import com.hzkans.crm.common.service.CrudService;
import com.hzkans.crm.common.service.ServiceException;
import com.hzkans.crm.modules.trade.utils.TradeUtil;
import com.hzkans.crm.modules.wechat.dao.WechatReplyKeywordDao;
import com.hzkans.crm.modules.wechat.entity.WechatMaterial;
import com.hzkans.crm.modules.wechat.entity.WechatReplyKeyword;
import com.hzkans.crm.modules.wechat.entity.WechatReplyNew;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hzkans.crm.modules.wechat.dao.WechatMaterialDao;

/**
 * 微信公众号素材库Service
 * @author dtm
 * @version 2018-12-04
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class WechatMaterialService extends CrudService<WechatMaterialDao, WechatMaterial> {

	@Autowired
	private WechatMaterialDao wechatMaterialDao;

	@Autowired
	private WechatReplyKeywordDao wechatReplyKeywordDao;

	public WechatMaterial get(String id) {
		return super.get(id);
	}

	public List<WechatMaterial> findList(WechatMaterial material) {
		return super.findList(material);
	}

	public Page<WechatMaterial> findPage(Page<WechatMaterial> page, WechatMaterial material) {
		return super.findPage(page, material);
	}


	public String saveWechatMaterial(WechatMaterial material) {
		try {
			wechatMaterialDao.insert(material);
			return material.getId();
		} catch (Exception e) {
			logger.error("saveWechatMaterial error",e);
			throw new ServiceException(ResponseEnum.DATEBASE_SAVE_ERROR);
		}
	}

	@Transactional(readOnly = false)
	public void delete(WechatMaterial material) {
		super.delete(material);
	}


	public List<WechatMaterial> getMatetialByRuleType(WechatReplyNew wechatReplyNew) {
		TradeUtil.isAllNull(wechatReplyNew);
		List<WechatMaterial> wechatMaFromWhere = null;
		try {
			wechatMaFromWhere = wechatMaterialDao.getWechatMaFromWhere(wechatReplyNew);
		} catch (Exception e) {
			logger.error("getMatetialByRuleType error",e);
			throw new ServiceException(ResponseEnum.DATEBASE_QUERY_ERROR);
		}
		return wechatMaFromWhere;
	}

}