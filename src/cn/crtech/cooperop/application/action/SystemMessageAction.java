package cn.crtech.cooperop.application.action;

import java.util.Map;
import cn.crtech.cooperop.application.authenticate.DisValidPermission;
import cn.crtech.cooperop.application.service.SystemMessageService;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Result;

@DisValidPermission
public class SystemMessageAction extends BaseAction {

	public Result query(Map<String, Object> req) throws Exception {
		return new SystemMessageService().query(req);
	}
	public Result messagenum(Map<String, Object> req) throws Exception {
		return new SystemMessageService().messagenum(req);
	}
	public Map ignoreAll(Map<String, Object> req) throws Exception {
		new SystemMessageService().ignoreAll(req);
		return req;
	}
}
