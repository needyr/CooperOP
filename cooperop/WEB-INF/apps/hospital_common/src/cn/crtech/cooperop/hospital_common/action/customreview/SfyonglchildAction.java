package cn.crtech.cooperop.hospital_common.action.customreview;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.service.DictdrugService;
import cn.crtech.cooperop.hospital_common.service.SfYonglChildService;
import cn.crtech.cooperop.hospital_common.service.SfYonglService;

public class SfyonglchildAction extends BaseAction{

	public Result query(Map<String, Object> params) throws Exception {
		
		return new SfYonglChildService().query(params);
	}
	
	public int insert(Map<String, Object> params) throws Exception {
		
		return new SfYonglChildService().insert(params);
	}
	
	public int save(Map<String, Object> params) throws Exception {
		if("大于".equals(params.get("formul"))) {
			params.put("formul", ">");
		}else if("小于".equals(params.get("formul"))) {
			params.put("formul", "<");
		}else if("等于".equals(params.get("formul"))) {
			params.put("formul", "=");
		}else if("不等于".equals(params.get("formul"))) {
			params.put("formul", "<>");
		}else if("类似".equals(params.get("formul"))) {
			params.put("formul", "like");
		}else if("不类似".equals(params.get("formul"))) {
			params.put("formul", "not like");
		}
		if(CommonFun.isNe(params.get("id"))){
			params.remove("id");
			return new SfYonglChildService().insert(params);
		}else{
			return new SfYonglChildService().update(params);
		}
}
	
	public int delete(Map<String, Object> params) throws Exception {
		
		return new SfYonglChildService().delete(params);
	}
	
	public Record datiledit(Map<String, Object> params) throws Exception {
		Record unit = new DictdrugService().getUnit(params);
		if(!CommonFun.isNe(params.get("id"))){
			Record record = new SfYonglChildService().get(params);
			record.put("value_unit_default", unit.get("use_dw"));
			return record;
		}else {
			Record record = new Record();
			record.put("value_unit_default", unit.get("use_dw"));
			return record;
		}
	}
	
	public Result shengfangzl_xm(Map<String, Object> params) throws Exception {
		return new SfYonglChildService().shengfangzl_xm(params);
	}
}
