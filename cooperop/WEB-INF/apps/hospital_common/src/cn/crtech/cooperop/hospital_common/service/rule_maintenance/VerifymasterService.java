package cn.crtech.cooperop.hospital_common.service.rule_maintenance;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.dao.rule_maintenance.VerifymasterDao;

public class VerifymasterService extends BaseService{
	public Result query(Map<String, Object> params) throws Exception {
		try {
			connect("guide");
			return new VerifymasterDao().query(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}
		
	}
	
	
}
