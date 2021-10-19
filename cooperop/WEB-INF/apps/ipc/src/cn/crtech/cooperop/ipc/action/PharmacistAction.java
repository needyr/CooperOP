package cn.crtech.cooperop.ipc.action;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.ipc.service.PharmacistService;

public class PharmacistAction extends BaseAction{

	public Result query(Map<String, Object> params) throws Exception {
		return new PharmacistService().query(params);
	}
	
	public void insert(Map<String, Object> params) throws Exception {
		 new PharmacistService().insert(params);
	}
	
	public int update(Map<String, Object> params) throws Exception {
		return new PharmacistService().update(params);
	}
	
	public int save(Map<String, Object> params) throws Exception {
		if(CommonFun.isNe(params.get("id"))){
			params.remove("id");
			 return new PharmacistService().insert(params);
		}else{
			 return new PharmacistService().update(params);
		}
    }
	
	public int delete(Map<String, Object> params) throws Exception {
		return new PharmacistService().delete(params);
	}
	
	public Record edit(Map<String, Object> params) throws Exception {
		if(!CommonFun.isNe(params.get("id"))){
			
				Record	r = new PharmacistService().get(params);
				return r;
		}else {
			return null;
		}
	}
	
	
	public Result queryHas(Map<String, Object> params) throws Exception {
		return new PharmacistService().queryHas(params);
	}
	
	/*public Result queryDept(Map<String, Object> params) throws Exception {
		return new PharmacistService().queryDept(params);
		
	}
	
	public Result querydoctor(Map<String, Object> params) throws Exception {
		return new PharmacistService().querydoctor(params);
	}
	
	public Record getcdoctor(Map<String, Object> params) throws Exception {
		return new PharmacistService().getcdoctor(params);
		
	}
*/
}
