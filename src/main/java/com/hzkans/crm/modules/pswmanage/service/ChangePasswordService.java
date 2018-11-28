package com.hzkans.crm.modules.pswmanage.service;

import com.hzkans.crm.modules.pswmanage.entity.ChangePasswordDO;

/**
 * Created with IDEA
 * author:dengtm
 * Date:2018/11/23
 * Time:21:54
 */
public interface ChangePasswordService {
    int insterChangePassword(ChangePasswordDO changePasswordDO) throws Exception;

    ChangePasswordDO selectChangePassword(Integer id) throws Exception;
}
