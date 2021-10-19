package cn.crtech.precheck.dao;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;

public class TradeDao extends BaseDao {
	public Result listService() throws Exception {
		Record params = new Record();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from data_service(nolock)");
		return executeQuery(sql.toString(), params);
	}
	
	public Record getService(String code) throws Exception {
		Record params = new Record();
		params.put("code", code);
		StringBuffer sql = new StringBuffer();
		sql.append("select * from data_service(nolock) where code = :code");
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	public Record getMethod(String service_code, String method_code) throws Exception {
		Record params = new Record();
		params.put("data_service_code", service_code);
		params.put("code", method_code);
		StringBuffer sql = new StringBuffer();
		sql.append("select * from data_service_method(nolock) where data_service_code = :data_service_code and code = :code");
		Record rtn = executeQuerySingleRecord(sql.toString(), params);
		return rtn;
	}

	public Result listTableRef(String service_code, String method_code) throws Exception {
		Record params = new Record();
		params.put("data_service_code", service_code);
		params.put("data_service_method_code", method_code);
		StringBuffer sql = new StringBuffer();
		sql.append("select * from data_service_table_ref(nolock) where data_service_code = :data_service_code and data_service_method_code = :data_service_method_code order by table_names");
		return executeQuery(sql.toString(), params);
	}
	
	public int newLog(String appId, String service_code, String method_code, String request) throws Exception {
		String id = getSeqNextVal("data_service_log");
		Record params = new Record();
		params.put("id", id);
		params.put("appId", appId);
		params.put("data_service_code", service_code);
		params.put("data_service_method_code", method_code);
		params.put("begin_time", "sysdate");
		params.put("state", "0");
		params.put("request", request);
		executeInsert("data_service_log", params);
		return Integer.parseInt(id);
//	    executeInsert("data_service_log", params);
//	    return getSeqVal("data_service_log").intValue();
	}

	public int updateLog(int data_service_log_id, Record params) throws Exception {
		Record conditions = new Record();
		conditions.put("id", data_service_log_id);
		return executeUpdate("data_service_log", params, conditions);
	}

	public int backup(int data_service_log_id) throws Exception {
		Record params = new Record();
		params.put("data_service_log_id", data_service_log_id);
		StringBuffer sql = new StringBuffer();
		sql.append("insert into data_service_log_bak (");
		sql.append("	data_service_code,                    ");
		sql.append("	begin_time,                           ");
		sql.append("	execute_begin_time,                   ");
		sql.append("	execute_end_time,                     ");
		sql.append("	end_time,                             ");
		sql.append("	state,                                ");
		sql.append("	error_message,                        ");
		sql.append("	data_service_method_code,             ");
		sql.append("	appId,                                ");
		sql.append("	request,                              ");
		sql.append("	result,                               ");
		sql.append("	id) select                            ");
		sql.append("		data_service_code,                ");
		sql.append("		begin_time,                       ");
		sql.append("		execute_begin_time,               ");
		sql.append("		execute_end_time,                 ");
		sql.append("		end_time,                         ");
		sql.append("		state,                            ");
		sql.append("		error_message,                    ");
		sql.append("		data_service_method_code,         ");
		sql.append("		appId,                            ");
		sql.append("		request,                          ");
		sql.append("		result,                           ");
		sql.append("		id from data_service_log(nolock)  ");
		sql.append(" where id = :data_service_log_id");
		executeUpdate(sql.toString(), params);
		sql = new StringBuffer();
		sql.append("delete from data_service_log where id = :data_service_log_id");
		return executeUpdate(sql.toString(), params);
	}
	
	public int insertTable(String table, Record r) throws Exception {
		String id = getSeqNextVal(table);
		r.put("id", id);
		executeInsert(table, r);
		return Integer.parseInt(id);
//		return getSeqVal(table).intValue();
	}
	
	// +++
	public String getAlias(String table_name, String field_name) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select alias_name from dbo.data_table_alias (nolock) where ");
		sql.append("table_name=:table_name and field_name=:field_name");
		
		Record conditions = new Record();
		conditions.put("table_name", table_name);
		conditions.put("field_name", field_name);
		Record r = executeQuerySingleRecord(sql.toString(), conditions);
		return (String)(r != null ? r.get("alias_name") : null);
	}
	// +++
}
