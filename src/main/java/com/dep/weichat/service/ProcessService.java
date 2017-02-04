package com.dep.weichat.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.dep.weichat.entity.Binding;

public interface ProcessService {

	/**
	 * 处理微信请求
	 * 
	 * @param request
	 * @return
	 */
	public String process(HttpServletRequest request);

	/**
	 * 获取向用户发送客服消息的JSON格式(使用素材)
	 * 
	 * @param binding
	 * @param message
	 * @return
	 */
	public String postMessageByMedia(Binding binding, String number);

	/**
	 * 获取向用户发送客服消息的JSON格式(使用数据库,单图文)
	 * 
	 * @param binding
	 * @param number
	 * @return
	 */
	public String postMessage(Binding binding, String number);

	/**
	 * 获取向用户发送客服消息的JSON格式(使用数据库,多图文)
	 * 
	 * @param binding
	 * @param numbers
	 * @return
	 */
	public String postMessage(Binding binding, List<String> numbers);

	/**
	 * 向用户发送素材客服消息(具备发送功能)
	 * 
	 * @param qrCodeID
	 * @param number
	 * @param ip
	 * @return 是否发送成功
	 */
	public boolean postMessage(String qrCodeID, String number, String ip);
}
