/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.hzkans.crm.modules.login.dao;

import com.hzkans.crm.common.persistence.TreeDao;
import com.hzkans.crm.common.persistence.annotation.MyBatisDao;
import com.hzkans.crm.modules.login.entity.ChangePasswordDO;
import com.hzkans.crm.modules.sys.entity.Office;

/**
 * 修改密码验证
 * @author dtm
 * @version 2018/11/23
 */
@MyBatisDao
public interface ChangePasswordADO {
    ChangePasswordDO selectChangePassword(Integer id);

    int inster(ChangePasswordDO changePasswordDO);

    void update(ChangePasswordDO changePasswordDO);

}
