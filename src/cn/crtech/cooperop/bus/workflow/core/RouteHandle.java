package cn.crtech.cooperop.bus.workflow.core;

import org.snaker.engine.TransitionHandler;
import org.snaker.engine.core.Execution;
import org.snaker.engine.model.TransitionModel;

import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.workflow.core.service.WorkFlowService;

public class RouteHandle implements TransitionHandler {

	@Override
	public boolean match(Execution execution) {
		return match(null, execution);
	}

	@Override
	public boolean match(TransitionModel transitionmodel, Execution execution) {
		boolean rtn = false;
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
			node = wfs.getRoute(system_product_code, id, transitionmodel.getSource().getName(), transitionmodel.getTarget().getName());
			if (node != null) {
				if (!CommonFun.isNe(node.get("expr_scheme"))) {
					rtn = "Y".equals(wfs.executeExprScheme(system_product_code, node.getString("expr_scheme"), djbh, audited, advice,execution.getArgs()));
				}
			}
			return rtn;
		} catch (Exception e) {
			throw new RuntimeException("单据号[" + djbh + "]流向[" + transitionmodel.getSource().getName() + "->" +  transitionmodel.getTarget().getName() + "]执行异常：" + e.getMessage(), e);
		}
	}

}
