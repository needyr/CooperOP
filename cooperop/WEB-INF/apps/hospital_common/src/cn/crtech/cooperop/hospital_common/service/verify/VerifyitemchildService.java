package cn.crtech.cooperop.hospital_common.service.verify;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.dao.verify.VerifyitemchildDao;

public class VerifyitemchildService extends BaseService{
	public Result querybypid(Map<String, Object> params) throws Exception {
		try {
			connect("guide");
			return new VerifyitemchildDao().querybypid(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}
	}
}
