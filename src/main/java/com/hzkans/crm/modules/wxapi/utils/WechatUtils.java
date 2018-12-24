package com.hzkans.crm.modules.wxapi.utils;

import com.alibaba.fastjson.JSONObject;
import com.hzkans.crm.common.config.Global;
import com.hzkans.crm.common.utils.EhCacheUtils;
import com.hzkans.crm.common.utils.JsonUtil;
import com.hzkans.crm.common.utils.StringUtils;
import com.hzkans.crm.modules.wechat.constants.MessageTypeEnum;
import com.hzkans.crm.modules.wechat.entity.WechatMaterial;
import com.hzkans.crm.modules.wechat.entity.WechatPlatfrom;
import com.hzkans.crm.modules.wechat.message.*;
import com.hzkans.crm.modules.wechat.service.WechatPlatfromService;
import com.hzkans.crm.modules.wxapi.constants.WechatCofig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WechatUtils {

	private final static Logger logger = LoggerFactory.getLogger(WechatUtils.class);
	public static final Integer MAX_NUM = 4;
	/**
	 * 验证签名
	 *
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @return
	 */
	public static boolean checkSignature(String signature, String timestamp,
										 String nonce) {
		// 1.将token、timestamp、nonce三个参数进行字典序排序

		String token = Global.getConfig("wx.token");
		if (StringUtils.isNotBlank(token)){
			return false;
		}
		String[] arr = new String[]{token, timestamp, nonce};
		Arrays.sort(arr);
		// 2. 将三个参数字符串拼接成一个字符串进行sha1加密
		StringBuilder content = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			content.append(arr[i]);
		}
		MessageDigest md = null;
		String tmpStr = null;
		try {
			md = MessageDigest.getInstance("SHA-1");
			// 将三个参数字符串拼接成一个字符串进行sha1加密
			byte[] digest = md.digest(content.toString().getBytes());
			tmpStr = byteToStr(digest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		content = null;

		// 3.将sha1加密后的字符串可与signature对比，标识该请求来源于微信
		return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;
	}

	/**
	 * 将字节数组转换为十六进制字符串
	 *
	 * @param byteArray
	 * @return
	 */
	private static String byteToStr(byte[] byteArray) {
		String strDigest = "";
		for (int i = 0; i < byteArray.length; i++) {
			strDigest += byteToHexStr(byteArray[i]);
		}
		return strDigest;
	}


	/**
	 * 将字节转换为十六进制字符串
	 *
	 * @param mByte
	 * @return
	 */
	private static String byteToHexStr(byte mByte) {
		char[] Digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
				'B', 'C', 'D', 'E', 'F'};
		char[] tempArr = new char[2];
		tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
		tempArr[1] = Digit[mByte & 0X0F];
		String s = new String(tempArr);
		return s;
	}


	/**
	 * @Description 获取微信唯一接口调用凭据 access_token,
	 * access_token的有效期目前为2个小时，需定时刷新，重复获取将导致上次获取的access_token失效。
	 * @param wechatPlatfromService
	 * @return
	 */
	public static String getAccessToken(WechatPlatfromService wechatPlatfromService,Long wechatId) {

		String token = Global.getConfig("wx.token");
		WechatPlatfrom wechatPlatfrom = (WechatPlatfrom)EhCacheUtils.get(WechatCofig.EHCACHE,token);
		if (null == wechatPlatfrom) {
			try {
				 wechatPlatfrom =  wechatPlatfromService.getWechatPlatformById(wechatId);
				if (null != wechatPlatfrom) {
					EhCacheUtils.put(WechatCofig.EHCACHE,token, wechatPlatfrom);
				}
			} catch (Exception e) {
				logger.error("getWechatPlatformById is error",e);
			}
		}

		String appId = wechatPlatfrom.getAppId();
		String appSecret = wechatPlatfrom.getAppSecret();
		Object result = EhCacheUtils.get(WechatCofig.EHCACHE, appId);
		logger.info("result {}",result);
		if(null != result) {
			return (String) result;
		}
		String url = WechatCofig.GET_ACCESS_TOKEN.replace("APPID", appId).replace("APPSECRET", appSecret);
		String data = HttpRequestUtil.HttpsDefaultExecute(HttpRequestUtil.GET_METHOD,url,"","",0,"false");
		JSONObject jsonObject = JSONObject.parseObject(data);
		String accessToken = jsonObject.getString("access_token");
		EhCacheUtils.put(WechatCofig.EHCACHE, appId, accessToken);
		return accessToken;
	}


	/**
	 * 组装微信不同类型的回复消息
	 * @param wechatMaterial
	 * @param wechatNo
	 * @param openId
	 * @return
	 */
	public static  String dealType(WechatMaterial wechatMaterial, String wechatNo, String openId) {
		Integer type = wechatMaterial.getType();
		switch (type) {
			case 0 :
				//文本回复
				ContentMessage message = new ContentMessage();
				message.setToUserName(openId);
				message.setFromUserName(wechatNo);
				message.setCreateTime(System.currentTimeMillis());
				message.setMsgType(MessageTypeEnum.TEXT.getCode());
				message.setContent(wechatMaterial.getContent());
				logger.info(" imageMessage {}",JsonUtil.toJson(message));
				return MessageUtils.messageToXml(message);
			case 1 :
				//图文回复
				ImageMessage imageMessage = new ImageMessage();
				imageMessage.setToUserName(openId);
				imageMessage.setFromUserName(wechatNo);
				imageMessage.setCreateTime(System.currentTimeMillis());
				imageMessage.setMsgType(MessageTypeEnum.IMAGE.getCode());
				Image image = new Image();
				image.setMediaId(wechatMaterial.getMediaId());
				imageMessage.setImage(image);
				logger.info(" imageMessage {}", JsonUtil.toJson(imageMessage));
				return  MessageUtils.messageToXml(imageMessage);
			case 2 :
				//视频回复现在不做
				return "";
			case 3 :
				//语音回复
				VoiceMessage voiceMessage = new VoiceMessage();
				voiceMessage.setToUserName(openId);
				voiceMessage.setFromUserName(wechatNo);
				voiceMessage.setCreateTime(System.currentTimeMillis());
				voiceMessage.setMsgType(MessageTypeEnum.VOICE.getCode());
				Image im = new Image();
				im.setMediaId(wechatMaterial.getMediaId());
				voiceMessage.setVoice(im);
				logger.info(" voiceMessage {}",JsonUtil.toJson(voiceMessage));
				return MessageUtils.messageToXml(voiceMessage);
			case 4 :
				//图文回复
				String url = Global.getConfig("wx.url");

				NewsMessage newsMessage = new NewsMessage();
				newsMessage.setToUserName(openId);
				newsMessage.setFromUserName(wechatNo);
				newsMessage.setCreateTime(System.currentTimeMillis());
				newsMessage.setMsgType(MessageTypeEnum.NEWS.getCode());
				newsMessage.setArticleCount("1");
				List<Article> articles = new ArrayList<>();
				Article article = new Article();
				article.setPicUrl(url+wechatMaterial.getCoverPicture());
				article.setTitle(wechatMaterial.getTitle());
				article.setDescription(wechatMaterial.getBrief());
				article.setUrl(wechatMaterial.getUri());
				articles.add(article);
				newsMessage.setArticles(articles);
				logger.info(" newsMessage {}",JsonUtil.toJson(newsMessage));
				return MessageUtils.messageToXml(newsMessage);
			default:
				return "";
		}


	}
}
