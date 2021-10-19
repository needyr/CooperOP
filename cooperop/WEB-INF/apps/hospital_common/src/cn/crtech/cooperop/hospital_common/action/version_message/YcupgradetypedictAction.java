package cn.crtech.cooperop.hospital_common.action.version_message;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.service.version_message.YcupgradetypedictService;

public class YcupgradetypedictAction extends BaseAction{
	public Result query(Map<String, Object> params) throws Exception {
		return new YcupgradetypedictService().query(params);
	}
	
	public void delete(Map<String, Object> params) throws Exception {
		new YcupgradetypedictService().delete(params);
	}
	
	public void insert(Map<String, Object> params) throws Exception {
		new YcupgradetypedictService().insert(params);
	}
	
	public Map<String, Object> edit(Map<String, Object> params) throws Exception {
		return new YcupgradetypedictService().getByTypeid(params);
	}
	
	public void update(Map<String, Object> params) throws Exception {
		new YcupgradetypedictService().update(params);
	}
}
