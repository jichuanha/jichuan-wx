package com.hzkans.crm.modules.wechat.service.impl;

import com.hzkans.crm.common.constant.ResponseEnum;
import com.hzkans.crm.common.service.ServiceException;
import com.hzkans.crm.common.utils.JsonUtil;
import com.hzkans.crm.modules.trade.utils.TradeUtil;
import com.hzkans.crm.modules.wechat.constants.MenuLvlEnum;
import com.hzkans.crm.modules.wechat.dao.CustomMenuDAO;
import com.hzkans.crm.modules.wechat.entity.CustomMainMenuDTO;
import com.hzkans.crm.modules.wechat.entity.CustomMenuDO;
import com.hzkans.crm.modules.wechat.service.CustomMenuService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.*;

/**
 * @author chenggm
 * @create 2017-02-22 17:32
 **/

public class CustomMenuServiceImpl implements CustomMenuService {

    private static final Logger logger = LoggerFactory
            .getLogger(CustomMenuServiceImpl.class);

    @Autowired
    private CustomMenuDAO customMenuDAO;

    @Override
    public Boolean saveCustomMenu(CustomMenuDO menuDO) throws Exception {
        try {
            customMenuDAO.insert(menuDO);
        } catch (Exception e) {
            logger.error("saveCustomMenu error :{}", e.getMessage());
            throw new Exception(ResponseEnum.B_E_FAILED_TO_ADD.getMsg());
        }
        return true;
    }

    @Override
    public void modifCustomMenu(CustomMenuDO menuDO) throws Exception {
        try {
            customMenuDAO.modifCustomMenu(menuDO);
        } catch (Exception e) {
            logger.error(" modifCustomMenu error:{} ", new Exception(e));
            throw new Exception(ResponseEnum.B_E_MODIFY_ERROR.getMsg());
        }

    }

