package cn.crtech.cooperop.hospital_common.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.dao.DictHisDiagnosisDao;
import cn.crtech.cooperop.hospital_common.dao.DictHisOperDao;

public class DictHisDiagnosisService extends BaseService{

	public Result query(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new DictHisDiagnosisDao().query(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public void updateByCode(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			new DictHisDiagnosisDao().updateByCode(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public void pipeiGRLX(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			new DictHisDiagnosisDao().pipeiGRLX(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public Map<String, Object> search(Map<String, Object> params) throws Exception {
		try {
				connect("hospital_common");
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("diagnosis", new DictHisDiagnosisDao().search(params).getResultset());
				return m;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	
	public Map<String, Object> searchCheck(Map<String, Object> params) throws Exception {
		try {
				connect("hospital_common");
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("diagnosis", new DictHisDiagnosisDao().searchCheck(params).getResultset());
				return m;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	
	/* 诊断 */	
	public List<Record> queryDiagnosis(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new DictHisDiagnosisDao().queryDiagnosis(params).getResultset();
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	
	public  List<Record> queryDiagnosisCheck(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new DictHisDiagnosisDao().queryDiagnosisCheck(params).getResultset();
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	
}
