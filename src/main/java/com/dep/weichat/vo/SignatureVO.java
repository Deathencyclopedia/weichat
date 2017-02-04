package com.dep.weichat.vo;

public class SignatureVO {
	/**
	 * 微信加密签名
	 */
	private String signature;
	/**
	 * 时间戳
	 */
	private String timestamp;
	/**
	 * 随机数
	 */
	private String nonce;
	/**
	 * 随机字符串
	 */
	private String echostr;
	
	/**
	 * 获取微信加密签名
	 */
	public String getSignature() {
		return signature;
	}
	/**
	 * 获取时间戳
	 */
	public String getTimestamp() {
		return timestamp;
	}
	/**
	 * 获取随机数
	 */
	public String getNonce() {
		return nonce;
	}
	/**
	 * 获取随机字符串
	 */
	public String getEchostr() {
		return echostr;
	}
	
	
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public void setNonce(String nonce) {
		this.nonce = nonce;
	}
	public void setEchostr(String echostr) {
		this.echostr = echostr;
	}
	
	
}
