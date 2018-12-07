package com.hzkans.crm.modules.wechat.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lizg on 2017/4/8. 主菜单
 */
public class CustomMainMenuDTO implements Serializable {

	private static final long serialVersionUID = 7173829209056201139L;
	private String name;

	private Integer type; // 菜单类型1.菜单2.按钮3.链接

	private String keywords;

	private String uri;

	private Integer sort;

	private Integer menuLevel;

	private Long id;

	private Long parentId;

	private List<CustomChildMenuDTO> customChildMenuDTOS;

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getMenuLevel() {
		return menuLevel;
	}

	public void setMenuLevel(Integer menuLevel) {
		this.menuLevel = menuLevel;
	}

	public List<CustomChildMenuDTO> getCustomChildMenuDTOS() {
		return customChildMenuDTOS;
	}

	public void setCustomChildMenuDTOS(
			List<CustomChildMenuDTO> customChildMenuDTOS) {
		this.customChildMenuDTOS = customChildMenuDTOS;
	}
}
