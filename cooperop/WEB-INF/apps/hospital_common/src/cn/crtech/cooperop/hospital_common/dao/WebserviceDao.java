package cn.crtech.cooperop.hospital_common.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
public class WebserviceDao extends BaseDao{

	public final static String TABLE_NAME = "dbo.data_webservice";
	
	
	public int insert(Map<String,Object> params) throws Exception {
		return executeInsert(TABLE_NAME, params);
	}
	
	public int delete(Map<String, Object> params) throws Exception {
		Map<String, Object> conditions = new Record();
		conditions.put("code", params.get("code"));
		return executeDelete(TABLE_NAME, conditions);
	}

	public int update(Map<String, Object> params) throws Exception {
		Record conditions = new Record();
		conditions.put("code", params.get("code"));
		return executeUpdate(TABLE_NAME, params, conditions);
	}
	
	public Result query(Map<String,Object> params) throws Exception{
		StringBuffer sql = new StringBuffer();
		sql.append("select code, name, type, definition as definition_1, header as header_1 from " + TABLE_NAME + "(nolock) ");
		sql.append(" where 1=1 ");
		setParameter(params, "type", " and type=:type ", sql);
		if (!CommonFun.isNe(params.get("key"))) {
			params.put("key", "%" + params.remove("key") + "%");
			setParameter(params, "key", " and (code like :key or "
					+ " name like :key ) ", sql);
		}
		return executeQueryLimit(sql.toString(), params);
	}
	
	public Record get(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select code, name, type, definition as definition_1, header as header_1 from " + TABLE_NAME + "(nolock) ");
		sql.append(" where code=:code");
		return executeQuerySingleRecord(sql.toString(), params);
	}
}
