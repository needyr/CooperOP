package cn.crtech.precheck.domain.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.core.domain.RequestTask;
import cn.crtech.cooperop.core.domain.RunStatus;
import cn.crtech.cooperop.core.service.AbstractExecuteService;
import cn.crtech.precheck.server.PrescripionInitServlet;

/**
 * 请求类服务的实现类 - 作为请求任务的接收服务
 * @author chenjunhong
 * 2021年1月27日
 */
public class InitService extends AbstractExecuteService {

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
		map.put("dept_code", map.get("departcode"));
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
		task.setRtnJson("N");
	}

	@Override
	public boolean filter(RequestTask task) {
		Map<String, Object> map = task.getIn();
		//1-病人id不存在
		if(CommonFun.isNe(map.get("patient_id"))){
			map.put("result", "err");
			map.put("message", "必须病人id【patient_id】");
		}
		//2-检查重复患者
		boolean flag = checkJoinPat(task);
		return flag;
	}
	
	@Override
	public void decorate(RequestTask task) {
		//获取初始化任务执行后的返回值
		String respUrl = (String)task.getOut().get("respUrl");
		String rtnJson = task.getRtnJson();
		if(!CommonFun.isNe(respUrl)) {
			rtnJson = respUrl;
		}
		HttpServletResponse resp = task.getResponse();
		// bs 项目跨域
		if(!CommonFun.isNe(task.getOrigin().get("isbs"))) {
			resp.setHeader("Access-Control-Allow-Origin", "*");
			Map<String, Object> bsRtn = new HashMap<String, Object>();
			if(CommonFun.isNe(rtnJson) || "N".equals(rtnJson.toString())) {
				bsRtn.put("isopen", "0");
			}else {
				bsRtn.put("isopen", "1");
			}
			bsRtn.put("url", rtnJson);
			task.setRtnJson(CommonFun.object2Json(bsRtn));
		}else {
			task.setRtnJson(rtnJson);
		}
	}

	private boolean checkJoinPat(RequestTask task) {
		boolean flag = false;
		//避免相同病人短时间内重复触发初始化请求
		if(this.getPatStaff().get(task.getPatKey())!=null) {
			//已存在 - 不再重复执行
			flag = true;
			task.setStatus(RunStatus.EXCLUDE);
			log("#患者存在 - [" + task.getPatKey() + "]");
		}else {
			//记录值 - 患者key + 执行开始时间
			this.getPatStaff().put(task.getPatKey(), System.currentTimeMillis());
			task.setStatus(RunStatus.INCLUDE);
			log("#登记患者 - [" + task.getPatKey() + "]");
		}
		return flag;
	}
	
	@Override
	public void clean(RequestTask task) {
		Long st = this.getPatStaff().get(task.getPatKey());
		if(st!=null) {
			long ed = System.currentTimeMillis();
			//设定初始化的执行间隔时间，暂定为：超时时间*4，如：5000 * 4 = 20秒
			long wt = task.getTimeout()*4;
			if(task.getTimeout()<=0) {
				wt = 20000L;
			}
			//手动测试间隔时间
			wt = 5000L;
			if(ed - st >= wt) {
				this.getPatStaff().remove(task.getPatKey());
				log("#移除名单 - [" + task.getPatKey() + "]");
			}
		}
	}
	
}
