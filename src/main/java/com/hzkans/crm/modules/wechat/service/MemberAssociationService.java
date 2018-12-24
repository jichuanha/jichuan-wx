package com.hzkans.crm.modules.wechat.service;



import com.hzkans.crm.common.constant.ResponseEnum;
import com.hzkans.crm.common.service.ServiceException;
import com.hzkans.crm.modules.trade.utils.TradeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hzkans.crm.modules.wechat.entity.MemberAssociation;
import com.hzkans.crm.modules.wechat.dao.MemberAssociationDao;

import java.util.List;

/**
 * 微信关注Service
 * @author jc
 * @version 2018-12-11
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MemberAssociationService{

	private Logger logger = LoggerFactory.getLogger(MemberAssociationService.class);

	@Autowired
	private MemberAssociationDao memberAssociationDao;

	/**
	 * 保存关注信息
	 * @param memberAssociation
	 */
	public void save(MemberAssociation memberAssociation) {
		TradeUtil.isAllNull(memberAssociation);
		try {
			MemberAssociation association = memberAssociationDao.get(memberAssociation);
			if(null == association) {
                memberAssociationDao.insert(memberAssociation);
            }else {
				memberAssociationDao.update(memberAssociation);
            }
		} catch (Exception e) {
			logger.error("save error",e);
			throw new ServiceException(ResponseEnum.DATEBASE_QUERY_ERROR);
		}

	}

	/**
	 * 根据条件获取信息(通用查)
	 * @param memberAssociation
	 * @return
	 */
	public List<MemberAssociation> getMessageAttentionInfo(MemberAssociation memberAssociation) throws ServiceException{
		TradeUtil.isAllNull(memberAssociation);
		List<MemberAssociation> memberAssociations = null;
		try {
			memberAssociations = memberAssociationDao.selectMembers(memberAssociation);
		} catch (Exception e) {
			logger.error("getMessageAttentionInfo error",e);
			throw new ServiceException(ResponseEnum.DATEBASE_QUERY_ERROR);
		}

		return memberAssociations;
	}

	/**
	 * 根据openId绑定手机号
	 * @param memberAssociation
	 */
	public void boundMobile(MemberAssociation memberAssociation) {
		TradeUtil.isAllNull(memberAssociation);
		try {
			memberAssociationDao.update(memberAssociation);
		} catch (Exception e) {
			logger.error("boundMobile error",e);
			throw new ServiceException(ResponseEnum.DATEBASE_SAVE_ERROR);
		}
	}

}