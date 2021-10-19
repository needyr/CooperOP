package cn.crtech.cooperop.ipc.schedule;

import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.schedule.AbstractSchedule;
import cn.crtech.precheck.ipc.service.DataService;


public class SyncUsersUpdate extends AbstractSchedule{

	private static boolean b = false;
	
	@Override
	public boolean executeOn() throws Exception {
		if(b){
			return true;
		}
		b = true;
		try{
			new DataService().updateIsSync();
		}catch(Exception ex){
			log.error("sync message: 向云端同步修改的用戶信息失败...");
		}finally{
			b = false;
		}
		return true;
	}
}
