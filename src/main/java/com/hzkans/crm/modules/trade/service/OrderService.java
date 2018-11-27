
package com.hzkans.crm.modules.trade.service;

import java.util.List;

import com.hzkans.crm.common.persistence.Page;
import com.hzkans.crm.common.service.CrudService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hzkans.crm.modules.trade.entity.Order;
import com.hzkans.crm.modules.trade.dao.OrderDao;

/**
 * 订单主表Service
 * @author lizg
 * @version 2018-11-27
 */
@Service
@Transactional(readOnly = true)
public class OrderService extends CrudService<OrderDao, Order> {

	public Order get(String id) {
		return super.get(id);
	}
	
	public List<Order> findList(Order tOrder) {
		return super.findList(tOrder);
	}
	
	public Page<Order> findPage(Page<Order> page, Order tOrder) {
		return super.findPage(page, tOrder);
	}
	
	@Transactional(readOnly = false)
	public void save(Order tOrder) {
		super.save(tOrder);
	}
	
	@Transactional(readOnly = false)
	public void delete(Order tOrder) {
		super.delete(tOrder);
	}
	
}