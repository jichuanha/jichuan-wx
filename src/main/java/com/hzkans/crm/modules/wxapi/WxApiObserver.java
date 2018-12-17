package com.hzkans.crm.modules.wxapi;


import com.hzkans.crm.common.service.ServiceException;
import com.hzkans.crm.modules.wechat.entity.WechatMaterial;
import com.hzkans.crm.modules.wechat.entity.WechatReplyKeyword;
import com.hzkans.crm.modules.wechat.entity.WechatReplyNew;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author jc
 * @description
 * @create 2018/12/17
 */
@Service
public interface WxApiObserver extends BaseApiObserver {

    /**
     * 获取素材(关注素材参数微信表id <WechatId>,ReplyTypeEnum第三种类型<RuleType> )
     * @param wechatReplyNew
     * @return
     */
    List<WechatMaterial> getAttentionMaterial(WechatReplyNew wechatReplyNew);

    /**
     * 获取关键字素材内容(微信表id <WechatId>, 关键字<Keyword>)
     * @param keyword
     * @return
     */
    List<WechatMaterial> getKeyWordMaterial(WechatReplyKeyword keyword) throws ServiceException;

}
