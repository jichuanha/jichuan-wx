/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.hzkans.crm.modules.pswmanage.web;

import com.google.common.base.Strings;
import com.hzkans.crm.common.constant.ResponseEnum;
import com.hzkans.crm.common.service.ServiceException;
import com.hzkans.crm.common.servlet.ValidateCodeServlet;
import com.hzkans.crm.common.utils.*;
import com.hzkans.crm.common.web.BaseController;
import com.hzkans.crm.modules.pswmanage.entity.ChangePasswordDO;
import com.hzkans.crm.modules.pswmanage.service.ChangePasswordService;
import com.hzkans.crm.modules.pswmanage.utils.MD5Util;
import com.hzkans.crm.modules.sys.entity.User;
import com.hzkans.crm.modules.sys.service.SystemService;
import com.hzkans.crm.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.mail.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/changePassword")
public class ChangePassWordController extends BaseController {
    //发送链接的地址
    private static String url = "http://10.1.35.118:8181/dongyin-CRM/changePassword/verification";

    @Autowired
    private ChangePasswordService changePasswordService;
    @Autowired
    private SystemService systemService;
    @RequestMapping(value = "/validateCode")
    @ResponseBody
    public void imagecode1(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            ValidateCodeServlet v = new ValidateCodeServlet();
            v.doPost(request,response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @RequestMapping(value = "/gotoSendMail")
    public String gotoSendMail()  {
        return "modules/pswmanage/sendEmail";
    }
    @ResponseBody
    @RequestMapping(value = "sendMail")
    public String sendMail(HttpServletRequest request, Model model) throws Exception {
        try {
            String loginName = RequestUtils.getString(request, false, "login_name", "");

            String verifyCode = RequestUtils.getString(request, false, "verify_code", "");

            String code = (String) request.getSession().getAttribute("validateCode");


            logger.info("[{}]code",JsonUtil.toJson(code.toLowerCase()));
            logger.info("[{}]verifyCode",JsonUtil.toJson(verifyCode.toLowerCase()));
            // 获得验证码对象
            if (StringUtils.isEmpty(verifyCode)) {
                String message = "请输入验证码";
                return ResponseUtils.getFailApiResponseStr(ResponseEnum.S_E_SERVICE_ERROR,message);
            }
            // 判断验证码输入是否正确
            if (!verifyCode.toLowerCase().equals(code.toLowerCase())) {
                String message = "验证码错误，请重新输入！";
                return ResponseUtils.getFailApiResponseStr(ResponseEnum.S_E_SERVICE_ERROR,message);
            }
            User user = UserUtils.getByLoginName(loginName);
            logger.info("[{}]user+++", JsonUtil.toJson(user));
            if (user != null) {
                //传入要发送的邮箱，用户id用户名
                sendEmil(user.getLoginName(), user.getId(), user.getName());
                UserUtils.clearCache();
                return ResponseUtils.getSuccessApiResponseStr(true);
            } else {
                String message = "没有该用户";
                return ResponseUtils.getFailApiResponseStr(ResponseEnum.S_E_SERVICE_ERROR,message);
            }
        } catch (Exception e) {
            logger.info("sendMail is error");
            return ResponseUtils.getFailApiResponseStr(ResponseEnum.S_E_SERVICE_ERROR,e.getMessage());
        }

    }


    /**
     * 重置密码发送邮件
     *
     * @throws MessagingException
     */
    public String sendEmil(String Femail, String id, String userName) throws Exception {

        String validataCode = UUID.randomUUID().toString();  //密钥
        Date outDate = new Date((System.currentTimeMillis() + 30 * 60 * 1000) / 1000 * 1000);//30分钟后过期
        logger.info("[{}]changePasswordDO.getRegisterDate()++++", JsonUtil.toJson(outDate));
        long date = outDate.getTime() / 1000 * 1000;                  //忽略毫秒数
        ChangePasswordDO changePasswordDO = new ChangePasswordDO();
        changePasswordDO.setValidataCode(validataCode);
        changePasswordDO.setRegisterDate(outDate);
        changePasswordDO.setUserId(id);
        try {
            int changePasswordId = changePasswordService.insterChangePassword(changePasswordDO);    //保存到数据库
            String key = userName + "$" + date + "$" + validataCode;
            String digitalSignature = MD5Util.getMD5(key);                 //数字签名
            String resetPassHref = "?cid=" + changePasswordId +
                    "&id=" + id + "&sid=" + digitalSignature+ "&user_name=" + userName;
            // 设置邮件内容
            String msgContent = "<h1 style='color:blue' align='center'>欢迎使用CRM系统！<h1><hr style='color:blue' align='center' width ='50%'>"
                    +"<div style='margin:0 auto;width:600px;height:1000px'>"+
                    "尊敬的 " + Femail + ":  </br>"
                    +"&nbsp;&nbsp;&nbsp;&nbsp;您好!  </br></br>"
                    +"&nbsp;&nbsp;&nbsp;&nbsp;系统收到了您发出的“修改密码”的请求，请点  </br></br>"
                    +"击下面按钮进行修改！  </br></br>"
                    +"&nbsp;&nbsp;&nbsp;&nbsp;若这封邮件并非应您要求寄发，请您忽略不须  </br></br>"
                    +"予以理会，这不会对您的账户安全造成任何影响。  </br></br>"
                    +"&nbsp;&nbsp;&nbsp;&nbsp;此为自动发送邮件，请勿直接回复！ </br></br></br></br></br> "
                    + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href=" + url + resetPassHref + ">" + "修改密码" + "</a></br>"
                    +"</div>";

            SendMailUtil.sendCommonMail(Femail, "修改密码",
                    msgContent);
            return "";
        } catch (Exception e) {
            logger.info("sendMail is error");
            return ResponseUtils.getFailApiResponseStr(ResponseEnum.S_E_SERVICE_ERROR,e.getMessage());

        }

    }


    @RequestMapping(value = "/verification")
    public String test1(HttpServletRequest request, Model model) throws Exception {
        try {
            Integer cid = RequestUtils.getInt(request, "cid", false, "id is null", "");

            String id = RequestUtils.getString(request, false, "id", "id is null");

            String userName = RequestUtils.getString(request, false, "user_name", "user_name is null");

            String sid = RequestUtils.getString(request, false, "sid", "sid is null");

            if (Strings.isNullOrEmpty(sid) || Strings.isNullOrEmpty(id)) {
                model.addAttribute("mesg", "链接不完整,请重新生成");
                return "modules/pswmanage/linkError";
            }
            ChangePasswordDO changePasswordDOtemp1 = new ChangePasswordDO();
            changePasswordDOtemp1.setId(cid);
            List<ChangePasswordDO> changePasswordDOsc = changePasswordService.selectChangePassword(changePasswordDOtemp1);

            //获取最新的验证信息
            ChangePasswordDO changePasswordDOtemp2 = new ChangePasswordDO();
            changePasswordDOtemp2.setUserId(id);
            List<ChangePasswordDO> changePasswordDOnew = changePasswordService.selectChangePassword(changePasswordDOtemp2);

            //判断该链接是不是最新的验证的信息
            if (!changePasswordDOsc.get(0).getValidataCode().equals(changePasswordDOnew.get(0).getValidataCode())){
                model.addAttribute("mesg", "不是最新的验证链接");
                return "modules/pswmanage/linkError";
            }
            if (changePasswordDOsc.get(0).getRegisterDate().getTime() <= System.currentTimeMillis()) { //表示已经过期
                model.addAttribute("mesg", "链接已经过期,请重新申请找回密码.");
                return "modules/pswmanage/linkError";
            }
            String key = userName + "$" + changePasswordDOsc.get(0).getRegisterDate().getTime() / 1000 * 1000 + "$" + changePasswordDOsc.get(0).getValidataCode();//数字签名

            String digitalSignature = MD5Util.getMD5(key);                 //数字签名
            if (!digitalSignature.equals(sid)) {
                model.addAttribute("mesg", "链接不正确,是否已经过期了?重新申请吧.");
                return "modules/pswmanage/linkError";
            } else {
                //链接验证通过 转到修改密码页面
                User user = UserUtils.get(id);
                model.addAttribute("id", user.getId());
                model.addAttribute("loginName", user.getLoginName());
                UserUtils.clearCache();
                return "modules/pswmanage/foundPsw";
            }
        } catch (Exception e) {
            logger.info("verification is error");
            return ResponseUtils.getFailApiResponseStr(ResponseEnum.S_E_SERVICE_ERROR,e.getMessage());
        }
    }
    /**
     * 修改个人用户密码
     * @return
     */
    @RequestMapping(value = "/modifyPwd")
    @ResponseBody
    public String modifyPwd(HttpServletRequest request) {
        try {
            String id = RequestUtils.getString(request, false, "id", "id is null");

            String newPassword = RequestUtils.getString(request, false, "new_password", "user_name is null");

            User user = UserUtils.get(id);
            if (StringUtils.isNotBlank(newPassword) && newPassword.length() > 5){
                systemService.updatePasswordById(user.getId(), user.getLoginName(), newPassword);
            }else {
                UserUtils.clearCache();
                return ResponseUtils.getFailApiResponseStr(ResponseEnum.S_E_SERVICE_ERROR, "新密码为空");
            }
            UserUtils.clearCache();
            return ResponseUtils.getSuccessApiResponseStr(true);
        } catch (ServiceException e) {
            logger.info("modifyPwd is error");
            return ResponseUtils.getFailApiResponseStr(ResponseEnum.S_E_SERVICE_ERROR,e.getMessage());
        }
    }
}
