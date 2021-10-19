package cn.crtech.cooperop.oa.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;

public class NoticeReadDao extends BaseDao{
	private final static String TABLE_NAME = "notice_read";
	
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * ");
		sql.append(" from "+TABLE_NAME+"(nolock) t ");
		sql.append(" where state!=-1 ");
		setParameter(params, "id", " and t.id=:id ", sql);
		setParameter(params, "system_user_id", " and t.system_user_id=:system_user_id ", sql);
		return executeQueryLimit(sql.toString(), params);
	}
	
	public Record get(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select top 1 t.* ");
		sql.append(" from "+TABLE_NAME+"(nolock) t ");
		sql.append(" where 1=1 ");
		setParameter(params, "notice_id", " and t.notice_id=:notice_id", sql);
		setParameter(params, "system_user_id", " and t.system_user_id=:system_user_id ", sql);
		return executeQuerySingleRecord(sql.toString(), params);
	}

	public int insert(Map<String, Object> params) throws Exception {
		return executeInsert(TABLE_NAME, params);
	}
	public int update(Map<String, Object> params, Map<String, Object> condition) throws Exception {
		return executeUpdate(TABLE_NAME, params, condition);
	}
	
	public int delete(Map<String, Object> condition) throws Exception {
		return executeDelete(TABLE_NAME, condition);
	}
}
