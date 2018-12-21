/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.hzkans.crm.modules.mobile.web;

import com.hzkans.crm.common.constant.ResponseEnum;
import com.hzkans.crm.common.utils.RequestUtils;
import com.hzkans.crm.common.utils.ResponseUtils;
import com.hzkans.crm.common.web.BaseController;
import com.hzkans.crm.modules.trade.entity.JoinActivity;
import com.hzkans.crm.modules.trade.service.JoinActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 抽奖记录Controller
 * @author wsh
 * @version 2018-12-20
 */
@Controller
@RequestMapping(value = "${frontPath}/mobile/lottery")
public class LotteryRecordController extends BaseController {

	@Autowired
	private JoinActivityService joinActivityService;

	@RequestMapping
	public String get() {

		return null;
	}
	@RequestMapping(value = "/link_lottery")
	public String link_lottery_test() {
		return "modules/mobile/lottery";
	}


	@RequestMapping("/lottery")
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