/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.hzkans.crm.modules.activity.web;

import com.hzkans.crm.common.constant.ResponseEnum;
import com.hzkans.crm.common.persistence.Page;
import com.hzkans.crm.common.utils.DateUtil;
import com.hzkans.crm.common.utils.JsonUtil;
import com.hzkans.crm.common.utils.RequestUtils;
import com.hzkans.crm.common.utils.ResponseUtils;
import com.hzkans.crm.common.web.BaseController;
import com.hzkans.crm.modules.activity.entity.ActivityLottery;
import com.hzkans.crm.modules.activity.service.ActivityLotteryService;
import com.hzkans.crm.modules.sys.entity.Dict;
import com.hzkans.crm.modules.sys.entity.User;
import com.hzkans.crm.modules.sys.service.DictService;
import com.hzkans.crm.modules.sys.utils.UserUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 幸运抽奖活动Controller
 * @author wsh
 * @version 2018-12-11
 */
@Controller
@RequestMapping(value = "${adminPath}/activity/activityLottery")
public class ActivityLotteryController extends BaseController {

	@Autowired
	private ActivityLotteryService activityLotteryService;

	@Autowired
	private DictService dictService;

	@RequestMapping(value = "saveLottery")
	public String saveLottery() {
		return "modules/activity/activity-lottery-new";
	}

	@RequestMapping(value = "getLotteryList")
	public String getLotteryList() {
		return "modules/activity/activity-lottery-list";
	}

	@RequestMapping(value = "getLotteryDetail")
	public String getLotteryDetail() {
		return "modules/activity/activity-lottery-detail";
	}

