package cn.crtech.cooperop.hospital_common.action;

import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.application.authenticate.DisLoggedIn;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.hospital_common.service.DictHisDiagnosisService;
import cn.crtech.cooperop.hospital_common.service.DictHisOperService;

public class DicthisdiagnosisAction extends BaseAction{
	@DisLoggedIn
	public Map<String, Object> search(Map<String, Object> params) throws Exception {
		return new DictHisDiagnosisService().search(params);
	}
	@DisLoggedIn
	public Map<String, Object> searchCheck(Map<String, Object> params) throws Exception {
		return new DictHisDiagnosisService().searchCheck(params);
	}
	
	
	
	
	/* 诊断 */
	public List<Record> queryDiagnosis(Map<String, Object> params) throws Exception {

		return new DictHisDiagnosisService().queryDiagnosis(params);
	}

	public List<Record> queryDiagnosisCheck(Map<String, Object> params) throws Exception {
		return new DictHisDiagnosisService().queryDiagnosisCheck(params);
	}
}
