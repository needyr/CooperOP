package cn.crtech.cooperop.hospital_common.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;

public class AfterAutoAuditDao extends BaseDao{


	private final static String TABLE_NAME = "audit_source_queue";
	
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("select * from "+TABLE_NAME+"(nolock) where 1=1 " );
		setParameter(params, "state", " and state=:state", sql);
		return executeQuery(sql.toString(), params);
	}
	
	public void update(Map<String, Object> params) throws Exception {
		Record r = new Record();
		r.put("id", params.remove("id"));
		executeUpdate(TABLE_NAME, params, r);
	}
	
	public Result queryAfterAudit(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("select * from audit_source_patient(nolock) where 1=1 " );
		setParameter(params, "audit_queue_id", " and audit_queue_id=:audit_queue_id", sql);
		return executeQuery(sql.toString(), params);
	}
	
	public Result queryAfterAuditOrders(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("select * from audit_source_row(nolock) where 1=1 " );
		setParameter(params, "audit_queue_id", " and audit_queue_id=:audit_queue_id", sql);
		return executeQuery(sql.toString(), params);
	}
}
