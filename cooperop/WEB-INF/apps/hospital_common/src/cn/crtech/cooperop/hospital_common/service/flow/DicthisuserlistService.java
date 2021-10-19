package cn.crtech.cooperop.hospital_common.service.flow;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.dao.flow.DicthisuserlistDao;

public class DicthisuserlistService extends BaseService{
	public Result query(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new DicthisuserlistDao().query(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}
	}
}
