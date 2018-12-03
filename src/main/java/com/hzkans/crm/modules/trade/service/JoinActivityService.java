package com.hzkans.crm.modules.trade.service;

import com.hzkans.crm.common.constant.ResponseEnum;
import com.hzkans.crm.common.persistence.PagePara;
import com.hzkans.crm.common.service.CrudService;
import com.hzkans.crm.common.service.ServiceException;
import com.hzkans.crm.common.utils.PriceUtil;
import com.hzkans.crm.common.utils.StringUtils;
import com.hzkans.crm.modules.activity.entity.PlatformShop;
import com.hzkans.crm.modules.activity.service.PlatformShopService;
import com.hzkans.crm.modules.trade.constants.AttentionEnum;
import com.hzkans.crm.modules.trade.constants.JoinActivityStatusEnum;
import com.hzkans.crm.modules.trade.entity.Order;
import com.hzkans.crm.modules.trade.entity.OrderMember;
import com.hzkans.crm.modules.trade.utils.TradeUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.hzkans.crm.modules.trade.entity.JoinActivity;
import com.hzkans.crm.modules.trade.dao.JoinActivityDao;

import java.util.List;

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

	/**
	 * 获取参加活动订单信息集合
	 * @param pagePara
	 * @param wechatId
	 * @return
	 */
	public PagePara<JoinActivity> listJoinActivityPage(PagePara<JoinActivity> pagePara, Integer wechatId) {
		TradeUtil.isAllNull(pagePara);
		PagePara<JoinActivity> para = null;
		try {
			para = new PagePara<>();
			List<JoinActivity> joinActivities = joinActivityDao.selectJoinActivityPage(pagePara);
			if(CollectionUtils.isNotEmpty(joinActivities)) {
				for (JoinActivity ja : joinActivities) {
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
					Order order1 = orderService.getPlatformShop(order);
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
						String attentionWechat = member.getAttention_wechat();
						if(StringUtils.isEmpty(attentionWechat)) {
							ja.setAttentionStr(AttentionEnum.ORDER_NOT_BIND.getDesc());
						}else if(attentionWechat.contains(wechatId.toString())) {
                            ja.setAttentionStr(AttentionEnum.ORDER_BIND.getDesc());
                        }else {
                            ja.setAttentionStr(AttentionEnum.ORDER_NOT_BIND.getDesc());
                        }
                    }
                }
			}
			int totalCount = joinActivityDao.selectJoinActivityPageCount(pagePara);
			para.setCount(totalCount);
			para.setList(joinActivities);
		} catch (Exception e) {
			logger.error("JoinActivityService error",e);
			throw new ServiceException(ResponseEnum.DATEBASE_QUERY_ERROR);
		}

		return para;

	}

	/**
	 * 订单审核(同意和拒绝)
	 * @param joinActivity
	 */
	public void auditOrder(JoinActivity joinActivity) throws ServiceException{
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
			joinActivityDao.updateJoinActivityStatus(joinActivity);
		} catch (Exception e) {
			logger.error("auditOrder error",e);
			throw new ServiceException(ResponseEnum.B_E_UPDATE_STATUS_FAIL);
		}

	}
}