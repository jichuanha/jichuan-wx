package com.hzkans.crm.modules.wechat.web;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.hzkans.crm.common.constant.ResponseEnum;
import com.hzkans.crm.common.utils.JsonUtil;
import com.hzkans.crm.common.utils.RequestUtils;
import com.hzkans.crm.common.utils.ResponseUtils;
import com.hzkans.crm.common.utils.WxOSSClient;
import com.hzkans.crm.common.web.BaseController;
import com.hzkans.crm.modules.sys.entity.User;
import com.hzkans.crm.modules.sys.utils.UserUtils;
import com.hzkans.crm.modules.trade.utils.TradeUtil;
import com.hzkans.crm.modules.wechat.constants.MessageTypeEnum;
import com.hzkans.crm.modules.wechat.entity.CustomMainMenuDTO;
import com.hzkans.crm.modules.wechat.service.WechatPlatfromService;
import com.hzkans.crm.modules.wechat.utils.HttpRequestUtil;
import com.hzkans.crm.modules.wechat.utils.WechatCofig;
import com.hzkans.crm.modules.wechat.utils.WechatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.hzkans.crm.modules.wechat.entity.WechatPlatfromDO;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


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
    @Autowired
    private WxOSSClient wxOSSClient;

    private static final String UPLOAD_EX = "ex";

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

    @RequestMapping(value = "/link_home_page")
    public String gotoHomePage(HttpServletRequest request,Model model) {
        Integer id = RequestUtils.getInt(request, "id", false, "id is null", "");
        model.addAttribute("wechat_platfrom_id",id);
        return "modules/sys/sysIndex";
    }

    /**
     * 进入修改微信公众号页面
     * @param request
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/link_update")
    public String gotoUpdate(HttpServletRequest request, Model model) throws Exception {
        try {
            Integer id = RequestUtils.getInt(request, "id", false, "id is null", "");

            WechatPlatfromDO wechatPlatfromDO = wechatPlatfromService.getWechatPlatformById(id);
            model.addAttribute("id", wechatPlatfromDO.getId());
            model.addAttribute("name", wechatPlatfromDO.getName());
            model.addAttribute("mainPart", wechatPlatfromDO.getMainPart());
            model.addAttribute("wechat_no", wechatPlatfromDO.getWechatNo());
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
            String appSecret = RequestUtils.getString(request, true, "app_secret", "app_secret is null");
            String token = RequestUtils.getString(request, true, "token", "token is null");
            String appId = RequestUtils.getString(request, true, "app_id", "token is null");
            String wechatNo = RequestUtils.getString(request, false, "wechat_no", "token is null");

            User user = UserUtils.getUser();
            if (null == user) {
                return ResponseUtils.getFailApiResponseStr(ResponseEnum.B_E_SESSION_TIMEOUT);
            }

            WechatPlatfromDO wechatPlatfromDO = new WechatPlatfromDO();
            wechatPlatfromDO.setName(name);
            wechatPlatfromDO.setCreateBy(user.getName());
            wechatPlatfromDO.setUpdateBy(user.getName());
            wechatPlatfromDO.setMainPart(mainPart);
            wechatPlatfromDO.setAppSecret(appSecret);
            wechatPlatfromDO.setToken(token);
            wechatPlatfromDO.setAppId(appId);
            wechatPlatfromDO.setWechatNo(wechatNo);

            wechatPlatfromService.addWechatPlatform(wechatPlatfromDO);
            return ResponseUtils.getSuccessApiResponseStr(true);
        } catch (Exception e) {
            logger.error("addWechatPlatform is error", e);
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
            logger.info("selectWechatPlatformById is error",e);
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
            logger.error("selectWechatPlatformById is error",e);
            return ResponseUtils.getFailApiResponseStr(ResponseEnum.B_E_RESULT_IS_NULL);
        }
    }

    /**
     * 绑定微信公众号
     * @param request
     * @return
     */
    @RequestMapping(value = "/binding_wechat_latform")
    public String bindingWechatPlatform(HttpServletRequest request){
        try {
            Integer id = RequestUtils.getInt(request, "id", false, "id is null", "");
            String appSecret = RequestUtils.getString(request, false, "app_secret", "app_secret is null");
            String token = RequestUtils.getString(request, false, "token", "token is null");
            String appId = RequestUtils.getString(request, false, "app_id", "token is null");

            User user = UserUtils.getUser();
            if (null == user) {
                return ResponseUtils.getFailApiResponseStr(ResponseEnum.B_E_SESSION_TIMEOUT, ResponseEnum.B_E_SESSION_TIMEOUT.getMsg());
            }

            WechatPlatfromDO wechatPlatfromDO = new WechatPlatfromDO();
            wechatPlatfromDO.setId(id);
            wechatPlatfromDO.setUpdateBy(user.getName());
            wechatPlatfromDO.setAppSecret(appSecret);
            wechatPlatfromDO.setToken(token);
            wechatPlatfromDO.setToken(appId);
            //绑定状态为1
            wechatPlatfromDO.setBindingFlag(1);

            wechatPlatfromService.updateWechatPlatform(wechatPlatfromDO);
            return ResponseUtils.getSuccessApiResponseStr(true);
        } catch (Exception e) {
            logger.error("bindingWechatPlatform is error", e);
            return ResponseUtils.getFailApiResponseStr(ResponseEnum.B_E_MODIFY_ERROR);
        }
    }

    /**
     * 图片和音频的上传
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/upload")
    @ResponseBody
    public String uploadImg(HttpServletRequest request, HttpServletResponse response) {

        Integer wechatId = RequestUtils.getInt(request, "wechat_id", "wechat_id is null");
        Integer type = RequestUtils.getInt(request, "fileType", "type is null");
        MessageTypeEnum messageTypeEnum = MessageTypeEnum.getMessageTypeEnum(type);
        if(null == messageTypeEnum) {
            return ResponseUtils.getFailApiResponseStr(ResponseEnum.B_E_MATE_TYPE_ERROR);
        }
        try {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            MultipartFile file = multipartRequest.getFile("file");
            String originalFilename = file.getOriginalFilename();
            logger.info("originalFilename {}",originalFilename);
            String replace = UUID.randomUUID().toString().replace("-", "");
            String realPath = TradeUtil.UPLOAD_ADDRESS+originalFilename;
            String newPath = replace+originalFilename;
            logger.info("realPath {}",realPath);
            File newFile = new File(realPath);
            if(!newFile.exists()) {
                newFile.mkdirs();
            }
            //先保存到本地,在上传到阿里云,最后删除本地文件
            file.transferTo(newFile);
            wxOSSClient.uploadFile(UPLOAD_EX,newPath,realPath);
            //上传到微信素材
            WechatPlatfromDO wechat = wechatPlatfromService.getWechatPlatformById(wechatId);
            logger.info("wechatPlatform {}", JsonUtil.toJson(wechat));

            String url = WechatCofig.UPLOAD_MEDIA
                    .replace("ACCESS_TOKEN", WechatUtils.getAccessToken(wechat.getAppId(), wechat.getAppSecret()))
                    .replace("TYPE",  messageTypeEnum.getCode());

            String result = HttpRequestUtil.uploadMaterial(newFile, MessageTypeEnum.IMAGE.getCode(), url, "",
                    "", "", 0, "false");
            logger.info(" result : {}",result);
            if(newFile.exists()) {
                newFile.delete();
            }
            JSONObject object = JSONObject.parseObject(result);
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("path", newPath);
            resultMap.put("mediaId", object.get("media_id"));
            logger.info(" resultMap {}",JsonUtil.toJson(resultMap));
            return ResponseUtils.getSuccessApiResponseStr(resultMap);
        } catch (Exception e) {
            return ResponseUtils.getFailApiResponseStr(ResponseEnum.S_E_SERVICE_ERROR);
        }

    }

}