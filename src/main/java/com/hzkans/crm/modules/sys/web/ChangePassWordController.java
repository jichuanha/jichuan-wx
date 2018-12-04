/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.hzkans.crm.modules.sys.web;

import com.google.common.base.Strings;
import com.hzkans.crm.common.config.Global;
import com.hzkans.crm.common.constant.ResponseEnum;
import com.hzkans.crm.common.service.ServiceException;
import com.hzkans.crm.common.utils.*;
import com.hzkans.crm.common.web.BaseController;
import com.hzkans.crm.modules.sys.service.ChangePasswordService;
import com.hzkans.crm.modules.sys.entity.User;
import com.hzkans.crm.modules.sys.service.SystemService;
import com.hzkans.crm.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("${adminPath}/changePassword")
public class ChangePassWordController extends BaseController {
    private static int LENGTH = 6;

    @Autowired
    private SystemService systemService;
    @Autowired
    private ChangePasswordService changePasswordService;
    @RequestMapping(value = "/link_send_mail")
    public String gotoSendMail() {
        return "modules/pswmanage/sendEmail";
    }

    /**
     * 发送邮件
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "send_mail")
    public String sendMail(HttpServletRequest request) throws Exception {
        try {
            String loginName = RequestUtils.getString(request, false, "login_name", "");
            String verifyCode = RequestUtils.getString(request, false, "verify_code", "");
            String code = (String) request.getSession().getAttribute("validateCode");

            logger.info("[{}]verifyCode vs [{}]code", JsonUtil.toJson(verifyCode.toLowerCase()), JsonUtil.toJson(code.toLowerCase()));
            // 判断验证码为空
            if (StringUtils.isEmpty(verifyCode)) {
                return ResponseUtils.getFailApiResponseStr(ResponseEnum.B_E_VERYFY_CODE_IS_NULL, ResponseEnum.B_E_VERYFY_CODE_IS_NULL.getMsg());
            }
            // 判断验证码输入是否正确
            if (!verifyCode.toLowerCase().equals(code.toLowerCase())) {
                return ResponseUtils.getFailApiResponseStr(ResponseEnum.B_E_VERYFY_CODE_INVALID, ResponseEnum.B_E_VERYFY_CODE_INVALID.getMsg());
            }

            User user = UserUtils.getByLoginName(loginName);
            if (null != user) {
                changePasswordService.sendMail(user);
            }else {
                return ResponseUtils.getFailApiResponseStr(ResponseEnum.B_E_USER_AUTH_INFO_NOT_EXIST);
            }
            return ResponseUtils.getSuccessApiResponseStr(true);
        } catch (Exception e) {
            logger.error("sendMail is error",e);
            return ResponseUtils.getFailApiResponseStr(ResponseEnum.B_E_SEND_MAIL_ERROR);
        }
    }

    /**
     * 校验链接的正确性
     * @param request
     * @param model
     * @return String
     * @throws Exception
     */
    @RequestMapping(value = "/verification")
    public String verification(HttpServletRequest request, Model model) throws Exception {
        try {
            Integer cid = RequestUtils.getInt(request, "cid", false, "id is null", "");

            String userId = RequestUtils.getString(request, false, "user_id", "id is null");

            String userName = RequestUtils.getString(request, false, "user_name", "user_name is null");

            String sid = RequestUtils.getString(request, false, "sid", "sid is null");

            if (Strings.isNullOrEmpty(sid) || Strings.isNullOrEmpty(userId)) {
                model.addAttribute("mesg", "链接不完整,请重新生成");
                return "modules/pswmanage/linkError";
            }
            String result = changePasswordService.verification(cid,userId,userName,sid);
            if (Global.TRUE.equals(result)){
                User user = UserUtils.get(userId);
                model.addAttribute("id", user.getId());
                model.addAttribute("loginName", user.getLoginName());
                UserUtils.clearCache();
                return "modules/pswmanage/foundPsw";
            }else {
                model.addAttribute("mesg", result);
                return "modules/pswmanage/linkError";
            }
        } catch (Exception e) {
            logger.error("verification is error",e);
            return ResponseUtils.getFailApiResponseStr(ResponseEnum.B_E_SEND_MAIL_ERROR);
        }
    }

    /**
     * 修改个人用户密码
     *
     * @return String
     */
    @RequestMapping(value = "/modify_pwd")
    @ResponseBody
    public String modifyPwd(HttpServletRequest request) {
        try {
            String id = RequestUtils.getString(request, false, "id", "id is null");
            String newPassword = RequestUtils.getString(request, false, "new_password", "user_name is null");

            logger.info("[{}]",JsonUtil.toJson(newPassword));
            if (StringUtils.isBlank(newPassword)){
                return ResponseUtils.getFailApiResponseStr(ResponseEnum.B_E_PASSWORD_IS_NULL);
            }else if(newPassword.length() < LENGTH) {
                return ResponseUtils.getFailApiResponseStr(ResponseEnum.B_E_PASSWORD_LENGTH_ERROR);
            } else {
                User user = UserUtils.get(id);
                systemService.updatePasswordById(user.getId(), user.getLoginName(), newPassword);
                UserUtils.clearCache();
                return ResponseUtils.getSuccessApiResponseStr(true);
            }
        } catch (ServiceException e) {
            logger.error("modifyPwd is error",e);
            return ResponseUtils.getFailApiResponseStr(ResponseEnum.B_E_SEND_MAIL_ERROR);
        }
    }
}
