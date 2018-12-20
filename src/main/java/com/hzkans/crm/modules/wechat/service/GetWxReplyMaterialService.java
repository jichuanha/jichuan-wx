package com.hzkans.crm.modules.wechat.service;

import com.hzkans.crm.common.service.ServiceException;
import com.hzkans.crm.modules.wechat.entity.WechatMaterial;
import com.hzkans.crm.modules.wechat.entity.WechatReplyKeyword;
import com.hzkans.crm.modules.wechat.entity.WechatReplyNew;

import java.util.List;

/**

 * @Description:    获取回复消息素材 接口
 * @Author:         lizg

 * @CreateDate: 2018/12/19

 * @Version:        1.0

 */
public interface GetWxReplyMaterialService {

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

    /**
     * 关键字处理
     * @param wechatId
     * @param keyWord
     * @param wechatNo
     * @param openId
     * @return
     */
    String keyWordDeal(Long wechatId, String keyWord, String wechatNo, String openId) throws ServiceException;

}
