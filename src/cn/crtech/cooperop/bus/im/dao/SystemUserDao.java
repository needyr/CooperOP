package cn.crtech.cooperop.bus.im.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;

public class SystemUserDao extends BaseDao {
	
	public final static String TABLE_NAME = "[system_user]";
	public final static String VIEW_NAME = "v_system_user";

	public Record getV(String id) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from " + VIEW_NAME + "(nolock) where id = :id ");
		Record params = new Record();
		params.put("id", id);
		return executeQuerySingleRecord(sql.toString(), params);
	}

	public Record get(String id) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from " + TABLE_NAME + "(nolock) where id = :id ");
		Record params = new Record();
		params.put("id", id);
		return executeQuerySingleRecord(sql.toString(), params);
	}

	public int changeOnlineStatus(String userid, String online_status) throws Exception {
		Record conditions = new Record();
		conditions.put("id", userid);
		Record params = new Record();
		params.put("online_status", online_status);
		params.put("online_status_change_time", "sysdate");
		return executeUpdate(TABLE_NAME, params, conditions);
	}
	
	public int insert(Map<String, Object> params) throws Exception {
		return executeInsert(TABLE_NAME, params);
	}

	public int update(Map<String, Object> params) throws Exception {
		Record conditions = new Record();
		conditions.put("id", params.remove("id"));
		return executeUpdate(TABLE_NAME, params, conditions);
	}

	public int delete(String id) throws Exception {
		Record conditions = new Record();
		conditions.put("id", id);
		return executeDelete(TABLE_NAME, conditions);
	}

}
