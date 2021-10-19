package cn.crtech.cooperop.bus.workflow.core;

import org.snaker.engine.Assignment;
import org.snaker.engine.AssignmentHandler;
import org.snaker.engine.core.Execution;
import org.snaker.engine.entity.Order;
import org.snaker.engine.entity.Task;
import org.snaker.engine.model.ProcessModel;
import org.snaker.engine.model.TaskModel;

import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.workflow.core.service.WorkFlowService;

public class CountersignAssignment extends Assignment {

	@Override
	public Object assign(TaskModel model, Execution execution) {
		String[] rtn = null;
		Order order = execution.getOrder();
		String djbh = order.getOrderNo();
		String system_department_id = (String) execution.getArgs().get("system_department_id");
		String name = execution.getModel().getName();
		String system_product_code = name.split(WorkFlowService.FLOW_NAME_SPLIT)[0];
		String id = name.split(WorkFlowService.FLOW_NAME_SPLIT)[1];
		Record node = null;
		try {
			WorkFlowService wfs = new WorkFlowService();
			node = wfs.getNode(system_product_code, id, model.getName());
			if (node != null) {
				if (!CommonFun.isNe(node.get("countersign_scheme"))) {
					rtn =  wfs.executeAssignmentScheme(system_product_code, node.getString("countersign_scheme"), djbh);
				} else if (!CommonFun.isNe(node.getString("countersigner_role"))) {
					rtn =  wfs.getActorList(node.getString("countersigner_role"), system_department_id);
				} else if (!CommonFun.isNe(node.getString("countersigner_post"))) {
					rtn =  wfs.getActorListByPost(node.getString("countersigner_post"));
				} else if (!CommonFun.isNe(node.getString("countersigners"))) {
					rtn =  node.getString("countersigners").split(",");
				}
			}
			if (CommonFun.isNe(rtn)) {
				throw new Exception("获取人员列表为空，无法分配，请检查。");
			}
			return rtn;
		} catch (Exception e) {
			throw new RuntimeException("单据号[" + djbh + "]会签节点[" + model.getName() + (node != null ? "-" + node.getString("name") : "") + "]分配异常：" + e.getMessage(), e);
		}
	}

}
