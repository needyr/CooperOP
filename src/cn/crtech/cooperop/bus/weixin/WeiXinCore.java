package cn.crtech.cooperop.bus.weixin;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;

import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.weixin.bean.Authorization;
import cn.crtech.cooperop.bus.weixin.bean.UserAuthorization;
import cn.crtech.cooperop.bus.weixin.util.HttpClient;

public class WeiXinCore {
	public static enum AUTH_INTERFACE {
		access_token("/cgi-bin/token"), get_ticket("/cgi-bin/ticket/getticket");

		private AUTH_INTERFACE(String uri) {
			this.uri = uri;
		}

		private String uri;

		public String getUri() {
			return uri;
		}

		public void setUri(String uri) {
			this.uri = uri;
		}
	}

	public static enum POST_INTERFACE {
		create_qrcode("/cgi-bin/qrcode/create"), user_list("/cgi-bin/user/get"), message_mass_sendall(
				"/cgi-bin/message/mass/sendall"), message_mass_send(
						"/cgi-bin/message/mass/send"), message_template_send(
								"/cgi-bin/message/template/send"), material_list(
										"/cgi-bin/material/batchget_material"), menu_create("/cgi-bin/menu/create"),;

		private POST_INTERFACE(String uri) {
			this.uri = uri;
		}

		private String uri;

		public String getUri() {
			return uri;
		}

		public void setUri(String uri) {
			this.uri = uri;
		}
	}

	public static enum GET_INTERFACE {
		user_info("/cgi-bin/user/info"), oauth2_access_token("/sns/oauth2/access_token"), oauth2_refresh_token(
				"/sns/oauth2/refresh_token"), page_userinfo("/sns/userinfo"), material_count(
						"/cgi-bin/material/get_materialcount"), template_list(
								"/cgi-bin/template/get_all_private_template"), menu_list("/cgi-bin/menu/get"),;

		private GET_INTERFACE(String uri) {
			this.uri = uri;
		}

		private String uri;

		public String getUri() {
			return uri;
		}

		public void setUri(String uri) {
			this.uri = uri;
		}
	}

	protected static String api_server;
	protected static String database_id;
	protected static String code;
	protected static String login_action;
	protected static String auth_server;

	protected static String AppID;

	protected static String AppSecret;

	public static int connect_timeout;

	public static int request_timeout;

	protected static String configfile;

	protected static long lastmodify = 0;

	protected static Properties config;

	protected static refreshToken rt;

	protected static Authorization resourceauth;
	
	protected static Map<String, UserAuthorization> userauthmap;
	
	private static refreshUserToken rut;

	protected static String workpath;

	public static void init(String workpath, String configfile) throws Exception {
		WeiXinCore.workpath = workpath;
		WeiXinCore.configfile = configfile;
		if (configfile.charAt(0) != '/' && configfile.charAt(1) != ':') {
			WeiXinCore.configfile = WeiXinCore.workpath + File.separator + configfile;
		}
		loadProperties();
		userauthmap = new HashMap<String, UserAuthorization>();
		rut = new refreshUserToken();
		rut.start();
		loadWeiXinToken();
		rt = new refreshToken();
		rt.start();
	}

	protected static void loadProperties() {
		File f = new File(configfile);
		if (lastmodify >= f.lastModified())
			return;

		try {
			config = CommonFun.loadPropertiesFile(configfile);
		} catch (IOException e) {
			log.error("load weixin.properties failed.", e);
			return;
		}
		lastmodify = f.lastModified();

		api_server = config.getProperty("APIServer");
		auth_server = config.getProperty("AuthUrl");
		AppID = config.getProperty("AppID");
		AppSecret = config.getProperty("AppSecret");
		database_id = config.getProperty("database.id");
		code = config.getProperty("code");
		login_action = config.getProperty("login_action");
		connect_timeout = Integer.parseInt(config.getProperty("connect_timeout"));
		request_timeout = Integer.parseInt(config.getProperty("request_timeout"));
	}

