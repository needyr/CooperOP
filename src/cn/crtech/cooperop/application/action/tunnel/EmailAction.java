package cn.crtech.cooperop.application.action.tunnel;

import java.util.Map;

import cn.crtech.cooperop.application.service.tunnel.EmailService;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class EmailAction extends BaseAction {

	public Result query(Map<String, Object> req) throws Exception {
		return new EmailService().query(req);
	}
	public Map<String, Object> save(Map<String, Object> req) throws Exception {
		if(CommonFun.isNe(req.get("id"))){
			new EmailService().insert(req);
		}else{
			new EmailService().update(req);
		}
		return req;
	}
	public Map<String, Object> add(Map<String, Object> req) throws Exception {
		if(!CommonFun.isNe(req.get("id"))){
			return new EmailService().get(req);
		}else{
			return req;
		}
	}
	public Map<String, Object> delete(Map<String, Object> req) throws Exception {
		req.put("state", -1);
		new EmailService().update(req);
		return req;
	}
}
