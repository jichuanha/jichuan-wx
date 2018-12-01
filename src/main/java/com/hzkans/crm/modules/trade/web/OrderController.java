
package com.hzkans.crm.modules.trade.web;

import com.hzkans.crm.common.persistence.Page;
import com.hzkans.crm.common.utils.StringUtils;
import com.hzkans.crm.common.web.BaseController;
import com.hzkans.crm.modules.trade.entity.Order;
import com.hzkans.crm.modules.trade.service.OrderService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 订单主表Controller
 * @author lizg
 * @version 2018-11-27
 */
@Controller
@RequestMapping(value = "${adminPath}/trade/order")
public class OrderController extends BaseController {

	@Autowired
	private OrderService orderService;

	@RequestMapping(value = "/link_order_list")
	public String gotoSelectAll() {
		return "modules/ordermanage/orderList";
	}

	@RequestMapping(value = "/link_index")
	public String gotoIndex() {
		return "modules/ordermanage/orderIndex";
	}

	@ModelAttribute
	public Order get(@RequestParam(required=false) String id) {
		Order entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = orderService.get(id);
		}
		if (entity == null){
			entity = new Order();
		}
		return entity;
	}
	
	@RequiresPermissions("trade:tOrder:view")
	@RequestMapping(value = {"list", ""})
	public String list(Order tOrder, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Order> page = orderService.findPage(new Page<Order>(request, response), tOrder);
		model.addAttribute("page", page);
		return "modules/trade/orderList";
	}

	@RequiresPermissions("trade:tOrder:view")
	@RequestMapping(value = "form")
	public String form(Order tOrder, Model model) {
		model.addAttribute("tOrder", tOrder);
		return "modules/trade/orderForm";




	}


}