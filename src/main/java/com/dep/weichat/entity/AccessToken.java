package com.dep.weichat.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import com.dep.weichat.util.ServerUtil;


/**
 * AccessToken
 * @author Maclaine
 *
 */
@Entity
@DynamicUpdate
public class AccessToken implements Serializable {
	private static final long serialVersionUID = -7894677483992531884L;
	
	public AccessToken(){}
	/**
	 * ID
	 */
	private String id;
	/**
	 * 系统名称
	 */
	private String systemName=ServerUtil.loadProperty("systemname");
	/**
	 * 票据
	 */
	private String accessToken;
	/**
	 * 有效时间(秒) 默认2小时
	 */
	private Long expires=2*60*60L;
	/**
	 * 更新日期
	 */
	private Date updateDate;
	
	@Id
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	@GeneratedValue(generator = "system-uuid")
	@Column(length = 36,nullable=false)
	public String getId() {
		return id;
	}
	@Column(length = 36,nullable=false)
	public String getSystemName() {
		return systemName;
	}
	@Column(length = 512,nullable=false)
	public String getAccessToken() {
		return accessToken;
	}
	@Column(nullable=false)
	public Long getExpires() {
		return expires;
	}
	@Column(nullable=false)
	public Date getUpdateDate() {
		return updateDate;
	}
	
	
	public void setId(String id) {
		this.id = id;
	}
	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public void setExpires(Long expires) {
		this.expires = expires;
	}
	
	/**
	 * 是否超时
	 * @return true:超时.false:未超时
	 */
	@Transient
	public boolean isOutTime(){
		Calendar cal=Calendar.getInstance();
		if(updateDate.getTime()+getExpires()*1000<cal.getTimeInMillis()){
			return true;
		}
		return false;
	}
}
