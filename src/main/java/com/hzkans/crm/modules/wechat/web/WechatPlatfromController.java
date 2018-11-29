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
import com.hzkans.crm.modules.wechat.dao.WechatPlatfromDAO;
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
 *
 * @author dtm
 * @version 2018-11-26
 */
@Controller
@RequestMapping(value = "${adminPath}/wechat/")
public class WechatPlatfromController extends BaseController {


    @Autowired
    private WechatPlatfromDAO wechatPlatfromDAO;

    @RequestMapping(value = "/gotoInsert")
    public String gotoInsert()  {
        return "modules/wechatmanage/creatShop";
    }
    @RequestMapping(value = "/gotoSelectAll")
    public String gotoSelectAll()  {
        return "modules/wechatmanage/selectShop";
    }
    @RequestMapping(value = "/gotoUpdate")
    public String gotoUpdate(HttpServletRequest request, Model model)  {
        Integer id = RequestUtils.getInt(request, "id", false, "id is null", "");
        model.addAttribute("id",id);
        return "modules/wechatmanage/editShop";
    }

    @RequestMapping(value = "/selectById")
    @ResponseBody
    public String selectWechatPlatformById(HttpServletRequest request) throws Exception {
        try {
            Integer id = RequestUtils.getInt(request, "id", false, "id is null", "");

            WechatPlatfromDO wechatPlatfromDO = wechatPlatfromDAO.selectWechatPlatformById(id);
            return ResponseUtils.getSuccessApiResponseStr(wechatPlatfromDO);
        } catch (Exception e) {
            logger.info("selectWechatPlatformById is error");
            return ResponseUtils.getFailApiResponseStr(ResponseEnum.S_E_SERVICE_ERROR, e.getMessage());
        }
    }

    @RequestMapping(value = "/insert")
    @ResponseBody
    public String insertWechatPlatform(HttpServletRequest request) throws Exception {
        try {
            String name = RequestUtils.getString(request, false, "name", "name is null");
            String mainPart = RequestUtils.getString(request, false, "main_part", "main_part is null");

            User user = UserUtils.getUser();
            if (null == user){
                return ResponseUtils.getFailApiResponseStr(ResponseEnum.S_E_SERVICE_ERROR, "user is null");
            }
            WechatPlatfromDO wechatPlatfromDO = new WechatPlatfromDO();
            wechatPlatfromDO.setCreateBy(user.getName());
            wechatPlatfromDO.setUpdateBy(user.getName());
            wechatPlatfromDO.setName(name);
            wechatPlatfromDO.setMainPart(mainPart);
            wechatPlatfromDAO.insertWechatPlatform(wechatPlatfromDO);
            return ResponseUtils.getSuccessApiResponseStr(true);
        } catch (Exception e) {
            logger.info("selectWechatPlatformById is error");
            return ResponseUtils.getFailApiResponseStr(ResponseEnum.S_E_SERVICE_ERROR, e.getMessage());
        }
    }
	@RequestMapping(value = "/update")
    @ResponseBody
	public String updateWechatPlatform( HttpServletRequest request) throws Exception {
		try {
            Integer id = RequestUtils.getInt(request, "id", false, "id is null", "");
            String newMainPart = RequestUtils.getString(request, true, "new_main_part", "new_main_part is null");

            User user = UserUtils.getUser();
            if (null == user){
                return ResponseUtils.getFailApiResponseStr(ResponseEnum.S_E_SERVICE_ERROR, "user is null");
            }

            WechatPlatfromDO wechatPlatfromDO = new WechatPlatfromDO();
            wechatPlatfromDO.setMainPart(newMainPart);
            wechatPlatfromDO.setId(id);
            wechatPlatfromDO.setUpdateBy(user.getName());
            wechatPlatfromDAO.updateWechatPlatform(wechatPlatfromDO);
			return ResponseUtils.getSuccessApiResponseStr(true);
		} catch (Exception e) {
			logger.info("selectWechatPlatformById is error");
			return ResponseUtils.getFailApiResponseStr(ResponseEnum.S_E_SERVICE_ERROR,e.getMessage());
		}
	}
    @RequestMapping(value = "/delete")
    @ResponseBody
    public String deleteWechatPlatform( HttpServletRequest request) throws Exception {
        try {
            Integer id = RequestUtils.getInt(request, "id", false, "id is null", "");

            wechatPlatfromDAO.deleteWechatPlatform(id);
            return ResponseUtils.getSuccessApiResponseStr(true);
        } catch (Exception e) {
            logger.info("selectWechatPlatformById is error");
            return ResponseUtils.getFailApiResponseStr(ResponseEnum.S_E_SERVICE_ERROR,e.getMessage());
        }
    }
    @RequestMapping(value = "/selectAll")
    @ResponseBody
    public String selectAllWechatPlatform() throws Exception {
        try {
            List<WechatPlatfromDO> allWechatPlatform;
            allWechatPlatform = wechatPlatfromDAO.selectAllWechatPlatform();
            return ResponseUtils.getSuccessApiResponseStr(allWechatPlatform);
        } catch (Exception e) {
            logger.info("selectWechatPlatformById is error");
            return ResponseUtils.getFailApiResponseStr(ResponseEnum.S_E_SERVICE_ERROR,e.getMessage());
        }
    }
}