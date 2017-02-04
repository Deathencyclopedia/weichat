package com.dep.weichat.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dep.weichat.dao.SystemLogDao;
import com.dep.weichat.entity.SystemLog;
import com.dep.weichat.service.SystemLogService;

@Transactional
@Service
public class SystemLogServiceImpl extends GenericServiceImpl<SystemLog> implements SystemLogService {

	private SystemLogDao systemLogDao;

	@Resource
	public void setSystemLogDao(SystemLogDao systemLogDao) {
		this.systemLogDao = systemLogDao;
		this.genericDao = systemLogDao;
	}
}
