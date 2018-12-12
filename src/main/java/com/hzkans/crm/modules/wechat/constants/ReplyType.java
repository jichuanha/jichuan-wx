package com.hzkans.crm.modules.wechat.constants;

/**
 * @author jc
 * @description 微信菜单类型
 * @create 2018/12/11
 */
public enum ReplyType {

    KEYWORD(1, "KEYWORD"),
    FOLLOW(2, "FOLLOW"),
    RECEIVED(3, "RECEIVED")
    ;

    private Integer code;

    private String desc;

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    private ReplyType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
