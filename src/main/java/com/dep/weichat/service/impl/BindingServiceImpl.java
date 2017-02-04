package com.dep.weichat.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dep.weichat.dao.BindingDao;
import com.dep.weichat.entity.Binding;
import com.dep.weichat.service.BindingService;

@Transactional
@Service
public class BindingServiceImpl extends GenericServiceImpl<Binding> implements BindingService {

	private BindingDao bindingDao;

	@Resource
	public void setBindingDao(BindingDao bindingDao) {
		this.bindingDao = bindingDao;
		this.genericDao = bindingDao;
	}

	@Override
	public Binding findByQRCode(String code) {
		List<Binding> list = bindingDao.findByQRCode(code);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public Binding findByOpenID(String openID) {
		List<Binding> list = bindingDao.findByOpenID(openID);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
}
