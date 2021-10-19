package cn.crtech.cooperop.ipc.schedule;

import java.util.HashMap;

import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.schedule.AbstractSchedule;
import cn.crtech.precheck.ipc.ws.client.Client;
import cn.crtech.precheck.ipc.service.DataService;


public class SyncComments extends AbstractSchedule{

	private static boolean b = false;
	@Override
	public boolean executeOn() throws Exception {
		if(b){
			return true;
		}
		b = true;
		try{
			Client.SyncData("admin_jq", new DataService().queryCommentData(new HashMap<String, Object>()));
			log.debug("sync message: 向云端同步处方点评字典...");
		}catch(Exception ex){
			log.error("sync message: 向云端同步处方点评字典失败...");
		}finally{
			b = false;
		}
		return true;
	}
}
