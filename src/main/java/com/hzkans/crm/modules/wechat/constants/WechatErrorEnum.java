package com.hzkans.crm.modules.wechat.constants;

/**
 * Created with IDEA
 * 返回前台错误信息的枚举类
 *
 * @author:dengtm
 * @Date:2018/12/6
 * @Time:15:43
 */
public enum WechatErrorEnum {

    KEYWORDS_ALREADY_EXIST(1, "关键词已存在"),
    KEYWORDS_DOSES_NOT_EXIST(1, "关键词不存在");


    private Integer code;
    private String desc;


    WechatErrorEnum(Integer code, String desc) {
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
