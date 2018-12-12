package com.hzkans.crm.modules.wechat.constants;

/**
 * @author jc
 * @description 消息类型
 * @create 2018/12/11
 */
public enum  MessageTypeEnum {

    EVENT("event", "事件"),
    TEXT("text", "文本"),
    IMAGE("image", "图片"),
    VOICE("voice", "视频"),
    SHORTVIDEO("shortvideo", "小视频"),
    LINK("link", "链接"),
    MUSIC("music", "音乐"),
    NEWS("news", "图文"),
    ;

    private String code;

    private String desc;

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    MessageTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
