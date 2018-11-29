package com.hzkans.crm.modules.pswmanage.service;

import com.hzkans.crm.modules.pswmanage.entity.ChangePasswordDO;

import java.util.List;

/**
 * Created with IDEA
 * author:dengtm
 * Date:2018/11/23
 * Time:21:54
 */
public interface ChangePasswordService {
    int insterChangePassword(ChangePasswordDO changePasswordDO) throws Exception;

    List<ChangePasswordDO> selectChangePassword(ChangePasswordDO changePasswordDO) throws Exception;

    ChangePasswordDO selectNewChangePassword(String userId) throws Exception;
}
