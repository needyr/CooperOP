package cn.crtech.cooperop.hospital_common.service;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.dao.AbaseDao;
import cn.crtech.cooperop.hospital_common.dao.DictHisDiagnosisDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AbaseService extends BaseService{

	
	public Result queryDept(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new AbaseDao().queryDept(params);
		} catch (Exception e) {
			throw e;
		}
		finally {
			disconnect();
		}
	}

	public Map<String, Object> searchField(Map<String, Object> params) throws Exception {
		try {
			connect((String)params.get("product"));
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("fields",new AbaseDao().searchField(params).getResultset());
			return map;
		} catch (Exception e) {
			throw e;
		}
		finally {
			disconnect();
		}
	}
	
	
	public List<Record> queryUsers(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new AbaseDao().queryUsers(params).getResultset();
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	
	public  List<Record> queryUsersCheck(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new AbaseDao().queryUsersCheck(params).getResultset();
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	
}
