package cn.crtech.cooperop.setting.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class UserDao extends BaseDao{
	private final static String TABLE_NAME = "[dbo].[system_user]";
	
	public Result queryByDep(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select f.*  from [dbo].[v_system_user] f ");
		if(!CommonFun.isNe(params.get("scheduling_type"))){
			sql.append(" left join system_user_extend sue on sue.system_user_id=f.id ");
		}
		sql.append(" where 1 = 1 ");
		setParameter(params, "depid", " and f.department in (:depid)", sql);
		setParameter(params, "scheduling_type", " and sue.scheduling_type = :scheduling_type", sql);
		setParameter(params, "selectedpersons", " and f.id not in (:selectedpersons)", sql);
		return executeQuery(sql.toString(), params);
	}

	public Result queryRoleByDep(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from [dbo].[system_rule] (nolock)");
		sql.append(" where system_department_id = :depid");
		return executeQuery(sql.toString(), params);
	}

	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select f.*,sd.name AS department_name  from [dbo].[system_user] f ");
		sql.append("  LEFT JOIN system_department sd ON f.department = sd.id  where 1 = 1 and (f.state!=-1 or f.state is null) ");
		if(!CommonFun.isNe(params.get("filter"))){
			params.put("key", "%"+params.get("filter")+"%");
		}
		setParameter(params, "base_dep", " and f.department = :base_dep", sql);
		setParameter(params, "filter", " and (f.id like :key or f.name like :key or f.no like :key) ", sql);
		return executeQueryLimit(sql.toString(), params);
	}
	public Result queryV(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select f.*  from [dbo].[v_system_user] f ");
		sql.append("   where 1 = 1 ");
		if(!CommonFun.isNe(params.get("filter"))){
			params.put("key", "%"+params.get("filter")+"%");
		}
		setParameter(params, "base_dep", " and f.department = :base_dep", sql);
		setParameter(params, "filter", " and (f.id like :key or f.name like :key or f.no like :key) ", sql);
		return executeQueryLimit(sql.toString(), params);
	}
	public Result queryUserCount(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select f.*  from [dbo].[v_system_user] f ");
		sql.append("   where 1 = 1 ");
		return executeQuery(sql.toString(), params);
	}
	public Record get(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select f.*,sd.name AS department_name,sp.name as position_name  ");
		sql.append(" from [dbo].[system_user] f ");
		sql.append(" left join system_post as sp on f.position=sp.id ");
		sql.append("  LEFT JOIN system_department sd ON f.department = sd.id ");
		sql.append(" where 1 = 1 ");
		setParameter(params, "id", " and f.id=:id", sql);
		setParameter(params, "no", " and f.no=:no", sql);
		return executeQuerySingleRecord(sql.toString(), params);
	}
	public int insert(Map<String, Object> params) throws Exception {
		params.remove("id");
		params.put("type", "employee");
		return executeInsert(TABLE_NAME, params);
	}
	public int update(Map<String, Object> params) throws Exception {
		Record r = new Record();
		r.put("id", params.remove("id"));
		return executeUpdate(TABLE_NAME, params, r);
	}
	public int delete(Map<String, Object> params) throws Exception {
		Record r = new Record();
		params.put("state", -1);
		r.put("id", params.remove("id"));
		return executeUpdate(TABLE_NAME, params, r);
	}
}
