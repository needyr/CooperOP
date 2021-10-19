package cn.crtech.cooperop.setting.action;

import java.util.Map;

import cn.crtech.cooperop.application.authenticate.DisValidPermission;
import cn.crtech.cooperop.bus.cache.SystemConfig;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.setting.service.SysconfigService;

public class SysconfigAction extends BaseAction{
	
	public Map<String, Object> query(Map<String, Object> req) throws Exception{
		return new SysconfigService().query(req);
	}
	
	public Map<String, Object> save(Map<String, Object> req) throws Exception{
		if(CommonFun.isNe(req.get("code"))){
			new SysconfigService().save(req);
		}else{
			new SysconfigService().update(req);
		}
		return req;
	}
	
	public Map<String, Object> saveAndRe(Map<String, Object> req) throws Exception{
		new SysconfigService().saveBatch(req);
		SystemConfig.load();
		return req;
	}
	
	public Map<String, Object> add(Map<String, Object> req) throws Exception{
		return new SysconfigService().get(req);
	}
	public Map<String, Object> qrcode(Map<String, Object> req) throws Exception{
		return new SysconfigService().getQrcode(req);
	}
	@DisValidPermission
	public Map<String, Object> reloadconfig(Map<String, Object> req) throws Exception{
		SystemConfig.load();
		return req;
	}
}
