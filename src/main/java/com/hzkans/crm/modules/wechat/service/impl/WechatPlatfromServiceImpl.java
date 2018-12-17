package com.hzkans.crm.modules.wechat.service.impl;
import com.hzkans.crm.common.constant.ResponseEnum;
import com.hzkans.crm.common.service.ServiceException;
import com.hzkans.crm.modules.trade.utils.TradeUtil;
import com.hzkans.crm.modules.wechat.dao.WechatPlatfromDAO;
import com.hzkans.crm.modules.wechat.entity.WechatPlatfrom;
import com.hzkans.crm.modules.wechat.service.WechatPlatfromService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created with IDEA
 * @author:dengtm
 * Date:2018/11/28
 * Time:11:13
 */
@Service
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
    public WechatPlatfrom getWechatPlatformById(Long id) throws Exception {
        try {
            return wechatPlatfromDAO.getWechatPlatformById(id);
        } catch (Exception e) {
            log.error("selectWechatPlatformById error", e);
            throw new Exception(ResponseEnum.B_E_RESULT_IS_NULL.getMsg());
        }
    }

    /**
     * 添加微信公众号
     * @param wechatPlatfrom
     * @return
     * @throws Exception
     */
    @Override
    public void addWechatPlatform(WechatPlatfrom wechatPlatfrom) throws Exception {

        WechatPlatfrom wechatPlatfrom1 = new WechatPlatfrom();
        wechatPlatfrom1.setName(wechatPlatfrom.getName());
        List<WechatPlatfrom> wechatPlatfroms = null;

        //判断是否存在这位微信公众号名称
        try {
            wechatPlatfroms = wechatPlatfromDAO.getWechatPlatforms(wechatPlatfrom1);
        } catch (Exception e) {
            log.error("getWechatPlatforms error", e);
            throw new Exception(ResponseEnum.B_E_FAILED_TO_ADD.getMsg());
        }
        if (CollectionUtils.isNotEmpty(wechatPlatfroms)) {
            throw new Exception(ResponseEnum.B_E_ALERADY_EXIST.getMsg());
        }

        //判断是否插入微信公众号绑定信息
        if (wechatPlatfrom.getAppId() != null || wechatPlatfrom.getAppSecret() != null
                || wechatPlatfrom.getToken() != null){
            wechatPlatfrom.setBindingFlag(1);
        }else {
            wechatPlatfrom.setBindingFlag(0);
        }

        try {
            wechatPlatfromDAO.insertWechatPlatform(wechatPlatfrom);
        } catch (Exception e) {
            log.error("insertWechatPlatform error", e);
            throw new Exception(ResponseEnum.B_E_FAILED_TO_ADD.getMsg());
        }

    }

    /**
     * 修改微信公众号
     * @param wechatPlatfrom
     * @throws Exception
     */
    @Override
    public void updateWechatPlatform(WechatPlatfrom wechatPlatfrom) throws Exception {
        try {
            wechatPlatfromDAO.updateWechatPlatform(wechatPlatfrom);
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
    public void removeWechatPlatform(Long id) throws Exception {
        try {
            wechatPlatfromDAO.removeWechatPlatform(id);
        } catch (Exception e) {
            log.error("deleteWechatPlatform error", e);
            throw new Exception(ResponseEnum.B_E_FAILED_TO_DELETE.getMsg());
        }
    }

    /**
     * 获取所有的公众号
     * @param wechatPlatfrom
     * @return
     * @throws Exception
     */
    @Override
    public List<WechatPlatfrom> listWechatPlatform(WechatPlatfrom wechatPlatfrom) throws Exception {
        try {
            List<WechatPlatfrom> wechatPlatfroms;
            wechatPlatfroms = wechatPlatfromDAO.getWechatPlatforms(wechatPlatfrom);
            return wechatPlatfroms;
        } catch (Exception e) {
            log.error("selectAllWechatPlatform error", e);
            throw new Exception(ResponseEnum.B_E_RESULT_IS_NULL.getMsg());
        }
    }

    @Override
    public WechatPlatfrom getWechatPlatform(WechatPlatfrom wechatPlatfrom) throws ServiceException {
        TradeUtil.isAllNull(wechatPlatfrom);
        WechatPlatfrom platfrom = null;
        try {
            platfrom = wechatPlatfromDAO.selectWechatPlatform(wechatPlatfrom);
        } catch (Exception e) {
            log.error("getWechatPlatform error",e);
            throw new ServiceException(ResponseEnum.DATEBASE_QUERY_ERROR);
        }
        return platfrom;
    }
}
