/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.hzkans.crm.modules.activity.entity;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import com.hzkans.crm.common.persistence.DataEntity;

/**
 * 奖品类型Entity
 * @author wsh
 * @version 2018-12-11
 */
public class ActivityPrize extends DataEntity<ActivityPrize> {
	
	private static final long serialVersionUID = 1L;
	private Long lotteryId;		// 幸运抽奖活动id
	private String prizeName;		// 奖品名称
	private Double prizeRate;		// 奖品比例
	
	public ActivityPrize() {
		super();
	}

	public ActivityPrize(String id){
		super(id);
	}

	@NotNull(message="幸运抽奖活动id不能为空")
	public Long getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}
	
	@Length(min=1, max=255, message="奖品名称长度必须介于 1 和 255 之间")
	public String getPrizeName() {
		return prizeName;
	}

	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
	}
	
	@NotNull(message="奖品比例不能为空")
	public Double getPrizeRate() {
		return prizeRate;
	}

	public void setPrizeRate(Double prizeRate) {
		this.prizeRate = prizeRate;
	}

	@Override
	public String toString() {
		return "ActivityPrize{" +
				"lotteryId=" + lotteryId +
				", prizeName='" + prizeName + '\'' +
				", prizeRate=" + prizeRate +
				'}';
	}
}