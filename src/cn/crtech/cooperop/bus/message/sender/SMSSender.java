package cn.crtech.cooperop.bus.message.sender;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.application.service.SystemMessageService;


/**
 * 发送短信实现类
 * 针对同一号码
 * 验证码信息1分钟不超过2条，1小时不超过5条
 * 普通通知信息30分钟内不要提交相同的内容
 * @author FORWAY R&D fuhong.chen
 * @version xx 1.0
 * @createTime Apr 22, 2010 1:39:10 PM
 */
public class SMSSender  {

	private String errorMsg = "";

	public int send(HashMap<String, Object> config,
			HashMap<String, Object> message) {
		String smsId = message.get("id").toString();
		String host = config.get("protocol").toString();
		String userName = config.get("username").toString();
		String pwd = config.get("password").toString();
		String mobile = message.get("send_to").toString();
		String content = message.get("content").toString();
		String pUser = config.get("parameter_id").toString();
		String pPwd = config.get("parameter_password").toString();
		String pMobile = config.get("parameter_mobile").toString();
		String pMessage = config.get("parameter_content").toString();
		String parameter_sub = (String)config.get("parameter_sub");
		String urlStr = host + "?" + pUser + "=" + userName
				+ "&" + pPwd + "=" + pwd + "&" + pMobile + "="
				+ mobile + "&" + pMessage + "=";
		//String u = "https://mb345.com/ws/BatchSend2.aspx?CorpID="+userName+"&Pwd="+pwd+"&Mobile="+mobile+"&Content=";
		int state = sendSms(urlStr, content);

		Map<String,Object> param = new HashMap<String,Object>();
		param.put("state", state);
		param.put("send_time", "sysdate");
		//param.put("error_msg", errorMsg);
		param.put("id", smsId);
		SystemMessageService sms = new SystemMessageService();
		try {
			sms.update(param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (state > 0) {
			System.out.println("NO[" + smsId + "] sms sending OK!");
		} else {
			System.out.println("NO[" + smsId + "] sms sending failure!");
		}
		return state;
	}
	/**
	 * @param config 配置
	 * @param message 消息
	 * @return
	 */
	public void save(Map<String, Object> config,
			Map<String, Object> message) {

		Map<String,Object> param = new HashMap<String,Object>();
		param.put("state", 0);
		param.put("send_time", "sysdate");
		param.put("content", message.get("content"));
		param.put("send_to", message.get("send_to"));
		param.put("tunnel_id", config.get("id"));
		param.put("type", 1);
		//param.put("error_msg", errorMsg);
		SystemMessageService sms = new SystemMessageService();
		try {
			sms.insert(param);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 直接发送
	 * @param config 配置
	 * @param message 消息
	 * @return
	 */
	public int sendImmediately(Map<String, Object> config,
			Map<String, Object> message) {
		String host = config.get("protocol").toString();
		String userName = config.get("username").toString();
		String pwd = config.get("password").toString();
		String mobile = message.get("send_to").toString();
		String content = message.get("content").toString();
		String pUser = config.get("parameter_id").toString();
		String pPwd = config.get("parameter_password").toString();
		String pMobile = config.get("parameter_mobile").toString();
		String pMessage = config.get("parameter_content").toString();
		String parameter_sub = (String)config.get("parameter_sub");
		String urlStr = host + "?" + pUser + "=" + userName
				+ "&" + pPwd + "=" + pwd + "&" + pMobile + "="
				+ mobile + "&" + pMessage + "=";
		String u = "https://mb345.com/ws/BatchSend2.aspx?CorpID="+userName+"&Pwd="+pwd+"&Mobile="+mobile+"&Content=";
		int state = sendSms(urlStr, content);

		Map<String,Object> param = new HashMap<String,Object>();
		param.put("state", state);
		param.put("send_time", "sysdate");
		param.put("content", message.get("content"));
		param.put("send_to", message.get("send_to"));
		param.put("tunnel_id", config.get("id"));
		param.put("type", 1);
		param.put("error_msg", errorMsg);
		SystemMessageService sms = new SystemMessageService();
		try {
			sms.insert(param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return state;
	}
	/**
	 * 直接发送
	 * 
	 * @param urlStr
	 * @param content
	 * @return
	 * @createTime Apr 29, 2010 10:24:49 AM
	 */
	private int sendSms(String urlStr, String content) {
		int resultState = 0;
		try {
			boolean b = true;
			String msg = "";
			String message = content.replaceAll("<br/>", " ");
			while (b) {
				if (message.length() > 60) {
					msg = message.substring(0, 60);
					message = message.substring(60);
				} else {
					msg = message;
					b = false;
				}
				String resStr = doGetRequest((urlStr + URLEncoder.encode(msg, "GB2312"))
						.toString());

				// 解析响应字符串
				//Map<String, String> pp = parseResStr(resStr);

				resultState = 1;
			}
		} catch (Exception e) {
			resultState = -1;
			e.printStackTrace();
			System.out.println(e.toString());
			errorMsg = e.toString();
		}
		return resultState;
	}

	/**
	 * 发送http GET请求，并返回http响应字符串
	 * 
	 * @param urlstr
	 *            完整的请求url字符串
	 * @return
	 */
	private String doGetRequest(String urlstr) {
		String res = null;
		try {
			System.out.println("SMS Setting[" + urlstr + "]");

			URL url = new URL(urlstr);
			HttpURLConnection httpConn = (HttpURLConnection) url
					.openConnection();
			httpConn.setRequestMethod("GET");
			httpConn.setRequestProperty("Content-Type",
					"text/html; charset=GB2312");
//			System.setProperty("sun.net.client.defaultConnectTimeout", "5000");// jdk1

			// 连接超时
//			System.setProperty("sun.net.client.defaultReadTimeout", "10000"); // jdk1

			// 读操作超时
			 httpConn.setConnectTimeout(5000);//jdk 1.5换成这个,连接超时
			 httpConn.setReadTimeout(10000);//jdk 1.5换成这个,读操作超时
			httpConn.setDoInput(true);
			int rescode = httpConn.getResponseCode();
			if (rescode == 200) {
				BufferedReader bfw = new BufferedReader(new InputStreamReader(
						httpConn.getInputStream()));
				res = bfw.readLine();
			} else {
				res = "Http request error code :" + rescode;
				errorMsg = res;
			}
		} catch (Exception e) {
			System.out.println(e.toString());
			errorMsg = e.toString();
		}
		return res;
	}

	/**
	 * 短信下行 请求响应字符串解析到HashMap
	 * 
	 * @param resStr
	 * @return
	 */
	private Map<String, String> parseResStr(String resStr) {
		Map<String, String> pp = new HashMap<String, String>();
		try {
			String[] ps = resStr.split("&");
			for (int i = 0; i < ps.length; i++) {
				int ix = ps[i].indexOf("=");
				if (ix != -1) {
					pp.put(ps[i].substring(0, ix), ps[i].substring(ix + 1));
				}
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return pp;
	}

	/**
	 * Hex编码字符
	 */
	private static final char[] DIGITS = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	/**
	 * 将普通字符串转换成Hex编码字符
	 * 
	 * @param dataCoding
	 *            编码格式5表示GBK编码表示UnicodeBigUnmarked编码表示ISO8859-1编码
	 * @param realStr
	 *            普字符
	 * @return Hex编码字符
	 */
	public static String encodeHexStr(int dataCoding, String realStr) {
		String hexStr = null;

		if (realStr != null) {
			byte[] data = null;
			try {
				if (dataCoding == 15) {
					data = realStr.getBytes("GBK");
				} else if ((dataCoding & 0x0C) == 0x08) {
					data = realStr.getBytes("UnicodeBigUnmarked");
				} else {
					data = realStr.getBytes("ISO8859-1");
				}
			} catch (UnsupportedEncodingException e) {
				System.out.println(e.toString());
			}

			if (data != null) {
				int len = data.length;
				char[] out = new char[len << 1];
				// two characters form the hex value.
				for (int i = 0, j = 0; i < len; i++) {
					out[j++] = DIGITS[(0xF0 & data[i]) >>> 4];
					out[j++] = DIGITS[0x0F & data[i]];
				}
				hexStr = new String(out);
			}
		}
		return hexStr;
	}
}
