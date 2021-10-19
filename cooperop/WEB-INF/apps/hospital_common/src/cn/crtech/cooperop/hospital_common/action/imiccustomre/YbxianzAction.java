package cn.crtech.cooperop.hospital_common.action.imiccustomre;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.service.auditset.CheckLevelService;
import cn.crtech.cooperop.hospital_common.service.imiccustomre.YbxianzService;

public class YbxianzAction extends BaseAction{

	public Result query(Map<String, Object> params) throws Exception {
		return new YbxianzService().query(params);
	}
	public Result queryWfprocess(Map<String, Object> params) throws Exception {
		return new YbxianzService().queryWfprocess(params);
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
			return new YbxianzService().insert(params);
		}else{
			return new YbxianzService().update(params);
		}
}
	
	public int delete(Map<String, Object> params) throws Exception {
		
		return new YbxianzService().delete(params);
	}
	
	public int deleteDia(Map<String, Object> params) throws Exception {
		return new YbxianzService().deleteDia(params);
	}
	
	public Record datiledit(Map<String, Object> params) throws Exception {
		if(!CommonFun.isNe(params.get("id"))){
			return new YbxianzService().get(params);
		}else {
			return null;
		}
	}
	
	public Record diainfo(Map<String, Object> params) throws Exception {
		if(!CommonFun.isNe(params.get("id"))){
			return new YbxianzService().getDia(params);
		}else {
			return null;
		}
	}
	
	public Result queryListByImic(Map<String, Object> params) throws Exception {
		return new CheckLevelService().queryListByImic(params);

	}
	
	public Result queryDias(Map<String, Object> params) throws Exception {
		return new YbxianzService().queryDias(params);
		
	}
	
	public int saveDia(Map<String, Object> params) throws Exception {
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
			return new YbxianzService().insertDia(params);
		}else{
			return new YbxianzService().updateDia(params);
		}
}
}
