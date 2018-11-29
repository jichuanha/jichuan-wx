package com.hzkans.zj.test;

import com.hzkans.crm.common.utils.JsonUtil;
import com.hzkans.crm.modules.trade.entity.Order;
import com.hzkans.crm.modules.trade.service.OrderService;
import org.junit.Test;


public class OrderTest extends BaseTest{


	@Test
	public void getOrder ()throws Exception{
		OrderService orderService = context.getBean(OrderService.class);
		Order order =  orderService.get("272200608488052227");
		System.out.println("order:{}"+ JsonUtil.toJson(order));



	}
	public static void main(String[] args) {
		
	}
}
