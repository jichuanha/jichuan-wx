package com.hzkans.crm.modules.wechat.message;

public class BaseMessage {

    /**发送方帐号（一个OpenID）*/
    private String ToUserName;

    /**开发者微信号 */
    private String FromUserName;

    /** 消息创建时间 （整型）*/
    private Long CreateTime;

    /** 消息创建时间 （整型）*/
    private String MsgType;

    /**消息id，64位整型*/
    private Long MsgId;

    public String getToUserName() {
        return ToUserName;
    }

    public void setToUserName(String toUserName) {
        ToUserName = toUserName;
    }

    public String getFromUserName() {
        return FromUserName;
    }

    public void setFromUserName(String fromUserName) {
        FromUserName = fromUserName;
    }

    public Long getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(Long createTime) {
        CreateTime = createTime;
    }

    public String getMsgType() {
        return MsgType;
    }

    public void setMsgType(String msgType) {
        MsgType = msgType;
    }

    public Long getMsgId() {
        return MsgId;
    }

    public void setMsgId(Long msgId) {
        MsgId = msgId;
    }
}
