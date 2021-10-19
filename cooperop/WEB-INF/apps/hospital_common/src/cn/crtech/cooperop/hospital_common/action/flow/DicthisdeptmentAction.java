package cn.crtech.cooperop.hospital_common.action.flow;

import java.util.Map;


import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.service.flow.DicthisdeptmentService;

public class DicthisdeptmentAction extends BaseAction{
	public Result query(Map<String, Object> params) throws Exception {
		Result result = new DicthisdeptmentService().query(params);
		return result;
	}
}
