package cn.crtech.cooperop.hospital_common.service;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.dao.DictHisDeptDao;

public class DictHisDeptService extends BaseService{

	public Result query(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new DictHisDeptDao().query(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public void updateByCode(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			new DictHisDeptDao().updateByCode(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
}
