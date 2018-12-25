package com.hzkans.crm.modules.wxapi.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.hzkans.crm.common.utils.JsonUtil;
import com.hzkans.crm.modules.wechat.constants.ReplyTypeEnum;
import com.hzkans.crm.modules.wechat.entity.MemberAssociation;
import com.hzkans.crm.modules.wechat.entity.WechatMaterial;
import com.hzkans.crm.modules.wechat.entity.WechatReplyNew;
import com.hzkans.crm.modules.wechat.service.GetWxReplyMaterialService;
import com.hzkans.crm.modules.wechat.service.MemberAssociationService;
import com.hzkans.crm.modules.wxapi.constants.EventMsgTypeEnum;
import com.hzkans.crm.modules.wxapi.service.BaseApiObserver;
import com.hzkans.crm.modules.wxapi.utils.WechatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description: 微信事件消息处理实现类
 * @Author: lizg
 * @CreateDate: 2018/12/19
 * @Version: 1.0
 */

public class WxEventMsgStepImpl implements BaseApiObserver {

    private static final Logger logger = LoggerFactory.getLogger(WxEventMsgStepImpl.class);

    private GetWxReplyMaterialService getWxReplyMaterialService;

    private MemberAssociationService memberAssociationService;

    public WxEventMsgStepImpl(GetWxReplyMaterialService getWxReplyMaterialService,
                              MemberAssociationService memberAssociationService) {
        this.getWxReplyMaterialService = getWxReplyMaterialService;
        this.memberAssociationService = memberAssociationService;
    }

    @Override
    public String executeStep(Map<String, String> requestMap) {

        String resultXml = "";
        String event = requestMap.get("Event");
        logger.info("[{}] event:{}", event);
        String wechatNo = requestMap.get("ToUserName");
        Long wechatId = Long.parseLong(requestMap.get("wechatId"));
        String openId = requestMap.get("FromUserName");
        switch (EventMsgTypeEnum.getByCode(event)) {
            case EVENT_TYPE_SUBSCRIBE:
                resultXml = dealSubscribeMsg(openId, wechatId, wechatNo);
                break;
            case EVENT_TYPE_UNSUBSCRIBE:
                dealUnSubscribeMsg(openId, wechatNo);
                break;
            case EVENT_TYPE_CLICK:
                String eventKey = requestMap.get("EventKey");
                //根据关键字获取主表id,再根据主表id获取素材内容
                resultXml = getWxReplyMaterialService.keyWordDeal(wechatId, eventKey, wechatNo, openId);
                break;
            default:
                break;
        }

        return resultXml;
    }

    private String dealSubscribeMsg(String openId, Long wechatId, String wechatNo) {

        String resultXml = "";

        //保存关注人的信息
        MemberAssociation association = new MemberAssociation();
        association.setOpenId(openId);
        association.setWechatId(wechatNo);
        association.setDeleted(0);
        association.setCreateDate(new Date());
        association.setUpdateDate(new Date());
        memberAssociationService.save(association);

        //获取主表id,然后根据主表id获取素材内容
        WechatReplyNew replyNew = new WechatReplyNew();
        replyNew.setWechatId(wechatId);
        replyNew.setRuleType(ReplyTypeEnum.RECEIVED.getCode());
        List<WechatMaterial> wechatMaterialList = getWxReplyMaterialService.getAttentionMaterial(replyNew);
        logger.info("wechatMaterialList:" + JsonUtil.toJson(wechatMaterialList));
        if (CollectionUtils.isEmpty(wechatMaterialList)) {
            return resultXml;
        }
        //根据素材的类型决定回复方法
        resultXml = WechatUtils.dealType(wechatMaterialList.get(0), wechatNo, openId);
        logger.info("resultXML {}",resultXml);
        return resultXml;

    }

    private void dealUnSubscribeMsg(String openId, String wechatNo) {
        MemberAssociation ass = new MemberAssociation();
        ass.setOpenId(openId);
        ass.setWechatId(wechatNo);
        ass.setDeleted(1);
        memberAssociationService.save(ass);
    }
}
