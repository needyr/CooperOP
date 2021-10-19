package cn.crtech.cooperop.pivascockpit.action;

import java.util.Map;

import cn.crtech.cooperop.bus.cache.Dictionary;
import cn.crtech.cooperop.bus.cache.SystemConfig;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.pivascockpit.service.ChartService;
import cn.crtech.cooperop.pivascockpit.service.CockpitService;

public class CockpitAction extends BaseAction {
	
	public Result query(Map<String, Object> req) throws Exception {
		return new CockpitService().query(req);
	}
	
	public Record edit(Map<String, Object> req) throws Exception {
		Record cockpit;
		if (CommonFun.isNe(req.get("id"))) {
			cockpit = new Record();
		} else {
			cockpit = new CockpitService().get(new Record(req).getInt("id"));
			cockpit.remove("json");
		}
		cockpit.put("supporter", SystemConfig.getSystemConfigValue("pivascockpit", "supporter"));
		cockpit.put("hotline", SystemConfig.getSystemConfigValue("pivascockpit", "hotline"));
		cockpit.put("weixin", SystemConfig.getSystemConfigValue("pivascockpit", "weixin"));
		cockpit.put("website", SystemConfig.getSystemConfigValue("pivascockpit", "website"));
		Record params = new Record();
		params.put("iscockpit", 1);
		params.put("sort", "name");
		params.put("limit", 0);
		cockpit.put("chartdefs", new ChartService().query(params).getResultset());
		cockpit.put("templates", Dictionary.listOptions("cockpit.templates"));
		cockpit.put("json", CommonFun.object2Json(cockpit));
		return cockpit;
	}
	
	public Map<String, Object> preview(Map<String, Object> req) throws Exception {
		return req;
	}
	
	public int save(Map<String, Object> req) throws Exception {
		return new CockpitService().save(req);
	}
	
	public int updateState(Map<String, Object> req) throws Exception {
		Record params = new Record(req);
		if (CommonFun.isNe(params.get("id")) || CommonFun.isNe(params.get("state"))) {
			throw new Exception("错误的请求");
		}
		return new CockpitService().updateState(params.getInt("id"), params.getInt("state"));
	}
	
	public int delete(Map<String, Object> req) throws Exception {
		if (CommonFun.isNe(req.get("id"))) {
			throw new Exception("错误的请求");
		}
		return new CockpitService().delete(new Record(req).getInt("id"));
	}
}
