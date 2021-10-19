package cn.crtech.cooperop.bus.weixin.bean;

import java.io.Serializable;
import java.util.Map;

import cn.crtech.cooperop.bus.util.CommonFun;

public class Authorization implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4496042356250603654L;

	public Authorization() {
	}

	public Authorization(Map<String, Object> rtn) {
		this.access_token = (String)rtn.get("access_token");
		this.expires_in = Long.parseLong(rtn.get("expires_in").toString());
		this.refresh_time = System.currentTimeMillis();
	}

	public void refresh(Map<String, Object> rtn) {
		this.access_token = (String)rtn.get("access_token");
		this.expires_in = Long.parseLong(rtn.get("expires_in").toString());
		this.refresh_time = System.currentTimeMillis();
	}

	private String access_token;
	private String jsapi_ticket;
	private long expires_in;
	private long refresh_time;

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public String getJsapi_ticket() {
		return jsapi_ticket;
	}

	public void setJsapi_ticket(String jsapi_ticket) {
		this.jsapi_ticket = jsapi_ticket;
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
	
	@Override
	public String toString() {
		return CommonFun.object2Json(this);
	}
}
