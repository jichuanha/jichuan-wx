/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.hzkans.crm.modules.wechat.service;

import java.util.List;

import com.hzkans.crm.common.constant.ResponseEnum;
import com.hzkans.crm.common.service.ServiceException;
import com.hzkans.crm.modules.trade.utils.TradeUtil;
import com.hzkans.crm.modules.wechat.entity.MessageRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hzkans.crm.common.persistence.Page;
import com.hzkans.crm.common.service.CrudService;
import com.hzkans.crm.modules.wechat.entity.MemberAssociation;
import com.hzkans.crm.modules.wechat.dao.MemberAssociationDao;

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
	 * 保存顾客发送的消息
	 * @param messageRecord
	 */
	public void saveMessageRecord(MessageRecord messageRecord) {
		TradeUtil.isAllNull(messageRecord);
		try {
			memberAssociationDao.insertMessageRecord(messageRecord);
		} catch (Exception e) {
			logger.error("saveMessageRecord error",e);
			throw new ServiceException(ResponseEnum.DATEBASE_SAVE_ERROR);
		}


	}




}