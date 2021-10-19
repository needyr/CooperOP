package cn.crtech.cooperop.core.service;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import cn.crtech.cooperop.bus.cache.SystemConfig;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.core.domain.RequestTask;
import cn.crtech.cooperop.core.service.AbstractExecuteService;
import cn.crtech.ylz.ylz;

/**
 * 执行服务实现类
 * @author chenjunhong
 * 2021年1月27日
 */
public class IFSService extends AbstractExecuteService {

	public static int final_control = Integer.parseInt(SystemConfig.getSystemConfigValue("hospital_common", "final_control", "4"));
	
	@Override
	public void prepare(RequestTask task) {
		//准备默认返回值
		Map<String, Object> respMap = task.getOut();
		respMap.put("state", "Y");
		respMap.put("use_flag", 1);
		respMap.put("flag", 1);
		respMap.put("id", "A");
		respMap.put("finish", false);
	}

	@Override
	public boolean filter(RequestTask task) {
		//审核请求不进行过滤
		return false;
	}

	@Override
	public void decorate(RequestTask task) {
		//审查结果
		Map<String, Object> respMap = task.getOut();
		//返回结果
		if(!respMap.isEmpty()) {
			respMap.remove("finish");
			task.setRtnJson(CommonFun.object2Json(respMap));
		}
		HttpServletResponse resp = task.getResponse();
		resp.setHeader("Access-Control-Allow-Origin", "*");
		//原输出日志
		ylz.p("final_control /" + final_control + " ：request in server for [" + (System.currentTimeMillis() - task.getStart()) + "ms]，return :" + task.getRtnJson());
	}

}
