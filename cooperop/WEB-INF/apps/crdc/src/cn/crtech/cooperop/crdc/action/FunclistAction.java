package cn.crtech.cooperop.crdc.action;

import java.util.Map;

import cn.crtech.cooperop.application.authenticate.DisValidPermission;
import cn.crtech.cooperop.bus.cache.Dictionary;
import cn.crtech.cooperop.bus.cache.SystemConfig;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.crdc.service.FunctionListService;

public class FunclistAction extends BaseAction {

	public Result query(Map<String, Object> req) throws Exception{
		return new FunctionListService().query(req);
	}
	
	public Map<String, Object> save(Map<String, Object> req) throws Exception{
		if(!CommonFun.isNe(req.get("functionname_old"))){
			new FunctionListService().update(req);
		}else{
			req.remove("functionname_old");
			new FunctionListService().save(req);
		}
		req.put("result", "success");
		return req;
	}
	public Map<String, Object> delete(Map<String, Object> req) throws Exception{
		new FunctionListService().delete(req);
		req.put("result", "success");
		return req;
	}
	public Map<String, Object> add(Map<String, Object> req) throws Exception{
		Record r = new Record();
		if(!CommonFun.isNe(req.get("functionname"))){
			r = new FunctionListService().get(req);
		}
		return r;
	}
	@DisValidPermission
	public Map<String, Object> reloadfield(Map<String, Object> req) throws Exception{
		SystemConfig.load();
		Dictionary.load();
		return req;
	}
}
