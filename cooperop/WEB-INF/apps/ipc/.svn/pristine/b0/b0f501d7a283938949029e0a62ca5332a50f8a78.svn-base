package cn.crtech.cooperop.ipc.service;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.ipc.dao.CheckDataParmsDao;

public class CheckDataParmsService extends BaseService{

	public void insert(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			new CheckDataParmsDao().insert(params);
		} finally {
			disconnect();
		}
	}
	
}
