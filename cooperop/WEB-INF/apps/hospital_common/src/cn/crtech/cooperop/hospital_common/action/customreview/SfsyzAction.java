package cn.crtech.cooperop.hospital_common.action.customreview;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.service.SfSyzService;

public class SfsyzAction extends BaseAction{

public Result query(Map<String, Object> params) throws Exception {
		
		return new SfSyzService().query(params);
	}
	
	public int insert(Map<String, Object> params) throws Exception {
		
		return new SfSyzService().insert(params);
	}
	
	public int save(Map<String, Object> params) throws Exception {
		if(CommonFun.isNe(params.get("id"))){
			params.remove("id");
			return new SfSyzService().insert(params);
		}else{
			return new SfSyzService().update(params);
		}
}
	
	public int delete(Map<String, Object> params) throws Exception {
		return new SfSyzService().delete(params);
	}
	
	public Record datiledit(Map<String, Object> params) throws Exception {
		
		if(!CommonFun.isNe(params.get("id"))){
			return new SfSyzService().get(params);
		}else {
			return null;
		}
	}
	
	public Result query_mx(Map<String, Object> params) throws Exception {
		return new SfSyzService().query_mx(params);
	}
	
	public int delete_mx(Map<String, Object> params) throws Exception {
		return new SfSyzService().delete_mx(params);
	}
	
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
			return new SfSyzService().insert_mx(params);
		}else{
			return new SfSyzService().update_mx(params);
		}
	}
	
	public Record diagnosis(Map<String, Object> params) throws Exception {
		if(!CommonFun.isNe(params.get("id"))){
			return new SfSyzService().get_mx(params);
		}else {
			return null;
		}
	}
	
	public Result batchQuery(Map<String, Object> params) throws Exception {
		return new SfSyzService().batchQuery(params);
	}
	
	public int batchSave(Map<String, Object> params) throws Exception {
			return new SfSyzService().batchInsert(params);
		
	}
}
