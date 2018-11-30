package com.hzkans.crm.modules.pswmanage.service.ChangePasswordImpl;

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
 * author:dengtm
 * Date:2018/11/23
 * Time:21:59
 */
public class ChangePasswordImpl implements ChangePasswordService {
    Logger logger = LoggerFactory.getLogger(ChangePasswordImpl.class);
    //发送链接的地址
    private static String url = "http://10.1.35.118:8181/dongyin-CRM/changePassword/verification";
    private static String tokenSeprator = "$";
    private static String emailSubject = "修改密码";
    @Autowired
    public ChangePasswordDAO changePasswordADO;

    public String sendMail(User user) {

            //传入要发送的邮箱，用户id用户名
            logger.info("[{}]user+++", JsonUtil.toJson(user));
            String Femail = user.getLoginName();
            String userName = user.getName();
            String id = user.getId();
            String validataCode = UUID.randomUUID().toString();  //密钥
            Date outTimeDate = new Date((System.currentTimeMillis() + 30 * 60 * 1000) / 1000 * 1000);//30分钟后过期
            logger.info("[{}]changePasswordDO.getRegisterDate()++++", JsonUtil.toJson(outTimeDate));
            long date = outTimeDate.getTime() / 1000 * 1000;                  //忽略毫秒数

            ChangePasswordDO changePasswordDO = new ChangePasswordDO();
            changePasswordDO.setUserId(user.getId());
            changePasswordDO.setValidataCode(validataCode);
            changePasswordDO.setRegisterDate(outTimeDate);

        try {
            changePasswordADO.inster(changePasswordDO);    //保存到数据库
            // --
            String key = new StringBuilder().append(userName).append(this.tokenSeprator).append(date)
                    .append(this.tokenSeprator).append(validataCode).toString();
            String digitalSignature = MD5Util.getMD5(key);                 //数字签名
            String resetPassHref = "?cid=" + changePasswordDO.getId() +
                    "&user_id=" + id + "&sid=" + digitalSignature + "&user_name=" + userName;
            // 设置邮件内容
            SendMailUtil.sendCommonMail(Femail, this.emailSubject,this.mailContent(Femail,resetPassHref));
            // 清楚缓存
            UserUtils.clearCache();
            return ResponseUtils.getSuccessApiResponseStr(true);
        } catch (Exception e) {
            logger.info("sendMail msg:",e);
            return ResponseUtils.getFailApiResponseStr(ResponseEnum.S_E_SERVICE_ERROR, ResponseEnum.S_E_SERVICE_ERROR.getMsg());
        }
    }


    @Override
    public List<ChangePasswordDO> selectChangePassword(ChangePasswordDO changePasswordDO) throws Exception {
        try {
            if (null == changePasswordDO) {
                logger.error("ChangePasswordImpl.selectChangePassword error : id is null ");
                throw new Exception("selectChangePassword.id is null ");
            }
            List<ChangePasswordDO> changePasswordDOtemp = changePasswordADO.selectChangePassword(changePasswordDO);
            return changePasswordDOtemp;
        } catch (Exception e) {
            logger.error("memberAccount error", e);
            throw  new Exception("changePasswordADO.inster is error");
        }
    }

    @Override
    public String verification(Integer cid, String userId, String userName, String sid) {
        try {
            //获取当前链接的验证信息
            ChangePasswordDO inputId = new ChangePasswordDO();
            inputId.setId(cid);
            List<ChangePasswordDO> currentResult = changePasswordADO.selectChangePassword(inputId);

            logger.info("[{}]",JsonUtil.toJson(currentResult));
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
            String key = new StringBuilder().append(userName).append(this.tokenSeprator).append(date)
                    .append(this.tokenSeprator).append(currentResult.get(0).getValidataCode()).toString();
            String digitalSignature = MD5Util.getMD5(key);                 //数字签名
            if (!digitalSignature.equals(sid)) {
                return ResponseEnum.B_E_VERIFY_LINK_IS_ERROE.getMsg();
            } else {
                //链接验证通过 转到修改密码页面
                return "true";
            }
        } catch (Exception e) {
            return ResponseUtils.getFailApiResponseStr(ResponseEnum.S_E_SERVICE_ERROR, ResponseEnum.S_E_SERVICE_ERROR.getMsg());
        }

    }

    private String mailContent(String toName, String resetPassHref){
        return  "<h1 style='color:blue' align='center'>欢迎使用CRM系统！<h1><hr style='color:blue' align='center' width ='50%'>"
                + "<div style='margin:0 auto;width:600px;height:1000px'>" +
                "尊敬的 " + toName + ":  </br>"
                + "&nbsp;&nbsp;&nbsp;&nbsp;您好!  </br></br>"
                + "&nbsp;&nbsp;&nbsp;&nbsp;系统收到了您发出的“修改密码”的请求，请点  </br></br>"
                + "击下面按钮进行修改！  </br></br>"
                + "&nbsp;&nbsp;&nbsp;&nbsp;若这封邮件并非应您要求寄发，请您忽略不须  </br></br>"
                + "予以理会，这不会对您的账户安全造成任何影响。  </br></br>"
                + "&nbsp;&nbsp;&nbsp;&nbsp;此为自动发送邮件，请勿直接回复！ </br></br></br></br></br> "
                + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                "<a href=" + url + resetPassHref + ">" + "修改密码" + "</a></br>"
                + "</div>";
    }

}
