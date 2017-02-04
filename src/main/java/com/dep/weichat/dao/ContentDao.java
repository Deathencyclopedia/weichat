package com.dep.weichat.dao;

import java.util.List;

import com.dep.weichat.entity.Content;


public interface ContentDao extends GenericDao<Content> {

	/**
	 * 根据编号查找
	 * @param number
	 * @return
	 */
	public List<Content> findByNumber(String number);
	/**
	 * 根据多个编号查找
	 * @param number
	 * @return
	 */
	public List<Content> findByNumber(List<String> numbers);
}
