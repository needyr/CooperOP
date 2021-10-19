package cn.crtech.cooperop.application.action;

import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.application.authenticate.DisLoggedIn;
import cn.crtech.cooperop.application.authenticate.DisValidPermission;
import cn.crtech.cooperop.application.medication.Ypsms;
import cn.crtech.cooperop.application.service.CommonService;
import cn.crtech.cooperop.bus.cache.Dictionary;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
@DisValidPermission
public class CommonAction extends BaseAction {
	public static Result listProducts(Map<String, Object> req) throws Exception {
		return new CommonService().listProducts(req);
	}
	
	public static Result dictionary(Map<String, Object> req) throws Exception {
		Result rs = new Result();
		rs.setResultset(Dictionary.listOptions((String)req.get("field")));
		return rs;
	}
	
	@DisLoggedIn
	public String ypsms(Map<String, Object> req) throws Exception {
		Record params = new Record(req);
		return Ypsms.getYpsmsURL(params.getString("name")); //user().getId(), user().getTypeName(), 
	}
	
	public Map<String, Object> getTemplate(Map<String, Object> req) throws Exception {
		
		return null;
	}
}
