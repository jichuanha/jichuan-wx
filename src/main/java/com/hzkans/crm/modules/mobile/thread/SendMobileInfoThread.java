package com.hzkans.crm.modules.mobile.thread;

import com.hzkans.crm.modules.mobile.utils.Config;
import com.hzkans.crm.modules.mobile.utils.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jc
 * @description 发送手机短信
 * @create 2018/12/26
 */
public class SendMobileInfoThread implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(SendMobileInfoThread.class);

    private static String operation = "/industrySMS/sendSMS";

    private String receiverPhoneNumber;
    private String content;


    @Override
    public void run() {
        try {
            String url = Config.BASE_URL + operation;
            String body = "accountSid=" + Config.ACCOUNT_SID + "&to=" + receiverPhoneNumber + "&smsContent=" + content
                    + HttpUtil.createCommonParam();

            // 提交请求
            String result = HttpUtil.post(url, body);
            logger.info("[{}] result:{}", System.lineSeparator() + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getReceiverPhoneNumber() {
        return receiverPhoneNumber;
    }

    public void setReceiverPhoneNumber(String receiverPhoneNumber) {
        this.receiverPhoneNumber = receiverPhoneNumber;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
