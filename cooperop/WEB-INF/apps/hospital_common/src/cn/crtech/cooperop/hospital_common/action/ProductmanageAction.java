package cn.crtech.cooperop.hospital_common.action;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.service.ProductmanagetService;
import cn.crtech.precheck.EngineInterface;

public class ProductmanageAction extends BaseAction {
	
	public Result query(Map<String, Object> params) throws Exception {
		return new ProductmanagetService().query(params);
	}
	
	public Record edit(Map<String, Object> params) throws Exception {
		if(!CommonFun.isNe(params.get("code"))){
			return new ProductmanagetService().get(params);
		}else {
			return null;
		}
	}
	
	public Result queryHas(Map<String, Object> params) throws Exception {
		if(!CommonFun.isNe(params.get("code"))){
			return new ProductmanagetService().queryHas(params);
		}else {
			return null;
		}	
	}
	
	public int save(Map<String, Object> params) throws Exception {
		ProductmanagetService pms = new ProductmanagetService();
		pms.insert(params);
		EngineInterface.loadCheckProduct();
		return 1;
	}
	
	public int update(Map<String, Object> params) throws Exception {
		ProductmanagetService pms = new ProductmanagetService();
		pms.update(params);
		EngineInterface.loadCheckProduct();
		return 1;
	}
	
	public int delete(Map<String, Object> params) throws Exception {
		ProductmanagetService pms = new ProductmanagetService();
		pms.delete(params);
		EngineInterface.loadCheckProduct();
		return 1;
	}

}
