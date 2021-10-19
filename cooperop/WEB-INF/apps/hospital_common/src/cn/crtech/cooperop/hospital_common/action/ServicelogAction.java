package cn.crtech.cooperop.hospital_common.action;

import java.util.Map;

import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.service.ServicelogService;
import cn.crtech.cooperop.hospital_common.service.WeblogService;


public class ServicelogAction {
	
	public int insert(Map<String, Object> params) throws Exception {
		return new ServicelogService().insert(params);
	}
	
	public int delete(Map<String, Object> params) throws Exception {
		return new ServicelogService().delete(params);
	}
	
	public int update(Map<String, Object> params) throws Exception {
		return new ServicelogService().update(params);
	}
	
	public Result query(Map<String, Object> params) throws Exception {
		return new ServicelogService().query(params);
	}
	
	public Result queryDistinct(Map<String, Object> params) throws Exception {
		if (CommonFun.isNe(params.get("data_service_code")))
			return new TypeAction().query(params);
		else
			return new MethodAction().query(params);
	}
	
	public Record get(Map<String, Object> params) throws Exception {
		return new ServicelogService().get(params);
	}
}
