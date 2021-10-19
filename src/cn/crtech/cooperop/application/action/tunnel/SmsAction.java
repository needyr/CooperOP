package cn.crtech.cooperop.application.action.tunnel;

import java.util.Map;

import cn.crtech.cooperop.application.service.tunnel.SmsService;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class SmsAction extends BaseAction {

	public Result query(Map<String, Object> req) throws Exception {
		return new SmsService().query(req);
	}
	public Map<String, Object> save(Map<String, Object> req) throws Exception {
		if(CommonFun.isNe(req.get("id"))){
			new SmsService().insert(req);
		}else{
			new SmsService().update(req);
		}
		return req;
	}
	public Map<String, Object> add(Map<String, Object> req) throws Exception {
		if(!CommonFun.isNe(req.get("id"))){
			return new SmsService().get(req);
		}else{
			return req;
		}
	}
	public Map<String, Object> delete(Map<String, Object> req) throws Exception {
		req.put("state", -1);
		new SmsService().update(req);
		return req;
	}
}
