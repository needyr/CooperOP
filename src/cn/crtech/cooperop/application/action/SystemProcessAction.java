package cn.crtech.cooperop.application.action;

import java.util.Map;

import cn.crtech.cooperop.application.authenticate.DisValidPermission;
import cn.crtech.cooperop.application.service.SystemProcessService;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Result;
@DisValidPermission
public class SystemProcessAction extends BaseAction {

	public Result query(Map<String, Object> req) throws Exception {
		return new SystemProcessService().query(req);
	}
	public Result queryNode(Map<String, Object> req) throws Exception {
		return new SystemProcessService().queryNode(req);
	}
}
