package cn.crtech.cooperop.hospital_common.action;

import java.util.Map;

import cn.crtech.cooperop.application.authenticate.DisLoggedIn;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.hospital_common.service.Check_repeatService;

public class Check_repeatAction extends BaseAction {
	
	@DisLoggedIn
	public Map<String, Object> hadSubmit(Map<String, Object> map) throws Throwable{
		return new Check_repeatService().hadSubmit(map);
	}
	
	@DisLoggedIn
	public Map<String, Object> hadSubmit_outp_deal(Map<String, Object> map) throws Throwable{
		return new Check_repeatService().hadSubmit_deal(map);
	}

}
