package com.hzkans.crm.modules.trade.constants;

/**
 * @author jc
 * @description
 * @create 2018/11/26
 */
public enum  TableFlowStatusEnum {


    IMPORT_SYSTEM_SUCCESS(1,"校验中"),
    IMPORT_SYSTEM_FAIL(2,"校验失败"),
    TIMING_SUCCESS(3,"待发布"),
    TIMING_FAIL(4,"定时导入失败"),
    ENSURE_TABLE_SUCCESS(5, "发布成功"),
    ENSURE_TABLE_FAIL(6, "发布失败");


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
