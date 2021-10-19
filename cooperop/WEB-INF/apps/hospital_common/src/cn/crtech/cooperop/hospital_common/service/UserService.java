package cn.crtech.cooperop.hospital_common.service;

import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.dao.UserDao;

public class UserService extends BaseService{
	public Map<String, Object> search(Map<String, Object> params) throws Exception {
		try {
				connect("hospital_common");
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("doctor", new UserDao().search(params).getResultset());
				return m;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}

	public Result queryUsers(Record params) throws Exception {
		try {
			connect("hospital_common");
			Map<String, Object> m = new HashMap<String, Object>();
			return new UserDao().queryUsers(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}

	public Map<String, Object> searchCheck(Map<String, Object> params) throws Exception {
		try {
				connect("hospital_common");
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("doctor", new UserDao().searchCheck(params).getResultset());
				return m;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
}
