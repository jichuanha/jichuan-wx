/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.hzkans.crm.modules.mobile.web;

import com.hzkans.crm.common.constant.ResponseEnum;
import com.hzkans.crm.common.utils.RequestUtils;
import com.hzkans.crm.common.utils.ResponseUtils;
import com.hzkans.crm.common.web.BaseController;
import com.hzkans.crm.modules.activity.entity.ActivityLottery;
import com.hzkans.crm.modules.activity.service.ActivityLotteryService;
import com.hzkans.crm.modules.trade.entity.JoinActivity;
import com.hzkans.crm.modules.trade.service.JoinActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 抽奖记录Controller
 * @author wsh
 * @version 2018-12-20
 */
@Controller
@RequestMapping(value = "/mobile/lottery")
public class LotteryRecordController extends BaseController {

	@Autowired
	private JoinActivityService joinActivityService;

	@Autowired
	private ActivityLotteryService activityLotteryService;

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
		String id = RequestUtils.getString(request,false,"id","id is null");
		try {
			JoinActivity joinActivity = new JoinActivity();
			joinActivity.setId(id);
			JoinActivity joinActivity1 = joinActivityService.lottery(joinActivity);
			return ResponseUtils.getSuccessApiResponseStr(joinActivity1);
		}catch (Exception e){
			logger.error("lottery error",e);
			return ResponseUtils.getFailApiResponseStr(ResponseEnum.B_E_MODIFY_ERROR);
		}
	}

	@RequestMapping("/lottery_prize_picture")
	@ResponseBody
	public String lotteryPrizePicture(HttpServletRequest request) {
		Long id = RequestUtils.getLong(request,"id",false,"id is null","");
		try {
			ActivityLottery activityLottery = new ActivityLottery();
			activityLottery.setId(id.toString());
			List<ActivityLottery.LotteryPrize> lotteryPrizeList = joinActivityService.getLotteryPrize(activityLottery);

			return ResponseUtils.getSuccessApiResponseStr(lotteryPrizeList);
		}catch (Exception e){
			logger.error("lottery error",e);
			return ResponseUtils.getFailApiResponseStr(ResponseEnum.B_E_MODIFY_ERROR);
		}
	}

}