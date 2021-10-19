package cn.crtech.cooperop.hospital_common.dao.syscustomreview;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;

public class SysdrugmxDao extends BaseDao{

	public static final String TABLE_NAME = "dict_sys_drug_mx";
	
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("select * from " + TABLE_NAME);
		sql.append(" (nolock) where sys_p_key = :drug_code");
		params.put("sort", " xmid asc ");
		return executeQueryLimit(sql.toString(), params);
	}
	
	public int insert(Map<String, Object> params) throws Exception {
		return executeInsert(TABLE_NAME, params);
	}
	
	public int delete(Map<String, Object> params) throws Exception {
		return executeDelete(TABLE_NAME, params);
	}
	
	public Record get(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from " + TABLE_NAME);
		sql.append(" (nolock) where drug_code = :drug_code");
		sql.append(" and xmid = :xmid");
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	public int update(Map<String, Object> params) throws Exception {
		Record r=new Record();
		r.put("drug_code", params.remove("drug_code"));
		r.put("xmid", params.remove("xmid"));
		return executeUpdate(TABLE_NAME, params, r);
	}
}
