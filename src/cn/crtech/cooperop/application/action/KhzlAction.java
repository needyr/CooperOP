package cn.crtech.cooperop.application.action;

import java.util.Map;

import cn.crtech.cooperop.application.authenticate.DisLoggedIn;
import cn.crtech.cooperop.application.service.KhzlService;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Result;

public class KhzlAction extends BaseAction {
	@DisLoggedIn
	public Result queryKHZL(Map<String, Object> map) throws Exception {
		return new KhzlService().query(map);
	}
}
