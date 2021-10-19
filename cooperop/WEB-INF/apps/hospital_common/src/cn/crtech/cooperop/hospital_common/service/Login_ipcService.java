package cn.crtech.cooperop.hospital_common.service;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.dao.Login_ipcDao;

public class Login_ipcService extends BaseService{

	public Result queryMenu(Map<String, Object> map) throws Exception {
		try {
			connect("base");
			Result result = new Login_ipcDao().queryMenu(map);
			return result;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}

}
