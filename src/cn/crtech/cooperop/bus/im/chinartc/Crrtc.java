package cn.crtech.cooperop.bus.im.chinartc;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONException;
import org.json.JSONObject;

import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.util.GlobalVar;

public class Crrtc {
	public static int ACC_INNER = 10;
	public static int ACC_ESURF = 11;
	public static int ACC_WEIBO = 12;
	public static int ACC_QQ = 13;

	private static long lastModifyTime = -1;
	private static Properties configs = null;

	private Crrtc() {
	}

	private static Crrtc ctrtc = getInstance();

	private synchronized static Crrtc getInstance() {
		if (ctrtc == null) {
			ctrtc = new Crrtc();
		}
		return ctrtc;
	}
	
	private static void load() {
		String config = GlobalVar.getSystemProperty("chinartc.config");
		if (config.charAt(0) != '/' && config.charAt(1) != ':') {
			config = GlobalVar.getWorkPath() + File.separator + config;
		}
		File f = new File(config);
		if (f.lastModified() > lastModifyTime) {
			try {
				configs = CommonFun.loadPropertiesFile(f.getAbsolutePath());
			} catch (IOException e) {
				log.error("load ChinaRTC config Failed.", e);
			}
		}
	}
	
	public static String getConfig(String key) {
		load();
		return configs.getProperty(key);
	}

	public static String generateToken(int pid, String username, String terminalType) throws JSONException {
		if (username == null) {
			throw new JSONException("ERR_NO_USERNAME");
		} else if (pid != ACC_INNER && pid != ACC_ESURF && pid != ACC_WEIBO && pid != ACC_QQ) {
			throw new JSONException("ERR_PID_ILLEGAL");
		} else if (terminalType == null) {
			throw new JSONException("ERR_NO_TERMINALTYPE");
		} else if ((terminalType.equals("Browser") || terminalType.equals("TV") || terminalType.equals("Phone")
				|| terminalType.equals("Pad")) == false) {
			throw new JSONException("ERR_TERMINALTYPE_ILLEGAL");
		}

		load();
		String appid = configs.getProperty("appid");
		String appkey = configs.getProperty("appkey");
		String restServer = configs.getProperty("restServer");
		String APIversion = configs.getProperty("APIversion");
		String authType_Token = configs.getProperty("authType_Token");

		String appAccountID_Token = pid + "-" + username;
		String userTerminalType_Token = terminalType;
		long userTerminalSN_Token = System.currentTimeMillis();

		String grantedCapabilityID_Token = "100<200<301<302<303<400";
		String callbackURL_Token = "www.baidu.com";
		String url_Token = restServer + "/RTC/ws/" + APIversion + "/ApplicationID/" + appid
				+ "/CapabilityToken";
		try {
			URL url = new URL(url_Token);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("authorization",
					"RTCAUTH,realm=AppServer,ApplicationId=" + appid + ",APP_Key=" + appkey);
			connection.connect();

			DataOutputStream out = new DataOutputStream(connection.getOutputStream());
			JSONObject obj = new JSONObject();
			obj.put("authType", authType_Token);
			obj.put("appAccountID", appAccountID_Token);
			obj.put("userTerminalType", userTerminalType_Token);
			obj.put("userTerminalSN", userTerminalSN_Token);
			obj.put("grantedCapabiltyID", grantedCapabilityID_Token);
			obj.put("callbackURL", callbackURL_Token);

			out.writeBytes(obj.toString());
			out.flush();
			out.close();

			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String lines;
			StringBuffer sb = new StringBuffer("");
			while ((lines = reader.readLine()) != null) {
				lines = new String(lines.getBytes(), "utf-8");
				sb.append(lines);
			}
			System.out.println(sb);
			String sbstring = sb.toString();
			if (sbstring.equals("1001")) {
				return "FAIL_CONNECT_RTC_PLATFORM";
			} else if (sbstring.equals("1002")) {
				return "FAIL_AUTHENTICATION";
			} else {
				JSONObject sbjson = new JSONObject(sbstring);
				String web_sip_password = sbjson.getString("capabilityToken");
				reader.close();
				connection.disconnect();
				String token = web_sip_password + "|" + username + "|" + pid + "|" + appid + "|" + userTerminalSN_Token
						+ "|" + userTerminalType_Token;
				log.debug("token:" + token);
				String tokenbase64 = null;
				tokenbase64 = encodeStr(token);
				log.debug("tokenbase64:" + tokenbase64);
				return tokenbase64;
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "error";
	}

	public static String encodeStr(String plainText) {
		byte[] b = plainText.getBytes();
		Base64 base64 = new Base64();
		b = base64.encode(b);
		String s = new String(b);
		return s;
	}

}
