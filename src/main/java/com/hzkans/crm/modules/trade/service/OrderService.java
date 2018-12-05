
package com.hzkans.crm.modules.trade.service;


import com.hzkans.crm.common.constant.ResponseEnum;
import com.hzkans.crm.common.persistence.PagePara;
import com.hzkans.crm.common.service.CrudService;
import com.hzkans.crm.common.service.ServiceException;
import com.hzkans.crm.common.utils.PriceUtil;
import com.hzkans.crm.modules.activity.entity.PlatformShop;
import com.hzkans.crm.modules.activity.service.PlatformShopService;
import com.hzkans.crm.modules.trade.dao.OrderDao;
import com.hzkans.crm.modules.trade.entity.Order;
import com.hzkans.crm.modules.trade.utils.TradeUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 订单主表Service
 * @author lizg
 * @version 2018-11-27
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class OrderService extends CrudService<OrderDao, Order> {

	@Autowired
	private OrderDao orderDao;
	@Autowired
	private PlatformShopService platformShopService;

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

	public PagePara<Order> getAllOrder(PagePara<Order> pagePara) {
		TradeUtil.isAllNull(pagePara);
		PagePara<Order> para = new PagePara<>();
		try {
			List<Order> orderList = orderDao.listOrderLimit(pagePara);
			if(CollectionUtils.isNotEmpty(orderList)) {
				for (Order order : orderList) {
					//因为导入的订单都是已付款订单
					order.setOrderStatus("已付款");
					PlatformShop platformShop = new PlatformShop();
					platformShop.setShop(Integer.parseInt(order.getShopNo()));
					platformShop.setPlatform(order.getPlatformType());
					PlatformShop shop = platformShopService.getPlatformShop(platformShop);
					order.setPlatformTypeStr(shop.getPlatformName());
					order.setShopNoStr(shop.getShopName());
					order.setPayAmountStr(PriceUtil.parseFen2YuanStr(order.getPayAmount()));
				}
			}

			int count = orderDao.listOrderLimitCount(pagePara);
			para.setList(orderList);
			para.setCount(count);
		} catch (Exception e) {
			logger.error("getAllOrder error",e);
			throw new ServiceException(ResponseEnum.DATEBASE_QUERY_ERROR);
		}
		return para;
	}

}