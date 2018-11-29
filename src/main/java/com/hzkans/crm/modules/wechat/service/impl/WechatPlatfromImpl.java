package com.hzkans.crm.modules.wechat.service.impl;

import com.hzkans.crm.modules.wechat.dao.WechatPlatfromDAO;
import com.hzkans.crm.modules.wechat.entity.WechatPlatfromDO;
import com.hzkans.crm.modules.wechat.service.WechatPlatfromService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IDEA
 * author:dengtm
 * Date:2018/11/28
 * Time:11:13
 */
public class WechatPlatfromImpl implements WechatPlatfromService {
    Logger log = LoggerFactory.getLogger(WechatPlatfromImpl.class);
    @Autowired
    WechatPlatfromDAO wechatPlatfromDAO;
    @Override
    public WechatPlatfromDO selectWechatPlatformById(Integer id) throws Exception {
        try {
            return wechatPlatfromDAO.selectWechatPlatformById(id);
        } catch (Exception e) {
            log.error("selectWechatPlatformById error", e);
            throw  new Exception("selectWechatPlatformById is error");
        }
    }

    @Override
    public int insertWechatPlatform(WechatPlatfromDO wechatPlatfromDO) throws Exception {
        try {
            return wechatPlatfromDAO.insertWechatPlatform(wechatPlatfromDO);
        } catch (Exception e) {
            log.error("insertWechatPlatform error", e);
            throw  new Exception("insertWechatPlatform is error");
        }
    }

    @Override
    public void updateWechatPlatform(WechatPlatfromDO wechatPlatfromDO) throws Exception {
        try {
            wechatPlatfromDAO.updateWechatPlatform(wechatPlatfromDO);
        } catch (Exception e) {
            log.error("updateWechatPlatform error", e);
            throw  new Exception("updateWechatPlatform is error");
        }
    }

    @Override
    public void deleteWechatPlatform(Integer id) throws Exception {
        try {
            wechatPlatfromDAO.deleteWechatPlatform(id);
        } catch (Exception e) {
            log.error("deleteWechatPlatform error", e);
            throw  new Exception("deleteWechatPlatform is error");
        }
    }

    @Override
    public List<WechatPlatfromDO> selectAllWechatPlatform() throws Exception {
        try {
            List<WechatPlatfromDO> wechatPlatfromDOS;
            wechatPlatfromDOS = wechatPlatfromDAO.selectAllWechatPlatform();
            return wechatPlatfromDOS;
        } catch (Exception e) {
            log.error("selectAllWechatPlatform error", e);
            throw  new Exception("selectAllWechatPlatform is error");
        }
    }
}
