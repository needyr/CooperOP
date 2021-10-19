package cn.crtech.cooperop.ipc.service;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.ipc.dao.AuditServerResetLogDao;

public class AuditServerResetLogService extends BaseService {
	public void insert(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			new AuditServerResetLogDao().insert(params);
		} catch (Exception ex){
			throw ex;
		}finally{
			disconnect();
		}
	}
}
