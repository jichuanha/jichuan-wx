package com.hzkans.crm.modules.trade.constants;

/**
 * @author jc
 * @description
 * @create 2018/12/3
 */
public enum PageTypeEnum {

    ORDER_LIST(1, "订单列表"),
    ORDER_AUDIT(2, "订单审核");

    private Integer code;
    private String desc;


    PageTypeEnum(Integer code, String desc) {
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
