package com.hzkans.crm.modules.trade.constants;

import com.hzkans.crm.common.utils.StringUtils;

/**
 * @author jc
 * @description
 * @create 2018/11/27
 */
public enum  TableFlowTypeEnum {

    ORDER_INFO(1, "订单"),
    EVALUATE(2, "评价"),
    CUSTOMER(3, "顾客");


    private Integer code;
    private String desc;

    TableFlowTypeEnum(Integer code, String desc){
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


    public static TableFlowTypeEnum getTableFlowTypeEnum(String desc) throws Exception{
        if(StringUtils.isEmpty(desc)) {
            throw new Exception("desc is null");
        }
        for (TableFlowTypeEnum typeEnum : values()) {
            if(desc.equals(typeEnum.desc)) {
                return typeEnum;
            }
        }
        return null;
    }

}
