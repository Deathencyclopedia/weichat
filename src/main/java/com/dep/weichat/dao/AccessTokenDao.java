package com.dep.weichat.dao;


import java.util.List;

import com.dep.weichat.entity.AccessToken;


public interface AccessTokenDao extends GenericDao<AccessToken> {
	
	/**
	 * 根据系统名获取token
	 * @param systemName
	 * @return
	 */
	public List<AccessToken> findBySystemName(String systemName);
}
