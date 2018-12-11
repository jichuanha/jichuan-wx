package com.hzkans.crm.modules.wechat.web;

import com.alibaba.fastjson.JSONObject;
import com.hzkans.crm.common.constant.ResponseEnum;
import com.hzkans.crm.common.service.ServiceException;
import com.hzkans.crm.common.utils.JsonUtil;
import com.hzkans.crm.common.utils.RequestUtils;
import com.hzkans.crm.common.utils.ResponseUtils;
import com.hzkans.crm.modules.wechat.constants.MenuType;
import com.hzkans.crm.modules.wechat.constants.WechatMenu;
import com.hzkans.crm.modules.wechat.entity.CustomMainMenuDTO;
import com.hzkans.crm.modules.wechat.entity.WechatPlatfromDO;
import com.hzkans.crm.modules.wechat.service.CustomMenuService;
import com.hzkans.crm.modules.wechat.service.WechatPlatfromService;
import com.hzkans.crm.modules.wechat.utils.HttpRequestUtil;
import com.hzkans.crm.modules.wechat.utils.WechatCofig;
import com.hzkans.crm.modules.wechat.utils.WechatUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jc
 * @description
 * @create 2018/12/10
 */
@Controller
@RequestMapping("${adminPath}/toWechat")
public class WechatInterfaceCallController {

    @Autowired
    private CustomMenuService customMenuService;
    @Autowired
    private WechatPlatfromService wechatPlatfromService;

    private static Logger logger = LoggerFactory.getLogger(WechatInterfaceCallController.class);

    @RequestMapping("syMenu")
    @ResponseBody
    public String syMenu2Wechat(HttpServletRequest request, HttpServletResponse response) {
        Integer wechatId = RequestUtils.getInt(request, "wechat_id", "wechat_id is null");
        try {
            List<CustomMainMenuDTO> allCustomMenu = customMenuService.getAllCustomMenu(wechatId);

            //获取该公众号的appid和appsecret
            WechatPlatfromDO wechatPlatform = wechatPlatfromService.getWechatPlatformById(wechatId);

            if(CollectionUtils.isEmpty(allCustomMenu)) {
                throw new ServiceException(ResponseEnum.B_E_MENU_EMPTY);
            }
            String bizCode = syMenu(allCustomMenu, wechatPlatform);
            if(!bizCode.equals("0")) {
                return ResponseUtils.getFailApiResponseStr(ResponseEnum.S_E_SERVICE_ERROR);
            }

            return ResponseUtils.getSuccessApiResponseStr(true);
        } catch (Exception e) {
            return ResponseUtils.getFailApiResponseStr(ResponseEnum.S_E_SERVICE_ERROR);
        }
    }


    private String syMenu(List<CustomMainMenuDTO> allCustomMenu, WechatPlatfromDO wechatPlatform) {
        //创建最外层
        Map<String, List<WechatMenu>> listMap = new HashMap<>();
        List<WechatMenu> menuList = new ArrayList<>();
        //遍历数据,按照微信规则匹配数据
        for (CustomMainMenuDTO dto : allCustomMenu) {
            //先判断一级菜单不是菜单
            WechatMenu wechatMenu = new WechatMenu();
            Integer type = dto.getType();
            if(!MenuType.MENU.getCode().equals(type)) {
                //如果是按钮
                if(MenuType.CLICK.getCode().equals(type)) {
                    wechatMenu.setType(MenuType.CLICK.getDesc());
                    wechatMenu.setName(dto.getName());
                    wechatMenu.setKey(dto.getKeywords());
                }
                //如果是view
                if(MenuType.VIEW.getCode().equals(type)) {
                    wechatMenu.setType(MenuType.VIEW.getDesc());
                    wechatMenu.setName(dto.getName());
                    wechatMenu.setUrl(dto.getUri());
                }
            }

            //如果一级菜单是菜单
            List<WechatMenu> childList = new ArrayList<>();
            if(MenuType.MENU.getCode().equals(type)) {
                wechatMenu.setName(dto.getName());
                List<CustomMainMenuDTO> customChildMenuDTOS = dto.getCustomChildMenuDTOS();
                for (CustomMainMenuDTO menuDTO : customChildMenuDTOS) {
                    WechatMenu child = new WechatMenu();
                    Integer type1 = menuDTO.getType();
                    //如果是按钮
                    if(MenuType.CLICK.getCode().equals(type1)) {
                        child.setType(MenuType.CLICK.getDesc());
                        child.setName(menuDTO.getName());
                        child.setKey(menuDTO.getKeywords());
                    }
                    //如果是view
                    if(MenuType.VIEW.getCode().equals(type1)) {
                        child.setType(MenuType.VIEW.getDesc());
                        child.setName(menuDTO.getName());
                        child.setUrl(menuDTO.getUri());
                    }
                    childList.add(child);
                }
                wechatMenu.setSub_button(childList);
            }

            menuList.add(wechatMenu);
        }
        listMap.put("button",menuList);

        logger.info("listMpa {}",JsonUtil.toJson(listMap));
        String accessToken = WechatUtils.getAccessToken(wechatPlatform.getAppId(), wechatPlatform.getAppSecret());
        String url = WechatCofig.CREATE_MENU.replace("ACCESS_TOKEN", accessToken);
        String data = HttpRequestUtil.HttpsDefaultExecute(HttpRequestUtil.POST_METHOD, url,
                JsonUtil.toJson(listMap), "", 0, "false");
        JSONObject jsonObject = JSONObject.parseObject(data);

        return jsonObject.getString("errcode");
    }



    public static void main(String[] args) {
        String accessToken = getAccessToken(WechatCofig.APPID, WechatCofig.APPSECRET);
        System.out.println(accessToken);
    }

    public static String getAccessToken(String appId, String appSecret) {

        String url = WechatCofig.GET_ACCESS_TOKEN.replace("APPID", appId).replace("APPSECRET", appSecret);
        String data = HttpRequestUtil.
                HttpsDefaultExecute(HttpRequestUtil.GET_METHOD, url, "", "", 0, "false");
        JSONObject jsonObject = JSONObject.parseObject(data);
        return jsonObject.getString("access_token");
    }
}
