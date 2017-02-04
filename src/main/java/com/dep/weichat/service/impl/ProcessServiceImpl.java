package com.dep.weichat.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dep.weichat.entity.Binding;
import com.dep.weichat.entity.Content;
import com.dep.weichat.entity.PostStatus;
import com.dep.weichat.exception.SystemException;
import com.dep.weichat.service.AccessTokenService;
import com.dep.weichat.service.BindingService;
import com.dep.weichat.service.ContentService;
import com.dep.weichat.service.PostStatusService;
import com.dep.weichat.service.ProcessService;
import com.dep.weichat.service.SystemLogService;
import com.dep.weichat.util.AccessTokenUtil;
import com.dep.weichat.util.EventKeyConst;
import com.dep.weichat.util.HttpsUtil;
import com.dep.weichat.util.type.ErrorCodeType;
import com.dep.weichat.vo.message.resp.Article;
import com.dep.weichat.vo.message.resp.TextMessage;

@Transactional
@Service
public class ProcessServiceImpl implements ProcessService {
	private static final Logger logger = LoggerFactory.getLogger(ProcessServiceImpl.class);

	@Resource
	private AccessTokenService accessTokenService;
	@Resource
	private BindingService bindingService;
	@Resource
	private ContentService contentService;
	@Resource
	private SystemLogService systemLogService;
	@Resource
	private PostStatusService postStatusService;

