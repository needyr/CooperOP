package cn.crtech.cooperop.application.action;

import java.util.Map;

import cn.crtech.cooperop.application.authenticate.DisValidPermission;
import cn.crtech.cooperop.application.service.WarnSettingService;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class WarnSettingAction extends BaseAction {

	public Result query(Map<String, Object> req) throws Exception{
		return new WarnSettingService().query(req);
	}
	public Map<String, Object> save(Map<String, Object> req) throws Exception{
		if(!CommonFun.isNe(req.get("id"))){
			new WarnSettingService().update(req);
		}else{
			new WarnSettingService().save(req);
		}
		return req;
	}
	public Map<String, Object> delete(Map<String, Object> req) throws Exception{
		new WarnSettingService().delete(req);
		return req;
	}
	public Map<String, Object> add(Map<String, Object> req) throws Exception{
		Record r = new Record();
		if(!CommonFun.isNe(req.get("id"))){
			r = new WarnSettingService().get(req);
		}
		return r;
	}
	@DisValidPermission
	public Map<String, Object> initWarn(Map<String, Object> req) throws Exception{
		return new WarnSettingService().initWarn(req);
	}
	@DisValidPermission
	public Result initWarnContent(Map<String, Object> map) throws Exception{
		return new WarnSettingService().initWarnContent(map);
	}
	
	public Map<String, Object> addsort(Map<String, Object> req) throws Exception{
		Record r = new Record();
		if(!CommonFun.isNe(req.get("id"))){
			r = new WarnSettingService().getSort(req);
		}
		return r;
	}
	public Map<String, Object> saveSort(Map<String, Object> req) throws Exception{
		if(!CommonFun.isNe(req.get("id"))){
			new WarnSettingService().updateSort(req);
		}else{
			new WarnSettingService().saveSort(req);
		}
		return req;
	}
	public Result querySort(Map<String, Object> req) throws Exception{
		return new WarnSettingService().querySort(req);
	}
	public Map<String, Object> deleteSort(Map<String, Object> req) throws Exception{
		new WarnSettingService().deleteSort(req);
		return req;
	}
}
