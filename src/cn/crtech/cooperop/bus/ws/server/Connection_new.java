package cn.crtech.cooperop.bus.ws.server;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.websocket.CloseReason;
import javax.websocket.Session;

import cn.crtech.cooperop.application.action.AuthAction;
import cn.crtech.cooperop.application.authenticate.DisLoggedIn;
import cn.crtech.cooperop.application.authenticate.DisValidPermission;
import cn.crtech.cooperop.application.bean.Account;
import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.util.GlobalVar;
import cn.crtech.cooperop.bus.util.LocalThreadMap;
import cn.crtech.cooperop.bus.util.MD5;
import cn.crtech.cooperop.bus.ws.bean.ClientInfo;
import cn.crtech.cooperop.bus.ws.bean.Message;

public class Connection_new {
	public final static String CLIENT_WEBBROWSER = "Browser";
	public final static String CLIENT_MOBILE = "Phone";
	public final static String CLIENT_PAD = "Pad";
	public final static String CLIENT_TV = "TV";
	public final static String CLIENT_WS = "WS";

	private String appId;
	private String token;
	private ClientInfo clientInfo;
	private Session session;

	public Connection_new() {
	}

	private final static String MD5_SPLIT = "_cooperop_ws_";
	private static checker checker = null;

	private static Map<String, Account> loginusers = new HashMap<String, Account>();
	public static Map<String, Account> disconnectusers = new HashMap<String, Account>();

	@SuppressWarnings("unchecked")
	public static String newConnection(Account userinfo, String clientType) {
		checker.init();
		String token = MD5.md5(userinfo.getId() + MD5_SPLIT + clientType + MD5_SPLIT + CommonFun.getSSID());
		Map<String, String> tokens = null;
		if (loginusers.containsKey(userinfo.getId())) {
			tokens = (Map<String, String>) loginusers.get(userinfo.getId()).getAttendantMap().get("tokens");
		} else {
			tokens = new HashMap<String, String>();
		}
		if (tokens.containsKey(clientType)) {
			try {
				cn.crtech.cooperop.bus.ws.server.Engine.kickOutClient(tokens.get(clientType));
			} catch (Exception e) {
			}
			tokens.remove(clientType);
		}
		tokens.put(clientType, token);
		userinfo.getAttendantMap().put("tokens", tokens);
		loginusers.put(userinfo.getId(), userinfo);
		return token;
	}

