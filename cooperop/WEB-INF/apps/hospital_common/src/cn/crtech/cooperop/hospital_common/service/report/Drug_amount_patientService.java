package cn.crtech.cooperop.hospital_common.service.report;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.dao.report.Drug_amount_patientDao;

public class Drug_amount_patientService extends BaseService {
	
	public Result query_stats(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new Drug_amount_patientDao().query_stats(params);
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
			return new Drug_amount_patientDao().query_dept(params);
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
			return new Drug_amount_patientDao().query_doctor(params);
		} catch (Exception e) {
			throw e;
		}
		finally {
			disconnect();
		}
	}
}
