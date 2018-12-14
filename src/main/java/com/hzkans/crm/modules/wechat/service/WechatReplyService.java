/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.hzkans.crm.modules.wechat.service;

import com.hzkans.crm.common.constant.ResponseEnum;
import com.hzkans.crm.common.utils.JsonUtil;
import com.hzkans.crm.modules.wechat.constants.MessageTypeEnum;
import com.hzkans.crm.modules.wechat.constants.ReplyTypeEnum;
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
     * 添加自动回复主表
     *
     * @param wechatReplyNew
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public WechatReplyNew saveReply(WechatReplyNew wechatReplyNew) throws Exception {
        try {
            if (!ReplyTypeEnum.KEYWORD.getCode().equals(wechatReplyNew.getRuleType())){
                List<WechatReplyNew> wechatReplyNewResult = wechatReplyRuleDao.listReply(wechatReplyNew);
                if (CollectionUtils.isEmpty(wechatReplyNewResult)) {
                    wechatReplyRuleDao.insert(wechatReplyNew);

                    //只需要添加内容表信息
                    saveReplyContent(wechatReplyNew);
                    return wechatReplyNew;
                } else {
                    wechatReplyNew.setId(wechatReplyNewResult.get(0).getId());
                    wechatReplyRuleDao.update(wechatReplyNew);

                    //只需要添加内容表信息
                    saveReplyContent(wechatReplyNew);
                    return wechatReplyNew;
                }
            } else {
                //判断规则名称是否存在
                if (CollectionUtils.isNotEmpty(wechatReplyRuleDao.listReply(wechatReplyNew))){
                    throw new Exception(WechatErrorEnum.NAME_IS_NOT_NULL.getDesc());
                }
                //添加主表信息
                wechatReplyRuleDao.insert(wechatReplyNew);
                //添加内容以及关键字
                saveReplyContent(wechatReplyNew);

                saveReplyKeyword(wechatReplyNew);
                return wechatReplyNew;
            }
        } catch (Exception e) {
            logger.error("saveReply is errpr", e);
            throw new Exception(ResponseEnum.DATEBASE_SAVE_ERROR.getMsg());
        }
    }

    /**
     * 添加自动回复内容表
     *
     * @param wechatReplyNew
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveReplyContent(WechatReplyNew wechatReplyNew) throws Exception {
        try {
            Integer ruleType = wechatReplyNew.getRuleType();
            //把Json内容转成对象
            if (!wechatReplyNew.getContent().isEmpty()) {
                logger.info("[{}]content++",JsonUtil.toJson(wechatReplyNew.getContent()));
                List<WechatReplyContent> contentList = new ArrayList<WechatReplyContent>();
                Type type = new com.google.gson.reflect.TypeToken<List<WechatReplyContent>>() {
                }.getType();
                contentList = (List<WechatReplyContent>) JsonUtil.parseJson(wechatReplyNew.getContent(),
                        type);
                for (WechatReplyContent wechatReplyContentDO : contentList) {
                    wechatReplyContentDO.setWechatId(wechatReplyNew.getWechatId());
                    wechatReplyContentDO.setRuleId(wechatReplyNew.getId());
                }
                //如果为被关注回复以及收到信息回复则判断是否有数据
                if (ReplyTypeEnum.FOLLOW.getCode().equals(ruleType) || ReplyTypeEnum.RECEIVED.getCode().equals(ruleType)) {
                    WechatReplyContent wechatReplyContentDO = wechatReplyRuleContentDao.getContent(contentList.get(0));
                    logger.info("wechatReplyContentDO[{}]", JsonUtil.toJson(wechatReplyContentDO));
                    WechatReplyContent wechatReplyContent = contentList.get(0);

                    if(wechatReplyContent.getContentType().equals(MessageTypeEnum.TEXT.getSign())){
                        saveMaterial(wechatReplyContent);
                    }
                    if (null == wechatReplyContentDO) {
                        wechatReplyRuleContentDao.insert(wechatReplyContent);
                    } else {
                        wechatReplyContent.setId(wechatReplyContentDO.getId());
                        wechatReplyRuleContentDao.update(wechatReplyContent);
                    }
                } else {
                    for (WechatReplyContent wechatReplyContentDO : contentList) {
                        if(wechatReplyContentDO.getContentType().equals(MessageTypeEnum.TEXT.getSign())){
                            saveMaterial(wechatReplyContentDO);
                        }
                        logger.info("[{}]wechatReplyContentDO ",JsonUtil.toJson(wechatReplyContentDO));
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
     * @param wechatReplyNew
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveReplyKeyword(WechatReplyNew wechatReplyNew) throws Exception {
        try {
            if (!wechatReplyNew.getKeyword().isEmpty()) {
                List<WechatReplyKeyword> descList = new ArrayList<WechatReplyKeyword>();
                Type type = new com.google.gson.reflect.TypeToken<List<WechatReplyKeyword>>() {
                }.getType();
                descList = JsonUtil.parseJson(wechatReplyNew.getKeyword(), type);
                for (WechatReplyKeyword wechatReplyKeywordDO : descList) {
                    wechatReplyKeywordDO.setWechatId(wechatReplyNew.getWechatId());
                    wechatReplyKeywordDO.setRuleId(wechatReplyNew.getId());
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
     * @param wechatReplyNew
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteReply(WechatReplyNew wechatReplyNew) throws Exception {
        try {
            Long wechatId = wechatReplyNew.getWechatId();
            Integer ruleType = wechatReplyNew.getRuleType();
            WechatReplyContent wechatReplyContentDO = new WechatReplyContent();
            if (ReplyTypeEnum.FOLLOW.getCode().equals(ruleType)
                    || ReplyTypeEnum.RECEIVED.getCode().equals(ruleType)){

                WechatReplyNew wechatReplyNewInput = new WechatReplyNew();
                wechatReplyNewInput.setWechatId(wechatId);
                wechatReplyNewInput.setRuleType(ruleType);
                WechatReplyNew wechatReplyNewTemp =  wechatReplyRuleDao.get(wechatReplyNewInput);

                wechatReplyContentDO.setWechatId(wechatId);
                wechatReplyContentDO.setRuleId(wechatReplyNewTemp.getId());
                wechatReplyRuleContentDao.delete(wechatReplyContentDO);

            }else if (ReplyTypeEnum.KEYWORD.getCode().equals(ruleType)){
                //如果是删除关键字则要删除主表以及关键词表,以及内容表的信息
                deleteReplykeywordAndContent(wechatReplyNew);

                WechatReplyNew wechatReplyNewInput = new WechatReplyNew();
                wechatReplyNewInput.setId(wechatReplyNew.getId());
                wechatReplyNewInput.setWechatId(wechatId);
                logger.info("wechatReplyNew [{}]", JsonUtil.toJson(wechatReplyNewInput));
                wechatReplyRuleDao.delete(wechatReplyNewInput);
            }
        } catch (Exception e) {
            logger.error("saveReply is errpr", e);
            throw new Exception(ResponseEnum.DATEBASE_SAVE_ERROR.getMsg());
        }

    }

    /**
     * 删除自动回复中关键字以及内容
     *
     * @param wechatReplyNew
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteReplykeywordAndContent(WechatReplyNew wechatReplyNew) throws Exception {
        try {
            WechatReplyContent wechatReplyContentDO = new WechatReplyContent();
            wechatReplyContentDO.setRuleId(wechatReplyNew.getId());
            wechatReplyContentDO.setWechatId(wechatReplyNew.getWechatId());
            wechatReplyRuleContentDao.delete(wechatReplyContentDO);

            WechatReplyKeyword wechatReplyKeywordDO = new WechatReplyKeyword();
            wechatReplyKeywordDO.setRuleId(wechatReplyNew.getId());
            wechatReplyKeywordDO.setWechatId(wechatReplyNew.getWechatId());
            wechatReplyKeywordDao.delete(wechatReplyKeywordDO);
        } catch (Exception e) {
            logger.error("saveReply is errpr", e);
            throw new Exception(ResponseEnum.DATEBASE_SAVE_ERROR.getMsg());
        }
    }

    /**
     * 修改关键词回复所有信息
     *
     * @param wechatReplyNew
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateReplyRrule(WechatReplyNew wechatReplyNew) throws Exception {
        try {
            wechatReplyRuleDao.update(wechatReplyNew);
            deleteReplykeywordAndContent(wechatReplyNew);
            saveReplyContent(wechatReplyNew);
            saveReplyKeyword(wechatReplyNew);
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

            for (WechatReplyNew wechatReplytemp:wechatReplyNewList) {
                WechatReplyKeyword wechatReplyKeywordDO = new WechatReplyKeyword();
                WechatReplyContent wechatReplyContentDO = new WechatReplyContent();
                //查询关键词
                wechatReplyKeywordDO.setRuleId(wechatReplytemp.getId());
                wechatReplyKeywordDO.setWechatId(wechatReplytemp.getWechatId());
                List<WechatReplyKeyword> wechatReplyKeywordDOS = wechatReplyKeywordDao.findList(wechatReplyKeywordDO);
                if (CollectionUtils.isNotEmpty(wechatReplyKeywordDOS)){
                    wechatReplytemp.setWechatReplyKeywordDOS(wechatReplyKeywordDOS);
                }
                //查询回复内容
                wechatReplyContentDO.setRuleId(wechatReplytemp.getId());
                wechatReplyContentDO.setWechatId(wechatReplytemp.getWechatId());
                List<WechatReplyContent> wechatReplyContentDOS = wechatReplyRuleContentDao.findList(wechatReplyContentDO);
                if (CollectionUtils.isNotEmpty(wechatReplyContentDOS)){

                    //把素材内容传进自动回复对象中
                    for (WechatReplyContent temp:wechatReplyContentDOS) {
                        if (!MessageTypeEnum.TEXT.getSign().equals(temp.getContentType()) && null != temp.getMaterialId()){
                            WechatMaterial wechatMaterial = new WechatMaterial();
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


    /**
     * 添加文本内容时   同时添加素材添加素材
     * @param wechatReplyContent
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public WechatReplyContent saveMaterial(WechatReplyContent wechatReplyContent) throws Exception {
        try {
            WechatMaterial wechatMaterial = new WechatMaterial();
            wechatMaterial.setTitle("文字内容");
            wechatMaterial.setType(wechatReplyContent.getContentType());
            wechatMaterial.setWechatId(wechatReplyContent.getWechatId());
            wechatMaterial.setContent(wechatReplyContent.getContent());
            wechatMaterialDao.insert(wechatMaterial);
            wechatReplyContent.setMaterialId(wechatMaterial.getId());
            return wechatReplyContent;
        } catch (Exception e) {
            logger.error("listWechatReply is errpr", e);
            throw new Exception(WechatErrorEnum.SUSPEND_IS_ERROR.getDesc());
        }
    }




}