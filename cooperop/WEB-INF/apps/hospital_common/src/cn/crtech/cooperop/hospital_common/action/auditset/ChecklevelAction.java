package cn.crtech.cooperop.hospital_common.action.auditset;

import java.util.Map;

import cn.crtech.cooperop.application.authenticate.DisLoggedIn;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.service.auditset.CheckLevelService;

public class ChecklevelAction extends BaseAction {

	@DisLoggedIn
	public Result query(Map<String, Object> params) throws Exception {
		return new CheckLevelService().query(params);

	}

	@DisLoggedIn
	public int insert(Map<String, Object> params) throws Exception {
		return new CheckLevelService().insert(params);

	}

	@DisLoggedIn
	public int update(Map<String, Object> params) throws Exception {
		return new CheckLevelService().update(params);

	}

	@DisLoggedIn
	public int delete(Map<String, Object> params) throws Exception {
		return new CheckLevelService().delete(params);

	}

	@DisLoggedIn
	public Record edit(Map<String, Object> params) throws Exception {
		return new CheckLevelService().get(params);

	}
	
	@DisLoggedIn
	public Result queryListByIpc(Map<String, Object> params) throws Exception {
		return new CheckLevelService().queryListByIpc(params);

	}
	
	public Result queryListByImic(Map<String, Object> params) throws Exception {
		return new CheckLevelService().queryListByImic(params);

	}
}
