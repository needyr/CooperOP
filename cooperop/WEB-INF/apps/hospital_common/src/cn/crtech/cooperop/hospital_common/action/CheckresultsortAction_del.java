package cn.crtech.cooperop.hospital_common.action;

import java.util.Map;

import cn.crtech.cooperop.application.authenticate.DisLoggedIn;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.service.CheckResultsortService_del;
@DisLoggedIn
public class CheckresultsortAction_del extends BaseAction{

	
	public Result query(Map<String, Object> params) throws Exception {
		return new CheckResultsortService_del().query(params);
	}
	
	public int delete(Map<String, Object> params) throws Exception {
		return new CheckResultsortService_del().delete(params);
	}
	
	public Record edits(Map<String, Object> params) throws Exception {
		if(!CommonFun.isNe(params.get("sort_id"))&&!CommonFun.isNe(params.get("type"))){
			return new CheckResultsortService_del().get(params);
		}else{
			return null;
		}
		
	}
		
	public int save(Map<String, Object> params) throws Exception {
		if(CommonFun.isNe(params.get("sort_id_old"))){
			params.remove("sort_id_old");
			params.remove("type_old");
			return new CheckResultsortService_del().insert(params);
		}else {
			return new CheckResultsortService_del().update(params);
		}
	}
}
