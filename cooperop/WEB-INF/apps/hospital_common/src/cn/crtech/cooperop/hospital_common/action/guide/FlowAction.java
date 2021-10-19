package cn.crtech.cooperop.hospital_common.action.guide;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sound.midi.Soundbank;

import cn.crtech.choho.authresource.util.MD5;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.service.guide.IndexService;
import cn.crtech.precheck.veriifythread.callThread;

public class FlowAction  extends BaseAction {
	
	public Map<String, Object> index(Map<String, Object> params) throws Exception {
		return new IndexService().index(params);
	}
	
	public List<Record> querychild(Map<String, Object> params) throws Exception {
		return new IndexService().querychild(params);
	}
	
	public String hasPermit(Map<String, Object> params) throws Exception {
		return new IndexService().hasPermit(params);
	}
	
	public boolean is_next(Map<String, Object> params) throws Exception {
		return new IndexService().is_next(params);
	}
	
	public boolean initUsers(Map<String, Object> params) throws Exception {
		return new IndexService().initUsers(params);
	}
	
	public boolean execOneAfterAudit(Map<String, Object> params) throws Exception {
		return new IndexService().execOneAfterAudit(params);
	}
	
	public boolean callRBFexe(Map<String, Object> params) throws Exception {
		return new IndexService().callRBFexe(params);
	}
	
	public Map<String, Object> welcome(Map<String, Object> params) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("is_finish", new IndexService().queryFinishAll(params));
		return map;
	}
	
	public boolean initData(Map<String, Object> params) throws Exception {
		return new IndexService().initData(params);
	}
	
	public Map<String, Object> finish(Map<String, Object> params) throws Exception {
		return new IndexService().finish(params);
	}

	public List<Record> getFinishLog(Map<String, Object> params) throws Exception {
		return new IndexService().getFinishLog(params);
	}

	public void updateOneActive(Map<String, Object> params) throws Exception {
		new IndexService().updateOneActive(params);
	}
	
	public void insertLog(Map<String, Object> params) throws Exception {
		new IndexService().insertLog(params);
	}
	
	public Result queryFlowLog(Map<String, Object> params) throws Exception {
		return new IndexService().queryFlowLog(params);
	}
	
	public Result queryFlowErrorLog(Map<String, Object> params) throws Exception {
		return new IndexService().queryFlowErrorLog(params);
	}
	
	public boolean execVerify(Map<String, Object> params) throws Exception {
		return callThread.getInstance().verify(params);
	}
	
	public Map<String, Object>  getVerifyBar(Map<String, Object> params) throws Exception {
		return callThread.getInstance().getFlowsBar(params);
	}
	
	public Record encryptmd5(Map<String, Object> params) {
		String hospital_id = (String) params.get("hospital_id");
		String hospital_name = (String) params.get("hospital_name");
		if (CommonFun.isNe(hospital_id) || CommonFun.isNe(hospital_name)) {
			throw new RuntimeException("医院ID或医院名称不能为空!");
		}
		String encrypt_result = MD5.md5("yckj_"+hospital_id+hospital_name);
		Record record = new Record();
		record.put("encrypt_result", encrypt_result);
		return record;
	}
}
