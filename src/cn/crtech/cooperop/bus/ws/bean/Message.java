package cn.crtech.cooperop.bus.ws.bean;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.bus.util.CommonFun;

public class Message implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5906404344961114521L;

	public static final String TYPE_CONNECTBACK = "connprop";
	public static final String TYPE_HEARTBEAT = "heartbeat";
	public static final String TYPE_TEXT = "message";
	public static final String TYPE_BLOB = "blob";
	public static final String TYPE_ERROR = "error";

	public static final String STATE_BEGIN = "begin";
	public static final String STATE_END = "end";

	private String id;
	private String type;
	private String state;
	private boolean multi;
	private ClientInfo from;
	private ClientInfo to;
	private Object params;

	// BLOB
	private String msgid;
	private List<String> path;
	private BlobItem blob;
	
	public Message() {

	}

	@SuppressWarnings("unchecked")
	public Message(String json) {
		Map<String, Object> tmp = CommonFun.json2Object(json, Map.class);
		this.id = (String) tmp.get("id");
		this.type = (String) tmp.get("type");
		this.state = (String) tmp.get("state");
		this.multi = tmp.containsKey("multi") ? (boolean) tmp.get("multi") : false;
		this.params = (Map<String, Object>) tmp.get("params");
		this.msgid = (String) tmp.get("msgid");
		this.path = (List<String>) tmp.get("path");
		if (TYPE_BLOB.equals(this.type)) {
			Map<String, Object> b = (Map<String, Object>) tmp.get("blob");
			String fileName = (String) b.get("fileName");
			String contentType = (String) b.get("contentType");
			long size = b.containsKey("size") ? Long.parseLong(String.valueOf(b.get("size"))) : 0L;
			String md5 = (String) b.get("md5");
			File file = (File) b.get("file");
			this.blob = new BlobItem(fileName, contentType, size, md5, file);
		}
	}
	
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("type", type);
		map.put("state", state);
		map.put("multi", multi);
		map.put("params", params);
		map.put("msgid", msgid);
		map.put("path", path);
		if (TYPE_BLOB.equals(this.type)) {
			map.put("blob", blob.serialize());
		}
		return map;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public boolean isMulti() {
		return multi;
	}

	public void setMulti(boolean multi) {
		this.multi = multi;
	}

	public ClientInfo getFrom() {
		return from;
	}

	public void setFrom(ClientInfo from) {
		this.from = from;
	}

	public ClientInfo getTo() {
		return to;
	}

	public void setTo(ClientInfo to) {
		this.to = to;
	}

	public Object getParams() {
		return params;
	}

	public void setParams(Object params) {
		this.params = params;
	}

	public String getMsgid() {
		return msgid;
	}

	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}

	public List<String> getPath() {
		return path;
	}

	public void setPath(List<String> path) {
		this.path = path;
	}

	public BlobItem getBlob() {
		return blob;
	}

	public void setBlob(BlobItem blob) {
		this.blob = blob;
	}
}
