package com.dep.weichat.util;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.dep.weichat.entity.AccessToken;
import com.dep.weichat.service.AccessTokenService;

/**
 * 获取token
 * 
 * @author Maclaine
 *
 */
@Component
public class AccessTokenUtil {

	private static AccessTokenService accessTokenService;

	@Resource
	public void setAccessTokenService(AccessTokenService accessTokenService) {
		AccessTokenUtil.accessTokenService = accessTokenService;
	}

	private static AccessToken accessToken;

	/**
	 * 获取可用Token
	 * 
	 * @return
	 */
	public static AccessToken getAccessToken() {
		if (accessToken == null) {// 无token,根据系统名从数据库获取
			accessToken = accessTokenService.getToken();
		}
		if (accessToken.isOutTime()) {// 已有token,验证是否过期
			accessToken = accessTokenService.updateToken(accessToken);
		}
		return accessToken;
	}

}
