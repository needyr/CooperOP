package cn.crtech.cooperop.crdc.action;

import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.crdc.service.PopedomService;

public class PopedomAction extends BaseAction {

	public List<Record> query(Map<String, Object> req) throws Exception{
		return new PopedomService().query(req).getResultset();
	}
	
	public Map<String, Object> save(Map<String, Object> req) throws Exception{
		PopedomService p = new PopedomService();
		if(!CommonFun.isNe(p.get(req))){
			p.update(req);
		}else{
			p.save(req);
		}
		req.put("result", "success");
		return req;
	}
	public Map<String, Object> add(Map<String, Object> req) throws Exception{
		Record r = new Record();
		if(!CommonFun.isNe(req.get("id"))){
			r = new PopedomService().get(req);
		}else{
			r.put("system_popedom_id_parent", req.get("system_popedom_id_parent"));
		}
		return r;
	}
	public Map<String, Object> deletep(Map<String, Object> req) throws Exception{
		new PopedomService().delete(req);
		req.put("result", "success");
		return req;
	}
	public Map<String, Object> getMaxId(Map<String, Object> req) throws Exception{
		req.put("result", new PopedomService().getMaxId(req));
		return req;
	}
}
