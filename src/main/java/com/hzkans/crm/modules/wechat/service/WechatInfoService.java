package com.hzkans.crm.modules.wechat.service;

import com.hzkans.crm.common.service.ServiceException;
import com.hzkans.crm.modules.wechat.constants.ReplyTypeEnum;
import com.hzkans.crm.modules.wechat.entity.*;
import com.hzkans.crm.modules.wechat.message.*;
import org.springframework.transaction.annotation.Transactional;
import com.hzkans.crm.common.utils.JsonUtil;
import com.hzkans.crm.modules.wechat.constants.MessageTypeEnum;
import com.hzkans.crm.modules.wechat.utils.MessageUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    @Autowired
    private WechatReplyService wechatReplyService;

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
        try {
            //根据wechatNo获取对应的数据库id
            WechatPlatfrom platfrom = new WechatPlatfrom();
            platfrom.setWechatNo(wechatNo);
            WechatPlatfrom wechatPlatform = wechatPlatfromService.getWechatPlatform(platfrom);
            Long wechatId = wechatPlatform.getId();
            //如果是事件推送
            if(MessageTypeEnum.EVENT.getCode().equals(msgType)) {
                String event = requestMap.get("Event");

                switch (event) {
                    //关注事件
                    case EventMessage.EVENT_TYPE_SUBSCRIBE :
                        MemberAssociation association = new MemberAssociation();
                        association.setOpenId(openId);
                        association.setWechatId(wechatNo);
                        association.setDeleted(0);
                        association.setCreateDate(new Date());
                        association.setUpdateDate(new Date());
                        memberAssociationService.save(association);
                        //获取回复内容
                        WechatReplyNew aNew = new WechatReplyNew();
                        aNew.setWechatId(wechatId);
                        aNew.setRuleType(ReplyTypeEnum.RECEIVED.getCode());
                        //获取主表id,然后根据主表id获取素材内容
                        WechatReplyNew replyNew = new WechatReplyNew();
                        replyNew.setWechatId(wechatId);
                        replyNew.setRuleType(ReplyTypeEnum.RECEIVED.getCode());
                        List<WechatReplyNew> wechatReplys = wechatReplyService.getWechatReplys(replyNew);
                        if(CollectionUtils.isEmpty(wechatReplys)) {
                            break;
                        }
                        //关注回复只会有一条规则
                        WechatReplyNew replyNew1 = wechatReplys.get(0);
                        List<WechatMaterial> matetials = wechatMaterialService.getMatetialByRuleType(replyNew1);
                        logger.info(" matetials {}",JsonUtil.toJson(matetials));
                        //根据素材的类型决定回复方法
                        resultXml = dealType(matetials.get(0), wechatNo, openId);
                        break;
                    //取消关注
                    case EventMessage.EVENT_TYPE_UNSUBSCRIBE :
                        MemberAssociation ass = new MemberAssociation();
                        ass.setOpenId(openId);
                        ass.setWechatId(wechatNo);
                        ass.setDeleted(1);
                        memberAssociationService.save(ass);
                        break;

                    case EventMessage.EVENT_TYPE_CLICK :
                        String eventKey = requestMap.get("EventKey");
                        //根据关键字获取主表id,再根据主表id获取素材内容
                        WechatReplyKeyword keyword = new WechatReplyKeyword();
                        keyword.setKeyword(eventKey);
                        keyword.setWechatId(wechatId);
                        List<WechatReplyNew> keyWords = wechatReplyService.getWechatReplyByKeyWord(keyword);
                        if(CollectionUtils.isEmpty(keyWords)) {
                            break;
                        }
                        //如果关键字对应多个规则,只选取最近添加的一条
                        WechatReplyNew aNew1 = keyWords.get(0);
                        List<WechatMaterial> matetial = wechatMaterialService.getMatetialByRuleType(aNew1);
                        logger.info(" matetials {}",JsonUtil.toJson(matetial));
                        break;

                }
            }
        } catch (ServiceException e) {
            e.printStackTrace();
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
                newsMessage.setArticleCount("1");
                List<Article> articles = new ArrayList<>();
                Article article = new Article();
                article.setPicUrl(wechatMaterial.getCoverPicture());
                article.setTitle(wechatMaterial.getTitle());
                article.setDescription(wechatMaterial.getBrief());
                article.setUrl(wechatMaterial.getUri());
                newsMessage.setArticles(articles);
                logger.info(" voiceMessage {}",JsonUtil.toJson(newsMessage));
                return MessageUtil.messageToXml(newsMessage);
            default:
                return "";
        }


    }

}