	public static Record get(GET_INTERFACE service, Record message) throws Exception {
		if (WeiXinCore.resourceauth == null) {
			throw new Exception("Weixin Client not ready.");
		}

		StringBuffer url = new StringBuffer();
		url.append(WeiXinCore.api_server);
		url.append(service.getUri());
		url.append("?access_token=" + WeiXinCore.resourceauth.getAccess_token());

		String rtnstr = null;
		try {
			rtnstr = HttpClient.get(url.toString(), message);
		} catch (Throwable e) {
			throw new Exception(e);
		}

		@SuppressWarnings("unchecked")
		Map<String, Object> rtn = CommonFun.json2Object(rtnstr, Map.class);

		if (rtn == null) {
			throw new Exception("call weixin " + service + " failed. " + rtnstr);
		}
		if (rtn.containsKey("errcode") && (int) rtn.get("errcode") > 0) {
			throw new Exception(
					"call weixin " + service + " failed.  [code=" + rtn.get("errcode") + ", error=" + rtn.get("errmsg")
							+ "]. " + (CommonFun.isNe(rtn.get("cause")) ? "" : "cause by " + rtn.get("cause")));
		}

		return new Record(rtn);
	}

	public static Record post(POST_INTERFACE service, Record message) throws Exception {
		if (WeiXinCore.resourceauth == null) {
			throw new Exception("Weixin Client not ready.");
		}

		StringBuffer url = new StringBuffer();
		url.append(WeiXinCore.api_server);
		url.append(service.getUri());
		url.append("?access_token=" + WeiXinCore.resourceauth.getAccess_token());

		String rtnstr = null;
		try {
			rtnstr = HttpClient.post(url.toString(), message);
		} catch (Throwable e) {
			throw new Exception(e);
		}

		@SuppressWarnings("unchecked")
		Map<String, Object> rtn = CommonFun.json2Object(rtnstr, Map.class);

		if (rtn == null) {
			throw new Exception("call weixin " + service + " failed. " + rtnstr);
		}
		if (rtn.containsKey("errcode") && (int) rtn.get("errcode") > 0) {
			throw new Exception(
					"call weixin " + service + " failed.  [code=" + rtn.get("errcode") + ", error=" + rtn.get("errmsg")
							+ "]. " + (CommonFun.isNe(rtn.get("cause")) ? "" : "cause by " + rtn.get("cause")));
		}

		return new Record(rtn);
	}

	private static void loadWeiXinToken() throws Exception {
		// 获取access_token
		StringBuffer url = new StringBuffer();
		url.append(api_server);
		url.append(AUTH_INTERFACE.access_token.getUri());

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("appid", AppID);
		params.put("secret", AppSecret);
		params.put("grant_type", "client_credential");

		String rtnstr = null;
		try {
			rtnstr = HttpClient.get(url.toString(), params);
		} catch (Throwable e) {
			throw new Exception(e);
		}

		@SuppressWarnings("unchecked")
		Map<String, Object> rtn = CommonFun.json2Object(rtnstr, Map.class);

		if (rtn == null) {
			throw new Exception("get weixin access_token failed. " + rtnstr);
		}
		if (rtn.containsKey("errcode") && (int) rtn.get("errcode") > 0) {
			throw new Exception(
					"get weixin access_token failed [code=" + rtn.get("errcode") + ", error=" + rtn.get("errmsg")
							+ "]. " + (CommonFun.isNe(rtn.get("cause")) ? "" : "cause by " + rtn.get("cause")));
		}

		if (resourceauth == null)
			resourceauth = new Authorization(rtn);
		else
			resourceauth.refresh(rtn);

		// 获取jsapi_ticket
		url = new StringBuffer();
		url.append(api_server);
		url.append(AUTH_INTERFACE.get_ticket.getUri());

		params.clear();
		params.put("access_token", resourceauth.getAccess_token());
		params.put("type", "jsapi");

		rtnstr = null;
		try {
			rtnstr = HttpClient.get(url.toString(), params);
		} catch (Throwable e) {
			throw new Exception(e);
		}

		rtn = CommonFun.json2Object(rtnstr, Map.class);

		if (rtn == null) {
			throw new Exception("get weixin jsapi ticket failed. " + rtnstr);
		}
		if (rtn.containsKey("errcode") && (int) rtn.get("errcode") > 0) {
			throw new Exception(
					"get weixin jsapi ticket failed [code=" + rtn.get("errcode") + ", error=" + rtn.get("errmsg")
							+ "]. " + (CommonFun.isNe(rtn.get("cause")) ? "" : "cause by " + rtn.get("cause")));
		}
		
		resourceauth.setJsapi_ticket((String) rtn.get("ticket"));

		log.info("get weixin access_token success. " + resourceauth);
	}

