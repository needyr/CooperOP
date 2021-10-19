package cn.crtech.cooperop.crdc.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;

public class DJsetJGDao extends BaseDao{
	private final static String TABLE_NAME = "cr_djset_jg";
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from " + TABLE_NAME);
		sql.append(" where 1 = 1 ");
		setParameter(params, "viewid", " and viewid = :viewid ", sql);
		setParameter(params, "shezhibs", " and shezhibs = :shezhibs ", sql);
		setParameter(params, "jgid", " and jigid = :jigid ", sql);
		return executeQueryLimit(sql.toString(), params);
	}

	public Record get(String viewid, String shezhibs, String jigid) throws Exception {
		Record conditions = new Record();
		conditions.put("viewid", viewid);
		conditions.put("shezhibs", shezhibs);
		conditions.put("jigid", jigid);
		StringBuffer sql = new StringBuffer();
		sql.append("select * from " + TABLE_NAME);
		sql.append(" where viewid = :viewid and shezhibs = :shezhibs and jigid = :jigid");
		return executeQuerySingleRecord(sql.toString(), conditions);
	}	
	
	public int insert(Map<String, Object> params) throws Exception {
		return executeInsert(TABLE_NAME, params);
	}
	
	public int update(String viewid, String shezhibs, String jigid, Map<String, Object> params) throws Exception {
		Record conditions = new Record();
		conditions.put("viewid", viewid);
		conditions.put("shezhibs", shezhibs);
		conditions.put("jigid", jigid);
		params.remove("viewid");
		params.remove("shezhibs");
		params.remove("jigid");
		return executeUpdate(TABLE_NAME, params, conditions);
	}

	public int delete(String viewid, String shezhibs, String jigid) throws Exception {
		Record conditions = new Record();
		conditions.put("viewid", viewid);
		conditions.put("shezhibs", shezhibs);
		conditions.put("jigid", jigid);
		return executeDelete(TABLE_NAME, conditions);
	}
	public int deleteByDJ(String viewid) throws Exception {
		Record conditions = new Record();
		conditions.put("viewid", viewid);
		return executeDelete(TABLE_NAME, conditions);
	}
}
