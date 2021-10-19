package cn.crtech.cooperop.hospital_common.action;

import java.util.HashMap;
import java.util.Map;
import cn.crtech.cooperop.application.authenticate.DisLoggedIn;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.service.patient.PatientService;
import cn.crtech.precheck.EngineInterface;

public class PatientAction extends BaseAction {
	
	@DisLoggedIn
	public Map<String, Object> index(Map<String, Object> params) throws Throwable{
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		rtnMap.put("ipc", EngineInterface.allProduct.get("ipc"));
		rtnMap.put("hospital_common", EngineInterface.allProduct.get("hospital_common"));
		rtnMap.put("hospital_imic", EngineInterface.allProduct.get("hospital_imic"));
		return rtnMap;
	}	
	
	@DisLoggedIn
	public Map<String, Object> baseinfo(Map<String, Object> params) throws Throwable{
		return new PatientService().baseinfo(params);
	}
	
	@DisLoggedIn
	public Map<String, Object> order(Map<String, Object> params) throws Throwable{
		return new PatientService().queryOrders(params);
	}
	
	@DisLoggedIn
	public Map<String, Object> bill2(Map<String, Object> params) throws Throwable{
		String imic = EngineInterface.allProduct.get("hospital_imic");
		if(CommonFun.isNe(imic)) {
			params.put("zzf_show", 0);
		}else {
			// 包含转自费标识
			params.put("zzf_show", 1);
		}
		return params;
	}
	
	@DisLoggedIn
	public Result order2(Map<String, Object> params) throws Throwable{
		return new PatientService().queryOrders2(params);
	}
	
	@DisLoggedIn
	public Map<String, Object> ipc_result(Map<String, Object> params) throws Throwable{
		return new PatientService().queryIpcResult(params);
	}

	@DisLoggedIn
	public Map<String, Object> check(Map<String, Object> params) throws Exception{
		return new PatientService().queryCheck(params);
	}
	
	@DisLoggedIn
	public Map<String, Object> oper(Map<String, Object> params) throws Exception {
		return new PatientService().queryOper(params);
	}
	
	@DisLoggedIn
	public Map<String, Object> oper2(Map<String, Object> params) throws Exception {
		return new PatientService().queryOper2(params);
	}
	
	@DisLoggedIn
	public Map<String, Object> queryBillItems(Map<String, Object> map) throws Exception{
		return new PatientService().queryBillItems(map);
	}
	
	@DisLoggedIn
	public Result queryBill(Map<String, Object> map) throws Exception{
		return new PatientService().queryBill(map);
	}
	
	@DisLoggedIn
	public Result billType(Map<String, Object> map) throws Exception{
		return new PatientService().queryBillType(map);
	}
	
	@DisLoggedIn
	public Result queryDiagnosis(Map<String, Object> map) throws Exception{
		return new PatientService().queryDiagnosis(map);
	}
	
	@DisLoggedIn
	public Map<String, Object> signs(Map<String, Object> map) throws Exception{
		return new PatientService().querySigns(map);
	}
	
	@DisLoggedIn
	public Map<String, Object> signs2(Map<String, Object> map) throws Exception{
		return new PatientService().querySigns(map);
	}
	
	@DisLoggedIn
	public Map<String, Object> sig_report(Map<String, Object> map) throws Exception{
		return new PatientService().sig_report(map);
	}
	
	@DisLoggedIn
	public Map<String, Object> querySignsDetail(Map<String, Object> map) throws Exception{
		return new PatientService().querySignsDetail(map);
	}
	
	@DisLoggedIn
	public Map<String, Object> queryExamDtl(Map<String, Object> map) throws Exception{
		return new PatientService().queryExamDtl(map);
	}
	
	@DisLoggedIn
	public Map<String, Object> queryOperDtl(Map<String, Object> map) throws Exception{
		return new PatientService().queryOperDtl(map);
	}
	
	@DisLoggedIn
	public Map<String, Object> inspection(Map<String, Object> map) throws Exception{
		return new PatientService().queryInspection(map);
	}
	
	@DisLoggedIn
	public Map<String, Object> exportInspection(Map<String, Object> map) throws Exception{
		return new PatientService().exportInspection(map);
	}
	
	@DisLoggedIn
	public Map<String, Object> exportCheck(Map<String, Object> map) throws Exception{
		return new PatientService().exportCheck(map);
	}

	@DisLoggedIn
	public Map<String, Object> exportOrders(Map<String, Object> map) throws Exception{
		return new PatientService().exportOrders(map);
	}

