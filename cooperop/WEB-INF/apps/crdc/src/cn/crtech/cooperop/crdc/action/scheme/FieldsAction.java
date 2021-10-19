package cn.crtech.cooperop.crdc.action.scheme;

import java.util.Map;

import cn.crtech.cooperop.application.authenticate.DisLoggedIn;
import cn.crtech.cooperop.application.authenticate.DisValidPermission;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.crdc.service.scheme.FieldsService;
@DisLoggedIn
@DisValidPermission
public class FieldsAction {
	public Result list(Map<String, Object> req) throws Exception {
		return new FieldsService().query(req);
	}
}
