package com.hzkans.crm.modules.wechat.service;

import com.hzkans.crm.common.utils.DateUtil;
import com.hzkans.crm.common.utils.JsonUtil;
import com.hzkans.crm.modules.wechat.constants.MessageTypeEnum;
import com.hzkans.crm.modules.wechat.entity.EventMessage;
import com.hzkans.crm.modules.wechat.entity.MemberAssociation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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
        String wechatId = requestMap.get("ToUserName");
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
                    association.setWechatId(wechatId);
                    association.setCreateDate(DateUtil.parse(createTime, DateUtil.NORMAL_DATETIME_PATTERN));
                    association.setUpdateDate(new Date());
                    memberAssociationService.save(association);
                   break;
                //取消关注
                case EventMessage.EVENT_TYPE_UNSUBSCRIBE :
                    break;

            }
        }



        return resultXml;
    }


}
