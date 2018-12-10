package com.hzkans.crm.modules.wechat.entity;

import java.io.Serializable;

/**
 * @author chenggm
 * @create 2017-02-27 13:16
 **/
public class ReplyDescDTO implements Serializable {
	private static final long serialVersionUID = -1914866738873858352L;
	private String title;
	private String brief;
	private String coverPicture;
	private String uri;
	private String wechat_id;
	private String article_uri;
	private Integer type;
	private String id;


	public String getWechat_id() {
		return wechat_id;
	}

	public void setWechat_id(String wechat_id) {
		this.wechat_id = wechat_id;
	}

	public String getArticle_uri() {
		return article_uri;
	}

	public void setArticle_uri(String article_uri) {
		this.article_uri = article_uri;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	public String getCoverPicture() {
		return coverPicture;
	}

	public void setCoverPicture(String coverPicture) {
		this.coverPicture = coverPicture;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

}
