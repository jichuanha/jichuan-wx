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
import com.hzkans.crm.modules.activity.utils.LotteryUtil;
import com.hzkans.crm.modules.trade.constants.AttentionEnum;
import com.hzkans.crm.modules.trade.constants.JoinActivityStatusEnum;
import com.hzkans.crm.modules.trade.constants.NeedCodeEnum;
import com.hzkans.crm.modules.trade.constants.OrderStatusEnum;
import com.hzkans.crm.modules.trade.entity.Order;
import com.hzkans.crm.modules.trade.entity.OrderMember;
import com.hzkans.crm.modules.trade.entity.QueryResult;
import com.hzkans.crm.modules.trade.utils.TradeUtil;
import com.hzkans.crm.modules.wechat.entity.MemberAssociation;
import com.hzkans.crm.modules.wechat.service.MemberAssociationService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.hzkans.crm.modules.trade.entity.JoinActivity;
import com.hzkans.crm.modules.trade.dao.JoinActivityDao;

import java.util.*;

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
	@Autowired
	private MemberAssociationService memberAssociationService;

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
		if (null == activity) {
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
		if (null == activityLottery) {
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
			logger.error("getOrderDetail error", e);
			throw new ServiceException(ResponseEnum.S_E_SERVICE_ERROR);
		}
	}
	/**
	 * 参加活动的逻辑处理
	 * @param queryResult
	 */
	public QueryResult joinActivity(QueryResult queryResult) {

		try {
			String mobile = queryResult.getMobile();
			Long actId = queryResult.getActId();
			QueryResult result = new QueryResult();
			result.setMobile(mobile);
			//判断是否要绑定手机号
			Integer needCode = queryResult.getNeedCode();
			if(NeedCodeEnum.NEED.getCode().equals(needCode)) {
                //openId绑定手机号
                MemberAssociation association = new MemberAssociation();
                association.setMobile(queryResult.getMobile());
                association.setOpenId(queryResult.getOpenId());
                memberAssociationService.boundMobile(association);
            }
			Activity activity = activityService.get(actId.toString());
            //获取可以参加活动的订单
			PagePara<Order> drawNum = getDrawNum(queryResult, activity);
			//1让这些订单参加活动
			Integer count = drawNum.getCount();
			List<Order> list = drawNum.getList();
			result.setDrawNum(count);
			//1.1 抽奖次数为0,就直接返回,不为0,将查询到的数据插入到参加活动表
			if(count == 0) {
				return result;
			}
			List<JoinActivity> joinActivities = dealParemater(list, activity, queryResult.getOpenId());
			joinActivityDao.insertAllJoinActivity(joinActivities);
			//1.2 将查询的订单状态修改
			Order order = new Order();
			order.setStatus(OrderStatusEnum.HAS_JOIN_ACT.getCode());
			order.setIds(getIds(list));
			orderService.updateOrder(order);
			return result;
		} catch (Exception e) {
			logger.error("joinActivity error",e);
			throw new ServiceException(ResponseEnum.B_E_JOIN_ACTIVITY_ERROR);
		}

	}

	private List<Long> getIds(List<Order> list) {
		List<Long> ids = new ArrayList<>();
		for (Order order : list) {
			ids.add(Long.valueOf(order.getId()));
		}
		return ids;
	}

	/**
	 * 组装数据
	 * @param orders
	 * @param activity
	 * @param openId
	 * @return
	 */
	private List<JoinActivity> dealParemater(List<Order> orders, Activity activity, String openId) {
		List<JoinActivity> joinActivities = new ArrayList<>();
		for (Order order : orders) {
			JoinActivity joinActivity = new JoinActivity();
			joinActivity.setMobile(order.getMobile());
			joinActivity.setOpenId(openId);
			joinActivity.setOrderId(Long.valueOf(order.getId()));
			joinActivity.setOrderSn(order.getOrderSn());
			joinActivity.setPlatformType(order.getPlatformType());
			joinActivity.setShopNo(Integer.valueOf(order.getShopNo()));
			joinActivity.setPayData(order.getPayTime());
			joinActivity.setActId(Long.valueOf(activity.getId()));
			joinActivity.setActType(activity.getActivityType());
			joinActivity.setActName(activity.getName());
			joinActivities.add(joinActivity);
		}
		return joinActivities;
	}

	/**
	 * 查询在该活动设置的订单有效时间内该手机号的订单
	 * @param queryResult
	 * @return
	 */
	public PagePara<Order> getDrawNum(QueryResult queryResult, Activity activity) {
		try {

			String shopNo = activity.getShopNo();
			String[] split = shopNo.split(",");
			List<String> strings = Arrays.asList(split);
			Order order = new Order();
			order.setMobile(queryResult.getMobile());
			order.setStartDate(activity.getOrderActiveDate());
			order.setEndDate(activity.getOrderInactiveDate());
			order.setShopLists(strings);
			order.setStatus(OrderStatusEnum.ORDER_LIST.getCode());
			PagePara<Order> pagePara = new PagePara<>();
			pagePara.setData(order);
			return orderService.getAllOrder(pagePara);
		} catch (Exception e) {
			logger.error("getDrawNum error",e);
			throw new ServiceException(ResponseEnum.B_E_GET_DRAWNUM_ERROR);
		}
	}

	public void saveAll(List<JoinActivity> joinActivities) {
		TradeUtil.isAllNull(joinActivities);
		try {
			joinActivityDao.insertAllJoinActivity(joinActivities);
		} catch (Exception e) {
			logger.error("joinActivities error",e);
			throw new ServiceException(ResponseEnum.DATEBASE_SAVE_ERROR);
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
	/**
	 * 抽奖
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	public JoinActivity lottery(Integer id) throws ServiceException{

		try {
			JoinActivity joinActivity = new JoinActivity();
			joinActivity.setId(id.toString());
			//从抽奖活动列表中获取活动id
			JoinActivity joinActivity1 = get(id.toString());
			Long actId = joinActivity1.getActId();

			//从此活动中获取奖品概率
			ActivityLottery activityLottery = activityLotteryService.getLottery
					(actId.toString());
			List<ActivityLottery.LotteryPrize> lotteryPrizeList =
					activityLottery.getLotteryPrizeList();
			ActivityLottery.LotteryPrize lotteryPrize = null;
			if (CollectionUtils.isNotEmpty(lotteryPrizeList)){
				Collections.sort(lotteryPrizeList, new Comparator<ActivityLottery.LotteryPrize>
						() {
					//按集合中的奖品概率升序排列
					@Override
					public int compare(ActivityLottery.LotteryPrize o1,
									   ActivityLottery.LotteryPrize o2) {
						if(o1.getPrizeRate() > o2.getPrizeRate()){
							return 1;
						}
						if(o1.getPrizeRate().equals(o2.getPrizeRate())){
							return 0;
						}
						return -1;
					}
				});
				lotteryPrize = LotteryUtil.lottery(lotteryPrizeList);
			}
			joinActivity.setAward(lotteryPrize.getPrizeName());
			joinActivity.setStatus(5);
			switch (lotteryPrize.getPrizeName()){
				case "2元红包":
					joinActivity.setPictureUrl("");
					break;
				case "5元红包":
					joinActivity.setPictureUrl("");
					break;
				default:
			}
			joinActivityDao.updateJoinActivityById(joinActivity);
			return joinActivity;
		} catch (Exception e) {
			logger.error("lottery error",e);
			throw new ServiceException(ResponseEnum.DATEBASE_SAVE_ERROR);
		}
	}
}