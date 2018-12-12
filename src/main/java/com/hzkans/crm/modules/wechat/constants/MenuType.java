package com.hzkans.crm.modules.wechat.constants;

/**
 * @author jc
 * @description 微信菜单类型
 * @create 2018/12/11
 */
public enum MenuType {

    MENU(1, "menu"),
    CLICK(2, "click"),
    VIEW(3, "view")
    ;

    private Integer code;

    private String desc;

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    private MenuType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
