package com.dep.weichat.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.dep.weichat.util.ServerUtil;


/**
 * 发送状态
 * 
 * @author Maclaine
 *
 */
@Entity
public class PostStatus implements Serializable{
	private static final long serialVersionUID = -8580275918477741872L;
	
	private String id;
	private Binding binding;
	private Content content;
	private Date updateDate;

	@Id
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	@GeneratedValue(generator = "system-uuid")
	@Column(length = 36, nullable = false)
	public String getId() {
		return id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bindingID",nullable = false)
	public Binding getBinding() {
		return binding;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "contentID",nullable = false)
	public Content getContent() {
		return content;
	}

	@Column
	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setBinding(Binding binding) {
		this.binding = binding;
	}

	public void setContent(Content content) {
		this.content = content;
	}

	/**
	 * 是否超时
	 * @return true:超时.false:未超时
	 */
	@Transient
	public boolean isOutTime(){
		if(updateDate==null){
			return true;
		}
		Calendar cal=Calendar.getInstance();
		if(updateDate.getTime()+Integer.parseInt(ServerUtil.loadProperty("msgtimeout"))*1000<cal.getTimeInMillis()){
			return true;
		}
		return false;
	}
}
