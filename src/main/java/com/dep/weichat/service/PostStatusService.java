package com.dep.weichat.service;

import com.dep.weichat.entity.PostStatus;


public interface PostStatusService extends GenericService<PostStatus>{

	/**
	 * 根据二维码和编号查询状态
	 * @param qrCode
	 * @param number
	 * @return
	 */
	public PostStatus findByQrcodeAndNumber(String qrCodeID,String number);
}
