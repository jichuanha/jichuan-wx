package com.hzkans.crm.modules.wechat.web;

import com.hzkans.crm.common.constant.ResponseEnum;
import com.hzkans.crm.common.utils.RequestUtils;
import com.hzkans.crm.common.utils.ResponseUtils;
import com.hzkans.crm.modules.sys.entity.User;
import com.hzkans.crm.modules.sys.utils.UserUtils;
import com.hzkans.crm.modules.wechat.entity.CustomMainMenuDTO;
import com.hzkans.crm.modules.wechat.entity.CustomMenuDO;
import com.hzkans.crm.modules.wechat.service.CustomMenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created with IDEA
 *
 * @author:dengtm
 * @Date:2018/12/7
 * @Time:13:46
 */
@Controller
@RequestMapping(value = "${adminPath}/wechat_menu")
public class CustomMenuController {
    private final static Logger logger = LoggerFactory.getLogger(CustomMenuController.class);

    @Autowired
    private CustomMenuService customMenuService;

    @RequestMapping(value = "/link_diy_menu")
    public String gotoInsert() {
        return "modules/wechatplatform/diy_menu";
    }
    /**
     * 获取自定义菜单列表
     *
     * @param request
     * @return
     */
    @RequestMapping("/custom_menu_list")
    @ResponseBody
    public String getCustomMenuList(HttpServletRequest request) {
        try {
            Integer wechatId = RequestUtils.getInt(request, "wechat_id", false, "id is null", "");

            List<CustomMainMenuDTO> customMenuDTOList = customMenuService.getCustomMenu(wechatId);
            return ResponseUtils.getSuccessApiResponseStr(customMenuDTOList);
        } catch (Exception e) {
            logger.error("---------->getCustomMenuList found error:", e);
            return ResponseUtils.getFailApiResponseStr(ResponseEnum.S_E_SERVICE_ERROR, e.getMessage());
        }
    }

    /**
     * 添加微信公众号菜单
     *
     * @param request
     * @return
     */
    @RequestMapping("/save_menu")
    @ResponseBody
    public String saveCustomMenu(HttpServletRequest request) {
        try {
            Long parentId = RequestUtils.getLong(request, "parent_id", true, "", "");
            String keywords = RequestUtils.getString(request, true, "keywords", "uri is null");
            String uri = RequestUtils.getString(request, true, "uri", "article_uri is null");
            String name = RequestUtils.getString(request, false, "name", "name is null");
            Integer type = RequestUtils.getInt(request, "type", false, "type is null", "");
            Integer sort = RequestUtils.getInt(request, "sort", false, "sort is null", "");
            Integer wechatId = RequestUtils.getInt(request, "wechat_id", false, "wechat_id is null", "");

            User user = UserUtils.getUser();
            if (null == user) {
                return ResponseUtils.getFailApiResponseStr(ResponseEnum.B_E_SESSION_TIMEOUT);
            }

            CustomMenuDO customMenuDO = new CustomMenuDO();
            customMenuDO.setParentId(parentId);
            customMenuDO.setName(name);
            customMenuDO.setType(type);
            customMenuDO.setSort(sort);
            customMenuDO.setKeywords(keywords);
            customMenuDO.setUri(uri);
            customMenuDO.setWechatId(wechatId);

            customMenuService.saveCustomMenu(customMenuDO);
            return ResponseUtils.getSuccessApiResponseStr(true);
        } catch (Exception e) {
            logger.error("---------->saveCustomMenu found error:", e);
            return ResponseUtils.getFailApiResponseStr(ResponseEnum.B_E_FAILED_TO_ADD, e.getMessage());
        }
    }

    /**
     * 修改微信公众号菜单信息
     *
     * @param request
     * @return
     */
    @RequestMapping("/modify_menu")
    @ResponseBody
    public String modifyCustomMenu(HttpServletRequest request) {
        try {
            Long id = RequestUtils.getLong(request, "id", false, "id is null", "");
            String keywords = RequestUtils.getString(request, true, "uri", "uri is null");
            String uri = RequestUtils.getString(request, true, "keywords", "article_uri is null");
            String name = RequestUtils.getString(request, true, "name", "name is null");
            Integer type = RequestUtils.getInt(request, "type", true, "type is null", "");
            Integer sort = RequestUtils.getInt(request, "sort", true, "sort is null", "");
            Integer wechatId = RequestUtils.getInt(request, "wechat_id", false, "wechat_id is null", "");

            CustomMenuDO customMenuDO = new CustomMenuDO();
            customMenuDO.setId(id);
            customMenuDO.setName(name);
            customMenuDO.setType(type);
            customMenuDO.setSort(sort);
            customMenuDO.setKeywords(keywords);
            customMenuDO.setUri(uri);
            customMenuDO.setWechatId(wechatId);

            customMenuService.modifCustomMenu(customMenuDO);
            return ResponseUtils.getSuccessApiResponseStr(true);
        } catch (Exception e) {
            logger.error("---------->saveCustomMenu found error:", e);
            return ResponseUtils.getFailApiResponseStr(ResponseEnum.B_E_MODIFY_ERROR, e.getMessage());
        }
    }

    /**
     * 删除微信菜单
     *
     * @param request
     * @return
     */
    @RequestMapping("/remove_menu")
    @ResponseBody
    public String removeCustomMenu(HttpServletRequest request) {
        Long id = RequestUtils.getLong(request, "id", false, "id is null", "");
        Integer wechatId = RequestUtils.getInt(request, "wechat_id", false, "wechat_id is null", "");
        try {
            Boolean flag = customMenuService.deleteCustomMenu(id, wechatId);
            return ResponseUtils.getSuccessApiResponseStr(flag);
        } catch (Exception e) {
            logger.error("---------->deletedCustomMenu found error:", e);
            return ResponseUtils.getFailApiResponseStr(ResponseEnum.B_E_FAILED_TO_DELETE, e.getMessage());
        }
    }

    /**
     * 获取所有的一级菜单
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/get_main_menu")
    @ResponseBody
    public String getMainMenu(HttpServletRequest request) throws Exception {
        try {
            Integer wechatId = RequestUtils.getInt(request, "wechat_id", false, "wechat_id is null", "");

            List<CustomMenuDO> customMenuDOList = customMenuService.selectParentMenu(wechatId);
            return ResponseUtils.getSuccessApiResponseStr(customMenuDOList);
        } catch (Exception e) {
            logger.error("---------->deletedCustomMenu found error:", e);
            return ResponseUtils.getFailApiResponseStr(ResponseEnum.DATEBASE_QUERY_ERROR, e.getMessage());
        }
    }

    /**
     * 获取自定义菜单详情
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/get_menu_details")
    @ResponseBody
    public String getMenuDetails(HttpServletRequest request) throws Exception {
        try {
            Integer wechatId = RequestUtils.getInt(request, "wechat_id", false, "wechat_id is null", "");
            Long id = RequestUtils.getLong(request, "id", false, "id is null", "");

            CustomMenuDO customMenuDO = new CustomMenuDO();
            customMenuDO.setWechatId(wechatId);
            customMenuDO.setId(id);

            CustomMenuDO customMenuDOresult = customMenuService.getCustomMenu(customMenuDO);
            return ResponseUtils.getSuccessApiResponseStr(customMenuDOresult);
        } catch (Exception e) {
            logger.error("---------->deletedCustomMenu found error:", e);
            return ResponseUtils.getFailApiResponseStr(ResponseEnum.DATEBASE_QUERY_ERROR, e.getMessage());
        }
    }

}
