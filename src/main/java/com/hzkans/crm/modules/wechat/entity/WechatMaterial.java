/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.hzkans.crm.modules.wechat.entity;

import com.hzkans.crm.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;
import java.util.Date;


/**
 * 微信公众号素材库Entity
 * @author dtm
 * @version 2018-12-04
 */
public class WechatMaterial extends DataEntity<WechatMaterial> {
	
	private static final long serialVersionUID = 1L;
	private String title;		// 标题
	private String coverPicture;		// 封面
	private String content;		// 内容
	private String brief;		// 简介
	private String uri;		// 链接地址
	private Integer type;		// 素材的类型，1-图片、2-视频（video）、3-语音 （voice）、4-图文（news）
	private String creator;		// creator
	private String updator;		// updator
	private Integer deleted;		// 0.存在1.删除
	private Integer wechatId;		// wechat_id
	private Date beginCreateDate;		// 开始 创建时间
	private Date endCreateDate;		// 结束 创建时间
	private String articleUri;
	private String mediaId;
	private Integer replyWay;
	private Integer status;


	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getReplyWay() {
		return replyWay;
	}

	public void setReplyWay(Integer replyWay) {
		this.replyWay = replyWay;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getArticleUri() {
		return articleUri;
	}

	public void setArticleUri(String articleUri) {
		this.articleUri = articleUri;
	}

	public WechatMaterial() {
		super();
	}

	public WechatMaterial(String id){
		super(id);
	}

	@Length(min=0, max=255, message="标题长度必须介于 0 和 255 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Length(min=0, max=255, message="封面长度必须介于 0 和 255 之间")
	public String getCoverPicture() {
		return coverPicture;
	}

	public void setCoverPicture(String coverPicture) {
		this.coverPicture = coverPicture;
	}

	@Length(min=0, max=1024, message="内容长度必须介于 0 和 1024 之间")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getUpdator() {
		return updator;
	}

	public void setUpdator(String updator) {
		this.updator = updator;
	}

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	public Integer getWechatId() {
		return wechatId;
	}

	public void setWechatId(Integer wechatId) {
		this.wechatId = wechatId;
	}

	public Date getBeginCreateDate() {
		return beginCreateDate;
	}

	public void setBeginCreateDate(Date beginCreateDate) {
		this.beginCreateDate = beginCreateDate;
	}

	public Date getEndCreateDate() {
		return endCreateDate;
	}

	public void setEndCreateDate(Date endCreateDate) {
		this.endCreateDate = endCreateDate;
	}
}