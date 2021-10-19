package cn.crtech.cooperop.bus.workflow.core;

import org.snaker.engine.DecisionHandler;
import org.snaker.engine.core.Execution;
import org.snaker.engine.entity.Task;
import org.snaker.engine.model.NodeModel;

import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.workflow.core.service.WorkFlowService;

public class JudgeHandle implements DecisionHandler {

	@Override
	public String decide(Execution execution) {
		return decide(null, execution);
	}
	
	@Override
	public String decide(NodeModel nodemodel, Execution execution) {
		String rtn = null;
		String djbh = execution.getOrder().getOrderNo();
		String system_department_id = (String) execution.getArgs().get("system_department_id");
		String audited = (String) execution.getArgs().get("audited");
		String advice = (String) execution.getArgs().get("advice");
		String name = execution.getModel().getName();
		String system_product_code = name.split(WorkFlowService.FLOW_NAME_SPLIT)[0];
		String id = name.split(WorkFlowService.FLOW_NAME_SPLIT)[1];
		Record node = null;
		try {
			WorkFlowService wfs = new WorkFlowService();
			node = wfs.getNode(system_product_code, id, nodemodel.getName());
			if (node != null) {
				if (!CommonFun.isNe(node.get("expr_scheme"))) {
					rtn = wfs.executeExprScheme(system_product_code, node.getString("expr_scheme"), djbh, audited, advice, execution.getArgs());
				}
			}
			if (CommonFun.isNe(rtn)) {
				throw new Exception("返回流向编号为空，无法流转，请检查判断条件。");
			}
			return rtn;
		} catch (Exception e) {
			throw new RuntimeException("单据号[" + djbh + "]判断节点[" + nodemodel.getName() + (node != null ? "-" + node.getString("name") : "") + "]执行异常：" + e.getMessage(), e);
		}
	}

}
