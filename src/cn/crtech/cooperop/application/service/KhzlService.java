package cn.crtech.cooperop.application.service;

import java.util.Map;

import cn.crtech.cooperop.application.dao.KhzlDao;
import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;


public class KhzlService extends BaseService {
	public Result query(Map<String, Object> params) throws Exception {
		try{
			connect();
			Result r = new KhzlDao().query(params);
			return r;
		}catch(Exception e){
			throw e;
		}finally {
			disconnect();
		}
	}
}
