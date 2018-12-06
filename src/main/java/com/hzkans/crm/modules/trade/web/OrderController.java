
package com.hzkans.crm.modules.trade.web;

import com.hzkans.crm.common.persistence.PagePara;
import com.hzkans.crm.common.service.ServiceException;
import com.hzkans.crm.common.utils.*;
import com.hzkans.crm.common.web.BaseController;
import com.hzkans.crm.modules.trade.constants.JoinActivityStatusEnum;
import com.hzkans.crm.modules.trade.constants.PageTypeEnum;
import com.hzkans.crm.modules.trade.entity.JoinActivity;
import com.hzkans.crm.modules.trade.service.JoinActivityService;
import com.hzkans.crm.modules.trade.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

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


	/**
	 * 活动管理中的订单列表和订单管理
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/orderListDate")
	@ResponseBody
	public String getOrderList(HttpServletRequest request, HttpServletResponse response) {
		//必传参数
		Integer actType = RequestUtils.getInt(request, "act_type", "act_type is null");
		Integer currentPage = RequestUtils.getInt(request, "current_page", "");
		Integer pageSize = RequestUtils.getInt(request, "page_size", "");
		//页面类型 1:订单列表  2:订单审核
		Integer pageType = RequestUtils.getInt(request, "page_type", "");
		//这个字段是
		Integer wechatId = RequestUtils.getInt(request, "wechat_id", "");

		//非必传参数
		String actName = RequestUtils.getString(request, "act_name");
		Integer actStatus = RequestUtils.getInt(request, "act_status", "");
		String startData = RequestUtils.getString(request, "start_data");
		String endData = RequestUtils.getString(request, "end_data");
		Integer platformType = RequestUtils.getInt(request, "platform_type", "");
		Integer shopNo = RequestUtils.getInt(request, "shop_no", "");
		String orderSn = RequestUtils.getString(request, "order_sn");
		//返利类型
		Integer rebateType = RequestUtils.getInt(request, "rebate_type", "");
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
			joinActivity.setRebateType(rebateType);
			//如果是订单审核页面,只需要查询为审核状态的订单
			if(PageTypeEnum.ORDER_AUDIT.getCode().equals(pageType)) {
				joinActivity.setStatus(JoinActivityStatusEnum.UN_AUDIT.getCode());
			}

			if(!StringUtils.isEmpty(startData)) {
                joinActivity.setStartDate(DateUtil.parse(startData, DateUtil.NORMAL_DATETIME_PATTERN));
            }
			if(!StringUtils.isEmpty(endData)) {
                joinActivity.setEndDate(DateUtil.parse(endData, DateUtil.NORMAL_DATETIME_PATTERN));
            }
			joinActivity.setPlatformType(platformType);
			joinActivity.setShopNo(shopNo);
			joinActivity.setOrderSn(orderSn);
			pagePara.setData(joinActivity);
			pagePara.setCurrentPage((currentPage-1)*pageSize);
			pagePara.setPageSize(pageSize);

			PagePara<JoinActivity> page = joinActivityService.listJoinActivityPage(pagePara, wechatId);
			return ResponseUtils.getSuccessApiResponseStr(page);
		} catch (ServiceException e) {
			logger.error("getOrderList error",e);
			return ResponseUtils.getFailApiResponseStr(e.getCode(), e.getServiceMessage());
		}
	}


	/**
	 * 订单审核
	 * @param request
	 * @param response
	 */
	@RequestMapping("/orderAudit")
	@ResponseBody
	public String orderAudit(HttpServletRequest request, HttpServletResponse response) {
		/*Long memberId = Long.parseLong(UserUtils.getUser().getNo());
		logger.info("memberId {}", memberId);*/
		Long memberId = 17L;
		logger.info("memberId {}", memberId);
		//必传参数 ids:格式  1,2,3
		String ids = RequestUtils.getString(request, "id", "id is null");
		Integer status = RequestUtils.getInt(request, "status", "");
		//非必传参数
		String message = RequestUtils.getString(request, "message");
		try {
			String[] split = ids.split(",");
			List<String> strings = Arrays.asList(split);
			JoinActivity joinActivity = new JoinActivity();
			joinActivity.setStatus(status);
			joinActivity.setIds(strings);
			joinActivity.setMessage(message);
			joinActivity.setUpdateDate(new Date());
			joinActivityService.auditOrder(joinActivity);
			return ResponseUtils.getSuccessApiResponseStr(true);
		} catch (ServiceException e) {
			logger.error("orderAudit error",e);
			return ResponseUtils.getFailApiResponseStr(e.getCode() ,e.getServiceMessage());
		}
	}


	/**
	 * 获取参加活动订单详情
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/orderDetail")
	@ResponseBody
	public String orderDetail(HttpServletRequest request, HttpServletResponse response) {
        Integer id = RequestUtils.getInt(request, "id", "id is null", "");
        Integer wechatId = RequestUtils.getInt(request, "wechat_id",
				"wechat_id is null", "");

		try {
			Map<String, Object> orderDetail = joinActivityService.getOrderDetail(id, wechatId);
			return ResponseUtils.getSuccessApiResponseStr(orderDetail);
		} catch (ServiceException e) {
			logger.error("orderDetail error",e);
			return ResponseUtils.getFailApiResponseStr(e.getCode(), e.getServiceMessage());
		}
	}
}