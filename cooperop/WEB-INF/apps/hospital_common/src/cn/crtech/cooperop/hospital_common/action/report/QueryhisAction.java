package cn.crtech.cooperop.hospital_common.action.report;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.service.report.QueryhisService;

public class QueryhisAction extends BaseAction{

	public Result query_orders(Map<String, Object> params) throws Exception {
		return new QueryhisService().query_orders(params);
	}
	
	public Record his_in_orders_list(Map<String, Object> params) throws Exception {
		return new QueryhisService().his_in_orders_list(params);
	}
}
