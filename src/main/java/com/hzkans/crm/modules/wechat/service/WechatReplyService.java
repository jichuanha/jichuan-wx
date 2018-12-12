/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.hzkans.crm.modules.wechat.service;

import com.hzkans.crm.common.constant.ResponseEnum;
import com.hzkans.crm.common.utils.JsonUtil;
import com.hzkans.crm.modules.wechat.constants.MessageTypeEnum;
import com.hzkans.crm.modules.wechat.constants.ReplyType;
import com.hzkans.crm.modules.wechat.constants.WechatErrorEnum;
import com.hzkans.crm.modules.wechat.dao.WechatMaterialDao;
import com.hzkans.crm.modules.wechat.dao.WechatReplyKeywordDao;
import com.hzkans.crm.modules.wechat.dao.WechatReplyRuleContentDao;
import com.hzkans.crm.modules.wechat.dao.WechatReplyRuleDao;
import com.hzkans.crm.modules.wechat.entity.*;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 微信公众号自动回复Service
 *
 * @author dtm
 * @version 2018-12-05
 */
@Service
@Transactional(readOnly = true)
public class WechatReplyService {

    private Logger logger = LoggerFactory.getLogger(WechatReplyService.class);

    @Autowired
    private WechatReplyKeywordDao wechatReplyKeywordDao;

    @Autowired
    private WechatReplyRuleContentDao wechatReplyRuleContentDao;

    @Autowired
    private WechatReplyRuleDao wechatReplyRuleDao;

    @Autowired
    private WechatMaterialDao wechatMaterialDao;


    /**
     * @param
     * @return
     */
    public WechatReplyNew getFollowedOrImmeReply(Integer wechatId, String ruleId) throws Exception {

        try {
            WechatReplyNew wechatReplyNew = new WechatReplyNew();
            wechatReplyNew.setWechatId(wechatId);
            wechatReplyNew.setId(ruleId);

            WechatReplyContentDO wechatReplyContentDO = new WechatReplyContentDO();
            wechatReplyContentDO.setWechatId(wechatId);
            wechatReplyContentDO.setRuleId(ruleId);

            WechatReplyKeywordDO wechatReplyKeywordDO = new WechatReplyKeywordDO();
            wechatReplyKeywordDO.setWechatId(wechatId);
            wechatReplyKeywordDO.setRuleId(ruleId);

            WechatReplyNew wechatReplyRule = wechatReplyRuleDao.get(wechatReplyNew);
            //把回复内容转化成对象传到前台
            if (null == wechatReplyRule) {
                throw new Exception(ResponseEnum.B_E_RESULT_IS_NULL.getMsg());
            }
            List<WechatReplyContentDO> wechatReplyContentS = wechatReplyRuleContentDao.findList(wechatReplyContentDO);
            List<WechatReplyKeywordDO> wechatReplyKeywordS = wechatReplyKeywordDao.findList(wechatReplyKeywordDO);

            logger.info("[{}] wechatReplyContentS",JsonUtil.toJson(wechatReplyContentS));
            //把回复内容转化成对象传到前台
            if (CollectionUtils.isNotEmpty(wechatReplyContentS)) {
                WechatMaterial wechatMaterial = new WechatMaterial();
                //把素材内容传进自动回复对象中
                for (WechatReplyContentDO temp:wechatReplyContentS) {
                    if (MessageTypeEnum.TEXT.getSign().equals(temp.getContentType()) && null != temp.getMaterialId()){
                        wechatMaterial.setMediaId(temp.getMaterialId());
                        wechatMaterial = wechatMaterialDao.get(wechatMaterial);
                        temp.setWechatMaterial(wechatMaterial);
                    }
                }
                wechatReplyRule.setWechatReplyContentDOS(wechatReplyContentS);
            }
            //把关键词信息发送到前台
            if (CollectionUtils.isNotEmpty(wechatReplyKeywordS)) {
                wechatReplyRule.setWechatReplyKeywordDOS(wechatReplyKeywordS);
            }

            return wechatReplyRule;
        } catch (Exception e) {
            logger.error("getFollowedOrImmeReply is errpr", e);
            throw new Exception(ResponseEnum.DATEBASE_QUERY_ERROR.getMsg());
        }
    }

