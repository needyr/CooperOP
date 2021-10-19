package cn.crtech.cooperop.hospital_common.dao.trade;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class RecordDao extends BaseDao{

	public static final String TABLE_NAME = "trade_record";
	
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append(" select * from " + TABLE_NAME + " (nolock) where 1=1 ");
		if(!CommonFun.isNe(params.get("filter"))) {
			params.put("filter", "%" +params.get("filter")+ "%");
			sql.append(" and trade_code like :filter ");
		}
		if(CommonFun.isNe(params.get("sort"))) {
			params.put("sort", " create_time desc ");
		}
		return executeQueryLimit(sql.toString(), params);
	}
	
	
	public int insert(Map<String, Object> params) throws Exception {
		return executeInsert(TABLE_NAME, params);
	}
	
	public int update(Map<String, Object> params) throws Exception {
		Record r=new Record();
		r.put("trade_code", params.remove("trade_code"));
		return executeUpdate(TABLE_NAME, params, r);
	}
	
	
	public int delete(Map<String, Object> params) throws Exception {
		return executeDelete(TABLE_NAME, params);
	}
	
	public Record get(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from " + TABLE_NAME);
		sql.append(" (nolock) where  trade_code = :trade_code ");
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
}
