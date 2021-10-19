package cn.crtech.cooperop.hospital_common.service.dictsys;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.dao.dictsys.DgpgjeDao;

public class DgpgjeService extends BaseService{

public Result query(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new DgpgjeDao().query(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}

	}


public Result queryInterfaceType(Map<String, Object> params) throws Exception {
	try {
		connect("hospital_common");
		return new DgpgjeDao().queryInterfaceType(params);
	} catch (Exception e) {
		throw e;
	} finally {
		disconnect();
	}

}

	public int insert(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new DgpgjeDao().insert(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}

	}

	public int update(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new DgpgjeDao().update(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}
	}

	public int delete(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new DgpgjeDao().delete(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}

	}

	public Record get(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new DgpgjeDao().get(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}

	}


}