	@DisLoggedIn
	public Map<String,Object> exportDignosis(Map<String, Object> map) throws Exception{
		return new PatientService().exportDignosis(map);
	}

	/**
	 * 检验图表显示
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@DisLoggedIn
	public Map<String, Object> insp_report(Map<String, Object> map) throws Exception{
		return new PatientService().insp_report(map);
	}
	
	
	@DisLoggedIn
	public Map<String, Object> imic_result(Map<String, Object> map) throws Exception{
		return new PatientService().queryImicType(map);
	}
	
	@DisLoggedIn
	public Result queryImicDtl(Map<String, Object> map) throws Exception{
		return new PatientService().queryImicDtl(map);
	}
	//////////////////////////////////////////////////////////////
	/*public Map<String, Object> onlyGetTime(Map<String, Object> map) throws Throwable{
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		map.put("today", sdf.format(date));
		return map;
	}
	
	@DisLoggedIn
	public Map<String, Object> queryComment(Map<String, Object> map) throws Throwable{
		return new ShowTurnsService().queryComment(map);
	}
	
	@DisLoggedIn
	public Map<String, Object> backAdjust(Map<String, Object> map) throws Exception{
		return new ShowTurnsService().backAdjust(map);
	}
	
		
	@DisLoggedIn
	public Map<String, Object> patientdetail(Map<String, Object> map) throws Exception{
		return new PatientService().querypatient(map);
	}
	
	*//**
	 * 患者详情医嘱信息
	 * @param map
	 * @return
	 * @throws Exception
	 *//*
	@DisLoggedIn
	public List<Record> patientdetailYZ(Map<String, Object> map) throws Exception{
		return new PatientService().patientdetailYZ(map);
	}
	
	*//**
	 * 患者详情诊断信息
	 * @param map
	 * @return
	 * @throws Exception
	 *//*
	@DisLoggedIn
	public List<Record> patientdetailZD(Map<String, Object> map) throws Exception{
		return new PatientService().patientdetailZD(map);
	}
	
	*//**
	 * 患者详情检查信息
	 * @param map
	 * @return
	 * @throws Exception
	 *//*
	@DisLoggedIn
	public List<Record> patientdetailJC(Map<String, Object> map) throws Exception{
		return new PatientService().patientdetailJC(map);
	}
	
	*//**
	 * 患者详情检验信息
	 * @param map
	 * @return
	 * @throws Exception
	 *//*
	@DisLoggedIn
	public List<Record> patientdetailJY(Map<String, Object> map) throws Exception{
		return new PatientService().patientdetailJY(map);
	}
	
	*//**
	 * 患者详体征信息
	 * @param map
	 * @return
	 * @throws Exception
	 *//*
	@DisLoggedIn
	public List<Record> patientdetailTZ(Map<String, Object> map) throws Exception{
		return new PatientService().patientdetailTZ(map);
	}
		
	@DisLoggedIn
	public Map<String, Object> getexamdetail(Map<String, Object> map) throws Exception{
		return new PatientService().getexamdetail(map);
	}
	
	@DisLoggedIn
	public Map<String, Object> queryvitaldetail(Map<String, Object> map) throws Exception{
		return new PatientService().queryvitaldetail(map);
	}
		
	@DisLoggedIn
	public Map<String, Object> queryrequestendetail(Map<String, Object> map) throws Exception{
		return new PatientService().queryrequestendetail(map);
	}
	
	@DisLoggedIn
	public Map<String, Object> queryOperDetil(Map<String, Object> map) throws Exception{
		return new PatientService().queryOperDetil(map);
	}
	
	@DisLoggedIn
	public Map<String, Object> getSimpleInfo(Map<String, Object> map) throws Exception{
		return new ShowTurnsService().getSimpleInfo(map);
	}
		
	@DisLoggedIn
	public Map<String, Object> queryBillItems(Map<String, Object> map) throws Exception{
		return new PatientService().queryBillItems(map);
	}
	
	@DisLoggedIn
	public void iMicChoose(Map<String, Object> map) throws Exception{
		new ShowTurnsService().iMicChoose(map);
	}
	
	*//**
	 * 查找业务流水表中的审查状态
	 * @param map
	 * @return
	 * @throws Throwable
	 *//*
	public Result queryAuditAll(Map<String, Object> map) throws Throwable{
		return new AutoCommonService().queryAuditAll(map);
	}*/
}