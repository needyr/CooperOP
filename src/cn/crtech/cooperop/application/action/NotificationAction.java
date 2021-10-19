package cn.crtech.cooperop.application.action;

import java.util.Map;

import cn.crtech.cooperop.application.authenticate.DisValidPermission;
import cn.crtech.cooperop.application.service.NotificationService;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;

@DisValidPermission
public class NotificationAction extends BaseAction {
	
	public Result queryMine(Map<String, Object> req) throws Exception {
		return new NotificationService().queryMine(req);
	}

	public Record detail(Map<String, Object> req) throws Exception {
		return new NotificationService().getMine(req);
	}

	public Record lastdetail(Map<String, Object> req) throws Exception {
		return new NotificationService().lastdetail(req);
	}

	public void saveRead(Map<String, Object> req) throws Exception {
		new NotificationService().saveRead(req);
	}

	public int notificationnum(Map<String, Object> req) throws Exception {
		return new NotificationService().queryNotificationCount(req);
	}
	
	public Result has_read(Map<String, Object> req) throws Exception {
		return new NotificationService().has_read(req);
	}
	
	public Result not_read(Map<String, Object> req) throws Exception {
		return new NotificationService().not_read(req);
	}
}
