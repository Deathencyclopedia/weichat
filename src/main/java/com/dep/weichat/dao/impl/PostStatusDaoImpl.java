package com.dep.weichat.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.dep.weichat.dao.PostStatusDao;
import com.dep.weichat.entity.PostStatus;


@Repository
public class PostStatusDaoImpl extends GenericDaoImpl<PostStatus> implements PostStatusDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<PostStatus> findByQrcodeAndNumber(String qrCodeID, String number) {
		String hql="from PostStatus t where t.binding.qrcodeID=:qr and t.content.number=:num";
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		query.setParameter("qr",qrCodeID);
		query.setParameter("num",number);
		return  query.list();
	}

}
