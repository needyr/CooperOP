package cn.crtech.cooperop.bus.im.dao;

import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class GroupDao extends BaseDao {

	public final static String TABLE_NAME = "system_group";
	public final static String TABLE_NAME_GROUP_USER = "system_group_user";

	public Record get(String id) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from ");
		sql.append(TABLE_NAME + "(nolock) ");
		sql.append("where id = :id");
		Record params = new Record();
		params.put("id", id);
		return executeQuerySingleRecord(sql.toString(), params);
	}

	public int insert(Map<String, Object> params) throws Exception {
		params.remove("id");
		params.put("create_time", "sysdate");
		executeInsert(TABLE_NAME, params);
		return getSeqVal(TABLE_NAME);
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
	public void deleteGroupUser(String gid) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("gid", gid);
		execute("delete from "+TABLE_NAME_GROUP_USER+" where system_user_group_id=:gid ", params);
	}
	public void saveGroupUser(Map<String, Object> params) throws Exception {
		executeInsert(TABLE_NAME_GROUP_USER, params);
	}
}
