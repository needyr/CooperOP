package cn.crtech.cooperop.setting.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.application.authenticate.DisValidPermission;
import cn.crtech.cooperop.application.service.ContacterService;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.setting.service.UserService;

public class UserAction extends BaseAction{
	@DisValidPermission
	public List<Record> queryByDep(Map<String, Object> req) throws Exception{
		Map<String, Object> params = CommonFun.json2Object((String)req.get("data"), Map.class);
		return new UserService().queryByDep(params).getResultset();
	}
	@DisValidPermission
	public Result query(Map<String, Object> req) throws Exception{
		return new UserService().query(req);
	}
	@DisValidPermission
	public Result queryV(Map<String, Object> req) throws Exception{
		return new UserService().queryV(req);
	}
	@DisValidPermission
	public Result querymine(Map<String, Object> req) throws Exception{
		req.put("type", "U");
		return new ContacterService().queryMine(req);
	}
	public Map<String, Object> save(Map<String, Object> req) throws Exception{
		if(!CommonFun.isNe(req.get("id"))){
			new UserService().update(req);
		}else{
			new UserService().save(req);
		}
		req.put("result", "success");
		return req;
	}
	public Map<String, Object> update(Map<String, Object> req) throws Exception{
		new UserService().update(req);
		req.put("result", "success");
		return req;
	}
	public Map<String, Object> delete(Map<String, Object> req) throws Exception{
		new UserService().delete(req);
		req.put("result", "success");
		return req;
	}
	
	public Map<String, Object> add(Map<String, Object> req) throws Exception{
		Record r = new Record();
		if(!CommonFun.isNe(req.get("id"))){
			r.put("p", new UserService().get(req));
		}
		return r;
	}
	public Map<String, Object> resetpwd(Map<String, Object> req) throws Exception{
		UserService us =  new UserService();
		Record user = us.get(req);
		new cn.crtech.cooperop.application.action.AuthAction().removeValidMap(user.getString("no"));
		us.resetpwd(req);
		req.put("result", "success");
		return req;
	}
}
