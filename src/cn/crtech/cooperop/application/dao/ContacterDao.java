package cn.crtech.cooperop.application.dao;

import java.util.Date;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class ContacterDao extends BaseDao {
	
	public Result queryMine(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from (select * from v_user_contacter where type='G' and id in (select distinct system_user_group_id from system_user_group_user where system_user_id=:system_user_id ) ");
		sql.append(" union all ");
		sql.append(" select  * from    v_user_contacter vuc " + "(nolock)");
		sql.append(" where   isnull(system_user_id, :system_user_id) = :system_user_id and type!='G' and name != '陈氏集团') vuc ");
		if(!CommonFun.isNe(params.get("scheduling_type"))){
			sql.append(" left join (select system_user_id as sue_id,scheduling_type from system_user_extend) as sue on sue.sue_id = vuc.id and vuc.type='u' ");
		}
		sql.append(" where 1=1 ");
		params.put("system_user_id", user().getId());
        setParameter(params, "filter", " and input_code like '%' + upper(:filter) + '%' ", sql);
        setParameter(params, "type", " and type in (:type)", sql);
        setParameter(params, "queryDep", " and department in (:queryDep)", sql);
        setParameter(params, "except", " and (type + '|' + id) not in (:except) ", sql);
        setParameter(params, "scheduling_type", " and scheduling_type=:scheduling_type ", sql);
        if (CommonFun.isNe(params.get("sort"))) {
        	params.put("sort", "order_no");
        }
		long start = CommonFun.isNe(params.get("start")) ? 1 : Long.parseLong((String)params.get("start"));
	    int limit = CommonFun.isNe(params.get("limit")) ? 0 : Integer.parseInt((String)params.get("limit"));
	    Result rs = executeQueryLimit(sql.toString(), params, start, limit);
	    Record totals = new Record();
	    totals.put("type", "E");
	    totals.put("type_name", "外部邮件");
	    totals.put("id", params.get("filter"));
	    totals.put("name", params.get("filter"));
		rs.setTotals(totals);
	    return rs;
	}
	
	public Result contacters(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from (select * from v_user_contacter where type='G' and id in (select distinct system_user_group_id from system_user_group_user where system_user_id=:system_user_id ) ");
		sql.append(" union all ");
		sql.append(" select  * from    v_user_contacter vuc " + "(nolock)");
		sql.append(" where   isnull(system_user_id, :system_user_id) = :system_user_id and type!='G') vuc where 1=1 ");
		
        params.put("system_user_id", user().getId());
		if (CommonFun.isNe(params.get("type"))) {
			sql.append("   and   type in ('G', 'U', 'D') ");
		} else {
			//sql.append("   and   type in (:type) ");
		}
        if (!CommonFun.isNe(params.get("filter"))) {
        	sql.append("   and exists (                                                             "); 
        	sql.append("   		select                                                              "); 
        	sql.append("   			1                                                               "); 
        	sql.append("   		from                                                                "); 
        	sql.append("   			v_user_contacter f                                              "); 
        	sql.append("   		where                                                               "); 
        	sql.append("   			input_code like '%' + upper (:filter) + '%'                     "); 
        	sql.append("   		and (                                                               "); 
        	sql.append("   			(                                                               "); 
        	sql.append("   				f.type = vuc.type                                           "); 
        	sql.append("   				and f.id = vuc.id                                           "); 
        	sql.append("   			)                                                               "); 
        	sql.append("   			or (                                                            "); 
        	sql.append("   				vuc.type = 'D'                                              "); 
        	sql.append("   				and ',' + f.departments + ',' like '%,' + vuc.id + ',%'     "); 
        	sql.append("   			)                                                               "); 
        	sql.append("   		)                                                                   "); 
        	sql.append("   	)                                                                       "); 
        }
        if (CommonFun.isNe(params.get("sort"))) {
        	params.put("sort", "order_no");
        }
        setParameter(params, "contacter_ids", " and type+''+id in(:contacter_ids)", sql);
		long start = CommonFun.isNe(params.get("start")) ? 1 : Long.parseLong((String)params.get("start"));
	    int limit = CommonFun.isNe(params.get("limit")) ? 0 : Integer.parseInt((String)params.get("limit"));
	    Result rs = executeQueryLimit(sql.toString(), params, start, limit);
	    return rs;
	}
	
	public Result contacter_users(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select  *                                                         ");
		sql.append(" from    v_user_contacter_users                                          " + "(nolock)");
		sql.append(" where   1 = 1 ");
        setParameter(params, "type", " and contacter_type = :type ", sql);
        setParameter(params, "id", " and contacter_id = :id ", sql);
        if (CommonFun.isNe(params.get("sort"))) {
        	params.put("sort", "name");
        }
	    Result rs = executeQuery(sql.toString(), params);
	    return rs;
	}
	
	public Result queryGroup(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select g.*,su.name as system_user_name from system_user_group g (nolock) ");
		sql.append(" left join v_system_user su on su.id = g.system_user_id ");
		sql.append(" where 1=1");
		setParameter(params, "system_user_id", " and g.system_user_id=:system_user_id", sql);
		setParameter(params, "id", " and g.id=:id", sql);
		Result rs = executeQuery(sql.toString(), params);
	    return rs;
	}
	public Result queryGroupUser(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		params.put("system_user_id", user().getId());
		sql.append(" select g.*,su.name as system_user_name from system_user_group_user g (nolock) ");
		sql.append(" left join v_system_user su on su.id = g.system_user_id ");
		sql.append(" where g.system_user_id=:system_user_id");
		setParameter(params, "gid", " and g.system_user_group_id=:id", sql);
		Result rs = executeQuery(sql.toString(), params);
	    return rs;
	}
	public int saveGroup(Map<String, Object> params) throws Exception {
		params.remove("id");
		params.put("system_user_id",user().getId());
		params.put("create_time", new Date());
		executeInsert("system_user_group", params);
		return getSeqVal("system_user_group");
	}
	public int saveGroupUser(Map<String, Object> params) throws Exception {
		executeInsert("system_user_group_user", params);
		return getSeqVal("system_user_group_user");
	}
	public int deleteGroup(Map<String, Object> params) throws Exception {
		Record conditions = new Record();
		conditions.put("id", params.get("id"));
		return executeDelete("system_user_group", conditions);
	}
	public int updateGroup(Map<String, Object> params) throws Exception {
		Record conditions = new Record();
		conditions.put("id", params.remove("id"));
		conditions.put("system_user_id", user().getId());
		return executeUpdate("system_user_group", params, conditions);
	}
	public void deleteGroupUser(Map<String, Object> params) throws Exception {
		if(!CommonFun.isNe(params.get("system_user_id"))){
			executeUpdate("delete from system_user_group_user where system_user_group_id=:system_user_group_id and system_user_id=:system_user_id", params);
		}else if(!CommonFun.isNe(params.get("system_user_group_id"))){
			executeUpdate("delete from system_user_group_user where system_user_group_id=:system_user_group_id", params);
		}
	}
}
