package cn.crtech.cooperop.setting.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class RoleDao extends BaseDao{
private final static String TABLE_NAME = "[dbo].[system_role]";
private final static String TABLE_NAME_RULE = "[dbo].[system_rule]";
	
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select f.* from [dbo].[system_role] f ");
		sql.append(" where 1 = 1 ");
		if(!CommonFun.isNe(params.get("filter"))){
			params.put("key", "%"+params.get("filter")+"%");
		}
		if(!CommonFun.isNe(params.get("namekey"))){
			params.put("nkey", "%"+params.get("namekey")+"%");
		}
		setParameter(params, "system_product_code", " and f.system_product_code=:system_product_code", sql);
		setParameter(params, "name", " and f.name=:name", sql);
		setParameter(params, "nkey", " and f.name like :nkey", sql);
		setParameter(params, "filter", " and f.name like :key ", sql);
		setParameter(params, "user_id", " and f.id in (select distinct system_role_id from system_rule where system_user_id=:user_id) ", sql);
		return executeQueryLimit(sql.toString(), params);
	}
	public Result queryPersonByRole(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select distinct su.no, system_user_id as system_user_id,su.name as system_user_name  ");
		sql.append(" from dbo.system_rule(nolock)   r ");
		sql.append(" left join v_system_user(nolock) su on su.id = r.system_user_id ");
		sql.append(" where system_role_id = :system_role_id ");
		setParameter(params, "role_user_id", " and (su.id=:role_user_id or su.no=:role_user_id or su.name like '%'+:role_user_id+'%')", sql);
		return executeQueryLimit(sql.toString(), params);
	}
	public Result queryDeps(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select d.* ");
		if(!CommonFun.isNe(params.get("system_user_id"))){
			sql.append(" ,r.system_role_id as roleid  ");
		}
		sql.append(" from [dbo].[system_department] d ");
		if(!CommonFun.isNe(params.get("system_user_id"))){
			sql.append(" left join [dbo].[system_rule] r on r.system_department_id=d.id ");
			sql.append(" and r.system_role_id=:system_role_id and r.system_user_id=:system_user_id ");
		}
		sql.append(" where d.state=1 ");
		return executeQuery(sql.toString(), params);
	}
	public Record get(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select f.* from [dbo].[system_role] f ");
		sql.append(" where f.id=:id ");
		return executeQuerySingleRecord(sql.toString(), params);
	}
	public int insert(Map<String, Object> params) throws Exception {
		params.remove("id");
		return executeInsert(TABLE_NAME, params);
	}
	public int insertRules(Map<String, Object> params) throws Exception {
		return executeInsert(TABLE_NAME_RULE, params);
	}
	public void saveDellog(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" insert into delrolelog (role_id,role_name,role_system_product_code,deltime,deluser,delusername,userplugin) ");
		sql.append(" values(:role_id,:role_name,:role_system_product_code,:deltime,:deluser,:delusername,:userplugin) ");
		execute(sql.toString(), params);
	}
	public int update(Map<String, Object> params) throws Exception {
		Record r = new Record();
		r.put("id", params.remove("id"));
		return executeUpdate(TABLE_NAME, params, r);
	}
	public void delete(Map<String, Object> params) throws Exception {
		int i = executeDelete(TABLE_NAME, params);
		params.put("system_role_id", params.remove("id"));
		executeDelete(TABLE_NAME_RULE, params);
		executeDelete("system_role_popedom", params);
	}
	public int deleteRules(Map<String, Object> params) throws Exception {
		return executeUpdate("delete from "+TABLE_NAME_RULE+" where system_user_id=:system_user_id and system_role_id=:system_role_id", params);
	}
	public int savePopedom(Map<String, Object> params) throws Exception {
		return executeInsert("[dbo].[system_role_popedom]", params);
	}
	public int deletePopedom(Map<String, Object> params) throws Exception {
		return executeDelete("[dbo].[system_role_popedom]", params);
	}
	public Result queryParentByPopedomid(Map<String, Object> params) throws Exception{
		StringBuffer sql = new StringBuffer();
		sql.append(" ;WITH t AS                                      ");
		sql.append(" (                                               ");
		sql.append("     SELECT *                                    ");
		sql.append("     FROM system_popedom                         ");
		sql.append("     WHERE id in (:system_popedom_id)            ");
		sql.append("     UNION ALL                                   ");
		sql.append("     SELECT a.*                                  ");
		sql.append("     FROM system_popedom a, t b                  ");
		sql.append("     WHERE b.system_popedom_id_parent = a.id     ");
		sql.append(" )                                               ");
		sql.append(" SELECT distinct id                              ");
		sql.append(" FROM t                                          ");
		return executeQuery(sql.toString(), params);
	}
}
