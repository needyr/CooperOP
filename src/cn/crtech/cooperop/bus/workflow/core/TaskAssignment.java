package cn.crtech.cooperop.bus.workflow.core;

import java.util.Map;

import org.snaker.engine.Assignment;
import org.snaker.engine.AssignmentHandler;
import org.snaker.engine.core.Execution;
import org.snaker.engine.entity.Order;
import org.snaker.engine.entity.Task;
import org.snaker.engine.model.ProcessModel;
import org.snaker.engine.model.TaskModel;

import cn.crtech.cooperop.bus.cache.SystemMessageTemplate;
import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.message.MessageSender;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.workflow.core.service.WorkFlowService;

public class TaskAssignment extends Assignment {

	@Override
	public Object assign(TaskModel model, Execution execution) {
		String[] rtn = null;
		Order order = execution.getOrder();
		String djbh = order.getOrderNo();
		String process_id = order.getProcessId();
		Map<String, Object> m = execution.getArgs();
		String system_department_id = (String) execution.getArgs().get("system_department_id");
		String name = execution.getModel().getName();
		String system_product_code = name.split(WorkFlowService.FLOW_NAME_SPLIT)[0];
		String id = name.split(WorkFlowService.FLOW_NAME_SPLIT)[1];
		Record node =  null;
		try {
			WorkFlowService wfs = new WorkFlowService();
			node = wfs.getNode(system_product_code, id, model.getName());
			if (node != null) {
				if (!CommonFun.isNe(node.get("processor_scheme"))) {
					rtn = wfs.executeAssignmentScheme(system_product_code, node.getString("processor_scheme"), djbh);
				} else if (!CommonFun.isNe(node.getString("processor_role"))) {
					rtn = wfs.getActorList(node.getString("processor_role"), system_department_id);
				} else if (!CommonFun.isNe(node.getString("processor_post"))) {
					rtn = wfs.getActorListByPost(node.getString("processor_post"));
				} else if (!CommonFun.isNe(node.getString("processors"))) {
					rtn = node.getString("processors").split(",");
				}
			}
			if (CommonFun.isNe(rtn)) {
				throw new Exception("获取人员列表为空，无法分配，请检查。");
			}
			//给人员发送消息
			if(SystemMessageTemplate.needSendM(system_product_code + wfs.getRecordByWFProcessId(process_id).getString("id") + node.get("id"))){
				MessageSender.sendProcessMessageBefore(system_product_code, wfs.getRecordByWFProcessId(process_id).getString("id"), node.getString("id"), m, rtn);
			}
			return rtn;
		} catch (Exception e) {
			throw new RuntimeException("单据号[" + djbh + "]任务节点[" + model.getName() + (node != null ? "-" + node.getString("name") : "") + "]分配异常：" + e.getMessage(), e);
		}
	}

}
