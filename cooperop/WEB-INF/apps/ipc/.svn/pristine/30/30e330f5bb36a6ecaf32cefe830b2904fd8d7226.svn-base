package cn.crtech.cooperop.ipc.action;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.ipc.service.DoctorService;

public class DoctorAction extends BaseAction{

	public Result query(Map<String, Object> params) throws Exception {
		return new DoctorService().query(params);
	}
	
	public void insert(Map<String, Object> params) throws Exception {
		 new DoctorService().insert(params);
	}
	
	public int update(Map<String, Object> params) throws Exception {
		return new DoctorService().update(params);
	}
	
	public int save(Map<String, Object> params) throws Exception {
		if(CommonFun.isNe(params.get("id"))){
			params.remove("id");
			 return new DoctorService().insert(params);
		}else{
			 return new DoctorService().update(params);
		}
    }
	
	public int delete(Map<String, Object> params) throws Exception {
		return new DoctorService().delete(params);
	}
	
	public Record edit(Map<String, Object> params) throws Exception {
		if(!CommonFun.isNe(params.get("id"))){
			return new DoctorService().get(params);
		}else {
			return null;
		}
	}
	
	public Result querydoctor(Map<String, Object> params) throws Exception {
		return new DoctorService().querydoctor(params);
	}
	
	public Record getcdoctor(Map<String, Object> params) throws Exception {
		return new DoctorService().getcdoctor(params);
		
	}
	
	
	public Result queryHas(Map<String, Object> params) throws Exception {
		return new DoctorService().queryHas(params);
		
	}
}
