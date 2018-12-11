/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.hzkans.crm.modules.wechat.service;

import com.hzkans.crm.common.service.CrudService;
import com.hzkans.crm.common.utils.JsonUtil;
import com.hzkans.crm.modules.wechat.dao.WechatReplyDao;
import com.hzkans.crm.modules.wechat.dao.WechatReplyKeywordDao;
import com.hzkans.crm.modules.wechat.dao.WechatReplyRuleContentDao;
import com.hzkans.crm.modules.wechat.dao.WechatReplyRuleDao;
import com.hzkans.crm.modules.wechat.entity.*;
import org.apache.commons.collections.CollectionUtils;
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
            WechatReplyNew wechatReplyNew = new WechatReplyNew();
            wechatReplyNew.setWechatId(wechatId);
            wechatReplyNew.setId("" + ruleId);

            WechatReplyContentDO wechatReplyContentDO = new WechatReplyContentDO();
            wechatReplyNew.setWechatId(wechatId);
            wechatReplyNew.setRuleId(ruleId);

            WechatReplyKeywordDO wechatReplyKeywordDO = new WechatReplyKeywordDO();
            wechatReplyNew.setWechatId(wechatId);
            wechatReplyNew.setRuleId(ruleId);

            WechatReplyNew wechatReplyRule = wechatReplyRuleDao.get(wechatReplyNew);
            List<WechatReplyContentDO> wechatReplyContentS = wechatReplyRuleContentDao.findList(wechatReplyContentDO);
            List<WechatReplyKeywordDO> wechatReplyKeywordS = wechatReplyKeywordDao.findList(wechatReplyKeywordDO);

            if (CollectionUtils.isNotEmpty(wechatReplyContentS)) {
                wechatReplyRule.setWechatReplyContentDOS(wechatReplyContentS);
            }
            if (CollectionUtils.isNotEmpty(wechatReplyKeywordS)) {
                wechatReplyRule.setWechatReplyKeywordDOS(wechatReplyKeywordS);
            }

            return wechatReplyRule;
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
        if (wechatReplyNew.getRuleType() == 2 || wechatReplyNew.getRuleType() == 3) {
            WechatReplyNew wechatReplyNewResult = wechatReplyRuleDao.get(wechatReplyNew);
            if (null == wechatReplyNewResult) {
                wechatReplyRuleDao.insert(wechatReplyNew);
                return wechatReplyNew.getId();
            } else {
                return wechatReplyNewResult.getId();
            }
        } else {
            wechatReplyRuleDao.insert(wechatReplyNew);
            return wechatReplyNew.getId();
        }
    }

    /**
     * 添加自动回复内容表
     *
     * @param wechatId
     * @param ruleId
     * @param content
     * @throws Exception
     */
    @Transactional(readOnly = false)
    public void saveReplyContent(Integer wechatId, String ruleId, String content, Integer ruleType) throws Exception {
        if (!content.isEmpty()) {
            List<WechatReplyContentDO> descList = new ArrayList<WechatReplyContentDO>();
            Type type = new com.google.gson.reflect.TypeToken<List<WechatReplyContentDO>>() {
            }.getType();
            descList = (List<WechatReplyContentDO>) JsonUtil.parseJson(content,
                    type);
            for (WechatReplyContentDO wechatReplyContentDO : descList) {
                wechatReplyContentDO.setWechatId(wechatId);
                wechatReplyContentDO.setRuleId(Integer.parseInt(ruleId));
            }
            if (ruleType == 2 || ruleType == 3) {
                WechatReplyContentDO wechatReplyContentDO = wechatReplyRuleContentDao.get(descList.get(0));
                logger.info("wechatReplyContentDO[{}]", JsonUtil.toJson(wechatReplyContentDO));
                if (null == wechatReplyContentDO) {
                    wechatReplyRuleContentDao.insert(descList.get(0));
                } else {
                    wechatReplyRuleContentDao.update(descList.get(0));
                }
            } else {
                for (WechatReplyContentDO wechatReplyContentDO : descList) {
                    wechatReplyRuleContentDao.insert(wechatReplyContentDO);
                }
            }
        }
    }

    /**
     * 添加自动回复关键字表
     *
     * @param wechatId
     * @param ruleId
     * @param keywords
     * @throws Exception
     */
    @Transactional(readOnly = false)
    public void saveReplyKeyword(Integer wechatId, String ruleId, String keywords) throws Exception {
        if (!keywords.isEmpty()) {
            List<WechatReplyKeywordDO> descList = new ArrayList<WechatReplyKeywordDO>();
            Type type = new com.google.gson.reflect.TypeToken<List<WechatReplyKeywordDO>>() {
            }.getType();
            descList = (List<WechatReplyKeywordDO>) JsonUtil.parseJson(keywords,
                    type);
            for (WechatReplyKeywordDO wechatReplyKeywordDO : descList) {
                wechatReplyKeywordDO.setWechatId(wechatId);
                wechatReplyKeywordDO.setRuleId(Integer.parseInt(ruleId));
                wechatReplyKeywordDao.insert(wechatReplyKeywordDO);
            }
        }
    }

    /**
     * 删除自动回复
     *
     * @param wechatId
     * @param ruleId
     * @throws Exception
     */
    @Transactional(readOnly = false)
    public void deleteReply(Integer ruleId, Integer wechatId, Integer ruleType) throws Exception {
        try {
            WechatReplyContentDO wechatReplyContentDO = new WechatReplyContentDO();
            wechatReplyContentDO.setRuleId(ruleId);
            wechatReplyContentDO.setWechatId(wechatId);

            wechatReplyRuleContentDao.delete(wechatReplyContentDO);

            if (ruleType == 1) {
                WechatReplyKeywordDO wechatReplyKeywordDO = new WechatReplyKeywordDO();
                wechatReplyKeywordDO.setRuleId(ruleId);
                wechatReplyKeywordDO.setWechatId(wechatId);
                wechatReplyKeywordDao.delete(wechatReplyKeywordDO);

                WechatReplyNew wechatReplyNew = new WechatReplyNew();
                wechatReplyNew.setId("" + ruleId);
                wechatReplyNew.setWechatId(wechatId);
                logger.info("wechatReplyNew [{}]",JsonUtil.toJson(wechatReplyNew));
                wechatReplyRuleDao.delete(wechatReplyNew);
            }
        } catch (Exception e) {
            logger.error("deleteReply is error", e);
            throw new Exception("查询***错误");
        }

    }

    /**
     * 删除自动回复中关键字以及内容
     *
     * @param wechatId
     * @param ruleId
     * @throws Exception
     */
    @Transactional(readOnly = false)
    public void deleteReplykeywordAndContent(Integer ruleId, Integer wechatId) throws Exception {
        try {
            WechatReplyContentDO wechatReplyContentDO = new WechatReplyContentDO();
            wechatReplyContentDO.setRuleId(ruleId);
            wechatReplyContentDO.setWechatId(wechatId);
            wechatReplyRuleContentDao.delete(wechatReplyContentDO);

            WechatReplyKeywordDO wechatReplyKeywordDO = new WechatReplyKeywordDO();
            wechatReplyKeywordDO.setRuleId(ruleId);
            wechatReplyKeywordDO.setWechatId(wechatId);
            wechatReplyKeywordDao.delete(wechatReplyKeywordDO);
        } catch (Exception e) {
            logger.error("deleteReply is error", e);
            throw new Exception("查询***错误");
        }
    }
    /**
     * 删除自动回复中关键字以及内容
     *
     * @param wechatReplyNew
     * @throws Exception
     */
    @Transactional(readOnly = false)
    public void updateReplyRrule(WechatReplyNew wechatReplyNew) throws Exception {
        try {
            wechatReplyRuleDao.update(wechatReplyNew);
        } catch (Exception e) {
            logger.error("deleteReply is error", e);
            throw new Exception("查询***错误");
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