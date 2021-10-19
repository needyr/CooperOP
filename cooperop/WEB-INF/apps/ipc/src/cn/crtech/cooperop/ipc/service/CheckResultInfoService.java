package cn.crtech.cooperop.ipc.service;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.ipc.dao.CheckResultInfoDao;

public class CheckResultInfoService extends BaseService{
	public String insert(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			return new CheckResultInfoDao().insert(params);
		} catch (Exception ex){
			throw ex;
		}finally{
			disconnect();
		}
	}
}
