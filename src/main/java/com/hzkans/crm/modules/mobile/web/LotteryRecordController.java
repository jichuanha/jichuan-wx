/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.hzkans.crm.modules.mobile.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hzkans.crm.common.constant.ResponseEnum;
import com.hzkans.crm.common.utils.RequestUtils;
import com.hzkans.crm.common.utils.ResponseUtils;
import com.hzkans.crm.modules.activity.entity.ActivityLottery;
import com.hzkans.crm.modules.activity.service.ActivityLotteryService;
import com.hzkans.crm.modules.activity.utils.LotteryUtil;
import com.hzkans.crm.modules.trade.entity.JoinActivity;
import com.hzkans.crm.modules.trade.service.JoinActivityService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hzkans.crm.common.config.Global;
import com.hzkans.crm.common.persistence.Page;
import com.hzkans.crm.common.web.BaseController;
import com.hzkans.crm.common.utils.StringUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 抽奖记录Controller
 * @author wsh
 * @version 2018-12-20
 */
@Controller
@RequestMapping(value = "${adminPath}/mobile/lottery")
public class LotteryRecordController extends BaseController {

	@Autowired
	private JoinActivityService joinActivityService;

	@Autowired
	private ActivityLotteryService activityLotteryService;
	
	@RequestMapping
	public String get() {

		return null;
	}
	
	@RequestMapping(value = "lottery")
	@ResponseBody
	public String lottery(HttpServletRequest request) {
		Integer id = RequestUtils.getInt(request,"id",false,"id is null","");
		try {
			JoinActivity joinActivity = joinActivityService.lottery(id);
			return ResponseUtils.getSuccessApiResponseStr(joinActivity);
		}catch (Exception e){
			logger.error("lottery error",e);
			return ResponseUtils.getFailApiResponseStr(ResponseEnum.B_E_FAILED_TO_ADD);
		}
	}

}