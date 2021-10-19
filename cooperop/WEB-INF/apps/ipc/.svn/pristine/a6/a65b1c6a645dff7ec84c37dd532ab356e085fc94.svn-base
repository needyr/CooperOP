package cn.crtech.cooperop.ipc.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.util.CommonFun;

public class AutoAuditPatienDao extends BaseDao{
	private final static String TABLE_NAME = "auto_audit_patient";//HIS患者信息
	
	public void insert(Map<String, Object> params) throws Exception {
		String id = CommonFun.getITEMID();
		params.put("id", id);
		executeInsert(TABLE_NAME, params);
	}
}
