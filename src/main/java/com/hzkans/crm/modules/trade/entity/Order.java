
package com.hzkans.crm.modules.trade.entity;

import javax.validation.constraints.NotNull;

import com.hzkans.crm.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 订单主表Entity
 * @author lizg
 * @version 2018-11-27
 */
public class Order extends DataEntity<Order> {
	
	private static final long serialVersionUID = 1L;
	private Long memberId;		// member_id
	private String orderSn;		// 订单流水号
	private String ownShop;		// 所属店铺
	private String shopNo;		// 店铺编号
	private Integer platformType;		// 平台类型 ： 1-淘宝 2-天猫  3-京东
	private Long payableAmmount;		// 应付金额  单位：分
	private Long payAmount;		// 实际支付金额
	private Integer status;		// 订单状态0-未审核 1-审核通过   2-审核拒绝
	private Date orderTime;		// 下单时间
	private Date payTime;		// 付款时间
	private String nickName;		// 买家昵称
	private String buyerName;		// 买家姓名
	private String mobile;		// 手机号码
	private String itemName;		// 商品名称
	private String itemNo;		// 商品编码
	private Long unitPrice;		// 商品单价
	private String buyCount;		// 购买数量
	private String provinceName;
	private String cityName;
	private String areaName;
	private String address;
	private String consignee;   //收件人
	private Long tableId;
	private Date startDate;
	private Date endDate;
	private String orderStatus;
	private String platformTypeStr;
	private String shopNoStr;
	private String payAmountStr;

	public String getPayAmountStr() {
		return payAmountStr;
	}

	public void setPayAmountStr(String payAmountStr) {
		this.payAmountStr = payAmountStr;
	}

	public String getPlatformTypeStr() {
		return platformTypeStr;
	}

	public void setPlatformTypeStr(String platformTypeStr) {
		this.platformTypeStr = platformTypeStr;
	}

	public String getShopNoStr() {
		return shopNoStr;
	}

	public void setShopNoStr(String shopNoStr) {
		this.shopNoStr = shopNoStr;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Long getTableId() {
		return tableId;
	}

	public void setTableId(Long tableId) {
		this.tableId = tableId;
	}

	public Order() {
		super();
	}

	public Order(String id){
		super(id);
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	@NotNull(message="member_id不能为空")
	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	
	@Length(min=1, max=64, message="订单流水号长度必须介于 1 和 64 之间")
	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}
	
	@Length(min=0, max=64, message="所属店铺长度必须介于 0 和 64 之间")
	public String getOwnShop() {
		return ownShop;
	}

	public void setOwnShop(String ownShop) {
		this.ownShop = ownShop;
	}
	
	@Length(min=0, max=64, message="店铺编号长度必须介于 0 和 64 之间")
	public String getShopNo() {
		return shopNo;
	}

	public void setShopNo(String shopNo) {
		this.shopNo = shopNo;
	}
	
	public Integer getPlatformType() {
		return platformType;
	}

	public void setPlatformType(Integer platformType) {
		this.platformType = platformType;
	}
	
	@NotNull(message="应付金额  单位：分不能为空")
	public Long getPayableAmmount() {
		return payableAmmount;
	}

	public void setPayableAmmount(Long payableAmmount) {
		this.payableAmmount = payableAmmount;
	}
	
	@NotNull(message="实际支付金额不能为空")
	public Long getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Long payAmount) {
		this.payAmount = payAmount;
	}
	
	@NotNull(message="订单状态0-未审核 1-审核通过   2-审核拒绝不能为空")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="下单时间不能为空")
	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="付款时间不能为空")
	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	
	@Length(min=1, max=64, message="买家昵称长度必须介于 1 和 64 之间")
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	@Length(min=1, max=64, message="买家姓名长度必须介于 1 和 64 之间")
	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}
	
	@Length(min=1, max=64, message="手机号码长度必须介于 1 和 64 之间")
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@Length(min=0, max=255, message="商品名称长度必须介于 0 和 255 之间")
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
	@Length(min=0, max=64, message="商品编码长度必须介于 0 和 64 之间")
	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}
	
	public Long getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Long unitPrice) {
		this.unitPrice = unitPrice;
	}
	
	@Length(min=0, max=11, message="购买数量长度必须介于 0 和 11 之间")
	public String getBuyCount() {
		return buyCount;
	}

	public void setBuyCount(String buyCount) {
		this.buyCount = buyCount;
	}
	
}