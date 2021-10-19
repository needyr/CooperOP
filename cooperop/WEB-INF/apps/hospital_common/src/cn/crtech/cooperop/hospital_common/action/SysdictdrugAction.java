package cn.crtech.cooperop.hospital_common.action;

import java.util.Map;

import cn.crtech.cooperop.application.authenticate.DisLoggedIn;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.service.SysDictDrugService;
@DisLoggedIn
public class SysdictdrugAction extends BaseAction{
	
	
	public Result queryzdysc(Map<String, Object> params) throws Exception {
		return new SysDictDrugService().queryzdysc(params);
	}
	
	public Result queryZdyAll(Map<String, Object> params) throws Exception {
		return new SysDictDrugService().queryZdyAll(params);
	}
	
	public Result querynotzdysc(Map<String, Object> params) throws Exception {
		return new SysDictDrugService().querynotzdysc(params);
	}
	
	//切换药品信息
	public Record getforzdy(Map<String, Object> params) throws Exception {
		return new SysDictDrugService().getforzdy(params);
	}
	
}
