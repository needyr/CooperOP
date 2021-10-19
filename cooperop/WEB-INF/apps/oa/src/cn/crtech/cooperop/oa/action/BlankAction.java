package cn.crtech.cooperop.oa.action;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.oa.service.BlankService;
public class BlankAction extends BaseAction{

	public Result query(Map<String, Object> req) throws Exception {
		return new BlankService().query(req);
	}

	public Map<String, Object> get(Map<String, Object> req) throws Exception {
		return new BlankService().get(req);
	}
	
	public Map<String, Object> add(Map<String, Object> req) throws Exception {
		if(CommonFun.isNe(req.get("id"))){
			return req;
		}else{
			return new BlankService().get(req);
		}
	}
	
	public Record insert(Map<String, Object> req) throws Exception {
		return new BlankService().insert(req);
	}
	
	public Record update(Map<String, Object> req) throws Exception {
		return new BlankService().update(req);
	}
	
	public Record delete(Map<String, Object> req) throws Exception {
		return new BlankService().delete(req);
	}

}
