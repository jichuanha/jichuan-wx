package com.hzkans.crm.modules.activity.entity;

import com.hzkans.crm.common.persistence.DataEntity;


/**
 * 平台店铺Entity
 * @author wsh
 * @version 2018-11-29
 */
public class PlatformShop extends DataEntity<PlatformShop> {

	private static final long serialVersionUID = 1L;

	private Integer platform;
	private String platformName;
	private Integer shop;
	private String  shopName;

	public Integer getPlatform() {
		return platform;
	}

	public void setPlatform(Integer platform) {
		this.platform = platform;
	}

	public String getPlatformName() {
		return platformName;
	}

	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}

	public Integer getShop() {
		return shop;
	}

	public void setShop(Integer shop) {
		this.shop = shop;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
}