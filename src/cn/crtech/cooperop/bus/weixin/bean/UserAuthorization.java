package cn.crtech.cooperop.bus.weixin.bean;

import java.io.Serializable;
import java.util.Map;

import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.util.CommonFun;

public class UserAuthorization implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4132683954471257657L;

	public UserAuthorization() {
	}

	public UserAuthorization(Map<String, Object> rtn) {
		this.access_token = (String)rtn.get("access_token");
		this.refresh_token = (String)rtn.get("refresh_token");
		this.openid = (String)rtn.get("openid");
		this.scope = (String)rtn.get("scope");
		this.expires_in = Long.parseLong(rtn.get("expires_in").toString());
		this.refresh_time = System.currentTimeMillis();
	}

	public void refresh(Map<String, Object> rtn) {
		this.access_token = (String)rtn.get("access_token");
		this.refresh_token = (String)rtn.get("refresh_token");
		this.openid = (String)rtn.get("openid");
		this.scope = (String)rtn.get("scope");
		this.expires_in = Long.parseLong(rtn.get("expires_in").toString());
		this.refresh_time = System.currentTimeMillis();
	}

	private String access_token;
	private String refresh_token;
	private String openid;
	private String scope;
	private Record userinfo;
	private long expires_in;
	private long refresh_time;

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public long getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(long expires_in) {
		this.expires_in = expires_in;
	}

	public long getRefresh_time() {
		return refresh_time;
	}

	public void setRefresh_time(long refresh_time) {
		this.refresh_time = refresh_time;
	}
	
	public String getRefresh_token() {
		return refresh_token;
	}

	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public Record getUserinfo() {
		return userinfo;
	}

	public void setUserinfo(Record userinfo) {
		this.userinfo = userinfo;
	}

	@Override
	public String toString() {
		return CommonFun.object2Json(this);
	}
}
