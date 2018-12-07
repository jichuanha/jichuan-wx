/**
 * Project Name:content-service-iface
 * File Name:MenuLvlEnum.java
 * Package Name:com.hzkans.leaf.content.constants
 * Date:2017年2月27日上午10:14:07
 *
 */

package com.hzkans.crm.modules.wechat.constants;

/**
 * ClassName:MenuLvlEnum Function: TODO ADD FUNCTION. Reason: TODO ADD REASON.
 * Date: 2017年2月27日 上午10:14:07
 * 
 * @author ly
 * @see
 */
public enum MenuLvlEnum {

	FIRST_LVL(1, "一级菜单"),

	SECOND_LVL(2, "二级菜单");

	private Integer code;

	private String desc;

	public Integer getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}

	private MenuLvlEnum(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}

}
