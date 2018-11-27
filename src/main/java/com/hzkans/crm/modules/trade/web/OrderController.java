/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.hzkans.crm.modules.trade.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hzkans.crm.common.config.Global;
import com.hzkans.crm.common.persistence.Page;
import com.hzkans.crm.common.utils.StringUtils;
import com.hzkans.crm.common.web.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.hzkans.crm.modules.trade.entity.Order;
import com.hzkans.crm.modules.trade.service.OrderService;

/**
 * 订单主表Controller
 * @author lizg
 * @version 2018-11-27
 */
@Controller
@RequestMapping(value = "${adminPath}/trade/tOrder")
public class OrderController extends BaseController {

	@Autowired
	private OrderService tOrderService;
	
	@ModelAttribute
	public Order get(@RequestParam(required=false) String id) {
		Order entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tOrderService.get(id);
		}
		if (entity == null){
			entity = new Order();
		}
		return entity;
	}
	
	@RequiresPermissions("trade:tOrder:view")
	@RequestMapping(value = {"list", ""})
	public String list(Order tOrder, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Order> page = tOrderService.findPage(new Page<Order>(request, response), tOrder);
		model.addAttribute("page", page);
		return "modules/trade/orderList";
	}

	@RequiresPermissions("trade:tOrder:view")
	@RequestMapping(value = "form")
	public String form(Order tOrder, Model model) {
		model.addAttribute("tOrder", tOrder);
		return "modules/trade/orderForm";
	}

	@RequiresPermissions("trade:tOrder:edit")
	@RequestMapping(value = "save")
	public String save(Order tOrder, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tOrder)){
			return form(tOrder, model);
		}
		tOrderService.save(tOrder);
		addMessage(redirectAttributes, "保存订单成功");
		return "redirect:"+ Global.getAdminPath()+"/trade/tOrder/?repage";
	}
	
	@RequiresPermissions("trade:tOrder:edit")
	@RequestMapping(value = "delete")
	public String delete(Order tOrder, RedirectAttributes redirectAttributes) {
		tOrderService.delete(tOrder);
		addMessage(redirectAttributes, "删除订单成功");
		return "redirect:"+Global.getAdminPath()+"/trade/tOrder/?repage";
	}

}