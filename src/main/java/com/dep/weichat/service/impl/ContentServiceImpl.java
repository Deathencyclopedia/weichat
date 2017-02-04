package com.dep.weichat.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dep.weichat.dao.ContentDao;
import com.dep.weichat.entity.Content;
import com.dep.weichat.service.ContentService;

@Transactional
@Service
public class ContentServiceImpl extends GenericServiceImpl<Content> implements ContentService {

	private ContentDao contentDao;

	@Resource
	public void setContentDao(ContentDao contentDao) {
		this.contentDao = contentDao;
		this.genericDao = contentDao;
	}

	@Override
	public Content findByNumber(String number) {
		List<Content> list = contentDao.findByNumber(number);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<Content> findByNumber(List<String> numbers) {
		return contentDao.findByNumber(numbers);
	}

}
