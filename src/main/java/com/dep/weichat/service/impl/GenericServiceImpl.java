package com.dep.weichat.service.impl;

import java.io.Serializable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dep.weichat.dao.GenericDao;
import com.dep.weichat.service.GenericService;

@Transactional
@Service
public abstract class GenericServiceImpl<T> implements GenericService<T> {

	protected GenericDao<T> genericDao;

	@Override
	public T find(Serializable id) {
		return genericDao.find(id);
	}

	@Override
	public T load(Serializable id) {
		return genericDao.load(id);
	}

	@Override
	public Serializable save(T t) {
		return genericDao.save(t);
	}

	@Override
	public void update(T t) {
		genericDao.update(t);
	}

	@Override
	public void saveOrUpdate(T t) {
		genericDao.saveOrUpdate(t);
	}

	@Override
	public void delete(Serializable... ids) {
		genericDao.delete(ids);
	}
}
