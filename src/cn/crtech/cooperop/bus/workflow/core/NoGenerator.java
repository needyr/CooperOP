package cn.crtech.cooperop.bus.workflow.core;

import org.snaker.engine.INoGenerator;
import org.snaker.engine.model.ProcessModel;

import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.workflow.core.service.WorkFlowService;

@Deprecated
public class NoGenerator implements INoGenerator {

	@Override
	public String generate(ProcessModel model) {
		String name = model.getName();
		String system_product_code = name.split(WorkFlowService.FLOW_NAME_SPLIT)[0];
		String id = name.split(WorkFlowService.FLOW_NAME_SPLIT)[1];
		try {
			return new WorkFlowService().getNo(system_product_code, id);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
