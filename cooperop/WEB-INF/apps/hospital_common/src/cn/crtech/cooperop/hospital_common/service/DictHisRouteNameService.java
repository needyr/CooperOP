package cn.crtech.cooperop.hospital_common.service;

import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.dao.DictHisRouteNameDao;

public class DictHisRouteNameService extends BaseService{

	public Result query(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new DictHisRouteNameDao().query(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public void updateByCode(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			new DictHisRouteNameDao().updateByCode(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public Map<String, Object> search(Map<String, Object> params) throws Exception {
		try {
				connect();
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("routename", new DictHisRouteNameDao().search(params).getResultset());
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
				m.put("routename", new DictHisRouteNameDao().searchCheck(params).getResultset());
				return m;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
}
