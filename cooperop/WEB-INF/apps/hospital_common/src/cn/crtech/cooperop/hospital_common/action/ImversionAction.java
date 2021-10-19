package cn.crtech.cooperop.hospital_common.action;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.service.VersionControlClientService;

public class ImversionAction extends BaseAction{
	
	public void capture(Map<String, Object> params) throws Exception {
		new VersionControlClientService().capture(params);
	}
	
	public Result queryVersionAll(Map<String, Object> params) throws Exception {
		return new VersionControlClientService().queryVersionAll(params);
	}
	
}
