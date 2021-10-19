package cn.crtech.cooperop.application.service.tunnel;

import java.util.Map;

import cn.crtech.cooperop.application.dao.tunnel.SmsDao;
import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Result;

public class SmsService extends BaseService {
	public Result query(Map<String, Object> params) throws Exception {
		try {
			connect();
			return new SmsDao().query(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	
	public void insert(Map<String, Object> params) throws Exception {
		try {
			connect();
			new SmsDao().insert(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	
	public Map<String, Object> get(Map<String, Object> params) throws Exception {
		try {
			connect("base");
			return new SmsDao().get(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public void update(Map<String, Object> params) throws Exception {
		try {
			connect();
			new SmsDao().update(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
}
