package cn.crtech.cooperop.hospital_common.service;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.dao.Pharmacist_check_passDao;

public class Pharmacist_check_passService extends BaseService{

	public Result query(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new Pharmacist_check_passDao().query(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}

	public Record get(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new Pharmacist_check_passDao().get(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}

	public void update(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			new Pharmacist_check_passDao().update(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}

	public void delete(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			new Pharmacist_check_passDao().delete(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}

}
