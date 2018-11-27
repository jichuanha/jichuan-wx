package com.hzkans.crm.modules.login.service.ChangePasswordImpl;

import com.hzkans.crm.common.constant.ResponseEnum;
import com.hzkans.crm.common.utils.ResponseUtils;
import com.hzkans.crm.modules.login.dao.ChangePasswordADO;
import com.hzkans.crm.modules.login.entity.ChangePasswordDO;
import com.hzkans.crm.modules.login.service.ChangePasswordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with IDEA
 * author:dengtm
 * Date:2018/11/23
 * Time:21:59
 */
public class ChangePasswordImpl implements ChangePasswordService {
    Logger log = LoggerFactory.getLogger(ChangePasswordImpl.class);
    @Autowired
    ChangePasswordADO changePasswordADO;

    @Override
    public int insterChangePassword(ChangePasswordDO changePasswordDO) throws Exception {

        try {
            if (null == changePasswordDO) {
                log.error("ChangePasswordImpl.insterChangePassword error : changePasswordDO is null ");
                throw new Exception("changePasswordDO is null ");
            }
            changePasswordADO.inster(changePasswordDO);
            log.info("[{}]changePasswordId",changePasswordDO.getId());
            return changePasswordDO.getId();
        } catch (Exception e) {
            log.error("memberAccount error", e);
            throw  new Exception("changePasswordADO.inster is error");
        }
    }

    @Override
    public ChangePasswordDO selectChangePassword(Integer id) throws Exception {
        try {
            if (null == id) {
                log.error("ChangePasswordImpl.selectChangePassword error : id is null ");
                throw new Exception("selectChangePassword.id is null ");
            }
            ChangePasswordDO changePasswordDO = changePasswordADO.selectChangePassword(id);
            return changePasswordDO;
        } catch (Exception e) {
            log.error("memberAccount error", e);
            throw  new Exception("changePasswordADO.inster is error");
        }
    }
}
