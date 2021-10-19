package cn.crtech.cooperop.ipc.action;

import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.application.authenticate.DisLoggedIn;
import cn.crtech.cooperop.bus.cache.SystemConfig;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.GlobalVar;
import cn.crtech.cooperop.hospital_common.service.AuditrecordService;
import cn.crtech.cooperop.ipc.service.AutoAuditService;
import cn.crtech.precheck.ipc.ws.client.Client;

public class AutoauditAction extends BaseAction {
	
	public Map<String, Object> get(Map<String, Object> map) throws Exception{
		return new AutoAuditService().get(map);
	}
	@DisLoggedIn
	public Map<String, Object> update(Map<String, Object> map) throws Exception{
		if("1".equals(map.get("is_sure"))) {
			try {
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("state", "ok");
				data.put("id", map.get("id"));
				data.put("hospital_id", SystemConfig.getSystemConfigValue("hospital_common", "hospital_id"));
				Record record = new AutoAuditService().get(data);
				Client.sendDoctorDeal(record.getString("doctor_no"), data);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		new AutoAuditService().update(map);
		return map;
	}
	
	public Result queryResult(Map<String, Object> params) throws Exception {
		return new AutoAuditService().queryResult(params);
	}
	
}
