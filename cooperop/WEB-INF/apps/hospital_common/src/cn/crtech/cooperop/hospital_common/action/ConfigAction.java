package cn.crtech.cooperop.hospital_common.action;

import java.util.Map;
import cn.crtech.cooperop.application.authenticate.DisLoggedIn;
import cn.crtech.cooperop.bus.cache.SystemConfig;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.service.ConfigService;

@DisLoggedIn
public class ConfigAction extends BaseAction{

	public Result query(Map<String, Object> params) throws Exception {
		return new ConfigService().query(params);
	}
	
	public Result queryCodes(Map<String, Object> params) throws Exception {
		return new ConfigService().queryCodes(params);
	}
	
	public Result queryProduct(Map<String, Object> params) throws Exception {
		return new ConfigService().queryProduct(params);
	}
	
	public int insert(Map<String, Object> params) throws Exception {
		new ConfigService().insert(params);
		SystemConfig.load();
		return 1;
	}
	
	public int update(Map<String, Object> params) throws Exception {
		new ConfigService().update(params);
		SystemConfig.load();
		return 1;
	}
	
	public int delete(Map<String, Object> params) throws Exception {
		if(CommonFun.isNe(params.get("code"))) {
			return -1;
		}else {
			return new ConfigService().delete(params);
		}
	}
	
	public Record edit(Map<String, Object> params) throws Exception {
		if(!CommonFun.isNe(params.get("code"))){
			return new ConfigService().get(params);
		}else {
			return null;
		}
	}
	
	public Record worktimeedit(Map<String, Object> params) throws Exception {
		if(!CommonFun.isNe(params.get("code"))){
			return new ConfigService().get(params);
		}else {
			return null;
		}
	}
}
