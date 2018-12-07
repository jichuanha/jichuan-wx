package com.hzkans.crm.modules.wechat.entity;

import java.io.Serializable;

/**
 * Created by lizg on 2017/4/8.
 * 子菜单
 */
public class CustomChildMenuDTO implements Serializable{

    private static final long serialVersionUID = -7310152972882801347L;
    private String name;

    private Integer type; // 菜单类型1.菜单2.按钮3.链接

    private String keywords;

    private String uri;

    private Integer sort;

    private Integer menuLevel;

    private Long parentId;

    private Integer wechatId;

    public Integer getWechatId() {
        return wechatId;
    }

    public void setWechatId(Integer wechatId) {
        this.wechatId = wechatId;
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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}
