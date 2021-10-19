package cn.crtech.cooperop.hospital_common.action;

import java.util.Map;

import cn.crtech.cooperop.application.authenticate.DisLoggedIn;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.service.ChangeqdetailService;

public class ChangeqdetailAction extends BaseAction{

	@DisLoggedIn
	public Result query(Map<String, Object> params) throws Exception {
		Result result = new ChangeqdetailService().query(params);
		return result;
	}
	
	@DisLoggedIn
	public void init(Map<String, Object> params) throws Exception {
		new ChangeqdetailService().init(params);
	}
	
	@DisLoggedIn
	public Result query_init(Map<String, Object> params) throws Exception {
		return new ChangeqdetailService().query_init(params);
	}
	
	@DisLoggedIn
	public void updateTmp(Map<String, Object> params) throws Exception {
		new ChangeqdetailService().updateTmp(params);
	}
	
	@DisLoggedIn
	public Result query_init_excel(Map<String, Object> params) throws Exception {
		return new ChangeqdetailService().query_init_excel(params);
	}
}
