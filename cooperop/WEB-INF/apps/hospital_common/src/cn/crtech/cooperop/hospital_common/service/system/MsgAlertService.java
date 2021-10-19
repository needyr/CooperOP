package cn.crtech.cooperop.hospital_common.service.system;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.dao.system.MsgAlertDao;

public class MsgAlertService extends BaseService{
	
	public Result queryMsg(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new MsgAlertDao().query(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	} 

	public Record queryMsgCount(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new MsgAlertDao().queryCount(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public Record getMsgToUser(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new MsgAlertDao().getMsgToUser(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public Result queryMsgToUser(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new MsgAlertDao().queryMsgToUser(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public Result queryAllMsg(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new MsgAlertDao().queryAllMsg(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public void update(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			new MsgAlertDao().update(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	} 
	
	public void updateRead(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			new MsgAlertDao().updateRead(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public String insert(Map<String, Object> params) throws Exception {
		String rtn = "1";
		try {
			connect("hospital_common");
			rtn = new MsgAlertDao().insert(params);
			return rtn;
		} catch (Exception e) {
			//rtn = e.getMessage();
			rtn= "-1";
			throw e;
		}finally {
			disconnect();
		}
	} 
}
