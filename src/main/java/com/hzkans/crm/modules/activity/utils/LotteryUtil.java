package com.hzkans.crm.modules.activity.utils;

import com.hzkans.crm.modules.activity.entity.ActivityLottery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author wsh
 * @Title: 根据设置的抽奖概率进行抽奖
 * @ProjectName dongyin-CRM
 * @date 2018/12/20 15:03
 */

public class LotteryUtil {
    /**
     * 抽奖，获取中奖奖品
     * @param awardList 奖品及中奖概率列表
     * @return 中奖商品
     */
    public static ActivityLottery.LotteryPrize lottery(List<ActivityLottery.LotteryPrize> awardList) {
        if(awardList.isEmpty()){
            return null;
        }
        //奖品总数
        int size = awardList.size();

        //计算总概率
        double sumProbability = 0d;
        for (ActivityLottery.LotteryPrize award : awardList) {
            sumProbability += award.getPrizeRate();
        }

        //计算每个奖品的概率区间
        //例如奖品A概率区间0-0.1  奖品B概率区间 0.1-0.5 奖品C概率区间0.5-1
        //每个奖品的中奖率越大，所占的概率区间就越大
        List<Double> sortAwardProbabilityList = new ArrayList<>(size);
        Double tempSumProbability = 0d;
        for (ActivityLottery.LotteryPrize award : awardList) {
            tempSumProbability += award.getPrizeRate();
            sortAwardProbabilityList.add(tempSumProbability / sumProbability);
        }

        //产生0-1之间的随机数
        //随机数在哪个概率区间内，则是哪个奖品
        double randomDouble = Math.random();
        //加入到概率区间中，排序后，返回的下标则是awardList中中奖的下标
        sortAwardProbabilityList.add(randomDouble);
        Collections.sort(sortAwardProbabilityList);
        int lotteryIndex = sortAwardProbabilityList.indexOf(randomDouble);
        return awardList.get(lotteryIndex);
    }
}
