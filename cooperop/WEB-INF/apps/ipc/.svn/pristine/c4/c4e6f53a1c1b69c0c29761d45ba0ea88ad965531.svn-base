package cn.crtech.cooperop.ipc.schedule;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.schedule.AbstractSchedule;
import cn.crtech.cooperop.ipc.service.AuditFlowService;
import cn.crtech.cooperop.ipc.service.AutoAuditService;

public class BackToHisSchedule extends AbstractSchedule {
	private static boolean b = false;
	@Override
	public boolean executeOn() throws Exception {
		if(b){
			return true;
		}
		b = true;
		try{
		AutoAuditService aas = new AutoAuditService();
		Map<String, Object> p = new HashMap<String, Object>();
		p.put("maxSendNum", 30);
		p.put("states", "YND");
		p.put("is_overtime", "1");
		p.put("is_backtohis", "0");
		Result imResult = new AutoAuditService().query(p);
		List<Record> imlList = imResult.getResultset();
		if(imlList.size() > 0) {
			log.debug("[im]发送条数[" + imlList.size() + "]");
		}
		for (Record im : imlList) {
			try {
				Map<String, Object> pa = new HashMap<String, Object>();
				if("D".equals(im.get("state"))){
					pa.put("auto_audit_id", im.get("id"));
					pa.put("doctor_no", im.get("doctor_no"));
					pa.put("department", im.get("department"));
					new AuditFlowService().submit(pa);
				}else{
					pa.put("id", im.get("id"));
					pa.put("is_backtohis", 1);
					//new DataService().updateResult(pa);
				}
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("id", im.get("id"));
				param.put("is_backtohis", 1);
				aas.update(param);
				Thread.sleep(1000);
			} catch (Exception e) {
				new Exception("发送im信息异常", e);
			}
		}
		}finally{
			b = false;
		}
		return true;
	}
}
