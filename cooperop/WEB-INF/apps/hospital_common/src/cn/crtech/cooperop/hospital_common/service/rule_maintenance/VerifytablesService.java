package cn.crtech.cooperop.hospital_common.service.rule_maintenance;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.dao.rule_maintenance.VerifytablesDao;

public class VerifytablesService extends BaseService{
	public Result queryAllName(Map<String, Object> params) throws Exception {
		try {
			connect("guide");
			return new VerifytablesDao().queryAllName(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}
		
	}
}
