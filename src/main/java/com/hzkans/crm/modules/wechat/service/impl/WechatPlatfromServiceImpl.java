package com.hzkans.crm.modules.wechat.service.impl;
import com.hzkans.crm.common.constant.ResponseEnum;
import com.hzkans.crm.common.utils.JsonUtil;
import com.hzkans.crm.modules.wechat.dao.WechatPlatfromDAO;
import com.hzkans.crm.modules.wechat.entity.WechatPlatfromDO;
import com.hzkans.crm.modules.wechat.service.WechatPlatfromService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;


/**
 * Created with IDEA
 * @author:dengtm
 * Date:2018/11/28
 * Time:11:13
 */
public class WechatPlatfromServiceImpl implements WechatPlatfromService {
    Logger log = LoggerFactory.getLogger(WechatPlatfromServiceImpl.class);
    @Autowired
    WechatPlatfromDAO wechatPlatfromDAO;

    /**
     * 通过id 获取微信公众号
     * @param id
     * @return
     * @throws Exception
     */
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
     * 添加微信公众号
     * @param wechatPlatfromDO
     * @return
     * @throws Exception
     */
    @Override
    public void addWechatPlatform(WechatPlatfromDO wechatPlatfromDO) throws Exception {

        WechatPlatfromDO wechatPlatfromDO1 = new WechatPlatfromDO();
        wechatPlatfromDO1.setName(wechatPlatfromDO.getName());
        List<WechatPlatfromDO> wechatPlatfromDOS = null;

        //判断是否存在这位微信公众号名称
        try {
            wechatPlatfromDOS = wechatPlatfromDAO.getWechatPlatforms(wechatPlatfromDO1);
        } catch (Exception e) {
            log.error("getWechatPlatforms error", e);
            throw new Exception(ResponseEnum.B_E_FAILED_TO_ADD.getMsg());
        }
        if (CollectionUtils.isNotEmpty(wechatPlatfromDOS)) {
            throw new Exception(ResponseEnum.B_E_ALERADY_EXIST.getMsg());
        }

        //判断是否插入微信公众号绑定信息
        if (wechatPlatfromDO.getAppId() != null
                || wechatPlatfromDO.getAppSecret() != null
                || wechatPlatfromDO.getToken() != null){
            wechatPlatfromDO.setBindingFlag(1);
        }else {
            wechatPlatfromDO.setBindingFlag(0);
        }

        try {
            wechatPlatfromDAO.insertWechatPlatform(wechatPlatfromDO);
        } catch (Exception e) {
            log.error("insertWechatPlatform error", e);
            throw new Exception(ResponseEnum.B_E_FAILED_TO_ADD.getMsg());
        }

    }

    /**
     * 修改微信公众号
     * @param wechatPlatfromDO
     * @throws Exception
     */
    @Override
    public void updateWechatPlatform(WechatPlatfromDO wechatPlatfromDO) throws Exception {
        try {
            wechatPlatfromDAO.updateWechatPlatform(wechatPlatfromDO);
        } catch (Exception e) {
            log.error("updateWechatPlatform error", e);
            throw new Exception(ResponseEnum.B_E_MODIFY_ERROR.getMsg());
        }
    }

    /**
     * 删除公众号
     * @param id
     * @throws Exception
     */
    @Override
    public void removeWechatPlatform(Integer id) throws Exception {
        try {
            wechatPlatfromDAO.removeWechatPlatform(id);
        } catch (Exception e) {
            log.error("deleteWechatPlatform error", e);
            throw new Exception(ResponseEnum.B_E_FAILED_TO_DELETE.getMsg());
        }
    }

    /**
     * 获取所有的公众号
     * @param wechatPlatfromDO
     * @return
     * @throws Exception
     */
    @Override
    public List<WechatPlatfromDO> listWechatPlatform(WechatPlatfromDO wechatPlatfromDO) throws Exception {
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
