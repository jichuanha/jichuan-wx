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
public class WechatReplyKeywordDO extends DataEntity<WechatReplyKeywordDO> {

	private static final long serialVersionUID = 1L;
	private Long wechatId;		// 微信ID
	private String ruleId;		// 规则ID
	private Integer keywordType;		// 关键词类型： 0-半匹配  1-全匹配
	private String keyword;		// 关键词
	private String creator;		// 创建者
	private String updator;		// 修改者
	private Integer deleted;		// 0.存在1.删除

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

	public Long getWechatId() {
		return wechatId;
	}

	public void setWechatId(Long wechatId) {
		this.wechatId = wechatId;
	}

	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	public Integer getKeywordType() {
		return keywordType;
	}

	public void setKeywordType(Integer keywordType) {
		this.keywordType = keywordType;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
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