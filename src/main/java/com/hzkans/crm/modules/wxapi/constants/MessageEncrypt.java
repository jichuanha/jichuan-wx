package com.hzkans.crm.modules.wxapi.constants;

import java.io.Serializable;

/**
 * @author jc
 * @description
 * @create 2019/1/7
 */
public class MessageEncrypt implements Serializable{

    private static final long serialVersionUID = 1L;

    private String AppId;
    private String CreateTime;
    private String InfoType;
    private String ComponentVerifyTicket;


    public String getAppId() {
        return AppId;
    }

    public void setAppId(String appId) {
        AppId = appId;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getInfoType() {
        return InfoType;
    }

    public void setInfoType(String infoType) {
        InfoType = infoType;
    }

    public String getComponentVerifyTicket() {
        return ComponentVerifyTicket;
    }

    public void setComponentVerifyTicket(String componentVerifyTicket) {
        ComponentVerifyTicket = componentVerifyTicket;
    }
}
