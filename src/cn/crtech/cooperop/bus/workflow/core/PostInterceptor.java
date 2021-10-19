package cn.crtech.cooperop.bus.workflow.core;

import java.util.ArrayList;
import java.util.List;

import org.snaker.engine.SnakerInterceptor;
import org.snaker.engine.core.Execution;
import org.snaker.engine.entity.Task;

import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.workflow.core.service.WorkFlowService;

public class PostInterceptor implements SnakerInterceptor {

	@Override
	public void intercept(Execution execution) {
		List<Task> tasks = execution.getTasks();
		for (Task task : tasks) {
			String djbh = task.getOrderId();
			String system_department_id = (String) execution.getArgs().get("system_department_id");
			String name = execution.getModel().getName();
			String system_product_code = name.split(WorkFlowService.FLOW_NAME_SPLIT)[0];
			String id = name.split(WorkFlowService.FLOW_NAME_SPLIT)[1];
			try {
				WorkFlowService wfs = new WorkFlowService();
				Record node = wfs.getNode(system_product_code, id, task.getTaskName());
				if (node != null) {
					// if (!CommonFun.isNe(node.get("expr_scheme"))) {
					// wfs.executeAutoScheme(system_product_code,
					// node.getString("expr_scheme"), djbh);
					// }
				}
				List<String> actorlist = new ArrayList<String>();
				String[] actors = task.getActorIds();
				for (int i = 0; i < actors.length; i++) {
					actorlist.add(actors[i]);
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

}
