package cn.crtech.cooperop.hospital_common.action;

import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.service.AbaseService;
import cn.crtech.cooperop.hospital_common.service.DictHisDiagnosisService;


public class AbaseAction extends BaseAction{

	public Result queryDept(Map<String, Object> params) throws Exception {
		return new AbaseService().queryDept(params);
	}

	public Map<String, Object> searchField(Map<String, Object> params) throws Exception {
		return new AbaseService().searchField(params);
	}
	
	/* 用户 */
	public List<Record> queryUsers(Map<String, Object> params) throws Exception {

		return new AbaseService().queryUsers(params);
	}

	public List<Record> queryUsersCheck(Map<String, Object> params) throws Exception {
		return new AbaseService().queryUsers(params);
	}

}
