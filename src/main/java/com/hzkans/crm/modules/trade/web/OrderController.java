
package com.hzkans.crm.modules.trade.web;


import com.google.common.base.Strings;
import com.hzkans.crm.common.persistence.PagePara;
import com.hzkans.crm.common.service.ServiceException;
import com.hzkans.crm.common.utils.DateUtil;
import com.hzkans.crm.common.utils.RequestUtils;
import com.hzkans.crm.common.utils.ResponseUtils;
import com.hzkans.crm.common.web.BaseController;
import com.hzkans.crm.modules.trade.entity.JoinActivity;
import com.hzkans.crm.modules.trade.service.JoinActivityService;
import com.hzkans.crm.modules.trade.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
	private JoinActivityService joinActivityService;


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

	/**
	 * 订单列表
	 * @return
	 */
	@RequestMapping("/order_list")
	public String orderListPage() {
		return "modules/order/order-list";
	}

	/**
	 * 订单详情
	 * @return
	 */
	@RequestMapping("/order_detail")
	public String orderDetailPage() {
		return "modules/order/order-detail";
	}

	/**
	 * 订单审核
	 * @return
	 */
	@RequestMapping("/order_review")
	public String ordeUpdatePage() {
		return "modules/order/order-review";
	}


	@RequestMapping("/orderListDate")
	@ResponseBody
	public String getOrderList(HttpServletRequest request, HttpServletResponse response) {
		//必传参数
		Integer actType = RequestUtils.getInt(request, "act_type", "act_type is null");
		Integer currentPage = RequestUtils.getInt(request, "current_page", "");
		Integer pageSize = RequestUtils.getInt(request, "page_size", "");

		//非必传参数
		String actName = RequestUtils.getString(request, "act_name");
		Integer actStatus = RequestUtils.getInt(request, "act_status", "");
		String startData = RequestUtils.getString(request, "start_data");
		String endData = RequestUtils.getString(request, "end_data");
		Integer platformType = RequestUtils.getInt(request, "platform_type", "");
		Integer shopNo = RequestUtils.getInt(request, "shop_no", "");
		String orderSn = RequestUtils.getString(request, "order_sn");
		//防止程序报错
		if(currentPage == null || pageSize == null) {
			currentPage = 1;
			pageSize = 10;
		}
		if(null == actType) {
			actType = 1;
		}

		try {
			PagePara<JoinActivity> pagePara = new PagePara<>();
			JoinActivity joinActivity = new JoinActivity();
			joinActivity.setActType(actType);
			joinActivity.setActName(actName);
			joinActivity.setActStatus(actStatus);
			if(!Strings.isNullOrEmpty(startData)) {
                joinActivity.setStartDate(DateUtil.parse(startData, DateUtil.NORMAL_DATETIME_PATTERN));
            }
			if(!Strings.isNullOrEmpty(endData)) {
                joinActivity.setStartDate(DateUtil.parse(endData, DateUtil.NORMAL_DATETIME_PATTERN));
            }
			joinActivity.setPlatformType(platformType);
			joinActivity.setShopNo(shopNo);
			joinActivity.setOrderSn(orderSn);
			pagePara.setData(joinActivity);
			pagePara.setCount((currentPage-1)*pageSize);
			pagePara.setPageSize(pageSize);

			PagePara<JoinActivity> page = joinActivityService.listJoinActivityPage(pagePara);
			return ResponseUtils.getSuccessApiResponseStr(page);
		} catch (ServiceException e) {
			logger.error("getOrderList error",e);
			return ResponseUtils.getFailApiResponseStr(e.getCode(), e.getServiceMessage());
		}
	}



}