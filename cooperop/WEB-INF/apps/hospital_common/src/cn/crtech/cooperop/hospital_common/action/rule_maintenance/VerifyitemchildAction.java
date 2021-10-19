package cn.crtech.cooperop.hospital_common.action.rule_maintenance;

import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.service.rule_maintenance.VerifyitemchildService;

public class VerifyitemchildAction extends BaseAction{
	
	public Result query(Map<String, Object> params) throws Exception {
		return new VerifyitemchildService().query(params);
	}
	
	public void insert(Map<String, Object> params) throws Exception {
		if (CommonFun.isNe((String)params.get("table_name"))) {
			throw new RuntimeException("表名不能为空!");
		}
		if (CommonFun.isNe((String)params.get("field"))) {
			throw new RuntimeException("字段不能为空!");
		}
		new VerifyitemchildService().insert(params);
	}
	
	public void delete(Map<String, Object> params) throws Exception {
		new VerifyitemchildService().delete(params);
	}
	
	public void update(Map<String, Object> params) throws Exception {
		new VerifyitemchildService().update(params);
	}
	
	public Map<String, Object> list(Map<String, Object> params) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("p_id", params.get("p_id"));
		return map;
	}
	
	public Map<String, Object> edit(Map<String, Object> params) throws Exception {
		Map<String, Object> map = null;
		if (CommonFun.isNe(params.get("id"))) {
			map = new HashMap<String, Object>();
			map.put("parent_id", params.get("p_id"));
		}else {
			map = new VerifyitemchildService().findbyid(params);
		}
		return map;
	}
	
}
