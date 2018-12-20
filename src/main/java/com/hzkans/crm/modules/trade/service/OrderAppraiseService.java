
package com.hzkans.crm.modules.trade.service;

import com.hzkans.crm.common.service.CrudService;
import com.hzkans.crm.modules.trade.dao.OrderAppraiseDao;
import com.hzkans.crm.modules.trade.entity.OrderAppraise;
import org.springframework.stereotype.Service;

/**
 * 订单评价表Service
 * @author lizg
 * @version 2018-11-27
 */
@Service
public class OrderAppraiseService extends CrudService<OrderAppraiseDao, OrderAppraise> {

	@Override
	public OrderAppraise get(String id) {
		return super.get(id);
	}

	@Override
	public void save(OrderAppraise orderAppraise) {
		super.save(orderAppraise);
	}
	
}