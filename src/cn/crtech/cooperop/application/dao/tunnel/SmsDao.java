package cn.crtech.cooperop.application.dao.tunnel;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;

public class SmsDao extends BaseDao {
	public final static String TABLE_NAME = "sms_tunnel";
	
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select  *                                                         ");
		sql.append(" from    "+ TABLE_NAME + "(nolock)");
		sql.append(" where   1=1 ");
		setParameter(params, "state", " and state=:state", sql);
	    return executeQuery(sql.toString(), params);
	}
	
	public Map<String, Object> get(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select  top 1 *        ");
		sql.append(" from    "+ TABLE_NAME + "(nolock) where 1=1");
		setParameter(params, "id", " and id = :id", sql);
		setParameter(params, "system_product_code", " and system_product_code = :system_product_code", sql);
		
	    return executeQuerySingleRecord(sql.toString(), params);
	}
	
	public int insert(Map<String, Object> params) throws Exception {
		params.remove("id");
		params.put("state", "1");
		params.put("last_modify_user", user().getId());
		params.put("last_modify_time", "sysdate");
	    executeInsert(TABLE_NAME, params);
	    return getSeqVal(TABLE_NAME);
	}

	public void update(Map<String, Object> params) throws Exception {
		Record conditions = new Record();
		conditions.put("id", params.get("id"));
		Record parameters = new Record(params);
		parameters.remove("id");
		parameters.put("last_modify_user", user().getId());
		parameters.put("last_modify_time", "sysdate");
	    executeUpdate(TABLE_NAME, parameters, conditions);
	}
}
