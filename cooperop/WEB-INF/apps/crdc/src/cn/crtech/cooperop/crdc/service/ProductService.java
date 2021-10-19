package cn.crtech.cooperop.crdc.service;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.mvc.view.ViewCreater;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.crdc.dao.ProductDao;

public class ProductService extends BaseService {
	public Result query(Map<String, Object> params) throws Exception {
		try {
			connect();
			ProductDao dd = new ProductDao();
			return dd.query(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}

	public Record get(String code) throws Exception {
		try {
			connect();
			ProductDao dd = new ProductDao();
			return dd.get(code);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}

	public int insert(Map<String, Object> params) throws Exception {
		try {
			connect();
			ProductDao dd = new ProductDao();
			start();
			int i = dd.insert(params);
			commit();
			return i;
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}
	}

	public int update(String code, Map<String, Object> params) throws Exception {
		try {
			connect();
			ProductDao dd = new ProductDao();
			start();
			int i = dd.update(code, params);
			commit();
			return i;
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}
	}

	public int delete(String code) throws Exception {
		try {
			connect();
			ProductDao dd = new ProductDao();
			start();
			int i = dd.delete(code);
			commit();
			return i;
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}
	}
	
	public void redeployAll(String code) throws Exception {
		ViewCreater.create(code);
	}

}
