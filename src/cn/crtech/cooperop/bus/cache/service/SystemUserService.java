package cn.crtech.cooperop.bus.cache.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.application.bean.Account;
import cn.crtech.cooperop.application.dao.UserDao;
import cn.crtech.cooperop.bus.cache.MemoryCache;
import cn.crtech.cooperop.bus.cache.SystemUser;
import cn.crtech.cooperop.bus.cache.dao.SystemUserDao;
import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import zk.jni.JavaToBiokey;

public class SystemUserService extends BaseService {
	@Override
	public void connect() throws Exception {
		connect("base");
	}

	public void load() throws Exception {
		HashMap<String, Object> memmap = new HashMap<String, Object>();
		try {
			connect();
			SystemUserDao dao = new SystemUserDao();
			Result result = dao.loadFinger();
			MemoryCache.put(SystemUser.prix, "system_users", dao.load().getResultset());
			for(Record r : result.getResultset()){
				Object obj = memmap.get(r.getString("id"));
				if(!CommonFun.isNe(obj)){
					List<Record> l = (List<Record>)((Record)memmap.get(SystemUser.prix + r.get("id"))).get("fingers");
					Record f = new Record();
					f.put("finger_image", r.get("finger_image"));
					l.add(f);
				}else{
					List<Record> l = new ArrayList<Record>();
					if(!CommonFun.isNe(r.get("finger_image"))){
						Record f = new Record();
						f.put("finger_image", r.get("finger_image"));
						l.add(f);
					}
					r.put("fingers", l);
					memmap.put(r.getString("id"), r);
				}
			}
			MemoryCache.putAll(SystemUser.prix, memmap);
		}
		catch (Exception ex) {
			log.error("load system user failed.", ex);
		}
		finally {
			disconnect();
		}
	}

	public Map<String, Object> regFinger(Map<String, Object> params) throws Exception{
		try{
			connect();
			Record r = new SystemUserDao().regFinger(params);
			//TODO 缓存
			Object o = MemoryCache.get(SystemUser.prix, (String)params.get("system_user_id"));
			if(((Record)MemoryCache.get(SystemUser.prix, (String)params.get("system_user_id"))).get("fingers") == null){
				List<Record> l = new ArrayList<Record>();
				Record f = new Record();
				f.put("finger_image", params.get("finger_image"));
				r.put("finger_image", params.get("finger_image"));
				r.put("sn", params.get("sn"));
				l.add(f);
				((Record)MemoryCache.get(SystemUser.prix, (String)params.get("system_user_id"))).put("fingers", l);
			}else{
				List<Record> l = (List<Record>)((Record)MemoryCache.get(SystemUser.prix, (String)params.get("system_user_id"))).get("fingers");
				Record f = new Record();
				f.put("finger_image", params.get("finger_image"));
				r.put("sn", params.get("sn"));
				r.put("finger_image", params.get("finger_image"));
				l.add(f);
			}
			return r;
		}catch(Exception e){
			throw e;
		}finally{
			disconnect();
		}
	}
	public Map<String, Object> deleteFingers(Map<String, Object> params) throws Exception{
		try{
			if(((Record)MemoryCache.get(SystemUser.prix, (String)params.get("system_user_id"))).get("fingers") != null){
				((Record)MemoryCache.get(SystemUser.prix, (String)params.get("system_user_id"))).remove("fingers");
			}
			connect();
			new SystemUserDao().deleteFingers(params);
			return params;
		}catch(Exception e){
			throw e;
		}finally{
			disconnect();
		}
	}
	public Map<String, Object> getFinger(Map<String, Object> params) throws Exception{
		try{
			if(CommonFun.isNe(((Record)MemoryCache.get(SystemUser.prix, (String)params.get("system_user_id"))).get("fingers"))){
				load();
			}
			List<Record> l = (List<Record>)((Record)MemoryCache.get(SystemUser.prix, (String)params.get("system_user_id"))).get("fingers");
			params.put("fingers", l);
			return params;
		}catch(Exception e){
			throw e;
		}finally{
		}
	}
	public Map<String, Object> checkFinger(Map<String, Object> params) throws Exception{
		try{
			boolean b = false;
			if(((Record)MemoryCache.get(SystemUser.prix, (String)params.get("system_user_id"))).get("fingers") != null){
				List<Record> l = (List<Record>)((Record)MemoryCache.get(SystemUser.prix, (String)params.get("system_user_id"))).get("fingers");
				for(Record f : l){
					if(JavaToBiokey.NativeToProcess((String)f.get("finger_image"), (String)params.get("finger_image"))){
						b = true;
						break;
					}
				}
			}
			params.put("flag", b);
			return params;
		}catch(Exception e){
			throw e;
		}finally{
		}
	}

	public Map<String, Object> getInfoByFinger(Map<String, Object> params) throws Exception{
		List<Record> system_users = (List<Record>) MemoryCache.get(SystemUser.prix,"system_users");
		for(Record u : system_users){
			Record mu = (Record)MemoryCache.get(SystemUser.prix, u.getString("id"));
			if(mu.get("fingers") != null){
				List<Record> fingers = (List<Record>) mu.get("fingers");
				for(Record f : fingers){
					if (JavaToBiokey.NativeToProcess(f.getString("finger_image"), (String)params.get("finger_image"))) {
						params.put("system_user_id", u.get("id"));
						break;
					}
				}
			}
		}
		params.remove("finger_image");

		return params;
	}

	public Account loginFinger(Map<String, Object> params) throws Exception{
		Account account = null;
		Record user = null;
		List<Record> system_users = (List<Record>) MemoryCache.get(SystemUser.prix,"system_users");
		for(Record u : system_users){
			Record mu = (Record)MemoryCache.get(SystemUser.prix, u.getString("id"));
			if(mu.get("fingers") != null){
				List<Record> fingers = (List<Record>) mu.get("fingers");
				for(Record f : fingers){
					if (JavaToBiokey.NativeToProcess(f.getString("finger_image"), (String)params.get("finger_image"))) {
						user = u;
						break;
					}
				}
			}
		}
		if(user != null){
			account = new Account();
			account.setSupperUser("CRY".equals(user.get("type")));
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
			account.setWelcomePage(user.getString("welcome_url"));
			if (account.isSupperUser()) {
//					for (String plugin : GlobalVar.getLicense().getPlugins()) {
//						account.getPluginList().add(plugin);
//					}
			} else {
				try{
					connect();
					UserDao ud = new UserDao();
					Result plugins = ud.queryUserPlugIn(account.getId());
					for (Record plugin : plugins.getResultset()) {
						account.getPluginList().add(plugin.getString("plugin"));
					}
					Result pages = ud.queryUserPage(account.getId());
					for (Record page : pages.getResultset()) {
						account.getPageList().add(page.getString("code"));
					}
				}catch(Exception e){
					throw e;
				}finally{
					disconnect();
				}
			}
		}

		return account;
	}
}
