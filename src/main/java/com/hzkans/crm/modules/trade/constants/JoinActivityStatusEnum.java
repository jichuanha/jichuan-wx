package com.hzkans.crm.modules.trade.constants;


/**
 * @author jc
 * @description
 * @create 2018/12/1
 */
public enum  JoinActivityStatusEnum {

    UN_AUDIT(0, "未审核"),
    PERSONAL_AGREE(1, "人工审核同意"),
    PERSONAL_DISAGREE(2, "人工审核不同意"),
    SYS_AGREE(3, "系统自动审核同意"),
    SYS_DISAGREE(4, "系统自动审核不同意");

    private Integer code;
    private String desc;


    JoinActivityStatusEnum(Integer code, String desc) {
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

    public static JoinActivityStatusEnum getJoinActivityStatusEnum(Integer code) throws Exception{
        if(null == code) {
            throw new Exception("desc is null");
        }
        for (JoinActivityStatusEnum typeEnum : values()) {
            if(code.equals(typeEnum.getCode())) {
                return typeEnum;
            }
        }
        return null;
    }
}
