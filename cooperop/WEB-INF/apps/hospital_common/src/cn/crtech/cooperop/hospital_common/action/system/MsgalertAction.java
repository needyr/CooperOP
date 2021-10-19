package cn.crtech.cooperop.hospital_common.action.system;

import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.application.authenticate.DisLoggedIn;
import cn.crtech.cooperop.bus.cache.SystemConfig;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.ylz.MAEngine;

public class MsgalertAction extends BaseAction{

	@DisLoggedIn
	public Map<String, Object> queryMsg(Map<String, Object> params) throws Exception {
		Map<String, Object> rtn = new HashMap<String, Object>();
		try {
			params.put("send_to_user", user().getId());
			//params.put("send_to_user", "root");
			rtn.put("msgs", MAEngine.getMsg(user().getId()));
		} catch (Exception e) {
			// TODO: handle exception
		}
		return rtn;
	}
	
	@DisLoggedIn
	public Record queryMsgCount(Map<String, Object> params) throws Exception {
		return null;
		//params.put("send_to_user", params.get("uid"));
		//return new MsgAlertService().queryMsgCount(params);
	}
	
	@DisLoggedIn
	public void beRead(Map<String, Object> params) throws Exception {
		MAEngine.readOne(user().getId(), (String)params.get("id"));
	}
	
	@DisLoggedIn
	public Map<String, Object> index(Map<String, Object> params) throws Exception {
		Map<String, Object> rtn = new HashMap<String, Object>();
		rtn.put("url_hospital", SystemConfig.getSystemConfigValue("hospital_common", "local_server_address", "http://127.0.0.1:8085"));
		return rtn;
	}

	@DisLoggedIn
	public int testConnect(Map<String, Object> params) throws Exception {
		byte code = 1 ; // 状态码 1成功
		return code;
	}
}
