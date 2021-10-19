package cn.crtech.cooperop.application.dao;

import java.util.Map;
import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;

public class SystemUserStapleTabsDao extends BaseDao {
	private final static String TABLE_NAME = "system_user_staple_tabs";
	public Result query(Map<String, Object> params) throws Exception {
		params.put("system_user_id", user().getId());
		StringBuffer sql = new StringBuffer();
		sql.append("select * from " + TABLE_NAME + " where system_user_id=:system_user_id ");
		return executeQuery(sql.toString(), params);
	}
	
	public Record get(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		params.put("system_user_id", user().getId());
		sql.append("select * from " + TABLE_NAME + " where system_user_id=:system_user_id and pageid=:pageid");
	    return executeQuerySingleRecord(sql.toString(), params);
	}
	
	public void insert(Map<String, Object> params) throws Exception {
		params.put("system_user_id", user().getId());
		executeInsert(TABLE_NAME, params);
	}
	public void delete(Map<String, Object> params) throws Exception {
		params.put("system_user_id", user().getId());
		executeDelete(TABLE_NAME, params);
	}
}
