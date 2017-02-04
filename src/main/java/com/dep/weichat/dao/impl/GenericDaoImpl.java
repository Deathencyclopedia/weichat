package com.dep.weichat.dao.impl;

import java.io.Serializable;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.dep.weichat.dao.GenericDao;
import com.dep.weichat.exception.SystemException;
import com.dep.weichat.util.GenericsUtil;
import com.dep.weichat.util.type.ErrorCodeType;

public abstract class GenericDaoImpl<T> extends HibernateDaoSupport implements GenericDao<T> {
	protected Class<T> entityClass;

	@SuppressWarnings("unchecked")
	public GenericDaoImpl() {
		this.entityClass = GenericsUtil.getSuperClassGenericType(this.getClass());
	}

	@Resource
	public void setSessionFacotry(SessionFactory sessionFacotry) {
		super.setSessionFactory(sessionFacotry);
	}

	@Override
	public T find(Serializable id) {
		if (id == null) {
			throw new SystemException(ErrorCodeType.id_null);
		}
		return getHibernateTemplate().get(this.entityClass, id);
	}

	@Override
	public T load(Serializable id) {
		if (id == null) {
			throw new SystemException(ErrorCodeType.id_null);
		}
		return getHibernateTemplate().load(this.entityClass, id);
	}

	@Override
	public Serializable save(T t) {
		return getHibernateTemplate().save(t);
	}

	@Override
	public void saveOrUpdate(T t) {
		getHibernateTemplate().saveOrUpdate(t);
	}

	@Override
	public void update(T t) {
		getHibernateTemplate().update(t);
	}

	@Override
	public void delete(Serializable... ids) {
		for (Serializable id : ids) {
			if (id == null) {
				throw new SystemException(ErrorCodeType.id_null);
			}
			T t = find(id);
			getHibernateTemplate().delete(t);
		}
	}
}
