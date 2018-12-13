package com.hzkans.crm.modules.wechat.service;

import com.hzkans.crm.modules.wechat.constants.ReplyTypeEnum;
import com.hzkans.crm.modules.wechat.entity.WechatPlatfrom;
import com.hzkans.crm.modules.wechat.message.*;
import org.springframework.transaction.annotation.Transactional;
import com.hzkans.crm.common.utils.JsonUtil;
import com.hzkans.crm.modules.wechat.constants.MessageTypeEnum;
import com.hzkans.crm.modules.wechat.entity.WechatMaterial;
import com.hzkans.crm.modules.wechat.entity.WechatReplyNew;
import com.hzkans.crm.modules.wechat.entity.MemberAssociation;
import com.hzkans.crm.modules.wechat.utils.MessageUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author jc
 * @description 处理微信消息接口
 * @create 2018/12/11
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class WechatInfoService {

    private Logger logger = LoggerFactory.getLogger(WechatInfoService.class);


    @Autowired
    private MemberAssociationService memberAssociationService;
    @Autowired
    private WechatMaterialService wechatMaterialService;
    @Autowired
    private WechatPlatfromService wechatPlatfromService;

    /**
     * 微信消息处理
     * @param requestMap
     * @return
     */
    public String messageDeal(Map<String, String> requestMap) {
        //默认返回参数
        String resultXml = "success";
        logger.info("requestMap {}", JsonUtil.toJson(requestMap));
        String msgType = requestMap.get("MsgType");
        String wechatNo = requestMap.get("ToUserName");
        String openId = requestMap.get("FromUserName");
        String createTime = requestMap.get("CreateTime");

        //如果是事件推送
        if(MessageTypeEnum.EVENT.getCode().equals(msgType)) {
            String event = requestMap.get("Event");

            switch (event) {
                //关注事件
                case EventMessage.EVENT_TYPE_SUBSCRIBE :
                    MemberAssociation association = new MemberAssociation();
                    association.setOpenId(openId);
                    association.setWechatId(wechatNo);
                    association.setCreateDate(new Date());
                    association.setUpdateDate(new Date());
                    memberAssociationService.save(association);
                    //根据wechatNo获取对应的数据库id
                    WechatPlatfrom platfrom = new WechatPlatfrom();
                    platfrom.setWechatNo(wechatNo);
                    WechatPlatfrom wechatPlatform = wechatPlatfromService.getWechatPlatform(platfrom);
                    Long wechatId = wechatPlatform.getId();

                    WechatReplyNew aNew = new WechatReplyNew();
                    aNew.setWechatId(wechatId);
                    aNew.setRuleType(ReplyTypeEnum.RECEIVED.getCode());
                    List<WechatMaterial> matetials = wechatMaterialService.getMatetialByRuleType(aNew);
                    logger.info(" matetials {}",JsonUtil.toJson(matetials));
                    if(CollectionUtils.isEmpty(matetials)) {
                        break;
                    }
                    WechatMaterial wechatMaterial = matetials.get(0);
                    //根据素材的类型决定回复方法
                    resultXml = dealType(wechatMaterial, wechatNo, openId);
                    break;
                //取消关注
                case EventMessage.EVENT_TYPE_UNSUBSCRIBE :

                    break;

                case EventMessage.EVENT_TYPE_CLICK :
                    String eventKey = requestMap.get("EventKey");

                    break;

            }
        }
        logger.info("resultXML  {}",JsonUtil.toJson(resultXml));
        return resultXml;
    }


    private String dealType(WechatMaterial wechatMaterial, String wechatNo, String openId) {
        Integer type = wechatMaterial.getType();
        switch (type) {
            case 0 :
                //文本回复
                ContentMessage message = new ContentMessage();
                message.setToUserName(openId);
                message.setFromUserName(wechatNo);
                message.setCreateTime(System.currentTimeMillis());
                message.setMsgType(MessageTypeEnum.TEXT.getCode());
                message.setContent(wechatMaterial.getContent());
                logger.info(" imageMessage {}",JsonUtil.toJson(message));
                return MessageUtil.messageToXml(message);
            case 1 :
                //图文回复
                ImageMessage imageMessage = new ImageMessage();
                imageMessage.setToUserName(openId);
                imageMessage.setFromUserName(wechatNo);
                imageMessage.setCreateTime(System.currentTimeMillis());
                imageMessage.setMsgType(MessageTypeEnum.IMAGE.getCode());
                Image image = new Image();
                image.setMediaId(wechatMaterial.getMediaId());
                imageMessage.setImage(image);
                logger.info(" imageMessage {}",JsonUtil.toJson(imageMessage));
                return  MessageUtil.messageToXml(imageMessage);
            case 2 :
                //视频回复现在不做
                return "";
            case 3 :
                //语音回复
                VoiceMessage voiceMessage = new VoiceMessage();
                voiceMessage.setToUserName(openId);
                voiceMessage.setFromUserName(wechatNo);
                voiceMessage.setCreateTime(System.currentTimeMillis());
                voiceMessage.setMsgType(MessageTypeEnum.VOICE.getCode());
                Image im = new Image();
                im.setMediaId(wechatMaterial.getMediaId());
                voiceMessage.setVoice(im);
                logger.info(" voiceMessage {}",JsonUtil.toJson(voiceMessage));
                return MessageUtil.messageToXml(voiceMessage);
            case 4 :
                //图文回复
                NewsMessage newsMessage = new NewsMessage();
                newsMessage.setToUserName(openId);
                newsMessage.setFromUserName(wechatNo);
                newsMessage.setCreateTime(System.currentTimeMillis());
                newsMessage.setMsgType(MessageTypeEnum.NEWS.getCode());

                logger.info(" voiceMessage {}",JsonUtil.toJson(newsMessage));
                return MessageUtil.messageToXml(newsMessage);
            default:
                return "";
        }


    }



    /**
     * 文本回复
     * @param wechatMaterial
     * @return
     */
    private String imageMessage(WechatMaterial wechatMaterial, String wechatNo, String openId) {
        ContentMessage message = new ContentMessage();

        message.setToUserName(openId);
        message.setFromUserName(wechatNo);
        message.setCreateTime(System.currentTimeMillis());
        message.setMsgType(MessageTypeEnum.IMAGE.getCode());
        message.setContent(wechatMaterial.getContent());
        logger.info(" imageMessage {}",JsonUtil.toJson(message));
        return MessageUtil.messageToXml(message);
    }


}
