package cn.crtech.cooperop.application.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;

public class DingdDao extends BaseDao {
	public Result queryProcessDefine(Map<String, Object> req) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from ding_process_definition where 1 = 1 and process_code is not null and state =1");
        return executeQuery(sql.toString(), req);
	}
	
	public Result queryProcessFieldDefine(Map<String, Object> req) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from ding_process_definition_field  where 1 = 1");
		setParameter(req, "process_code", " and process_code=:process_code", sql);
		return executeQuery(sql.toString(), req);
	}
	
	public void deleteByTime(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		String tablename = "dd_" + params.remove("table_code_");
		sql.append(" delete from "+tablename+" where create_time > :query_time  ");
        execute(sql.toString(), params);
	}
	public Record queryProcessTime(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		String tablename = "dd_" + params.get("table_code_");
		sql.append(" select case when(select count(1) from "+tablename+" where status ='RUNNING')>0  ");
		sql.append(" then (select min(create_time) from "+tablename+" where status ='RUNNING')       ");
		sql.append(" else (select max(create_time) from "+tablename+") end as query_time           ");
        return executeQuerySingleRecord(sql.toString(), params);
	}
	
	public void insert(Map<String, Object> params) throws Exception {
		String tablename = "dd_" + params.remove("table_code_");
	    executeInsert(tablename, params);
	}
}
