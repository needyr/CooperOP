package cn.crtech.cooperop.application.action;

import java.util.Map;

import cn.crtech.cooperop.application.authenticate.DisLoggedIn;
import cn.crtech.cooperop.application.authenticate.DisValidPermission;
import cn.crtech.cooperop.application.bean.Account;
import cn.crtech.cooperop.application.service.UserService;
import cn.crtech.cooperop.bus.cache.SystemUser;
import cn.crtech.cooperop.bus.cache.service.SystemUserService;
import cn.crtech.cooperop.bus.im.transfer.Engine;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.util.GlobalVar;
@DisValidPermission
public class UserAction extends BaseAction {
	public Map<String, Object> profile(Map<String, Object> map) throws Exception {
		return new UserService().get((String)map.get("userid"));
	}
	
	public Map<String, Object> regFinger(Map<String, Object> map) throws Exception {
		return new SystemUserService().regFinger(map);
	}
	public Map<String, Object> deleteFingers(Map<String, Object> map) throws Exception {
		return new SystemUserService().deleteFingers(map);
	}
	public Map<String, Object> getFinger(Map<String, Object> map) throws Exception {
		return new SystemUserService().getFinger(map);
	}
	public Map<String, Object> getInfoByFinger(Map<String, Object> map) throws Exception {
		return new SystemUserService().getInfoByFinger(map);
	}
	public Map<String, Object> checkFinger(Map<String, Object> map) throws Exception {
		return new SystemUserService().checkFinger(map);
	}
	
	@DisLoggedIn
	public Map<String, Object> loginFinger(Map<String, Object> map) throws Exception {
		Record res = new Record();
		
		Account user = new SystemUserService().loginFinger(map);
		if(user != null){
			session("userinfo", user);
			res.put("userinfo", user);
			String redirect_url = GlobalVar.getSystemProperty("login.url", "index.jsp");
			if (!CommonFun.isNe(user.getWelcomePage())) {
				redirect_url = user.getWelcomePage();
			}
			if("Y".equals(map.get("ism"))){
				redirect_url = GlobalVar.getSystemProperty("login.app.url", "moblieMain.jsp");
			}
			res.put("redirect_url", redirect_url);
		}
		return res;
	}
	@DisValidPermission
	public Map<String, Object> loadUserCache(Map<String, Object> map) throws Exception {
		SystemUser.load();
		Engine.refreshUser();
		return map;
	}
}
