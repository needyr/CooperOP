package cn.crtech.cooperop.hospital_common.action;

import java.util.Map;

import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.service.WeblogService;


public class WeblogAction {
	
	public int insert(Map<String, Object> params) throws Exception {
		return new WeblogService().insert(params);
	}
	
	public int delete(Map<String, Object> params) throws Exception {
		return new WeblogService().delete(params);
	}
	
	public int update(Map<String, Object> params) throws Exception {
		return new WeblogService().update(params);
	}
	
	public Result query(Map<String, Object> params) throws Exception {
		return new WeblogService().query(params);
	}
	public Result queryDB(Map<String, Object> params) throws Exception {
		return new WeblogService().queryDB(params);
	}
	public Result queryDistinct(Map<String, Object> params) throws Exception {
		if (CommonFun.isNe(params.get("data_webservice_code"))){
			return new WebserviceAction().query(params);
		} else{
			/*params.put("start", "0");
			params.put("limit", "200");*/
			return new WebmethodAction().query(params);
		}
	}
	
	public Record get(Map<String, Object> params) throws Exception {
		return new WeblogService().get(params);
	}
}
