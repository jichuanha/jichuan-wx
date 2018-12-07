package com.hzkans.crm.modules.wechat.dao;

import com.hzkans.crm.common.persistence.annotation.MyBatisDao;
import com.hzkans.crm.modules.wechat.entity.CustomMenuDO;


import java.util.List;
import java.util.Map;

@MyBatisDao
public interface CustomMenuDAO {
	int deleteByPrimaryKey(Map map);

	int insert(CustomMenuDO record);

	CustomMenuDO select(CustomMenuDO record);

	List<CustomMenuDO> selectchildMenu(Integer wechatId);

	List<CustomMenuDO> selectParentMenu(Integer wechatId);

	List<CustomMenuDO> selectChildMenu(Long parentId);

	int modifCustomMenu(CustomMenuDO record);
}