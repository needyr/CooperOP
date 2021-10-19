package cn.crtech.cooperop.hospital_common.service;

import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.hospital_common.dao.Dict_sys_drug_tagDao;

public class Dict_sys_drug_tagService extends BaseService {

	public Map<String, Object> search(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("tags", new Dict_sys_drug_tagDao().search(params).getResultset());
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
			result.put("tags", new Dict_sys_drug_tagDao().searchCheck(params).getResultset());
			return result;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
}
