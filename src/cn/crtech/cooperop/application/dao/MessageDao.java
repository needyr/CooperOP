package cn.crtech.cooperop.application.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class MessageDao extends BaseDao {
	
	public Result session(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select  *                                           ");
		sql.append(" from    v_message_session(nolock) vms                                    ");
		sql.append(" where   read_user_id = :system_user_id                       ");
		params.put("system_user_id", user().getId());
		params.put("sort", "last_send_time desc, last_id desc");
		long start = CommonFun.isNe(params.get("start")) ? 1 : Long.parseLong((String)params.get("start"));
	    int limit = CommonFun.isNe(params.get("limit")) ? 0 : Integer.parseInt((String)params.get("limit"));
	    Result rs = executeQueryLimit(sql.toString(), params, start, limit);
	    return rs;
	}
	
	public Result listnew(Map<String, Object> params) throws Exception {
        params.put("system_user_id", user().getId());
		long start = CommonFun.isNe(params.get("start")) ? 1 : Long.parseLong((String)params.get("start"));
		int limit = CommonFun.isNe(params.get("limit")) ? 0 : Integer.parseInt((String)params.get("limit"));
		StringBuffer sql = new StringBuffer();
		if(CommonFun.isNe(params.get("queryall"))){
			if (limit > 0) {
				sql.append(" select  count(1) as num                                                       ");
				sql.append(" from    v_message(nolock) e                                                    ");
				sql.append(" where   e.read_user_id = :system_user_id                           ");
				sql.append("   and   e.target = :target                           ");
				sql.append("   and   e.session_id = :send_to                           ");
				sql.append("   and   e.read_time is null                           ");
				Record record = executeQuerySingleRecord(sql.toString(), params);
				if (record != null) {
					limit += record.getInt("num");
				}
			}
		}
		sql = new StringBuffer();
		sql.append(" select  e.*                                                          ");
		sql.append(" from    v_message(nolock) e                                                    ");
		sql.append(" where   e.read_user_id = :system_user_id                           ");
		sql.append("   and   e.target = :target                           ");
		sql.append("   and   e.session_id = :send_to                           ");
		setParameter(params, "noread", " and e.read_time is null ", sql);
		if (!CommonFun.isNe(params.get("starttime"))) {
			sql.append("   and   e.send_time_label >= :starttime                           ");
		}
		if (!CommonFun.isNe(params.get("endtime"))) {
			sql.append("   and   e.send_time_label <= :endtime                           ");
		}
		if (!CommonFun.isNe(params.get("contents"))) {
			params.put("contents", "%"+params.get("contents")+"%");
			sql.append("   and   e.content like :contents                           ");
		}
		if (CommonFun.isNe(params.get("sort"))) {
			params.put("sort", "send_time desc, id desc");
		}
	    Result rs = executeQueryLimit(sql.toString(), params, start, limit);
	    return rs;
	}
	
	public void read(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" insert into message_read(message_id, system_user_id, read_time)          ");
		sql.append(" select  e.id, :system_user_id, getDate()                             ");
		sql.append(" from    v_message(nolock) e                                                    ");
		sql.append(" where   e.read_user_id = :system_user_id                           ");
		sql.append("   and   e.read_time is null                           ");
		sql.append("   and   e.target = :target                           ");
		sql.append("   and   e.session_id = :send_to                           ");
		setParameter(params, "send_user_id", " and e.system_user_id = :send_user_id ", sql);
        params.put("system_user_id", user().getId());
	    executeUpdate(sql.toString(), params);
	}

	public Result history(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select  e.*                                                          ");
		sql.append(" from    v_message(nolock) e                                                    ");
		sql.append(" where   e.read_user_id = :system_user_id                           ");
		setParameter(params, "send_user_id", " and e.system_user_id = :send_user_id ", sql);
		setParameter(params, "target", " and e.target = :target ", sql);
		setParameter(params, "send_to", " and e.send_to = :send_to ", sql);
        params.put("system_user_id", user().getId());
		long start = CommonFun.isNe(params.get("start")) ? 1 : Long.parseLong((String)params.get("start"));
	    int limit = CommonFun.isNe(params.get("limit")) ? 0 : Integer.parseInt((String)params.get("limit"));
	    Result rs = executeQueryLimit(sql.toString(), params, start, limit);
	    return rs;
	}
	
	public int messagenum(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select  count(1) as num                                                       ");
		sql.append(" from    v_message(nolock) e                                                    ");
		sql.append(" where   e.read_user_id = :system_user_id                           ");
		sql.append("   and   e.read_time is null                           ");
		params.put("system_user_id", user().getId());
	    Record record = executeQuerySingleRecord(sql.toString(), params);
	    if (record == null) return 0;
	    return record.getInt("num");
	}

	public int insert(Map<String, Object> params) throws Exception {
		params.put("system_user_id", user().getId());
		params.put("send_time", "sysdate");
	    executeInsert("message", params);
	    return getSeqVal("message");
	}
	
	public Record get(int id) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select  e.*                                                          ");
		sql.append(" from    v_message(nolock) e                                                    ");
		sql.append(" where   e.read_user_id = :system_user_id                           ");
		sql.append("   and   e.id = :id                           ");
        Record params = new Record();
		params.put("system_user_id", user().getId());
		params.put("id", id);
		return executeQuerySingleRecord(sql.toString(), params);
	}

}
