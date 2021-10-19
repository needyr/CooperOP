package cn.crtech.cooperop.setting.action;

import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.application.authenticate.DisLoggedIn;
import cn.crtech.cooperop.application.authenticate.DisValidPermission;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.setting.service.PostService;

public class PostAction extends BaseAction{
	@DisValidPermission	//不验证该方法权限
	public Result query(Map<String, Object> req) throws Exception{
		return new PostService().query(req);
	}
	@DisValidPermission	//不验证该方法权限
	public Result queryMyDep(Map<String, Object> req) throws Exception{
		req.put("depids", user().getBaseDepCode());
		return new PostService().query(req);
	}
	public List<Record> queryByDep(Map<String, Object> req) throws Exception{
		return new PostService().query(req).getResultset();
	}
	public Result queryProperty(Map<String, Object> req) throws Exception{
		req.put("property", 1);
		return new PostService().queryDistinct(req);
	}
	public Map<String, Object> save(Map<String, Object> req) throws Exception{
		if(!CommonFun.isNe(req.get("id"))){
			new PostService().update(req);
		}else{
			new PostService().insert(req);
		}
		return req;
	}
	
	public Map<String, Object> add(Map<String, Object> req) throws Exception{
		if(!CommonFun.isNe(req.get("id"))){
			return new PostService().get(req);
		}else{
			return req;
		}
	}
	
	public Map<String, Object> detail(Map<String, Object> req) throws Exception{
		req.remove("ismodal");
		return new PostService().get(req);
	}
	
	public Map<String, Object> delete(Map<String, Object> req) throws Exception{
		new PostService().delete(req);
		return req;
	}
	
	public Result queryReq(Map<String, Object> req) throws Exception{
		return new PostService().queryReq(req);
	}
	public Map<String, Object> saveReq(Map<String, Object> req) throws Exception{
		new PostService().saveReq(req);
		return req;
	}
	public Map<String, Object> deleteReq(Map<String, Object> req) throws Exception{
		new PostService().deleteReq(req);
		return req;
	}
}
