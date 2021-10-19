package cn.crtech.cooperop.application.action;

import java.util.HashMap;
import java.util.Map;
import cn.crtech.cooperop.application.authenticate.DisValidPermission;
import cn.crtech.cooperop.application.service.StapleTabsService;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

@DisValidPermission
public class StapleTabsAction extends BaseAction {
	
	public Result query(Map<String, Object> req) throws Exception {
		return new StapleTabsService().query(req);
	}

	public Map<String, Object> staple(Map<String, Object> req) throws Exception {
		Map<String, Object> map = CommonFun.json2Object(req.get("data").toString(), Map.class);
		if("1".equals(map.remove("ding"))){
			map.put("params_str", CommonFun.object2Json(map.get("params_str")));
			new StapleTabsService().save(map);
		}else{
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("pageid", map.get("pageid"));
			new StapleTabsService().delete(param);
		}
		return map;
	}
}
