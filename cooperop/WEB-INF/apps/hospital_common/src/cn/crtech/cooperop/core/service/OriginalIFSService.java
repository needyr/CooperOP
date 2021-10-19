package cn.crtech.cooperop.core.service;

import java.util.Map;

import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.core.domain.RequestTask;
import cn.crtech.cooperop.core.domain.RunStatus;
import cn.crtech.cooperop.core.service.AbstractExecuteService;
import cn.crtech.precheck.server.PrescripionInitServlet;

/**
 * 原审核业务实现服务 - 保留原有的业务实现流程，通过配置实现新旧服务的切换，备用
 * @author chenjunhong
 * 2021年1月29日
 */
public class OriginalIFSService extends AbstractExecuteService {

	@SuppressWarnings("unchecked")
	public void prepare(RequestTask task) {
		//执行前的准备
		Map<String, Object> params = task.getParams();
		Map<String, Object> doctor = (Map<String, Object>) params.get("doctor");
		Map<String, Object> map = (Map<String, Object>) params.get("patient");
		map.put("p_type", params.get("p_type"));
		map.put("d_type", params.get("d_type"));
		map.put("doctor_no", doctor.get("code"));
		map.put("patient_id", map.get("id"));
		map.put("visit_id", map.get("visitid"));
		map.put("user_ip", task.getControl().get("ip"));
		//1-客户端关联处理
		if(!CommonFun.isNe(map.get("doctor_no"))) {
			Record pat = new Record();
			pat.put("patient_id", map.get("id"));
			pat.put("visit_id",  map.get("visitid"));
			PrescripionInitServlet.DOCTORMPAT.put((String)map.get("doctor_no"), pat);
		}
		//2-实际的应用参数
		task.getIn().putAll(map);
		//3-准备默认返回值
		if(task.getOut().isEmpty()) {
			task.setRtnJson("");
		}else {
			task.setRtnJson(CommonFun.object2Json(task.getOut()));
		}
	}

	@Override
	public boolean filter(RequestTask task) {
		//过滤检查：true - 过滤任务，false - 执行任务
		boolean flag = false;
		Map<String, Object> map = task.getIn();
		if(CommonFun.isNe(map.get("patient_id"))){
			map.put("result", "err");
			map.put("message", "必须病人id【patient_id】");
			flag = true;
			task.setStatus(RunStatus.EXCLUDE);
		}
		return flag;
	}

	@Override
	public void decorate(RequestTask task) {
		// TODO Auto-generated method stub
	}

	@Override
	public void clean(RequestTask task) {
		// TODO Auto-generated method stub
		
	}

}
