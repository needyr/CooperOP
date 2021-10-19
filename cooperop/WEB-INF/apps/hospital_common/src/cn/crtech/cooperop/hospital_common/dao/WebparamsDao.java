package cn.crtech.cooperop.hospital_common.dao;

import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class WebparamsDao extends BaseDao {

	// 0 完成   1 未完成
	private static String TABLE_NAME = "dbo.data_webservice_quene_param";
	private static String TABLE_NAME_DONE = "dbo.data_webservice_quene_param_bak";
	
	public int insert(Map<String, Object> params) throws Exception {
		return executeInsert(getTableName(params), params);
	}
	
	public int delete(Map<String, Object> params) throws Exception {
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("data_webservice_quene_id", params.get("data_webservice_quene_id"));
		conditions.put("order_no", params.get("order_no"));
		return executeDelete(getTableName(params), conditions);
	}
	
	public int update(Map<String, Object> params) throws Exception {
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("data_webservice_quene_id", params.get("data_webservice_quene_id"));
		conditions.put("order_no", params.get("order_no"));
		return executeUpdate(getTableName(params), params, conditions);
	}
	
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from " + getTableName(params) + "(nolock) ");
		sql.append(" where 1=1 ");
		setParameter(params, "id", " and data_webservice_quene_id=:id ", sql);
		if (!CommonFun.isNe(params.get("key"))) {
			params.put("key", "%" + params.remove("key") + "%");
			setParameter(params, "key", " and (table_name like :key "
					+ " or value like :key)", sql);
		}
		return executeQueryLimit(sql.toString(), params);
	}
	
	public Record get(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from " + getTableName(params) + "(nolock) ");
		sql.append(" where data_webservice_quene_id=:data_webservice_quene_id"
				+ " and order_no=:order_no");
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	public static String getTableName(Map<String, Object> params) {
		if (!CommonFun.isNe(params.get("case")) && 
				Integer.parseInt((String)params.get("case")) == 1) {
			return TABLE_NAME;
		} else {
			return TABLE_NAME_DONE;
		}
	}
}