	public Connection_new(String token) {
		this.token = token;
	}

	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("clazz", this.getClass().getName());
		map.put("clientInfo", this.clientInfo);
		return map;
	}

	public void fill(ClientInfo clientInfo) {
		this.appId = clientInfo.getAppid();
		this.token = clientInfo.getToken();
		this.clientInfo = clientInfo;
	}

	public void onConnect() throws Exception {
		if (disconnectusers.containsKey(getToken())) {
			Account user = disconnectusers.remove(getToken());
			if (loginusers.containsKey(user.getId())) {
				@SuppressWarnings("unchecked")
				Map<String, String> ts = (Map<String, String>) loginusers.get(user.getId()).getAttendantMap().get("tokens");
				if (!ts.containsKey((String) user.getAttendantMap().get("clienttype"))) {
					ts.put((String) user.getAttendantMap().get("clienttype"), getToken());
				}
			} else {
				@SuppressWarnings("unchecked")
				Map<String, String> ts = (Map<String, String>) user.getAttendantMap().get("tokens");
				if (!ts.containsKey((String) user.getAttendantMap().get("clienttype"))) {
					ts.put((String) user.getAttendantMap().get("clienttype"), getToken());
				}
				user.getAttendantMap().remove("offline_time");
				user.getAttendantMap().remove("clienttype");
				loginusers.put(user.getId(), user);
			}
		}

		Iterator<String> userids = loginusers.keySet().iterator();
		boolean b = false;
		while (userids.hasNext()) {
			Account user = loginusers.get(userids.next());
			if (user.getAttendantMap().containsKey("tokens")) {
				@SuppressWarnings("unchecked")
				Map<String, String> tokens = (Map<String, String>) user.getAttendantMap().get("tokens");
				Iterator<String> keys = tokens.keySet().iterator();
				while (keys.hasNext()) {
					if (tokens.get(keys.next()).equals(getToken())) {
						getClientInfo().setUserinfo((Map<String, Object>) user);
						b = true;
						break;
					}
				}
			}
			if (b) {
				break;
			}
		}
		if (!b) {
			throw new Exception("notlogin");
		}
	}

	public Object onMessage(Message message) throws Exception {
		if (message != null) {
			@SuppressWarnings("unchecked")
			Map<String, Object> params = (Map<String, Object>) message.getParams();
			if (params != null) {
				if (params.containsKey("action")) {
					return callAction((String) params.get("action"), message.getParams());
				}
			}
		}
		return null;
	}

	public void onError(Throwable error) {
		log.error(error);
	}

	public void onDisconnect(CloseReason reason) {
		if (getClientInfo().getUserinfo() == null)
			return;
		Account user = (Account) getClientInfo().getUserinfo();
		String token = null;
		String _browser = null;
		@SuppressWarnings("unchecked")
		Map<String, String> tokens = (Map<String, String>) user.getAttendantMap().get("tokens");
		Iterator<String> keys = tokens.keySet().iterator();
		while (keys.hasNext()) {
			String browser = keys.next();
			if (tokens.get(browser).equals(getToken())) {
				token = tokens.remove(browser);
				_browser = browser;
				break;
			}
		}
		if (token != null) {
			user.getAttendantMap().put("offline_time", System.currentTimeMillis());
			user.getAttendantMap().put("browser", _browser);
			disconnectusers.put(token, user);
		}
		if (tokens.size() == 0) {
			loginusers.remove(user.getId());
		}
	}

	@SuppressWarnings("unchecked")
	public List<String> list2Tokens(Message message) {
		List<String> rtn = new ArrayList<String>();
		if (message != null) {
			Map<String, Object> params = (Map<String, Object>) message.getParams();
			if (params != null) {
				if (params.containsKey("action")) {
					List<String> userids = null;
					try {
						userids = (List<String>) callAction((String) params.get("action"), message.getParams(), params.get("send_to"));
					} catch (Exception e) {
					}
					if (userids != null) {
						for (String userid : userids) {
							Account user = loginusers.get(userid);
							if (user != null && user.getAttendantMap().containsKey("tokens")) {
								Map<String, String> tokens = (Map<String, String>) user.getAttendantMap().get("tokens");
								Iterator<String> keys = tokens.keySet().iterator();
								while (keys.hasNext()) {
									rtn.add(tokens.get(keys.next()));
								}
							}
						}
					}
				} else if (params.containsKey("send_to")) {
					Account user = loginusers.get((String) params.get("send_to"));
					if (user != null && user.getAttendantMap().containsKey("tokens")) {
						Map<String, String> tokens = (Map<String, String>) user.getAttendantMap().get("tokens");
						Iterator<String> keys = tokens.keySet().iterator();
						while (keys.hasNext()) {
							rtn.add(tokens.get(keys.next()));
						}
					}
				}
			}
		}
		return rtn;
	}

	private Map<String, Object> getAction(String pageid, Object... params) {
		try {
			String method = pageid.substring(pageid.lastIndexOf('.') + 1);
			pageid = pageid.substring(0, pageid.lastIndexOf('.'));
			String moduleName = pageid.indexOf('.') < 0 ? pageid : pageid.substring(0, pageid.indexOf('.'));
			pageid = pageid.indexOf('.') < 0 ? pageid : pageid.substring(pageid.indexOf('.') + 1);
			String className = pageid.substring(pageid.lastIndexOf('.') + 1);
			className = CommonFun.capitalize(className);
			String packages = "";
			if (pageid.lastIndexOf('.') > 0) {
				packages = pageid.substring(0, pageid.lastIndexOf('.'));
			}

			String rule = GlobalVar.getSystemProperty("action.access.rule");

			String clazz = rule.replace("@[MODULE]", moduleName);
			if ("".equals(packages)) {
				clazz = clazz.replace("@[PACKAGE].", "");
			} else {
				clazz = clazz.replace("@[PACKAGE]", packages);
			}
			clazz = clazz.replace("@[CLASS]", className);

			Class[] tc = new Class[params.length];
			for (int i = 0; i < tc.length; i++) {
				tc[i] = params[0] == null ? Object.class : params[0].getClass();
			}

			Class<?> c = null;
			Method m = null;
			try {
				c = Class.forName(clazz);
				m = c.getDeclaredMethod(method, tc);
			} catch (Exception ex) {
				log.warning(clazz + "." + method + " not found, use view absolute.");
			}
			if (m != null) {
				Map<String, Object> rtn = new HashMap<String, Object>();
				rtn.put("class", c);
				rtn.put("method", m);
				return rtn;
			} else {
				return null;
			}
		} catch (Exception ex) {
			log.error(ex);
			return null;
		}
	}

	private int checkRight(String pageid, Account user) {
		Map<String, Object> action = getAction(pageid);

		if (action == null) {
			return 1;
		}

		Class<?> c = (Class<?>) action.get("class");
		Method m = (Method) action.get("method");

		// 无需校验是否登录
		DisLoggedIn dli = c.getAnnotation(DisLoggedIn.class);
		if (dli != null) {
			return 1;
		}
		dli = m.getAnnotation(DisLoggedIn.class);
		if (dli != null) {
			return 1;
		}
		if (user == null) {
			return -1;
		}
		// 无需校验是否授权
		DisValidPermission dvp = c.getAnnotation(DisValidPermission.class);
		if (dvp != null && user != null) {
			return 1;
		}
		dvp = m.getAnnotation(DisValidPermission.class);
		if (dvp != null && user != null) {
			return 1;
		}

		return user.checkRight(pageid);
	}

	private Object callAction(String pageid, Object... params) throws Exception {
		boolean removeflag = false;
		int right = checkRight(pageid, (Account)getClientInfo().getUserinfo());
		if (right == -1) {
			throw new Exception("无权调用该功能");
		}
		if (right == 0) {
			throw new Exception("无权调用该功能");
		}

		Map<String, Object> action = getAction(pageid);
		if (action == null) {
			throw new Exception("frame engine call action error.", new NoSuchMethodException(pageid));
		}

		if (LocalThreadMap.get("pageid") == null) {
			removeflag = true;
			LocalThreadMap.put("pageid", pageid);
		}
		String error = null;
		long actioncost = 0;
		Object tmp = null;
		try {
			long s = System.currentTimeMillis();
			Class<?> c = (Class<?>) action.get("class");
			Method m = (Method) action.get("method");
			long threadid = Thread.currentThread().getId();
			String mapStr = params.toString();
			if (mapStr.length() > 200) {
				mapStr = mapStr.substring(0, 200) + "...";
			}
			log.debug("call action[Thread-" + threadid + "][start]: " + c.getName() + "." + m.getName() + "(" + mapStr + ")");

			tmp = m.invoke(c.newInstance(), params);

			actioncost = System.currentTimeMillis() - s;

			log.debug("call action[Thread-" + threadid + "][end]: " + c.getName() + "." + m.getName() + "(" + mapStr + ")");
		} catch (Exception e) {
			log.error("frame engine call action error.", e);
			error = e.getMessage() == null ? "null" : e.getMessage();
			if (e.getCause() != null) {
				error = e.getCause().getMessage() == null ? "null" : e.getCause().getMessage();
			}
		}

		if (error != null) {
			if (removeflag) {
				LocalThreadMap.clear();
			}
			throw new Exception(error);
		}

		if (removeflag) {
			LocalThreadMap.clear();
		}

		if ("true".equalsIgnoreCase(GlobalVar.getSystemProperty("debug"))) {
			long action_waring = Long.parseLong(GlobalVar.getSystemProperty("debug.action.waring"));
			long action_alarm = Long.parseLong(GlobalVar.getSystemProperty("debug.action.alarm"));
			if (actioncost >= action_alarm) {
				log.error("action[" + pageid + "] cost: " + actioncost + "ms");
			} else if (actioncost >= action_waring) {
				log.warning("action[" + pageid + "] cost: " + actioncost + "ms");
			}
		}
		return tmp;
	}

	public static void sendMessage(Map<String, Object> params, String... tokens) throws Exception {
		if (tokens != null) {
			for (String t : tokens) {
				Engine.sendMessage(t, params);
			}
		}
	}

	public void disConnect(String reason) throws Exception {
		Engine.disconnect(token, reason);
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public ClientInfo getClientInfo() {
		return clientInfo;
	}

	public void setClientInfo(ClientInfo clientInfo) {
		this.clientInfo = clientInfo;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	private static class checker extends Thread {
		protected boolean isrunning = false;
		protected boolean stoped = false;

		public static void init() {
			if (checker == null) {
				checker = new checker();
				checker.setName("用户掉线检测线程");
				checker.start();
				log.release("用户掉线检测线程已启动。处理线程数=1");
			}
		}

		@Override
		public void run() {
			isrunning = true;
			stoped = false;
			while (isrunning) {
				long s = Long.parseLong(GlobalVar.getSystemProperty("offline_check_time", "10"));
				try {
					sleep(s * 100);
				} catch (Exception e) {
				}
				if (stoped) {
					log.info(getName() + "：侦测到关闭标识，线程退出");
					break;
				}
				try {
					Iterator<String> keys = disconnectusers.keySet().iterator();
					while (keys.hasNext()) {
						String token = keys.next();
						Account user = disconnectusers.get(token);
						Map<String, String> ts = (Map<String, String>) user.getAttendantMap().get("tokens");
						long offline_time = (long) user.getAttendantMap().get("offline_time");
						if (System.currentTimeMillis() - offline_time >= s * 1000) {
							if (ts.size() == 0) {
								try {
									log.debug(getName() + ": 检测到用户掉线" + user);
									if (user != null) {
										new AuthAction().logout(null);
									}
								} catch (Exception e) {
									log.error(getName() + ": 用户掉线处理异常，" + e.getMessage(), e);
								}
							}
							disconnectusers.remove(token);
						}
					}
				} catch (Exception e) {
				} finally {
				}
			}
		}
	}
}
