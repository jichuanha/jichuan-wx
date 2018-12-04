
package com.hzkans.crm.modules.trade.service;


import com.hzkans.crm.common.constant.ResponseEnum;
import com.hzkans.crm.common.service.CrudService;
import com.hzkans.crm.common.service.ServiceException;
import com.hzkans.crm.modules.trade.dao.OrderDao;
import com.hzkans.crm.modules.trade.entity.Order;
import com.hzkans.crm.modules.trade.utils.TradeUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 订单主表Service
 * @author lizg
 * @version 2018-11-27
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class OrderService extends CrudService<OrderDao, Order> {

	/**
	 * 查询单个订单(通用查)
	 * @param order
	 * @return
	 * @throws ServiceException
	 */
	public Order getOrder(Order order) throws ServiceException {
		TradeUtil.isAllNull(order);
		Order order1 = null;
		try {
			order1 = get(order);
		} catch (Exception e) {
			logger.error("getPlatformShop service error",e);
			throw new ServiceException(ResponseEnum.DATEBASE_QUERY_ERROR);
		}
		return order1;
	}

	/**
	 * 更新订单的状态(通用改)
	 * @param order
	 * @throws ServiceException
	 */
	public void updateOrder(Order order) throws ServiceException{
		TradeUtil.isAllNull(order);
		try {
			update(order);
		} catch (Exception e) {
			logger.error("updateOrder error",e);
			throw new ServiceException(ResponseEnum.B_E_MODIFY_ERROR);
		}
	}

}