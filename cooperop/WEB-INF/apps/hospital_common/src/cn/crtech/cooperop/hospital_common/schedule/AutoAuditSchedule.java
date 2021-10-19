package cn.crtech.cooperop.hospital_common.schedule;

import java.util.HashMap;
import java.util.Map;
import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.schedule.AbstractSchedule;
import cn.crtech.cooperop.hospital_common.service.AfterAutoService;

public class AutoAuditSchedule extends AbstractSchedule {
	private static boolean b = false;
	
	/** 事后审查待执行队列标志  */
	public static final String TOEXEC = "1";
	/** 事后审查完成队列标志  */
	public static final String ENDEXEC = "0";
	/** 事后审查正在审查队列标志  */
	public static final String INGEXEC = "2";
	@Override
	public boolean executeOn() throws Exception {
		if(b){
			return true;
		}
		try{
			b = true;
			AfterAutoService aas = new AfterAutoService();
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("state", TOEXEC);
			Result res = aas.query(params);
			if(res != null && res.getResultset().size()>0){
				//执行审查，同时把标识改为正在执行，并在审查结束时，改为已执行
				Record record=res.getResultset(0);
				aas.exeAudit(record);
			}
		}catch(Exception e){ 
			log.error(e);
		}finally{
			b = false;
		}
		return true;
	}
}
