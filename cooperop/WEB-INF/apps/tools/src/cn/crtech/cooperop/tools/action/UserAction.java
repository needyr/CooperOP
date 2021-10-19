package cn.crtech.cooperop.tools.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.application.authenticate.DisValidPermission;
import cn.crtech.cooperop.application.service.ContacterService;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.tools.service.UserService;


public class UserAction extends BaseAction{
	public Map<String, Object> save(Map<String, Object> req) throws Exception{
		new UserService().save(req);
		req.put("result", "success");
		return req;
	}
}
