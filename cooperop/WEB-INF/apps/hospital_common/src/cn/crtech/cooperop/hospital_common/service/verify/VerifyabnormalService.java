package cn.crtech.cooperop.hospital_common.service.verify;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.hospital_common.dao.verify.VerifyabnormalDao;

public class VerifyabnormalService extends BaseService{
	public void insert(Map<String, Object> params) throws Exception {
		try {
			connect("guide");
			start();
			new VerifyabnormalDao().insert(params);
			commit();
		} catch (Exception e) {
			rollback();
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public void update(Map<String, Object> params) throws Exception {
		try {
			connect("guide");
			start();
			new VerifyabnormalDao().update(params);
			commit();
		} catch (Exception e) {
			rollback();
			throw e;
		}finally {
			disconnect();
		}
	}
}