	/**
	 * 新建幸运抽奖活动
	 * @param request
	 * @return
	 */
	@RequestMapping("/save_Lottery")
	@ResponseBody
	public String saveLottery(HttpServletRequest request) {
		String name = RequestUtils.getString(request, false, "name", "name is null");
		Integer activityType = RequestUtils.getInt(request,"activity_type",false,"activity_type is null","");
		String activeDate = RequestUtils.getString(request, false, "active_date", "active_date is null");
		String inactiveDate = RequestUtils.getString(request, false, "inactive_date", "inactive_date is null");
		String orderActiveDate = RequestUtils.getString(request, false, "order_active_date", "order_active_date is null");
		String orderInactiveDate = RequestUtils.getString(request, false, "order_inactive_date", "order_inactive_date is null");
		Long wechatPlatformId = RequestUtils.getLong(request, "wechat_platform_id", false, "wechat_platform_id is null", "");
		Integer isFollow = RequestUtils.getInt(request,"is_follow",false,"is_follow is null","");
		Integer auditType = RequestUtils.getInt(request,"audit_type",false,"audit_type is null","");
		Integer textAuditType = RequestUtils.getInt(request,"text_audit_type",false,"text_audit_type is null","");
		Integer totalOrder = RequestUtils.getInt(request,"total_order",true,"","");
		String shopName = RequestUtils.getString(request, false, "shop_name", "shop_name is null");
		String shopNo = RequestUtils.getString(request, false, "shop_no", "shop_no is null");
		String templateLink = RequestUtils.getString(request, false, "template_link", "template_link is null");
		String content = RequestUtils.getString(request, false, "content",
				"content is null");

		Type type = new com.google.gson.reflect.TypeToken<List<ActivityLottery.LotteryPrize>>() {
		}.getType();

		List<ActivityLottery.LotteryPrize> activityPrizeList = JsonUtil.parseJson(content, type);
		if (activityPrizeList == null) {
			return ResponseUtils.getFailApiResponseStr(ResponseEnum.P_E_PARAM_ISNULL);
		}

		ActivityLottery activityLottery = new ActivityLottery();
		activityLottery.setName(name);
		activityLottery.setDelFlag("0");
		activityLottery.setWechatPlatformId(wechatPlatformId);
		List<ActivityLottery> activityLotteryList;

		//必填不能为空
		if (null == name || null == activityType || null == isFollow || null == textAuditType
				|| null == auditType || null == activeDate || null == inactiveDate
				|| null == orderActiveDate || null == orderInactiveDate){
			return ResponseUtils.getFailApiResponseStr(ResponseEnum.B_E_REQUIRED_NOT_FILLED);
		}

		//不能添加已存在的活动
		try {
			activityLotteryList = activityLotteryService.findList(activityLottery);
		} catch (Exception e) {
			logger.error("findList is error",e);
			return ResponseUtils.getFailApiResponseStr(ResponseEnum.B_E_FAILED_TO_GET);
		}
		if (CollectionUtils.isNotEmpty(activityLotteryList)){
			return ResponseUtils.getFailApiResponseStr(ResponseEnum.B_E_ACTIVITY_EXIST);
		}

		//比例总和要为1,不为1则给出提示
		Double rate = 0.0;
		for (ActivityLottery.LotteryPrize activityPrize : activityPrizeList){
			Double prizeRate = activityPrize.getPrizeRate();
			if (null != prizeRate) {
				rate += prizeRate;
			}
		}
		if (rate > 1){
			return ResponseUtils.getFailApiResponseStr(ResponseEnum.B_E_RATE_IS_BIGGER);
		}else if (rate < 1){
			return ResponseUtils.getFailApiResponseStr(ResponseEnum.B_E_RATE_IS_SMALLER);
		}

		//添加活动
		try {
			activityLottery.setActivityType(activityType);
			activityLottery.setActiveDate(DateUtil.parse(activeDate, DateUtil.NORMAL_DATETIME_PATTERN));
			activityLottery.setInactiveDate(DateUtil.parse(inactiveDate, DateUtil.NORMAL_DATETIME_PATTERN));
			activityLottery.setOrderActiveDate(DateUtil.parse(orderActiveDate, DateUtil.NORMAL_DATETIME_PATTERN));
			activityLottery.setOrderInactiveDate(DateUtil.parse(orderInactiveDate, DateUtil.NORMAL_DATETIME_PATTERN));
			activityLottery.setIsFollow(isFollow);
			activityLottery.setAuditType(auditType);
			activityLottery.setTextAuditType(textAuditType);
			//若totalOrder没传，则表示不限制
			if (null == totalOrder){
				activityLottery.setTotalOrder(0);
			}
			activityLottery.setShopName(shopName);
			activityLottery.setShopNo(shopNo);
			activityLottery.setTemplateLink(templateLink);
			activityLottery.setStatus(0);

			//获取user
			User user = UserUtils.getUser();
			activityLottery.setCreateBy(user.getCreateBy());
			activityLottery.setUpdateBy(user.getUpdateBy());
			activityLottery.setLotteryPrizeList(activityPrizeList);
			activityLotteryService.saveLottery(activityLottery);
			return ResponseUtils.getSuccessApiResponseStr(true);
		} catch (Exception e) {
			logger.error("save activity lottery is error",e);
			return ResponseUtils.getFailApiResponseStr(ResponseEnum.B_E_FAILED_TO_ADD);
		}
	}

