package cn.crtech.cooperop.hospital_common.dao.trade;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class DBDao extends BaseDao{

	public static final String TABLE_NAME = "trade_db";
	
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append(" select * from " + TABLE_NAME + " (nolock) where 1=1 ");
		if(!CommonFun.isNe(params.get("filter"))) {
			params.put("filter", "%" +params.get("filter")+ "%");
			sql.append(" and ( db_code like :filter or db_name like :filter or db_type like :filter or db_config_name like :filter)");
		}
		if(!CommonFun.isNe(params.get("state"))) {
			sql.append(" and state = :state");
		}
		return executeQueryLimit(sql.toString(), params);
	}
	
	
	public int insert(Map<String, Object> params) throws Exception {
		return executeInsert(TABLE_NAME, params);
	}
	
	public int update(Map<String, Object> params) throws Exception {
		Record r=new Record();
		r.put("db_code", params.remove("db_code"));
		return executeUpdate(TABLE_NAME, params, r);
	}
	
	
	public int delete(Map<String, Object> params) throws Exception {
		return executeDelete(TABLE_NAME, params);
	}
	
	public Record get(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from " + TABLE_NAME);
		sql.append(" (nolock) where  db_code = :db_code ");
		return executeQuerySingleRecord(sql.toString(), params);
	}
}
