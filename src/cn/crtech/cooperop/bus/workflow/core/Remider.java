package cn.crtech.cooperop.bus.workflow.core;

import java.util.Map;

import org.snaker.engine.entity.Process;
import org.snaker.engine.model.NodeModel;
import org.snaker.engine.scheduling.IReminder;

import cn.crtech.cooperop.bus.log.log;

public class Remider implements IReminder {

	@Override
	public void remind(Process process, String orderId, String taskId, NodeModel nodeModel, Map<String, Object> data) {
		// TODO Auto-generated method stub
		log.debug(orderId);
	}

}
