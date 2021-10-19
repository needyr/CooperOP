package cn.crtech.cooperop.ipc.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class SystemUserDao extends BaseDao{
	
	private final static String V_SYSTEM_USER = "v_system_user";
	
	public Result query(Map<String, Object> params) throws Exception{
		StringBuffer sql = new StringBuffer();
		sql.append("select vsc.*,vsc.id as comment_user from "+V_SYSTEM_USER+" (nolock) vsc ");
		sql.append(" inner join system_users (nolock) ss on vsc.no = ss.cooperop_sys_no ");
		sql.append(" where ss.type=2 ");
		if(!CommonFun.isNe(params.get("filter"))){
			params.put("key", "%"+params.get("filter")+"%");
		}
		setParameter(params, "filter", " and (name like :key or no=:filter)", sql);
		return executeQueryLimit(sql.toString(), params);
	}

	/*public Result query(Map<String, Object> params) throws Exception{
		StringBuffer sql = new StringBuffer();
		sql.append("select vsc.*,vsc.id as comment_user from "+V_SYSTEM_USER+" (nolock) vsc ");
		sql.append(" inner join system_users (nolock) ss on vsc.no = ss.cooperop_sys_no ");
		sql.append(" where ss.type=2 ");
		if(!CommonFun.isNe(params.get("data"))){
			params.put("key", "%"+params.get("data")+"%");
		}
		setParameter(params, "data", " and name like :key ", sql);
		return executeQueryLimit(sql.toString(), params);
	}*/
}
