package com.dep.weichat.dao;

import java.util.List;

import com.dep.weichat.entity.PostStatus;


public interface PostStatusDao extends GenericDao<PostStatus>{
	/**
	 * 根据二维码和编号查询状态
	 * @param qrCode
	 * @param number
	 * @return
	 */
	public List<PostStatus> findByQrcodeAndNumber(String qrCodeID, String number);
}
