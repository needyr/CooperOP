package cn.crtech.cooperop.hospital_common.action.dict;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.service.dict.DicSysUrlService;

public class DictsysurlAction extends BaseAction{

	public Result query(Map<String, Object> params) throws Exception {
		return new DicSysUrlService().query(params);
	}
	
	public int insert(Map<String, Object> params) throws Exception {
		new DicSysUrlService().insert(params);
		return 1;
	}
	
	public int update(Map<String, Object> params) throws Exception {
		new DicSysUrlService().update(params);
		return 1;
	}
	
	public int delete(Map<String, Object> params) throws Exception {
		if(CommonFun.isNe(params.get("id"))) {
			return -1;
		}else {
			return new DicSysUrlService().delUpdate(params);
		}
	}
	
	public Record edit(Map<String, Object> params) throws Exception {
		if(!CommonFun.isNe(params.get("id"))){
			return new DicSysUrlService().get(params);
		}else {
			return null;
		}
	}
}
