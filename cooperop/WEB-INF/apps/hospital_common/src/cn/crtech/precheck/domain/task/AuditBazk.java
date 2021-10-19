package cn.crtech.precheck.domain.task;

import java.lang.reflect.Method;
import java.util.Map;

import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.core.domain.RequestTask;
import cn.crtech.cooperop.core.domain.RunResult;
import cn.crtech.cooperop.core.service.AbstractExecuteTask;
import cn.crtech.ylz.ylz;

/**
 * 病案质控审核任务
 * @author chenjunhong
 * 2021年2月3日
 */
public class AuditBazk extends AbstractExecuteTask {

	@Override
	public boolean filter(RequestTask task) {
		boolean flag = false;
		Map<String, Object> filter = task.getFilter();
		if(!"52".equals(filter.get("d_type").toString())) {
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
			//病案质控原执行过程 - 先保留
			Class<?> classz = Class.forName("cn.crtech.cooperop.hospital_bazk.core.BAZK");
			Method method = classz.getMethod("audit",  Record.class);
			//调用静态执行方法
			Record bazkRet = (Record) method.invoke(null, new Record(data));
			bazkRet.put("id", "bazk_"+ bazkRet.get("id"));
			retVal.putAll(bazkRet);
		} catch (Exception e) {
			ylz.p("BAZK调用异常！");
			e.printStackTrace();
		}
		log("#AuditBazk retVal -> " + retVal);
		return retVal;
	}
	
}
