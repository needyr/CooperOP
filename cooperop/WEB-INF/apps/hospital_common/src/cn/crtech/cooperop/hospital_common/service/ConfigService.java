package cn.crtech.cooperop.hospital_common.service;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.dao.ConfigDao;


public class ConfigService extends BaseService{

	public Result query(Map<String, Object> params) throws Exception {
		try {
			connect("base");
			return new ConfigDao().query(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public Result queryCodes(Map<String, Object> params) throws Exception {
		try {
			connect("base");
			return new ConfigDao().queryCodes(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public Result queryProduct(Map<String, Object> params) throws Exception {
		try {
			connect("base");
			return new ConfigDao().queryProduct(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public int insert(Map<String, Object> params) throws Exception {
		try {
			connect("base");
			params.put("is_open", "Y");
			new ConfigDao().insert(params);
			return 1;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public int update(Map<String, Object> params) throws Exception {
		try {
			connect("base");
			new ConfigDao().update(params);
			return 1;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public int delete(Map<String, Object> params) throws Exception {
		try {
			connect("base");
			new ConfigDao().delete(params);
			return 1;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public Record get(Map<String, Object> params) throws Exception {
		try {
			connect("base");
			return new ConfigDao().get(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
}
