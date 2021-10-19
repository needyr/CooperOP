package cn.crtech.cooperop.bus.workflow.core;

import java.util.Map;

import org.snaker.engine.core.Execution;
import org.snaker.engine.entity.Order;
import org.snaker.engine.handlers.IHandler;
import org.snaker.engine.model.NodeModel;

import cn.crtech.cooperop.bus.cache.SystemMessageTemplate;
import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.message.MessageSender;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.workflow.core.service.WorkFlowService;

public class AutoTaskHandler implements IHandler {

	public void handle(Execution execution) {
		handle(null, execution);
	}
	@Override
	public void handle(NodeModel nodemodel, Execution execution) {
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
				if (!CommonFun.isNe(node.get("execute_scheme"))) {
					Map<String, Object> m1 = execution.getArgs();
					m1.put("operator", execution.getOperator());
					wfs.executeAutoScheme(system_product_code, node.getString("execute_scheme"), djbh, audited, advice, m1);
				}
				Order order = execution.getOrder();
				String process_id = order.getProcessId();
				//给人员发送消息
				Map<String, Object> m = execution.getArgs();
				m.put("djbh", djbh);
				if(SystemMessageTemplate.needSendM(system_product_code + wfs.getRecordByWFProcessId(process_id).getString("id") + nodemodel.getName())){
					MessageSender.sendProcessMessageAfter(system_product_code, wfs.getRecordByWFProcessId(process_id).getString("id"), nodemodel.getName(), m);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("单据号[" + djbh + "]自动节点[" + nodemodel.getName() + (node != null ? "-" + node.getString("name") : "") + "]执行异常：" + e.getMessage(), e);
		}
	}

}
