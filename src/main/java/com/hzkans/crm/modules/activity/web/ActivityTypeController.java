/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.hzkans.crm.modules.activity.web;

import com.hzkans.crm.common.constant.ResponseEnum;
import com.hzkans.crm.common.utils.DateUtil;
import com.hzkans.crm.common.utils.PriceUtil;
import com.hzkans.crm.common.utils.RequestUtils;
import com.hzkans.crm.common.utils.ResponseUtils;
import com.hzkans.crm.common.web.BaseController;
import com.hzkans.crm.modules.activity.entity.Activity;
import com.hzkans.crm.modules.activity.entity.ActivityType;
import com.hzkans.crm.modules.activity.entity.PlatformShop;
import com.hzkans.crm.modules.activity.service.ActivityTypeService;
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
 * 活动类型Controller
 * @author wsh
 * @version 2018-12-05
 */
@Controller
@RequestMapping(value = "${adminPath}/activity/activityType")
public class ActivityTypeController extends BaseController {

	@Autowired
	private ActivityTypeService activityTypeService;

	/**
	 * 创建活动
	 *
	 * @return
	 */
	@RequestMapping("/activity-ype-new")
	public String saveActivityType(){
		return "modules/activity/activity-type-new";
	}


	/**
	 * 新增活动类型
	 * @param request
	 * @return
	 */
	@RequestMapping("/saveActivityType")
	@ResponseBody
	public String saveActivityType(HttpServletRequest request) {

		String typeName = RequestUtils.getString(request, false, "type_name", "");
		String remarks = RequestUtils.getString(request, true, "remarks", "");

		ActivityType activityType = new ActivityType();
		activityType.setTypeName(typeName);
		List<ActivityType> activityTypeList;

		//不能添加已存在的活动类型
		try {
			activityTypeList = activityTypeService.findList(activityType);
		} catch (Exception e) {
			logger.error("findList is error",e);
			return ResponseUtils.getFailApiResponseStr(ResponseEnum.B_E_FAILED_TO_GET);
		}
		if (CollectionUtils.isNotEmpty(activityTypeList)){
			return ResponseUtils.getFailApiResponseStr(ResponseEnum.B_E_ACTIVITY_TYPE_EXIST);
		}

		//添加活动类型
		try {
			//获取user
			User user = UserUtils.getUser();
			if (null != remarks){
				activityType.setRemarks(remarks);
			}
			activityType.setCreateBy(user.getCreateBy());
			activityType.setUpdateBy(user.getUpdateBy());
			activityTypeService.save(activityType);
			return ResponseUtils.getSuccessApiResponseStr(true);
		} catch (Exception e) {
			logger.error("save activity type is error",e);
			return ResponseUtils.getFailApiResponseStr(ResponseEnum.B_E_FAILED_TO_ADD);
		}
	}

	/**
	 * 获取活动类型列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/activityTypeList")
	@ResponseBody
	public String activityTypeList(HttpServletRequest request) {
		try {
			ActivityType activityType = new ActivityType();
			List<ActivityType> activityTypeList = activityTypeService.findList(activityType);
			return ResponseUtils.getSuccessApiResponseStr(activityTypeList);
		} catch (Exception e) {
			logger.error("findList is error",e);
			return ResponseUtils.getFailApiResponseStr(ResponseEnum.B_E_FAILED_TO_GET);
		}
	}
}