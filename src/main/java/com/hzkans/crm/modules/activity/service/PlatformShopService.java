/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.hzkans.crm.modules.activity.service;

import com.hzkans.crm.common.service.CrudService;
import com.hzkans.crm.modules.activity.dao.PlatformShopDao;
import com.hzkans.crm.modules.activity.entity.PlatformShop;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 平台店铺Service
 * @author wsh
 * @version 2018-11-26
 */
@Service
@Transactional(readOnly = true)
public class PlatformShopService extends CrudService<PlatformShopDao, PlatformShop> {
	
	public List<PlatformShop> findList(PlatformShop platformShop) {
		return super.findList(platformShop);
	}

}