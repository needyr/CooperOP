package cn.crtech.cooperop.ipc.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.util.CommonFun;

public class AuditServerResetLogDao extends BaseDao {
private final static String TABLE_NAME = "audit_server_reset_log";
	
	public void insert(Map<String, Object> params) throws Exception {
		String id = CommonFun.getITEMID();
		params.put("id", id);
		executeInsert(TABLE_NAME, params);
	}
}
