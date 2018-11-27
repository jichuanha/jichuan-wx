/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.hzkans.crm.modules.activity.web;

import com.hzkans.crm.common.utils.DateUtil;
import com.hzkans.crm.common.utils.PriceUtil;
import com.hzkans.crm.common.utils.RequestUtils;
import com.hzkans.crm.common.web.BaseController;
import com.hzkans.crm.modules.activity.entity.Activity;
import com.hzkans.crm.modules.activity.service.ActivityService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 活动管理Controller
 * @author wsh
 * @version 2018-11-26
 */
@Controller
@RequestMapping(value = "${adminPath}/activity/activity")
public class ActivityController extends BaseController {

	@Autowired
	private ActivityService activityService;

	/**
	 * 创建活动
	 *
	 * @return
	 */
	@RequestMapping("/activityForm")
	public String teamMember(){
		return "modules/activity/activityForm";
	}

	/**
	 * 修改活动
	 *
	 * @return
	 */
	@RequestMapping("/activityList")
	public String newAgentState(){
		return "modules/activity/activityList";
	}

	/**
	 * 创建活动
	 * @param request
	 * @return
	 */
	@RequestMapping("/saveActivity")
	@ResponseBody
	public String saveActivity(HttpServletRequest request) {

		String name = RequestUtils.getString(request, false, "name", "");
		String effectiveStartTime = RequestUtils.getString(request, false, "effective_start_time", "");
		String effectiveEndTime = RequestUtils.getString(request, false, "effective_end_time", "");
		String orderAgingStartTime = RequestUtils.getString(request, false, "order_aging_start_time", "");
		String orderAgingEndTime = RequestUtils.getString(request, false, "order_aging_end_time", "");
		String url = RequestUtils.getString(request, false, "url", "");
		Integer attentionType = RequestUtils.getInt(request,"attention_type","");
		Integer rebateType = RequestUtils.getInt(request,"rebate_type","");
		Integer rebateChannel = RequestUtils.getInt(request,"rebate_channel","");
		Long singleAmount = RequestUtils.getLong(request,"single_amount","");
		Integer totalOrder = RequestUtils.getInt(request,"total_order","");
		Long totalRebate = RequestUtils.getLong(request,"total_rebate","");
        Integer isAudit = RequestUtils.getInt(request,"is_audit","");
		String shopName = RequestUtils.getString(request, false, "shop_name", "");
		String shopNo = RequestUtils.getString(request, false, "shop_no", "");
		String templateLink = RequestUtils.getString(request, false, "template_link", "");
        Integer status = RequestUtils.getInt(request,"status","");
		Activity activity = new Activity();
		activity.setName(name);
		activity.setDelFlag("0");
        List<Activity> activityList = activityService.findList(activity);
        if (CollectionUtils.isNotEmpty(activityList)){

        }
		activity.setEffectiveStartTime(DateUtil.parse(effectiveStartTime, DateUtil.NORMAL_DATETIME_PATTERN));
		activity.setEffectiveEndTime(DateUtil.parse(effectiveEndTime, DateUtil.NORMAL_DATETIME_PATTERN));
		activity.setOrderAgingStartTime(DateUtil.parse(orderAgingStartTime, DateUtil.NORMAL_DATETIME_PATTERN));
		activity.setOrderAgingEndTime(DateUtil.parse(orderAgingEndTime, DateUtil.NORMAL_DATETIME_PATTERN));
		activity.setUrl(url);
		activity.setAttentionType(attentionType);
		activity.setRebateType(rebateType);
		activity.setRebateChannel(rebateChannel);
		activity.setSingleAmount(singleAmount);
		activity.setSingleAmountStr(PriceUtil.parseFen2YuanStr(singleAmount));
		activity.setTotalOrder(totalOrder);
        activity.setTotalRebate(totalRebate);
        activity.setTotalRebateStr(PriceUtil.parseFen2YuanStr(totalRebate));
        activity.setIsAudit(isAudit);
        activity.setShopName(shopName);
        activity.setShopNo(shopNo);
        activity.setTemplateLink(templateLink);
        activity.setStatus(status);
		activityService.save(activity);
		return null;
	}

	/**
	 * 修改活动
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateActivity")
	@ResponseBody
	public String updateActivity(HttpServletRequest request) {
		return null;
	}
}