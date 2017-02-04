package com.dep.weichat.dao;

import java.util.List;

import com.dep.weichat.entity.Binding;


public interface BindingDao extends GenericDao<Binding>{
	/**
	 * 根据二维码编号查询
	 * @param code
	 * @return
	 */
	public List<Binding> findByQRCode(String code);
	/**
	 * 根据openID查询
	 * @param openID
	 * @return
	 */
	public List<Binding> findByOpenID(String openID);
}
