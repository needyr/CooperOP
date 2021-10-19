package cn.crtech.cooperop.hospital_common.service.report;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.dao.report.QueryhisDao;

public class QueryhisService  extends BaseService{

	public Result query_orders(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new QueryhisDao().query_orders(params);
		} catch (Exception e) {
			throw e;
		}
		finally {
			disconnect();
		}
	}
	
	
	public Record his_in_orders_list(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new QueryhisDao().his_in_orders_list(params);
		} catch (Exception e) {
			throw e;
		}
		finally {
			disconnect();
		}
	}
}
