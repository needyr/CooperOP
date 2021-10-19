package cn.crtech.cooperop.application.dao;

import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class EmailDao extends BaseDao {
	
	public Result queryMineFolder(boolean withoutsystemfolder) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select  ef.* ,                                                   ");
		sql.append("         ( select    count(1)                                     ");
		sql.append("           from      v_email(nolock) er                            ");
		sql.append("           where     read_time is null                            ");
		sql.append("                     and ((er.email_folder_id = ef.id               ");
		sql.append("                           and er.is_recycle = 0)              ");
		sql.append("                          or (ef.id = 4               ");
		sql.append("                              and er.is_recycle = 1))             ");
		sql.append("                     and er.system_user_id = :system_user_id      ");
		sql.append("         ) as noreadnum,                                           ");
		sql.append("         ( select    count(1)                                     ");
		sql.append("           from      v_email(nolock) er                            ");
		sql.append("           where     ((er.email_folder_id = ef.id               ");
		sql.append("                           and er.is_recycle = 0)              ");
		sql.append("                          or (ef.id = 4               ");
		sql.append("                              and er.is_recycle = 1))               ");
		sql.append("                     and er.system_user_id = :system_user_id      ");
		sql.append("         ) as email_num                                           ");
		sql.append(" from    email_folder(nolock) ef                                      ");
		sql.append(" where   ( system_user_id = :system_user_id                       ");
		if (!withoutsystemfolder) {
			sql.append("           or is_default = 1                                      ");
		}
		sql.append("         )                                                        ");
		sql.append(" order by order_nos                                               ");
		Record params = new Record();
		params.put("system_user_id", user().getId());
		return executeQuery(sql.toString(), params);
	}
	
	public Result queryMine(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select  e.*                                                          ");
		sql.append(" from    v_email(nolock) e                                                    ");
		sql.append(" where   e.system_user_id = :system_user_id                           ");
        params.put("system_user_id", user().getId());
        if ("3".equals(params.get("email_folder_id"))) {  //草稿箱
        	sql.append("     and e.send_time is null                                      ");
        } else if ("4".equals(params.get("email_folder_id"))) {   //废件箱
        	sql.append("     and e.is_recycle = 1                                        ");
        } else if (!CommonFun.isNe(params.get("email_folder_id"))) {
        	sql.append("     and e.email_folder_id = :email_folder_id  and e.is_recycle = 0                   ");
        }
        setParameter(params, "subject", " and upper(subject) like '%' + :subject + '%' ", sql);
        setParameter(params, "send_user_name", " and upper(send_user_name) like '%' + :send_user_name + '%' ", sql);
        setParameter(params, "to_name", " and upper(to_name) like '%' + :to_name + '%' ", sql);
		long start = CommonFun.isNe(params.get("start")) ? 1 : Long.parseLong((String)params.get("start"));
	    int limit = CommonFun.isNe(params.get("limit")) ? 0 : Integer.parseInt((String)params.get("limit"));
	    Result rs = executeQueryLimitTotal(sql.toString(), params, (String)params.remove("totals"), start, limit);
	    return rs;
	}
	
	public Record getMine(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select  e.*                                                          ");
		sql.append(" from    v_email(nolock) e                                                    ");
		sql.append(" where   e.system_user_id = :system_user_id                           ");
		sql.append("   AND   e.id = :id                                                   ");
        if ("3".equals(params.get("email_folder_id"))) {  //草稿箱
        	sql.append("     and e.send_time is null                                      ");
        } else if ("4".equals(params.get("email_folder_id"))) {   //废件箱
        	sql.append("     and e.is_recycle = 1                                        ");
        } else if (!CommonFun.isNe(params.get("email_folder_id"))) {
        	sql.append("     and e.email_folder_id = :email_folder_id and e.is_recycle = 0                    ");
        }
		params.put("system_user_id", user().getId());
	    return executeQuerySingleRecord(sql.toString(), params);
	}
	public int queryEmailCount(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select    count(1) as num                                    ");
		sql.append(" from      v_email(nolock) er                            ");
		sql.append(" where     read_time is null                            ");
		sql.append("           and er.system_user_id = :system_user_id      ");
    	sql.append("           and er.is_recycle != 1                                        ");
		params.put("system_user_id", user().getId());
	    Record record = executeQuerySingleRecord(sql.toString(), params);
	    if (record == null) return 0;
	    return record.getInt("num");
	}

	public void saveRead(String[] email_ids) throws Exception {
		Record params = new Record();
		params.put("read_time", "sysdate");
		Record conditions = new Record();
		conditions.put("email_id", email_ids);
		conditions.put("system_user_id", user().getId());
	    executeUpdate("email_read", params, conditions);
	}

	public void unRead(String[] email_ids) throws Exception {
		Record params = new Record();
		params.put("read_time", null);
		Record conditions = new Record();
		conditions.put("email_id", email_ids);
		conditions.put("system_user_id", user().getId());
	    executeUpdate("email_read", params, conditions);
	}

	public int insert(Map<String, Object> params) throws Exception {
		if("xt".equals(params.get("system_user_id"))){
			params.put("system_user_id", "CRY0000root");
		}else{
			params.put("system_user_id", user().getId());
		}
	    executeInsert("email", params);
	    return getSeqVal("email");
	}

	public void update(int email_id, Map<String, Object> params) throws Exception {
		Record parameters = new Record(params);
		parameters.remove("id");
		parameters.remove("system_user_id");
		Record conditions = new Record();
		conditions.put("id", email_id);
		conditions.put("system_user_id", user().getId());
	    executeUpdate("email", parameters, conditions);
	}

	public void cancel(int email_id) throws Exception {
		Record parameters = new Record();
		parameters.put("is_cancel", "1");
		parameters.put("cancel_time", "sysdate");
		Record conditions = new Record();
		conditions.put("id", email_id);
		conditions.put("system_user_id", user().getId());
	    executeUpdate("email", parameters, conditions);
	}

	public void changeFolder(String[] email_ids, int new_folder_id, boolean isfrom) throws Exception {
		Record parameters = new Record();
		parameters.put("email_folder_id", new_folder_id);
		Record conditions = new Record();
		conditions.put("system_user_id", user().getId());
		if (isfrom) {
			conditions.put("id", email_ids);
			executeUpdate("email", parameters, conditions);
		} else {
			conditions.put("email_id", email_ids);
			executeUpdate("email_read", parameters, conditions);
		}
	}

	public void recycle(String[] email_ids, boolean isfrom) throws Exception {
		Record parameters = new Record();
		parameters.put("is_recycle", 1);
		Record conditions = new Record();
		conditions.put("system_user_id", user().getId());
		if (isfrom) {
			conditions.put("id", email_ids);
			executeUpdate("email", parameters, conditions);
		} else {
			conditions.put("email_id", email_ids);
			executeUpdate("email_read", parameters, conditions);
		}
	}

	public void restore(String[] email_ids, boolean isfrom) throws Exception {
		Record parameters = new Record();
		parameters.put("is_recycle", 0);
		Record conditions = new Record();
		conditions.put("system_user_id", user().getId());
		if (isfrom) {
			conditions.put("id", email_ids);
			executeUpdate("email", parameters, conditions);
		} else {
			conditions.put("email_id", email_ids);
			executeUpdate("email_read", parameters, conditions);
		}
	}

	public void delete(String[] email_ids, boolean isfrom) throws Exception {
		Record parameters = new Record();
		parameters.put("is_delete", 1);
		Record conditions = new Record();
		conditions.put("system_user_id", user().getId());
		if (isfrom) {
			conditions.put("id", email_ids);
			executeUpdate("email", parameters, conditions);
		} else {
			conditions.put("email_id", email_ids);
			executeUpdate("email_read", parameters, conditions);
		}
	}

	public void updateSendTo(int email_id, List<Map<String, Object>> sendlist) throws Exception {
		Record conditions = new Record();
		conditions.put("email_id", email_id);
		executeDelete("email_sendto", conditions);
		for (Map<String, Object> params : sendlist) {
			Record parameters = new Record(params);
			parameters.put("email_id", email_id);
			executeInsert("email_sendto", parameters);
		}
	}

	public void send(int email_id) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" insert into email_read (email_id, type, email_folder_id, system_user_id) ");
		sql.append(" select email_id, type, email_folder_id, system_user_id from v_email_sendtoread ");
		sql.append("  where email_id = :email_id ");
		Record conditions = new Record();
		conditions.put("email_id", email_id);
		executeUpdate(sql.toString(), conditions);
	}
	
	public Record queryOut(int email_id) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from v_email_sendout(nolock) ");
		sql.append("  where id = :email_id ");
		Record conditions = new Record();
		conditions.put("email_id", email_id);
		return executeQuerySingleRecord(sql.toString(), conditions);
	}

	public int insertFolder(Map<String, Object> params) throws Exception {
		params.put("system_user_id", user().getId());
		params.put("is_default", 0);
	    executeInsert("email_folder", params);
	    return getSeqVal("email_folder");
	}

	public void updateFolder(int folder_id, Map<String, Object> params) throws Exception {
		Record parameters = new Record(params);
		parameters.remove("id");
		parameters.remove("system_user_id");
		Record conditions = new Record();
		conditions.put("id", folder_id);
		conditions.put("system_user_id", user().getId());
	    executeUpdate("email_folder", parameters, conditions);
	}

	public void deleteFolder(int folder_id) throws Exception {
		Record conditions = new Record();
		conditions.put("email_folder_id", folder_id);
		conditions.put("system_user_id", user().getId());
		Record parameters = new Record();
		parameters.put("email_folder_id", 1);
		executeUpdate("email_read", parameters, conditions);
		parameters.put("email_folder_id", 2);
		executeUpdate("email", parameters, conditions);
		conditions.put("id", folder_id);
		conditions.remove("email_folder_id");
	    executeDelete("email_folder", conditions);
	}

	public Result queryMineServer() throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select  es.*                                                   ");
		sql.append(" from    email_server(nolock) es                                      ");
		sql.append(" where   system_user_id = :system_user_id                       ");
		sql.append(" order by id                                               ");
		Record params = new Record();
		params.put("system_user_id", user().getId());
		return executeQuery(sql.toString(), params);
	}

	public Record getMineServer(int server_id) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select  es.*                                                   ");
		sql.append(" from    email_server(nolock) es                                      ");
		sql.append(" where   system_user_id = :system_user_id                       ");
		sql.append("   and   id = :id                                               ");
		Record params = new Record();
		params.put("system_user_id", user().getId());
		params.put("id", server_id);
		return executeQuerySingleRecord(sql.toString(), params);
	}

	public int insertServer(Map<String, Object> params) throws Exception {
		params.put("system_user_id", user().getId());
	    executeInsert("email_server", params);
	    return getSeqVal("email_server");
	}

	public void updateServer(int server_id, Map<String, Object> params) throws Exception {
		Record parameters = new Record(params);
		parameters.remove("id");
		parameters.remove("system_user_id");
		Record conditions = new Record();
		conditions.put("id", server_id);
		conditions.put("system_user_id", user().getId());
	    executeUpdate("email_server", parameters, conditions);
	}

	public void deleteServer(int server_id) throws Exception {
		Record conditions = new Record();
		conditions.put("system_user_id", user().getId());
		conditions.put("id", server_id);
	    executeDelete("email_server", conditions);
	}

}
