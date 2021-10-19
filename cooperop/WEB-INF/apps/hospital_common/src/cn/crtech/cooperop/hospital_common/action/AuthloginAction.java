package cn.crtech.cooperop.hospital_common.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import cn.crtech.cooperop.application.authenticate.DisLoggedIn;
import cn.crtech.cooperop.application.bean.Account;
import cn.crtech.cooperop.application.service.UserService;
import cn.crtech.cooperop.bus.cache.SystemConfig;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.session.Session;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.service.Login_ipcService;
import cn.crtech.ylz.ylz;

public class AuthloginAction extends BaseAction{
	
	public static Map<String, Object> CRSID = new ConcurrentHashMap<String, Object>();

	@DisLoggedIn
	public Map<String, Object> ipcsso(Map<String, Object> map) throws Exception {
		String uid = (String) map.get("userid");
		Record res = new Record();
		if (CommonFun.isNe(uid)) {
			throw new Exception("无效连接串。");
		}
		Account user = new UserService().sso(uid);
		if (user == null) {
			throw new Exception("无效连接串。");
		}
		session("userinfo", user);
		res.put("uid", user.getNo());
		res.put(Session.SIDNAME, session().getId());
		List<Map<String, Object>> list = getMenus(map);
		res.put("menus", list);
		CRSID.put(uid, session().getId());
		ylz.p(" login success ... sessionid = " + session().getId());
		return res;
	}

	@DisLoggedIn
	public Map<String, Object> openSSO(Map<String, Object> map) throws Exception {
		String uid = (String) map.get("userid");
		Record info = new Record();
		if (CommonFun.isNe(uid) || CommonFun.isNe(map.get("openkey"))) {
			info.put("msg", "登录失败：参数 userid 或 openkey 缺失！");
			return info;
		}
		String openSSOKey = SystemConfig.getSystemConfigValue("hospital_common", "open.sso.key", "8ad9902aecba32e2");
		if (!openSSOKey.equals(map.get("openkey"))) {
			info.put("msg", "登录失败：openkey 错误！");
			return info;
		}
		Account user = new UserService().sso(uid);
		if (user == null) {
			info.put("msg", "登录失败：用户不存在");
			return info;
		}
		session("userinfo", user);
		CRSID.put(uid, session().getId());
		info.put(Session.SIDNAME, session().getId());
		return info;
	}

	@DisLoggedIn
	public Map<String, Object> login(Map<String, Object> map) throws Exception {
		String uid = (String) map.get("userid");
		String upwd = (String) map.get("password");
		Record res = new Record();
		if (CommonFun.isNe(uid)||CommonFun.isNe(upwd)) {
			throw new Exception("无效连接串。");
		}
		Account user = new UserService().login(map);
		if (user == null) {
			throw new Exception("无效连接串。");
		}
		res.put("uid", user.getNo());
		res.put("_CRSID", session().getId());
		map.put("userid", user.getId());
		List<Map<String, Object>> list = getMenus(map);
		res.put("menus", list);
		return res;
	}
	
	public List<Map<String, Object>> getMenus(Map<String, Object> map) throws Exception {
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		List<Record> menus = new Login_ipcService().queryMenu(map).getResultset();
		for (Record record : menus) {
			if(record.getInt("child_num")== 0){
				Map<String, Object> mapmenu=new HashMap<String, Object>();
				mapmenu.put("menu_url", record.get("code"));
				mapmenu.put("menu_name", record.get("name"));
				list.add(mapmenu);
			}
		}
		return list;
	}
}
