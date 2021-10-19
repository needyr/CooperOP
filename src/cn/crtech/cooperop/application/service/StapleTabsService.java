package cn.crtech.cooperop.application.service;

import java.util.Map;

import cn.crtech.cooperop.application.dao.SystemUserStapleTabsDao;
import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;

public class StapleTabsService extends BaseService {
	public Result query(Map<String, Object> params) throws Exception {
		try {
			connect();
			return new SystemUserStapleTabsDao().query(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public Record get(Map<String, Object> params) throws Exception {
		try {
			connect();
			SystemUserStapleTabsDao dd = new SystemUserStapleTabsDao();
			return dd.get(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}

	public void save(Map<String, Object> params) throws Exception {
		try {
			connect();
			new SystemUserStapleTabsDao().insert(params);
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}
	}
	public void delete(Map<String, Object> params) throws Exception {
		try {
			connect();
			new SystemUserStapleTabsDao().delete(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}

}
