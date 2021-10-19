package cn.crtech.cooperop.crdc.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class FunctionListDao extends BaseDao {
	private final static String TABLE_NAME = "cr_funclist";
	
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select f.* from " + TABLE_NAME + "(nolock) f ");
		sql.append(" where 1 = 1 ");
		if(!CommonFun.isNe(params.get("filter"))){
			params.put("key", "%"+params.get("filter")+"%");
		}
		setParameter(params, "filter", " and (f.functionname like :key or f.functitle like :key) ", sql);
		return executeQueryLimit(sql.toString(), params);
	}
	public Record get(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select f.* from " + TABLE_NAME + "(nolock) f ");
		sql.append(" where f.functionname=:functionname ");
		return executeQuerySingleRecord(sql.toString(), params);
	}
	public int insert(Map<String, Object> params) throws Exception {
		return executeInsert(TABLE_NAME, params);
	}
	public int update(Map<String, Object> params) throws Exception {
		Record r = new Record();
		r.put("functionname", params.remove("functionname_old"));
		return executeUpdate(TABLE_NAME, params, r);
	}
	public int delete(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from  "+ TABLE_NAME);
		sql.append(" where functionname=:functionname ");
		return executeUpdate(sql.toString(), params);
	}
}
