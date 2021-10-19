package cn.crtech.cooperop.hospital_common.action;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.service.DictHisExamService;

public class DicthisexamAction extends BaseAction {

	public Map<String, Object>  search(Map<String, Object> params) throws Exception {
		return new DictHisExamService().search(params);
	}
	
	public Map<String, Object>  searchCheck(Map<String, Object> params) throws Exception {
		return new DictHisExamService().searchCheck(params);
	}
	
	public Result  query_items(Map<String, Object> params) throws Exception {
		return new DictHisExamService().query_items(params);
	}
}
