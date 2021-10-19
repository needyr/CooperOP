package cn.crtech.cooperop.crdc.service;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.crdc.dao.DjasperDao;

public class DjasperService extends BaseService {
	public Result query(Map<String, Object> params) throws Exception {
		try {
			connect();
			DjasperDao dd = new DjasperDao();
			return dd.query(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public Record get(Map<String, Object> params) throws Exception {
		try {
			connect();
			DjasperDao dd = new DjasperDao();
			return dd.get(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}

	public int save(Map<String, Object> params) throws Exception {
		try {
			connect();
			DjasperDao dd = new DjasperDao();
			int i = dd.insert(params);
			return i;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public int update(Map<String, Object> params) throws Exception {
		try {
			connect();
			DjasperDao dd = new DjasperDao();
			int i = dd.update(params);
			return i;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public int delete(Map<String, Object> params) throws Exception {
		try {
			connect();
			DjasperDao dd = new DjasperDao();
			int i = dd.delete(params);
			return i;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}

}
