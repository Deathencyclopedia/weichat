package com.dep.weichat.service;

import java.util.List;

import com.dep.weichat.entity.Content;

public interface ContentService extends GenericService<Content> {

	/**
	 * 根据编号查询
	 * 
	 * @param number
	 * @return
	 */
	public Content findByNumber(String number);

	/**
	 * 根据多个编号查询
	 * 
	 * @param numbers
	 * @return
	 */
	public List<Content> findByNumber(List<String> numbers);
}
