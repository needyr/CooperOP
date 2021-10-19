package cn.crtech.cooperop.setting.action;

import java.util.Map;

import cn.crtech.cooperop.setting.service.NotificationService;
import cn.crtech.cooperop.application.authenticate.DisValidPermission;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
public class NotificationAction extends BaseAction {
	
	public Result query(Map<String, Object> req) throws Exception {
		return new NotificationService().query(req);
	}

	public Record detail(Map<String, Object> req) throws Exception {
		int id = Integer.parseInt((String)req.get("id"));
		return new NotificationService().get(id);
	}

	public Record modify(Map<String, Object> req) throws Exception {
		if (!CommonFun.isNe(req.get("id"))) {
			int id = Integer.parseInt((String)req.get("id"));
			return new NotificationService().get(id);
		} else {
			return new Record();
		}
	}

	public void publish(Map<String, Object> req) throws Exception {
		int id = Integer.parseInt((String)req.get("id"));
		new NotificationService().publish(id);
	}

	public void unpublish(Map<String, Object> req) throws Exception {
		int id = Integer.parseInt((String)req.get("id"));
		new NotificationService().unpublish(id);
	}

	public void insert(Map<String, Object> req) throws Exception {
		@SuppressWarnings("unchecked")
		Map<String, Object> params = CommonFun.json2Object((String)req.get("jsondata"), Map.class);
		new NotificationService().insert(params);
	}

	public void update(Map<String, Object> req) throws Exception {
		@SuppressWarnings("unchecked")
		Map<String, Object> params = CommonFun.json2Object((String)req.get("jsondata"), Map.class);
		int id = Integer.parseInt((String)params.remove("id"));
		new NotificationService().update(id, params);
	}

	public void delete(Map<String, Object> req) throws Exception {
		int id = Integer.parseInt((String)req.get("id"));
		new NotificationService().delete(id);
	}

}
