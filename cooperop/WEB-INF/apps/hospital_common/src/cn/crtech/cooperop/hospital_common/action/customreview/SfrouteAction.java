package cn.crtech.cooperop.hospital_common.action.customreview;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.service.SfRouteService;

public class SfrouteAction extends BaseAction{

	public Result query(Map<String, Object> params) throws Exception {
		
		return new SfRouteService().query(params);
	}
	
	public int insert(Map<String, Object> params) throws Exception {
		
		return new SfRouteService().insert(params);
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
			return new SfRouteService().insert(params);
		}else{
			return new SfRouteService().update(params);
		}
}
	
	public int delete(Map<String, Object> params) throws Exception {
		return new SfRouteService().delete(params);
	}
	
	public Record datiledit(Map<String, Object> params) throws Exception {
		
		if(!CommonFun.isNe(params.get("id"))){
			return new SfRouteService().get(params);
		}else {
			return null;
		}
	}
}
