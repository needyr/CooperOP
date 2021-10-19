package cn.crtech.cooperop.hospital_common.service.report;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.dao.report.Drug_expend_monDao;

public class Drug_expend_monService extends BaseService{
	
	public Result query_dept(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new Drug_expend_monDao().query_dept(params);
		} catch (Exception e) {
			throw e;
		}
		finally {
			disconnect();
		}
	}

}
