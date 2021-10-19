package cn.crtech.cooperop.bus.message.sender;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import cn.crtech.cooperop.application.service.SystemMessageService;
import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.util.ELExpression;
import cn.crtech.cooperop.bus.util.GlobalVar;


public class MB345 implements GateWay {
	
	private Map<String, String> templates = new HashMap<String, String>();
	private String url_mould;
	private int letter_limit;
	private int mobile_limit;
	private String signature;
	private String mobile_separater;

	public MB345(String url, String no, String pwd, String signature) {
		this.url_mould = url+ "?CorpID=" + no + "&Pwd=" + pwd + "&Mobile=$[mobile]&Content=$[content]";
		this.mobile_limit = Integer.parseInt(GlobalVar.getSystemProperty("mobile_limit", "60"));
		this.mobile_separater = GlobalVar.getSystemProperty("mobile_separater", ",");
		this.letter_limit = Integer.parseInt(GlobalVar.getSystemProperty("letter_limit", "20"));
		this.signature = signature;
	}

	@Override
	public void send(String mobile, String template, Map<String, Object> params, Object smsId) throws Exception {
		send(new String[] { mobile }, template, params, smsId);
	}

	@Override
	public void send(String[] mobiles, String template, Map<String, Object> params, Object smsId) throws Exception {
		if (CommonFun.isNe(mobiles))
			throw new Exception("接收短信手机号码不能为空.");
		template = templates.get(template);
		if (CommonFun.isNe(template))
			throw new Exception("模板文件" + template + "未找到.");
		String msg = "";
		String message = (String) ELExpression.excuteExpression(template, params);

		StringBuffer t = new StringBuffer();
		int ts = 1;
		int tl = letter_limit - 5 - signature.length();
		if (message.length() > letter_limit) {
			ts = message.length() / (tl) + (message.length() % (tl) > 0 ? 1 : 0);
		}
		for (int i = 0; i < mobiles.length; i++) {
			if (i % mobile_limit == 0) {
				t = new StringBuffer();
			} else {
				t.append(mobile_separater);
			}
			t.append(mobiles[i]);
			if (i == mobiles.length - 1 || i % mobile_limit == mobile_limit - 1) {
				String m = t.toString();
				for (int j = 1; j <= ts; j++) {
					if (ts > 1) {
						msg = "(" + j + "/" + ts + ")";
						if (tl * j > message.length()) {
							msg += message.substring(tl * (j - 1));
						} else {
							msg += message.substring(tl * (j - 1), tl * j);
						}
						msg += signature;
					} else {
						msg = message;
					}
					String url = url_mould;
					// $[mobile]、$[content]、$[sendtime]、$[authno]、$[sub]
					url = CommonFun.replaceOnlyStr(url, "$[mobile]", m);
					url = CommonFun.replaceOnlyStr(url, "$[sendtime]", String.valueOf(System.currentTimeMillis()));
					url = CommonFun.replaceOnlyStr(url, "$[content]", URLEncoder.encode(msg, "GB2312"));
					log.debug("SMS Setting[" + url + "] begin");
					String resStr = doGetRequest(url);
					log.debug("SMS Setting[" + url + "] end, " + resStr);
				}
			}
		}
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("state", 1);
		param.put("send_time", "sysdate");
		//param.put("error_msg", errorMsg);
		param.put("id", smsId);
		SystemMessageService sms = new SystemMessageService();
		try {
			sms.update(param);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 发送http GET请求，并返回http响应字符串
	 * 
	 * @param urlstr
	 *            完整的请求url字符串
	 * @return
	 * @throws Exception
	 */
	private String doGetRequest(String urlstr) throws Exception {
		String res = null;
		URL url = new URL(urlstr);
		HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
		httpConn.setRequestMethod("GET");
		httpConn.setRequestProperty("Content-Type", "text/html; charset=GB2312");
		// 读操作超时
		httpConn.setConnectTimeout(5000);
		httpConn.setReadTimeout(10000);
		httpConn.setDoInput(true);
		int rescode = httpConn.getResponseCode();
		if (rescode == 200) {
			BufferedReader bfw = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
			res = bfw.readLine();
		} else {
			throw new Exception("Http request error code :" + rescode);
		}
		return res;
	}
}
