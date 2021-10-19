package cn.crtech.cooperop.hospital_common.service;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.dao.DictHislabReportItemDao;

public class DictHislabReportItemService extends BaseService {
	public Result query_items(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return  new DictHislabReportItemDao().search(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
}
