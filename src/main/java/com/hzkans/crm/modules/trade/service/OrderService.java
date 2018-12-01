
package com.hzkans.crm.modules.trade.service;

import java.util.List;

import com.hzkans.crm.common.constant.ResponseEnum;
import com.hzkans.crm.common.persistence.Page;
import com.hzkans.crm.common.service.CrudService;
import com.hzkans.crm.common.service.ServiceException;
import com.hzkans.crm.modules.activity.entity.PlatformShop;
import com.hzkans.crm.modules.trade.utils.TradeUtil;
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
@Transactional(rollbackFor = Exception.class)
public class OrderService extends CrudService<OrderDao, Order> {

	/**
	 * 查询单个店铺信息(通用查)
	 * @param order
	 * @return
	 * @throws ServiceException
	 */
	public Order getPlatformShop(Order order) throws ServiceException{
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

}