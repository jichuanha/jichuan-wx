package com.hzkans.crm.modules.wechat.service.impl;

import com.hzkans.crm.common.constant.ResponseEnum;
import com.hzkans.crm.modules.wechat.dao.WechatPlatfromDAO;
import com.hzkans.crm.modules.wechat.entity.WechatPlatfromDO;
import com.hzkans.crm.modules.wechat.service.WechatPlatfromService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

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
    public WechatPlatfromDO getWechatPlatformById(Integer id) throws Exception {
        try {
            return wechatPlatfromDAO.getWechatPlatformById(id);
        } catch (Exception e) {
            log.error("selectWechatPlatformById error", e);
            throw new Exception(ResponseEnum.B_E_RESULT_IS_NULL.getMsg());
        }
    }

    /**
     * @param wechatPlatfromDO
     * @return
     * @throws Exception
     */
    @Override
    public void addWechatPlatform(WechatPlatfromDO wechatPlatfromDO) throws Exception {
        try {

            wechatPlatfromDAO.insertWechatPlatform(wechatPlatfromDO);
        } catch (Exception e) {
            log.error("insertWechatPlatform error", e);
            throw new Exception(ResponseEnum.B_E_FAILED_TO_ADD.getMsg());
        }
    }


    @Override
    public void updateWechatPlatform(WechatPlatfromDO wechatPlatfromDO) throws Exception {
        try {
            wechatPlatfromDAO.updateWechatPlatform(wechatPlatfromDO);
        } catch (Exception e) {
            log.error("updateWechatPlatform error", e);
            throw new Exception(ResponseEnum.B_E_MODIFY_ERROR.getMsg());
        }
    }

    @Override
    public void removeWechatPlatform(Integer id) throws Exception {
        try {
            wechatPlatfromDAO.removeWechatPlatform(id);
        } catch (Exception e) {
            log.error("deleteWechatPlatform error", e);
            throw new Exception(ResponseEnum.B_E_FAILED_TO_DELETE.getMsg());
        }
    }

    @Override
    public List<WechatPlatfromDO> getWechatPlatforms(WechatPlatfromDO wechatPlatfromDO) throws Exception {
        try {
            List<WechatPlatfromDO> wechatPlatfromDOS;
            wechatPlatfromDOS = wechatPlatfromDAO.getWechatPlatforms(wechatPlatfromDO);
            return wechatPlatfromDOS;
        } catch (Exception e) {
            log.error("selectAllWechatPlatform error", e);
            throw new Exception(ResponseEnum.B_E_RESULT_IS_NULL.getMsg());
        }
    }
}
