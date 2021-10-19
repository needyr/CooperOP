package cn.crtech.cooperop.hospital_common.dao;

import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class ReftDao extends BaseDao {
	
	private static final String TABLE_NAME = "dbo.data_service_table_ref";
	
	public int insert(Map<String, Object> params) throws Exception {
		return executeInsert(TABLE_NAME, params);
	}
	
	public int delete(Map<String, Object> params) throws Exception {
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("data_service_method_code", params.get("data_service_method_code"));
		conditions.put("table_name", params.get("table_name"));
		conditions.put("data_service_code", params.get("data_service_code"));
		return executeDelete(TABLE_NAME, conditions);
	}
	
	public int update(Map<String, Object> params) throws Exception {
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("data_service_method_code", params.get("data_service_method_code"));
		conditions.put("table_name", params.get("table_name"));
		conditions.put("data_service_code", params.get("data_service_code"));
		return executeUpdate(TABLE_NAME, params, conditions);
	}
	
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from " + TABLE_NAME + "(nolock) ");
		sql.append(" where 1=1 ");
		setParameter(params, "data_service_code", " and data_service_code=:data_service_code", sql);
		setParameter(params, "data_service_method_code", " and data_service_method_code=:data_service_method_code", sql);
		setParameter(params, "_exceptColumn_", " and table_name<>:_exceptColumn_", sql);
		setParameter(params, "_exceptType_", " and type=:_exceptType_", sql);
		if (!CommonFun.isNe(params.get("key"))) {
			params.put("key", "%" + params.remove("key") + "%");
			setParameter(params, "key", 
					" and ( table_name like :key or parent_table_name like :key "
					+ "or param_name like :key)", sql);
		}
		return executeQueryLimit(sql.toString(), params);
	}
	
	public Record get(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from " + TABLE_NAME + "(nolock) " + " where "
				+ "data_service_method_code=:data_service_method_code "
				+ " and table_name=:table_name"
				+ " and data_service_code=:data_service_code");
		return executeQuerySingleRecord(sql.toString(), params);
	}
}
