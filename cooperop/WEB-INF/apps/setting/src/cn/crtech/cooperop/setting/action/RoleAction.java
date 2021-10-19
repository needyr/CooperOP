package cn.crtech.cooperop.setting.action;

import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.application.authenticate.DisValidPermission;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.setting.service.PopedomService;
import cn.crtech.cooperop.setting.service.RoleService;

public class RoleAction extends BaseAction{
	public Result query(Map<String, Object> req) throws Exception{
		return new RoleService().query(req);
	}
	@DisValidPermission
	public Result queryPersonByRole(Map<String, Object> req) throws Exception{
		return new RoleService().queryPersonByRole(req);
	}
	public List<Record> queryDeps(Map<String, Object> req) throws Exception{
		return new RoleService().queryDeps(req).getResultset();
	}
	public List<Record> queryPopedom(Map<String, Object> req) throws Exception{
		return new PopedomService().query(req).getResultset();
	}
	public List<Record> queryPopedomByRole(Map<String, Object> req) throws Exception{
		return new PopedomService().queryByRole(req).getResultset();
	}
	public Map<String, Object> save(Map<String, Object> req) throws Exception{
		if(!CommonFun.isNe(req.get("id"))){
			new RoleService().update(req);
		}else{
			new RoleService().save(req);
		}
		return req;
	}
	public Map<String, Object> savePopedom(Map<String, Object> req) throws Exception{
		Map<String, Object> params = CommonFun.json2Object((String)req.get("data"), Map.class);
		new RoleService().savePopedom(params);
		return req;
	}
	public Map<String, Object> setPopedom(Map<String, Object> req) throws Exception{
		Map<String, Object> params = CommonFun.json2Object((String)req.get("jdata"), Map.class);
		new RoleService().setPopedom(params);
		return req;
	}
	public Map<String, Object> setPopedoms(Map<String, Object> req) throws Exception{
		Map<String, Object> params = CommonFun.json2Object((String)req.get("jdata"), Map.class);
		new RoleService().setPopedoms(params);
		return req;
	}
	public Map<String, Object> update(Map<String, Object> req) throws Exception{
		new RoleService().update(req);
		return req;
	}
	public Map<String, Object> delete(Map<String, Object> req) throws Exception{
		new RoleService().delete(req);
		return req;
	}
	public Map<String, Object> deleteRule(Map<String, Object> req) throws Exception{
		new RoleService().deleteRules(req);
		return req;
	}
	public Map<String, Object> add(Map<String, Object> req) throws Exception{
		Record r = new Record();
		if(!CommonFun.isNe(req.get("id"))){
			r = new RoleService().get(req);
		}
		return r;
	}
}
