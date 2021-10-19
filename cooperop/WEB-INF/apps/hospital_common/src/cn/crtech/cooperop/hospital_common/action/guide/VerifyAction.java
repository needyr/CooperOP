package cn.crtech.cooperop.hospital_common.action.guide;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.service.guide.IndexService;
import cn.crtech.cooperop.hospital_common.service.verify.VerifyTableService;
import cn.crtech.cooperop.hospital_common.service.verify.VerifyabnormalService;
import cn.crtech.cooperop.hospital_common.service.verify.VerifylogService;
import cn.crtech.precheck.veriifythread.callThread;

public class VerifyAction extends BaseAction {
	
	public Result queryQuestion(Map<String, Object> params) throws Exception {
		return new VerifylogService().queryQuestion(params);
	}
	
	public Result queryLogMx(Map<String, Object> params) throws Exception {
		return new VerifylogService().queryLogMx(params);
	}
	
	public Result queryLog(Map<String, Object> params) throws Exception {
		return new VerifylogService().queryLog(params);
	}
	
	public Map<String, Object> getBar(Map<String, Object> params) throws Exception {
		return callThread.getInstance().getFlowsBar(params);
	}
	
	public void updateLog(Map<String, Object> params) throws Exception {
		new VerifyabnormalService().update(params);
	}	
	
	public int getState(Map<String, Object> params) throws Exception {
		return new VerifylogService().getState(params);
	}	
	
	/**
	 * 重置设置向导
	 * @param params
	 * @throws Exception 
	 */
	public void reset(Map<String, Object> params) throws Exception {
		new IndexService().reset(params);
	}
	
	/**
	 * 删除日志
	 * @param params
	 * @throws Exception 
	 */
	public void deletelog(Map<String, Object> params) throws Exception {
		new VerifyTableService().deletelog(params);
	}
}
