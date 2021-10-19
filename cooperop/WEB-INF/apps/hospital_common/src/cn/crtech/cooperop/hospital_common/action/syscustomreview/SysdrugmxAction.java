package cn.crtech.cooperop.hospital_common.action.syscustomreview;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.service.syscustomreview.SysdrugmxService;

public class SysdrugmxAction extends BaseAction{

	public Result query(Map<String, Object> params) throws Exception {
		return new SysdrugmxService().query(params);
	}
	
	public int delete(Map<String, Object> params) throws Exception {
		return new SysdrugmxService().delete(params);
	}
	
	public Map<String, Object> drugedit(Map<String, Object> params) throws Exception {
		if(!CommonFun.isNe(params.get("drug_code"))&&!CommonFun.isNe(params.get("xmid"))){
			return new SysdrugmxService().get(params);
		}else{
			return params;
		}
		
	}
		
	public int update(Map<String, Object> params) throws Exception {
		return new SysdrugmxService().update(params);
	}
	
	public int insert(Map<String, Object> params) throws Exception {
		return new SysdrugmxService().insert(params);
	}
}
