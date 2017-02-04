package com.dep.weichat.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.ConnectException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dep.weichat.entity.SystemLog;
import com.dep.weichat.service.SystemLogService;
import com.dep.weichat.util.type.SystemLogType;
import com.dep.weichat.vo.message.resp.TextMessage;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * HTTPS请求访问
 * 
 * @author Maclaine
 *
 */
@Service
public class HttpsUtil {
	private static final Logger logger = LoggerFactory.getLogger(HttpsUtil.class);

	private static SystemLogService systemLogService;
	@Resource
	public void setSystemLogService(SystemLogService systemLogService) {
		HttpsUtil.systemLogService = systemLogService;
	}

	/**
	 * 返回消息类型：文本
	 */
	public static final String RESP_MESSAGE_TYPE_TEXT = "text";

	/**
	 * 返回消息类型：音乐
	 */
	public static final String RESP_MESSAGE_TYPE_MUSIC = "music";

	/**
	 * 返回消息类型：图文
	 */
	public static final String RESP_MESSAGE_TYPE_NEWS = "news";
	
	/**
	 * 返回消息类型：图文(素材)
	 */
	public static final String RESP_MESSAGE_TYPE_MPNEWS="mpnews";

	/**
	 * 请求消息类型：文本
	 */
	public static final String REQ_MESSAGE_TYPE_TEXT = "text";

	/**
	 * 请求消息类型：图片
	 */
	public static final String REQ_MESSAGE_TYPE_IMAGE = "image";

	/**
	 * 请求消息类型：链接
	 */
	public static final String REQ_MESSAGE_TYPE_LINK = "link";

	/**
	 * 请求消息类型：地理位置
	 */
	public static final String REQ_MESSAGE_TYPE_LOCATION = "location";

	/**
	 * 请求消息类型：音频
	 */
	public static final String REQ_MESSAGE_TYPE_VOICE = "voice";

	/**
	 * 请求消息类型：推送
	 */
	public static final String REQ_MESSAGE_TYPE_EVENT = "event";

	/**
	 * 事件类型：subscribe(订阅)
	 */
	public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";

	/**
	 * 事件类型：unsubscribe(取消订阅)
	 */
	public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";

	/**
	 * 事件类型：CLICK(自定义菜单点击事件)
	 */
	public static final String EVENT_TYPE_CLICK = "CLICK";

	/**
	 * 发起https请求并获取结果
	 * 
	 * @param requestUrl
	 *            请求地址
	 * @param requestMethod
	 *            请求方式（GET、POST）
	 * @param outputStr
	 *            提交的数据
	 * @param ip
	 * 			    请求源IP
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr,String ip) {
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new SSLManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL");// TLS,
																	// "SunJSSE"
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);

			if ("GET".equalsIgnoreCase(requestMethod))
				httpUrlConn.connect();

			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			jsonObject = JSONObject.fromObject(buffer.toString());
			// 记录日志
			writeLog(jsonObject, outputStr,ip);
		} catch (ConnectException e) {
			logger.error(e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return jsonObject;
	}

	/**
	 * GET请求
	 * @param requestUrl
	 * @param ip
	 * @return
	 */
	public static JSONObject get(String requestUrl,String ip) {
		return httpRequest(requestUrl, "GET", null,ip);
	}
	
	/**
	 * GET请求
	 * 
	 * @param requestUrl
	 * @return
	 */
	public static JSONObject get(String requestUrl) {
		return httpRequest(requestUrl, "GET", null,"127.0.0.1");
	}

	/**
	 * POST请求
	 * @param requestUrl
	 * @param outputStr
	 * @param ip
	 * @return
	 */
	public static JSONObject post(String requestUrl, String outputStr,String ip) {
		return httpRequest(requestUrl, "POST", outputStr,ip);
	}
	
	/**
	 * POST请求
	 * 
	 * @param requestUrl
	 * @param outputStr
	 *            提交的数据
	 * @return
	 */
	public static JSONObject post(String requestUrl, String outputStr) {
		return httpRequest(requestUrl, "POST", outputStr,"127.0.0.1");
	}

	// 写日志
	private static void writeLog(JSONObject jsonObject, String outputStr,String ip) {
		try {
			SystemLog log = new SystemLog();
			log.setIp(ip==null?"无法获取对方主机IP":ip);
			log.setOperate(SystemLogType.SENDMESSAGE);
			log.setDate(new Date());
			if (jsonObject.get("errcode")!=null&&"0".equals(jsonObject.get("errcode").toString())) {
				log.setContent("发送成功,respMessage:" + outputStr);
			} else if(jsonObject.get("errcode")!=null&&jsonObject.get("errmsg")!=null) {
				logger.warn("微信返回异常");
				log.setContent("发送失败,respMessage:"+outputStr+",msg:"+jsonObject.toString());
			}else{
				logger.warn("未知返回数据");
				log.setContent("未知返回数据,respMessage:"+outputStr+",msg:"+jsonObject.toString());
			}
			systemLogService.save(log);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 解析xml
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> parseXml(HttpServletRequest request) throws Exception {
		// 将解析结果存储在HashMap中
		Map<String, Object> map = new HashMap<String, Object>();

		// 从request中取得输入流
		InputStream inputStream = request.getInputStream();
		// 读取输入流
		SAXReader reader = new SAXReader();
		Document document = reader.read(inputStream);
		document.setXMLEncoding(ServerUtil.loadProperty("encoding"));
		// 得到xml根元素
		Element root = document.getRootElement();
		// 得到根元素的所有子节点
		List<Element> elementList = root.elements();

		// TODO: 目前只支持到二重,后期可优化
		// 遍历所有子节点
		for (Element e : elementList) {
			if (e.elements().size() > 0) {
				Map<String, Object> tempMap = new HashMap<String, Object>();
				List<Element> tempe = e.elements();
				for (Element temp : tempe) {
					tempMap.put(temp.getName(), temp.getText());
				}
				map.put(e.getName(), tempMap);
			} else {
				map.put(e.getName(), e.getText());
			}

		}

		// 释放资源
		inputStream.close();
		inputStream = null;

		return map;
	}

	/**
	 * 文本消息对象转换成xml
	 * 
	 * @param textMessage
	 * @return
	 */
	public static String textMessageToXml(TextMessage textMessage) {
		xstream.alias("xml", textMessage.getClass());
		return xstream.toXML(textMessage);
	}

	/**
	 * 扩展xstream，使其支持CDATA块
	 */
	private static XStream xstream = new XStream(new XppDriver() {
		public HierarchicalStreamWriter createWriter(Writer out) {
			return new PrettyPrintWriter(out) {
				// 对所有xml节点的转换都增加CDATA标记
				boolean cdata = true;

				public void startNode(String name, @SuppressWarnings("rawtypes") Class clazz) {
					super.startNode(name, clazz);
				}

				protected void writeText(QuickWriter writer, String text) {
					if (cdata) {
						writer.write("<![CDATA[");
						writer.write(text);
						writer.write("]]>");
					} else {
						writer.write(text);
					}
				}
			};
		}
	});

	public static void print(HttpServletResponse response, String respMessage) throws IOException {
		PrintWriter out = response.getWriter();
		out.print(respMessage);
		out.flush();
		out.close();
	}
}