	private static class refreshToken extends Thread {

		private boolean running = true;

		protected void destory() {
			running = false;
			interrupt();
			try {
				join();
			} catch (InterruptedException e) {
				log.error(e.getMessage(), e);
			}
		}

		@Override
		public void run() {
			while (running) {
				try {
					try {
						sleep(60 * 1000);
					} catch (InterruptedException e) {
					}

					if (!running)
						break;

					if (System.currentTimeMillis() - resourceauth.getRefresh_time() > resourceauth.getExpires_in()
							* 1000 * 4 / 5) {
						loadWeiXinToken();
					}

				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
			}
		}
	}

	public static void login(String state, String rd, HttpServletResponse resp) throws Exception {
		if (resourceauth == null) {
			throw new Exception("Weixin Client not ready.");
		}
		loadProperties();
		StringBuffer url = new StringBuffer();
		url.append(auth_server + "?");
		url.append("appid=" + AppID);
		url.append("&redirect_uri=" + URLEncoder.encode(rd, "UTF-8"));
		url.append("&response_type=code");
		url.append("&scope=snsapi_userinfo");
		url.append("&state=" + state);
		url.append("#wechat_redirect");

		resp.sendRedirect(url.toString());
	}

	@SuppressWarnings("unchecked")
	public static Record addUserAccessToken(String code) throws Exception {
		if (resourceauth == null) {
			throw new Exception("Weixin Client not ready.");
		}

		Record params = new Record();
		params.put("appid", AppID);
		params.put("secret", AppSecret);
		params.put("code", code);
		params.put("grant_type", "authorization_code");

		StringBuffer url = new StringBuffer();
		url.append(api_server);
		url.append(GET_INTERFACE.oauth2_access_token.uri);

		String rtnstr = null;
		try {
			rtnstr = HttpClient.get(url.toString(), params);
		} catch (Throwable e) {
			throw new Exception(e);
		}

		Map<String, Object> rtn = CommonFun.json2Object(rtnstr, Map.class);

		if (rtn == null) {
			throw new Exception("call weixin " + GET_INTERFACE.oauth2_access_token + " failed. " + rtnstr);
		}
		if (rtn.containsKey("errcode") && (int) rtn.get("errcode") > 0) {
			throw new Exception("call weixin " + GET_INTERFACE.oauth2_access_token + " failed.  [code="
					+ rtn.get("errcode") + ", error=" + rtn.get("errmsg") + "]. "
					+ (CommonFun.isNe(rtn.get("cause")) ? "" : "cause by " + rtn.get("cause")));
		}

		UserAuthorization userauth = new UserAuthorization(rtn);

		params.clear();
		params.put("access_token", userauth.getAccess_token());
		params.put("openid", userauth.getOpenid());
		params.put("lang", "zh_CN");

		url = new StringBuffer();
		url.append(api_server);
		url.append(GET_INTERFACE.page_userinfo.uri);

		rtnstr = null;
		try {
			rtnstr = HttpClient.get(url.toString(), params);
		} catch (Throwable e) {
			throw new Exception(e);
		}

		rtn = CommonFun.json2Object(rtnstr, Map.class);

		if (rtn == null) {
			throw new Exception("call weixin " + GET_INTERFACE.page_userinfo + " failed. " + rtnstr);
		}
		if (rtn.containsKey("errcode") && (int) rtn.get("errcode") > 0) {
			throw new Exception("call weixin " + GET_INTERFACE.page_userinfo + " failed.  [code=" + rtn.get("errcode")
					+ ", error=" + rtn.get("errmsg") + "]. "
					+ (CommonFun.isNe(rtn.get("cause")) ? "" : "cause by " + rtn.get("cause")));
		}
		
		userauth.setUserinfo(new Record(rtn));
		
		userauthmap.put(CommonFun.getITEMID(), userauth);

		return userauth.getUserinfo();
	}

