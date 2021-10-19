package cn.crtech.cooperop.ipc.action;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.application.authenticate.DisLoggedIn;
import cn.crtech.cooperop.bus.cache.SystemConfig;
import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.ipc.service.AutoAuditService;
import cn.crtech.cooperop.ipc.service.SendErrorMsgService;
import cn.crtech.cooperop.ipc.service.SubmitFailService;
import cn.crtech.precheck.ipc.service.DataService;
import cn.crtech.precheck.ipc.ws.client.Client;
import cn.crtech.precheck.ipc.ws.client.Connection;

public class SubmitfailAction extends BaseAction{
	@DisLoggedIn
	public Result failList(Map<String, Object> params) throws Exception {
		return new SubmitFailService().failList(params);
	}
	@DisLoggedIn
	public int ispass(Map<String, Object> params) throws Exception {
		Record single = new AutoAuditService().getSingle(params);
		if (!CommonFun.isNe(single) 
				&& !single.get("state").equals("Y")
				&& !single.get("state").equals("N")
				&& !single.get("state").equals("D")) {
			//int wait_time = Integer.parseInt(SystemConfig.getSystemConfigValue("hospital_common", "wait_time", "30"));
			//Map<String, Object> FlagMap = Connection.FlagMap;
			//Map<String, Object> reMap = Connection.reMap;
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("state", params.get("state"));
			m.put("pharmacist_id", user().getNo());
			m.put("yaoshi_name", user().getName());
			m.put("id", single.get("id"));
			//if(wait_time != 0) {
			Client.conns.get(single.get("doctor_no")).reMap.remove("return_A_"+(String)Client.conns.get(single.get("doctor_no")).FlagMap.get("flag_A_"+single.get("doctor_no")));
			Client.conns.get(single.get("doctor_no")).reMap.put("return_A_"+(String)m.get("id"), m);
			Client.conns.get(single.get("doctor_no")).FlagMap.put("flag_A_"+single.get("doctor_no"), m.get("id"));
			//}
			new DataService().update(m);
			//已处理,升级发送成功的医嘱,修改状态为已发送
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
			}
			return 1;
		}
		return 0;
	}
}
