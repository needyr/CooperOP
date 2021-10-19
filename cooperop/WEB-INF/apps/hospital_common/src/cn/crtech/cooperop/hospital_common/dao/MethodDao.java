package cn.crtech.cooperop.hospital_common.dao;

import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class MethodDao extends BaseDao {
	
	public static final String TABLE_NAME = "dbo.data_service_method";
	
	public int insert(Map<String, Object> params) throws Exception {
		return executeInsert(TABLE_NAME, params);
	}
	
	public int delete(Map<String, Object> params) throws Exception {
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("code", params.get("code"));
		conditions.put("data_service_code", params.get("data_service_code"));
		return executeDelete(TABLE_NAME, conditions);
	}
	
	public int update(Map<String, Object> params) throws Exception {
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("code", params.get("code"));
		conditions.put("data_service_code", params.get("data_service_code"));
		return executeUpdate(TABLE_NAME, params, conditions);
	}
	
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from " + TABLE_NAME + "(nolock) ");
		sql.append(" where 1=1 ");
		setParameter(params, "data_service_code", " and data_service_code=:data_service_code", sql);
		if (!CommonFun.isNe(params.get("key"))) {
			params.put("key", "%" + params.remove("key") + "%");
			setParameter(params, "key", 
					" and ( code like :key or name like :key or "
					+ "execute_procedure like :key or description like :key)", sql);
		}
		return executeQueryLimit(sql.toString(), params);
	}
	
	public Record get(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from " + TABLE_NAME + "(nolock) ");
		sql.append(" where code=:code"
				+ " and data_service_code=:data_service_code");
		return executeQuerySingleRecord(sql.toString(), params);
	}
}
