/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.hzkans.crm.modules.wechat.service;

import com.hzkans.crm.common.constant.ResponseEnum;
import com.hzkans.crm.common.persistence.Page;
import com.hzkans.crm.common.service.CrudService;
import com.hzkans.crm.common.utils.JsonUtil;
import com.hzkans.crm.modules.wechat.constants.WechatErrorEnum;
import com.hzkans.crm.modules.wechat.dao.WechatReplyDao;
import com.hzkans.crm.modules.wechat.dao.WechatReplyKeywordDao;
import com.hzkans.crm.modules.wechat.dao.WechatReplyRuleContentDao;
import com.hzkans.crm.modules.wechat.dao.WechatReplyRuleDao;
import com.hzkans.crm.modules.wechat.entity.ReplyDescDTO;
import com.hzkans.crm.modules.wechat.entity.WechatReply;
import com.hzkans.crm.modules.wechat.entity.WechatReplyContentDO;
import com.hzkans.crm.modules.wechat.entity.WechatReplyNew;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 微信公众号自动回复Service
 *
 * @author dtm
 * @version 2018-12-05
 */
@Service
@Transactional(readOnly = true)
public class NewWechatReplyService extends CrudService<WechatReplyDao, WechatReply> {

    @Autowired
    private WechatReplyKeywordDao wechatReplyKeywordDao;

    @Autowired
    private WechatReplyRuleContentDao wechatReplyRuleContentDao;

    @Autowired
    private WechatReplyRuleDao wechatReplyRuleDao;

    /**
     * @param
     * @return
     */
    public WechatReplyNew getFollowedOrImmeReply(Integer wechatId, Integer ruleId) throws Exception {

        try {
//            WechatReplyNew wechatReplyNew = new WechatReplyNew();
//            wechatReplyNew.setWechatId(wechatId);
//            wechatReplyNew.setRuleId(ruleId);
//
//            WechatReplyNew wechatReplyRule = wechatReplyRuleDao.get(wechatReplyNew);
//            WechatReplyNew wechatReplyContent = wechatReplyRuleContentDao.get(wechatReplyNew);
//
//            wechatReplyContent.setStatus(wechatReplyRule.getStatus());
//            wechatReplyContent.setRuleName(wechatReplyRule.getRuleName());
//            wechatReplyContent.setRuleType(wechatReplyRule.getRuleType());
//            wechatReplyContent.setReplyWay(wechatReplyRule.getReplyWay());
//            wechatReplyContent.setRemarks(wechatReplyRule.getRemarks());
            return  new WechatReplyNew();
        } catch (Exception e) {
            logger.error("查询错误", e);
            throw new Exception("查询***错误");
        }
    }

    /**
     * 添加自动回复主表
     *
     * @param wechatReplyNew
     * @throws Exception
     */
    @Transactional(readOnly = false)
    public String saveReply(WechatReplyNew wechatReplyNew) throws Exception {
        WechatReplyNew wechatReplyNewResult = wechatReplyRuleDao.get(wechatReplyNew);
        if (null == wechatReplyNewResult) {
            wechatReplyRuleDao.insert(wechatReplyNew);
            return wechatReplyNew.getId();
        } else {
            return wechatReplyNewResult.getId();
        }

    }

    /**
     * 添加自动回复内容表
     *
     * @param descList
     * @throws Exception
     */
    @Transactional(readOnly = false)
    public void saveReplyContent(List<WechatReplyContentDO> descList) throws Exception {


        WechatReplyContentDO wechatReplyContentDO = wechatReplyRuleContentDao.get(descList.get(0));
        if (null == wechatReplyContentDO) {
            wechatReplyRuleContentDao.insert(wechatReplyContentDO);
        } else {
            wechatReplyRuleContentDao.update(wechatReplyContentDO);
        }

    }


//    /**
//     * 修改自动回复
//     * @param reply
//     * @throws Exception
//     */
//    @Transactional(readOnly = false)
//    public void updateReply(WechatReply reply) throws Exception {
//        checkByKeywords(reply);
//
//        try {
//            wechatNewReplyDao.updateReply(reply);
//        } catch (Exception e) {
//            throw new Exception(ResponseEnum.B_E_FAILED_TO_ADD.getMsg());
//        }
//    }
//
//    /**
//     * 添加自动回复
//     * @param reply
//     * @throws Exception
//     */
//    @Transactional(readOnly = false)
//    public void saveReply(WechatReply reply) throws Exception {
//        checkByKeywords(reply);
//        try {
//            wechatNewReplyDao.saveReply(reply);
//        } catch (Exception e) {
//            logger.error("saveReply is errpr", e);
//            throw new Exception(ResponseEnum.B_E_FAILED_TO_ADD.getMsg());
//        }
//    }
//
//
//    public void delete(WechatReply reply) {
//        super.delete(reply);
//    }
//
//    /**
//     * 通过keywords查询出自动回复
//     * @param keywords
//     * @return
//     */
//    public WechatReply getReplyByKeywords(String keywords, Integer wechatId) throws Exception {
//        try {
//            Map<String, Object> map = new HashMap<>();
//            map.put("keywords",keywords);
//            map.put("wechatId",wechatId);
//            return wechatNewReplyDao.getReplyByKeywords(map);
//        } catch (Exception e) {
//            throw new Exception(ResponseEnum.DATEBASE_QUERY_ERROR.getMsg());
//        }
//    }
//
//    /**
//     * 检验keywords是否存在
//     * @param reply
//     * @return
//     * @throws Exception
//     */
//    public boolean checkByKeywords(WechatReply reply) throws Exception {
//        if (null != reply.getKeywords()){
//            WechatReply wechatReply = getReplyByKeywords(reply.getKeywords(),reply.getWechatId());
//            if (null != wechatReply){
//                throw new Exception(WechatErrorEnum.KEYWORDS_ALREADY_EXIST.getDesc());
//            }
//        }
//        return true;
//    }
//
//    /**
//     * /单独包装图文回复实体类
//     * @param wechatReply
//     * @throws Exception
//     */
//    public void packageDesc(WechatReply wechatReply) throws Exception {
//        String content = wechatReply.getReplyDesc();
//        if (null == content) {
//            return;
//        }
//        try {
//            if (!content.isEmpty()) {
//                List<ReplyDescDTO> descList = new ArrayList<ReplyDescDTO>();
//                Type type = new com.google.gson.reflect.TypeToken<List<ReplyDescDTO>>() {
//                }.getType();
//                descList = (List<ReplyDescDTO>) JsonUtil.parseJson(content,
//                        type);
//                wechatReply.setReplyDescDTOS(descList);
//            }
//        } catch (Exception e) {
//            logger.error("packageDesc error:{}", e.getMessage());
//            throw new Exception(ResponseEnum.DATEBASE_QUERY_ERROR.getMsg());
//        }
//
//    }
}