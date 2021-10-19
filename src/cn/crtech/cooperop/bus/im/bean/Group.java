package cn.crtech.cooperop.bus.im.bean;

import java.io.Serializable;
import java.util.Date;

public class Group implements Serializable {

	private static final long serialVersionUID = 164920131100509439L;

	public static final int STATE_NORMAL = 1;
	public static final int STATE_FREEZEN = 0;
	public static final int STATE_DELETE = -1;

	public static final int OPEN_OPEN = 1;
	public static final int OPEN_PRIVATE = 0;

	public static final int AUDIT_NEED = 1;
	public static final int AUDIT_NONEED = 0;
	
	private String id;
	private String system_user_id;
	private String name;
	private String introduce;
	private int state;
	private int is_public;
	private int need_audit;
	private Date create_time;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSystem_user_id() {
		return system_user_id;
	}

	public void setSystem_user_id(String system_user_id) {
		this.system_user_id = system_user_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getIs_public() {
		return is_public;
	}

	public void setIs_public(int is_public) {
		this.is_public = is_public;
	}

	public int getNeed_audit() {
		return need_audit;
	}

	public void setNeed_audit(int need_audit) {
		this.need_audit = need_audit;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
}