    /**
     * 添加自动回复主表
     *
     * @param wechatReplyNew
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public String saveReply(WechatReplyNew wechatReplyNew) throws Exception {
        try {
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
        } catch (Exception e) {
            logger.error("saveReply is errpr", e);
            throw new Exception(ResponseEnum.DATEBASE_SAVE_ERROR.getMsg());
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
    @Transactional(rollbackFor = Exception.class)
    public void saveReplyContent(Integer wechatId, String ruleId, String content,
                                 Integer ruleType) throws Exception {
        try {
            //把Json内容转成对象
            if (!content.isEmpty()) {
                List<WechatReplyContentDO> descList = new ArrayList<WechatReplyContentDO>();
                Type type = new com.google.gson.reflect.TypeToken<List<WechatReplyContentDO>>() {
                }.getType();
                descList = (List<WechatReplyContentDO>) JsonUtil.parseJson(content,
                        type);
                for (WechatReplyContentDO wechatReplyContentDO : descList) {
                    wechatReplyContentDO.setWechatId(wechatId);
                    wechatReplyContentDO.setRuleId(ruleId);
                }
                //如果为被关注回复以及收到信息回复则判断是否有数据
                if (ruleType == 2 || ruleType == 3) {
                    WechatReplyContentDO wechatReplyContentDO = wechatReplyRuleContentDao.getContent(descList.get(0));
                    logger.info("wechatReplyContentDO[{}]", JsonUtil.toJson(wechatReplyContentDO));
                    if (null == wechatReplyContentDO) {
                        wechatReplyRuleContentDao.insert(descList.get(0));
                    } else {
                        descList.get(0).setId(wechatReplyContentDO.getId());
                        wechatReplyRuleContentDao.update(descList.get(0));
                    }
                } else {
                    for (WechatReplyContentDO wechatReplyContentDO : descList) {
                        wechatReplyRuleContentDao.insert(wechatReplyContentDO);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("saveReply is errpr", e);
            throw new Exception(ResponseEnum.DATEBASE_SAVE_ERROR.getMsg());
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
    @Transactional(rollbackFor = Exception.class)
    public void saveReplyKeyword(Integer wechatId, String ruleId, String keywords) throws Exception {
        try {
            if (!keywords.isEmpty()) {
                List<WechatReplyKeywordDO> descList = new ArrayList<WechatReplyKeywordDO>();
                Type type = new com.google.gson.reflect.TypeToken<List<WechatReplyKeywordDO>>() {
                }.getType();
                descList = JsonUtil.parseJson(keywords, type);
                for (WechatReplyKeywordDO wechatReplyKeywordDO : descList) {
                    wechatReplyKeywordDO.setWechatId(wechatId);
                    wechatReplyKeywordDO.setRuleId(ruleId);
                    wechatReplyKeywordDao.insert(wechatReplyKeywordDO);
                }
            }
        } catch (Exception e) {
            logger.error("saveReply is errpr", e);
            throw new Exception(ResponseEnum.DATEBASE_SAVE_ERROR.getMsg());
        }
    }

    /**
     * 删除自动回复
     *
     * @param wechatId
     * @param ruleId
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteReply(String ruleId, Integer wechatId, Integer ruleType) throws Exception {
        try {
            WechatReplyContentDO wechatReplyContentDO = new WechatReplyContentDO();
            if (ReplyType.FOLLOW.getCode().equals(ruleType) || ReplyType.RECEIVED.getCode().equals(ruleType)){
                WechatReplyNew wechatReplyNew = new WechatReplyNew();
                wechatReplyNew.setWechatId(wechatId);
                wechatReplyNew.setRuleType(ruleType);
                WechatReplyNew wechatReplyNewTemp =  wechatReplyRuleDao.get(wechatReplyNew);
                wechatReplyContentDO.setWechatId(wechatId);
                wechatReplyContentDO.setRuleId(wechatReplyNewTemp.getId());
                wechatReplyRuleContentDao.delete(wechatReplyContentDO);
            }else if (ruleType == 1) {
                //如果是删除关键字则要删除主表以及关键词表的信息
                wechatReplyContentDO.setRuleId(ruleId);
                wechatReplyContentDO.setWechatId(wechatId);

                wechatReplyRuleContentDao.delete(wechatReplyContentDO);
                WechatReplyKeywordDO wechatReplyKeywordDO = new WechatReplyKeywordDO();
                wechatReplyKeywordDO.setRuleId(ruleId);
                wechatReplyKeywordDO.setWechatId(wechatId);
                wechatReplyKeywordDao.delete(wechatReplyKeywordDO);

                WechatReplyNew wechatReplyNew = new WechatReplyNew();
                wechatReplyNew.setId(ruleId);
                wechatReplyNew.setWechatId(wechatId);
                logger.info("wechatReplyNew [{}]", JsonUtil.toJson(wechatReplyNew));
                wechatReplyRuleDao.delete(wechatReplyNew);
            }
        } catch (Exception e) {
            logger.error("saveReply is errpr", e);
            throw new Exception(ResponseEnum.DATEBASE_SAVE_ERROR.getMsg());
        }

    }

    /**
     * 删除自动回复中关键字以及内容
     *
     * @param wechatId
     * @param ruleId
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteReplykeywordAndContent(String ruleId, Integer wechatId) throws Exception {
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
            logger.error("saveReply is errpr", e);
            throw new Exception(ResponseEnum.DATEBASE_SAVE_ERROR.getMsg());
        }
    }

    /**
     * 修改主表信息
     *
     * @param wechatReplyNew
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateReplyRrule(WechatReplyNew wechatReplyNew) throws Exception {
        try {
            wechatReplyRuleDao.update(wechatReplyNew);
        } catch (Exception e) {
            logger.error("updateReplyRrule is errpr", e);
            throw new Exception(ResponseEnum.DATEBASE_SAVE_ERROR.getMsg());
        }
    }

    /**
     * 获取所有的回复信息，能够把所有信息查询出来
     * @param wechatReplyNew
     * @return
     * @throws Exception
     */
    public List<WechatReplyNew> listWechatReply(WechatReplyNew wechatReplyNew) throws Exception {
        try {
            List<WechatReplyNew> wechatReplyNewList = wechatReplyRuleDao.findList(wechatReplyNew);
            WechatReplyKeywordDO wechatReplyKeywordDO = new WechatReplyKeywordDO();
            WechatReplyContentDO wechatReplyContentDO = new WechatReplyContentDO();
            for (WechatReplyNew wechatReplytemp:wechatReplyNewList) {
                //查询关键词
                wechatReplyKeywordDO.setRuleId(wechatReplytemp.getId());
                wechatReplyKeywordDO.setWechatId(wechatReplytemp.getWechatId());
                List<WechatReplyKeywordDO> wechatReplyKeywordDOS = wechatReplyKeywordDao.findList(wechatReplyKeywordDO);
                if (CollectionUtils.isNotEmpty(wechatReplyKeywordDOS)){
                    wechatReplytemp.setWechatReplyKeywordDOS(wechatReplyKeywordDOS);
                }

                //查询回复内容
                wechatReplyContentDO.setRuleId(wechatReplytemp.getId());
                wechatReplyContentDO.setWechatId(wechatReplytemp.getWechatId());
                List<WechatReplyContentDO> wechatReplyContentDOS = wechatReplyRuleContentDao.findList(wechatReplyContentDO);
                if (CollectionUtils.isNotEmpty(wechatReplyContentDOS)){
                    WechatMaterial wechatMaterial = new WechatMaterial();
                    //把素材内容传进自动回复对象中
                    for (WechatReplyContentDO temp:wechatReplyContentDOS) {
                        if (!MessageTypeEnum.TEXT.getSign().equals(temp.getContentType()) && null != temp.getMaterialId()){
                            wechatMaterial.setId(temp.getMaterialId());
                            wechatMaterial = wechatMaterialDao.get(wechatMaterial);
                            logger.info("[{}]wechatMaterial ",JsonUtil.toJson(wechatMaterial));
                            temp.setWechatMaterial(wechatMaterial);
                        }
                    }
                    wechatReplytemp.setWechatReplyContentDOS(wechatReplyContentDOS);
                }

            }
            return  wechatReplyNewList;
        } catch (Exception e) {
            logger.error("listWechatReply is errpr", e);
            throw new Exception(ResponseEnum.DATEBASE_QUERY_ERROR.getMsg());
        }
    }

    /**
     * 暂停自动回复
     * @param wechatReplyNew
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public void suspendReply(WechatReplyNew wechatReplyNew) throws Exception {
        try {
            wechatReplyRuleDao.update(wechatReplyNew);
        } catch (Exception e) {
            logger.error("listWechatReply is errpr", e);
            throw new Exception(WechatErrorEnum.SUSPEND_IS_ERROR.getDesc());
        }
    }


}