package cn.crtech.cooperop.crdc.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class PopedomDao extends BaseDao {
	private final static String TABLE_NAME = "[dbo].[system_popedom]";
	
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT                                                                        ");
		sql.append(" f.*, (                                                                        ");
		sql.append(" 	SELECT                                                                     ");
		sql.append(" 		COUNT (1)                                                              ");
		sql.append(" 	FROM                                                                       ");
		sql.append(" 		[dbo].[system_popedom]                                                 ");
		sql.append(" 	WHERE                                                                      ");
		sql.append(" 		system_popedom_id_parent = f.id                                        ");
		sql.append(" ) AS childnums                                                                ");
		sql.append(" from [dbo].[system_popedom](nolock) f ");
		sql.append(" where 1 = 1 ");
		if(!CommonFun.isNe(params.get("root"))){
			sql.append(" and f.system_popedom_id_parent is null");
		}
		if(!CommonFun.isNe(params.get("parentid"))){
			sql.append(" and f.system_popedom_id_parent = :parentid");
		}
		if (CommonFun.isNe(params.get("sort"))) {
			params.put("sort", "order_nos");
		}
		return executeQuery(sql.toString(), params);
	}
	public Result queryByRole(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT                                                                        ");
		sql.append(" f.*, (                                                                        ");
		sql.append(" 	SELECT                                                                     ");
		sql.append(" 		COUNT (1)                                                              ");
		sql.append(" 	FROM                                                                       ");
		sql.append(" 		[dbo].[system_popedom]                                                 ");
		sql.append(" 	WHERE                                                                      ");
		sql.append(" 		system_popedom_id_parent = f.id                                        ");
		sql.append(" ) AS childnums,                                                               ");
		sql.append(" rp.system_popedom_id AS hasp                                                  ");
		sql.append(" FROM                                                                          ");
		sql.append(" 	[dbo].[system_popedom] f                                                   ");
		sql.append(" LEFT JOIN [dbo].[system_role_popedom] rp ON rp.system_popedom_id = f.id       ");
		sql.append(" AND rp.system_role_id = :roleid                                                   ");
		return executeQueryLimit(sql.toString(), params);
	}
	public Record get(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select *  from [dbo].[system_popedom] f where 1=1 ");
		if(!CommonFun.isNe(params.get("id"))){
			sql.append(" and id = :id ");
		}
		if(!CommonFun.isNe(params.get("system_popedom_id_parent"))){
			sql.append(" and system_popedom_id_parent = :system_popedom_id_parent ");
		}
		
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	public Record getMaxId(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select max(id) id  from [dbo].[system_popedom] f where 1=1 ");
		if(!CommonFun.isNe(params.get("system_popedom_id_parent"))){
			sql.append(" and system_popedom_id_parent = :system_popedom_id_parent ");
		}
		
		return executeQuerySingleRecord(sql.toString(), params);
	}
	public int insert(Map<String, Object> params) throws Exception {
		return executeInsert(TABLE_NAME, params);
	}
	public int update(Map<String, Object> params) throws Exception {
		Record r = new Record();
		r.put("id", params.remove("id"));
		params.remove("system_popedom_id_parent");
		return executeUpdate(TABLE_NAME, params,r);
	}
	public int delete(Map<String, Object> params) throws Exception {
		return executeDelete(TABLE_NAME, params);
	}
	
}
