package cn.crtech.cooperop.hospital_common.action;

import java.util.Map;

import cn.crtech.cooperop.application.authenticate.DisLoggedIn;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.hospital_common.service.DictHisFeiBieService;

@DisLoggedIn
public class DicthisfeibieAction extends BaseAction{
	
	public Map<String, Object> search(Map<String, Object> params) throws Exception {
		return new DictHisFeiBieService().search(params);
	}
	
	public Map<String, Object> searchCheck(Map<String, Object> params) throws Exception {
		return new DictHisFeiBieService().searchCheck(params);
	}
	
}
