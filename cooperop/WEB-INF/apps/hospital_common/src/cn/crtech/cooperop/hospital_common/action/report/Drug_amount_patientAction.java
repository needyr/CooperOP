package cn.crtech.cooperop.hospital_common.action.report;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.service.DictSysYpflService;
import cn.crtech.cooperop.hospital_common.service.DictdrugService;
import cn.crtech.cooperop.hospital_common.service.report.Drug_amount_patientService;

public class Drug_amount_patientAction extends BaseAction {
	public Result query_stats(Map<String, Object> params) throws Exception {
		return new Drug_amount_patientService().query_stats(params);
	}
	
	public Result query_dept(Map<String, Object> params) throws Exception {
		return new Drug_amount_patientService().query_dept(params);
	}

	public Result query_doctor(Map<String, Object> params) throws Exception {
		return new Drug_amount_patientService().query_doctor(params);
	}
	
	public Map<String, Object> stats(Map<String, Object> params) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("now_time", CommonFun.formatDate(new Date(), "yyyy-MM-dd"));
		//map.put("now_time", "2019-04-28");
		//药品类型
		map.put("ypfl", new DictSysYpflService().query(params).getResultset());
		map.put("types", new DictdrugService().queryDrugType(params).getResultset());
		//剂型
		
		return map;
	}
	
	public Map<String, Object> dept(Map<String, Object> params) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("now_time", CommonFun.formatDate(new Date(), "yyyy-MM-dd"));
		//map.put("now_time", "2019-04-28");
		//药品类型
		map.put("ypfl", new DictSysYpflService().query(params).getResultset());
		map.put("types", new DictdrugService().queryDrugType(params).getResultset());
		//剂型
		
		return map;
	}
	
	public Map<String, Object> doctor(Map<String, Object> params) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("now_time", CommonFun.formatDate(new Date(), "yyyy-MM-dd"));
		//map.put("now_time", "2019-04-28");
		//药品类型
		map.put("ypfl", new DictSysYpflService().query(params).getResultset());
		map.put("types", new DictdrugService().queryDrugType(params).getResultset());
		//剂型
		
		return map;
	}
}
