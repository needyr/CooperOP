package cn.crtech.cooperop.application.quartz;


import java.util.HashMap;

import cn.crtech.cooperop.application.service.DingdingService;
import cn.crtech.cooperop.bus.schedule.AbstractSchedule;



public class DingdingSchedule extends AbstractSchedule {
	private static boolean b = false;
	@Override
	public boolean executeOn() throws Exception {
		if(b){
			return true;
		}
		b = true;
		try {
		    new DingdingService().getAttendance(new HashMap<String,Object>());
			return true;
		} catch (Exception e) {
			throw new Exception("提取数据异常",e);
		}finally {
			b = false;
		}
	}

}
