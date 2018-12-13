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
    KEYWORDS_DOSES_NOT_EXIST(2, "关键词不存在"),
    CONTENT_IS_NULL(3, "文本内容不能为空"),
    SUSPEND_IS_ERROR(4, "暂停失败"),
    NAME_IS_NOT_NULL(5,"规则名称已存在");


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
