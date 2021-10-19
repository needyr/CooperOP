package cn.crtech.cooperop.bus.im.bean;

import java.io.Serializable;

public class Orgnization implements Serializable {

	private static final long serialVersionUID = 5283610549046726731L;

	public static final int STATE_NORMAL = 1;
	public static final int STATE_FREEZEN = 0;
	public static final int STATE_DELETE = -1;

	private String id;
	private String code;
	private String name;
	private String sort_name;
	private int state;
	private String weixin_public;
	private String introduce;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSort_name() {
		return sort_name;
	}

	public void setSort_name(String sort_name) {
		this.sort_name = sort_name;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getWeixin_public() {
		return weixin_public;
	}

	public void setWeixin_public(String weixin_public) {
		this.weixin_public = weixin_public;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

}
