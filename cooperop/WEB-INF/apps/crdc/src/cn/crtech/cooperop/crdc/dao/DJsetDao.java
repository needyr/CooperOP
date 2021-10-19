package cn.crtech.cooperop.crdc.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;

public class DJsetDao extends BaseDao{
	private final static String TABLE_NAME = "cr_djset";
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from " + TABLE_NAME);
		sql.append(" where 1 = 1 ");
		setParameter(params, "viewid", " and viewid = :viewid ", sql);
		setParameter(params, "shezhibs", " and shezhibs = :shezhibs ", sql);
		return executeQueryLimit(sql.toString(), params);
	}

	public Record get(String viewid, String shezhibs) throws Exception {
		Record conditions = new Record();
		conditions.put("viewid", viewid);
		conditions.put("shezhibs", shezhibs);
		StringBuffer sql = new StringBuffer();
		sql.append("select * from " + TABLE_NAME);
		sql.append(" where viewid = :viewid and shezhibs = :shezhibs");
		return executeQuerySingleRecord(sql.toString(), conditions);
	}	
	
	public int insert(Map<String, Object> params) throws Exception {
		return executeInsert(TABLE_NAME, params);
	}
	
	public int update(String viewid, String shezhibs, Map<String, Object> params) throws Exception {
		Record conditions = new Record();
		conditions.put("viewid", viewid);
		conditions.put("shezhibs", shezhibs);
		params.remove("shezhibs");
		return executeUpdate(TABLE_NAME, params, conditions);
	}

	public int delete(String viewid, String shezhibs) throws Exception {
		Record conditions = new Record();
		conditions.put("viewid", viewid);
		conditions.put("shezhibs", shezhibs);
		return executeDelete(TABLE_NAME, conditions);
	}
	public int deleteByDJ(String viewid) throws Exception {
		Record conditions = new Record();
		conditions.put("viewid", viewid);
		return executeDelete(TABLE_NAME, conditions);
	}
}
