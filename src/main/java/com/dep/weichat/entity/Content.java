package com.dep.weichat.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

/**
 * 文章内容
 * @author Maclaine
 *
 */
@Entity
@DynamicUpdate
public class Content {
	
	/**
	 * ID
	 */
	private String id;
	/**
	 * 编号(唯一)
	 */
	private String number;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 简介(只有单图文的时候才会显示)
	 */
	private String description;
	/**
	 * 图片地址
	 */
	private String picURL;
	/**
	 * 文章地址
	 */
	private String articleURL;
	/**
	 * 素材ID
	 */
	private String mediaID;
	
	/**
	 * 发送状态
	 */
	private List<PostStatus> postStatus;
	
	@Id
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	@GeneratedValue(generator = "system-uuid")
	@Column(length = 36,nullable=false)
	public String getId() {
		return id;
	}
	@Column(nullable=false,unique=true)
	public String getNumber() {
		return number;
	}
	@Column(length = 255)
	public String getTitle() {
		return title;
	}
	@Column(length = 1000)
	public String getDescription() {
		return description;
	}
	@Column(length = 255)
	public String getPicURL() {
		return picURL;
	}
	@Column(length = 255)
	public String getArticleURL() {
		return articleURL;
	}
	@Column(length = 255)
	public String getMediaID() {
		return mediaID;
	}
	@OneToMany(targetEntity = PostStatus.class, cascade = { CascadeType.ALL }, mappedBy = "content")
	@Fetch(FetchMode.SUBSELECT)  
	public List<PostStatus> getPostStatus() {
		return postStatus;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setPicURL(String picURL) {
		this.picURL = picURL;
	}
	public void setArticleURL(String articleURL) {
		this.articleURL = articleURL;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setPostStatus(List<PostStatus> postStatus) {
		this.postStatus = postStatus;
	}
	
	public void setMediaID(String mediaID) {
		this.mediaID = mediaID;
	}
	
	
}
