package cn.crtech.cooperop.application.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Account implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2288940861802145236L;

	public static final String TYPE_SUPER = "0";
	
	private boolean supperUser;
	private String id;
	private String businessId;
	private String no;
	private String name;
	private String passWord;
	private String type;
	private String typeName;
	private String baseDepName;
	private String baseDepCode;  //cooperop 部门id （账套做为顶级部门）
	private int depNum; 
	private String gender;
	private String telephone;
	private String email;
	private String mobile;
	private String jigid;  //erp jig id  账套id
	private String jigname;  //erp jig name  账套名称
	private String bmid;  //erp bm id 单位id
	private String roleNames;
	private String avatar;
	private String position;
	private String welcomePage;
	private String openid;
	private String unionid;
	private String deviceId;
	private String postName;
	private String companyid;
	
	
	public String getCompanyid() {
		return companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public String getPostName() {
		return postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getOpenid() {
		return openid;
	}

	public String getUnionid() {
		return unionid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}
	

	private final Map<String, Object> attendantMap = new HashMap<String, Object>();

	private List<String> pluginList = new ArrayList<String>();

	private final List<String> pageList = new ArrayList<String>();

	private final List<String> roleList = new ArrayList<String>();

	private final Map<String, List<String>> ruleMap = new HashMap<String, List<String>>();
	
	public String getWelcomePage() {
		return welcomePage;
	}
	
	public void setWelcomePage(String welcomePage) {
		this.welcomePage = welcomePage;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public boolean isSupperUser() {
		return supperUser;
	}

	public void setSupperUser(boolean supperUser) {
		this.supperUser = supperUser;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<String> getPageList() {
		return pageList;
	}

	public List<String> getRoleList() {
		return roleList;
	}

	public Map<String, List<String>> getRuleMap() {
		return ruleMap;
	}

	public Map<String, Object> getAttendantMap() {
		return attendantMap;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getRoleNames() {
		return roleNames;
	}

	public void setRoleNames(String roleNames) {
		this.roleNames = roleNames;
	}

	public String getBaseDepName() {
		return baseDepName;
	}

	public void setBaseDepName(String baseDepName) {
		this.baseDepName = baseDepName;
	}

	public String getBaseDepCode() {
		return baseDepCode;
	}

	public void setBaseDepCode(String baseDepCode) {
		this.baseDepCode = baseDepCode;
	}

	public String getJigid() {
		return jigid;
	}

	public void setJigid(String jigid) {
		this.jigid = jigid;
	}

	public String getJigname() {
		return jigname;
	}

	public void setJigname(String jigname) {
		this.jigname = jigname;
	}

	public String getBmid() {
		return bmid;
	}

	public void setBmid(String bmid) {
		this.bmid = bmid;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public int getDepNum() {
		return depNum;
	}

	public void setDepNum(int depNum) {
		this.depNum = depNum;
	}
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
	public void setPluginList(List<String> pluginList) {
		this.pluginList = pluginList;
	}

	public List<String> getPluginList() {
		return pluginList;
	}

	public int checkRight(String pageid) {
//		超用户不能访问其它非base页面
		if (supperUser) {
			return 1;
		}
		for (String page : pageList) {
			String a = page;
			String[] ea;
			if (a.equals(pageid)) {
				return 1;
			}

			if (a.indexOf('!') > -1) {
				ea = a.substring(a.indexOf("!") + 1).split("!");
				a = a.substring(0, a.indexOf('!'));
			} else if (a.endsWith("*")) {
				ea = new String[0];
			} else {
				continue;
			}

			boolean except = false;
			String exceptpageid = "";
			if (a.endsWith("*")) {
				if (pageid.startsWith(a.substring(0, a.length() - 1))) {
					exceptpageid = pageid.substring(a.length() - 1);
				} else {
					continue;
				}
			}
			for (String e : ea) {
				boolean bs = false, es = false;
				if (e.indexOf('*') == 0) {
					bs = true;
					e = e.substring(1);
				}
				if (e.endsWith("*")) {
					es = true;
					e = e.substring(0, e.length() - 1);
				}
				if (bs && es) {
					if (exceptpageid.indexOf(e) > -1) {
						except = true;
						break;
					}
				} else if (bs) {
					if (exceptpageid.endsWith(e)) {
						except = true;
						break;
					}
				} else if (es) {
					if (exceptpageid.startsWith(e)) {
						except = true;
						break;
					}
				}
			}
			if (!except) {
				return 1;
			}
		}
		return 0;
	}

	@Override
	public String toString() {
		return id + "-" + name;
	}
}
