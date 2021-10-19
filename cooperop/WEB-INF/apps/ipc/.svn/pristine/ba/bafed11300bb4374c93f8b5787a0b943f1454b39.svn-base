package cn.crtech.cooperop.ipc.schedule;

import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.schedule.AbstractSchedule;
import cn.crtech.cooperop.ipc.service.AuditResultService;


public class GetInstruction extends AbstractSchedule{

	private static boolean b = false;
	@Override
	public boolean executeOn() throws Exception {
		if(b){
			return true;
		}
		b = true;
		try{
			new AuditResultService().sysnDrugDetail();
		}catch(Exception ex){
			log.error("sync message: 批量抓取药品说明书失败...");
		}finally{
			b = false;
		}
		return true;
	}
}