    @Override
    public Boolean deleteCustomMenu(Long id, Integer wechatId) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("id",id);
        map.put("wechatId",wechatId);
        try {
            logger.info("map[{}]",JsonUtil.toJson(map));
            customMenuDAO.deleteByPrimaryKey(map);
            return true;
        } catch (Exception e) {
            logger.error("deleteCustomMenu error :{}", e.getMessage());
            throw new Exception(ResponseEnum.B_E_MODIFY_ERROR.getMsg());
        }

    }

    @Override
    public List<CustomMainMenuDTO> getCustomMenu(Integer wechatId) throws Exception {

        List<CustomMainMenuDTO> customMainMenuDTOS = Collections.EMPTY_LIST;
        try {
            customMainMenuDTOS = new ArrayList<>();

             //获取所有的菜单信息
            List<CustomMenuDO> customMenuDOList = customMenuDAO
                    .selectchildMenu(wechatId);

            if (CollectionUtils.isNotEmpty(customMenuDOList)) {

                Map<Long, List<CustomMenuDO>> customMenuDOMap;

                //用parentMenuList装下所有的一级菜单
                //用customMenuDOMap装下所有以一级菜单id为键的二级菜单
                List<CustomMenuDO> parentMenuList = new ArrayList<CustomMenuDO>();

                customMenuDOMap = customMenuDOList2Map(customMenuDOList, parentMenuList);

                //把一级菜单以及所属的二级菜单按顺序排列在customMainMenuDTOS中
                for (CustomMenuDO parentMenuDO : parentMenuList) {
                    CustomMainMenuDTO customMainMenuDTO = new CustomMainMenuDTO();
                    BeanUtils.copyProperties(parentMenuDO, customMainMenuDTO);
                    customMainMenuDTOS.add(customMainMenuDTO);

                    List<CustomMenuDO> customChildMenuDOs = customMenuDOMap
                            .get(parentMenuDO.getId());
                    //把二级菜单都放入到customMainMenuDTOS中
                    List<CustomMainMenuDTO> customChildMenuDTOS = new ArrayList<CustomMainMenuDTO>();
                    if (CollectionUtils.isNotEmpty(customChildMenuDOs)) {
                        customChildMenuDTOS = convert2CustomChildMenuDTOS(customChildMenuDOs);
                    }
                    customMainMenuDTOS.addAll(customChildMenuDTOS);
                }
                logger.info(" customMainMenuDTOS:{} ",
                        JsonUtil.toJson(customMainMenuDTOS));
            }
        } catch (Exception e) {
            logger.error("getCustomMenu error :{}", e.getMessage());
            throw new Exception(ResponseEnum.DATEBASE_QUERY_ERROR.getMsg());
        }
        return customMainMenuDTOS;
    }

    private List<CustomMainMenuDTO> convert2CustomChildMenuDTOS(
            List<CustomMenuDO> customChildMenuDOs) {
        if (CollectionUtils.isNotEmpty(customChildMenuDOs)) {
            List<CustomMainMenuDTO> customChildMenuDTOs = new ArrayList<CustomMainMenuDTO>();
            for (CustomMenuDO customMenuDO : customChildMenuDOs) {
                CustomMainMenuDTO customChildMenuDTO = new CustomMainMenuDTO();
                BeanUtils.copyProperties(customMenuDO, customChildMenuDTO);
                customChildMenuDTOs.add(customChildMenuDTO);
            }
            return customChildMenuDTOs;
        }
        return null;

    }

    private Map<Long, List<CustomMenuDO>> customMenuDOList2Map(
            List<CustomMenuDO> customMenuDOList,
            List<CustomMenuDO> parentMenuList) {
        Map<Long, List<CustomMenuDO>> customMenuDOMap = new HashMap<Long, List<CustomMenuDO>>();

        for (CustomMenuDO customMenuDO : customMenuDOList) {
            Long parentId = customMenuDO.getParentId();
            // 一级菜单信息
            if (parentId == null) {
                customMenuDO.setMenuLevel(MenuLvlEnum.FIRST_LVL.getCode());
                parentMenuList.add(customMenuDO);
            } else {
                // 子菜单分组
                customMenuDO.setMenuLevel(MenuLvlEnum.SECOND_LVL.getCode());
                if (customMenuDOMap.get(parentId) == null) {
                    List<CustomMenuDO> list = new ArrayList<CustomMenuDO>();
                    list.add(customMenuDO);
                    customMenuDOMap.put(parentId, list);
                } else {
                    customMenuDOMap.get(parentId).add(customMenuDO);
                }
            }

        }
        return customMenuDOMap;
    }


    @Override
    public List<CustomMenuDO> selectParentMenu(Integer wechatId) throws Exception {
        try {
            List<CustomMenuDO> list = customMenuDAO.selectParentMenu(wechatId);
            if (CollectionUtils.isEmpty(list)) {
                return null;
            }
            return list;
        } catch (Exception e) {
            logger.error("selectParentMenu error :{}", e.getMessage());
            throw new Exception(ResponseEnum.DATEBASE_QUERY_ERROR.getMsg());
        }

    }

    @Override
    public CustomMenuDO getCustomMenu(CustomMenuDO customMenuDO) throws Exception {
        try {
            CustomMenuDO customMenu = customMenuDAO.select(customMenuDO);
            if (null == customMenu) {
                return null;
            }
            return customMenu;
        } catch (Exception e) {
            logger.error("getCustomMenu error :{}", e.getMessage());
            throw new Exception(ResponseEnum.DATEBASE_QUERY_ERROR.getMsg());
        }
    }

    @Override
    public List<CustomMainMenuDTO> getAllCustomMenu(Integer wechatId) throws ServiceException {
        List<CustomMainMenuDTO> customMainMenuDTOS = new ArrayList<>();;
        try {
            //获取所有的菜单信息
            List<CustomMenuDO> customMenuDOList = customMenuDAO
                    .selectchildMenu(wechatId);

            if (CollectionUtils.isNotEmpty(customMenuDOList)) {

                Map<Long, List<CustomMenuDO>> customMenuDOMap;

                //用parentMenuList装下所有的一级菜单
                //用customMenuDOMap装下所有以一级菜单id为键的二级菜单
                List<CustomMenuDO> parentMenuList = new ArrayList<CustomMenuDO>();

                customMenuDOMap = customMenuDOList2Map(customMenuDOList, parentMenuList);
                logger.info("customMenuDOMap {}",JsonUtil.toJson(customMenuDOMap));
                logger.info("parentMenuList {}",JsonUtil.toJson(parentMenuList));

                //把一级菜单以及所属的二级菜单按顺序排列在customMainMenuDTOS中
                for (CustomMenuDO parentMenuDO : parentMenuList) {
                    CustomMainMenuDTO customMainMenuDTO = new CustomMainMenuDTO();
                    BeanUtils.copyProperties(parentMenuDO, customMainMenuDTO);


                    List<CustomMenuDO> customChildMenuDOs = customMenuDOMap
                            .get(parentMenuDO.getId());
                    //把二级菜单都放入到customMainMenuDTOS中

                    if (CollectionUtils.isNotEmpty(customChildMenuDOs)) {
                        List<CustomMainMenuDTO> customChildMenuDTOS = new ArrayList<CustomMainMenuDTO>();
                        customChildMenuDTOS = convert2CustomChildMenuDTOS(customChildMenuDOs);
                        customMainMenuDTO.setCustomChildMenuDTOS(customChildMenuDTOS);
                    }

                    customMainMenuDTOS.add(customMainMenuDTO);
                }
                logger.info(" customMainMenuDTOS:{} ",
                        JsonUtil.toJson(customMainMenuDTOS));
            }
        } catch (Exception e) {
            logger.error("getCustomMenu error :{}", e.getMessage());
            throw new ServiceException(ResponseEnum.DATEBASE_QUERY_ERROR.getMsg());
        }
        return customMainMenuDTOS;
    }

}
