/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.hzkans.crm.modules.wechat.entity;

import com.hzkans.crm.common.persistence.DataEntity;

import java.util.List;


/**
 * 微信公众号自动回复Entity
 * @author dtm
 * @version 2018-12-05
 */
public class WechatReplyContentDO extends DataEntity<WechatReplyContentDO> {

	private static final long serialVersionUID = 1L;
	private Integer wechatId;		// 微信ID
	private Integer ruleId;		// 规则ID
	private String creator;		// 创建者
	private String updator;		// 修改者
	private Integer deleted;		// 0.存在1.删除
	private Integer contentType;		// 回复消息内容类型：0.文字回复1.图片 2-语音 3-视频 4-图文 5-自定义
	private String content;		// 回复消息内容

	List<WechatMaterial> wechatMaterials;

	List<Keyword> keywords;

	private class Keyword{
		private String type;
		private String keyWord;

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getKeyWord() {
			return keyWord;
		}

		public void setKeyWord(String keyWord) {
			this.keyWord = keyWord;
		}
	}

	public Integer getWechatId() {
		return wechatId;
	}

	public void setWechatId(Integer wechatId) {
		this.wechatId = wechatId;
	}

	public Integer getRuleId() {
		return ruleId;
	}

	public void setRuleId(Integer ruleId) {
		this.ruleId = ruleId;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

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

	public Integer getContentType() {
		return contentType;
	}

	public void setContentType(Integer contentType) {
		this.contentType = contentType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<WechatMaterial> getWechatMaterials() {
		return wechatMaterials;
	}

	public void setWechatMaterials(List<WechatMaterial> wechatMaterials) {
		this.wechatMaterials = wechatMaterials;
	}

	public List<Keyword> getKeywords() {
		return keywords;
	}

	public void setKeywords(List<Keyword> keywords) {
		this.keywords = keywords;
	}
}