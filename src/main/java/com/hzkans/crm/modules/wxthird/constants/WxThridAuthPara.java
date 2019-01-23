package com.hzkans.crm.modules.wxthird.constants;

public class WxThridAuthPara {

    private String appid; //公众号的appid
    private String code;
    private String grant_type; // authorization_code
    private String component_appid; // 服务开发方的appid
    private String component_access_token; //服务开发方的access_token


    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getGrant_type() {
        return grant_type;
    }

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }

    public String getComponent_appid() {
        return component_appid;
    }

    public void setComponent_appid(String component_appid) {
        this.component_appid = component_appid;
    }

    public String getComponent_access_token() {
        return component_access_token;
    }

    public void setComponent_access_token(String component_access_token) {
        this.component_access_token = component_access_token;
    }
}
