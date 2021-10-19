package cn.crtech.cooperop.setting.action;

import java.util.Map;

import cn.crtech.cooperop.application.authenticate.DisValidPermission;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.workflow.workflow;
import cn.crtech.cooperop.setting.service.TaskService;

@DisValidPermission
public class TaskAction extends BaseAction {
	
	public Result queryTasking(Map<String, Object> req) throws Exception {
		return new TaskService().queryTasking(req);
	}
	
	public Map<String, Object> finish(Map<String, Object> req) throws Exception {
		workflow.terminate(null, (String)req.get("order_id"));
		return req;
	}
	
	public Map<String, Object> urgep(Map<String, Object> req) throws Exception {
		new TaskService().urgep(req);
		return req;
	}
}
