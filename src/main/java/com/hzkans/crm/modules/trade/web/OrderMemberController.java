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


import com.hzkans.crm.modules.trade.entity.OrderMember;
import com.hzkans.crm.modules.trade.service.OrderMemberService;

/**
 * 会员信息表Controller
 * @author jc
 * @version 2018-11-27
 */
@Controller
@RequestMapping(value = "${adminPath}/trade/orderMember")
public class OrderMemberController extends BaseController {

	@Autowired
	private OrderMemberService orderMemberService;
	
	@ModelAttribute
	public OrderMember get(@RequestParam(required=false) String id) {
		OrderMember entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = orderMemberService.get(id);
		}
		if (entity == null){
			entity = new OrderMember();
		}
		return entity;
	}
	
	@RequiresPermissions("trade:orderMember:view")
	@RequestMapping(value = {"list", ""})
	public String list(OrderMember orderMember, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OrderMember> page = orderMemberService.findPage(new Page<OrderMember>(request, response), orderMember);
		model.addAttribute("page", page);
		return "modules/trade/orderMemberList";
	}

	@RequiresPermissions("trade:orderMember:view")
	@RequestMapping(value = "form")
	public String form(OrderMember orderMember, Model model) {
		model.addAttribute("orderMember", orderMember);
		return "modules/trade/orderMemberForm";
	}

	@RequiresPermissions("trade:orderMember:edit")
	@RequestMapping(value = "save")
	public String save(OrderMember orderMember, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, orderMember)){
			return form(orderMember, model);
		}
		orderMemberService.save(orderMember);
		addMessage(redirectAttributes, "保存会员信息表成功");
		return "redirect:"+ Global.getAdminPath()+"/trade/orderMember/?repage";
	}
	
	@RequiresPermissions("trade:orderMember:edit")
	@RequestMapping(value = "delete")
	public String delete(OrderMember orderMember, RedirectAttributes redirectAttributes) {
		orderMemberService.delete(orderMember);
		addMessage(redirectAttributes, "删除会员信息表成功");
		return "redirect:"+Global.getAdminPath()+"/trade/orderMember/?repage";
	}

}