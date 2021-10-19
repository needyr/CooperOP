package cn.crtech.cooperop.ipc.dao;

import java.util.Date;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;


public class AutoAuditFlowDao extends BaseDao{


private final static String TABLE_NAME = "auto_audit_flow";//流程表
	
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select t.*,vsu.name as doctor_name,vsu.department,vsu.department_name,aa.common_id,aa.yxk_advice ");
		sql.append(" from auto_audit (nolock) aa ");
		sql.append(" left join "+TABLE_NAME+"(nolock) t on aa.id=t.auto_audit_id ");
		sql.append(" left join v_system_user (nolock) vsu on vsu.no=t.doctor_no ");
		sql.append(" where 1=1 ");
		setParameter(params, "auto_audit_id", " and aa.id=:auto_audit_id", sql);
		setParameter(params, "djbh", " and t.djbh=:djbh", sql);
		return executeQuery(sql.toString(), params);
	}
	
	public Record get(String id) throws Exception {
		Record params = new Record();
		params.put("id", id);
		StringBuffer sql = new StringBuffer();
		sql.append("select * from "+TABLE_NAME+"(nolock) where id = :id");
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	
	public Record getAuto_audit_id(String id) throws Exception {
		Record params = new Record();
		params.put("auto_audit_id", id);
		StringBuffer sql = new StringBuffer();
		sql.append("select * from "+TABLE_NAME+"(nolock) where auto_audit_id = :auto_audit_id");
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	public String insert(Map<String, Object> params) throws Exception {
		params.remove("id");
		String id = CommonFun.getITEMID();
		params.put("id", id);
		params.put("create_time", CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
		executeInsert(TABLE_NAME, params);
		return id;
	}
	
	public int update(Map<String, Object> params) throws Exception {
		Record r = new Record();
		r.put("id", params.remove("id"));
		return executeUpdate(TABLE_NAME, params,r);
	}
	public void delete(Map<String, Object> params) throws Exception {
		Record r = new Record();
		r.put("id", params.remove("id"));
		executeDelete(TABLE_NAME, r);
	}

	public String hisCancelOrders(Map<String, Object> params) throws Exception {
		Record resultset = executeCallQuery("declare @a varchar(50),@re varchar(50);  exec scr_doctor_cancel_orders :auto_audit_id,@a output; select @re meg ", params).getResultset(0);
		return resultset.getString("meg");
	}
}
