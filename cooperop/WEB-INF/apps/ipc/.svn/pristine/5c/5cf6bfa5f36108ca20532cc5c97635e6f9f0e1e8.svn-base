package cn.crtech.cooperop.ipc.action;

import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.application.authenticate.DisLoggedIn;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.ipc.service.PatientService;

public class PatientAction extends BaseAction {
	@DisLoggedIn
	public Result queryOperation(Map<String, Object> params) throws Exception {
		return new PatientService().queryOperation(params);
	}
	
	@DisLoggedIn
	public Record getPatient2(Map<String, Object> params) throws Exception {
		return new PatientService().getPatient2(params);
	}
	
	@DisLoggedIn
	public Result queryEnjoin(Map<String, Object> params) throws Exception {
		return new PatientService().queryEnjoin(params);
	}
	
	@DisLoggedIn
	public Result queryDiagnos(Map<String, Object> params) throws Exception {
		return new PatientService().queryDiagnos(params);
	}
	
	@DisLoggedIn
	public Result queryExam(Map<String, Object> params) throws Exception {
		return new PatientService().queryExam(params);
	}
	
	@DisLoggedIn
	public Result queryRequesten(Map<String, Object> params) throws Exception {
		return new PatientService().queryRequesten(params);
	}
	
	@DisLoggedIn
	public Result queryVital(Map<String, Object> params) throws Exception {
		return new PatientService().queryVital(params);
	}
	
	@DisLoggedIn
	public Map<String, Object> getExamDetail(Map<String, Object> params) throws Exception {
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		rtnMap.put("data", new PatientService().getExamDetail(params).getResultset());
		return rtnMap;
	}
	
	@DisLoggedIn
	public Map<String, Object> queryRequestenDetail(Map<String, Object> params) throws Exception {
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		rtnMap.put("data", new PatientService().queryRequestenDetail(params).getResultset());
		return rtnMap;
	}
	
	@DisLoggedIn
	public Map<String, Object> queryVitalDetail(Map<String, Object> params) throws Exception {
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		rtnMap.put("data", new PatientService().queryVitalDetail(params).getResultset());
		return rtnMap;
	}
	
	@DisLoggedIn
	public Map<String, Object> queryOperDetil(Map<String, Object> params) throws Exception {
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		rtnMap.put("data",new PatientService().queryOperDetil(params).getResultset());
		return rtnMap;
	}
	
}
