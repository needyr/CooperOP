package cn.crtech.cooperop.ipc.schedule;

import java.util.HashMap;

import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.schedule.AbstractSchedule;
import cn.crtech.precheck.ipc.service.DataService;
import cn.crtech.precheck.ipc.ws.client.Client;


public class SyncInstruction extends AbstractSchedule{

	private static boolean b = false;
	@Override
	public boolean executeOn() throws Exception {
		if(b){
			return true;
		}
		b = true;
		try{
			Client.SyncData("admin_jq", new DataService().querySmsData(new HashMap<String,Object>()));
			Client.syncShuomsImg();
			log.debug("sync message: 向云端同步药品说明书...");
		}catch(Exception ex){
			log.error("sync message: 向云端同步药品说明书失败...");
		}finally{
			b = false;
		}
		return true;
	}
}
