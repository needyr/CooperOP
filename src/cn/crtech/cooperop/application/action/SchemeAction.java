package cn.crtech.cooperop.application.action;

import java.util.Map;

import cn.crtech.cooperop.application.authenticate.DisValidPermission;
import cn.crtech.cooperop.application.service.SchemeService;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
@DisValidPermission
public class SchemeAction extends BaseAction {
	public Map<String, Object> query(Map<String, Object> req) throws Exception {
		return new SchemeService().initQueryDialog(req);
	}
	public Map<String, Object> check_isModal(Map<String, Object> req) throws Exception {
		return new SchemeService().initQueryDialog(req);
	}
	public Result executeQueryScheme(Map<String, Object> req) throws Exception {
		return new SchemeService().executeQueryScheme(req);
	}
	public Result executeQuerySchemeList(Map<String, Object> req) throws Exception {
		return new SchemeService().schemelist(req);
	}
	
	public Map<String, Object> executeYmUpScheme(Map<String, Object> req) throws Exception {
		Map<String, Object> params = CommonFun.json2Object((String)req.get("data"),Map.class);
		return new SchemeService().executeYmUpScheme(params);
	}
	
	public Map<String, Object> executeDjSelectScheme(Map<String, Object> req) throws Exception {
		Map<String, Object> params = CommonFun.json2Object((String)req.get("data"),Map.class);
		return new SchemeService().executeDjSelectScheme(params);
	}
	public Result queryDjSelectSchememx(Map<String, Object> req) throws Exception {
		return new SchemeService().queryDjSelectSchememx(req);
	}
	public Map<String, Object> executeDjSelectSchememx(Map<String, Object> req) throws Exception {
		Map<String, Object> params = CommonFun.json2Object((String)req.get("data"),Map.class);
		return new SchemeService().executeDjSelectSchememx(params);
	}
}
