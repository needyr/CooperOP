package cn.crtech.cooperop.setting.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.setting.dao.RoleDao;

public class RoleService extends BaseService{
	public Result query(Map<String, Object> params) throws Exception {
		try {
			connect();
			RoleDao dd = new RoleDao();
			return dd.query(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public Result queryPersonByRole(Map<String, Object> params) throws Exception {
		try {
			connect();
			RoleDao dd = new RoleDao();
			return dd.queryPersonByRole(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public Result queryDeps(Map<String, Object> params) throws Exception {
		try {
			connect();
			RoleDao dd = new RoleDao();
			return dd.queryDeps(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public Record get(Map<String, Object> params) throws Exception {
		try {
			connect();
			RoleDao dd = new RoleDao();
			return dd.get(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}

	public int save(Map<String, Object> params) throws Exception {
		try {
			connect();
			RoleDao dd = new RoleDao();
			int i = dd.insert(params);
			return i;
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}
	}
	public void savePopedom(Map<String, Object> params) throws Exception {
		try {
			connect();
			start();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("system_role_id", params.get("roleid"));
			RoleDao dd = new RoleDao();
			dd.deletePopedom(map);
			List<Map<String, Object>> list = (List<Map<String, Object>>) params.get("rps");
			String[] system_popedom_id = new String[list.size()];
			for(int i =0; i<list.size();i++){
				system_popedom_id[i] = list.get(i).get("system_popedom_id").toString();
			}
			params.put("system_popedom_id", system_popedom_id);
			if(system_popedom_id.length > 0) {
				Result res = dd.queryParentByPopedomid(params);
				for(int i =0; i<res.getResultset().size() ;i++){
					Map<String, Object> m = new HashMap<String, Object>();
					m.put("system_role_id", params.get("roleid"));
					m.put("system_popedom_id", res.getResultset(i).get("id"));
					dd.savePopedom(m);
				}
			}else {
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("system_role_id", params.get("roleid"));
				dd.deletePopedom(m);
			}
			commit();
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}
	}
	public void setPopedom(Map<String, Object> params) throws Exception {
		try {
			connect();
			start();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("system_user_id", params.get("system_user_id"));
			map.put("system_role_id", params.get("system_role_id"));
			List<Map<String, Object>> deps = (List<Map<String, Object>>) params.get("system_department_id");
			if(deps == null){
				return;
			}
			RoleDao rd = new RoleDao();
			rd.deleteRules(map);
			for(int i =0;i<deps.size();  i++){
				map.put("system_department_id", deps.get(i));
				rd.insertRules(map);
			}
			commit();
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}
	}
	public void setPopedoms(Map<String, Object> params) throws Exception {
		try {
			connect();
			start();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("system_role_id", params.get("system_role_id"));
			List<Map<String, Object>> deps = (List<Map<String, Object>>) params.get("system_department_id");
			List<Map<String, Object>> system_users = (List<Map<String, Object>>) params.get("system_users");
			if(deps == null || system_users == null){
				return;
			}
			for(Map<String, Object> u : system_users){
				map.put("system_user_id", u.get("system_user_id"));
				RoleDao rd = new RoleDao();
				rd.deleteRules(map);
				for(int i =0;i<deps.size();  i++){
					map.put("system_department_id", deps.get(i));
					rd.insertRules(map);
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
	public int update(Map<String, Object> params) throws Exception {
		try {
			connect();
			int i = new RoleDao().update(params);
			return i;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public int delete(Map<String, Object> params) throws Exception {
		try {
			connect();
			start();
			RoleDao dd = new RoleDao();
			//添加删除记录
			Map<String, Object> dellog = new HashMap<String, Object>();
			Record rolerecord = dd.get(params);
			String userplugin = "";
			List<String> up =  user().getPluginList();
			if(up.size() > 0){
				for(String s : up){
					userplugin += s+",";
				}
			}
			dellog.put("role_id", rolerecord.get("id"));
			dellog.put("role_name", rolerecord.get("name"));
			dellog.put("role_system_product_code", rolerecord.get("system_product_code"));
			dellog.put("deltime", "sysdate");
			dellog.put("deluser", user().getId());
			dellog.put("delusername", user().getName());
			dellog.put("userplugin", userplugin);
			dd.saveDellog(dellog);
			new RoleDao().delete(params);
			commit();
			return 1;
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}
	}
	public int deleteRules(Map<String, Object> params) throws Exception {
		try {
			connect();
			RoleDao dd = new RoleDao();
			int i = dd.deleteRules(params);
			return i;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
}
