package cn.crtech.cooperop.ipc.service;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.ipc.dao.CheckResultDao;

public class CheckResultService extends BaseService {
	
	public String insert(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			return new CheckResultDao().insert(params);
		} catch (Exception ex){
			throw ex;
		}finally{
			disconnect();
		}
	}
}
