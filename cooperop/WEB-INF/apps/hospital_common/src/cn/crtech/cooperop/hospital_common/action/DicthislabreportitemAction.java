package cn.crtech.cooperop.hospital_common.action;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.service.DictHislabReportItemService;

public class DicthislabreportitemAction extends BaseAction {
	public Result  query_items(Map<String, Object> params) throws Exception {
		return new DictHislabReportItemService().query_items(params);
	}
}
