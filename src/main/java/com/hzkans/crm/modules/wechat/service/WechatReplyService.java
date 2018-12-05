/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.hzkans.crm.modules.wechat.service;

import java.util.List;

import com.hzkans.crm.common.persistence.Page;
import com.hzkans.crm.common.service.CrudService;
import com.hzkans.crm.modules.wechat.entity.WechatReply;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hzkans.crm.modules.wechat.dao.WechatReplyDao;

/**
 * 微信公众号自动回复Service
 * @author dtm
 * @version 2018-12-05
 */
@Service
@Transactional(readOnly = true)
public class WechatReplyService extends CrudService<WechatReplyDao, WechatReply> {
	@Autowired
	private WechatReplyDao wechatNewReplyDao;

	public WechatReply get(String id) {
		return super.get(id);
	}
	
	public List<WechatReply> findList(WechatReply reply) {
		return super.findList(reply);
	}
	
	public Page<WechatReply> findPage(Page<WechatReply> page, WechatReply reply) {
		return super.findPage(page, reply);
	}
	
	@Transactional(readOnly = false)
	public void save(WechatReply reply) {

		WechatReply inputReply = new WechatReply();
		inputReply.setKeywords(reply.getKeywords());
		List<WechatReply> wechatNewReplies= wechatNewReplyDao.findList(inputReply);
		if (CollectionUtils.isNotEmpty(wechatNewReplies)){
		}
		super.save(reply);
	}
	
	@Transactional(readOnly = false)
	public void delete(WechatReply reply) {
		super.delete(reply);
	}

	public WechatReply getReplyByKeywords(String keywords){
		WechatReply inputReply = new WechatReply();
		inputReply.setKeywords(keywords);
		List<WechatReply> wechatNewReplies= wechatNewReplyDao.findList(inputReply);
		if (CollectionUtils.isEmpty(wechatNewReplies)){

		}
		return new WechatReply();
	}
	
}