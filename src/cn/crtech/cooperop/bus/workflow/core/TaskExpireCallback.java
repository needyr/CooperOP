package cn.crtech.cooperop.bus.workflow.core;

import java.util.List;

import org.snaker.engine.entity.Task;
import org.snaker.engine.scheduling.JobCallback;

import cn.crtech.cooperop.bus.log.log;

public class TaskExpireCallback implements JobCallback {

	@Override
	public void callback(String taskId, List<Task> newTasks) {
		// TODO Auto-generated method stub
		log.info("callback taskId=" + taskId);
		log.info("newTasks=" + newTasks);
	}

}
