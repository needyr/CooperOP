package cn.crtech.cooperop.ipc.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;

public class SendErrorMsgDao extends BaseDao{

	private final static String TABLE_NAME = "system_send_error_message";
	
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("select * from " + TABLE_NAME);
		sql.append(" (nolock) where state = 0 ");
		sql.append(" order by id ");
		return executeQuery(sql.toString(), params);
	}
	
	public void insert (Map<String, Object> params) throws Exception {
		executeInsert(TABLE_NAME, params);
	}
	
	public void update (Map<String, Object> params) throws Exception {
		Record r = new Record();
		r.put("doctor_no", params.remove("doctor_no"));
		r.put("auto_audit_id", params.remove("auto_audit_id"));
		r.put("action", params.remove("action"));
		executeUpdate(TABLE_NAME, params, r);
	}
	
	public Record getShenFang(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("select * from " + TABLE_NAME);
		sql.append(" (nolock) where auto_audit_id = :auto_audit_id and hospital_id = :hospital_id and doctor_no = :doctor_no ");
		return executeQuerySingleRecord(sql.toString(), params);
	}
}
