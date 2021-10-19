package cn.crtech.cooperop.hospital_common.action.customreview;

import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.service.SfjjzService;

public class SfjjzAction extends BaseAction{

	public Result query(Map<String, Object> params) throws Exception {
		
		return new SfjjzService().query(params);
	}
	
	public Result query_mx(Map<String, Object> params) throws Exception {
		return new SfjjzService().query_mx(params);
	}
	
	public int insert(Map<String, Object> params) throws Exception {
		
		return new SfjjzService().insert(params);
	}
	
	public int save(Map<String, Object> params) throws Exception {
		if(CommonFun.isNe(params.get("id"))){
			params.remove("id");
			return new SfjjzService().insert(params);
		}else{
			return new SfjjzService().update(params);
		}
	}
	
	public void saveAll(Map<String, Object> params) throws Exception {
		new SfjjzService().saveAll(params);
	}
	
	public void save_mx_all(Map<String, Object> params) throws Exception {
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
		new SfjjzService().save_mx_all(params);
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
			return new SfjjzService().insert_mx(params);
		}else{
			return new SfjjzService().update_mx(params);
		}
	}
	
	public int delete(Map<String, Object> params) throws Exception {
		return new SfjjzService().delete(params);
	}
	
	public int delete_mx(Map<String, Object> params) throws Exception {
		return new SfjjzService().delete_mx(params);
	}
	
	public Record datiledit(Map<String, Object> params) throws Exception {
		
		if(!CommonFun.isNe(params.get("id"))){
			return new SfjjzService().get(params);
		}else {
			return null;
		}
	}
	
	public Record diagnosis(Map<String, Object> params) throws Exception {
		
		if(!CommonFun.isNe(params.get("id"))){
			return new SfjjzService().get_mx(params);
		}else {
			return null;
		}
	}
	
	public Map<String, List<Record>> queryItem(Map<String, Object> params) throws Exception {
		return new SfjjzService().queryItem(params);
	}
	
	public List<Record> queryOrther(Map<String, Object> params) throws Exception {
		return new SfjjzService().queryOrther(params);
	}
	
	public Record queryDiagnisis(Map<String, Object> params) throws Exception {
		return new SfjjzService().queryDiagnisis(params);
	}
	
	public Record queryJjzZd(Map<String, Object> params) throws Exception {
		return new SfjjzService().queryJjzZd(params);
	}
}
