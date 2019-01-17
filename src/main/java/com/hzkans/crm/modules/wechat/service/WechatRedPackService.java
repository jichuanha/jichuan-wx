package com.hzkans.crm.modules.wechat.service;

import com.alibaba.fastjson.JSONArray;
import com.hzkans.crm.common.constant.ResponseEnum;
import com.hzkans.crm.common.service.ServiceException;
import com.hzkans.crm.common.utils.JsonUtil;
import com.hzkans.crm.common.utils.StringUtils;
import com.hzkans.crm.common.utils.XmlUtil;
import com.hzkans.crm.modules.trade.constants.JoinActivityStatusEnum;
import com.hzkans.crm.modules.trade.entity.JoinActivity;
import com.hzkans.crm.modules.trade.service.JoinActivityService;
import com.hzkans.crm.modules.wechat.dao.WxCompanyPayRecordDao;
import com.hzkans.crm.modules.wechat.dao.WxRedPackRecordDao;
import com.hzkans.crm.modules.wechat.entity.WxCompanyPayRecord;
import com.hzkans.crm.modules.wechat.entity.WxRedPackRecord;
import com.hzkans.crm.modules.wxapi.constants.WechatCofig;
import com.hzkans.crm.modules.wxapi.service.WxApiObserver;
import com.hzkans.crm.modules.wxapi.utils.WXRedPackUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author jc
 * @description
 * @create 2018/12/25
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class WechatRedPackService {

    private static final Logger logger = LoggerFactory.getLogger(WechatRedPackService.class);

    private static final String SUCCESS = "SUCCESS";
    static ReentrantLock lock = new ReentrantLock();

    @Autowired
    private WxApiObserver wxApiObserver;
    @Autowired
    private JoinActivityService joinActivityService;
    @Autowired
    private WxRedPackRecordDao wxRedPackRecordDao;
    @Autowired
    private WxCompanyPayRecordDao wxCompanyPayRecordDao;


    /**
     * 发放奖励的逻辑处理
     * @param joinActivity
     */
    public void sendAwardBusi(JoinActivity joinActivity) {
        Integer status = joinActivity.getStatus();
        if(!JoinActivityStatusEnum.LOTTERYED.getCode().equals(status)
                && !JoinActivityStatusEnum.SEND_FAIL.getCode().equals(status)) {
            //只有已抽奖和发放失败的才可以发红包
            return ;
        }
        int type = 1;
        try {
            lock.lockInterruptibly();
            String resultCode = "";
            String returnCode = "";
            int joinStatus = 0;
            String openId = joinActivity.getOpenId();
            if(type == 1) {
                //调用发送红包api
                WxRedPackRecord record1 = sendRedPack(joinActivity);
                resultCode = record1.getResult_code();
                returnCode = record1.getReturn_code();
                //保存记录
                wxRedPackRecordDao.insert(record1);
            }else if(type == 2) {
                //企业付款到余额
                WxCompanyPayRecord record1 = companyPay(joinActivity);
                resultCode = record1.getResult_code();
                returnCode = record1.getReturn_code();
                record1.setRe_openid(openId);
                //下面这种状况时需要查询付款状态
                if(resultCode != null && resultCode.equals("FAIL")) {
                    String payStatus = queryCompanyPay(joinActivity);
                    //当查询到支付状态为成功时,修改前面的状态
                    if(payStatus != null && payStatus.equals(SUCCESS)) {
                        resultCode = SUCCESS;
                    }
                }
                //保存记录
                wxCompanyPayRecordDao.insert(record1);
            }
            if(SUCCESS.equals(returnCode) && SUCCESS.equals(resultCode)) {
                //发送成功
                joinStatus = JoinActivityStatusEnum.SEND_SUCCESS.getCode();
            }else {
                //发送失败
                joinStatus = JoinActivityStatusEnum.SEND_FAIL.getCode();
            }
            //修改状态
            /*JoinActivity activity = new JoinActivity();
            activity.setStatus(joinStatus);
            activity.setId(joinActivity.getId());
            joinActivityService.updateJoinActivity(activity);*/

        } catch (Exception e) {
            logger.error("sendRedPackBusi error",e);
            throw new ServiceException(ResponseEnum.B_E_SEND_RED_PACK_ERROR);
        }finally {
            lock.unlock();
        }

    }

    /**
     * 统一下单
     * @return
     */
    public Map<String, String> payOrder(String ip, String openId) {
        Map<String, String> map = null;
        try {
            Map<String, String> objectMap = generateParameter(ip, openId);
            logger.info("objectMap {}", objectMap);
            String s = wxApiObserver.wxJsPay(objectMap);
            logger.info("s {}",s);
            map = WXRedPackUtils.xmlToMap(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    private Map<String, String> generateParameter(String ip, String openId) {
        Map<String, String> parameterMap = new TreeMap<>();
        if(StringUtils.isEmpty(ip)) {
            ip = "10.1.10.56";
        }
        try {
            parameterMap.put("appid", "wxc34e2d05619f3915");
            parameterMap.put("mch_id", "1523079941");
            parameterMap.put("nonce_str", WXRedPackUtils.buildRandom());
            parameterMap.put("body", "嗨云商超");
            parameterMap.put("out_trade_no", WXRedPackUtils.buildRandom());
            parameterMap.put("openid", openId);
            parameterMap.put("total_fee", 1+"");
            parameterMap.put("spbill_create_ip", ip);
            parameterMap.put("notify_url", "https://crmtest.yyzws.com/dongyin_CRM/pay/callback");
            parameterMap.put("trade_type", "JSAPI");
            parameterMap.put("sign",WXRedPackUtils.getWxParamSign(parameterMap));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return parameterMap;
    }

    private String queryCompanyPay(JoinActivity joinActivity) {
        try {
            Map<String, Object> objectMap = new HashMap<>();
            objectMap.put("nonce_str", WXRedPackUtils.buildRandom());
            objectMap.put("partner_trade_no", joinActivity.getOrderSn());
            objectMap.put("mch_id", WechatCofig.MCH_ID);
            objectMap.put("appid", joinActivity.getAppId());
            objectMap.put("sign", WXRedPackUtils.createSign(objectMap));
            String result = wxApiObserver.queryConpanyPay(objectMap);
            logger.info("queryCompanyPay {}",result);
            Map<String, String> map = XmlUtil.xmlStr2Map(result);
            return map.get("status");

        } catch (Exception e) {
            logger.error("queryCompanyPay error");
            throw new ServiceException("queryCompanyPay error");
        }
    }

    private WxRedPackRecord sendRedPack(JoinActivity joinActivity) {
        try {
            Map<String, Object> objectMap = dealRedPackParemater(joinActivity);
            String sendResult = wxApiObserver.sendWxRedPack(objectMap);
            logger.info("sendResult {}", sendResult);
            Map<String, String> map = XmlUtil.xmlStr2Map(sendResult);
            return JSONArray.parseObject(JsonUtil.toJson(map), WxRedPackRecord.class);
        } catch (Exception e) {
            logger.error("sendRedPack error",e);
            throw new ServiceException("sendRedPack error");
        }
    }

    private WxCompanyPayRecord companyPay(JoinActivity joinActivity) {
        try {
            Map<String, Object> objectMap = dealCompanyPay(joinActivity);
            logger.info("objectMap {} ",JsonUtil.toJson(objectMap));
            String payResult = wxApiObserver.companyPay(objectMap);
            logger.info("payResult {}", payResult);
            Map<String, String> map = XmlUtil.xmlStr2Map(payResult);
            return JSONArray.parseObject(JsonUtil.toJson(map), WxCompanyPayRecord.class);
        } catch (Exception e) {
            logger.error("sendRedPack error",e);
            throw new ServiceException("sendRedPack error");
        }
    }



    private Map<String, Object> dealCompanyPay(JoinActivity joinActivity) {

        try {
            Map<String, Object> map = new TreeMap<>();
            map.put("amount", joinActivity.getAward());
            //是否校验用户名称 NO_CHECK :不校验  FORCE_CHECK :强制校验
            map.put("check_name", "NO_CHECK");
            map.put("desc", "活动");
            map.put("mch_appid", joinActivity.getAppId());
            map.put("mchid", WechatCofig.MCH_ID);
            map.put("nonce_str", WXRedPackUtils.buildRandom());
            map.put("openid", joinActivity.getOpenId());
            map.put("partner_trade_no", joinActivity.getOrderSn());
            map.put("spbill_create_ip", InetAddress.getLocalHost());
            map.put("sign", WXRedPackUtils.createSign(map));
            return map;
        } catch (Exception e) {
            logger.error("dealCompanyPay error",e);
            throw new ServiceException("dealCompanyPay error");
        }

    }


    private Map<String, Object> dealRedPackParemater(JoinActivity joinActivity) {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("nonce_str", WXRedPackUtils.buildRandom());
            map.put("mch_billno", joinActivity.getOrderSn());
            map.put("mch_id", WechatCofig.MCH_ID);
            map.put("wxappid", joinActivity.getAppId());
            map.put("send_name", "杭州东印");
            map.put("re_openid", joinActivity.getOpenId());
            map.put("total_amount", joinActivity.getAward());

            //红包发送总人数
            map.put("total_num", 1);
            map.put("wishing", "恭喜您中奖");
            map.put("client_ip", InetAddress.getLocalHost());
            map.put("act_name", joinActivity.getActName());
            map.put("remark", "欢迎下次光临");
            map.put("sign", WXRedPackUtils.createSign(map));
            return map;
        } catch (UnknownHostException e) {
            logger.error("dealRedPackParemater error",e);
            throw new ServiceException(ResponseEnum.B_E_DEAL_PARaMETER_ERROR);
        }
    }

}
