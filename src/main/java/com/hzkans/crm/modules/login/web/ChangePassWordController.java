/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.hzkans.crm.modules.login.web;

import com.google.common.base.Strings;
import com.hzkans.crm.common.servlet.ValidateCodeServlet;
import com.hzkans.crm.common.utils.*;
import com.hzkans.crm.common.web.BaseController;
import com.hzkans.crm.modules.login.entity.ChangePasswordDO;
import com.hzkans.crm.modules.login.service.ChangePasswordService;
import com.hzkans.crm.modules.login.utils.MD5Util;
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

import java.util.UUID;

@Controller
@RequestMapping("/changePassword")
public class ChangePassWordController extends BaseController {
    //发送链接的地址
    private static String url = "http://localhost:8181/dongyin-CRM/changePassword/verification";

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

    @ResponseBody
    @RequestMapping(value = "sendMail")
    public String updateResetPwd(HttpServletRequest request, Model model) throws Exception {
        try {
            String loginName = RequestUtils.getString(request, false, "login_name", "");

            String verifyCode = RequestUtils.getString(request, false, "verifyc_code", "");

            String code = (String) request.getSession().getAttribute("validateCode");
            // 获得验证码对象
            if (StringUtils.isEmpty(verifyCode)) {
                model.addAttribute("message", "请输入验证码");
            }
            // 判断验证码输入是否正确
            if (!verifyCode.equals(code)) {
                model.addAttribute("message", "验证码错误，请重新输入！");
            }
            User user = UserUtils.getByLoginName(loginName);
            logger.info("[{}]user+++", JsonUtil.toJson(user));
            if (user != null) {
                //传入要发送的邮箱，用户id用户名
                sendEmil(user.getLoginName(), user.getId(), user.getName());
                model.addAttribute("message", "发送成功");

            } else {
                model.addAttribute("message", "没有该用户");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "modules/sys/userModifyPwd";
    }


    /**
     * 重置密码发送邮件
     *
     * @throws MessagingException
     */
    public void sendEmil(String Femail, String id, String userName) throws Exception {

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
            String msgContent = "亲爱的用户 " + userName + " ，您好，<br/><br/>"
                    + "您在" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "提交重置密码的请求。<br/><br/>"
                    + "请打开以下链接重置密码：<br/><br/>"
                    + "<a href=" + url + resetPassHref + ">" + "请点击这里进入修改密码" + "</a><br/><br/>"
                    + "感谢使用本系统。" + "<br/><br/>"
                    + "此为自动发送邮件，请勿直接回复！";

            SendMailUtil.sendCommonMail(Femail, "修改密码",
                    msgContent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @RequestMapping(value = "/verification")
    public String test1(HttpServletRequest request, Model model) throws Exception {
        Integer cid = RequestUtils.getInt(request, "cid", false, "id is null", "");

        String id = RequestUtils.getString(request, false, "id", "id is null");

        String userName = RequestUtils.getString(request, false, "user_name", "user_name is null");

        String sid = RequestUtils.getString(request, false, "sid", "sid is null");

        if (Strings.isNullOrEmpty(sid) || Strings.isNullOrEmpty(id)) {
            model.addAttribute("mesg", "链接不完整,请重新生成");
            return "error";
        }
        ChangePasswordDO changePasswordDO = changePasswordService.selectChangePassword(cid);
        if (changePasswordDO.getRegisterDate().getTime() <= System.currentTimeMillis()) { //表示已经过期
            model.addAttribute("mesg", "链接已经过期,请重新申请找回密码.");
            return "error";
        }
        String key = userName + "$" + changePasswordDO.getRegisterDate().getTime() / 1000 * 1000 + "$" + changePasswordDO.getValidataCode();//数字签名

        String digitalSignature = MD5Util.getMD5(key);                 //数字签名
        if (!digitalSignature.equals(sid)) {
            model.addAttribute("mesg", "链接不正确,是否已经过期了?重新申请吧.");
            return "error";
        } else {
            //链接验证通过 转到修改密码页面
            User user = UserUtils.get(id);
            model.addAttribute("user", user);
            return "modules/sys/test";
        }
    }
    /**
     * 修改个人用户密码
     * @return
     */
    @RequestMapping(value = "modifyPwd")
    public String modifyPwd(HttpServletRequest request, Model model) {
        String id = RequestUtils.getString(request, false, "id", "id is null");

        String newPassword = RequestUtils.getString(request, false, "new_password", "user_name is null");

        User user = UserUtils.get(id);
        if (StringUtils.isNotBlank(newPassword)){
            systemService.updatePasswordById(user.getId(), user.getLoginName(), newPassword);
        }
        model.addAttribute("user", user);
        return "modules/sys/sysLogin";
    }
}
