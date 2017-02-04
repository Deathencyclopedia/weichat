package com.dep.weichat.vo.message.resp;

import com.dep.weichat.entity.Content;

public class Article {

	public Article() {
	}

	public Article(Content content) {
		this.Title = content.getTitle();
		this.Description = content.getDescription();
		this.Picurl = content.getPicURL();
		this.Url = content.getArticleURL();
	}

	// 图文消息名称
	private String Title;
	// 图文消息描述
	private String Description;
	// 图片链接，支持JPG、PNG格式，较好的效果为大图640*320，小图80*80，限制图片链接的域名需要与开发者填写的基本资料中的Url一致
	private String Picurl;
	// 点击图文消息跳转链接
	private String Url;

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getDescription() {
		return null == Description ? "" : Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getPicurl() {
		return null == Picurl ? "" : Picurl;
	}

	public void setPicurl(String picurl) {
		Picurl = picurl;
	}

	public String getUrl() {
		return null == Url ? "" : Url;
	}

	public void setUrl(String url) {
		Url = url;
	}

}
