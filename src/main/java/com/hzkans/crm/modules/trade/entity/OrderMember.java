package com.hzkans.crm.modules.trade.entity;

import com.hzkans.crm.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 会员信息表Entity
 * @author jc
 * @version 2018-11-27
 */
public class OrderMember extends DataEntity<OrderMember> {
	
	private static final long serialVersionUID = 1L;
	private String nickName;		// 所属订单会员id
	private String mobile;		// 手机号
	private String membershipLevel;		// 会员等级
	private String memberName;		// 会员姓名
	private String email;		// email
	private String provinceName;		// 区行政编号
	private String areaName;		// area_name
	private String cityName;		// 乡镇行政编号
	private String address;		// 详细地址
	private  String attention_wechat; // 关注的微信号, 1,2,3 格式
	private String wechatNo;


	public String getWechatNo() {
		return wechatNo;
	}

	public void setWechatNo(String wechatNo) {
		this.wechatNo = wechatNo;
	}

	public String getAttention_wechat() {
		return attention_wechat;
	}

	public void setAttention_wechat(String attention_wechat) {
		this.attention_wechat = attention_wechat;
	}

	public OrderMember() {
		super();
	}

	public OrderMember(String id){
		super(id);
	}

	@Length(min=1, max=64, message="所属订单会员id长度必须介于 1 和 64 之间")
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	@Length(min=1, max=16, message="手机号长度必须介于 1 和 16 之间")
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@Length(min=1, max=32, message="会员等级长度必须介于 1 和 32 之间")
	public String getMembershipLevel() {
		return membershipLevel;
	}

	public void setMembershipLevel(String membershipLevel) {
		this.membershipLevel = membershipLevel;
	}
	
	@Length(min=1, max=64, message="会员姓名长度必须介于 1 和 64 之间")
	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	
	@Length(min=0, max=100, message="email长度必须介于 0 和 100 之间")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Length(min=1, max=64, message="区行政编号长度必须介于 1 和 64 之间")
	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	
	@Length(min=0, max=64, message="area_name长度必须介于 0 和 64 之间")
	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	
	@Length(min=0, max=64, message="乡镇行政编号长度必须介于 0 和 64 之间")
	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
	@Length(min=1, max=255, message="详细地址长度必须介于 1 和 255 之间")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
}