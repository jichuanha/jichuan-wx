package com.hzkans.crm.modules.wechat.service;

import com.hzkans.crm.modules.wechat.entity.CustomMainMenuDTO;
import com.hzkans.crm.modules.wechat.entity.CustomMenuDO;
import java.util.List;

/**
 * 自定义菜单
 * @author dtm
 * @create 2018-12-7
 */
public interface CustomMenuService {

	/**
	 * 添加自定义菜单
	 * @param menuDO
	 * @return
	 * @throws Exception
	 */
	Boolean saveCustomMenu(CustomMenuDO menuDO) throws Exception;

	/**
	 *   修改微信公众号菜单
	 * @param menuDTO
	 * @throws Exception
	 */
	void modifCustomMenu(CustomMenuDO menuDTO) throws Exception;

	/**
	 * 删除自定义菜单
	 * @param id
	 * @param wechatId
	 * @return
	 * @throws Exception
	 */
	Boolean deleteCustomMenu(Long id,Integer wechatId) throws Exception;

	/**
	 * 自定义菜单列表
	 * @param wechatId
	 * @return
	 * @throws Exception
	 */
	List<CustomMainMenuDTO> getCustomMenu(Integer wechatId) throws Exception;

	/**
	 * 获取所有的一级菜单
	 * @param wechatId
	 * @return
	 * @throws Exception
	 */
	List<CustomMenuDO> selectParentMenu(Integer wechatId) throws Exception;

	/**
	 * 自定义菜单详情
	 * @param customMenuDO
	 * @return
	 * @throws Exception
	 */
	CustomMenuDO getCustomMenu(CustomMenuDO customMenuDO) throws Exception;


}
