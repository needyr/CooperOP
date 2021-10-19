package cn.crtech.cooperop.crdc.action;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.crdc.service.DjasperService;

public class DjasperAction extends BaseAction {

	public Result query(Map<String, Object> req) throws Exception{
		return new DjasperService().query(req);
	}
	
	public Map<String, Object> save(Map<String, Object> req) throws Exception{
		if(!CommonFun.isNe(req.get("id"))){
			new DjasperService().update(req);
		}else{
			new DjasperService().save(req);
		}
		return req;
	}
	public Map<String, Object> delete(Map<String, Object> req) throws Exception{
		new DjasperService().delete(req);
		return req;
	}
	public Map<String, Object> add(Map<String, Object> req) throws Exception{
		if(!CommonFun.isNe(req.get("id"))){
			return new DjasperService().get(req);
		}else{
			return req;
		}
	}
}
