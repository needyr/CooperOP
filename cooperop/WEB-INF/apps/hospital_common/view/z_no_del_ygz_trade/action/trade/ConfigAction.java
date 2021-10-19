package cn.crtech.cooperop.hospital_common.action.trade;

import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.service.trade.ConfigService;
import cn.crtech.cooperop.hospital_common.service.trade.DataIfsService;

public class ConfigAction extends BaseAction{

	public Result query(Map<String, Object> params) throws Exception {
		return new ConfigService().query(params);
	}
	
	public int insert(Map<String, Object> params) throws Exception {
		new ConfigService().insert(params);
		Map<String, Object> upM = new HashMap<String, Object>();
		upM.put("code", params.get("data_interface_code"));
		upM.put("interface_url", params.get("interface_url"));
		new DataIfsService().update(upM);
		return 1;
	}
	
	public int update(Map<String, Object> params) throws Exception {
		new ConfigService().update(params);
		Map<String, Object> upM = new HashMap<String, Object>();
		upM.put("code", params.get("data_interface_code"));
		upM.put("interface_url", params.get("interface_url"));
		new DataIfsService().update(upM);
		return 1;
	}
	
	public int delete(Map<String, Object> params) throws Exception {
		if(CommonFun.isNe(params.get("trade_code"))) {
			return -1;
		}else {
			return new ConfigService().delete(params);
		}
	}
	
	public Record edit(Map<String, Object> params) throws Exception {
		if(!CommonFun.isNe(params.get("trade_code"))){
			return new ConfigService().get(params);
		}else {
			return null;
		}
	}
	
}
