package cn.crtech.cooperop.ipc.service;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.ipc.dao.SendErrorMsgDao;

public class SendErrorMsgService extends BaseService{

	public Result query(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			return new SendErrorMsgDao().query(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public void insert(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			new SendErrorMsgDao().insert(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public Record getShenFang(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			return new SendErrorMsgDao().getShenFang(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public void update(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			new SendErrorMsgDao().update(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
}
