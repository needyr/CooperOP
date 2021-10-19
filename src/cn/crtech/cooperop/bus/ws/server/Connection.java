package cn.crtech.cooperop.bus.ws.server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.websocket.CloseReason;
import javax.websocket.Session;

import cn.crtech.cooperop.bus.ws.bean.ClientInfo;

public abstract class Connection {
	public final static String CLIENT_WEBBROWSER = "Browser";
	public final static String CLIENT_MOBILE = "Phone";
	public final static String CLIENT_PAD = "Pad";
	public final static String CLIENT_TV = "TV";

	private String appId;
	private String token;
	private ClientInfo clientInfo;
	private Session session;

	public Connection() {
	}

	public Connection(String token) {
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

	public abstract void onConnect() throws Exception;

	public abstract Object onMessage(Object message) throws Exception;

	public abstract void onError(Throwable error);

	public abstract void onDisconnect(CloseReason reason);

	public abstract List<String> list2Tokens(Object message);

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

}
