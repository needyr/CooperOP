package cn.crtech.cooperop.hospital_common.action.dict;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.service.dict.DiagnosisService;

public class DiagnosisAction extends BaseDao{
	public Result queryin(Map<String, Object> params) throws Exception {
		Result result = new DiagnosisService().queryin(params);
		return result;
	}
	
	public void callinit(Map<String, Object> params) throws Exception {
		new DiagnosisService().init(params);
	}
	
	public Result querysys(Map<String, Object> params) throws Exception {
		Result	result = new DiagnosisService().querysys(params);
		return result;
	}
	
	public void updateMapping(Map<String, Object> params) throws Exception {
		new DiagnosisService().updateMapping(params);
	}
	
	public void direptUpdateSys(Map<String, Object> params) throws Exception {
		new DiagnosisService().direptUpdateSys(params);
	}
	
	public void updateSureMapping(Map<String, Object> params) throws Exception {
		new DiagnosisService().updateSureMapping(params);
	}
	
	
}
