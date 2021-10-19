package cn.crtech.cooperop.hospital_common.service;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.dao.SysdeptDao;

public class SysdeptService extends BaseService {

	public Result queryTree(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			SysdeptDao dd = new SysdeptDao();
			Result queryTree = dd.queryTree(params);
			return queryTree;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
}
