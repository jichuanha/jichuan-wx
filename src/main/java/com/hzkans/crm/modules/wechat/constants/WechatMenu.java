package com.hzkans.crm.modules.wechat.constants;

import java.io.Serializable;
import java.util.List;

/**
 * @author jc
 * @description
 * @create 2018/12/11
 */
public class WechatMenu implements Serializable{
    private static final long serialVersionUID = 1L;

    private String key;
    private String name;
    private List<WechatMenu> sub_button;
    private String type;
    private String url;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<WechatMenu> getSub_button() {
        return sub_button;
    }

    public void setSub_button(List<WechatMenu> sub_button) {
        this.sub_button = sub_button;
    }
}
