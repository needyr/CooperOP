package cn.crtech.precheck.service;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.precheck.dao.PatDao;

public class PatService extends BaseService{

	public void hisInTmpProc(Map<String, Object> params) {
		try {
			connect("hospital_common");
			new PatDao().hisInTmpProc(params);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		
	}
}
