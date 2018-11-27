
package com.hzkans.crm.modules.trade.service;

import com.hzkans.crm.common.service.CrudService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hzkans.crm.modules.trade.entity.OrderAppraise;
import com.hzkans.crm.modules.trade.dao.OrderAppraiseDao;

/**
 * 订单评价表Service
 * @author lizg
 * @version 2018-11-27
 */
@Service
@Transactional(readOnly = true)
public class OrderAppraiseService extends CrudService<OrderAppraiseDao, OrderAppraise> {

	@Override
	public OrderAppraise get(String id) {
		return super.get(id);
	}

	@Override
	@Transactional(readOnly = false)
	public void save(OrderAppraise orderAppraise) {
		super.save(orderAppraise);
	}
	
}