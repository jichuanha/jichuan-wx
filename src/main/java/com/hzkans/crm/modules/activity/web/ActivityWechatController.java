/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.hzkans.crm.modules.activity.web;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.hzkans.crm.common.constant.ResponseEnum;
import com.hzkans.crm.common.utils.RequestUtils;
import com.hzkans.crm.common.utils.ResponseUtils;
import com.hzkans.crm.common.web.BaseController;
import com.hzkans.crm.modules.activity.entity.ActivityWechat;
import com.hzkans.crm.modules.activity.service.ActivityWechatService;
import com.hzkans.crm.modules.sys.entity.User;
import com.hzkans.crm.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 活动公众号关系Controller
 * @author wsh
 * @version 2018-12-06
 */
@Controller
@RequestMapping(value = "${adminPath}/activity/activityWechat")
public class ActivityWechatController extends BaseController {

	@Autowired
	private ActivityWechatService activityWechatService;

	/**
	 * 绑定活动和公众号
	 * @param request
	 * @return
	 */
	@RequestMapping("/saveActivity")
	@ResponseBody
	public String bingActivity(HttpServletRequest request) {

		Long activityId = RequestUtils.getLong(request, "activity_id", false, "activity_id is null", "");
		String id = RequestUtils.getString(request, true, "id", "id is null");
		Long wechatPlatformId = RequestUtils.getLong(request, "wechat_platform_id", false, "wechat_platform_id is null", "");
		Integer type = RequestUtils.getInt(request, "type", false, "type is null", "");
		ActivityWechat activityWechat = new ActivityWechat();
		activityWechat.setActivityId(activityId);
		activityWechat.setWechatPlatformId(wechatPlatformId);
		List<ActivityWechat> activityWechatList;

		//获取user
		User user = UserUtils.getUser();
		if (type.equals(1)) {
			//已绑定的不能再绑定
			try {
				activityWechatList = activityWechatService.findList(activityWechat);
			} catch (Exception e) {
				logger.error("findList is error", e);
				return ResponseUtils.getFailApiResponseStr(ResponseEnum.B_E_FAILED_TO_GET);
			}
			if (CollectionUtils.isNotEmpty(activityWechatList)) {
				ActivityWechat activityWechat1 = activityWechatList.get(0);
				if (activityWechat1.getDelFlag().equals(0)) {
					return ResponseUtils.getFailApiResponseStr(ResponseEnum.B_E_BING_EXIST);
				} else {
					ActivityWechat activityWechat2 = new ActivityWechat();
					activityWechat2.setId(activityWechat1.getId());
					activityWechat2.setDelFlag("0");
					activityWechat2.setUpdateBy(user.getUpdateBy());
					activityWechatService.update(activityWechat2);
				}
			}

			//活动与公众号绑定
			try {
				activityWechat.setCreateBy(user.getCreateBy());
				activityWechat.setUpdateBy(user.getUpdateBy());
				activityWechatService.save(activityWechat);
				return ResponseUtils.getSuccessApiResponseStr(true);
			} catch (Exception e) {
				logger.error("bind activity wechat is error", e);
				return ResponseUtils.getFailApiResponseStr(ResponseEnum.B_E_FAILED_TO_ADD);
			}
			//解绑
		} else {
			try {
				ActivityWechat activityWechat2 = new ActivityWechat();
				activityWechat2.setId(id);
				activityWechat2.setDelFlag("1");
				activityWechat2.setUpdateBy(user.getUpdateBy());
				activityWechatService.update(activityWechat2);
				return ResponseUtils.getSuccessApiResponseStr(true);
			} catch (Exception e) {
				logger.error("bind activity wechat is error", e);
				return ResponseUtils.getFailApiResponseStr(ResponseEnum.B_E_MODIFY_ERROR);
			}
		}
	}
}