package cn.crtech.cooperop.hospital_common.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class DictSysYlflDao extends BaseDao{
	private final static String TABLE_NAME = "dict_sys_ylfl";
	
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append(" SELECT                                                                        ");
		sql.append(" f.*, (                                                                        ");
		sql.append(" 	SELECT                                                                     ");
		sql.append(" 		COUNT (1)                                                              ");
		sql.append(" 	FROM                                                                       ");
		sql.append(TABLE_NAME+" (nolock) ");
		sql.append(" 	WHERE                                                                      ");
		sql.append(" 		parent_id = f.id                                        ");
		sql.append(" ) AS childnums                                                                ");
		sql.append(" from "+TABLE_NAME+" (nolock) f ");
		sql.append(" where 1 = 1 ");
		if(!CommonFun.isNe(params.get("parent_id"))){
			sql.append(" and f.parent_id = :parent_id");
		}
		if (CommonFun.isNe(params.get("sort"))) {
			params.put("sort", "sort");
		}
		return executeQuery(sql.toString(), params);
	}

	public void delete(Map<String, Object> params)  throws Exception  {
		execute("delete from "+TABLE_NAME+" where id = :id or parent_id=:id", params);
	}
	
	public Record get(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select *  from "+TABLE_NAME+" (nolock) where 1=1 ");
		if(!CommonFun.isNe(params.get("id"))){
			sql.append(" and id = :id ");
		}
		if(!CommonFun.isNe(params.get("parent_id"))){
			sql.append(" and parent_id = :parent_id ");
		}
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	public int insert(Map<String, Object> params) throws Exception {
		return executeInsert(TABLE_NAME, params);
	}
	
	public int update(Map<String, Object> params) throws Exception {
		Record r = new Record();
		r.put("id", params.remove("id"));
		params.remove("parent_id");
		return executeUpdate(TABLE_NAME, params,r);
	}
}
