package cn.crtech.cooperop.hospital_common.action;

import java.util.Map;

import cn.crtech.cooperop.application.authenticate.DisLoggedIn;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.hospital_common.service.DictHisRouteNameService;

@DisLoggedIn
public class DicthisroutenameAction extends BaseAction{
	
	public Map<String, Object> search(Map<String, Object> params) throws Exception {
		return new DictHisRouteNameService().search(params);
	}
	
	public Map<String, Object> searchCheck(Map<String, Object> params) throws Exception {
		return new DictHisRouteNameService().searchCheck(params);
	}
	
}
