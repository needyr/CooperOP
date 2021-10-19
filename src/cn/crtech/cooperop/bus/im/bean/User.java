package cn.crtech.cooperop.bus.im.bean;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6188806281707600342L;

	public static final int STATE_NORMAL = 1;
	public static final int STATE_FREEZEN = 0;
	public static final int STATE_DELETE = -1;

	private String id;
	private String no;
	private String name;
	private String gender;
	private String department;
	private String mobile;
	private String telephone;
	private String weixin;
	private String weixin_public;
	private String email;
	private String qq;
	private String idcard_type;
	private String idcard_no;
	private int state;
	private Date birthday;
	private FileItem avatar;
	private String position;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getWeixin() {
		return weixin;
	}

	public void setWeixin(String weixin) {
		this.weixin = weixin;
	}

	public String getWeixin_public() {
		return weixin_public;
	}

	public void setWeixin_public(String weixin_public) {
		this.weixin_public = weixin_public;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getIdcard_type() {
		return idcard_type;
	}

	public void setIdcard_type(String idcard_type) {
		this.idcard_type = idcard_type;
	}

	public String getIdcard_no() {
		return idcard_no;
	}

	public void setIdcard_no(String idcard_no) {
		this.idcard_no = idcard_no;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public FileItem getAvatar() {
		return avatar;
	}

	public void setAvatar(FileItem avatar) {
		this.avatar = avatar;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

}
