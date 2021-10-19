package cn.crtech.cooperop.hospital_common.service;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.dao.DictSysYlflDao;

public class DictSysYlflService extends BaseService {
	
	public Result query(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new DictSysYlflDao().query(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}

	public void delete(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			new DictSysYlflDao().delete(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public Record get(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			DictSysYlflDao dd = new DictSysYlflDao();
			return dd.get(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	
	public int save(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			DictSysYlflDao dd = new DictSysYlflDao();
			int i = dd.insert(params);
			return i;
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}
	}
	
	public int update(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			DictSysYlflDao dd = new DictSysYlflDao();
			int i = dd.update(params);
			return i;
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}
	}
}
