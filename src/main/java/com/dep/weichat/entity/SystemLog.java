package com.dep.weichat.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import com.dep.weichat.util.ServerUtil;


/**
 * 日志
 * @author Maclaine
 *
 */
@Entity
@DynamicUpdate
public class SystemLog implements Serializable{
	private static final long serialVersionUID = -6805341256111440131L;
	
	public SystemLog() {}
	/**
	 * 构造
	 * @param operate 操作
	 * @param content 内容
	 */
	public SystemLog(String operate,String content) {
		this.operate=operate;
		this.content=content;
		this.date=new Date();
	}
	/**
	 * 构造
	 * @param operate 操作
	 * @param content 内容
	 * @param ip IP地址
	 */
	public SystemLog(String operate,String content,String ip) {
		this.operate=operate;
		this.content=content;
		this.ip=ip;
		this.date=new Date();
	}
	/**
	 * 构造
	 * @param operate 操作
	 * @param content 内容
	 * @param request request,用户获取IP
	 */
	public SystemLog(String operate,String content,HttpServletRequest request) {
		this.operate=operate;
		this.content=content;
		this.ip=ServerUtil.getIpAddr(request);
		this.date=new Date();
	}
	
	/**
	 * ID
	 */
	private String id;
	/**
	 * 操作类型
	 */
	private String operate;
	/**
	 * 操作内容
	 */
	private String content;
	/**
	 * 操作者IP
	 */
	private String ip;
	/**
	 * 操作日期
	 */
	private Date date;
	
	@Id
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	@GeneratedValue(generator = "system-uuid")
	@Column(length = 36,nullable=false)
	public String getId() {
		return id;
	}
	@Column(length = 36,nullable=false)
	public String getOperate() {
		return operate;
	}
	@Column(length = 8000,nullable=false)
	public String getContent() {
		return content;
	}
	@Column(length = 255)
	public String getIp() {
		return ip;
	}
	@Column
	public Date getDate() {
		return date;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setOperate(String operate) {
		this.operate = operate;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
}
