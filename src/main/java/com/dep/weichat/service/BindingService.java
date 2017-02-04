package com.dep.weichat.service;

import com.dep.weichat.entity.Binding;

public interface BindingService extends GenericService<Binding> {
	/**
	 * 根据QRCodeID查找
	 * 
	 * @param code
	 * @return 找到则返回Binding,否则返回null
	 */
	public Binding findByQRCode(String code);

	/**
	 * 根据openID查找
	 * 
	 * @param openID
	 * @return
	 */
	public Binding findByOpenID(String openID);
}
