package cn.crtech.cooperop.application.action;

import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.application.authenticate.DisValidPermission;
import cn.crtech.cooperop.application.service.ContacterService;
import cn.crtech.cooperop.application.service.MessageService;
import cn.crtech.cooperop.application.service.UserService;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;

@DisValidPermission
public class MessageAction extends BaseAction {
	
	public Result session(Map<String, Object> req) throws Exception {
		return new MessageService().session(req);
	}

	public Map<String, Object> chat(Map<String, Object> map) throws Exception {
		if ("U".equals(map.get("target"))) {
			return new UserService().get((String)map.get("send_to"));
		} else if("G".equals(map.get("target"))){
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("id", map.get("send_to"));
			Record r = new ContacterService().queryGroup(params).getResultset(0);
			r.put("currentuserid", user().getId());
			return r;
		} else {
			return new Record();
		}
	}
	public Result listnew(Map<String, Object> req) throws Exception {
		return new MessageService().listnew(req);
	}

	public Record send(Map<String, Object> req) throws Exception {
		return new MessageService().send(req);
	}

	public int messagenum(Map<String, Object> req) throws Exception {
		return new MessageService().messagenum(req);
	}
}
