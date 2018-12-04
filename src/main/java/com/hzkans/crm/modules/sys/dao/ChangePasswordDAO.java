/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.hzkans.crm.modules.sys.dao;

import com.hzkans.crm.common.persistence.annotation.MyBatisDao;
import com.hzkans.crm.modules.sys.entity.ChangePasswordDO;

import java.util.List;

/**
 * 修改密码验证
 * @author dtm
 * @version 2018/11/23
 */
@MyBatisDao
public interface ChangePasswordDAO {
    List<ChangePasswordDO> selectChangePassword(ChangePasswordDO changePasswordDO);

    int inster(ChangePasswordDO changePasswordDO);


}
