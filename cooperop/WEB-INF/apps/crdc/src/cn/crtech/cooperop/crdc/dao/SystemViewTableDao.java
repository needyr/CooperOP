package cn.crtech.cooperop.crdc.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;

public class SystemViewTableDao extends BaseDao{
	private final static String TABLE_NAME = "system_view_table";
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from " + TABLE_NAME);
		sql.append(" where 1 = 1 ");
		setParameter(params, "viewtype", " and viewtype = :type ", sql);
		setParameter(params, "viewid", " and viewid = :viewid ", sql);
		setParameter(params, "flag", " and flag = :flag ", sql);
		setParameter(params, "tableid", " and tableid = :tableid ", sql);
		setParameter(params, "system_product_code", " and system_product_code = :system_product_code ", sql);
		return executeQueryLimit(sql.toString(), params);
	}

	public Record get(String type, String flag, String viewid, String tableid, String system_product_code) throws Exception {
		Record conditions = new Record();
		conditions.put("viewtype", type);
		conditions.put("flag", flag);
		conditions.put("viewid", viewid);
		conditions.put("tableid", tableid);
		conditions.put("system_product_code", system_product_code);
		StringBuffer sql = new StringBuffer();
		sql.append("select * from " + TABLE_NAME);
		sql.append(" where flag=:flag and viewid = :viewid and tableid = :tableid and system_product_code=:system_product_code");
		return executeQuerySingleRecord(sql.toString(), conditions);
	}	
	
	public int insert(Map<String, Object> params) throws Exception {
		return executeInsert(TABLE_NAME, params);
	}
	
	public int update(String type, String flag, String viewid, String tableid, String system_product_code, Map<String, Object> params) throws Exception {
		Record conditions = new Record();
		conditions.put("viewtype", type);
		conditions.put("flag", flag);
		conditions.put("viewid", viewid);
		conditions.put("tableid", tableid);
		conditions.put("system_product_code", system_product_code);
		params.remove("tableid");
		return executeUpdate(TABLE_NAME, params, conditions);
	}

	public int delete(String type, String flag, String viewid, String tableid) throws Exception {
		Record conditions = new Record();
		conditions.put("viewtype", type);
		conditions.put("flag", flag);
		conditions.put("viewid", viewid);
		conditions.put("tableid", tableid);
		return executeDelete(TABLE_NAME, conditions);
	}
	public int deleteByDJ(String type, String flag,String viewid, String system_product_code) throws Exception {
		Record conditions = new Record();
		conditions.put("viewtype", type);
		conditions.put("viewid", viewid);
		conditions.put("flag", flag);
		conditions.put("system_product_code", system_product_code);
		return executeDelete(TABLE_NAME, conditions);
	}
}
