package cn.crtech.cooperop.application.service.tunnel;

import java.util.Map;

import cn.crtech.cooperop.application.dao.tunnel.EmailDao;
import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Result;

public class EmailService extends BaseService {
	public Result query(Map<String, Object> params) throws Exception {
		try {
			connect();
			return new EmailDao().query(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	
	public int insert(Map<String, Object> params) throws Exception {
		try {
			connect();
			return new EmailDao().insert(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	
	public Map<String, Object> get(Map<String, Object> params) throws Exception {
		try {
			connect();
			return new EmailDao().get(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public void update(Map<String, Object> params) throws Exception {
		try {
			connect();
			new EmailDao().update(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
}
