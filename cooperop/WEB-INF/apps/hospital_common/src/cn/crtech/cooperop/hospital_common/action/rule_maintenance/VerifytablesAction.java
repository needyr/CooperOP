package cn.crtech.cooperop.hospital_common.action.rule_maintenance;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.service.rule_maintenance.VerifytablesService;

public class VerifytablesAction extends BaseAction{
	
	public Result queryallname(Map<String, Object> params) throws Exception {
		return new VerifytablesService().queryAllName(params);
	}
	
}
