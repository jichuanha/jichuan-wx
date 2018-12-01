package com.hzkans.crm.modules.trade.constants;

/**
 * @author jc
 * @description
 * @create 2018/11/26
 */
public enum  TableFlowStatusEnum {


    IMPORT_SYSTEM_SUCCESS(1,"导入系统成功"),
    IMPORT_SYSTEM_FAIL(2,"导入系统失败"),
    TIMING_SUCCESS(3,"定时插入数据库成功"),
    TIMING_FAIL(4,"定时插入数据库失败");

    private Integer code;
    private String desc;

    TableFlowStatusEnum(Integer code, String desc){
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

    public static TableFlowStatusEnum getTableFlowStatusEnum(Integer code) throws Exception{
        if(null == code) {
            throw new Exception("desc is null");
        }
        for (TableFlowStatusEnum typeEnum : values()) {
            if(code.equals(typeEnum.code)) {
                return typeEnum;
            }
        }
        return null;
    }


}
