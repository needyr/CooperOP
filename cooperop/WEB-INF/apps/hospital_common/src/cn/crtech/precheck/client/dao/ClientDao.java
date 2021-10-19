package cn.crtech.precheck.client.dao;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.util.GlobalVar;

public class ClientDao extends BaseDao {
	/**
	 * 返回所有客服端服务（以 code  排序）
	 * @return
	 * @throws Exception
	 */
	public Result listClient() throws Exception {
		Record params = new Record();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from data_webservice(nolock) order by code");
		return executeQuery(sql.toString(), params);
	}

	/**
	 * 返回指定的客服端服务
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public Record getClient(String code, String state) throws Exception {
		Record params = new Record();
		params.put("code", code);
		StringBuffer sql = new StringBuffer();
		sql.append("select * from data_webservice(nolock) where code = :code ");
		if(state != null){
			params.put("state", state);
			sql.append(" and state=:state ");
		}
		return executeQuerySingleRecord(sql.toString(), params);
	}

	/**
	 * 获取所有待交易、指定服务、指定交易的交易队列 或者
	 * 所有待交易或者交易异常并且重试次数少于3次的交易队列
	 * @param data_webservice_code
	 * @param data_webservice_method_code
	 * @return
	 * @throws Exception
	 */
	public Result listClientTrading(String data_webservice_code, String data_webservice_method_code) throws Exception {
		Record params = new Record();
		params.put("data_webservice_code", data_webservice_code);
		params.put("data_webservice_method_code", data_webservice_method_code);
		StringBuffer sql = new StringBuffer();
		//sql.append("select * from data_webservice_quene where (state = 0) or (state = -1 and isnull(retry_times, 0) < " + GlobalVar.getSystemProperty("webservice.retry_times", "3") + ") order by id");
		sql.append("select * from data_webservice_quene (nolock) ");
		if (!CommonFun.isNe(data_webservice_code) && !CommonFun.isNe(data_webservice_method_code)) {
			sql.append(" where state = 0 ");
			sql.append(" and data_webservice_code = :data_webservice_code ");
			sql.append(" and data_webservice_method_code = :data_webservice_method_code ");
		} else {
			sql.append(" where (state = 0) or (state = -1 and isnull(retry_times, 0) < " + GlobalVar.getSystemProperty("webservice.retry_times", "3") + ") ");
		}
		sql.append(" order by id");
		return executeQuery(sql.toString(), params);
	}

	/**
	 * 获取指定服务的所有接口方法
	 * @param data_webservice_code
	 * @return
	 * @throws Exception
	 */
	public Result queryMethod(String data_webservice_code ,String state) throws Exception {
		Record params = new Record();
		params.put("data_webservice_code", data_webservice_code);
		params.put("state", state);
		StringBuffer sql = new StringBuffer();
		sql.append("select * from data_webservice_method(nolock) where data_webservice_code = :data_webservice_code");
		if(state != null){
			sql.append("  and state=:state");
		}
		Result rtn = executeQuery(sql.toString(), params);
		return rtn;
	}
	/**
	 * 获取指定服务的所有接口方法
	 * @param data_webservice_code
	 * @return
	 * @throws Exception
	 */
	public Result queryMethodByManual(String data_webservice_code, String p_type) throws Exception {
		Record params = new Record();
		params.put("data_webservice_code", data_webservice_code);
		params.put("p_type", p_type);
		params.put("can_manual", "1");
		StringBuffer sql = new StringBuffer();
		sql.append("select * from data_webservice_method(nolock) where data_webservice_code = :data_webservice_code");
		sql.append(" and (p_type='0' or p_type=:p_type) and can_manual=:can_manual");
		Result rtn = executeQuery(sql.toString(), params);
		return rtn;
	}
	/**
	 * 获取指定服务的指定接口方法
	 * @param data_webservice_code
	 * @param data_webservice_method_code
	 * @return
	 * @throws Exception
	 */
	public Record getMethod(String data_webservice_code, String data_webservice_method_code) throws Exception {
		Record params = new Record();
		params.put("data_webservice_code", data_webservice_code);
		params.put("code", data_webservice_method_code);
		StringBuffer sql = new StringBuffer();
		sql.append("select * from data_webservice_method(nolock) where data_webservice_code = :data_webservice_code and code = :code");
		Record rtn = executeQuerySingleRecord(sql.toString(), params);
		return rtn;
	}

	/**
	 * 通过指定的交易调用流水号（就是日志 ID）返回参数列表，根据 order_no 升序排序（排序值，参数顺序）
	 * @param data_webservice_quene_id
	 * @return
	 * @throws Exception
	 */
	public Result listParams(long data_webservice_quene_id) throws Exception {
		Record params = new Record();
		params.put("data_webservice_quene_id", data_webservice_quene_id);
		StringBuffer sql = new StringBuffer();
		sql.append(
				"select * from data_webservice_quene_param (nolock) where data_webservice_quene_id = :data_webservice_quene_id order by order_no");
		return executeQuery(sql.toString(), params);
	}

	/**
	 * 
	 * @param data_webservice_code
	 * @param data_webservice_method_code
	 * @return
	 * @throws Exception
	 */
	public Result listTableRef(String data_webservice_code, String data_webservice_method_code) throws Exception {
		Record params = new Record();
		params.put("data_webservice_code", data_webservice_code);
		params.put("data_webservice_method_code", data_webservice_method_code);
		StringBuffer sql = new StringBuffer();
		sql.append(
				"select * from data_webservice_table_ref(nolock) where data_webservice_code = :data_webservice_code and data_webservice_method_code = :data_webservice_method_code order by table_names");
		return executeQuery(sql.toString(), params);
	}

	public int updateLog(long data_webservice_quene_id, Record params) throws Exception {
		Record conditions = new Record();
		conditions.put("id", data_webservice_quene_id);
		return executeUpdate("data_webservice_quene", params, conditions);
	}

	public int writeLog(Record logs) throws Exception {
		String id = getSeqNextVal("data_webservice_schedule_log");
		logs.put("id", id);
		executeInsert("data_webservice_schedule_log", logs);
		return Integer.parseInt(id);
	}
	
	public int backup(long data_webservice_quene_id) throws Exception {
		Record params = new Record();
		params.put("data_webservice_quene_id", data_webservice_quene_id);
		StringBuffer sql = new StringBuffer();
		sql.append("insert into data_webservice_quene_bak(data_webservice_method_code,begin_time,return_time,execute_begin_time,end_time,state,error_message,data_webservice_code,retry_times,result,request,id) select data_webservice_method_code,begin_time,return_time,execute_begin_time,end_time,state,error_message,data_webservice_code,retry_times,result,request,id from data_webservice_quene where id = :data_webservice_quene_id");
		executeUpdate(sql.toString(), params);
		sql = new StringBuffer();
		sql.append("insert into data_webservice_quene_param_bak(data_webservice_quene_id,table_name,value,order_no) select data_webservice_quene_id,table_name,value,order_no from data_webservice_quene_param where data_webservice_quene_id = :data_webservice_quene_id");
		executeUpdate(sql.toString(), params);
		sql = new StringBuffer();
		sql.append("delete from data_webservice_quene_param where data_webservice_quene_id = :data_webservice_quene_id");
		int i = executeUpdate(sql.toString(), params);
		sql = new StringBuffer();
		sql.append("delete from data_webservice_quene where id = :data_webservice_quene_id");
		executeUpdate(sql.toString(), params);
		return i;
	}

	public int insertTable(String table, Record r) throws Exception {
		String id = getSeqNextVal(table);
		r.put("id", id);
		executeInsert(table, r);
		return Integer.parseInt(id);
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
