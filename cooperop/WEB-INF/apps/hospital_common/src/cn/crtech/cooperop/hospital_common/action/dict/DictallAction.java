package cn.crtech.cooperop.hospital_common.action.dict;

import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.application.authenticate.DisLoggedIn;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.service.dict.DictAllService;
@DisLoggedIn
public class DictallAction extends BaseAction{

	public Result querynot(Map<String, Object> params) throws Exception {
		Result result = new DictAllService().querynot(params);
		List<Record> list=result.getResultset();
		
		return result;
	}
	
	public Result queryis(Map<String, Object> params) throws Exception {
		
		return new DictAllService().queryis(params);
	}
	
	public Result querysys(Map<String, Object> params) throws Exception {
		
		return new DictAllService().querysys(params);
	}
	
	public Result querymap(Map<String, Object> params) throws Exception {
		
		return new DictAllService().querymap(params);
	}
	
	public Map<String, Object> iflist(Map<String, Object> params) throws Exception {
		return new DictAllService().queryFields(params);
	}

	public int editss(Map<String, Object> params) throws Exception {
		
		return new DictAllService().update(params);
	}
	
	public Record edit(Map<String, Object> params) throws Exception {
		return new DictAllService().edit(params);
	}
	
	public void reMapNum(Map<String, Object> params) throws Exception {
		new DictAllService().reMapNum(params);
	}
}
