package cn.crtech.cooperop.hospital_common.action.dict;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.service.dict.TimeexpressionService;

public class TimeexpressionAction extends BaseAction{
	
	public Result query(Map<String, Object> params) throws Exception {
		return new TimeexpressionService().query(params);
	}
	
	
	public int insert(Map<String, Object> params) throws Exception {
		return new TimeexpressionService().insert(params);
	}

	public int update(Map<String, Object> params) throws Exception {
		return new TimeexpressionService().update(params);
	}
	
	public int delete(Map<String, Object> params) throws Exception {
		return new TimeexpressionService().delete(params);
	}
	
	public Record edit(Map<String, Object> params) throws Exception {
		if(!CommonFun.isNe(params.get("p_key"))){
			return new TimeexpressionService().get(params);
		}else {
			return null;
		}
	}
}
