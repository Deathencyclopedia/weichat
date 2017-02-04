package com.dep.weichat.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.dep.weichat.dao.ContentDao;
import com.dep.weichat.entity.Content;


@Repository
public class ContentDaoImpl extends GenericDaoImpl<Content> implements ContentDao{

	@SuppressWarnings("unchecked")
	@Override
	public List<Content> findByNumber(String number) {
		String hql="from Content t where t.number=?";
		return (List<Content>) getHibernateTemplate().find(hql, number);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Content> findByNumber(List<String> numbers) {
		String hql="from Content t where t.number in (:nlist)";
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		query.setParameterList("nlist",numbers);
		return query.list();
		//return (List<Content>) getHibernateTemplate().find(hql, numbers);
	}

}
