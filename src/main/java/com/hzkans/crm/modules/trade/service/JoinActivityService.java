package com.hzkans.crm.modules.trade.service;

import com.hzkans.crm.common.constant.ResponseEnum;
import com.hzkans.crm.common.persistence.PagePara;
import com.hzkans.crm.common.service.CrudService;
import com.hzkans.crm.common.service.ServiceException;
import com.hzkans.crm.common.utils.JsonUtil;
import com.hzkans.crm.common.utils.PriceUtil;
import com.hzkans.crm.common.utils.StringUtils;
import com.hzkans.crm.modules.activity.constants.ActivityStatusEnum;
import com.hzkans.crm.modules.activity.entity.Activity;
import com.hzkans.crm.modules.activity.entity.ActivityLottery;
import com.hzkans.crm.modules.activity.entity.PlatformShop;
import com.hzkans.crm.modules.activity.service.ActivityLotteryService;
import com.hzkans.crm.modules.activity.service.ActivityService;
import com.hzkans.crm.modules.activity.service.PlatformShopService;
import com.hzkans.crm.modules.trade.constants.AttentionEnum;
import com.hzkans.crm.modules.trade.constants.JoinActivityStatusEnum;
import com.hzkans.crm.modules.trade.entity.Order;
import com.hzkans.crm.modules.trade.entity.OrderMember;
import com.hzkans.crm.modules.trade.utils.TradeUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.hzkans.crm.modules.trade.entity.JoinActivity;
import com.hzkans.crm.modules.trade.dao.JoinActivityDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 活动订单管理Service
 * @author jc
 * @version 2018-12-01
 */
@Service
@Transactional(rollbackFor = ServiceException.class)
public class JoinActivityService extends CrudService<JoinActivityDao, JoinActivity> {

	@Autowired
	private JoinActivityDao joinActivityDao;
	@Autowired
	private PlatformShopService platformShopService;
	@Autowired
	private OrderService orderService;
	@Autowired
    private OrderMemberService orderMemberService;
	@Autowired
	private ActivityService activityService;
	@Autowired
	private ActivityLotteryService activityLotteryService;

	/**
	 * 获取参加活动订单信息集合
	 * @param pagePara
	 * @param wechatId
	 * @return
	 */
	public PagePara<JoinActivity> listJoinActivityPage(PagePara<JoinActivity> pagePara, Long wechatId) {
		TradeUtil.isAllNull(pagePara);
		PagePara<JoinActivity> para = null;
		try {
			logger.info(" pagePara {}",JsonUtil.toJson(pagePara));
			para = new PagePara<>();
			List<JoinActivity> joinActivities = null;
			Integer actType = pagePara.getData().getActType();
			switch (actType){
				case 1:
					joinActivities = joinActivityDao.selectJoinActivityPage(pagePara);
					break;
				case 2:
					joinActivities = joinActivityDao.selectJoinActivityLotteryPage(pagePara);
					break;
				default:
			}

			List<JoinActivity> newJoinAct = new ArrayList<>();
			if(CollectionUtils.isNotEmpty(joinActivities)) {
				for (JoinActivity ja : joinActivities) {
					JoinActivity joinActivity = initParameter(wechatId.toString(), ja);
					newJoinAct.add(joinActivity);
                }
			}

			int totalCount = 0;
			switch (actType){
				case 1:
					totalCount = joinActivityDao.selectJoinActivityPageCount(pagePara);
					break;
				case 2:
					totalCount = joinActivityDao.selectJoinActivityLotteryPageCount(pagePara);
					break;
				default:
			}

			para.setCount(totalCount);
			para.setList(newJoinAct);
		} catch (Exception e) {
			logger.error("JoinActivityService error",e);
			throw new ServiceException(ResponseEnum.DATEBASE_QUERY_ERROR);
		}

		return para;

	}

	/**
	 *	获取参加活动订单信息
	 * @param joinActivity
	 * @return
	 * @throws ServiceException
	 */
	public JoinActivity getJoinActivity(JoinActivity joinActivity) throws ServiceException {
		TradeUtil.isAllNull(joinActivity);
		JoinActivity joinActivity1 = null;
		try {
			joinActivity1 = get(joinActivity);
		} catch (Exception e) {
			logger.error("getJoinActivity error",e);
			throw new ServiceException(ResponseEnum.DATEBASE_QUERY_ERROR);
		}
		return joinActivity1;
	}

