package cn.crtech.cooperop.hospital_common.action.report;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.service.DictSysYpflService;
import cn.crtech.cooperop.hospital_common.service.DictdrugService;
import cn.crtech.cooperop.hospital_common.service.report.Drug_use_intensionService;

public class Drug_use_intensionAction extends BaseAction {
	
	public Result query_dui(Map<String, Object> params) throws Exception {
		Result re = new Drug_use_intensionService().query_dui(params);
		return re;
	}
	
	public Result query_expend_dept_zj(Map<String, Object> params) throws Exception {
		Result re = new Drug_use_intensionService().query_expend_dept_zj(params);
		return re;
	}
	
	public Result query_expend_doctor_zj(Map<String, Object> params) throws Exception {
		Result re = new Drug_use_intensionService().query_expend_doctor_zj(params);
		return re;
	}
	
	public Result query_expend_stats(Map<String, Object> params) throws Exception {
		Result re = new Drug_use_intensionService().query_expend_stats(params);
		return re;
	}
	
	public Result query_dept_dui(Map<String, Object> params) throws Exception {
		Result re = new Drug_use_intensionService().query_dept_dui(params);
		return re;
	}
	
	public Result query_expend_dept_dui(Map<String, Object> params) throws Exception {
		Result re = new Drug_use_intensionService().query_expend_dept_dui(params);
		return re;
	}
	
	public Result query_expend_doctor_dui(Map<String, Object> params) throws Exception {
		Result re = new Drug_use_intensionService().query_expend_doctor_dui(params);
		return re;
	}
	
	public Result query_dept_zj(Map<String, Object> params) throws Exception {
		Result re = new Drug_use_intensionService().query_dept_zj(params);
		return re;
	}
	
	public Result query_dti(Map<String, Object> params) throws Exception {
		Result re = new Drug_use_intensionService().query_dti(params);
		return re;
	}
	
	public Map<String, Object> dept_dui(Map<String, Object> params) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		//map.put("now_time", "2019-04-28");
		//药品类型
		map.put("ypfl", new DictSysYpflService().query(params).getResultset());
		map.put("types", new DictdrugService().queryDrugType(params).getResultset());
		//剂型
		
		return map;
	}

}