	@Override
	public String process(HttpServletRequest request) {
		String respMessage = null;
		try {
			// 默认返回的文本消息内容
			String respContent = "请求处理异常，请稍候尝试！";
			Map<String, Object> requestMap = HttpsUtil.parseXml(request);
			// 公众帐号
			String fromUserName = requestMap.get("FromUserName").toString();
			// 发送方帐号（open_id）
			String toUserName = requestMap.get("ToUserName").toString();
			// 消息类型
			String msgType = requestMap.get("MsgType").toString();
			logger.debug("from:{},to:{},type:{}", fromUserName, toUserName, msgType);
			// 回复文本消息
			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(HttpsUtil.RESP_MESSAGE_TYPE_TEXT);
			textMessage.setFuncFlag(0);
			// 文本消息
			if (msgType.equals(HttpsUtil.REQ_MESSAGE_TYPE_TEXT)) {
				respContent = "";
			}
			// 图片消息
			else if (msgType.equals(HttpsUtil.REQ_MESSAGE_TYPE_IMAGE)) {
				respContent = "";
			}
			// 地理位置消息
			else if (msgType.equals(HttpsUtil.REQ_MESSAGE_TYPE_LOCATION)) {
				respContent = "";
			}
			// 链接消息
			else if (msgType.equals(HttpsUtil.REQ_MESSAGE_TYPE_LINK)) {
				respContent = "";
			}
			// 音频消息
			else if (msgType.equals(HttpsUtil.REQ_MESSAGE_TYPE_VOICE)) {
				respContent = "";
			}
			// 事件推送
			else if (msgType.equals(HttpsUtil.REQ_MESSAGE_TYPE_EVENT)) {
				// 事件类型
				String eventType = requestMap.get("Event").toString();
				logger.debug("key:{},code:{},type:{},result:{}", requestMap.get("EventKey"), requestMap.get("ScanCodeInfo"), requestMap.get("ScanType"),
						requestMap.get("ScanResult"));
				// 订阅
				if (eventType.equals(HttpsUtil.EVENT_TYPE_SUBSCRIBE)) {
					respContent = "谢谢您的关注！";
				}
				// 取消订阅
				else if (eventType.equals(HttpsUtil.EVENT_TYPE_UNSUBSCRIBE)) {
					// 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
				}
				// 自定义菜单点击事件
				else if (eventType.equals(HttpsUtil.EVENT_TYPE_CLICK)) {
					respContent = "";
				}
				// 其他事件
				else {
					respContent = "";
					// 事件KEY值，与创建自定义菜单时指定的KEY值对应
					String eventKey = requestMap.get("EventKey").toString();
					// 自定义事件key
					if (eventKey.equals(EventKeyConst.menu_0_0)) {
						try {
							@SuppressWarnings("unchecked")
							Map<String, Object> tempMap = (Map<String, Object>) requestMap.get("ScanCodeInfo");
							JSONObject json = JSONObject.fromObject(tempMap.get("ScanResult").toString());
							// TODO:可能需要进行数据验证,确保是指定的二维码
							Binding binding = bindingService.findByQRCode(json.getString("qrcode"));
							if (binding == null) {
								binding = new Binding();
							}
							binding.setOpenID(fromUserName);
							binding.setQrcodeID(json.getString("qrcode"));
							List<PostStatus> list = binding.getPostStatus();
							if (list != null) {
								for (PostStatus postStatus : list) {
									postStatus.setUpdateDate(null);
								}
							}
							bindingService.saveOrUpdate(binding);
							logger.info("用户绑定数据,code:{}", json.getString("qrcode"));
							respContent = "数据绑定成功！";// +json.getString("qrcode");
						} catch (Exception e) {
							logger.error(e.getMessage());
							e.printStackTrace();
							respContent = "数据绑定失败！";
						}
					} else {
						logger.warn("未知事件");
					}
				}
			}
			textMessage.setContent(respContent);
			respMessage = HttpsUtil.textMessageToXml(textMessage);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return respMessage;
	}

	@Override
	public String postMessageByMedia(Binding binding, String number) {
		if (binding == null) {
			throw new SystemException(ErrorCodeType.data_error);
		}
		// 构造JSON结构
		Map<String, Object> map = new HashMap<String, Object>();

		Content content = contentService.findByNumber(number);
		if (content == null) {
			throw new SystemException(ErrorCodeType.data_error);
		}
		// 填充数据
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("media_id", content.getMediaID());
		map.put(HttpsUtil.RESP_MESSAGE_TYPE_MPNEWS, map2);
		map.put("touser", binding.getOpenID());
		map.put("msgtype", HttpsUtil.RESP_MESSAGE_TYPE_MPNEWS);
		return JSONObject.fromObject(map).toString();
	}


	@Override
	public String postMessage(Binding binding, String number) {
		List<String> numbers=new ArrayList<String>();
		numbers.add(number);
		return postMessage(binding,numbers);
	}
	
	@Override
	public String postMessage(Binding binding, List<String> numbers) {
		if (binding == null) {
			throw new SystemException(ErrorCodeType.data_error);
		}
		// 构造JSON结构
		Map<String, Object> map = new HashMap<String, Object>();

		List<Content> list = contentService.findByNumber(numbers);
		if (list.size() <= 0) {
			throw new SystemException(ErrorCodeType.data_error);
		}

		Map<String, Object> map2 = new HashMap<String, Object>(); // 填充数据
		List<Article> articleList = new ArrayList<Article>();
		for (Content item : list) {
			Article article = new Article(item);
			articleList.add(article);
		}
		map2.put("articles", articleList);

		map.put(HttpsUtil.RESP_MESSAGE_TYPE_NEWS, map2);
		map.put("touser", binding.getOpenID());
		map.put("msgtype", HttpsUtil.RESP_MESSAGE_TYPE_NEWS);
		return JSONObject.fromObject(map).toString();
	}

	@Override
	public boolean postMessage(String qrCodeID, String number,String ip) {
		try {
			PostStatus postStatus = postStatusService.findByQrcodeAndNumber(qrCodeID, number);
			if (postStatus == null) {
				postStatus = new PostStatus();
				Binding binding = bindingService.findByQRCode(qrCodeID);
				Content content = contentService.findByNumber(number);
				postStatus.setBinding(binding);
				postStatus.setContent(content);
			} else if (!postStatus.isOutTime()) {
				return false;
			}
			String respMessage = postMessageByMedia(postStatus.getBinding(), postStatus.getContent().getNumber());
			JSONObject jsonObject = HttpsUtil.post("https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + AccessTokenUtil.getAccessToken().getAccessToken(),
					respMessage,ip);
			if ("0".equals(jsonObject.get("errcode").toString())) {// 发送成功才保存记录
				postStatus.setUpdateDate(new Date());
				postStatusService.saveOrUpdate(postStatus);
			}
		}catch(SystemException e){
			logger.error(e.getMessage());
		}catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
