package cn.crtech.cooperop.hospital_common.action;

import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.service.SystemAssistQueryService;

public class SystemassistqueryAction extends BaseAction{

	public Result query(Map<String, Object> params) throws Exception {
		return new SystemAssistQueryService().query(params);
	}
	
	public Map<String, Object> assist(Map<String, Object> params) throws Exception {
		Map<String, Object> rtn = new HashMap<String, Object>();
		params.put("assist", 1);
		rtn.put("ipc_assist", new SystemAssistQueryService().query(params).getResultset());
		return rtn;
	}
	
	public int insert(Map<String, Object> params) throws Exception {
		new SystemAssistQueryService().insert(params);
		return 1;
	}
	
	public int update(Map<String, Object> params) throws Exception {
		new SystemAssistQueryService().update(params);
		return 1;
	}
	
	public int delete(Map<String, Object> params) throws Exception {
		if(CommonFun.isNe(params.get("id"))) {
			return -1;
		}else {
			return new SystemAssistQueryService().delete(params);
		}
	}
	
	public Record edit(Map<String, Object> params) throws Exception {
		if(!CommonFun.isNe(params.get("id"))){
			return new SystemAssistQueryService().get(params);
		}else {
			return null;
		}
	}
}
