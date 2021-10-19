package cn.crtech.cooperop.hospital_common.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.hospital_common.dao.DictHisOperDao;

public class DictHisOperService extends BaseService {
	
	public Map<String, Object> searchCheck(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("oper", new DictHisOperDao().searchCheck(params).getResultset());
			return m;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}

	public Map<String, Object> search(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("oper", new DictHisOperDao().search(params).getResultset());
			return m;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	
	
	
	public List<Record> queryOperType(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new DictHisOperDao().queryOperType(params).getResultset();
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	
	public  List<Record> queryOperTypeCheck(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new DictHisOperDao().queryOperTypeCheck(params).getResultset();
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	
	
	
	public List<Record> queryOperName(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new DictHisOperDao().search(params).getResultset();
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	
	public  List<Record> queryOperNameCheck(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new DictHisOperDao().searchCheck(params).getResultset();
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	
	
}







