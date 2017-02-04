package com.dep.weichat.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

/**
 * 菜单
 * @author Maclaine
 *
 */
@Entity
@DynamicUpdate
public class Menu implements Serializable{
	private static final long serialVersionUID = 4578747245337938956L;

	public Menu(){}
	
	
	/**
	 * ID
	 */
	private String id;
	/**
	 * 按钮名
	 */
	private String name;
	/**
	 * 按钮类型
	 */
	private String type;
	/**
	 * 按钮链接
	 */
	private String url;
	/**
	 * 父按钮
	 */
	private Menu parentMenu;
	/**
	 * 子按钮
	 */
	private List<Menu> childMenu = new ArrayList<Menu>();
	
	@Id
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	@GeneratedValue(generator = "system-uuid")
	@Column(length = 36,nullable=false)
	public String getId() {
		return id;
	}
	@Column(length = 36,nullable=false)
	public String getName() {
		return name;
	}
	@Column(length = 36,nullable=false)
	public String getType() {
		return type;
	}
	@Column(length = 255,nullable=false)
	public String getUrl() {
		return url;
	}
	@ManyToOne(fetch = FetchType.LAZY)  
    @JoinColumn(name = "pid")  
	public Menu getParentMenu() {
		return parentMenu;
	}
	@OneToMany(targetEntity = Menu.class, cascade = { CascadeType.ALL }, mappedBy = "parentMenu")  
    @Fetch(FetchMode.SUBSELECT)  
	public List<Menu> getChildMenu() {
		return childMenu;
	}
	
	
	public void setId(String id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public void setParentMenu(Menu parentMenu) {
		this.parentMenu = parentMenu;
	}
	public void setChildMenu(List<Menu> childMenu) {
		this.childMenu = childMenu;
	} 
	
	
}
