package cn.crtech.cooperop.hospital_common.dao.system;

import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class MsgAlertDao extends BaseDao{

	private static final String TABLE = "system_msg_alert";
	
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from " + TABLE + " (nolock) ");
		sql.append(" where send_to_user = :send_to_user ");
		sql.append(" and is_read = 0 ");
		return executeQuery(sql.toString(), params);
	}
	
	// 查询所有未读
	public Result queryAllMsg(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from " + TABLE + " (nolock) ");
		sql.append(" where is_read = 0 ");
		return executeQuery(sql.toString(), params);
	}
	
	public Result queryMsgToUser(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select                               "); 
		sql.append("a.send_to_user,                      "); 
		sql.append("STUFF(                               "); 
		sql.append("(select ','+id                       "); 
		sql.append("from "+TABLE+" (nolock)       "); 
		sql.append("where send_to_user = a.send_to_user  "); 
		sql.append("and is_read = 0                      "); 
		sql.append("group by id for xml path(''))        "); 
		sql.append(", 1, 1, '') as msg_ids,              "); 
		sql.append("count(1) count                       "); 
		sql.append("from "+TABLE+" (nolock) a     "); 
		sql.append("where a.is_read = 0                  "); 
		sql.append("group by a.send_to_user              "); 
		return executeQuery(sql.toString(), params);
	}
	
	public Record getMsgToUser(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select                               "); 
		sql.append("a.send_to_user,                      "); 
		sql.append("STUFF(                               "); 
		sql.append("(select ','+id                       "); 
		sql.append("from "+TABLE+" (nolock)       "); 
		sql.append("where send_to_user = a.send_to_user  "); 
		sql.append("and is_read = 0 and send_to_user = :send_to_user "); 
		sql.append("group by id for xml path(''))        "); 
		sql.append(", 1, 1, '') as msg_ids,              "); 
		sql.append("count(1) count                       "); 
		sql.append("from "+TABLE+" (nolock) a     "); 
		sql.append("where a.is_read = 0 and a.send_to_user = :send_to_user "); 
		sql.append("group by a.send_to_user              "); 
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	public Record queryCount(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select count(*) num from " + TABLE + " (nolock) ");
		sql.append(" where send_to_user = :send_to_user ");
		sql.append(" and is_read = 0 ");
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	public void update(Map<String, Object> params) throws Exception {
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("id", params.remove("id"));
		executeUpdate(TABLE, params, condition);
	}
	
	public String insert(Map<String, Object> params) throws Exception {
		String id = CommonFun.getITEMID();
		params.put("id", id);
		executeInsert(TABLE, params);
		return id;
	}

	public void updateRead(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("update hospital_common..system_msg_alert set is_read = '1' where id = '"+params.get("id")+"'");
		execute(sql.toString(), params);
	}
	
}
