package cn.crtech.cooperop.hospital_common.service.dictsys;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.dao.dictsys.DgDao;

public class DgService extends BaseService {

	public Result query(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new DgDao().query(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}
	}
	
	public Result queryDictHisDiagnosis(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new DgDao().queryDictHisDiagnosis(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}
	}

	public Result queryByIcdAndRemark(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new DgDao().queryByIcdAndRemark(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}
	}

	public int insert(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new DgDao().insert(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}

	}

	public int update(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new DgDao().update(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}
	}

	public int delete(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new DgDao().delete(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}

	}

	public Record get(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new DgDao().get(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}

	}

}
