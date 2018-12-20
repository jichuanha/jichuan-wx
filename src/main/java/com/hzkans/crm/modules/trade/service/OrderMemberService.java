package com.hzkans.crm.modules.trade.service;

import com.hzkans.crm.common.constant.ResponseEnum;
import com.hzkans.crm.common.service.CrudService;
import com.hzkans.crm.common.service.ServiceException;
import com.hzkans.crm.modules.trade.dao.OrderMemberDao;
import com.hzkans.crm.modules.trade.entity.OrderMember;
import com.hzkans.crm.modules.trade.utils.TradeUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 会员信息表Service
 * @author jc
 * @version 2018-11-27
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class OrderMemberService extends CrudService<OrderMemberDao, OrderMember> {

	/**
	 * 查询单个店铺信息(通用查)
	 * @param orderMember
	 * @return
	 * @throws ServiceException
	 */
	public OrderMember getOrderMember(OrderMember orderMember) throws ServiceException{
		TradeUtil.isAllNull(orderMember);
		OrderMember member = null;
		try {
			member = get(orderMember);
		} catch (Exception e) {
			logger.error("getPlatformShop service error",e);
			throw new ServiceException(ResponseEnum.DATEBASE_QUERY_ERROR);
		}
		return member;
	}
	
}