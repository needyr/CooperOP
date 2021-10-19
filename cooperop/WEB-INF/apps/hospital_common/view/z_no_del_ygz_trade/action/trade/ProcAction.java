package cn.crtech.cooperop.hospital_common.action.trade;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.service.trade.ProcService;

public class ProcAction extends BaseAction{

	public Result query(Map<String, Object> params) throws Exception {
		return new ProcService().query(params);
	}
	
	public int insert(Map<String, Object> params) throws Exception {
		new ProcService().insert(params);
		return 1;
	}
	
	public int update(Map<String, Object> params) throws Exception {
		new ProcService().update(params);
		return 1;
	}
	
	public int delete(Map<String, Object> params) throws Exception {
		if(CommonFun.isNe(params.get("proc_code"))) {
			return -1;
		}else {
			return new ProcService().delete(params);
		}
	}
	
	public Record edit(Map<String, Object> params) throws Exception {
		if(!CommonFun.isNe(params.get("proc_code"))){
			return new ProcService().get(params);
		}else {
			return null;
		}
	}
	
}
