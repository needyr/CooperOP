package cn.crtech.cooperop.hospital_common.action;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.hospital_common.service.Dict_sys_drug_tagService;

public class Dict_sys_drug_tagAction extends BaseAction {
	
	public Map<String, Object>  search(Map<String, Object> params) throws Exception {
		return new Dict_sys_drug_tagService().search(params);
	}
	
	public Map<String, Object>  searchCheck(Map<String, Object> params) throws Exception {
		return new Dict_sys_drug_tagService().searchCheck(params);
	}

}
