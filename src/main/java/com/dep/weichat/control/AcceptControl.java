package com.dep.weichat.control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dep.weichat.service.ProcessService;
import com.dep.weichat.util.HttpsUtil;
import com.dep.weichat.util.ServerUtil;
import com.dep.weichat.vo.SignatureVO;

/**
 * 用于接受微信的推送消息
 * @author Maclaine
 *
 */
@Controller
@RequestMapping("/accept")
public class AcceptControl {
	private static final Logger logger = LoggerFactory.getLogger(AcceptControl.class);
	@Resource
	private ProcessService processService;

	/**
	 * 验证微信
	 * @param signature
	 * @return
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping(value={"/process"},method=RequestMethod.GET)
	public Map<String,Object> checkSignature(SignatureVO signature,HttpServletResponse response) throws IOException{
		Map<String,Object> map=new HashMap<String, Object>();
		List<String> list=new ArrayList<String>();
		list.add(ServerUtil.loadProperty("token"));
		list.add(signature.getTimestamp());
		list.add(signature.getNonce());
		Collections.sort(list);
		String str="";
		for (String item : list) {
			str+=item;
		}
		String sha1 = DigestUtils.sha1Hex(str);
		logger.debug("{}",sha1);
		if(signature.getSignature().equals(sha1)){
			HttpsUtil.print(response, signature.getEchostr());
			//logger.debug("微信接入成功");
			return null;
		}else{
			map.put("echostr", "error");
			logger.warn("微信接入失败,signature:{}",signature.getSignature());
		}
		
		return map;
	}
	
	/**
	 * 响应微信推送消息
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value={"/process"},method=RequestMethod.POST)
	public Map<String,Object> process(HttpServletRequest request,HttpServletResponse response) throws IOException{
		//logger.debug("process:post");
		String respMessage =processService.process(request);
		HttpsUtil.print(response, respMessage);
		return null;
	}
}
