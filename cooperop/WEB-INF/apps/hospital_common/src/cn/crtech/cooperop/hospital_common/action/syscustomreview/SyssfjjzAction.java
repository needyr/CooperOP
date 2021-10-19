package cn.crtech.cooperop.hospital_common.action.syscustomreview;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.service.syscustomreview.SysSfjjzService;

public class SyssfjjzAction extends BaseAction{

	public Result query(Map<String, Object> params) throws Exception {
		
		return new SysSfjjzService().query(params);
	}
	
	public Result query_mx(Map<String, Object> params) throws Exception {
		return new SysSfjjzService().query_mx(params);
	}
	
	public int insert(Map<String, Object> params) throws Exception {
		
		return new SysSfjjzService().insert(params);
	}
	
	public int save(Map<String, Object> params) throws Exception {
		if(CommonFun.isNe(params.get("id"))){
			params.remove("id");
			return new SysSfjjzService().insert(params);
		}else{
			return new SysSfjjzService().update(params);
		}
	}
	
	public void saveAll(Map<String, Object> params) throws Exception {
		new SysSfjjzService().saveAll(params);
	}
	
	//
	public int save_mx(Map<String, Object> params) throws Exception {
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
			return new SysSfjjzService().insert_mx(params);
		}else{
			return new SysSfjjzService().update_mx(params);
		}
	}
	
	public int delete(Map<String, Object> params) throws Exception {
		return new SysSfjjzService().delete(params);
	}
	
	public int delete_mx(Map<String, Object> params) throws Exception {
		return new SysSfjjzService().delete_mx(params);
	}
	
	public Record datiledit(Map<String, Object> params) throws Exception {
		
		if(!CommonFun.isNe(params.get("id"))){
			return new SysSfjjzService().get(params);
		}else {
			return null;
		}
	}
	
	public Record diagnosis(Map<String, Object> params) throws Exception {
		
		if(!CommonFun.isNe(params.get("id"))){
			return new SysSfjjzService().get_mx(params);
		}else {
			return null;
		}
	}
	
	
	public Record queryJjzZd(Map<String, Object> params) throws Exception {
		return new SysSfjjzService().queryJjzZd(params);
	}
}
