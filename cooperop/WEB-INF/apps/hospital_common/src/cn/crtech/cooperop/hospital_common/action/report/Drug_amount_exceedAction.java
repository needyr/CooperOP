package cn.crtech.cooperop.hospital_common.action.report;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.service.report.Drug_amount_exceedService;

public class Drug_amount_exceedAction extends BaseAction {
	
	public Result query_stats(Map<String, Object> params) throws Exception {
		return new Drug_amount_exceedService().query_stats(params);
	}
	
	public Result query_dept(Map<String, Object> params) throws Exception {
		return new Drug_amount_exceedService().query_dept(params);
	}
	
	public Result query_doctor(Map<String, Object> params) throws Exception {
		return new Drug_amount_exceedService().query_doctor(params);
	}
}
