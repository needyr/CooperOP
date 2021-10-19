package cn.crtech.cooperop.application.service;

import java.util.Map;

import cn.crtech.cooperop.application.dao.SystemUserStoreMenusDao;
import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;

public class StoreMenusService extends BaseService {
	public Result query(Map<String, Object> params) throws Exception {
		try {
			connect();
			return new SystemUserStoreMenusDao().query(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public Record get(Map<String, Object> params) throws Exception {
		try {
			connect();
			SystemUserStoreMenusDao dd = new SystemUserStoreMenusDao();
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
			new SystemUserStoreMenusDao().insert(params);
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}
	}
	public void delStore(Map<String, Object> params) throws Exception {
		try {
			connect();
			new SystemUserStoreMenusDao().delete(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}

}
