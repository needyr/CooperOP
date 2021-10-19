package cn.crtech.cooperop.setting.action;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.setting.service.OrganizationService;

public class OrganizationAction extends BaseAction{

	public int insert(Map<String, Object> params) throws Exception {
		return new OrganizationService().insert(params);
	}
	
	public int update(Map<String, Object> params) throws Exception {
		return new OrganizationService().update(params);
	}
	
	public Result query(Map<String, Object> params) throws Exception {
		return new OrganizationService().query(params);
	}
	
	public Record edit(Map<String, Object> params) throws Exception {
		return new OrganizationService().get(params);
	}
	
	public int checkJigid(Map<String, Object> params) throws Exception {
		Record r = new OrganizationService().get(params);
		if (r == null || r.isEmpty()) {
			return 0;
		}
		
		return 1;
	}
	
}
