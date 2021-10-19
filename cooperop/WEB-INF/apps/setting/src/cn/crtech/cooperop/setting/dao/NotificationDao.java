package cn.crtech.cooperop.setting.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class NotificationDao extends BaseDao {
	
	private static final int PUBLISH_PUBLISHED = 1;
	private static final int PUBLISH_UNPUBLISHED = 0;
	private static final int DELETED = 1;
	private static final String TABLE_NAME = "notice";
	private static final String TABLE_SENDTO_NAME = "notice_sendto";
	
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select  n.*, vsuh.name as modifier_name          ");
		sql.append("from    notice n                 ");
		sql.append(" left join v_system_user_hr as vsuh on n.modifier=vsuh.id ");
		sql.append("where   is_delete = 0  ");
		if (!CommonFun.isNe(params.get("pdate"))) {
			sql.append(" and isnull(convert(varchar(10), pdate, 120), :pdate) >= :pdate ");
		}
		if (!CommonFun.isNe(params.get("edate"))) {
			sql.append(" and isnull(convert(varchar(10), edate, 120), :edate) <= :edate ");
		}
		if (!CommonFun.isNe(params.get("published"))) {
			sql.append(" and published in (:published) ");
		}
		if (!CommonFun.isNe(params.get("notification_type"))) {
			sql.append(" and notification_type = :notification_type ");
		}
		if (!CommonFun.isNe(params.get("filter"))) {
			sql.append(" and upper(subject + '|' + convert(varchar(max), content) + '|' + author) like '%' + upper(:filter) + '%' ");
		}
		long start = CommonFun.isNe(params.get("start")) ? 1 : Long.parseLong((String)params.get("start"));
	    int limit = CommonFun.isNe(params.get("limit")) ? 0 : Integer.parseInt((String)params.get("limit"));
	    Result rs = executeQueryLimitTotal(sql.toString(), params, (String)params.remove("totals"), start, limit);
	    return rs;
	}
	public Result queryReader(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select  n.*           ");
		sql.append("from    notice_read n                 ");
		sql.append("where   is_delete = 0  ");
		long start = CommonFun.isNe(params.get("start")) ? 1 : Long.parseLong((String)params.get("start"));
	    int limit = CommonFun.isNe(params.get("limit")) ? 0 : Integer.parseInt((String)params.get("limit"));
	    Result rs = executeQueryLimitTotal(sql.toString(), params, (String)params.remove("totals"), start, limit);
	    return rs;
	}
	public Result querySendto(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select  t.* ,c.name as cc_name          ");
		sql.append("from    "+TABLE_SENDTO_NAME+" t                 ");
		sql.append(" left join dbo.v_user_contacter c on c.id=t.send_to and t.target=c.type                 ");
		sql.append("where   notice_id = :notice_id  ");
	    Result rs = executeQuery(sql.toString(), params);
	    return rs;
	}
	public Record get(int id) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select  n.*           ");
		sql.append("from    notice n                 ");
		sql.append("where   is_delete = 0  ");
		sql.append("  and   id = :id  ");
	    Record params = new Record();
	    params.put("id", id);
		return executeQuerySingleRecord(sql.toString(), params );
	}
	public int insert(Map<String, Object> params) throws Exception {
		params.remove("id");
		params.put("modifier", user().getId());
		params.put("modify_time", "sysdate");
		executeInsert(TABLE_NAME, params);
		return getSeqVal(TABLE_NAME);
	}
	public int update(int id, Map<String, Object> params) throws Exception {
		Record conditions = new Record();
		conditions.put("id", id);
		params.put("modifier", user().getId());
		params.put("modify_time", "sysdate");
	    return executeUpdate(TABLE_NAME, params, conditions);
	}
	public int delete(int id) throws Exception {
		Record conditions = new Record();
		conditions.put("id", id);
		Record params = new Record();
		params.put("modifier", user().getId());
		params.put("modify_time", "sysdate");
		params.put("is_delete", DELETED);
	    return executeUpdate(TABLE_NAME, params, conditions);
	}
	public int publish(int id) throws Exception {
		Record conditions = new Record();
		conditions.put("id", id);
		Record params = new Record();
		params.put("modifier", user().getId());
		params.put("modify_time", "sysdate");
		params.put("published", PUBLISH_PUBLISHED);
	    return executeUpdate(TABLE_NAME, params, conditions);
	}
	public int unpublish(int id) throws Exception {
		Record conditions = new Record();
		conditions.put("id", id);
		Record params = new Record();
		params.put("modifier", user().getId());
		params.put("modify_time", "sysdate");
		params.put("published", PUBLISH_UNPUBLISHED);
	    return executeUpdate(TABLE_NAME, params, conditions);
	}
	public int deleteSendto(int id) throws Exception {
		Record conditions = new Record();
		conditions.put("notice_id", id);
	    return executeDelete(TABLE_SENDTO_NAME, conditions);
	}
	public int insertSendto(int id, Map<String, Object> params) throws Exception {
		params.put("notice_id", id);
	    return executeInsert(TABLE_SENDTO_NAME, params);
	}
}
