package cn.crtech.cooperop.hospital_common.action;

import java.util.Map;

import cn.crtech.cooperop.application.authenticate.DisLoggedIn;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.service.ImicitemflowService;
@DisLoggedIn
public class ImicitemflowAction extends BaseAction{
	
	public Result query(Map<String, Object> params) throws Exception {
		return new ImicitemflowService().query(params);
	}
	
	public Result querymx(Map<String, Object> params) throws Exception {
		return new ImicitemflowService().querymx(params);
	}
	public int saveflow(Map<String, Object> params) throws Exception {
		if(CommonFun.isNe(params.get("id"))){
			return new ImicitemflowService().addflow(params);
		}else{
			return new ImicitemflowService().updateflow(params);
		}
	}
	public int delete(Map<String, Object> params) throws Exception {
		return new ImicitemflowService().delete(params);
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
			return new ImicitemflowService().insertmx(params);
		}else{
			return new ImicitemflowService().updatemx(params);
		}
	}
	public int deletemx(Map<String, Object> params) throws Exception {
		return new ImicitemflowService().deletemx(params);
	}
	//切换项目信息
	public Record getItem(Map<String, Object> params) throws Exception {
		if(!CommonFun.isNe(params.get("item_code"))) {
			return new ImicitemflowService().getItem(params);
		}else {
			return null;
		}
	}
	
	public Record edit(Map<String, Object> params) throws Exception {
		if(!CommonFun.isNe(params.get("id"))) {
			return new ImicitemflowService().get(params);
		}else {
			return null;
		}
	}
	
	public Record mxedit(Map<String, Object> params) throws Exception {
		if(!CommonFun.isNe(params.get("id"))) {
			return new ImicitemflowService().getmx(params);
		}else {
			return null;
		}
	}
	
}
