package cn.crtech.cooperop.hospital_common.action.auditset;

import java.util.Map;

import cn.crtech.cooperop.application.authenticate.DisLoggedIn;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.service.auditset.MapchecklevelService;


public class MapchecklevelAction extends BaseAction {
	
	@DisLoggedIn
	public Result query(Map<String, Object> params) throws Exception {
		return new MapchecklevelService().query(params);

	}
	@DisLoggedIn
	public int insert(Map<String, Object> params) throws Exception {
		return new MapchecklevelService().insert(params);

	}
	@DisLoggedIn
	public int update(Map<String, Object> params) throws Exception {
		return new MapchecklevelService().update(params);

	}
	@DisLoggedIn
	public int delete(Map<String, Object> params) throws Exception {
		return new MapchecklevelService().delete(params);

	}
	@DisLoggedIn
	public Record edit(Map<String, Object> params) throws Exception {
		return new MapchecklevelService().get(params);

	}
	@DisLoggedIn
	public Result queryAllLevel(Map<String, Object> params) throws Exception {
		return new MapchecklevelService().queryAllLevel(params);

	}
}
