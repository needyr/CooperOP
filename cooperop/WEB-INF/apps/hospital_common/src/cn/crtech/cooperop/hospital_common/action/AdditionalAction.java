package cn.crtech.cooperop.hospital_common.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.application.authenticate.DisLoggedIn;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.schedule.GetCG;
import cn.crtech.cooperop.hospital_common.service.DictCGService;
import cn.crtech.cooperop.hospital_common.service.DictdrugService;
import cn.crtech.cooperop.hospital_common.service.PatientService;
import cn.crtech.cooperop.hospital_common.service.additional.ShuoMSService;

public class AdditionalAction extends BaseAction{

	@DisLoggedIn
	public Result queryDrug(Map<String, Object> params) throws Exception {
		return new ShuoMSService().queryDrug(params);
	}
	
	//系统药品说明书
	public Result querySysDrug(Map<String, Object> params) throws Exception {
		return new ShuoMSService().querySysDrug(params);
	}
	
	//查询药品类型
	@DisLoggedIn
	public Map<String, Object> verifyINS(Map<String, Object> params) throws Exception {
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		rtnMap.put("types", new DictdrugService().queryDrugType(params).getResultset());
		return rtnMap;
	}
	
	@DisLoggedIn
	public Map<String, Object> instruction(Map<String, Object> params) throws Exception {
		if(CommonFun.isNe(params.get("drug_code"))) {
			params.put("drug", null);
			return params;
		}
		return new ShuoMSService().getInstruction(params);
	}
	
	//查询单条系统药品说明书
	public Map<String, Object> sysinstruction(Map<String, Object> params) throws Exception {
		if(CommonFun.isNe(params.get("drug_code"))) {
			params.put("drug", null);
			return params;
		}
		return new ShuoMSService().getSysInstruction(params);
	}
	
	@DisLoggedIn
	public Map<String, Object> patientInfo(Map<String, Object> params) throws Exception {
		if(CommonFun.isNe(params.get("patient_id")) && CommonFun.isNe(params.get("visit_id"))) {
			params.put("info", null);
			return params;
		}
		return new PatientService().queryPatientBasicInfo(params);
	}
	
	@DisLoggedIn
	public Map<String, Object> queryPatExam_Detail(Map<String, Object> params) throws Exception {
		return new PatientService().queryPatExam_Detail(params);
	}
	
	@DisLoggedIn
	public Map<String, Object> queryPatRequesten_Detail(Map<String, Object> params) throws Exception {
		return new PatientService().queryPatRequesten_Detail(params);
	}
	
	@DisLoggedIn
	public Map<String, Object> queryPatVital_Detail(Map<String, Object> params) throws Exception {
		return new PatientService().queryPatVital_Detail(params);
	}
	
	@DisLoggedIn
	public Map<String, Object> queryPatCheckRunInfo(Map<String, Object> params) throws Exception {
		return new PatientService().queryPatCheckRunInfo(params);
	}
	
	@DisLoggedIn
	public Map<String, Object> queryPatCheckResult(Map<String, Object> params) throws Exception {
		return new PatientService().queryPatCheckResult(params);
	}
	
	@DisLoggedIn
	public Map<String, Object> queryPatCommentResult(Map<String, Object> params) throws Exception {
		return new PatientService().queryPatCommentResult(params);
	}
	
	@DisLoggedIn
	public Map<String, Object> queryRealYizu(Map<String, Object> params) throws Exception {
		return new PatientService().queryRealYizu(params);
	}
	
	@DisLoggedIn
	public List<Record> queryCG(Map<String, Object> params) throws Exception {
		return new DictCGService().queryByLike(params);
	}
	
	@DisLoggedIn
	public void executeOnGetCG(Map<String, Object> params) throws Exception {
		new GetCG().executeOn();
	}
	
	@DisLoggedIn
	public Map<String, Object> shuoms_img(Map<String, Object> params) throws Exception {
		if(CommonFun.isNe(params.get("drug_code"))) {
			return null;
		}
		return new ShuoMSService().shuoms_img(params);
	}
}
