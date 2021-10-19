package cn.crtech.cooperop.crdc.action;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.crdc.service.SystemParamsService;

public class SysparamsAction extends BaseAction {

	public Result query(Map<String, Object> req) throws Exception{
		return new SystemParamsService().query(req);
	}
	
	public Map<String, Object> save(Map<String, Object> req) throws Exception{
		SystemParamsService fs = new SystemParamsService();
		if(!CommonFun.isNe(req.get("id"))){
			fs.update(req);
		}else{
			req.remove("id");
			fs.insert(req);
		}
		req.put("result", "success");
		return req;
	}
	public Map<String, Object> delete(Map<String, Object> req) throws Exception{
		new SystemParamsService().delete(req);
		req.put("result", "success");
		return req;
	}
	public Map<String, Object> add(Map<String, Object> req) throws Exception{
		Record r = new Record();
		if(!CommonFun.isNe(req.get("id"))){
			r = new SystemParamsService().get(req);
		}
		return r;
	}
}
