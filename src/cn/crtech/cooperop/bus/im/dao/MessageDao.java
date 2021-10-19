package cn.crtech.cooperop.bus.im.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class MessageDao extends BaseDao {
	
	public final static String TABLE_NAME = "message";
	public final static String TABLE_NAME_READ = "message_read";
	public final static String VIEW_NAME = "v_message";
	public final static String VIEW_NAME_SESSION = "v_message_session";

	public int insert(Map<String, Object> params) throws Exception {
		String id = getSeqNextVal("message");
		params.put("id", id);
		executeInsert(TABLE_NAME, params);
		return Integer.parseInt(id);
	}

	public Result getSessions(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from " + VIEW_NAME + "(nolock) where 1 = 1 ");
		setParameter(params, "system_user_id", " and system_user_id = :system_user_id ", sql);
		return executeQueryLimit(sql.toString(), params);
	}
	
	public Result querySessions(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select  min(send_time) as last_send_time                                                       ");
		sql.append(" from    " + VIEW_NAME + "(nolock) e                                                    ");
		sql.append(" where   e.read_time is null                           ");
		setParameter(params, "system_user_id", " and read_user_id = :system_user_id ", sql);
		Record param = new Record(params);
		param.remove("sort");
		Record record = executeQuerySingleRecord(sql.toString(), param);
		if (record != null && record.getDate("last_send_time") != null) {
			params.put("last_send_time", record.get("last_send_time"));
			sql = new StringBuffer();
			sql.append(" select  count(1) as num                                                       ");
			sql.append(" from    " + VIEW_NAME_SESSION + "(nolock) e                                                    ");
			sql.append(" where   1 = 1                           ");
			setParameter(params, "system_user_id", " and read_user_id = :system_user_id ", sql);
			setParameter(params, "last_send_time", " and last_send_time >= :last_send_time ", sql);
			param = new Record(params);
			param.remove("sort");
			record = executeQuerySingleRecord(sql.toString(), param);
			params.put("limit", record.getInt("num") > (int)params.get("limit") ? -1 : params.get("limit"));
		}
		sql = new StringBuffer();
		sql.append(" select uc.*,   ");
		sql.append(" ms.target,ms.session_id,ms.session_name,ms.read_user_id,ms.last_content,ms.last_send_time,   ");
		sql.append(" ms.last_content_type,ms.last_content_mime_type,ms.last_send_user_name,ms.last_id,ms.noreadnum ");
		sql.append("  from " + VIEW_NAME_SESSION + "(nolock) ms ");
		sql.append("     left join " + ContactorDao.VIEW_NAME + "(nolock) uc ");
		sql.append("            on uc.type = ms.target ");
		sql.append("           and uc.id = ms.session_id ");
		sql.append("           and (uc.system_user_id = ms.read_user_id ");
		sql.append("                or isnull(uc.system_user_id, '') = '') ");
		sql.append("         where 1 = 1 ");
		setParameter(params, "system_user_id", " and read_user_id = :system_user_id ", sql);
		return executeQueryLimit(sql.toString(), params);
	}

	public Result queryMessage(Map<String, Object> params) throws Exception {
		if(!CommonFun.isNe(params.get("noread"))){
			StringBuffer sql = new StringBuffer();
			sql.append(" select  min(id) as min_id, count(id) as num                                                       ");
			sql.append(" from    " + VIEW_NAME + "(nolock) e                                                    ");
			sql.append(" where   e.read_time is null                           ");
			setParameter(params, "system_user_id", " and read_user_id = :system_user_id ", sql);
			setParameter(params, "target", " and target = :target ", sql);
			setParameter(params, "session_id", " and session_id = :session_id ", sql);
			Record param = new Record(params);
			param.remove("sort");
			Record record = executeQuerySingleRecord(sql.toString(), param);
			if (record != null && record.getInt("min_id") > 0) {
				params.put("min_id", record.get("min_id"));
				sql = new StringBuffer();
				sql.append(" select  count(1) as num                                                       ");
				sql.append(" from    " + VIEW_NAME + "(nolock) e                                                    ");
				sql.append(" where   e.read_time is null                           ");
				setParameter(params, "system_user_id", " and read_user_id = :system_user_id ", sql);
				setParameter(params, "target", " and target = :target ", sql);
				setParameter(params, "session_id", " and session_id = :session_id ", sql);
				setParameter(params, "min_id", " and id >= :min_id ", sql);
				param = new Record(params);
				param.remove("sort");
				record = executeQuerySingleRecord(sql.toString(), param);
				params.put("limit", record.getInt("num") > (int)params.get("limit") ? -1 : params.get("limit"));
			}
		}
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from " + VIEW_NAME + "(nolock) where 1 = 1 ");
		setParameter(params, "system_user_id", " and read_user_id = :system_user_id ", sql);
		setParameter(params, "target", " and target = :target ", sql);
		setParameter(params, "session_id", " and session_id = :session_id ", sql);
		setParameter(params, "id", " and id = :id ", sql);
		return executeQueryLimit(sql.toString(), params);
	}
	
	public int read(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" insert into " + TABLE_NAME_READ + "(message_id, system_user_id, read_time)          ");
		sql.append(" select  e.id, e.read_user_id, getDate()                             ");
		sql.append(" from    " + VIEW_NAME + "(nolock) e                                                    ");
		sql.append(" where   e.read_time is null                            ");
		setParameter(params, "system_user_id", " and read_user_id = :system_user_id ", sql);
		setParameter(params, "target", " and target = :target ", sql);
		setParameter(params, "session_id", " and session_id = :session_id ", sql);
		setParameter(params, "id", " and id = :id ", sql);
	    return executeUpdate(sql.toString(), params);
	}

}
