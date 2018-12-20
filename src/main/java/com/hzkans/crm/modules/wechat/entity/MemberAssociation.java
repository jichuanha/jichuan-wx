/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.hzkans.crm.modules.wechat.entity;

import org.hibernate.validator.constraints.Length;

import com.hzkans.crm.common.persistence.DataEntity;

/**
 * 微信关注Entity
 * @author jc
 * @version 2018-12-11
 */
public class MemberAssociation extends DataEntity<MemberAssociation> {
	
	private static final long serialVersionUID = 1L;
	private String wechatId;		// wechat_id
	private String openId;		// open_id
	private String nickName;
	private Integer sex;
	private String headUrl;
	private Integer deleted;		// deleted
	private String unionId;		// union_id
	private String sign;		// 0 -未更新  1-已更新
	
	public MemberAssociation() {
		super();
	}

	public MemberAssociation(String id){
		super(id);
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getHeadUrl() {
		return headUrl;
	}

	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}

	public String getWechatId() {
		return wechatId;
	}

	public void setWechatId(String wechatId) {
		this.wechatId = wechatId;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
	
}