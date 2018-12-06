/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.hzkans.crm.modules.wechat.utils;

import com.hzkans.crm.common.utils.SpringContextHolder;
import com.hzkans.crm.modules.wechat.dao.WechatPlatfromDAO;
import com.hzkans.crm.modules.wechat.entity.WechatPlatfromDO;

/**
 * 用户工具类
 * @author ThinkGem
 * @version 2013-12-05
 */
public class WechatUtils {

	private static WechatPlatfromDAO wechatPlatfromDAO = SpringContextHolder.getBean(WechatPlatfromDAO.class);


	public static WechatPlatfromDO getWechatPlatform(Integer id){
		if (null != id){
			return wechatPlatfromDAO.getWechatPlatformById(id);
		}else {
			return new WechatPlatfromDO();
		}
	}
	
}
