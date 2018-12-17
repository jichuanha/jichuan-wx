package com.hzkans.crm.modules.wxapi.impl;

import com.hzkans.crm.common.constant.ResponseEnum;
import com.hzkans.crm.common.service.ServiceException;
import com.hzkans.crm.modules.trade.utils.TradeUtil;
import com.hzkans.crm.modules.wechat.entity.WechatMaterial;
import com.hzkans.crm.modules.wechat.entity.WechatReplyKeyword;
import com.hzkans.crm.modules.wechat.entity.WechatReplyNew;
import com.hzkans.crm.modules.wechat.service.WechatMaterialService;
import com.hzkans.crm.modules.wechat.service.WechatReplyService;
import com.hzkans.crm.modules.wxapi.WxApiObserver;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IDEA
 *
 * @author:dengtm
 * @Date:2018/12/17
 * @Time:14:17
 */
public class WxApiObserverImpl implements WxApiObserver {
    private static final Logger logger = LoggerFactory.getLogger(WxApiObserverImpl.class);

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
            if(CollectionUtils.isEmpty(wechatReplys)) {
                return matetials;
            }

            //关注回复只会有一条规则
            WechatReplyNew replyNew1 = wechatReplys.get(0);
            matetials = wechatMaterialService.getMatetialByRuleType(replyNew1);
        } catch (Exception e) {
            logger.error("getAttentionMaterial error",e);
            throw new ServiceException(ResponseEnum.DATEBASE_QUERY_ERROR);
        }
        return matetials;
    }

    @Override
    public List<WechatMaterial> getAttentionMaterial(WechatReplyKeyword keyword) throws Exception {
        TradeUtil.isAllNull(keyword);
        List<WechatMaterial> wechatMaterials = null;

        try {
            wechatMaterials= wechatMaterialService.listMaterialByRuleId(keyword);
        } catch (Exception e) {
            logger.error("getAttentionMaterial error",e);
        }
        return wechatMaterials;
    }
}
