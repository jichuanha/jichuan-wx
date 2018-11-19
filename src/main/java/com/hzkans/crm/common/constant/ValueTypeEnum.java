package com.hzkans.crm.common.constant;

/**
 * Created by Administrator on 2016/9/22.
 */
public enum  ValueTypeEnum {
            PRODUCT_LIST_HEADER("productListHeader",0),
            IMAGE_BANNER("imageBanner",1),
            PRODUCT("product",2);

    private int code;
    private String valueType;

    ValueTypeEnum(String valueType,int code) {
        this.valueType = valueType;
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }
}
