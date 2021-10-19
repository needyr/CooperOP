package cn.crtech.cooperop.ipc.service;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.ipc.dao.PrecheckDao;

public class PrecheckService extends BaseService {

	public int updatePassword(Map<String, Object> params) throws Exception {
		try {
			connect();
			int a = new PrecheckDao().updatePassword(params);
			if(a==0){
				throw new Exception("原密码输入不正确！");
			}
			return 0;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}

}
