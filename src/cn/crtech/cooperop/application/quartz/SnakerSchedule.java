package cn.crtech.cooperop.application.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import cn.crtech.cooperop.application.service.TaskService;

public class SnakerSchedule implements Job {
	private static boolean b = false;
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		if(b){
			return;
		}
		b = true;
		try {
			new TaskService().autoSubmitFromCS();
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			b = false;
		}
	}

}
