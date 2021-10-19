package cn.crtech.cooperop.hospital_common.action.dictextend;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.service.DictHisRouteNameService;

public class RoutenameAction extends BaseAction {
	public Result query(Map<String, Object> params) throws Exception {
		Result result = new DictHisRouteNameService().query(params);
		return result;
	}
	
	public void updateByCode(Map<String, Object> params) throws Exception {
		new DictHisRouteNameService().updateByCode(params);
	}
}
