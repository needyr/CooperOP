package cn.crtech.cooperop.hospital_common.service;

import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.dao.DictHisExamDao;

public class DictHisExamService extends BaseService {
	public Map<String, Object> search(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("exam", new DictHisExamDao().search(params).getResultset());
			return result;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}

	public Map<String, Object> searchCheck(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("exam", new DictHisExamDao().searchCheck(params).getResultset());
			return result;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public Result query_items(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return  new DictHisExamDao().search(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
}
