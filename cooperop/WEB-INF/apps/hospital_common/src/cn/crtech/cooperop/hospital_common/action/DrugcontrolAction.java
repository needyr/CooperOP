package cn.crtech.cooperop.hospital_common.action;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.service.DrugControlService;

public class DrugcontrolAction extends BaseAction{
	
	public Result query(Map<String, Object> params) throws Exception {
		return new DrugControlService().query(params);
	}

	public Result queryByDrugCode(Map<String, Object> params) throws Exception {
		return new DrugControlService().queryByDrugCode(params);
	}
	
	/**
	 * @param params
	 * @return
	 * @throws Exception
	 * @function ipc审查提示等级
	 * @author yankangkang 2019年2月25日 下午2:08:18
	 */
	public Result queryCheckLevel(Map<String, Object> params) throws Exception {
		return new DrugControlService().queryCheckLevel(params);
	}
	
	
	public Result queryRegulation(Map<String, Object> params) throws Exception {
		return new DrugControlService().queryRegulation(params);
	}
	
	public int insert(Map<String, Object> params) throws Exception {
		
		return new DrugControlService().insert(params);
	}
	
	public int update(Map<String, Object> params) throws Exception {
		
		return new DrugControlService().update(params);
	}
	
	public int delete(Map<String, Object> params) throws Exception {
		
		return new DrugControlService().delete(params);
	}
	
	public Record departmentedit(Map<String, Object> params) throws Exception {
		if(!CommonFun.isNe(params.get("id"))){
			return new DrugControlService().get(params);
		}else {
			return null;
		}
	}
	public Record doctoredit(Map<String, Object> params) throws Exception {
		if(!CommonFun.isNe(params.get("id"))){
			return new DrugControlService().get(params);
		}else {
			return null;
		}
	}
	public Record patientedit(Map<String, Object> params) throws Exception {
		if(!CommonFun.isNe(params.get("id"))){
			return new DrugControlService().get(params);
		}else {
			return null;
		}
	}
	public Record roleedit(Map<String, Object> params) throws Exception {
		if(!CommonFun.isNe(params.get("id"))){
			return new DrugControlService().get(params);
		}else {
			return null;
		}
	}
}
