package cn.crtech.cooperop.bus.mvc.service;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.mvc.dao.ViewDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;

public class ViewService extends BaseService {
	public Result query(Map<String, Object> params) throws Exception {
		try {
			connect();
			ViewDao dd = new ViewDao();
			return dd.query(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}

	public Record get(String type, String flag, String id, String system_product_code) throws Exception {
		try {
			connect();
			ViewDao dd = new ViewDao();
			return dd.get(type, flag, id,system_product_code);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}

	public int setDeploy(String type, String flag, String id,String system_product_code, int state) throws Exception {
		try {
			connect();
			ViewDao dd = new ViewDao();
			start();
			Record params = new Record();
			params.put("state", state);
			int i = dd.update(type, flag, id,system_product_code, params);
			commit();
			return i;
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}
	}
}
