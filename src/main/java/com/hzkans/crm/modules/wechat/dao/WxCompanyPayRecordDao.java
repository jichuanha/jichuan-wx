/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.hzkans.crm.modules.wechat.dao;

import com.hzkans.crm.common.persistence.CrudDao;
import com.hzkans.crm.common.persistence.annotation.MyBatisDao;
import com.hzkans.crm.modules.wechat.entity.WxCompanyPayRecord;

/**
 * 企业付款DAO接口
 * @author jc
 * @version 2019-01-03
 */
@MyBatisDao
public interface WxCompanyPayRecordDao extends CrudDao<WxCompanyPayRecord> {
	
}