	/**
	 * 获取幸运抽奖活动列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/activityLotteryList")
	@ResponseBody
	public String activityList(HttpServletRequest request) {

		Integer start = RequestUtils.getInt(request, "current_page", true, "", "");
		Integer count = RequestUtils.getInt(request, "page_size", true, "", "");
		Long wechatPlatformId = RequestUtils.getLong(request, "wechat_platform_id", true, "wechat_platform_id is " +
				"null", "");
		Integer isFollow = RequestUtils.getInt(request,"is_follow",true,"is_follow is null","");
		String name = RequestUtils.getString(request, true, "name", "");
		String shopNo = RequestUtils.getString(request, true, "shop_no", "");
		Integer status = RequestUtils.getInt(request,"status",true,"","");
		Integer auditType = RequestUtils.getInt(request,"audit_type",true,"audit_type is null","");
		Integer textAuditType = RequestUtils.getInt(request,"text_audit_type",true,"text_audit_type is null","");
		Integer activityType = RequestUtils.getInt(request,"activity_type",true,"","");
		String startDate = RequestUtils.getString(request, true,"active_date", "");
		String endDate = RequestUtils.getString(request,true, "inactive_date", "");
		if (start == null || start == 0) {
			start = 1;
		}
		if (count == null || count == 0) {
			count = 20;
		}
		try {
			Page activityLotteryPage = new Page<ActivityLottery>();
			activityLotteryPage.setPageNo(start);
			activityLotteryPage.setPageSize(count);
			ActivityLottery activityLottery = new ActivityLottery();
			activityLottery.setDelFlag("0");
			activityLottery.setStatus(status);
			activityLottery.setAuditType(auditType);
			activityLottery.setTextAuditType(textAuditType);
			activityLottery.setName(name);
			activityLottery.setIsFollow(isFollow);
			activityLottery.setShopNo(shopNo);
			activityLottery.setActivityType(activityType);
			activityLottery.setWechatPlatformId(wechatPlatformId);
			//搜索开始时间和结束时间非空判断
			if (startDate != null) {
				activityLottery.setActiveDate(DateUtil.parse(startDate, DateUtil.NORMAL_DATETIME_PATTERN));
			}
			if (endDate != null) {
				activityLottery.setInactiveDate(DateUtil.parse(endDate, DateUtil.NORMAL_DATETIME_PATTERN));
			}

			//分页获取活动列表
			Page<ActivityLottery> page = activityLotteryService.findPageLottery(activityLotteryPage,activityLottery);
			return ResponseUtils.getSuccessApiResponseStr(page);
		} catch (Exception e) {
			logger.error("findPage is error",e);
			return ResponseUtils.getFailApiResponseStr(ResponseEnum.B_E_FAILED_TO_GET);
		}
	}

	/**
	 * 获取幸运抽奖活动详情
	 * @param request
	 * @return
	 */
	@RequestMapping("/activityLotteryDetail")
	@ResponseBody
	public String activityLotteryDetail(HttpServletRequest request) {

		String id = RequestUtils.getString(request,false,"id","id is null");
		try {
			ActivityLottery activityLottery = activityLotteryService.getLottery(id);
			return ResponseUtils.getSuccessApiResponseStr(activityLottery);
		} catch (Exception e) {
			logger.error("get activity lottery detail is error",e);
			return ResponseUtils.getFailApiResponseStr(ResponseEnum.B_E_FAILED_TO_GET);
		}
	}

	/**
	 * 删除幸运抽奖活动
	 * @param request
	 * @return
	 */
	@RequestMapping("/deleteActivityLottery")
	@ResponseBody
	public String deleteActivityLottery(HttpServletRequest request) {

		String id = RequestUtils.getString(request,false,"id","id is null");
		try {
			ActivityLottery activityLottery = new ActivityLottery();
			activityLottery.setId(id);
			activityLotteryService.delete(activityLottery);
			return ResponseUtils.getSuccessApiResponseStr(true);
		} catch (Exception e) {
			logger.error("delete activity lottery is error",e);
			return ResponseUtils.getFailApiResponseStr(ResponseEnum.B_E_FAILED_TO_DELETE);
		}
	}

	/**
	 * 获取奖品类型列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/prizeTypeList")
	@ResponseBody
	public String activityTypeList(HttpServletRequest request) {
		try {
			//从字典管理中查询活动类型
			Map<String,String> prizeMap = new LinkedHashMap<>();
			List<Dict> dictList = dictService.findListByType("prize_type");
			if (CollectionUtils.isNotEmpty(dictList)){
				for (Dict dict : dictList){
					prizeMap.put(dict.getValue(),dict.getLabel());
				}
			}
			logger.info("[{}]prizeMap:{}",JsonUtil.toJson(ResponseUtils.getSuccessApiResponseStr(prizeMap)));

			return ResponseUtils.getSuccessApiResponseStr(prizeMap);
		} catch (Exception e) {
			logger.error("findList is error",e);
			return ResponseUtils.getFailApiResponseStr(ResponseEnum.B_E_FAILED_TO_GET);
		}
	}
}