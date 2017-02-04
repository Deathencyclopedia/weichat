package com.dep.weichat.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dep.weichat.dao.BindingDao;
import com.dep.weichat.entity.Binding;


@Repository
public class BindingDaoImpl extends GenericDaoImpl<Binding> implements BindingDao{

	@SuppressWarnings("unchecked")
	@Override
	public List<Binding> findByQRCode(String code) {
		String hql="from Binding t where t.qrcodeID=?";
		return (List<Binding>) getHibernateTemplate().find(hql, code);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Binding> findByOpenID(String openID) {
		String hql="from Binding t where t.openID=?";
		return (List<Binding>) getHibernateTemplate().find(hql, openID);
	}

}
