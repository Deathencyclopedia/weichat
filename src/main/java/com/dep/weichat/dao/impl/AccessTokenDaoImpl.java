package com.dep.weichat.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dep.weichat.dao.AccessTokenDao;
import com.dep.weichat.entity.AccessToken;


@Repository
public class AccessTokenDaoImpl extends GenericDaoImpl<AccessToken> implements AccessTokenDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<AccessToken> findBySystemName(String systemName) {
		String hql="from AccessToken t where t.systemName=?";
		return (List<AccessToken>) getHibernateTemplate().find(hql, systemName);
	}

}
