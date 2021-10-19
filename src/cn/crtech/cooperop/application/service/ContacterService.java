package cn.crtech.cooperop.application.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.application.dao.ContacterDao;
import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;


public class ContacterService extends BaseService {

	public Result queryMine(Map<String, Object> params) throws Exception {
		try {
			connect();
			ContacterDao td = new ContacterDao();
			Result rs = td.queryMine(params);
			return rs;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}

	public Result contacter_users(Map<String, Object> params) throws Exception {
		try {
			connect();
			ContacterDao td = new ContacterDao();
			Result rs = td.contacter_users(params);
			return rs;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}

	public Result contacters(Map<String, Object> params) throws Exception {
		try {
			connect();
			ContacterDao td = new ContacterDao();
			Result rs = td.contacters(params);
			return rs;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}

	public void createGroup(Map<String, Object> params) throws Exception {
		try {
			connect();
			start();
			ContacterDao cd = new ContacterDao();
			List<Map<String, Object>> group_users = (List<Map<String, Object>>) params.remove("group_users");
			int gid = cd.saveGroup(params);
			String is_holdconfirm = "1";
			if("0".equals(params.get("need_audit"))){
				is_holdconfirm = "0";
			}
			for(int i =0;i<group_users.size() ; i++){
				Map<String, Object> u = new HashMap<String, Object>();
				u.put("system_user_group_id", gid);
				u.put("system_user_id", group_users.get(i).get("system_user_id"));
				u.put("request_time", new Date());
				if(user().getId().equals(u.get("system_user_id"))){//创建人是管理人
					u.put("is_holdconfirm", 0);
					u.put("is_manager", 1);
				}else{
					u.put("is_holdconfirm", is_holdconfirm);
					u.put("is_manager", 0);
				}
				cd.saveGroupUser(u);
			}
			commit();
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public void deleteGroup(Map<String, Object> params) throws Exception {
		try {
			connect();
			new ContacterDao().deleteGroup(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public void deleteGroupUser(Map<String, Object> params) throws Exception {
		try {
			connect();
			new ContacterDao().deleteGroupUser(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public void updateGroup(Map<String, Object> params) throws Exception {
		try {
			connect();
			start();
			ContacterDao cd = new ContacterDao();
			Object gid = params.get("id");
			if(!CommonFun.isNe(params.get("group_users"))){
				List<Map<String, Object>> group_users = (List<Map<String, Object>>) params.remove("group_users");
				cd.updateGroup(params);
				if(group_users != null){
					Map<String, Object> d = new HashMap<String, Object>();
					d.put("system_user_group_id", gid);
					cd.deleteGroupUser(d);
					String is_holdconfirm = "1";
					if("0".equals(params.get("need_audit"))){
						is_holdconfirm = "0";
					}
					for(int i =0;i<group_users.size() ; i++){
						Map<String, Object> u = new HashMap<String, Object>();
						u.put("system_user_group_id", gid);
						u.put("system_user_id", group_users.get(i).get("system_user_id"));
						u.put("request_time", new Date());
						if(user().getId().equals(u.get("system_user_id"))){//创建人是管理人
							u.put("is_holdconfirm", 0);
							u.put("is_manager", 1);
						}else{
							u.put("is_holdconfirm", is_holdconfirm);
							u.put("is_manager", 0);
						}
						cd.saveGroupUser(u);
					}
				}
			}
			commit();
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public Result queryGroup(Map<String, Object> params) throws Exception {
		try {
			connect();
			return new ContacterDao().queryGroup(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public Record getGroup(Map<String, Object> params) throws Exception {
		try {
			connect();
			ContacterDao cd = new ContacterDao();
			Record r = cd.queryGroup(params).getResultset(0);
			r.put("group_users", cd.queryGroupUser(params));
			return r;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
}
