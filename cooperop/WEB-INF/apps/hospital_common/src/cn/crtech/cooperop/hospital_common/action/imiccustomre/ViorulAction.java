package cn.crtech.cooperop.hospital_common.action.imiccustomre;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.service.imiccustomre.ViorulService;
public class ViorulAction extends BaseAction{

	public Result query(Map<String, Object> params) throws Exception {
		return new ViorulService().query(params);
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
			return new ViorulService().insert(params);
		}else{
			return new ViorulService().update(params);
		}
}
	
	public int delete(Map<String, Object> params) throws Exception {
		
		return new ViorulService().delete(params);
	}
	
	public Record datiledit(Map<String, Object> params) throws Exception {
		if(!CommonFun.isNe(params.get("id"))){
			return new ViorulService().get(params);
		}else {
			return null;
		}
	}
}
