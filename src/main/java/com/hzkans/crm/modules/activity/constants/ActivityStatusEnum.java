package com.hzkans.crm.modules.activity.constants;

/**
 * 活动状态枚举类
 * @author wsh
 * @description
 * @create 2018/11/29
 */
public enum ActivityStatusEnum {


    NOT_START(0,"未开始"),
    ACTIVING(1,"进行中"),
    PAUSE(2,"暂停中"),
    ENDED(3,"已结束");

    private Integer code;
    private String desc;

    ActivityStatusEnum(Integer code, String desc){
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
