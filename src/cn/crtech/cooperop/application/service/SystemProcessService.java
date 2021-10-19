package cn.crtech.cooperop.application.service;

import java.util.Map;

import cn.crtech.cooperop.application.dao.SystemProcessDao;
import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Result;

public class SystemProcessService extends BaseService {
	public Result query(Map<String, Object> params) throws Exception {
		try {
			connect();
			return new SystemProcessDao().query(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public Result queryNode(Map<String, Object> params) throws Exception {
		try {
			connect();
			return new SystemProcessDao().queryNode(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
}
