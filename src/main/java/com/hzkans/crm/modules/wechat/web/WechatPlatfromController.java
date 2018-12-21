package com.hzkans.crm.modules.wechat.web;


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
import com.hzkans.crm.modules.wechat.entity.WechatPlatfrom;
import com.hzkans.crm.modules.wechat.service.WechatPlatfromService;
import com.hzkans.crm.modules.wxapi.constants.WechatCofig;
import com.hzkans.crm.modules.wxapi.utils.HttpRequestUtil;
import com.hzkans.crm.modules.wxapi.utils.WechatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
            Long id = RequestUtils.getLong(request, "id", false, "id is null", "");

            WechatPlatfrom wechatPlatfrom = wechatPlatfromService.getWechatPlatformById(id);
            model.addAttribute("id", wechatPlatfrom.getId());
            model.addAttribute("name", wechatPlatfrom.getName());
            model.addAttribute("mainPart", wechatPlatfrom.getMainPart());
            model.addAttribute("wechat_no", wechatPlatfrom.getWechatNo());
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

            WechatPlatfrom wechatPlatfrom = new WechatPlatfrom();
            wechatPlatfrom.setName(name);
            wechatPlatfrom.setCreateBy(user.getName());
            wechatPlatfrom.setUpdateBy(user.getName());
            wechatPlatfrom.setMainPart(mainPart);
            wechatPlatfrom.setAppSecret(appSecret);
            wechatPlatfrom.setToken(token);
            wechatPlatfrom.setAppId(appId);
            wechatPlatfrom.setWechatNo(wechatNo);

            wechatPlatfromService.addWechatPlatform(wechatPlatfrom);
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
            Long id = RequestUtils.getLong(request, "id", false, "id is null", "");
            String newMainPart = RequestUtils.getString(request, true, "new_main_part", "new_main_part is null");

            User user = UserUtils.getUser();
            if (null == user) {
                return ResponseUtils.getFailApiResponseStr(ResponseEnum.B_E_SESSION_TIMEOUT, ResponseEnum.B_E_SESSION_TIMEOUT.getMsg());
            }

            WechatPlatfrom wechatPlatfrom = new WechatPlatfrom();
            wechatPlatfrom.setMainPart(newMainPart);
            wechatPlatfrom.setId(id);
            wechatPlatfrom.setUpdateBy(user.getName());
            wechatPlatfromService.updateWechatPlatform(wechatPlatfrom);
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
            Long id = RequestUtils.getLong(request, "id", false, "id is null", "");

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
            List<WechatPlatfrom> allWechatPlatform;
            allWechatPlatform = wechatPlatfromService.listWechatPlatform(new WechatPlatfrom());
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
            Long id = RequestUtils.getLong(request, "id", false, "id is null", "");
            String appSecret = RequestUtils.getString(request, false, "app_secret", "app_secret is null");
            String token = RequestUtils.getString(request, false, "token", "token is null");
            String appId = RequestUtils.getString(request, false, "app_id", "token is null");

            User user = UserUtils.getUser();
            if (null == user) {
                return ResponseUtils.getFailApiResponseStr(ResponseEnum.B_E_SESSION_TIMEOUT, ResponseEnum.B_E_SESSION_TIMEOUT.getMsg());
            }

            WechatPlatfrom wechatPlatfrom = new WechatPlatfrom();
            wechatPlatfrom.setId(id);
            wechatPlatfrom.setUpdateBy(user.getName());
            wechatPlatfrom.setAppSecret(appSecret);
            wechatPlatfrom.setToken(token);
            wechatPlatfrom.setToken(appId);
            //绑定状态为1
            wechatPlatfrom.setBindingFlag(1);

            wechatPlatfromService.updateWechatPlatform(wechatPlatfrom);
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

        Long wechatId = RequestUtils.getLong(request, "wechat_id", false, "id is null", "");
        Integer type = RequestUtils.getInt(request, "fileType", "fileType is null");
        MessageTypeEnum messageTypeEnum = MessageTypeEnum.getMessageTypeEnum(type);
        if(null == messageTypeEnum) {
            return ResponseUtils.getFailApiResponseStr(ResponseEnum.B_E_MATE_TYPE_ERROR);
        }
        Map<String, Object> resultMap = new HashMap<>();
        JSONObject object = null;
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
            //上传到微信素材(只有上传是图片和语音类型走,图文中图片的上传不走这个接口)
            if(MessageTypeEnum.IMAGE.getSign().equals(type) || MessageTypeEnum.VOICE.getSign().equals(type)) {
                String accessToken  = WechatUtils.getAccessToken(wechatPlatfromService,wechatId);
                logger.info("accessToken:"+accessToken);
                String url = WechatCofig.UPLOAD_MEDIA
                        .replace("ACCESS_TOKEN", accessToken)
                        .replace("TYPE",  messageTypeEnum.getCode());

                String result = HttpRequestUtil.uploadMaterial(newFile, MessageTypeEnum.IMAGE.getCode(), url, "",
                        "", "", 0, "false");
                logger.info(" result : {}",result);
                object = JSONObject.parseObject(result);
                Object errmsg = object.get("errmsg");
                if(errmsg != null) {
                    return ResponseUtils.getFailApiResponseStr(ResponseEnum.B_E_WEHCAT_NUM_ERROR);
                }
                resultMap.put("mediaId", object.get("media_id"));
            }
            if(newFile.exists()) {
                newFile.delete();
            }
            resultMap.put("path", newPath);
            logger.info(" resultMap {}",JsonUtil.toJson(resultMap));
            return ResponseUtils.getSuccessApiResponseStr(resultMap);
        } catch (Exception e) {
            return ResponseUtils.getFailApiResponseStr(ResponseEnum.S_E_SERVICE_ERROR);
        }
    }

}