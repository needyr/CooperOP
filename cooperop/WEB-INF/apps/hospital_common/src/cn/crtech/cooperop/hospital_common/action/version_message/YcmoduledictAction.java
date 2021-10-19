package cn.crtech.cooperop.hospital_common.action.version_message;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.service.version_message.YcmoduledictService;

public class YcmoduledictAction extends BaseAction{
	public Result query(Map<String, Object> params) throws Exception {
		return new YcmoduledictService().query(params);
	}
	
	public void delete(Map<String, Object> params) throws Exception {
		new YcmoduledictService().delete(params);
	}
	
	public void insert(Map<String, Object> params) throws Exception {
		new YcmoduledictService().insert(params);
	}
	
	public Map<String, Object> edit(Map<String, Object> params) throws Exception {
		Map<String, Object> map = new YcmoduledictService().getByModuleid(params);
		return map;
	}
	
	public void update(Map<String, Object> params) throws Exception {
		new YcmoduledictService().update(params);
	}
}
