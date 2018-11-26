
package com.hzkans.crm.modules.trade.dao;


import com.hzkans.crm.common.persistence.CrudDao;
import com.hzkans.crm.common.persistence.annotation.MyBatisDao;
import com.hzkans.crm.modules.trade.entity.TableFlow;

/**
 * 表格流程DAO接口
 * @author chuan
 * @version 2018-11-26
 */
@MyBatisDao
public interface TableFlowDao extends CrudDao<TableFlow> {
	
}