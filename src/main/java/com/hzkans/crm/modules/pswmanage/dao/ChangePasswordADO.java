/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.hzkans.crm.modules.pswmanage.dao;

import com.hzkans.crm.common.persistence.annotation.MyBatisDao;
import com.hzkans.crm.modules.pswmanage.entity.ChangePasswordDO;

import java.util.List;

/**
 * 修改密码验证
 * @author dtm
 * @version 2018/11/23
 */
@MyBatisDao
public interface ChangePasswordADO {
    List<ChangePasswordDO> selectChangePassword(ChangePasswordDO changePasswordDO);

    int inster(ChangePasswordDO changePasswordDO);

    void update(ChangePasswordDO changePasswordDO);

}
