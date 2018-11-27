package com.hzkans.crm.modules.trade.dao;


import com.hzkans.crm.common.persistence.CrudDao;
import com.hzkans.crm.common.persistence.annotation.MyBatisDao;
import com.hzkans.crm.modules.trade.entity.OrderMember;

/**
 * 会员信息表DAO接口
 * @author jc
 * @version 2018-11-27
 */
@MyBatisDao
public interface OrderMemberDao extends CrudDao<OrderMember> {
	
}