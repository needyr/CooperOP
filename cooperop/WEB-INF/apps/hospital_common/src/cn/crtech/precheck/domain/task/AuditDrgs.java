package cn.crtech.precheck.domain.task;

import java.lang.reflect.Method;
import java.util.Map;

import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.core.domain.RequestTask;
import cn.crtech.cooperop.core.domain.RunResult;
import cn.crtech.cooperop.core.service.AbstractExecuteTask;
import cn.crtech.ylz.ylz;

/**
 * DRGS审核任务
 * @author chenjunhong
 * 2021年2月3日
 */
public class AuditDrgs extends AbstractExecuteTask {

	@Override
	public boolean filter(RequestTask task) {
		boolean flag = false;
		Map<String, Object> filter = task.getFilter();
		if(!"51".equals(filter.get("d_type").toString())) {
			flag = true;
		}
		return flag;
	}
	
	public RunResult execute(RequestTask task) {
		RunResult retVal = new RunResult();
		//request节点参数
		Map<String, Object> data = task.getParams();
		data.put("ip", task.getControl().get("ip"));
		try {
			//DRGS原执行过程 - 先保留
			Class<?> classz = Class.forName("cn.crtech.cooperop.hospital_imic_drgs.core.DRGS");
			Method method = classz.getMethod("DGPredict",  Record.class);
			//调用静态执行方法
			Record drgsRet = (Record) method.invoke(null, new Record(data));
			drgsRet.put("id", "drgs_"+ drgsRet.get("id"));
			retVal.putAll(drgsRet);
		} catch (Exception e) {
			ylz.p("DRGS调用异常！");
			e.printStackTrace();
		}
		log("#AuditDrgs retVal -> " + retVal);
		return retVal;
	}
	
}
