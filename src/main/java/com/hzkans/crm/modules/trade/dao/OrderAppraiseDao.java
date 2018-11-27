
package com.hzkans.crm.modules.trade.dao;

import com.hzkans.crm.common.persistence.CrudDao;
import com.hzkans.crm.common.persistence.annotation.MyBatisDao;
import com.hzkans.crm.modules.trade.entity.OrderAppraise;

/**
 * 订单评价表DAO接口
 * @author lizg
 * @version 2018-11-27
 */
@MyBatisDao
public interface OrderAppraiseDao extends CrudDao<OrderAppraise> {
	
}