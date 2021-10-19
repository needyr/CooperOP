package cn.crtech.cooperop.application.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.application.authenticate.DisValidPermission;
import cn.crtech.cooperop.application.service.ContacterService;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

@DisValidPermission
public class ContacterAction extends BaseAction {
	
	public Result queryMine(Map<String, Object> req) throws Exception {
		return new ContacterService().queryMine(req);
	}

	public Result contacter_users(Map<String, Object> req) throws Exception {
		return new ContacterService().contacter_users(req);
	}
	public List<Record> contacterusers(Map<String, Object> req) throws Exception {
		return new ContacterService().contacter_users(req).getResultset();
	}
	public List<Record> contacters(Map<String, Object> req) throws Exception {
		return new ContacterService().contacters(req).getResultset();
	}
	public List<Record> contactersByIds(Map<String, Object> req) throws Exception {
		List<Map<String,Object>> list = (List<Map<String, Object>>) CommonFun.json2Object((String)req.get("contacter_ids"),List.class);
		if(list == null){
			return new ArrayList<Record>();
		}
		int a = list.size();
		if(a>0){
			String s[] = new String[a];
			for(int i=0;i<list.size();i++){
				s[i] = list.get(i).get("type")+""+list.get(i).get("id");
			}
			req.put("contacter_ids", s);
			return new ContacterService().contacters(req).getResultset();
		}else{
			return new ArrayList<Record>();
		}
	}
	public Map<String, Object> createGroup(Map<String, Object> req) throws Exception {
		Map<String, Object> params = CommonFun.json2Object((String)req.get("data"),Map.class);
		if(!CommonFun.isNe(params.get("id"))){
			new ContacterService().updateGroup(params);
		}else{
			new ContacterService().createGroup(params);
		}
		return req;
	}
	public Map<String, Object> group(Map<String, Object> req) throws Exception {
		if(!CommonFun.isNe(req.get("id"))){
			return new ContacterService().getGroup(req);
		}else{
			return req;
		}
	}
	public Map<String, Object> deleteGroup(Map<String, Object> req) throws Exception {
		new ContacterService().deleteGroup(req);
		return req;
	}
	public Map<String, Object> outGroup(Map<String, Object> req) throws Exception {
		req.put("system_user_id", user().getId());
		req.put("system_user_group_id",req.remove("id"));
		new ContacterService().deleteGroupUser(req);
		return req;
	}
}
