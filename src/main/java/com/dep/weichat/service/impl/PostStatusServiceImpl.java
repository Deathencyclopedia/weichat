package com.dep.weichat.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dep.weichat.dao.PostStatusDao;
import com.dep.weichat.entity.PostStatus;
import com.dep.weichat.service.PostStatusService;

@Transactional
@Service
public class PostStatusServiceImpl extends GenericServiceImpl<PostStatus> implements PostStatusService {

	private PostStatusDao postStatusDao;

	@Resource
	public void setPostStatusDao(PostStatusDao postStatusDao) {
		this.postStatusDao = postStatusDao;
		this.genericDao = postStatusDao;
	}

	@Override
	public PostStatus findByQrcodeAndNumber(String qrCodeID, String number) {
		List<PostStatus> list = postStatusDao.findByQrcodeAndNumber(qrCodeID, number);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}

}