	/**
	 * 订单审核(同意和拒绝)
	 * @param joinActivity
	 */
	public void auditOrder(JoinActivity joinActivity) throws ServiceException {
		TradeUtil.isAllNull(joinActivity);
		Integer status = joinActivity.getStatus();
		//审核同意
		if(JoinActivityStatusEnum.PERSONAL_AGREE.getCode().equals(status)) {
			//TODO 同意后的逻辑处理还没有明确,稍后完成
		}
		//审核拒接 必须有拒绝原因
		if(JoinActivityStatusEnum.PERSONAL_DISAGREE.getCode().equals(status)) {
			if(StringUtils.isEmpty(joinActivity.getMessage())) {
				logger.error("message is null");
				throw new ServiceException(ResponseEnum.B_E_MESSAGE_IS_NULL);
			}

		}
		try {
			//修改表的状态
			joinActivityDao.updateJoinActivityStatus(joinActivity);
		} catch (Exception e) {
			logger.error("auditOrder error",e);
			throw new ServiceException(ResponseEnum.B_E_UPDATE_STATUS_FAIL);
		}

	}

	/**
	 * 获取参加活动订单详情
	 * @param id
	 * @param wechatId
	 * @return
	 */
	public Map<String, Object> getOrderDetail(Integer id, Integer wechatId) {
		TradeUtil.isAllNull(id);
		Map<String, Object> resultMap = new HashMap<>(2);
		//根据id查找到申请记录
		JoinActivity joinActivity = new JoinActivity();
		joinActivity.setId(id.toString());
		JoinActivity activity = getJoinActivity(joinActivity);
		if(null == activity) {
			logger.error(ResponseEnum.B_E_NOT_FIND_JOIN.getMsg());
			throw new ServiceException(ResponseEnum.B_E_NOT_FIND_JOIN);
		}
		Activity act = null;
		try {
			act = activityService.get(activity.getActId().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		Activity newAct = new Activity();
		if(null == act) {
			logger.error(ResponseEnum.B_E_NOT_FIND_ACT.getMsg());
			throw new ServiceException(ResponseEnum.B_E_NOT_FIND_ACT);
		}
		try {
			activity.setActMoney(act.getPerAmount());
			newAct.setName(act.getName());
			newAct.setStatusStr(ActivityStatusEnum.getActivityStatusEnum(act.getStatus()).getDesc());
			newAct.setActiveDate(act.getActiveDate());
			newAct.setInactiveDate(act.getInactiveDate());
			newAct.setActivityType(act.getActivityType());
			JoinActivity newJoin = initParameter(wechatId.toString(), activity);

			resultMap.put("act", newAct);
			resultMap.put("order", newJoin);
			return resultMap;
		} catch (Exception e) {
			logger.error("getOrderDetail error",e);
			throw new ServiceException(ResponseEnum.S_E_SERVICE_ERROR);
		}

	}

	/**
	 * 获取参加幸运抽奖活动订单详情
	 * @param id
	 * @param wechatId
	 * @return
	 */
	public Map<String, Object> getOrderLotteryDetail(Integer id, Integer wechatId) {
		TradeUtil.isAllNull(id);
		Map<String, Object> resultMap = new HashMap<>(2);
		//根据id查找到申请记录
		JoinActivity joinActivity = new JoinActivity();
		joinActivity.setId(id.toString());
		JoinActivity activity = getJoinActivity(joinActivity);
		if(null == activity) {
			logger.error(ResponseEnum.B_E_NOT_FIND_JOIN.getMsg());
			throw new ServiceException(ResponseEnum.B_E_NOT_FIND_JOIN);
		}
		ActivityLottery activityLottery = null;
		try {
			activityLottery = activityLotteryService.get(activity.getActId().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		Activity newAct = new Activity();
		if(null == activityLottery) {
			logger.error(ResponseEnum.B_E_NOT_FIND_ACT.getMsg());
			throw new ServiceException(ResponseEnum.B_E_NOT_FIND_ACT);
		}
		try {
			newAct.setName(activityLottery.getName());
			newAct.setStatusStr(ActivityStatusEnum.getActivityStatusEnum(activityLottery.getStatus()).getDesc());
			newAct.setActiveDate(activityLottery.getActiveDate());
			newAct.setInactiveDate(activityLottery.getInactiveDate());
			newAct.setActivityType(activityLottery.getActivityType());
			JoinActivity newJoin = initLotteryParameter(wechatId.toString(), activity);

			resultMap.put("act", newAct);
			resultMap.put("order", newJoin);
			return resultMap;
		} catch (Exception e) {
			logger.error("getOrderDetail error",e);
			throw new ServiceException(ResponseEnum.S_E_SERVICE_ERROR);
		}

	}

	private JoinActivity initParameter(String wechatId, JoinActivity ja) throws ServiceException{
		try {
			//获取店铺和平台名称
			PlatformShop platformShop = new PlatformShop();
			platformShop.setPlatform(ja.getPlatformType());
			platformShop.setShop(ja.getShopNo());
			PlatformShop shop = platformShopService.getPlatformShop(platformShop);
			ja.setPlatformName(shop.getPlatformName());
			ja.setShopName(shop.getShopName());
			//订单信息
			Order order = new Order();
			order.setId(ja.getOrderId().toString());
			Order order1 = orderService.getOrder(order);
			ja.setMemberName(order1.getBuyerName());
			ja.setMobile(order1.getMobile());
			ja.setActMoneyStr(PriceUtil.parseFen2YuanStr(ja.getActMoney()));
			ja.setStatusStr(JoinActivityStatusEnum.getJoinActivityStatusEnum(ja.getStatus()).getDesc());
			//查询会员是否绑定公众号
			OrderMember orderMember = new OrderMember();
			orderMember.setNickName(order1.getBuyerName());
			OrderMember member = orderMemberService.getOrderMember(orderMember);
			if(null == member) {
                logger.info("未找到对应的顾客信息",order1.getNickName());
                ja.setAttentionStr(AttentionEnum.ORDER_NOT_BIND.getDesc());
            }
			if(null != member) {
				ja.setWechatNo(member.getWechatNo());
                String attentionWechat = member.getAttention_wechat();
                if(StringUtils.isEmpty(attentionWechat)) {
                    ja.setAttentionStr(AttentionEnum.ORDER_NOT_BIND.getDesc());
                }else if(attentionWechat.contains(wechatId)) {
                    ja.setAttentionStr(AttentionEnum.ORDER_BIND.getDesc());
                }else {
                    ja.setAttentionStr(AttentionEnum.ORDER_NOT_BIND.getDesc());
                }
            }
            return ja;
		} catch (Exception e) {
			logger.error("initParameter error",e);
			throw new ServiceException(ResponseEnum.DATEBASE_QUERY_ERROR);
		}
	}

	private JoinActivity initLotteryParameter(String wechatId, JoinActivity ja) throws ServiceException{
		try {
			//获取店铺和平台名称
			PlatformShop platformShop = new PlatformShop();
			platformShop.setPlatform(ja.getPlatformType());
			platformShop.setShop(ja.getShopNo());
			PlatformShop shop = platformShopService.getPlatformShop(platformShop);
			ja.setPlatformName(shop.getPlatformName());
			ja.setShopName(shop.getShopName());
			//订单信息
			Order order = new Order();
			order.setId(ja.getOrderId().toString());
			Order order1 = orderService.getOrder(order);
			ja.setMemberName(order1.getBuyerName());
			ja.setStatusStr(JoinActivityStatusEnum.getJoinActivityStatusEnum(ja.getStatus()).getDesc());
			//查询会员是否绑定公众号
			OrderMember orderMember = new OrderMember();
			orderMember.setNickName(order1.getBuyerName());
			OrderMember member = orderMemberService.getOrderMember(orderMember);
			if(null == member) {
                logger.info("未找到对应的顾客信息",order1.getNickName());
                ja.setAttentionStr(AttentionEnum.ORDER_NOT_BIND.getDesc());
            }
			if(null != member) {
				ja.setWechatNo(member.getWechatNo());
                String attentionWechat = member.getAttention_wechat();
                if(StringUtils.isEmpty(attentionWechat)) {
                    ja.setAttentionStr(AttentionEnum.ORDER_NOT_BIND.getDesc());
                }else if(attentionWechat.contains(wechatId)) {
                    ja.setAttentionStr(AttentionEnum.ORDER_BIND.getDesc());
                }else {
                    ja.setAttentionStr(AttentionEnum.ORDER_NOT_BIND.getDesc());
                }
            }
            return ja;
		} catch (Exception e) {
			logger.error("initLotteryParameter error",e);
			throw new ServiceException(ResponseEnum.DATEBASE_QUERY_ERROR);
		}
	}

}