package cn.crtech.cooperop.hospital_common.action.rule_maintenance;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.service.rule_maintenance.VerifyitemsService;


public class VerifyitemsAction extends BaseAction{
	public Result query(Map<String, Object> params) throws Exception {
		return new VerifyitemsService().query(params);
	}
	
	public void insert(Map<String, Object> params) throws Exception {
		if (CommonFun.isNe((String)params.get("table_name"))) {
			throw new RuntimeException("表名不能为空!");
		}
		if (CommonFun.isNe((String)params.get("field"))) {
			throw new RuntimeException("字段不能为空!");
		}
		if ("float".equals((String)params.get("field_type"))) {
			if (!isPositiveInteger((String)params.get("float_num"))) {
				throw new RuntimeException("小数点后位数输入异常!");
			}
		}
		
		new VerifyitemsService().insert(params);
	}
	
	public void update(Map<String, Object> params) throws Exception {
		if ("float".equals((String)params.get("field_type")) && !isPositiveInteger((String)params.get("float_num"))) {
			throw new RuntimeException("小数点后位数输入异常!");
		}
		new VerifyitemsService().update(params);
	}
	
	public void delete(Map<String, Object> params) throws Exception {
		new VerifyitemsService().delete(params);
	}
	
	public Map<String, Object> list(Map<String, Object> params) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("p_bh", params.get("parent_bh"));
		return map;
	}
	
	public Map<String, Object> edit(Map<String, Object> params) throws Exception {
		Map<String, Object> map;
		if (CommonFun.isNe(params.get("id"))) {
			map = new HashMap<String, Object>();
			map.put("parent_bh", params.get("p_bh"));
		}else {
			map = new VerifyitemsService().findbyid(params);
		}
		return map;
	}
	
	public static boolean isPositiveInteger(String tp) {
		if (tp == null || "".equals(tp)){
	        return false;
	    }

	    Pattern p;
	    Matcher m;
	    p = Pattern.compile("[0-9]*");
	    m = p.matcher(tp);
	    if (m.matches()){
	        return true;
	    }else{
	        return false;
	    }
	}
}
