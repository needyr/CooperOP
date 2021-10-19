package cn.crtech.cooperop.hospital_common.action.report;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.service.DictHisDrugService;
import cn.crtech.cooperop.hospital_common.service.DictSysYpflService;
import cn.crtech.cooperop.hospital_common.service.ReportService;

public class KjyAction extends BaseAction {
	
	public Result querykjyoutpuse(Map<String, Object> params) throws Exception {
		return new ReportService().queryKjyOutpUse(params);
	}
	
	public Result querykjyoper(Map<String, Object> params) throws Exception {
		return new ReportService().querykjyoper(params);
	}
	
	public Result querykjyoper_dept(Map<String, Object> params) throws Exception {
		return new ReportService().querykjyoper_dept(params);
	}
	
	public Result querykjyoper_doctor(Map<String, Object> params) throws Exception {
		return new ReportService().querykjyoper_doctor(params);
	}
	
	public Result querykjymj(Map<String, Object> params) throws Exception {
		return new ReportService().querykjymj(params);
	}
	
	public Result querykjyoutpuse_dept(Map<String, Object> params) throws Exception {
		return new ReportService().querykjyoutpuse_dept(params);
	}
	
	public Result querykjymj_dept(Map<String, Object> params) throws Exception {
		return new ReportService().querykjymj_dept(params);
	}
	
	public Result querykjyoutpuse_doctor(Map<String, Object> params) throws Exception {
		return new ReportService().querykjyoutpuse_doctor(params);
	}
	
	public Result querykjyomj_doctor(Map<String, Object> params) throws Exception {
		return new ReportService().querykjyomj_doctor(params);
	}
	
	public Map<String, Object> kjy_in_use(Map<String, Object> params) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("now_time", CommonFun.formatDate(new Date(), "yyyy-MM-dd"));
		//map.put("now_time", "2019-04-28");
		return map;
	}
	
	public Map<String, Object> kjy_outp_use(Map<String, Object> params) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("now_time", CommonFun.formatDate(new Date(), "yyyy-MM-dd"));
		//map.put("now_time", "2019-04-28");
		return map;
	}
	
	public Map<String, Object> drug_use_intension(Map<String, Object> params) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("now_time", CommonFun.formatDate(new Date(), "yyyy-MM-dd"));
		//map.put("now_time", "2019-04-28");
		//药品类型
		map.put("drug_type", new DictHisDrugService().queryKJYPropertyToxi(params).getResultset());
		//剂型
		
		return map;
	}
	
	public Result query_dui(Map<String, Object> params) throws Exception {
		Result re = new ReportService().query_dui(params);
		return re;
	}
	
	public Result query_dti(Map<String, Object> params) throws Exception {
		Result re = new ReportService().query_dti(params);
		return re;
	}
}
