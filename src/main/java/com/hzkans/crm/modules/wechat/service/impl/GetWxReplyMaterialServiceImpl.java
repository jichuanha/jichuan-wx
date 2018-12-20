package com.hzkans.crm.modules.wechat.service.impl;

import com.hzkans.crm.common.constant.ResponseEnum;
import com.hzkans.crm.common.service.ServiceException;
import com.hzkans.crm.common.utils.JsonUtil;
import com.hzkans.crm.modules.trade.utils.TradeUtil;
import com.hzkans.crm.modules.wechat.entity.WechatMaterial;
import com.hzkans.crm.modules.wechat.entity.WechatReplyKeyword;
import com.hzkans.crm.modules.wechat.entity.WechatReplyNew;
import com.hzkans.crm.modules.wechat.service.GetWxReplyMaterialService;
import com.hzkans.crm.modules.wechat.service.WechatMaterialService;
import com.hzkans.crm.modules.wechat.service.WechatReplyService;
import com.hzkans.crm.modules.wxapi.utils.WechatUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetWxReplyMaterialServiceImpl implements GetWxReplyMaterialService {

    private static final Logger logger = LoggerFactory.getLogger(GetWxReplyMaterialServiceImpl.class);

    @Autowired
    private WechatMaterialService wechatMaterialService;

    @Autowired
    private WechatReplyService wechatReplyService;

    @Override
    public List<WechatMaterial> getAttentionMaterial(WechatReplyNew wechatReplyNew) {
        TradeUtil.isAllNull(wechatReplyNew);

        List<WechatMaterial> matetials = null;
        try {
            List<WechatReplyNew> wechatReplys = wechatReplyService.getWechatReplys(wechatReplyNew);

            matetials = new ArrayList<>();
            if (CollectionUtils.isEmpty(wechatReplys)) {
                return matetials;
            }

            //关注回复只会有一条规则
            WechatReplyNew replyNew1 = wechatReplys.get(0);
            matetials = wechatMaterialService.getMatetialByRuleType(replyNew1);
        } catch (Exception e) {
            logger.error("getAttentionMaterial error", e);
            throw new ServiceException(ResponseEnum.DATEBASE_QUERY_ERROR);
        }
        return matetials;
    }

    @Override
    public List<WechatMaterial> getKeyWordMaterial(WechatReplyKeyword keyword) throws ServiceException {
        TradeUtil.isAllNull(keyword);
        List<WechatReplyNew> keyWords = null;

        try {
            keyWords = wechatReplyService.getWechatReplyByKeyWord(keyword);
        } catch (Exception e) {
            logger.error("getAttentionMaterial error", e);
            throw new ServiceException(ResponseEnum.DATEBASE_QUERY_ERROR);
        }

        //判断是否存在关键字
        if (CollectionUtils.isEmpty(keyWords)) {
            throw new ServiceException(ResponseEnum.KEYWORD_NO_REPLY_CONTENT.getMsg());
        }

        //如果关键字对应多个规则,只选取最近添加的一条
        WechatReplyNew aNew1 = keyWords.get(0);
        List<WechatMaterial> matetial = null;
        try {
            matetial = wechatMaterialService.getMatetialByRuleType(aNew1);
        } catch (Exception e) {
            logger.error("getAttentionMaterial error", e);
            throw new ServiceException(ResponseEnum.DATEBASE_QUERY_ERROR);
        }
        return matetial;
    }

    @Override
    public String keyWordDeal(Long wechatId, String keyWord, String wechatNo, String openId) throws ServiceException {
        WechatReplyKeyword keyword = new WechatReplyKeyword();
        keyword.setKeyword(keyWord);
        keyword.setWechatId(wechatId);
        List<WechatMaterial> material = getKeyWordMaterial(keyword);
        logger.info(" matetials {}", JsonUtil.toJson(material));
        if(CollectionUtils.isEmpty(material)) {
            return "";
        }
        return WechatUtils.dealType(material.get(0), wechatNo, openId);
    }
}
