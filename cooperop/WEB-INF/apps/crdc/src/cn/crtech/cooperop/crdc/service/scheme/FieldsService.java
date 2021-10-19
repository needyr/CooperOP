package cn.crtech.cooperop.crdc.service.scheme;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.crdc.dao.scheme.FieldsDao;

public class FieldsService extends BaseService {
	
	public Result query(Map<String, Object> map) throws Exception {
		try {
			connect();
			return new FieldsDao().query(map);
		} catch (Exception e) {
			throw e;
		}finally{
			disconnect();
		}
		   
	}
}
