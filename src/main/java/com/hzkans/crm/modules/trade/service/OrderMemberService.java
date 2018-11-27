package com.hzkans.crm.modules.trade.service;

import java.util.List;

import com.hzkans.crm.common.persistence.Page;
import com.hzkans.crm.common.service.CrudService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.hzkans.crm.modules.trade.entity.OrderMember;
import com.hzkans.crm.modules.trade.dao.OrderMemberDao;

/**
 * 会员信息表Service
 * @author jc
 * @version 2018-11-27
 */
@Service
@Transactional(readOnly = true)
public class OrderMemberService extends CrudService<OrderMemberDao, OrderMember> {

	public OrderMember get(String id) {
		return super.get(id);
	}
	
	public List<OrderMember> findList(OrderMember orderMember) {
		return super.findList(orderMember);
	}
	
	public Page<OrderMember> findPage(Page<OrderMember> page, OrderMember orderMember) {
		return super.findPage(page, orderMember);
	}
	
	@Transactional(readOnly = false)
	public void save(OrderMember orderMember) {
		super.save(orderMember);
	}
	
	@Transactional(readOnly = false)
	public void delete(OrderMember orderMember) {
		super.delete(orderMember);
	}
	
}