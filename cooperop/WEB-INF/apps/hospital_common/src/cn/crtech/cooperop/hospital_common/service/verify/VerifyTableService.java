package cn.crtech.cooperop.hospital_common.service.verify;

import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.dao.guide.Dict_flowDao;
import cn.crtech.cooperop.hospital_common.dao.guide.IndexDao;
import cn.crtech.cooperop.hospital_common.dao.verify.VerifyTableDao;

public class VerifyTableService extends BaseService {
	public long tableTotal(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new VerifyTableDao().tableTotal(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}
	}
	
	public Result queryLimit(Map<String, Object> params) throws Exception {
		try {
			connect((String)params.get("product"));
			return new VerifyTableDao().queryLimit(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}
	}
	
	public Record repetitiveness(Map<String, Object> params) throws Exception {
		try {
			connect((String)params.get("product"));
			return new VerifyTableDao().repetitiveness(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}
	}
	
	public Record incompleteResults(Map<String, Object> params) throws Exception {
		try {
			connect((String)params.get("product"));
			return new VerifyTableDao().incompleteResults(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}
	}
	
	public Record uniteNullValue(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new VerifyTableDao().uniteNullValue(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}
	}

	public Record uniteNullValue_new(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new VerifyTableDao().uniteNullValue_new(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}
	}

	public long uniteNullValueTotal(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new VerifyTableDao().uniteNullValueTotal(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}
	}
	
	
	
	public void deletelog(Map<String, Object> params) throws Exception {
		try {
			connect("guide");
			Dict_flowDao dict_flowDao = new Dict_flowDao();
		    Map<String, Object> map = new HashMap<String, Object>();
		    map.put("info", "empty log record");
			start();
			new IndexDao().resetDictflow(params);
			new VerifyTableDao().empty(params);
			dict_flowDao.insertLog(map);
			commit();
		} catch (Exception e) {
			rollback();
			throw e;
		} finally {
			disconnect();
		}
	}
}
