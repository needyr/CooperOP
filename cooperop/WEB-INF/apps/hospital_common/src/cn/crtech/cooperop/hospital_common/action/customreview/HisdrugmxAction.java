package cn.crtech.cooperop.hospital_common.action.customreview;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.service.HisdrugmxService;

public class HisdrugmxAction extends BaseAction{

	public Result query(Map<String, Object> params) throws Exception {
		return new HisdrugmxService().query(params);
	}
	
	public int delete(Map<String, Object> params) throws Exception {
		return new HisdrugmxService().delete(params);
	}
	
	public Map<String, Object> drugedit(Map<String, Object> params) throws Exception {
		if(!CommonFun.isNe(params.get("drug_code"))&&!CommonFun.isNe(params.get("xmid"))){
			return new HisdrugmxService().get(params);
		}else{
			return params;
		}
		
	}
		
	public int update(Map<String, Object> params) throws Exception {
		return new HisdrugmxService().update(params);
	}
	
	public int insert(Map<String, Object> params) throws Exception {
		return new HisdrugmxService().insert(params);
	}
}
