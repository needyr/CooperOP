package cn.crtech.cooperop.hospital_common.service.dictsys;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.dao.dictsys.DggradeDao;

public class DggradeService extends BaseService{

public Result query(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new DggradeDao().query(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}

	}

	public int insert(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new DggradeDao().insert(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}

	}

	public int update(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new DggradeDao().update(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}
	}

	public int delete(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new DggradeDao().delete(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}

	}

	public Record get(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new DggradeDao().get(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}

	}


}
