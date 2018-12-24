package com.hzkans.crm.modules.activity.constants;


/**
 * 活动类型枚举类
 * @author wsh
 * @description
 * @create 2018/12/24
 */
public enum ActivityTypeEnum {


    FAVOUR(1,"好评有礼"),
    LOTTERY(2,"幸运抽奖"),
    ;

    private Integer code;
    private String desc;

    ActivityTypeEnum(Integer code, String desc){
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

    public static ActivityTypeEnum getActivityStatusEnum(Integer code) throws Exception{
        if(null == code) {
            throw new Exception("desc is null");
        }
        for (ActivityTypeEnum typeEnum : values()) {
            if(code.equals(typeEnum.code)) {
                return typeEnum;
            }
        }
        return null;
    }
}
