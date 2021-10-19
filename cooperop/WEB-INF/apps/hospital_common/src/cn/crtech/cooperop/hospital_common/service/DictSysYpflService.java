package cn.crtech.cooperop.hospital_common.service;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.dao.DictSysYpflDao;

public class DictSysYpflService extends BaseService {
	
	public Result query(Map<String, Object> params) throws Exception {
		try {
				connect("hospital_common");
				return new DictSysYpflDao().query(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
}
