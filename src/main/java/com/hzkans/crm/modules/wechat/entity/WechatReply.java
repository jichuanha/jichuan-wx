/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.hzkans.crm.modules.wechat.entity;

import javax.validation.constraints.NotNull;

import com.hzkans.crm.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;
import java.util.Date;


/**
 * 微信公众号自动回复Entity
 * @author dtm
 * @version 2018-12-05
 */
public class WechatReply extends DataEntity<WechatReply> {
	
	private static final long serialVersionUID = 1L;
	private Integer unOpen;		// 开启：0-开启 1.关闭
	private Integer replyType;		// 类型 0.关键词回复 1-收到消息回复  2-被关注回复
	private Integer contentType;		// 回复内容类型0.文字回复1.图文回复 2-语音 3-视频
	private Integer keyType;		// 关键字类型1-半匹配 2 -全匹配
	private String keywords;		// 关键字
	private String replyDesc;		// 回复消息内容
	private Integer replyWay;		// 1-全部回复 2-随机回复一条 关键字回复不能为空
	private String creator;		// 创建者
	private String updator;		// 修改者
	private Integer deleted;		// 0.存在1.删除
	private Integer wechatId;		// wechat_id
	private Date beginCreateDate;		// 开始 创建时间
	private Date endCreateDate;		// 结束 创建时间

	public WechatReply() {
		super();
	}

	public WechatReply(String id){
		super(id);
	}

	public Integer getUnOpen() {
		return unOpen;
	}

	public void setUnOpen(Integer unOpen) {
		this.unOpen = unOpen;
	}

	public Integer getReplyType() {
		return replyType;
	}

	public void setReplyType(Integer replyType) {
		this.replyType = replyType;
	}


	public Integer getContentType() {
		return contentType;
	}

	public void setContentType(Integer contentType) {
		this.contentType = contentType;
	}

	public Integer getKeyType() {
		return keyType;
	}

	public void setKeyType(Integer keyType) {
		this.keyType = keyType;
	}

	@Length(min=1, max=30, message="关键字长度必须介于 1 和 30 之间")
	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getReplyDesc() {
		return replyDesc;
	}

	public void setReplyDesc(String replyDesc) {
		this.replyDesc = replyDesc;
	}

	public Integer getReplyWay() {
		return replyWay;
	}

	public void setReplyWay(Integer replyWay) {
		this.replyWay = replyWay;
	}

	@Length(min=0, max=64, message="创建者长度必须介于 0 和 64 之间")
	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Length(min=0, max=64, message="修改者长度必须介于 0 和 64 之间")
	public String getUpdator() {
		return updator;
	}

	public void setUpdator(String updator) {
		this.updator = updator;
	}

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	@NotNull(message="wechat_id不能为空")
	public Integer getWechatId() {
		return wechatId;
	}

	public void setWechatId(Integer wechatId) {
		this.wechatId = wechatId;
	}

	public Date getBeginCreateDate() {
		return beginCreateDate;
	}

	public void setBeginCreateDate(Date beginCreateDate) {
		this.beginCreateDate = beginCreateDate;
	}

	public Date getEndCreateDate() {
		return endCreateDate;
	}

	public void setEndCreateDate(Date endCreateDate) {
		this.endCreateDate = endCreateDate;
	}

}