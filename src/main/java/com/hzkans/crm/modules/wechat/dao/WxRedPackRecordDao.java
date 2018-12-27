/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.hzkans.crm.modules.wechat.dao;

import com.hzkans.crm.common.persistence.CrudDao;
import com.hzkans.crm.common.persistence.annotation.MyBatisDao;
import com.hzkans.crm.modules.wechat.entity.WxRedPackRecord;

/**
 * 红包记录DAO接口
 * @author jc
 * @version 2018-12-26
 */
@MyBatisDao
public interface WxRedPackRecordDao extends CrudDao<WxRedPackRecord> {
	
}