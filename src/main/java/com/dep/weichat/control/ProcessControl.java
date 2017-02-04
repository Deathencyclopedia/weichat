package com.dep.weichat.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dep.weichat.entity.Binding;
import com.dep.weichat.exception.SystemException;
import com.dep.weichat.service.BindingService;
import com.dep.weichat.service.ProcessService;
import com.dep.weichat.service.SystemLogService;
import com.dep.weichat.util.AccessTokenUtil;
import com.dep.weichat.util.HttpsUtil;
import com.dep.weichat.util.type.ErrorCodeType;

/**
 * 系统逻辑控制.用于向微信发送消息
 * 
 * @author Maclaine
 *
 */
@Controller
@RequestMapping("/api")
public class ProcessControl {
	private static final Logger logger = LoggerFactory.getLogger(ProcessControl.class);

	@Resource
	private BindingService bindingService;
	@Resource
	private ProcessService processService;
	@Resource
	private SystemLogService systemLogService;

	@RequestMapping(value = { "/", "index" })
	public String index() {
		return "index";
	}

	//@ResponseBody
	//@RequestMapping("/postmessage")
	@Deprecated
	public Map<String, Object> sendMessage(HttpServletRequest request, String qrcode, String message) {
		// TODO:可能需要对访问者进行身份验证
	    if ((qrcode == null) || (message == null)) {
	        throw new SystemException(ErrorCodeType.param_null);
	      }
	      String[] split = message.split(",");
	      List<String> numbers = new ArrayList<String>();
	      String[] arrayOfString1;
	      int j = (arrayOfString1 = split).length;
	      for (int i = 0; i < j; i++)
	      {
	        String str = arrayOfString1[i];
	        numbers.add(str);
	      }
	      Map<String, Object> map = new HashMap<String, Object>();
	      Binding binding = this.bindingService.findByQRCode(qrcode);
	      String respMessage = this.processService.postMessage(binding, numbers);
	      JSONObject post = HttpsUtil.post("https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + AccessTokenUtil.getAccessToken().getAccessToken(), respMessage);
	      
	      logger.info("发送完毕");
	      map.put("status", post.get("errcode"));
	      map.put("info", post.get("errmsg"));
	      return map;
	}
}
