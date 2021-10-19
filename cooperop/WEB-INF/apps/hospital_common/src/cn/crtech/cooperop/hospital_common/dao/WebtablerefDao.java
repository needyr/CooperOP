package cn.crtech.cooperop.hospital_common.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Result;
public class WebtablerefDao extends BaseDao{

	private final static String TABLE_NAME = "dbo.data_webservice_table_ref";
	
	
	public int insert(Map<String,Object> params) throws Exception {
		return executeInsert(TABLE_NAME, params);
	}
	
	public int delete(Map<String, Object> params) throws Exception {
		Map<String, Object> conditions = new Record();
		conditions.put("data_webservice_method_code", params.get("data_webservice_method_code"));
		conditions.put("data_webservice_code", params.get("data_webservice_code"));
		conditions.put("table_name", params.get("table_name"));
		return executeDelete(TABLE_NAME, conditions);
	}

	public int update(Map<String, Object> params) throws Exception {
		Record conditions = new Record();
		conditions.put("data_webservice_method_code", params.get("data_webservice_method_code"));
		conditions.put("data_webservice_code", params.get("data_webservice_code"));
		conditions.put("table_name", params.get("table_name"));
		return executeUpdate(TABLE_NAME, params, conditions);
	}
	
	public Result query(Map<String,Object> params) throws Exception{
		StringBuffer sql = new StringBuffer();
		sql.append("select * from " + TABLE_NAME + "(nolock) ");
		sql.append(" where 1=1 ");
		//setParameter(params, "type", " and type=:type ", sql);
		setParameter(params, "data_webservice_code", " and data_webservice_code=:data_webservice_code ", sql);
		setParameter(params, "data_webservice_method_code", " and data_webservice_method_code=:data_webservice_method_code ", sql);
		setParameter(params, "_exceptColumn_", " and table_name <> :_exceptColumn_", sql);
		setParameter(params, "_exceptType_", " and type=:_exceptType_", sql);
		if (!CommonFun.isNe(params.get("key"))) {
			params.put("key", "%" + params.remove("key") + "%");
			setParameter(params, "key", " and (table_name like :key or "
					+ " parent_table_name like :key or param_name like :key) ", sql);
		}
		return executeQueryLimit(sql.toString(), params);
	}
	
	public Record get(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from " + TABLE_NAME + "(nolock) ");
		sql.append(" where table_name=:table_name and ");
		sql.append(" data_webservice_method_code=:data_webservice_method_code and ");
		sql.append(" data_webservice_code=:data_webservice_code");
		return executeQuerySingleRecord(sql.toString(), params);
	}
}
