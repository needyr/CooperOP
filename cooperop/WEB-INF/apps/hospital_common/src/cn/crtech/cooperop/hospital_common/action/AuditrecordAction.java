package cn.crtech.cooperop.hospital_common.action;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.service.AuditrecordService;

public class AuditrecordAction extends BaseAction{

	public Result query(Map<String, Object> params) throws Exception {
		return new AuditrecordService().queryAudit(params);
	}
	
	public Result queryByDept(Map<String, Object> params) throws Exception {
		String deptCode = user().getBaseDepCode();
		if (!CommonFun.isNe(deptCode)){
			params.put("department",deptCode);
		}
		return new AuditrecordService().queryAuditByDept(params);
	}
	
	
	public Result queryAuditAll(Map<String, Object> map) throws Throwable{
		return new AuditrecordService().queryAuditAll(map);
	}
	
	public Map<String, Object> detail(Map<String, Object> params) throws Exception {
		return new AuditrecordService().get(params);
	}
	
	public Record info(Map<String, Object> params) throws Exception {
		if(CommonFun.isNe(params.get("id"))) {
			return null;
		}
		return new AuditrecordService().getById(params);
	}
	
}
