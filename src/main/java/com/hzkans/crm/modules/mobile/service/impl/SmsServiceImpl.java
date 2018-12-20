package com.hzkans.crm.modules.mobile.service.impl;

import com.hzkans.crm.common.constant.ResponseEnum;
import com.hzkans.crm.common.service.ServiceException;
import com.hzkans.crm.common.utils.StringUtils;
import com.hzkans.crm.modules.mobile.entity.SmsSendLogDO;
import com.hzkans.crm.modules.mobile.service.iface.SmsService;
import com.hzkans.crm.modules.mobile.utils.Config;
import com.hzkans.crm.modules.mobile.utils.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


/**
 * Created by lizg on 2017/1/5.
 */
@Service
public class SmsServiceImpl implements SmsService {

	private static Logger logger = LoggerFactory
			.getLogger(SmsServiceImpl.class);

	/* 短信接口配置 */
	private final String spID = "000050";

	private final String password = "yyzws1234";

	private final String accessCode = "1069032239089";

	/* 短信模板 */
	private static String TEMP_CONTENT = null;

	private static String operation = "/industrySMS/sendSMS";

	private static String accountSid = Config.ACCOUNT_SID;



	private String generateSmsContg(String tempSn, String... values) {

		String smsTemplate = "【东印】您的验证码为{vcode}，请于{MINUTE}分钟内正确输入，如非本人操作，请忽略此短信。";

		// 如果不需要模板，直接返回

		if (StringUtils.isBlank(smsTemplate)) {

			StringBuffer sb = new StringBuffer();

			for (String value : values) {
				sb.append(value);
			}
			return sb.toString();
		}

		int index = 0;

		String msg = smsTemplate.replaceFirst("\\{\\w+\\}", values[index++]);

		while (index < values.length) {
			msg = msg.replaceFirst("\\{\\w+\\}", values[index++]);
		}

		return msg;
	}

	/**
	 * 根据模板编号获取模板内容
	 *
	 * @param tempSn
	 * @return
	 */
	private String getSmsTemplate(String tempSn) {

		return TEMP_CONTENT;

	}


	@Override
	public Boolean sendSecondsTick(String receiverPhoneNumber, String smsTemplateCode, String... smsTemplateParamValues) throws ServiceException {

		SmsSendLogDO smsSendLogDO = new SmsSendLogDO();
		smsSendLogDO.setMobile(receiverPhoneNumber);

		try {
			String content = generateSmsContg(smsTemplateCode, smsTemplateParamValues);

			String url = Config.BASE_URL + operation;
			String body = "accountSid=" + accountSid + "&to=" + receiverPhoneNumber + "&smsContent=" + content
					+ HttpUtil.createCommonParam();

			// 提交请求
			String result = HttpUtil.post(url, body);
			logger.info("[{}] result:{}", System.lineSeparator() + result);
			return true;
		} catch (Exception e) {
			logger.error(" sendSecondsTick error:{} ", e);
			throw new ServiceException(ResponseEnum.B_E_MOBILE_SEND_MESSAGE_ERROR);
		}
	}

}
