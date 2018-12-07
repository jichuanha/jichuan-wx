/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.hzkans.crm.modules.wechat.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hzkans.crm.common.constant.ResponseEnum;
import com.hzkans.crm.common.persistence.Page;
import com.hzkans.crm.common.service.CrudService;
import com.hzkans.crm.modules.wechat.constants.WechatErrorEnum;
import com.hzkans.crm.modules.wechat.entity.WechatReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hzkans.crm.modules.wechat.dao.WechatReplyDao;

/**
 * 微信公众号自动回复Service
 *
 * @author dtm
 * @version 2018-12-05
 */
@Service
@Transactional(readOnly = true)
public class WechatReplyService extends CrudService<WechatReplyDao, WechatReply> {
    @Autowired
    private WechatReplyDao wechatNewReplyDao;

    public WechatReply get(String id) {
        return super.get(id);
    }

    public List<WechatReply> findList(WechatReply reply) {
        return super.findList(reply);
    }

    public Page<WechatReply> findPage(Page<WechatReply> page, WechatReply reply) {
        return super.findPage(page, reply);
    }


    /**
     * 修改自动回复
     * @param reply
     * @throws Exception
     */
    public void updateReply(WechatReply reply) throws Exception {
        checkByKeywords(reply);

        try {
            wechatNewReplyDao.updateReply(reply);
        } catch (Exception e) {
            throw new Exception(ResponseEnum.B_E_FAILED_TO_ADD.getMsg());
        }
    }

    /**
     * 添加自动回复
     * @param reply
     * @throws Exception
     */
    public void saveReply(WechatReply reply) throws Exception {
        checkByKeywords(reply);

        try {
            wechatNewReplyDao.saveReply(reply);
        } catch (Exception e) {
            throw new Exception(ResponseEnum.B_E_FAILED_TO_ADD.getMsg());
        }
    }

    @Transactional(readOnly = false)
    public void delete(WechatReply reply) {
        super.delete(reply);
    }

    /**
     * 通过keywords查询出自动回复
     * @param keywords
     * @return
     */
    public WechatReply getReplyByKeywords(String keywords, Integer wechatId) throws Exception {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("keywords",keywords);
            map.put("wechatId",wechatId);
            return wechatNewReplyDao.getReplyByKeywords(map);
        } catch (Exception e) {
            throw new Exception(ResponseEnum.DATEBASE_QUERY_ERROR.getMsg());
        }
    }

    /**
     * 检验keywords是否存在
     * @param reply
     * @return
     * @throws Exception
     */
    public boolean checkByKeywords(WechatReply reply) throws Exception {
        if (null != reply.getKeywords()){
            WechatReply wechatReply = getReplyByKeywords(reply.getKeywords(),reply.getWechatId());
            if (null == wechatReply){
                throw new Exception(WechatErrorEnum.KEYWORDS_ALREADY_EXIST.getDesc());
            }
        }
        return true;
    }
}