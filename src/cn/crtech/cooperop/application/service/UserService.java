package cn.crtech.cooperop.application.service;

import cn.crtech.cooperop.application.dao.UserDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.application.authenticate.DisLoggedIn;
import cn.crtech.cooperop.application.bean.Account;
import cn.crtech.cooperop.bus.cache.SystemConfig;
import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CRPasswordEncrypt;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.util.GlobalVar;

public class UserService extends BaseService {
	@Override
	public void connect() throws Exception {
		connect("base");
	}
	
	@DisLoggedIn
	public Account login(Map<String, Object> params) throws Exception {
		Account account = null;
		try {
			connect();
			Record p = new Record(params);
			UserDao ud = new UserDao();
			Record user = ud.loginSupper(p.getString("userid"), p.getString("password"));
			if (user != null) {
				user.put("supper_user", "1");
			} else {
				user = ud.login(p.getString("user_type"), p.getString("userid"), p.getString("password"));
				if (user != null) {
					user.put("supper_user", "0");
				}
			}
			if (user != null) {
				account = new Account();
				account.setSupperUser("1".equals(user.get("supper_user")));
				account.setId(user.getString("id"));
				account.setBusinessId(user.getString("bid"));
				account.setType(user.getString("type"));
				account.setTypeName(user.getString("type_name"));
				account.setNo(user.getString("no"));
				account.setName(user.getString("name"));
				account.setGender(user.getString("gender"));
				account.setBaseDepCode(user.getString("department"));
				account.setBaseDepName(user.getString("department_name"));
				account.setEmail(user.getString("email"));
				account.setMobile(user.getString("mobile"));
				account.setTelephone(user.getString("telephone"));
				account.setJigid(user.getString("jigid"));
				account.setJigname(user.getString("jigname"));
				account.setBmid(user.getString("bmid"));
				account.setAvatar(user.getString("avatar"));
				account.setRoleNames(user.getString("role_names"));
				account.setPosition(user.getString("position"));
				account.setDeviceId(user.getString("deviceid"));
				account.setWelcomePage(user.getString("welcome_url"));;
				account.setPostName(user.getString("position_name"));;
				account.setOpenid(user.getString("openid"));
				account.setUnionid(user.getString("unionid"));
				account.setCompanyid(user.getString("company_id"));
				if (account.isSupperUser()) {
//					for (String plugin : GlobalVar.getLicense().getPlugins()) {
//						account.getPluginList().add(plugin);
//					}
				} else {
					Result plugins = ud.queryUserPlugIn(account.getId());
					for (Record plugin : plugins.getResultset()) {
						account.getPluginList().add(plugin.getString("plugin"));
					}
					Result pages = ud.queryUserPage(account.getId());
					for (Record page : pages.getResultset()) {
						account.getPageList().add(page.getString("code"));
					}
				}
			}
			return account;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	
	
	@DisLoggedIn
	public Record login_v12(Map<String, Object> params) throws Exception {
		Record rtn = new Record();
		Account account = null;
		try {
			connect();
			Record p = new Record(params);
			UserDao ud = new UserDao();
			Record user = ud.loginSupper(p.getString("userid"), p.getString("password"));
			if (user != null) {
				user.put("supper_user", "1");
			} else {
				user = ud.login(p.getString("user_type"), p.getString("userid"), p.getString("password"));
				if (user != null) {
					user.put("supper_user", "0");
				}
			}
			if (user != null) {
				account = new Account();
				account.setSupperUser("1".equals(user.get("supper_user")));
				account.setId(user.getString("id"));
				account.setBusinessId(user.getString("bid"));
				account.setType(user.getString("type"));
				account.setTypeName(user.getString("type_name"));
				account.setNo(user.getString("no"));
				account.setName(user.getString("name"));
				account.setGender(user.getString("gender"));
				account.setBaseDepCode(user.getString("department"));
				account.setBaseDepName(user.getString("department_name"));
				account.setEmail(user.getString("email"));
				account.setMobile(user.getString("mobile"));
				account.setTelephone(user.getString("telephone"));
				account.setJigid(user.getString("jigid"));
				account.setJigname(user.getString("jigname"));
				account.setBmid(user.getString("bmid"));
				account.setAvatar(user.getString("avatar"));
				account.setRoleNames(user.getString("role_names"));
				account.setPosition(user.getString("position"));
				account.setDeviceId(user.getString("deviceid"));
				account.setWelcomePage(user.getString("welcome_url"));;
				account.setPostName(user.getString("position_name"));;
				account.setOpenid(user.getString("openid"));
				account.setUnionid(user.getString("unionid"));
				account.setCompanyid(user.getString("company_id"));
				if (account.isSupperUser()) {
//					for (String plugin : GlobalVar.getLicense().getPlugins()) {
//						account.getPluginList().add(plugin);
//					}
				} else {
					Result plugins = ud.queryUserPlugIn(account.getId());
					for (Record plugin : plugins.getResultset()) {
						account.getPluginList().add(plugin.getString("plugin"));
					}
					Result pages = ud.queryUserPage(account.getId());
					for (Record page : pages.getResultset()) {
						account.getPageList().add(page.getString("code"));
					}
				}
				
				Record baseUserInfo = new Record();
				baseUserInfo.put("jigid", account.getJigid());
				baseUserInfo.put("jigname", account.getJigname());
				baseUserInfo.put("bmid", account.getBaseDepCode());
				baseUserInfo.put("bmname", account.getBaseDepName());
				baseUserInfo.put("zhiyid", account.getId());
				baseUserInfo.put("username", user.getString("ex_username"));
				baseUserInfo.put("userpass", user.getString("ex_userpass"));
				rtn.put("BaseUserInfo", baseUserInfo);
				rtn.put("account", account);
				rtn.put("middbconfigs", ud.middbconfig(account, params).getResultset());
			}
			return rtn;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	
	public Account weixinlogin(String openid) throws Exception {
		Account account = null;
		try {
			connect();
			UserDao ud = new UserDao();
			
			Record user = ud.weixinlogin(openid);
			if (user != null) {
				account = new Account();
				account.setSupperUser(false);
				account.setId(user.getString("id"));
				account.setBusinessId(user.getString("bid"));
				account.setType(user.getString("type"));
				account.setTypeName(user.getString("type_name"));
				Result plugins = ud.queryUserPlugIn(account.getId());
				for (Record plugin : plugins.getResultset()) {
					account.getPluginList().add(plugin.getString("plugin"));
				}
				Result pages = ud.queryUserPage(account.getId());
				for (Record page : pages.getResultset()) {
					account.getPageList().add(page.getString("code"));
				}
				account.setNo(user.getString("no"));
				account.setName(user.getString("name"));
				account.setGender(user.getString("gender"));
				account.setBaseDepCode(user.getString("base_dep_code"));
				account.setBaseDepName(user.getString("base_dep_name"));
				account.setEmail(user.getString("email"));
				account.setMobile(user.getString("mobile"));
				account.setTelephone(user.getString("telephone"));
				account.setJigid(user.getString("jigid"));
				account.setJigname(user.getString("jigname"));
				account.setBmid(user.getString("bmid"));
				account.setAvatar(user.getString("avatar"));
				account.setRoleNames(user.getString("role_names"));
				account.setPosition(user.getString("position"));
				account.setWelcomePage(user.getString("welcome_url"));
				account.setOpenid(user.getString("openid"));
				account.setUnionid(user.getString("unionid"));
				account.setCompanyid(user.getString("company_id"));
			}
			return account;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}

	@DisLoggedIn
	public Account weixinlogin(Map<String, Object> params) throws Exception {
		Account account = null;
		try {
			connect();
			UserDao ud = new UserDao();
			
			Record p = new Record(params);
			Record user = ud.weixinlogin(p.getString("user_type"), p.getString("openid"), p.getString("unionid"));
			if (user != null) {
				account = new Account();
				account.setSupperUser(false);
				account.setId(user.getString("id"));
				account.setBusinessId(user.getString("bid"));
				account.setType(user.getString("type"));
				account.setTypeName(user.getString("type_name"));
				Result plugins = ud.queryUserPlugIn(account.getId());
				for (Record plugin : plugins.getResultset()) {
					account.getPluginList().add(plugin.getString("plugin"));
				}
				Result pages = ud.queryUserPage(account.getId());
				for (Record page : pages.getResultset()) {
					account.getPageList().add(page.getString("code"));
				}
				account.setNo(user.getString("no"));
				account.setName(user.getString("name"));
				account.setGender(user.getString("gender"));
				account.setBaseDepCode(user.getString("base_dep_code"));
				account.setBaseDepName(user.getString("base_dep_name"));
				account.setEmail(user.getString("email"));
				account.setMobile(user.getString("mobile"));
				account.setTelephone(user.getString("telephone"));
				account.setJigid(user.getString("jigid"));
				account.setJigname(user.getString("jigname"));
				account.setBmid(user.getString("bmid"));
				account.setAvatar(user.getString("avatar"));
				account.setRoleNames(user.getString("role_names"));
				account.setPosition(user.getString("position"));
				account.setWelcomePage(user.getString("welcome_url"));
				account.setOpenid(user.getString("openid"));
				account.setUnionid(user.getString("unionid"));
				account.setCompanyid(user.getString("company_id"));
			}
			return account;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}

	public Account sso(String loginuser) throws Exception {
		Account account;
		try {
			connect();

			UserDao ud = new UserDao();

			Record user = ud.sso(loginuser);
			if (user == null) return null;
			account = new Account();
			account.setSupperUser(false);
			account.setId(user.getString("id"));
			account.setBusinessId(user.getString("bid"));
			account.setType(user.getString("type"));
			account.setTypeName(user.getString("type_name"));

			Result plugins = ud.queryUserPlugIn(account.getId());
			for (Record plugin : plugins.getResultset()) {
				account.getPluginList().add(plugin.getString("plugin"));
			}
			Result pages = ud.queryUserPage(account.getId());
			for (Record page : pages.getResultset()) {
				account.getPageList().add(page.getString("code"));
			}
			account.setNo(user.getString("no"));
			account.setName(user.getString("name"));
			account.setGender(user.getString("gender"));
			account.setBaseDepCode(user.getString("base_dep_code"));
			account.setBaseDepName(user.getString("base_dep_name"));
			account.setEmail(user.getString("email"));
			account.setMobile(user.getString("mobile"));
			account.setTelephone(user.getString("telephone"));
			account.setJigid(user.getString("jigid"));
			account.setJigname(user.getString("jigname"));
			account.setBmid(user.getString("bmid"));
			account.setAvatar(user.getString("avatar"));
			account.setRoleNames(user.getString("role_names"));
			account.setPosition(user.getString("position"));
			account.setCompanyid(user.getString("company_id"));
			return account;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}

	public Result getMenuTree(String rootPopedom) throws Exception {
		try {
			connect();
			UserDao dao = new UserDao();
			return dao.getMenuTree(rootPopedom);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}

	public Result getMenu(String popedom) throws Exception {
		try {
			connect();
			UserDao dao = new UserDao();
			return dao.getMenu(popedom);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public Result getMenus(Map<String, Object> map) throws Exception {
		try {
			connect();
			UserDao dao = new UserDao();
			return dao.getMenus(map);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public Map<String, Object> getSelfMenus(Map<String, Object> map) throws Exception {
		try {
			connect();
			UserDao dao = new UserDao();
			Result r = dao.getSelfMenus(new HashMap<String, Object>());
			map.put("self_menus", r.getResultset());
			map.put("query_product",1);
			map.put("self_products", dao.getSelfMenus(map).getResultset());
			map.put("charts", dao.getSelfCharts(map).getResultset());
			return map;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public Account getUser(String loginuser) throws Exception {
		Account account;
		try {
			connect();

			UserDao ud = new UserDao();

			Record user = ud.sso(loginuser);
			if (user == null) return null;
			
			account = new Account();
			account.setSupperUser(false);
			account.setId(user.getString("id"));
			account.setBusinessId(user.getString("bid"));
			account.setType(user.getString("type"));
			account.setTypeName(user.getString("type_name"));
			account.setNo(user.getString("no"));
			account.setName(user.getString("name"));
			account.setGender(user.getString("gender"));
			account.setBaseDepCode(user.getString("base_dep_code"));
			account.setBaseDepName(user.getString("base_dep_name"));
			account.setEmail(user.getString("email"));
			account.setMobile(user.getString("mobile"));
			account.setTelephone(user.getString("telephone"));
			account.setJigid(user.getString("jigid"));
			account.setJigname(user.getString("jigname"));
			account.setBmid(user.getString("bmid"));
			account.setAvatar(user.getString("avatar"));
			account.setRoleNames(user.getString("role_names"));
			account.setPosition(user.getString("position"));
			account.setOpenid(user.getString("openid"));
			account.setUnionid(user.getString("unionid"));
			account.setCompanyid(user.getString("company_id"));
			return account;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	
	public Map<String,Object> getUserInfo( Map<String,Object> map) throws Exception {
		
		try {
			connect();
			Map<String,Object> req = new HashMap<String,Object>();
			UserDao ud = new UserDao();
			req.put("uinfo", ud.getUserInfo(user().getNo()));	
			return req;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public Record get(String userid) throws Exception {
		try {
			connect();
			Map<String,Object> req = new HashMap<String,Object>();
			UserDao ud = new UserDao();
			return ud.get(userid);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public int update(Map<String, Object> params) throws Exception {
		try {
			connect();
			UserDao dd = new UserDao();
			Object id = params.get("id");
			int i = dd.update(params);
			if (id.equals(user().getId())) {
				Record t = dd.get(user().getId());
				Account user = user();
				user.setName(t.getString("name"));
				user.setGender(t.getString("gender"));
				user.setEmail(t.getString("email"));
				user.setMobile(t.getString("mobile"));
				user.setTelephone(t.getString("telephone"));
				user.setAvatar(t.getString("avatar"));
			}
			return i;
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}
	}
	
	public void updateByVID(Map<String, Object> params) throws Exception {
		try {
			connect();
			UserDao dd = new UserDao();
			dd.updateByVID(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	
	public int updatePassword(Map<String, Object> params) throws Exception {
		try {
			connect();
			UserDao ud = new UserDao();
			int a=0;
			if(user().isSupperUser()){
				params.put("no", user().getNo());
				a=ud.updateSupperPassword(params);
			}else{
				params.put("id", user().getBusinessId());
				a = ud.updatePassword(params);
			}
			if(a==0){
				throw new Exception("原密码输入不正确！");
			}
			return 0;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	
	public boolean checkpwd(Map<String, Object> params) throws Exception {
		try {
			connect();	
			String userid = user().getNo();
			String pwd = CRPasswordEncrypt.Encryptstring(SystemConfig.getSystemConfigValue("global", "password", "000000"));
			Record re = new UserDao().getUserInfo(userid);
			if(!CommonFun.isNe(re)){
				if(pwd.equals(re.get("password"))){
					return true;
				}else{
					return false;
				}
			}else{
				return false;
			}
			
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}

	
	public void saveHomeSetting(Map<String, Object> params) throws Exception {
		try {
			connect();
			start();
			UserDao dd = new UserDao();
			List<Map<String, Object>> fks = (List<Map<String, Object>>) params.get("fks");
			if(fks != null){
				dd.deleteHomeSetting(params);
				for(Map<String, Object> fk : fks){
					Map<String, Object> p = new HashMap<String, Object>();
					p.put("fk_id", fk.get("fk_id"));
					p.put("order_no", fk.get("order_no"));
					p.put("system_user_id", user().getId());
					p.put("type", params.get("type"));
					dd.saveHomeSetting(p);
				}
			}
			commit();
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}
	}
	public Map<String, Object> initHomeSetting(Map<String, Object> params) throws Exception {
		try {
			connect();
			UserDao dd = new UserDao();
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("type", "M");
			params.put("cmenus", dd.queryMenuSetting(m).getResultset());
			m.put("type", "C");
			params.put("charts", dd.queryChartSetting(m).getResultset());
			return params;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	
	public Result queryHomeMenuSetting(Map<String, Object> params) throws Exception {
		try {
			connect();
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("type", "M");
			return new UserDao().queryMenuSetting(m);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	
	public Result initProduct(Map<String, Object> params) throws Exception {
		try {
			connect();
			return new UserDao().initProduct(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	
	public Result queryOrganization(Map<String, Object> params) throws Exception {
		try {
			connect();
			return new UserDao().queryOrganization(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public Result searchMenu(Map<String, Object> params) throws Exception {
		try {
			connect();
			return new UserDao().getMenuTree(null, (String)params.get("searchName"));
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
}
