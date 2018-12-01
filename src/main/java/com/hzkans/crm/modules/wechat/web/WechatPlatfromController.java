/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.hzkans.crm.modules.wechat.web;

import javax.servlet.http.HttpServletRequest;
import com.hzkans.crm.common.constant.ResponseEnum;
import com.hzkans.crm.common.utils.RequestUtils;
import com.hzkans.crm.common.utils.ResponseUtils;
import com.hzkans.crm.common.web.BaseController;
import com.hzkans.crm.modules.sys.entity.User;
import com.hzkans.crm.modules.sys.utils.UserUtils;
import com.hzkans.crm.modules.wechat.service.WechatPlatfromService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.hzkans.crm.modules.wechat.entity.WechatPlatfromDO;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;


/**
 * 微信公众号Controller
 * @author dtm
 * @version 2018-11-26
 */
@Controller
@RequestMapping(value = "${adminPath}/wechat/")
public class WechatPlatfromController extends BaseController {

    @Autowired
    private WechatPlatfromService wechatPlatfromService;

    @RequestMapping(value = "/link_add")
    public String gotoInsert() {
        return "modules/wechatmanage/creatShop";
    }

    @RequestMapping(value = "/link_get_list")
    public String gotoSelectAll() {
        return "modules/wechatmanage/selectShop";
    }

    @RequestMapping(value = "/link_index")
    public String gotoIndex() {
        return "modules/wechatmanage/shopIndex";
    }

    @RequestMapping(value = "/link_update")
    public String gotoUpdate(HttpServletRequest request, Model model) throws Exception {
        try {
            Integer id = RequestUtils.getInt(request, "id", false, "id is null", "");

            WechatPlatfromDO wechatPlatfromDO = wechatPlatfromService.getWechatPlatformById(id);
            model.addAttribute("id", wechatPlatfromDO.getId());
            model.addAttribute("name", wechatPlatfromDO.getName());
            model.addAttribute("mainPart", wechatPlatfromDO.getMainPart());
            return "modules/wechatmanage/editShop";
        } catch (Exception e) {
            logger.error("selectWechatPlatformById is error", e);
            return ResponseUtils.getFailApiResponseStr(ResponseEnum.B_E_RESULT_IS_NULL);
        }
    }

    /**
     * 添加公众号
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/add_wechat_latform")
    @ResponseBody
    public String addWechatPlatform(HttpServletRequest request) throws Exception {
        try {
            String name = RequestUtils.getString(request, false, "name", "name is null");
            String mainPart = RequestUtils.getString(request, false, "main_part", "main_part is null");

            User user = UserUtils.getUser();
            if (null == user) {
                return ResponseUtils.getFailApiResponseStr(ResponseEnum.B_E_SESSION_TIMEOUT);
            }

            WechatPlatfromDO wechatPlatfromDO = new WechatPlatfromDO();
            wechatPlatfromDO.setName(name);
            wechatPlatfromDO.setCreateBy(user.getName());
            wechatPlatfromDO.setUpdateBy(user.getName());
            wechatPlatfromDO.setMainPart(mainPart);
            wechatPlatfromService.addWechatPlatform(wechatPlatfromDO);
            return ResponseUtils.getSuccessApiResponseStr(true);
        } catch (Exception e) {
            logger.error("selectWechatPlatformById is error", e);
            return ResponseUtils.getFailApiResponseStr(ResponseEnum.B_E_FAILED_TO_ADD, e.getMessage());
        }
    }

    /**
     * 修改公众号
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/update_wechat_latform")
    @ResponseBody
    public String updateWechatPlatform(HttpServletRequest request) throws Exception {
        try {
            Integer id = RequestUtils.getInt(request, "id", false, "id is null", "");
            String newMainPart = RequestUtils.getString(request, true, "new_main_part", "new_main_part is null");

            User user = UserUtils.getUser();
            if (null == user) {
                return ResponseUtils.getFailApiResponseStr(ResponseEnum.B_E_SESSION_TIMEOUT, ResponseEnum.B_E_SESSION_TIMEOUT.getMsg());
            }

            WechatPlatfromDO wechatPlatfromDO = new WechatPlatfromDO();
            wechatPlatfromDO.setMainPart(newMainPart);
            wechatPlatfromDO.setId(id);
            wechatPlatfromDO.setUpdateBy(user.getName());
            wechatPlatfromService.updateWechatPlatform(wechatPlatfromDO);
            return ResponseUtils.getSuccessApiResponseStr(true);
        } catch (Exception e) {
            logger.error("selectWechatPlatformById is error", e);
            return ResponseUtils.getFailApiResponseStr(ResponseEnum.B_E_MODIFY_ERROR);
        }
    }

    /**
     * 删除公众号
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/delete_wechat_latform")
    @ResponseBody
    public String deleteWechatPlatform(HttpServletRequest request) throws Exception {
        try {
            Integer id = RequestUtils.getInt(request, "id", false, "id is null", "");

            wechatPlatfromService.removeWechatPlatform(id);
            return ResponseUtils.getSuccessApiResponseStr(true);
        } catch (Exception e) {
            logger.info("selectWechatPlatformById is error");
            return ResponseUtils.getFailApiResponseStr(ResponseEnum.B_E_FAILED_TO_DELETE);
        }
    }

    /**
     * 获取所有的公众号
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/wechat_latform_list")
    @ResponseBody
    public String getAllWechatPlatform() throws Exception {
        try {
            List<WechatPlatfromDO> allWechatPlatform;
            allWechatPlatform = wechatPlatfromService.listWechatPlatform(new WechatPlatfromDO());
            return ResponseUtils.getSuccessApiResponseStr(allWechatPlatform);
        } catch (Exception e) {
            logger.error("selectWechatPlatformById is error");
            return ResponseUtils.getFailApiResponseStr(ResponseEnum.B_E_RESULT_IS_NULL);
        }
    }
}