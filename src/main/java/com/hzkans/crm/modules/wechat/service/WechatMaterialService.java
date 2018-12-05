/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.hzkans.crm.modules.wechat.service;

import java.util.List;

import com.hzkans.crm.common.persistence.Page;
import com.hzkans.crm.common.service.CrudService;
import com.hzkans.crm.modules.wechat.entity.WechatMaterial;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hzkans.crm.modules.wechat.dao.WechatMaterialDao;

/**
 * 微信公众号素材库Service
 * @author dtm
 * @version 2018-12-04
 */
@Service
@Transactional(readOnly = true)
public class WechatMaterialService extends CrudService<WechatMaterialDao, WechatMaterial> {

	public WechatMaterial get(String id) {
		return super.get(id);
	}
	
	public List<WechatMaterial> findList(WechatMaterial material) {
		return super.findList(material);
	}
	
	public Page<WechatMaterial> findPage(Page<WechatMaterial> page, WechatMaterial material) {
		return super.findPage(page, material);
	}
	
	@Transactional(readOnly = false)
	public void save(WechatMaterial material) {
		super.save(material);
	}
	
	@Transactional(readOnly = false)
	public void delete(WechatMaterial material) {
		super.delete(material);
	}
	
}