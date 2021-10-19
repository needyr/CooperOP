package cn.crtech.cooperop.application.action;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.application.authenticate.Authenticate;
import cn.crtech.cooperop.application.authenticate.DisValidPermission;
import cn.crtech.cooperop.application.service.BillService;
import cn.crtech.cooperop.bus.engine.Engine;
import cn.crtech.cooperop.bus.engine.bean.ProcessBean;
import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.LocalThreadMap;

@DisValidPermission
public class ExcelAction extends BaseAction {
	public Result export(Map<String, Object> req) throws Exception {
		return new BillService().queryTable(req);
	}
	public Result export1(Map<String, Object> req) throws Exception {
		try {
			String pageid = (String) req.get("action_url");
			LocalThreadMap.put("pageid", pageid);
			Map<String, Object> action = Authenticate.getAction(pageid);
		
			Class<?> c = (Class<?>) action.get("class");
			Method m = (Method) action.get("method");
			Map<String, Object> map = new HashMap<String, Object>();
			map.putAll(req);
			map.put("pageid", pageid);
			Object t = Engine.callAction(c, m, map);
			Result res = (Result) t;
			return res;
		} catch (Exception e) {
			log.error("frame engine call action error.", e);
			throw e;
		}finally{
			
		}
	}
	public Map<String, Object> excelProcess(Map<String, Object> map) throws Exception {
		ProcessBean processBean = (ProcessBean)session().get(map.get("$seq").toString());
		map.put("processBean", processBean);
		return map;
	}
}
