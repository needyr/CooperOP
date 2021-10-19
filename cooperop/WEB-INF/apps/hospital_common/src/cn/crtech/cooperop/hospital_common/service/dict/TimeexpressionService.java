package cn.crtech.cooperop.hospital_common.service.dict;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.dao.dict.TimeexpressionDao;

public class TimeexpressionService extends BaseService{
	
	public Result query(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new TimeexpressionDao().query(params);
		} finally {
			disconnect();
		}
	}
	
	
	public int insert(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			Record record = new TimeexpressionDao().get(params);
			if (record==null) { 
				params.remove("p_key");
				return new TimeexpressionDao().insert(params);
			}else {
				return 2;
			}
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public int update(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			Record record = new TimeexpressionDao().get(params);
			if (record==null) { 
				return new TimeexpressionDao().update(params);
			}else {
				return 2;
			}

		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public int delete(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new TimeexpressionDao().delete(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	
	
	public Record get(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new TimeexpressionDao().get(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
}
