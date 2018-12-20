package com.hzkans.crm.modules.trade.constants;

/**
 * @author jc
 * @description
 * @create 2018/12/20
 */
public enum  NeedCodeEnum {

    NEED(1, "需要验证码"),
    NOT_NEED(2, "不需要验证码")

    ;

    private Integer code;
    private String desc;


    NeedCodeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }


    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

}
