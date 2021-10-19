package cn.crtech.cooperop.hospital_common.action;

import java.util.Map;

import cn.crtech.cooperop.application.authenticate.DisLoggedIn;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.service.CheckSortService_del;

@DisLoggedIn
public class ChecksortAction_del extends BaseAction{

	public Result query(Map<String, Object> params) throws Exception {
		
		return new CheckSortService_del().query(params);
	}
	
	public int delete(Map<String, Object> params) throws Exception {
		
		return new CheckSortService_del().delete(params);
	}
	
	public Record edits(Map<String, Object> params) throws Exception {
		
		if(!CommonFun.isNe(params.get("sort_id"))){
			return new CheckSortService_del().get(params);
		}else{
			return null;
		}
		
	}
	
	public int save(Map<String, Object> params) throws Exception {
		
		if(CommonFun.isNe(params.get("sort_id"))){
			return new CheckSortService_del().insert(params);
		}else {
			return new CheckSortService_del().update(params);
		}
	}
	
	public Result queryLevel(Map<String, Object> params) throws Exception {
		return new CheckSortService_del().queryLevel(params);
	}

}
