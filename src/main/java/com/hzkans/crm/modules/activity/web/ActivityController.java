/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.hzkans.crm.modules.activity.web;

import com.hzkans.crm.common.constant.ResponseEnum;
import com.hzkans.crm.common.persistence.Page;
import com.hzkans.crm.common.utils.DateUtil;
import com.hzkans.crm.common.utils.PriceUtil;
import com.hzkans.crm.common.utils.RequestUtils;
import com.hzkans.crm.common.utils.ResponseUtils;
import com.hzkans.crm.common.web.BaseController;
import com.hzkans.crm.modules.activity.constants.ActivityStatusEnum;
import com.hzkans.crm.modules.activity.entity.Activity;
import com.hzkans.crm.modules.activity.entity.PlatformShop;
import com.hzkans.crm.modules.activity.service.ActivityService;
import com.hzkans.crm.modules.activity.service.PlatformShopService;
import com.hzkans.crm.modules.sys.entity.Dict;
import com.hzkans.crm.modules.sys.entity.User;
import com.hzkans.crm.modules.sys.service.DictService;
import com.hzkans.crm.modules.sys.utils.UserUtils;
import com.hzkans.crm.modules.wechat.entity.WechatPlatfromDO;
import com.hzkans.crm.modules.wechat.service.WechatPlatfromService;
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

	@Autowired
	private DictService dictService;

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

		String name = RequestUtils.getString(request, false, "name", "name is null");
		Integer activityType = RequestUtils.getInt(request,"activity_type",false,"activity_type is null","");
		String activeDate = RequestUtils.getString(request, false, "active_date", "active_date is null");
		String inactiveDate = RequestUtils.getString(request, false, "inactive_date", "inactive_date is null");
		String orderActiveDate = RequestUtils.getString(request, false, "order_active_date", "order_active_date is null");
		String orderInactiveDate = RequestUtils.getString(request, false, "order_inactive_date", "order_inactive_date is null");
		Long wechatPlatformId = RequestUtils.getLong(request, "wechat_platform_id", false, "wechat_platform_id is null", "");
		Integer isFollow = RequestUtils.getInt(request,"is_follow",false,"is_follow is null","");
		Integer rebateType = RequestUtils.getInt(request,"rebate_type",false,"rebate_type is null","");
		Integer rebateChannel = RequestUtils.getInt(request,"rebate_channel",false,"rebate_channel is null","");
		Long perAmount = RequestUtils.getLong(request,"per_amount",false,"per_amount is null","");
		Integer maxOrderLimit = RequestUtils.getInt(request,"max_order_limit",true,"","");
		Long totalAmount = RequestUtils.getLong(request,"total_amount",true,"","");
        Integer isAudit = RequestUtils.getInt(request,"is_audit",false,"is_audit is null","");
		String shopName = RequestUtils.getString(request, false, "shop_name", "shop_name is null");
		String shopNo = RequestUtils.getString(request, false, "shop_no", "shop_no is null");
		String templateLink = RequestUtils.getString(request, false, "template_link", "template_link is null");
		Activity activity = new Activity();
		activity.setName(name);
		activity.setDelFlag("0");
		activity.setWechatPlatformId(wechatPlatformId);
		List<Activity> activityList;

		//必填不能为空
		if (null == name || null == activityType || null == isFollow || null == rebateChannel
				|| null == rebateType || null == perAmount || null == isAudit
				|| null == activeDate || null == inactiveDate || null == orderActiveDate
				|| null == orderInactiveDate){
			return ResponseUtils.getFailApiResponseStr(ResponseEnum.B_E_REQUIRED_NOT_FILLED);
		}
		//如果返利总额已填且小于单笔金额，则给出提示
		if (null != totalAmount && totalAmount < perAmount){
			return ResponseUtils.getFailApiResponseStr(ResponseEnum.B_E_PER_MUST_LOWER_TOTAL);
		}
		//不能添加已存在的活动
		try {
			activityList = activityService.findList(activity);
		} catch (Exception e) {
			logger.error("findList is error",e);
			return ResponseUtils.getFailApiResponseStr(ResponseEnum.B_E_FAILED_TO_GET);
		}
		if (CollectionUtils.isNotEmpty(activityList)){
			return ResponseUtils.getFailApiResponseStr(ResponseEnum.B_E_ACTIVITY_EXIST);
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
			activity.setMaxOrderLimit(maxOrderLimit);
			//如果没填订单总数，则默认为0
			if (null == maxOrderLimit){
				activity.setMaxOrderLimit(0);
			}
			if (null != totalAmount){
				activity.setTotalAmount(PriceUtil.parseYuan2Fen(totalAmount * 1.0));
				//如果没填返利金额，则默认为0
			}else {
				activity.setTotalAmount(0L);
			}

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
			return ResponseUtils.getFailApiResponseStr(ResponseEnum.B_E_FAILED_TO_ADD);
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
		Long wechatPlatformId = RequestUtils.getLong(request, "wechat_platform_id", false, "wechat_platform_id is null", "");
		String name = RequestUtils.getString(request, true, "name", "");
		String shopNo = RequestUtils.getString(request, true, "shop_no", "");
		Integer status = RequestUtils.getInt(request,"status",true,"","");
		Integer rebateType = RequestUtils.getInt(request,"rebate_type",true,"","");
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
			Page activityPage = new Page<Activity>();
			activityPage.setPageNo(start);
			activityPage.setPageSize(count);
			Activity activity = new Activity();
			activity.setDelFlag("0");
			activity.setStatus(status);
			activity.setRebateType(rebateType);
			activity.setName(name);
			activity.setShopNo(shopNo);
			activity.setActivityType(activityType);
			activity.setWechatPlatformId(wechatPlatformId);
			//搜索开始时间和结束时间非空判断
			if (startDate != null) {
				activity.setActiveDate(DateUtil.parse(startDate, DateUtil.NORMAL_DATETIME_PATTERN));
			}
			if (endDate != null) {
				activity.setInactiveDate(DateUtil.parse(endDate, DateUtil.NORMAL_DATETIME_PATTERN));
			}

			//分页获取活动列表
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
			return ResponseUtils.getFailApiResponseStr(ResponseEnum.B_E_FAILED_TO_GET);
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

		String id = RequestUtils.getString(request,false,"id","id is null");
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
			return ResponseUtils.getFailApiResponseStr(ResponseEnum.B_E_FAILED_TO_GET);
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

		String id = RequestUtils.getString(request,false,"id","id is null");
		try {
			Activity activity = new Activity();
			activity.setId(id);
			activityService.delete(activity);
			return ResponseUtils.getSuccessApiResponseStr(true);
		} catch (Exception e) {
			logger.error("delete activity is error",e);
			return ResponseUtils.getFailApiResponseStr(ResponseEnum.B_E_FAILED_TO_DELETE);
		}
	}

	/**
	 * 创建店铺
	 * @param request
	 * @return
	 */
	@RequestMapping("/saveShop")
	@ResponseBody
	public String saveShop(HttpServletRequest request) {

		Integer platform = RequestUtils.getInt(request,"platform",true,"","");
		String platformName = RequestUtils.getString(request, false, "platform_name", "platform_name is null");
		String shopName = RequestUtils.getString(request, false, "shop_name", "shop_name is null");
		Integer shop = 1;
		List<Integer> shopList = new ArrayList<>();
		List<PlatformShop> platformShopList;

		//查询是否有这个平台添加过
		try {
			PlatformShop platformShop = new PlatformShop();
			platformShop.setPlatform(platform);
			platformShop.setDelFlag("0");
			platformShopList = platformShopService.findList(platformShop);

			//有平台添加过则查出这个平台店铺id的最大值
			if (CollectionUtils.isNotEmpty(platformShopList)){
				for (PlatformShop platformShop1 : platformShopList){
					shopList.add(platformShop1.getShop());
				}
				//若已存在，则最大值+1就是所要添加shop的id；否则取默认值1
				shop = Collections.max(shopList) + 1;
			}
		} catch (Exception e) {
			logger.error("findList is error",e);
			return ResponseUtils.getFailApiResponseStr(ResponseEnum.B_E_FAILED_TO_GET);
		}

		//不能添加已存在的店铺
		try {
			PlatformShop platformShop1 = new PlatformShop();
			platformShop1.setShopName(shopName);
			platformShop1.setPlatform(platform);
			platformShop1.setDelFlag("0");
			platformShopList = platformShopService.findList(platformShop1);
		} catch (Exception e) {
			logger.error("findList is error",e);
			return ResponseUtils.getFailApiResponseStr(ResponseEnum.B_E_FAILED_TO_GET);
		}
		if (CollectionUtils.isNotEmpty(platformShopList)){
			return ResponseUtils.getFailApiResponseStr(ResponseEnum.B_E_SHOP_EXIST);
		}

		//添加店铺
		try {
			PlatformShop platformShop2 = new PlatformShop();
			platformShop2.setPlatform(platform);
			platformShop2.setPlatformName(platformName);
			platformShop2.setShop(shop);
			platformShop2.setShopName(shopName);
			platformShop2.setDelFlag("0");
			platformShopService.save(platformShop2);
			return ResponseUtils.getSuccessApiResponseStr(true);
		} catch (Exception e) {
			logger.error("save platform shop is error",e);
			return ResponseUtils.getFailApiResponseStr(ResponseEnum.B_E_FAILED_TO_ADD);
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
				//将平台放到set中
				for (PlatformShop platformShop1 : platformShopList){
					Integer platform = platformShop1.getPlatform();
					set.add(platform);
				}
				Iterator<Integer> iterator = set.iterator();
				while (iterator.hasNext()) {
					platformShopDTOS = new ArrayList<>();
					Integer platform = iterator.next();
					//将各个店铺放到相应的平台中
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
			return ResponseUtils.getFailApiResponseStr(ResponseEnum.B_E_FAILED_TO_GET);
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
			String id = RequestUtils.getString(request,false,"id","id is null");
			Integer type = RequestUtils.getInt(request,"type",false,"type is null","");
			Activity activity = new Activity();
			activity.setId(id);

			//type:1为暂停；2为继续（进行中）；3为取消（结束）
			if (null != type) {
				switch (type) {
					case 1:
						activity.setStatus(ActivityStatusEnum.PAUSE.getCode());
						break;
					case 2:
						activity.setStatus(ActivityStatusEnum.ACTIVING.getCode());
						break;
					case 3:
						activity.setStatus(ActivityStatusEnum.ENDED.getCode());
						break;
					default:
				}
			}
			activityService.update(activity);
			return ResponseUtils.getSuccessApiResponseStr(true);
		} catch (Exception e) {
			logger.error("update status is error",e);
			return ResponseUtils.getFailApiResponseStr(ResponseEnum.B_E_UPDATE_STATUS_FAIL);
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
			//从字典管理中查询活动类型
			Map map = new HashMap();
			List<Dict> dictList = dictService.findListByType("name_type");
			if (CollectionUtils.isNotEmpty(dictList)){
				for (Dict dict : dictList){
					map.put(dict.getValue(),dict.getLabel());
				}
			}
			return ResponseUtils.getSuccessApiResponseStr(map);
		} catch (Exception e) {
			logger.error("findList is error",e);
			return ResponseUtils.getFailApiResponseStr(ResponseEnum.B_E_FAILED_TO_GET);
		}
	}
}