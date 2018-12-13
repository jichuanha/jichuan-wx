package com.hzkans.crm.modules.wechat.constants;


/**
 * @author jc
 * @description 消息类型
 * @create 2018/12/11
 */
public enum  MessageTypeEnum {
    TEXT(0,"text", "文本"),
    IMAGE(1, "image", "图片"),
    VIDEO(2, "video", "视频"),
    VOICE(3, "voice", "语音"),
    NEWS(4, "news", "图文"),
    EVENT(5, "event", "事件"),
    SHORTVIDEO(6, "shortvideo", "小视频"),
    LINK(7, "link", "链接"),
    MUSIC(8, "music", "音乐"),

    ;

    private Integer sign;
    private String code;
    private String desc;

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public Integer getSign() {
        return sign;
    }

    MessageTypeEnum(Integer sign, String code, String desc) {
        this.sign = sign;
        this.code = code;
        this.desc = desc;
    }

    public static MessageTypeEnum getMessageTypeEnum(Integer sign) {
        if(null == sign) {
            return null;
        }
        for (MessageTypeEnum typeEnum : values()) {
            if(sign.equals(typeEnum.sign)) {
                return typeEnum;
            }
        }
        return null;
    }
}
