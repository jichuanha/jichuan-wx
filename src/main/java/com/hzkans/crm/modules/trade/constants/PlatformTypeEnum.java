package com.hzkans.crm.modules.trade.constants;

import com.hzkans.crm.common.utils.StringUtils;

/**
 * @author jc
 * @description
 * @create 2018/11/30
 */
public enum PlatformTypeEnum {
    ;


    private Integer code;
    private String desc;


    PlatformTypeEnum(Integer code, String desc) {
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


    public static PlatformTypeEnum getPlatformTypeEnum(Integer code) throws Exception{
        if(null == code) {
            throw new Exception("desc is null");
        }
        for (PlatformTypeEnum typeEnum : values()) {
            if(code.equals(typeEnum.code)) {
                return typeEnum;
            }
        }
        return null;
    }
}
