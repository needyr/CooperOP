package cn.crtech.cooperop.hospital_common.action;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.service.AssistConfigService;

public class AssistconfigAction extends BaseAction{

	public Result query(Map<String, Object> params) throws Exception {
		return new AssistConfigService().query(params);
	}
	
	public int moveUp(Map<String, Object> params) throws Exception {
		return new AssistConfigService().moveUp(params);
	}
	
	public int moveDown(Map<String, Object> params) throws Exception {
		return new AssistConfigService().moveDown(params);
	}
	
	public int updateShow(Map<String, Object> params) throws Exception {
		return new AssistConfigService().updateShow(params);
	}
	
	public int updateUrl(Map<String, Object> params) throws Exception {
		return new AssistConfigService().updateUrl(params);
	}
	

}
