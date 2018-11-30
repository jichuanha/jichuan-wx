/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.hzkans.crm.modules.activity.web;

import com.hzkans.crm.common.persistence.Page;
import com.hzkans.crm.common.utils.DateUtil;
import com.hzkans.crm.common.utils.PriceUtil;
import com.hzkans.crm.common.utils.RequestUtils;
import com.hzkans.crm.common.utils.ResponseUtils;
import com.hzkans.crm.common.web.BaseController;
import com.hzkans.crm.modules.activity.constants.ActivityStatusEnum;
import com.hzkans.crm.modules.activity.constants.ActivityStatusTypeEnum;
import com.hzkans.crm.modules.activity.entity.Activity;
import com.hzkans.crm.modules.activity.entity.PlatformShop;
import com.hzkans.crm.modules.activity.service.ActivityService;
import com.hzkans.crm.modules.activity.service.PlatformShopService;
import com.hzkans.crm.modules.sys.entity.User;
import com.hzkans.crm.modules.sys.utils.UserUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

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

	@Autowired
	private PlatformShopService platformShopService;

	/**
	 * 创建活动
	 *
	 * @return
	 */
	@RequestMapping("/activity-new")
	public String saveActivity(){
		return "modules/activity/activity-new";
	}

	/**
	 * 活动列表
	 *
	 * @return
	 */
	@RequestMapping("/activity-list")
	public String activityList(){
		return "modules/activity/activity-list";
	}

	/**
	 * 活动详情
	 *
	 * @return
	 */
	@RequestMapping("/activity-detail")
	public String deleteActivity(){
		return "modules/activity/activity-detail";
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
		Integer activityType = RequestUtils.getInt(request,"activity_type","");
		String activeDate = RequestUtils.getString(request, false, "active_date", "");
		String inactiveDate = RequestUtils.getString(request, false, "inactive_date", "");
		String orderActiveDate = RequestUtils.getString(request, false, "order_active_date", "");
		String orderInactiveDate = RequestUtils.getString(request, false, "order_inactive_date", "");
		Integer isFollow = RequestUtils.getInt(request,"is_follow","");
		Integer rebateType = RequestUtils.getInt(request,"rebate_type","");
		Integer rebateChannel = RequestUtils.getInt(request,"rebate_channel","");
		Long perAmount = RequestUtils.getLong(request,"per_amount","");
		Integer maxOrderLimit = RequestUtils.getInt(request,"max_order_limit","");
		Long totalAmount = RequestUtils.getLong(request,"total_amount","");
        Integer isAudit = RequestUtils.getInt(request,"is_audit","");
		String shopName = RequestUtils.getString(request, false, "shop_name", "");
		String shopNo = RequestUtils.getString(request, false, "shop_no", "");
		String templateLink = RequestUtils.getString(request, false, "template_link", "");
		Activity activity = new Activity();
		activity.setName(name);
		activity.setDelFlag("0");
		List<Activity> activityList;

		//必填不能为空
		if (null == name || null == activityType || null == isFollow || null == rebateChannel
				|| null == rebateType || null == perAmount || null == isAudit){
			return ResponseUtils.getFailApiResponseStr(100,"有必填选项未填");
		}
		//不能添加已存在的活动
		try {
			activityList = activityService.findList(activity);
		} catch (Exception e) {
			logger.error("findList is error",e);
			return ResponseUtils.getFailApiResponseStr(100,e.getMessage());
		}
		if (CollectionUtils.isNotEmpty(activityList)){
			return ResponseUtils.getFailApiResponseStr(100,"活动已存在");
        }

        //添加活动
		try {
			//获取user
			User user = UserUtils.getUser();
			activity.setActivityType(activityType);
			activity.setActiveDate(DateUtil.parse(activeDate, DateUtil.NORMAL_DATETIME_PATTERN));
			activity.setInactiveDate(DateUtil.parse(inactiveDate, DateUtil.NORMAL_DATETIME_PATTERN));
			activity.setOrderActiveDate(DateUtil.parse(orderActiveDate, DateUtil.NORMAL_DATETIME_PATTERN));
			activity.setOrderInactiveDate(DateUtil.parse(orderInactiveDate, DateUtil.NORMAL_DATETIME_PATTERN));
			activity.setIsFollow(isFollow);
			activity.setRebateType(rebateType);
			activity.setRebateChannel(rebateChannel);
			activity.setPerAmount(PriceUtil.parseYuan2Fen(perAmount * 1.0));
			activity.setPerAmountStr(PriceUtil.parseFen2YuanStr(perAmount));
			activity.setMaxOrderLimit(maxOrderLimit);
			if (null != totalAmount){
				activity.setTotalAmount(PriceUtil.parseYuan2Fen(totalAmount * 1.0));
			}
			activity.setTotalAmountStr(PriceUtil.parseFen2YuanStr(totalAmount));
			activity.setIsAudit(isAudit);
			activity.setShopName(shopName);
			activity.setShopNo(shopNo);
			activity.setTemplateLink(templateLink);
			activity.setStatus(0);
			activity.setCreateBy(user.getCreateBy());
			activity.setUpdateBy(user.getUpdateBy());
			activityService.save(activity);
			return ResponseUtils.getSuccessApiResponseStr(true);
		} catch (Exception e) {
			logger.error("save activity is error",e);
			return ResponseUtils.getFailApiResponseStr(100,"创建活动失败");
		}
	}

	/**
	 * 获取活动列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/activityList")
	@ResponseBody
	public String activityList(HttpServletRequest request) {

		Integer start = RequestUtils.getInt(request, "current_page", true, "", "");
		Integer count = RequestUtils.getInt(request, "page_size", true, "", "");
		String name = RequestUtils.getString(request, true, "name", "");
		String shopNo = RequestUtils.getString(request, true, "shop_no", "");
		Integer status = RequestUtils.getInt(request,"status","");
		Integer activityType = RequestUtils.getInt(request,"activity_type","");
		String startDate = RequestUtils.getString(request, true,"start_date", "");
		String endDate = RequestUtils.getString(request,true, "end_date", "");
		if (start == null || start == 0) {
			start = 1;
		}
		if (count == null || count == 0) {
			count = 20;
		}
		try {
			Page activityPage = new Page<Activity>();
			activityPage.setPageNo(start);
			activityPage.setPageSize(count);
			Activity activity = new Activity();
			activity.setDelFlag("0");
			activity.setStatus(status);
			activity.setName(name);
			activity.setShopNo(shopNo);
			activity.setActivityType(activityType);

			//搜索开始时间和结束时间非空判断
			if (startDate != null) {
				activity.setActiveDate(DateUtil.parse(startDate, DateUtil.NORMAL_DATETIME_PATTERN));
			}
			if (endDate != null) {
				activity.setInactiveDate(DateUtil.parse(endDate, DateUtil.NORMAL_DATETIME_PATTERN));
			}
			Page<Activity> page = activityService.findPage(activityPage,activity);
			if (null != page){
				List<Activity> activityList = page.getList();
				if (CollectionUtils.isNotEmpty(activityList)){
					for (Activity activity1 : activityList){
						//将金额分转化为元
						activity1.setPerAmountStr(PriceUtil.parseFen2YuanStr(activity1.getPerAmount()));
						if (null != activity1.getTotalAmount()){
							activity1.setTotalAmountStr(PriceUtil.parseFen2YuanStr(activity1.getTotalAmount()));
						}
					}
				}
			}
			return ResponseUtils.getSuccessApiResponseStr(page);
		} catch (Exception e) {
			logger.error("findPage is error",e);
			return ResponseUtils.getFailApiResponseStr(100,"获取活动列表失败");
		}
	}

	/**
	 * 获取活动详情
	 * @param request
	 * @return
	 */
	@RequestMapping("/activityDetail")
	@ResponseBody
	public String activityDetail(HttpServletRequest request) {

		String id = RequestUtils.getString(request,false,"id","");
		try {
			Activity activity = activityService.get(id);
			//将金额分转化为元
			if (null != activity.getTotalAmount()){
				activity.setTotalAmountStr(PriceUtil.parseFen2YuanStr(activity.getTotalAmount()));
			}
			activity.setPerAmountStr(PriceUtil.parseFen2YuanStr(activity.getPerAmount()));
			return ResponseUtils.getSuccessApiResponseStr(activity);
		} catch (Exception e) {
			logger.error("get activity detail is error",e);
			return ResponseUtils.getFailApiResponseStr(100,"获取活动详情失败");
		}
	}

	/**
	 * 删除活动
	 * @param request
	 * @return
	 */
	@RequestMapping("/deleteActivity")
	@ResponseBody
	public String deleteActivity(HttpServletRequest request) {

		String id = RequestUtils.getString(request,false,"id","");
		try {
			Activity activity = new Activity();
			activity.setId(id);
			activityService.delete(activity);
			return ResponseUtils.getSuccessApiResponseStr(true);
		} catch (Exception e) {
			logger.error("delete activity is error",e);
			return ResponseUtils.getFailApiResponseStr(100,"删除活动失败");
		}
	}

	/**
	 * 获取平台店铺列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/platformShopList")
	@ResponseBody
	public String shopList(HttpServletRequest request) {
		try {
			PlatformShop platformShop = new PlatformShop();
			List<PlatformShop> platformShopList = platformShopService.findList(platformShop);

			Set<Integer> set = new HashSet();
			List<PlatformShop> platformShopDTOS;
			Map<String,Object> map = new HashMap<>();
			if (CollectionUtils.isNotEmpty(platformShopList)){
				for (PlatformShop platformShop1 : platformShopList){
					Integer platform = platformShop1.getPlatform();
					set.add(platform);
				}
				Iterator<Integer> iterator = set.iterator();
				while (iterator.hasNext()) {
					platformShopDTOS = new ArrayList<>();
					Integer platform = iterator.next();
					for (PlatformShop platformShop1 : platformShopList){
						if (platform.equals(platformShop1.getPlatform())){
							platformShopDTOS.add(platformShop1);
						}
					}
					map.put("platform" + platform,platformShopDTOS);
				}
			}
			return ResponseUtils.getSuccessApiResponseStr(map);
		} catch (Exception e) {
			logger.error("findList is error",e);
			return ResponseUtils.getFailApiResponseStr(100,"获取平台店铺失败");
		}
	}

	/**
	 * 暂停、继续、取消活动
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateStatus")
	@ResponseBody
	public String updateStatus(HttpServletRequest request) {
		try {
			String id = RequestUtils.getString(request,false,"id","");
			Integer type = RequestUtils.getInt(request,"type","");
			Activity activity = new Activity();
			activity.setId(id);
			if (type.equals(ActivityStatusTypeEnum.PAUSE.getCode())){
				activity.setStatus(ActivityStatusEnum.PAUSE.getCode());
			}else if (type.equals(ActivityStatusTypeEnum.GO_ON.getCode())){
				activity.setStatus(ActivityStatusEnum.ACTIVING.getCode());
			}else {
				activity.setStatus(ActivityStatusEnum.ENDED.getCode());
			}
			activityService.update(activity);
			return ResponseUtils.getSuccessApiResponseStr(true);
		} catch (Exception e) {
			logger.error("update status is error",e);
			return ResponseUtils.getFailApiResponseStr(100,"更改状态失败");
		}
	}
}