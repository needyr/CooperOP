package cn.crtech.cooperop.bus.workflow.core;


import org.snaker.engine.SnakerInterceptor;
import org.snaker.engine.core.Execution;
import org.snaker.engine.entity.Order;
import org.snaker.engine.entity.Task;

import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.workflow.core.service.WorkFlowService;

public class PreInterceptor implements SnakerInterceptor {

	@Override
	public void intercept(Execution execution) {
		Task task = execution.getTask();
		Order order = execution.getOrder();
		if(!CommonFun.isNe(task)){
			try {
				String check = new WorkFlowService().checkCountersign(order.getId(), null, task.getTaskName(), task.getId());
				if("Y".equals(check) || "N".equals(check)){
					execution.setMerged(true);//不再进行流转
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
