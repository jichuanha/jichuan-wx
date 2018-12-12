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
	private String deleted;		// deleted
	private String unionId;		// union_id
	private String sign;		// 0 -未更新  1-已更新
	
	public MemberAssociation() {
		super();
	}

	public MemberAssociation(String id){
		super(id);
	}

	@Length(min=0, max=255, message="wechat_id长度必须介于 0 和 255 之间")
	public String getWechatId() {
		return wechatId;
	}

	public void setWechatId(String wechatId) {
		this.wechatId = wechatId;
	}
	
	@Length(min=1, max=255, message="open_id长度必须介于 1 和 255 之间")
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
	@Length(min=1, max=4, message="deleted长度必须介于 1 和 4 之间")
	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
	
	@Length(min=1, max=255, message="union_id长度必须介于 1 和 255 之间")
	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}
	
	@Length(min=1, max=4, message="0 -未更新  1-已更新长度必须介于 1 和 4 之间")
	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
	
}