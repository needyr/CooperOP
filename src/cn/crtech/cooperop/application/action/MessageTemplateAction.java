package cn.crtech.cooperop.application.action;

import java.util.ArrayList;
import java.util.Map;

import cn.crtech.cooperop.application.authenticate.DisValidPermission;
import cn.crtech.cooperop.application.service.MessageTemplateService;
import cn.crtech.cooperop.bus.cache.Dictionary;
import cn.crtech.cooperop.bus.cache.SystemConfig;
import cn.crtech.cooperop.bus.cache.SystemMessageTemplate;
import cn.crtech.cooperop.bus.message.MessageSender;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class MessageTemplateAction extends BaseAction {

	public Result query(Map<String, Object> req) throws Exception {
		return new MessageTemplateService().query(req);
	}
	public Map<String, Object> save(Map<String, Object> req) throws Exception {
		Map<String, Object> map = CommonFun.json2Object((String)req.get("jdata"), Map.class);
		if(CommonFun.isNe(map.get("id"))){
			new MessageTemplateService().insert(map);
		}else{
			new MessageTemplateService().update(map);
		}
		SystemMessageTemplate.load();
		return req;
	}
	public Map<String, Object> add(Map<String, Object> req) throws Exception {
		if(!CommonFun.isNe(req.get("id"))){
			return new MessageTemplateService().get(req);
		}else{
			return req;
		}
	}
	public Map<String, Object> update(Map<String, Object> req) throws Exception {
		new MessageTemplateService().update(req);
		return req;
	}
	public Map<String, Object> delete(Map<String, Object> req) throws Exception {
		req.put("state", -1);
		new MessageTemplateService().updateSimple(req);
		return req;
	}
	
	public Map<String, Object> jqtest(Map<String, Object> req) throws Exception {
		MessageSender.sendProcessMessageBefore((String)req.get("p_code"), (String)req.get("p_id"), (String)req.get("p_node"), req, null);
		return req;
	}
	 
}
