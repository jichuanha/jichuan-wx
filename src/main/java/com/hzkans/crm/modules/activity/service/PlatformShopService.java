/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.hzkans.crm.modules.activity.service;

import com.hzkans.crm.common.constant.ResponseEnum;
import com.hzkans.crm.common.service.CrudService;
import com.hzkans.crm.common.service.ServiceException;
import com.hzkans.crm.modules.activity.dao.PlatformShopDao;
import com.hzkans.crm.modules.activity.entity.Activity;
import com.hzkans.crm.modules.activity.entity.PlatformShop;
import com.hzkans.crm.modules.trade.utils.TradeUtil;
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

	@Override
	public List<PlatformShop> findList(PlatformShop platformShop) {
		return super.findList(platformShop);
	}

	/**
	 * 查询单个店铺信息(通用查)
	 * @param platformShop
	 * @return
	 * @throws ServiceException
	 */
	public PlatformShop getPlatformShop(PlatformShop platformShop) throws ServiceException{
		TradeUtil.isAllNull(platformShop);
		PlatformShop shop = null;
		try {
			shop = get(platformShop);
		} catch (Exception e) {
			logger.error("getPlatformShop service error",e);
			throw new ServiceException(ResponseEnum.DATEBASE_QUERY_ERROR);
		}
		return shop;
	}

	@Override
	@Transactional(readOnly = false)
	public void save(PlatformShop PlatformShop) {
		super.save(PlatformShop);
	}
}