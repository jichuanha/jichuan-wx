package com.hzkans.crm.modules.activity.constants;

/**
 * 活动状态类型枚举类
 * @author wsh
 * @description
 * @create 2018/11/29
 */
public enum ActivityStatusTypeEnum {


    PAUSE(1,"暂停"),
    GO_ON(2,"继续"),
    CANCEL(3,"取消");

    private Integer code;
    private String desc;

    ActivityStatusTypeEnum(Integer code, String desc){
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
