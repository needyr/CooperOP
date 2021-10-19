package cn.crtech.cooperop.hospital_common.action;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.service.MethodService;

public class MethodAction extends BaseAction {
	
	public int insert(Map<String, Object> params) throws Exception {
		return new MethodService().insert(params);
	}
	
	public int delete(Map<String, Object> params) throws Exception {
		return new MethodService().delete(params);
	}
	
	public int update(Map<String, Object> params) throws Exception {
		return new MethodService().update(params);
	}
	
	public Result query(Map<String, Object> params) throws Exception {
		return new MethodService().query(params);
	}
	
	public Record edit(Map<String, Object> params) throws Exception {
		return new MethodService().get(params);
	}
}
