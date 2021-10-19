package cn.crtech.cooperop.crdc.action;

import java.util.Map;

import cn.crtech.cooperop.application.authenticate.DisValidPermission;
import cn.crtech.cooperop.bus.cache.Dictionary;
import cn.crtech.cooperop.bus.cache.SystemConfig;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.crdc.service.FieldService;

public class FieldAction extends BaseAction {

	public Result query(Map<String, Object> req) throws Exception{
		return new FieldService().query(req);
	}
	
	public Map<String, Object> save(Map<String, Object> req) throws Exception{
		FieldService fs = new FieldService();
		if(!CommonFun.isNe(fs.get(req))){
			fs.update(req);
		}else{
			fs.save(req);
		}
		req.put("result", "success");
		return req;
	}
	public Map<String, Object> delete(Map<String, Object> req) throws Exception{
		new FieldService().delete(req);
		req.put("result", "success");
		return req;
	}
	public Map<String, Object> add(Map<String, Object> req) throws Exception{
		Record r = new Record();
		if(!CommonFun.isNe(req.get("fdname"))){
			r = new FieldService().get(req);
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
