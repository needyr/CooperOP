package cn.crtech.cooperop.ipc.service;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.ipc.dao.AutoAuditOrdersDao;

public class AutoAuditOrdersService extends BaseService {
	
	public Result get_audit_def_oredrs(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			return new AutoAuditOrdersDao().get_audit_def_oredrs(params);
		} catch (Exception ex){
			throw ex;
		}finally{
			disconnect();
		}
	}
}
