package cn.crtech.cooperop.application.action;

import java.util.Map;

import cn.crtech.cooperop.application.authenticate.DisValidPermission;
import cn.crtech.cooperop.application.service.ChartService;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Result;

@DisValidPermission
public class ChartAction extends BaseAction {

	public Map<String, Object> query(Map<String, Object> req) throws Exception {
		return new ChartService().initChart(req);
	}
	
	public Map<String, Object> queryPlotNum(Map<String, Object> req) throws Exception {
		return new ChartService().queryPlotNum(req);
	}
}
