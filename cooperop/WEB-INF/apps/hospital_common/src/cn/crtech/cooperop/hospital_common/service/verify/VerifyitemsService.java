package cn.crtech.cooperop.hospital_common.service.verify;

import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.dao.verify.VerifyitemsDao;

public class VerifyitemsService extends BaseService{
	public List<Record> query(Map<String, Object> params) throws Exception {
		try {
			connect("guide");
			Result result = new VerifyitemsDao().query(params);
			return result.getResultset();
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}
	}
}
