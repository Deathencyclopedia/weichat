package com.dep.weichat.service.impl;

import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dep.weichat.dao.AccessTokenDao;
import com.dep.weichat.entity.AccessToken;
import com.dep.weichat.service.AccessTokenService;
import com.dep.weichat.util.HttpsUtil;
import com.dep.weichat.util.ServerUtil;

@Transactional
@Service
public class AccessTokenServiceImpl extends GenericServiceImpl<AccessToken> implements AccessTokenService {

	private AccessTokenDao accessTokenDao;

	@Resource
	public void setAccessTokenDao(AccessTokenDao accessTokenDao) {
		this.accessTokenDao = accessTokenDao;
		this.genericDao = accessTokenDao;
	}

	// 从配置文件读取数据
	private String grantURL = ServerUtil.loadProperty("grantURL");
	private String appid = ServerUtil.loadProperty("appid");
	private String appsecret = ServerUtil.loadProperty("appsecret");
	private String systemName = ServerUtil.loadProperty("systemname");

	@Override
	public AccessToken getToken() {
		return getToken(systemName);
	}

	@Override
	public AccessToken getToken(String systemName) {
		List<AccessToken> list = accessTokenDao.findBySystemName(systemName);
		if (list.size() > 0) {// 存在则返回
			return list.get(0);
		} else {// 不存在则获取
			return updateToken(null);
		}
	}

	@Override
	public AccessToken updateToken(AccessToken accessToken) {
		if (accessToken == null) {
			accessToken = new AccessToken();
		}
		// 从微信服务器获取token,expires
		String url = grantURL + "?grant_type=client_credential&appid=" + appid + "&secret=" + appsecret;
		JSONObject jsonObject = HttpsUtil.get(url);
		accessToken.setAccessToken(jsonObject.getString("access_token"));
		accessToken.setExpires(Long.parseLong(jsonObject.getString("expires_in")));
		accessToken.setSystemName(systemName);
		accessToken.setUpdateDate(Calendar.getInstance().getTime());
		accessTokenDao.saveOrUpdate(accessToken);
		return accessToken;
	}

}
