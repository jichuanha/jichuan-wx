
package com.hzkans.crm.modules.trade.dao;

import com.hzkans.crm.common.persistence.CrudDao;
import com.hzkans.crm.common.persistence.PagePara;
import com.hzkans.crm.common.persistence.annotation.MyBatisDao;
import com.hzkans.crm.modules.trade.entity.Order;

import java.util.List;

/**
 * 订单主表DAO接口
 * @author lizg
 * @version 2018-11-27
 */
@MyBatisDao
public interface OrderDao extends CrudDao<Order> {

    List<Order> listOrderLimit(PagePara<Order> pagePara);

    int listOrderLimitCount(PagePara<Order> pagePara);
	
}