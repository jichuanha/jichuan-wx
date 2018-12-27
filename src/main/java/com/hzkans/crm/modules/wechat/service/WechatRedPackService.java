package com.hzkans.crm.modules.wechat.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hzkans.crm.common.constant.ResponseEnum;
import com.hzkans.crm.common.service.ServiceException;
import com.hzkans.crm.common.utils.JsonUtil;
import com.hzkans.crm.modules.trade.constants.JoinActivityStatusEnum;
import com.hzkans.crm.modules.trade.entity.JoinActivity;
import com.hzkans.crm.modules.trade.service.JoinActivityService;
import com.hzkans.crm.modules.wechat.dao.WxRedPackRecordDao;
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


    /**
     * 发红包的逻辑处理
     * @param joinActivity
     */
    public void sendRedPackBusi(JoinActivity joinActivity) {
        Integer status = joinActivity.getStatus();
        if(!JoinActivityStatusEnum.LOTTERYED.getCode().equals(status)
                && !JoinActivityStatusEnum.SEND_FAIL.getCode().equals(status)) {
            //只有已抽奖和发放失败的才可以发红包
            return ;
        }
        try {
            lock.lockInterruptibly();
            Map<String, Object> objectMap = dealRedPackParemater(joinActivity);
            //调用发送红包api
            String sendResult = wxApiObserver.sendWxRedPack(objectMap);
            logger.info("sendResult {}", sendResult);
            int joinStatus = 0;
            WxRedPackRecord record1 = JSONArray.parseObject(sendResult, WxRedPackRecord.class);
            logger.info("record1 {}",JsonUtil.toJson(record1));
            String resultCode = record1.getResult_code();
            String returnCode = record1.getReturn_code();
            if(SUCCESS.equals(returnCode) && SUCCESS.equals(resultCode)) {
                //发送成功
                joinStatus = JoinActivityStatusEnum.SEND_SUCCESS.getCode();
            }else {
                //发送失败
                joinStatus = JoinActivityStatusEnum.SEND_FAIL.getCode();
            }
            //保存记录
            wxRedPackRecordDao.get(record1);
            //修改状态
            JoinActivity activity = new JoinActivity();
            activity.setStatus(joinStatus);
            activity.setId(joinActivity.getId());
            joinActivityService.updateJoinActivity(activity);

        } catch (Exception e) {
            logger.error("sendRedPackBusi error",e);
            throw new ServiceException(ResponseEnum.B_E_SEND_RED_PACK_ERROR);
        }finally {
            lock.unlock();
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
