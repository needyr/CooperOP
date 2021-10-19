package cn.crtech.cooperop.hospital_common.action.auditset;

import java.util.Map;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.service.auditset.RuleCtrlService;

public class RulectrlAction extends BaseAction{

	public Result query(Map<String, Object> params) throws Exception {
		return new RuleCtrlService().query(params);
	}
	
	public Map<String, Object> edit(Map<String, Object> params) throws Exception {
		return new RuleCtrlService().get(params);
	}
	
	public Map<String, Object> imic_list(Map<String, Object> params) throws Exception {
		 return new RuleCtrlService().queryLevel(params);
	}
	
	public int save(Map<String, Object> params) throws Exception {
		RuleCtrlService rcs = new RuleCtrlService();
		int code = 1;
		if(CommonFun.isNe(params.get("id"))) {
			//验证重复
			Record record = rcs.getByCondition(params);
			if(CommonFun.isNe(record)) {
				rcs.insert(params);
			}else {
				code = -1;
			}
		}else {
			new RuleCtrlService().update(params);
		}
		return code;
	}
	
	public int delete(Map<String, Object> params) throws Exception {
		if(CommonFun.isNe(params.get("id"))) {
			return -1;
		}else {
			return new RuleCtrlService().delete(params);
		}
	}
	
}
