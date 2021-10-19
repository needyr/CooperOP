package cn.crtech.cooperop.hospital_common.service.report;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.dao.report.Jb_drug_useDao;

public class Jb_drug_useService extends BaseService {
	public Result query_stats(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new Jb_drug_useDao().query_stats(params);
		} catch (Exception e) {
			throw e;
		}
		finally {
			disconnect();
		}
	}
	
	public Result query_dept(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new Jb_drug_useDao().query_dept(params);
		} catch (Exception e) {
			throw e;
		}
		finally {
			disconnect();
		}
	}
	
	public Result query_doctor(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new Jb_drug_useDao().query_doctor(params);
		} catch (Exception e) {
			throw e;
		}
		finally {
			disconnect();
		}
	}
}
