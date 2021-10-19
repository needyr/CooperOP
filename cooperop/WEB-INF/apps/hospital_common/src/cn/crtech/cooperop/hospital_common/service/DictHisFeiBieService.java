package cn.crtech.cooperop.hospital_common.service;

import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.hospital_common.dao.DictHisFeiBieDao;

public class DictHisFeiBieService extends BaseService{
	public Map<String, Object> search(Map<String, Object> params) throws Exception {
		try {
				connect();
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("feibie", new DictHisFeiBieDao().search(params).getResultset());
				return m;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	
	public Map<String, Object> searchCheck(Map<String, Object> params) throws Exception {
		try {
				connect();
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("feibie", new DictHisFeiBieDao().searchCheck(params).getResultset());
				return m;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
}
