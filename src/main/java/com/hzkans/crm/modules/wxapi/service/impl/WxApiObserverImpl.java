package com.hzkans.crm.modules.wxapi.service.impl;

import com.hzkans.crm.common.utils.CacheUtils;
import com.hzkans.crm.modules.wechat.constants.MessageTypeEnum;
import com.hzkans.crm.modules.wechat.entity.WechatPlatfrom;
import com.hzkans.crm.modules.wechat.service.GetWxReplyMaterialService;
import com.hzkans.crm.modules.wechat.service.MemberAssociationService;
import com.hzkans.crm.modules.wechat.service.WechatPlatfromService;
import com.hzkans.crm.modules.wxapi.constants.WechatCofig;
import com.hzkans.crm.modules.wxapi.service.BaseApiObserver;
import com.hzkans.crm.modules.wxapi.service.WxApiObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created with IDEA
 *
 * @author:dengtm
 * @Date:2018/12/17
 * @Time:14:17
 */
@Service
public class WxApiObserverImpl implements WxApiObserver {

    @Autowired
    private WechatPlatfromService wechatPlatfromService;

    @Autowired
    private GetWxReplyMaterialService getWxReplyMaterialService;

    @Autowired
    private MemberAssociationService memberAssociationService;

    private static final Logger logger = LoggerFactory.getLogger(WxApiObserverImpl.class);


    @Override
    public String dealWxMsg(Map<String, String> requestMap) {

        String resultXml = "";

        String wechatNo = requestMap.get("ToUserName");
        logger.info("wechatNo:",wechatNo);
        Long wechatId = (Long) CacheUtils.get(WechatCofig.EHCACHE, wechatNo);
        if (null == wechatId) {

            //根据wechatNo获取对应的数据库id
            WechatPlatfrom platfrom = new WechatPlatfrom();
            platfrom.setWechatNo(wechatNo);
            WechatPlatfrom wechatPlatform = wechatPlatfromService.getWechatPlatform(platfrom);
            if (null == wechatPlatform) {
                logger.info("get wechat plat info is null,wechatNo:{}",wechatNo);
                return resultXml;
            }
            wechatId = wechatPlatform.getId();
            CacheUtils.put(WechatCofig.EHCACHE, wechatNo, wechatId);
        }

        requestMap.put("wechatId",String.valueOf(wechatId));

        String msgType = requestMap.get("MsgType");

        //事件消息
        if(MessageTypeEnum.EVENT.getCode().equals(msgType)) {
            BaseApiObserver wxEventMsgStep = new WxEventMsgStepImpl(getWxReplyMaterialService,memberAssociationService);
            resultXml = wxEventMsgStep.executeStep(requestMap);
        }

        //普通消息
        if (MessageTypeEnum.TEXT.getCode().equals(msgType)) {
            BaseApiObserver wxTextMsgStep = new WxTextMsgStepImpl(getWxReplyMaterialService);
            resultXml = wxTextMsgStep.executeStep(requestMap);
        }
        return resultXml;
    }

}
