package cn.crtech.cooperop.hospital_common.action;

import java.util.Map;

import cn.crtech.cooperop.application.authenticate.DisLoggedIn;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.service.DepartmentService;
public class DepartmentAction extends BaseAction{

	public Result query(Map<String, Object> params) throws Exception {
		return new DepartmentService().query(params);
	}
	
	public int insert(Map<String, Object> params) throws Exception {
		return new DepartmentService().insert(params);
	}
	
	public int save(Map<String, Object> params) throws Exception {
		if(CommonFun.isNe(params.get("id"))){
			params.remove("id");
			return new DepartmentService().insert(params);
		}else{
			return new DepartmentService().update(params);
		}
}
	
	public int delete(Map<String, Object> params) throws Exception {
		
		return new DepartmentService().delete(params);
	}
	
	public Record edit(Map<String, Object> params) throws Exception {
		if(!CommonFun.isNe(params.get("id"))){
			return new DepartmentService().get(params);
		}else {
			return null;
		}
	}
	
	
	public Result queryHisDept(Map<String, Object> params) throws Exception {
		return new DepartmentService().queryHisDept(params);
	}
	
	public Record getHisDept(Map<String, Object> params) throws Exception {
		return new DepartmentService().getHisDept(params);
		
	}
	
	@DisLoggedIn
	public Map<String, Object> querySearch(Map<String, Object> params) throws Exception {
		return new DepartmentService().querySearch(params);
		
	}
	
	@DisLoggedIn
	public Map<String, Object> searchCheck(Map<String, Object> params) throws Exception {
		return new DepartmentService().searchCheck(params);
		
	}
}
