/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.hzkans.crm.modules.wechat.utils;

import com.alibaba.fastjson.JSONObject;
import com.hzkans.crm.common.utils.EhCacheUtils;
import com.hzkans.crm.common.utils.JsonUtil;
import com.hzkans.crm.common.utils.SpringContextHolder;
import com.hzkans.crm.modules.wechat.dao.WechatPlatfromDAO;
import com.hzkans.crm.modules.wechat.entity.WechatPlatfromDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * 用户工具类
 * @author ThinkGem
 * @version 2013-12-05
 */
public class WechatUtils {

	private final static Logger logger = LoggerFactory
			.getLogger(WechatUtils.class);

	private static WechatPlatfromDAO wechatPlatfromDAO = SpringContextHolder.getBean(WechatPlatfromDAO.class);


	public static WechatPlatfromDO getWechatPlatform(Long id){
		if (null != id){
			return wechatPlatfromDAO.getWechatPlatformById(id);
		}else {
			return new WechatPlatfromDO();
		}
	}


	/**
	 * 验证签名
	 *
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @return
	 */
	public static boolean checkSignature(String signature, String timestamp,
										 String nonce, String token) {
		// 1.将token、timestamp、nonce三个参数进行字典序排序
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


	public static String getAccessToken(String appId, String appSecret) {
		Object result = EhCacheUtils.get(WechatCofig.EHCACHE, appId);
		logger.info("result {}",result);
		if(null != result) {
			return (String) result;
		}
		String url = WechatCofig.GET_ACCESS_TOKEN.replace("APPID", appId).replace("APPSECRET", appSecret);
		String data = HttpRequestUtil.
				HttpsDefaultExecute(HttpRequestUtil.GET_METHOD, url, "", "", 0, "false");
		logger.info("[{}] data:{}", JsonUtil.toJson(data));
		JSONObject jsonObject = JSONObject.parseObject(data);
		String accessToken = jsonObject.getString("access_token");
		EhCacheUtils.put(WechatCofig.EHCACHE, appId, accessToken);
		return accessToken;
	}
	
}
