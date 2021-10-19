package cn.crtech.cooperop.ipc.dao;

import java.util.Date;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.util.CommonFun;

public class CheckDataParmsDao extends BaseDao{
	private static final String TABLE_NAME = "check_data_parms";
	
	public int insert(Map<String, Object> params) throws Exception {
		String p_key = CommonFun.getITEMID();
		params.put("create_time", CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss:SSS"));
		params.put("p_key", p_key);
		return executeInsert(TABLE_NAME, params);
	}
	
	/*
	 * public int update(Map<String, Object> params) throws Exception { Record
	 * conditions = new Record(); conditions.put("auto_audit_id",
	 * params.remove("id")); return executeUpdate(TABLE_NAME, params, conditions); }
	 */
	
	/*
	 * @Deprecated public String updateTReq(Map<String, Object> params) throws
	 * Exception { StringBuffer sql = new StringBuffer(); sql.append(" update " +
	 * TABLE_NAME); sql.append(" set thirdt_request = :thirdt_request ");
	 * sql.append(" where auto_audit_id = :id "); return execute(sql.toString(),
	 * params); }
	 * 
	 * public String updateTResp(Map<String, Object> params) throws Exception {
	 * StringBuffer sql = new StringBuffer(); sql.append(" update " + TABLE_NAME +
	 * " with (updlock, holdlock)");
	 * sql.append(" set thirdt_response = :thirdt_response, ");
	 * sql.append(" update_time = :update_time, ");
	 * sql.append(" cost_time = :cost_time ");
	 * sql.append(" where auto_audit_id = :id "); return execute(sql.toString(),
	 * params); }
	 */
}
