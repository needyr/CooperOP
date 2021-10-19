package cn.crtech.cooperop.setting.action;

import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.application.authenticate.DisValidPermission;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.setting.service.DepService;

public class DepAction extends BaseAction{
	@DisValidPermission
	public List<Record> query(Map<String, Object> req) throws Exception{
		return new DepService().query(req).getResultset();
	}
	@DisValidPermission
	public Result querydep(Map<String, Object> req) throws Exception{
		return new DepService().querydep(req);
	}
	public Map<String, Object> save(Map<String, Object> req) throws Exception{
		if(!CommonFun.isNe(req.get("id"))){
			int update = new DepService().update(req);
			if (update != -1){
				req.put("result", "add_success");
			}else{
				req.put("result", "update_fail");
			}
		}else{
			int save = new DepService().save(req);
			if (save > 0){
				req.put("result", "add_success");
			}else if (save  == -1){
				req.put("result", "add_fail");
			}else if (save  == -2){
				req.put("result", "code_repeat");
			}
		}
		return req;
	}
	public Map<String, Object> update(Map<String, Object> req) throws Exception{
		new DepService().update(req);
		req.put("result", "success");
		return req;
	}
	public Map<String, Object> delete(Map<String, Object> req) throws Exception{
		int i = new DepService().delete(req);
		if(i == -1){
			req.put("result", "N");
		}else{
			req.put("result", "success");
		}
		return req;
	}
	
	public Map<String, Object> add(Map<String, Object> req) throws Exception{
		Record r = new Record();
		if(!CommonFun.isNe(req.get("id"))){
			r.put("depart", new DepService().get(req));
		}
		return r;
	}
}
