package cn.crtech.cooperop.pivascockpit.action;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.pivascockpit.service.ChartService;

public class ChartAction extends BaseAction {
	
	public Result query(Map<String, Object> req) throws Exception {
		return new ChartService().query(req);
	}
	
	public Record edit(Map<String, Object> req) throws Exception {
		if (CommonFun.isNe(req.get("code"))) {
			return new Record();
		}
		return new ChartService().get((String)req.get("code"));
	}
	
	public int save(Map<String, Object> req) throws Exception {
		if (CommonFun.isNe(req.get("code")) || CommonFun.isNe(req.get("name")) || CommonFun.isNe(req.get("refresh_time")) || CommonFun.isNe(req.get("iscockpit")) || CommonFun.isNe(req.get("cockpitsql")) || CommonFun.isNe(req.get("fullsql"))) {
			throw new Exception("错误的请求");
		}
		return new ChartService().save((String)req.remove("code"), req);
	}
}
