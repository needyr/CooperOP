package cn.crtech.precheck.ipc.ws.client;

import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.bus.util.CommonFun;

public class ClientMessage {
	public static enum STATES {
		HOLD(0, "准备就绪"), QUENE(1, "等待上传"), TRANS(2, "正在上传"), SUCCESS(9, "上传成功"), FAILED(-9, "上传失败");
		private int code;
		private String text;

		private STATES(int code, String text) {
			this.code = code;
			this.text = text;
		}

		public int getCode() {
			return code;
		}

		public String getText() {
			return text;
		}
	}

	private String id;
	private String userid;
	private String password;
	private String appid;
	private STATES state;
	private String path;
	private String errmsg;
	private Map<String, Object> data;

	public ClientMessage() {

	}

	@SuppressWarnings("unchecked")
	public ClientMessage(Map<String, Object> map) {
		this.id = (String) map.get("id");
		this.userid = (String) map.get("userid");
		this.password = (String) map.get("password");
		this.appid = (String) map.get("appid");
		this.data = (Map<String, Object>) map.get("data");
	}

	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("userid", userid);
		map.put("password", password);
		map.put("appid", appid);
		map.put("data", data);
		return map;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public STATES getState() {
		return state;
	}

	public void setState(STATES state) {
		this.state = state;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("userid", userid);
		map.put("appid", appid);
		map.put("state", state);
		map.put("path", path);
		map.put("errmsg", errmsg);
		return CommonFun.object2Json(map);
	}
}
