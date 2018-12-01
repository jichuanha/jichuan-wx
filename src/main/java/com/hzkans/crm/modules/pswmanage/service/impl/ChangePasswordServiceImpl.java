package com.hzkans.crm.modules.pswmanage.service.impl;

import com.hzkans.crm.common.config.Global;
import com.hzkans.crm.common.constant.ResponseEnum;
import com.hzkans.crm.common.utils.JsonUtil;
import com.hzkans.crm.common.utils.ResponseUtils;
import com.hzkans.crm.common.utils.SendMailUtil;
import com.hzkans.crm.modules.pswmanage.dao.ChangePasswordDAO;
import com.hzkans.crm.modules.pswmanage.entity.ChangePasswordDO;
import com.hzkans.crm.modules.pswmanage.service.ChangePasswordService;
import com.hzkans.crm.modules.pswmanage.utils.MD5Util;
import com.hzkans.crm.modules.sys.entity.User;
import com.hzkans.crm.modules.sys.utils.UserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created with IDEA
 * @author:dengtm
 * Date:2018/11/23
 * Time:21:59
 */
public class ChangePasswordServiceImpl implements ChangePasswordService {
    Logger logger = LoggerFactory.getLogger(ChangePasswordServiceImpl.class);
    /**
     * 链接的地址
     */
    private final static String URL = "http://10.1.35.118:8181/dongyin-CRM/a/changePassword/verification";
    private final static String TOKEN_SEPRATOR= "$";
    private final static String EMAIL_SUBJECT= "修改密码";
    @Autowired
    public ChangePasswordDAO changePasswordADO;

    @Override
    public String sendMail(User user) {

        //传入要发送的邮箱，用户id用户名
        logger.info("[{}]user+++", JsonUtil.toJson(user));
        String mailBox = user.getLoginName();
        String userName = user.getName();
        String id = user.getId();
        //密钥
        String validataCode = UUID.randomUUID().toString();
        //30分钟后过期
        Date outTimeDate = new Date((System.currentTimeMillis() + 30 * 60 * 1000) / 1000 * 1000);
        logger.info("[{}]changePasswordDO.getRegisterDate()++++", JsonUtil.toJson(outTimeDate));
        //忽略毫秒数
        long date = outTimeDate.getTime() / 1000 * 1000;

        ChangePasswordDO changePasswordDO = new ChangePasswordDO();
        changePasswordDO.setUserId(user.getId());
        changePasswordDO.setValidataCode(validataCode);
        changePasswordDO.setRegisterDate(outTimeDate);

        try {
            //保存到数据库
            changePasswordADO.inster(changePasswordDO);
            //组合信息
            String key = new StringBuilder().append(userName).append(TOKEN_SEPRATOR).append(date)
                    .append(TOKEN_SEPRATOR).append(validataCode).toString();
            //数字签名
            String digitalSignature = MD5Util.getMD5(key);
            String resetPassHref = "?cid=" + changePasswordDO.getId() +
                    "&user_id=" + id + "&sid=" + digitalSignature + "&user_name=" + userName;
            // 设置邮件内容
            SendMailUtil.sendCommonMail(mailBox, EMAIL_SUBJECT, this.mailContent(mailBox, resetPassHref));
            // 清楚缓存
            UserUtils.clearCache();
            return ResponseUtils.getSuccessApiResponseStr(true);
        } catch (Exception e) {
            logger.error("sendMail msg:", e);
            return ResponseUtils.getFailApiResponseStr(ResponseEnum.B_E_SEND_MAIL_ERROR);
        }
    }


    @Override
    public String verification(Integer cid, String userId, String userName, String sid) {
        try {
            //获取当前链接的验证信息
            ChangePasswordDO inputId = new ChangePasswordDO();
            inputId.setId(cid);
            List<ChangePasswordDO> currentResult = changePasswordADO.selectChangePassword(inputId);

            logger.info("[{}]", JsonUtil.toJson(currentResult));
            //获取最新的验证信息
            ChangePasswordDO inputUserId = new ChangePasswordDO();
            inputUserId.setUserId(userId);
            List<ChangePasswordDO> newResult = changePasswordADO.selectChangePassword(inputUserId);

            //判断该链接是不是最新的验证的信息
            if (!currentResult.get(0).getValidataCode().equals(newResult.get(0).getValidataCode())) {
                return ResponseEnum.B_E_VERIFY_LINK_NOT_NEW.getMsg();
            }
            if (currentResult.get(0).getRegisterDate().getTime() <= System.currentTimeMillis()) {
                //表示已经过期
                return ResponseEnum.B_E_VERIFY_LINK_EXPIRES.getMsg();
            }
            long date = currentResult.get(0).getRegisterDate().getTime() / 1000 * 1000;
            String key = new StringBuilder().append(userName).append(TOKEN_SEPRATOR).append(date)
                    .append(TOKEN_SEPRATOR).append(currentResult.get(0).getValidataCode()).toString();
            //数字签名
            String digitalSignature = MD5Util.getMD5(key);
            if (!digitalSignature.equals(sid)) {
                return ResponseEnum.B_E_VERIFY_LINK_IS_ERROE.getMsg();
            } else {
                //链接验证通过 转到修改密码页面
                return Global.TRUE;
            }
        } catch (Exception e) {
            logger.error("verification msg:", e);
            return ResponseUtils.getFailApiResponseStr(ResponseEnum.B_E_SEND_MAIL_ERROR);
        }

    }

    private String mailContent(String toName, String resetPassHref) {
        return "<h1 style='color:blue' align='center'>欢迎使用CRM系统！<h1><hr style='color:blue' align='center' width ='50%'>"
                + "<div style='margin:0 auto;width:600px;height:1000px'>" +
                "尊敬的 " + toName + ":  </br>"
                + "&nbsp;&nbsp;&nbsp;&nbsp;您好!  </br></br>"
                + "&nbsp;&nbsp;&nbsp;&nbsp;系统收到了您发出的“修改密码”的请求，请点  </br></br>"
                + "击下面按钮进行修改！  </br></br>"
                + "&nbsp;&nbsp;&nbsp;&nbsp;若这封邮件并非应您要求寄发，请您忽略不须  </br></br>"
                + "予以理会，这不会对您的账户安全造成任何影响。  </br></br>"
                + "&nbsp;&nbsp;&nbsp;&nbsp;此为自动发送邮件，请勿直接回复！ </br></br></br></br></br> "
                + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                "<a href=" + URL + resetPassHref + ">" + "修改密码" + "</a></br>"
                + "</div>";
    }

}
