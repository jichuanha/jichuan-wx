
package com.hzkans.crm.modules.trade.entity;

import com.hzkans.crm.common.persistence.DataEntity;

import javax.validation.constraints.NotNull;

/**
 * 订单评价表Entity
 * @author lizg
 * @version 2018-11-27
 */
public class OrderAppraise extends DataEntity<OrderAppraise> {
	
	private static final long serialVersionUID = 1L;
	private Long orderSn;		// 订单编号
	private Integer type;		// 评价类型 1-好评  2-差评
	
	public OrderAppraise() {
		super();
	}

	public OrderAppraise(String id){
		super(id);
	}

	@NotNull(message="订单编号不能为空")
	public Long getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(Long orderSn) {
		this.orderSn = orderSn;
	}
	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
}