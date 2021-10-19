package cn.crtech.cooperop.hospital_common.service;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.dao.SystemProductDao;

public class SystemProductService extends BaseService{

	public Result query(Map<String, Object> params) throws Exception {
		try {
			connect("base");
			return new SystemProductDao().query(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
}
