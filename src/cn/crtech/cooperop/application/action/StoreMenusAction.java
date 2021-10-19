package cn.crtech.cooperop.application.action;

import java.util.Map;
import cn.crtech.cooperop.application.authenticate.DisValidPermission;
import cn.crtech.cooperop.application.service.StoreMenusService;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Result;

@DisValidPermission
public class StoreMenusAction extends BaseAction {
	
	public Result query(Map<String, Object> req) throws Exception {
		return new StoreMenusService().query(req);
	}

	public Map<String, Object> store(Map<String, Object> req) throws Exception {
		new StoreMenusService().save(req);
		return req;
	}

	public Map<String, Object> delStore(Map<String, Object> req) throws Exception {
		new StoreMenusService().delStore(req);
		return req;
	}
}
