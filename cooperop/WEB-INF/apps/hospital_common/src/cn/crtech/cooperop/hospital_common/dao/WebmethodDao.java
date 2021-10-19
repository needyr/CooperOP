package cn.crtech.cooperop.hospital_common.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
public class WebmethodDao extends BaseDao{

	public final static String TABLE_NAME = "dbo.data_webservice_method";
	
	
	public int insert(Map<String,Object> params) throws Exception {
		return executeInsert(TABLE_NAME, params);
	}
	
	public int delete(Map<String, Object> params) throws Exception {
		Map<String, Object> conditions = new Record();
		conditions.put("code", params.get("code"));
		conditions.put("data_webservice_code", params.get("data_webservice_code"));
		return executeDelete(TABLE_NAME, conditions);
	}

	public int update(Map<String, Object> params) throws Exception {
		Record conditions = new Record();
		conditions.put("code", params.get("code"));
		conditions.put("data_webservice_code", params.get("data_webservice_code"));
		return executeUpdate(TABLE_NAME, params, conditions);
	}
	
	public Result query(Map<String,Object> params) throws Exception{
		StringBuffer sql = new StringBuffer();
		sql.append("select * from " + TABLE_NAME + "(nolock) ");
		sql.append(" where 1=1 ");
		setParameter(params, "data_webservice_code", " and data_webservice_code=:data_webservice_code ", sql);
		if (!CommonFun.isNe(params.get("key"))) {
			params.put("key", "%" + params.remove("key") + "%");
			setParameter(params, "key", " and (code like :key or "
					+ " name like :key or description like :key "
					+ " or execute_procedure like :key) ", sql);
		}
		setParameter(params, "filter", " and upper(code) like '%' + upper(:filter) + '%' ", sql);
		setParameter(params, "state", " and state=:state", sql);
		return executeQueryLimit(sql.toString(), params);
	}
	
	public Record get(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from " + TABLE_NAME + "(nolock) ");
		sql.append(" where code=:code and ");
		sql.append(" data_webservice_code=:data_webservice_code");
		return executeQuerySingleRecord(sql.toString(), params);
	}
}
