package cn.crtech.cooperop.hospital_common.action.dict;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.service.dict.SysDrugXmService;

public class SysdrugxmAction extends BaseAction{
	
	public Result query(Map<String, Object> params) throws Exception {
		return new SysDrugXmService().query(params);
	}
	
	public int insert(Map<String, Object> params) throws Exception {
		return new SysDrugXmService().insert(params);
	}

	public int update(Map<String, Object> params) throws Exception {
		return new SysDrugXmService().update(params);
	}
	
	public int delete(Map<String, Object> params) throws Exception {
		return new SysDrugXmService().delete(params);
	}
	
	public Record edit(Map<String, Object> params) throws Exception {
		if(!CommonFun.isNe(params.get("xmid"))){
			return new SysDrugXmService().get(params);
		}else {
			return null;
		}
	}
	
	
	/**********************************以下为药品属性值的维护**************************************/
	
	
	public Result queryXmValue(Map<String, Object> params) throws Exception {
		return new SysDrugXmService().queryXmValue(params);
	}
	
	public int xmValueInsert(Map<String, Object> params) throws Exception {
		return new SysDrugXmService().xmValueInsert(params);
	}

	public int xmValueUpdate(Map<String, Object> params) throws Exception {
		return new SysDrugXmService().xmValueUpdate(params);
	}
	
	public int xmValueDelete(Map<String, Object> params) throws Exception {
		return new SysDrugXmService().xmValueDelete(params);
	}
	
	public Record xmvalueedit(Map<String, Object> params) throws Exception {
		if(!CommonFun.isNe(params.get("value"))){
			return new SysDrugXmService().xmValueGet(params);
		}else {
			return null;
		}
	}
}
