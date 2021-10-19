package cn.crtech.cooperop.application.action;

import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.application.authenticate.DisValidPermission;
import cn.crtech.cooperop.application.bean.Account;
import cn.crtech.cooperop.application.service.UserService;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.util.CommonFun;
@DisValidPermission
public class MineAction extends BaseAction {
	public Map<String, Object> profile(Map<String, Object> map) throws Exception {
		return new UserService().getUserInfo(map);
	}
	
	public Map<String, Object> setting_main(Map<String, Object> map) throws Exception {
		return new UserService().getSelfMenus(map);
	}
	public Map<String, Object> saveHomeSetting(Map<String, Object> map) throws Exception {
		Map<String, Object> m = CommonFun.json2Object((String)map.get("data"),Map.class);
		new UserService().saveHomeSetting(m);
		return map;
	}
	public static Map<String, Object> initHomeSetting() throws Exception {
		return new UserService().initHomeSetting(new HashMap<String, Object>());
	}
	public Map<String, Object> update(Map<String, Object> req) throws Exception{
		UserService us = new UserService();
		Object no = req.get("no");
		us.update(req);
		//if (no.equals(user().getNo())) 
		{
			Record t = us.get(user().getId());
			Account user = user();
			user.setName(t.getString("name"));
			user.setGender(t.getString("gender"));
			user.setEmail(t.getString("email"));
			user.setMobile(t.getString("mobile"));
			user.setTelephone(t.getString("telephone"));
			user.setAvatar(t.getString("avatar"));
			session("userinfo", user);
		}
		req.put("result", "success");
		return req;
	}
	public int updatePassword(Map<String, Object> params) throws Exception {
		return new UserService().updatePassword(params);
	}
	
}
