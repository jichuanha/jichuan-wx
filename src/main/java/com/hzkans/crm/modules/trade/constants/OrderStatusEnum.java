package com.hzkans.crm.modules.trade.constants;

/**
 * @author jc
 * @description
 * @create 2018/12/4
 */
public enum  OrderStatusEnum {
    ORDER_TO_AUDIT(0, "订单待审核"),
    ORDER_LIST(1, "订单审核成功"),
    ORDER_AUDIT(2, "订单审核失败");

    private Integer code;
    private String desc;


    OrderStatusEnum(Integer code, String desc) {
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
