package com.hzkans.crm.modules.wxapi.constants;

import com.hzkans.crm.common.utils.StringUtils;

public enum EventMsgTypeEnum {


    EVENT_TYPE_SUBSCRIBE("subscribe","订阅"),

    EVENT_TYPE_UNSUBSCRIBE("unsubscribe","取消订阅"),

    EVENT_TYPE_SCAN("SCAN","用户已关注时的事件推送"),

    EVENT_TYPE_CLICK("CLICK","自定义菜单 CLICK"),

    EVENT_TYPE_VIEW("VIEW","自定义菜单 VIEW");

    private String code;
    private String desc;

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    EventMsgTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static EventMsgTypeEnum getByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        for (EventMsgTypeEnum ie : values()) {
            if (ie.getCode().equals(code)) {
                return ie;
            }
        }
        return null;
    }
}
