package cn.crtech.cooperop.ipc.service;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.ipc.dao.BadpostDao;

public class BadpostService extends BaseService{

	public Result query(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			return new BadpostDao().query(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
}
