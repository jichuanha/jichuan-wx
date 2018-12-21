package com.hzkans.crm.modules.wxapi.service.impl;

import com.hzkans.crm.common.utils.CacheUtils;
import com.hzkans.crm.common.utils.JsonUtil;
import com.hzkans.crm.modules.wechat.entity.MessageRecord;
import com.hzkans.crm.modules.wechat.service.GetWxReplyMaterialService;
import com.hzkans.crm.modules.wxapi.constants.WechatCofig;
import com.hzkans.crm.modules.wxapi.service.BaseApiObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @Description: 文本内容消息回复实现类
 * @Author: lizg
 * @CreateDate:
 * @Version: 1.0
 */
public class WxTextMsgStepImpl implements BaseApiObserver {

    private static final Logger logger = LoggerFactory.getLogger(WxTextMsgStepImpl.class);

    private GetWxReplyMaterialService getWxReplyMaterialService;

    public WxTextMsgStepImpl(GetWxReplyMaterialService getWxReplyMaterialService) {
        this.getWxReplyMaterialService = getWxReplyMaterialService;

    }

    @Override
    public String executeStep(Map<String, String> requestMap) {

        String resultXml = "";

        String msgType = requestMap.get("MsgType");
        String wechatNo = requestMap.get("ToUserName");
        String wechatId = requestMap.get("wechatId");
        String openId = requestMap.get("FromUserName");
        String msgId = requestMap.get("MsgId");
        String mediaId = requestMap.get("MediaId");
        String content = requestMap.get("Content");

        //判断该回复是否已经处理过
        MessageRecord messageRecord = (MessageRecord) CacheUtils.get(WechatCofig.EHCACHE, msgId);
        logger.info("[{}] messageRecord:{}", JsonUtil.toJson(messageRecord));
        if (null != messageRecord) {
            return resultXml;
        }

        MessageRecord record = new MessageRecord();
        record.setMsgId(msgId);
        record.setMsgType(msgType);
        record.setContent(content);
        record.setToUserName(wechatNo);
        record.setFromUserName(openId);
        record.setMediaId(mediaId);

        CacheUtils.put(WechatCofig.EHCACHE, msgId, record);
        try {
            resultXml = getWxReplyMaterialService.keyWordDeal(Long.parseLong(wechatId), content, wechatNo, openId);
        } catch (Exception e) {
            logger.error("text keyWordDeal is error:", e);
        }

        return resultXml;
    }
}
