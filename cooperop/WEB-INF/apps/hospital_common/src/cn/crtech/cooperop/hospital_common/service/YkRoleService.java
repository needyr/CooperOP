package cn.crtech.cooperop.hospital_common.service;

import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.hospital_common.dao.YkRoleDao;

public class YkRoleService extends BaseService{

	public Map<String, Object> search(Map<String, Object> params) throws Exception {
		try {
				connect("IADSCP");
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("role", new YkRoleDao().search(params).getResultset());
				return m;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	
	public Map<String, Object> searchCheck(Map<String, Object> params) throws Exception {
		try {
				connect("IADSCP");
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("role", new YkRoleDao().searchCheck(params).getResultset());
				return m;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
}
