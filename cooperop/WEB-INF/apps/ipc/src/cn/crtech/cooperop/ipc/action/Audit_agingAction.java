package cn.crtech.cooperop.ipc.action;

import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.ipc.service.Audit_agingService;
import cn.crtech.cooperop.ipc.service.AutoAuditService;
import cn.crtech.precheck.ipc.service.DataService;
import cn.crtech.precheck.ipc.ws.client.Connection;


public class Audit_agingAction extends BaseAction {
	
	//@DisLoggedIn
	public Result list(Map<String, Object> params) throws Exception {
		return new Audit_agingService().list(params);
	}
	
	public int ispass(Map<String, Object> params) throws Exception {
		Record single = new AutoAuditService().getSingle(params);
		if (!CommonFun.isNe(single) 
				&& !single.get("state").equals("Y")
				&& !single.get("state").equals("N")) {
			Map<String, Object> FlagMap = Connection.FlagMap;
			Map<String, Object> reMap = Connection.reMap;
			Map<String, Object> m = new HashMap<String, Object>();
			/*m.put("is_overtime", "1");*/
			m.put("state", params.get("state"));
			m.put("pharmacist_id", user().getNo());
			m.put("yaoshi_name", user().getName());
			m.put("id", single.get("id"));
			 /*reMap.remove("return_A_"+(String)FlagMap.get("flag_A_"+single.get("doctor_no")));
			 reMap.put("return_A_"+(String)m.get("id"), m);
			 FlagMap.put("flag_A_"+single.get("doctor_no"), m.get("id"));*/
			new DataService().update(m);
			/*//已处理,升级发送成功的医嘱,修改状态为已发送
			Map<String, Object> mapin = new HashMap<String, Object>();
			mapin.put("state", 1);
			mapin.put("update_time", CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
			mapin.put("auto_audit_id",single.get("id"));
			mapin.put("doctor_no",single.get("doctor_no"));
			mapin.put("action","initiate");
			try {
				new SendErrorMsgService().update(mapin);
			} catch (Exception e) {
				log.error(e);
			}*/
			return 1;
		}
		return 0;
	}
}
