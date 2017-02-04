package com.dep.weichat.service;

import com.dep.weichat.entity.AccessToken;

public interface AccessTokenService extends GenericService<AccessToken> {
	/**
	 * 获取token
	 * 
	 * @return
	 */
	public AccessToken getToken();

	/**
	 * 根据系统名获取token
	 * 
	 * @param systemName
	 * @return
	 */
	public AccessToken getToken(String systemName);

	/**
	 * 刷新token
	 * 
	 * @param accessToken
	 * @return
	 */
	public AccessToken updateToken(AccessToken accessToken);
}
