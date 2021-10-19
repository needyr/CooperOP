package cn.crtech.cooperop.ipc.action;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.ipc.service.SpecificationService;

public class SpecificationAction extends BaseAction {
	
	public Result query(Map<String, Object> params) throws Exception {
		return new SpecificationService().query(params);
	}
	
	public Record detail(Map<String, Object> params) throws Exception {
		if(!CommonFun.isNe(params.get("sys_drug_code"))){
			return new SpecificationService().get(params);
		}else {
			return null;
		}
	}
	
	public Record edit(Map<String, Object> params) throws Exception {
		if(!CommonFun.isNe(params.get("sys_drug_code"))){
			return new SpecificationService().get(params);
		}else {
			return null;
		}
	}
	
	public int insert(Map<String, Object> params) throws Exception {
		return new SpecificationService().insert(params);
    }
	
	public int update(Map<String, Object> params) throws Exception {
		return new SpecificationService().update(params);
	}
	
	public Result isHas(Map<String, Object> params) throws Exception {
		return new SpecificationService().isHas(params);
	}
	
	public int delete(Map<String, Object> params) throws Exception {
		return new SpecificationService().delete(params);
	}

}
