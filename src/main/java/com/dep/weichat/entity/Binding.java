package com.dep.weichat.entity;

import java.io.Serializable;
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
 * 绑定用户和qrcode
 * @author Maclaine
 *
 */
@Entity
@DynamicUpdate
public class Binding implements Serializable{
	private static final long serialVersionUID = -6035889914166876955L;

	public Binding(){}
	
	/**
	 * ID
	 */
	private String id;
	/**
	 * 用户ID
	 */
	private String openID;
	/**
	 * 二维码ID
	 */
	private String qrcodeID;
	
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
	@Column(length = 255,nullable=false)
	public String getOpenID() {
		return openID;
	}
	@Column(length = 255,nullable=false,unique=true)
	public String getQrcodeID() {
		return qrcodeID;
	}
	
	@OneToMany(targetEntity = PostStatus.class, cascade = { CascadeType.ALL }, mappedBy = "binding")
	@Fetch(FetchMode.SUBSELECT)  
	public List<PostStatus> getPostStatus() {
		return postStatus;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	public void setOpenID(String openID) {
		this.openID = openID;
	}
	public void setQrcodeID(String qrcodeID) {
		this.qrcodeID = qrcodeID;
	}
	public void setPostStatus(List<PostStatus> postStatus) {
		this.postStatus = postStatus;
	}
	
	
	
}
