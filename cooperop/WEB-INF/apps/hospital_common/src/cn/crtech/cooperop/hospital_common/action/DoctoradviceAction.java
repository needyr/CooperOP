package cn.crtech.cooperop.hospital_common.action;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.service.DoctorAdviceService;

public class DoctoradviceAction extends BaseAction{

	public Result query(Map<String, Object> params) throws Exception {
		
		return new DoctorAdviceService().query(params);
	}
	
	public int insert(Map<String, Object> params) throws Exception {
		
		return new DoctorAdviceService().insert(params);
	}
	
	public int save(Map<String, Object> params) throws Exception {
		
		if(CommonFun.isNe(params.get("id"))){
			params.remove("id");
			return new DoctorAdviceService().insert(params);
		}else{
			return new DoctorAdviceService().update(params);
		}
}
	
	public int delete(Map<String, Object> params) throws Exception {
		
		return new DoctorAdviceService().delete(params);
	}
	
	public Record edit(Map<String, Object> params) throws Exception {
		
		if(!CommonFun.isNe(params.get("id"))){
			return new DoctorAdviceService().get(params);
		}else {
			return null;
		}
	}
}
