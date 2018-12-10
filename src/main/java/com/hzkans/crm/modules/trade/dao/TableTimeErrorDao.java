package com.hzkans.crm.modules.trade.dao;

import com.hzkans.crm.common.persistence.CrudDao;
import com.hzkans.crm.common.persistence.annotation.MyBatisDao;
import com.hzkans.crm.modules.trade.entity.TableTimeError;

/**
 * 错误信息展示DAO接口
 * @author jc
 * @version 2018-12-07
 */
@MyBatisDao
public interface TableTimeErrorDao extends CrudDao<TableTimeError> {
	
}