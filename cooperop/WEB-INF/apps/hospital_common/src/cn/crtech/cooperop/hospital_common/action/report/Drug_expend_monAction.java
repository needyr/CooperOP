package cn.crtech.cooperop.hospital_common.action.report;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.service.DictSysYpflService;
import cn.crtech.cooperop.hospital_common.service.DictdrugService;
import cn.crtech.cooperop.hospital_common.service.report.Drug_expend_monService;

public class Drug_expend_monAction extends BaseAction{
	
	public Result query_dept(Map<String, Object> params) throws Exception {
		return new Drug_expend_monService().query_dept(params);
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
}
