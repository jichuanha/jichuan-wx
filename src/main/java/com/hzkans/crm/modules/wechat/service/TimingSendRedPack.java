package com.hzkans.crm.modules.wechat.service;

import com.hzkans.crm.modules.trade.entity.JoinActivity;
import com.hzkans.crm.modules.trade.service.JoinActivityService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author jc
 * @description
 * @create 2018/12/26
 */
@Service
public class TimingSendRedPack {

    @Autowired
    private JoinActivityService joinActivityService;
    @Autowired
    private WechatRedPackService wechatRedPackService;

    public void sendRedPack() {
        List<JoinActivity> sendRedPackInfo = joinActivityService.getSendRedPackInfo();
        if(CollectionUtils.isEmpty(sendRedPackInfo)) {
            return ;
        }
        for (JoinActivity joinActivity : sendRedPackInfo) {
            wechatRedPackService.sendRedPackBusi(joinActivity);
        }

    }

}
