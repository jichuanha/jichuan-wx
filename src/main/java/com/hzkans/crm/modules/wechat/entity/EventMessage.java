package com.hzkans.crm.modules.wechat.entity;

/**
 * @author jc
 * @description 事件消息
 * @create 2018/12/11
 */
public class EventMessage extends BaseMessage {

    // 事件类型：subscribe(订阅)
    public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";

    // 事件类型：unsubscribe(取消订阅)
    public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";

    // 事件类型：SCAN( 用户已关注时的事件推送)
    public static final String EVENT_TYPE_SCAN = "SCAN";

    // 事件类型：CLICK(自定义菜单)
    public static final String EVENT_TYPE_CLICK = "CLICK";

    // 事件类型：VIEW(自定义菜单)
    public static final String EVENT_TYPE_VIEW = "VIEW";


    private String Event;

    public String getEvent() {
        return Event;
    }

    public void setEvent(String event) {
        Event = event;
    }
}
