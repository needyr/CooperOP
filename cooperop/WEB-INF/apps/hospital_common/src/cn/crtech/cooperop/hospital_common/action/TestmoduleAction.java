package cn.crtech.cooperop.hospital_common.action;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.hospital_common.service.TestmoduleService;

public class TestmoduleAction extends BaseAction{

	public String clientTestModule(Map<String, Object> params) throws Exception {
		return new TestmoduleService().clientTestModule(params);
	}
	
	public String clientTrade(Map<String, Object> params) throws Exception {
		return new TestmoduleService().clientTrade(params);
	}
	
	public Record client(Map<String, Object> params) throws Exception {
		return new TestmoduleService().client(params);
	}
	
	//
	public String serverTestModule(Map<String, Object> params) throws Exception {
		return new TestmoduleService().serverTestModule(params);
	}
	
	public String serverTrade(Map<String, Object> params) throws Exception {
		return new TestmoduleService().serverTrade(params);
	}
	
	public Record server(Map<String, Object> params) throws Exception {
		return new TestmoduleService().server(params);
	}
}
