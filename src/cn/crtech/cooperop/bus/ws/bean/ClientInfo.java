package cn.crtech.cooperop.bus.ws.bean;

import java.io.Serializable;
import java.util.Map;

public class ClientInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3814848286808603343L;

	private String token;
	private String appid;

	private Map<String, Object> request;
	private Map<String, Object> userinfo;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public Map<String, Object> getRequest() {
		return request;
	}

	public void setRequest(Map<String, Object> request) {
		this.request = request;
	}

	public Map<String, Object> getUserinfo() {
		return userinfo;
	}

	public void setUserinfo(Map<String, Object> userinfo) {
		this.userinfo = userinfo;
	}

}