	private static class refreshUserToken extends Thread {

		private boolean running = true;

		protected void destory() {
			running = false;
			interrupt();
			try {
				join();
			} catch (InterruptedException e) {
				log.error(e.getMessage(), e);
			}
		}

		@Override
		public void run() {
			while (running) {
				try {
					try {
						sleep(60 * 1000);
					} catch (InterruptedException e) {
					}

					if (!running)
						break;

					Iterator<String> keys = userauthmap.keySet().iterator();

					UserAuthorization userauth = null;
					List<String> errsessions = new ArrayList<String>();
					while (keys.hasNext()) {
						String sessionid = keys.next();
						userauth = userauthmap.get(sessionid);

						if (System.currentTimeMillis() - userauth.getRefresh_time() > userauth.getExpires_in() * 1000 * 4 / 5) {
							try {
								// refresh_token
								Record params = new Record();
								params.put("appid", AppID);
								params.put("grant_type", "refresh_token");
								params.put("refresh_token", userauth.getRefresh_token());

								StringBuffer url = new StringBuffer();
								url.append(api_server);
								url.append(GET_INTERFACE.oauth2_refresh_token.uri);

								String rtnstr = null;
								try {
									rtnstr = HttpClient.get(url.toString(), params);
								} catch (Throwable e) {
									throw new Exception(e);
								}

								Map<String, Object> rtn = CommonFun.json2Object(rtnstr, Map.class);

								if (rtn == null) {
									throw new Exception("call weixin " + GET_INTERFACE.oauth2_access_token + " failed. " + rtnstr);
								}
								if (rtn.containsKey("errcode") && (int) rtn.get("errcode") > 0) {
									throw new Exception("call weixin " + GET_INTERFACE.oauth2_access_token + " failed.  [code="
											+ rtn.get("errcode") + ", error=" + rtn.get("errmsg") + "]. "
											+ (CommonFun.isNe(rtn.get("cause")) ? "" : "cause by " + rtn.get("cause")));
								}

								userauth.refresh(rtn);


								params.clear();
								params.put("access_token", userauth.getAccess_token());
								params.put("openid", userauth.getOpenid());
								params.put("lang", "zh_CN");

								url = new StringBuffer();
								url.append(api_server);
								url.append(GET_INTERFACE.page_userinfo.uri);

								rtnstr = null;
								try {
									rtnstr = HttpClient.get(url.toString(), params);
								} catch (Throwable e) {
									throw new Exception(e);
								}

								rtn = CommonFun.json2Object(rtnstr, Map.class);

								if (rtn == null) {
									throw new Exception("call weixin " + GET_INTERFACE.page_userinfo + " failed. " + rtnstr);
								}
								if (rtn.containsKey("errcode") && (int) rtn.get("errcode") > 0) {
									throw new Exception("call weixin " + GET_INTERFACE.page_userinfo + " failed.  [code=" + rtn.get("errcode")
											+ ", error=" + rtn.get("errmsg") + "]. "
											+ (CommonFun.isNe(rtn.get("cause")) ? "" : "cause by " + rtn.get("cause")));
								}
								
								userauth.setUserinfo(new Record(rtn));
								
								log.debug("session[" + sessionid + "] refresh weixin user access_token success. " + userauth);

							} catch (Exception ex) {
								errsessions.add(sessionid);
								ex.printStackTrace();
							}
						}
					}

					for (String sessionid : errsessions) {
						userauthmap.remove(sessionid);
					}

				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
			}
		}

	}

	public static void destory() {
		rut.destory();
		rt.destory();
	}
}
