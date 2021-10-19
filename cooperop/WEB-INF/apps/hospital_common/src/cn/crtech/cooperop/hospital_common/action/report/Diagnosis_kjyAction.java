package cn.crtech.cooperop.hospital_common.action.report;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.service.report.Diagnosis_kjyService;

public class Diagnosis_kjyAction extends BaseAction {
	public Result research_query(Map<String, Object> params) throws Exception {
		return new Diagnosis_kjyService().research_query(params);
	}
	
	public Result cost_comparison_query(Map<String, Object> params) throws Exception {
		return new Diagnosis_kjyService().cost_comparison_query(params);
	}
	
	public Result sum_query(Map<String, Object> params) throws Exception {
		return new Diagnosis_kjyService().sum_query(params);
	}
}
