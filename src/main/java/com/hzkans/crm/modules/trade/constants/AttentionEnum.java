package com.hzkans.crm.modules.trade.constants;

/**
 * @author jc
 * @description
 * @create 2018/12/3
 */
public enum AttentionEnum {


    ORDER_BIND(1, "绑定"),
    ORDER_NOT_BIND(2, "未绑定");

    private Integer code;
    private String desc;


    AttentionEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
