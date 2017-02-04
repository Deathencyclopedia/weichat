package com.dep.weichat.control;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dep.weichat.entity.SystemLog;
import com.dep.weichat.entity.User;
import com.dep.weichat.service.SystemLogService;
import com.dep.weichat.util.ServerUtil;

@RequestMapping("admin")
public class AdminControl {
	private static final Logger logger = LoggerFactory.getLogger(AdminControl.class);
	@Resource
	private SystemLogService systemLogService;
	
	@RequestMapping(value = "bsclogin")
	public String admin(){
		return "login.jsp";
	}
	
	@RequestMapping(value="login")
	public String login(@Valid User user,HttpServletRequest request){
		if(ServerUtil.loadProperty("appid").equals(user.getUserName())&&ServerUtil.loadProperty("appsecret").equals(user.getPassword())){
			logger.info("admin login!ip:{}",ServerUtil.getIpAddr(request));
			systemLogService.save(new SystemLog("管理员登录","管理员登录成功",ServerUtil.getIpAddr(request)));
		}else{
			logger.warn("admin login fail!ip:{}",ServerUtil.getIpAddr(request));
			systemLogService.save(new SystemLog("管理员登录","管理员登录失败",ServerUtil.getIpAddr(request)));
		}
		return "main";
	}
	
	
}
