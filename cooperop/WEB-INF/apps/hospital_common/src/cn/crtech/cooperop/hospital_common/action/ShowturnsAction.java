package cn.crtech.cooperop.hospital_common.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.application.authenticate.DisLoggedIn;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.service.AutoCommonService;
import cn.crtech.cooperop.hospital_common.service.DictdrugService;
import cn.crtech.cooperop.hospital_common.service.PatientService;
import cn.crtech.cooperop.hospital_common.service.ShowTurnsService;
import cn.crtech.cooperop.hospital_common.service.TPNService;

public class ShowturnsAction extends BaseAction {
	
	@DisLoggedIn
	public Map<String, Object> detail(Map<String, Object> map) throws Throwable{
		return new ShowTurnsService().detailForCR(map);
	}
	
	@DisLoggedIn
	public Map<String, Object> show(Map<String, Object> map) throws Throwable{
		return new ShowTurnsService().detail(map);
	}
	
	@DisLoggedIn
	public Map<String, Object> detailshow(Map<String, Object> map) throws Throwable{
		return new ShowTurnsService().detail(map);
	}
	
	@DisLoggedIn
	public Map<String, Object> mustDo(Map<String, Object> map) throws Throwable{
		return new ShowTurnsService().mustDo(map);
	}
	
	@DisLoggedIn
	public void sureAgain(Map<String, Object> map) throws Throwable{
		new ShowTurnsService().sureAgain(map);
	}
	
	@DisLoggedIn
	public Map<String, Object> getPCResult(Map<String, Object> map) throws Throwable{
		return new ShowTurnsService().getPCResult(map);
	}
	
	@DisLoggedIn
	public Map<String, Object> isWork(Map<String, Object> map) throws Throwable{
		map.put("iswork", new ShowTurnsService().isPharmacistWork(map));
		return map;
	}
	
	@DisLoggedIn
	public Map<String, Object> hadSubmit(Map<String, Object> map) throws Throwable{
		return new ShowTurnsService().hadSubmit(map);
	}
	
	public Result queryAudit(Map<String, Object> map) throws Throwable{
		return new AutoCommonService().queryAudit(map);
	}
	
	public Map<String, Object> onlyGetTime(Map<String, Object> map) throws Throwable{
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
	
		
	/*@DisLoggedIn
	public Map<String, Object> patientdetail(Map<String, Object> map) throws Exception{
		return new PatientService().querypatient(map);
	}*/
	
	/**
	 * 患者详情医嘱信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@DisLoggedIn
	public List<Record> patientdetailYZ(Map<String, Object> map) throws Exception{
		return new PatientService().patientdetailYZ(map);
	}
	
	/**
	 * 患者详情诊断信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@DisLoggedIn
	public List<Record> patientdetailZD(Map<String, Object> map) throws Exception{
		return new PatientService().patientdetailZD(map);
	}
	
	/**
	 * 患者详情检查信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@DisLoggedIn
	public List<Record> patientdetailJC(Map<String, Object> map) throws Exception{
		return new PatientService().patientdetailJC(map);
	}
	
	/**
	 * 患者详情检验信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@DisLoggedIn
	public List<Record> patientdetailJY(Map<String, Object> map) throws Exception{
		return new PatientService().patientdetailJY(map);
	}
	
	/**
	 * 患者详体征信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
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
	
	//简要提示说明书
	@DisLoggedIn
	public Map<String, Object> simpleinfo2(Map<String, Object> map) throws Exception{
		//return new ShowTurnsService().getbydrugcode(map);
		return new ShowTurnsService().queryDrugByHisCode(map);
	}
	
	@DisLoggedIn
	public Result queryAllShuoms(Map<String, Object> map) throws Exception{
		return new ShowTurnsService().queryAllShuoms(map);
	}
	
	//简要提示说明书
	@DisLoggedIn
	public Map<String, Object> getSimpleinfo(Map<String, Object> map) throws Exception{
		return new ShowTurnsService().getbydrugcode(map);
	}
		
	@DisLoggedIn
	public Map<String, Object> queryBillItems(Map<String, Object> map) throws Exception{
		return new PatientService().queryBillItems(map);
	}
	
	@DisLoggedIn
	public void iMicChoose(Map<String, Object> map) throws Exception{
		new ShowTurnsService().iMicChoose(map);
	}
	
	/**
	 * 查找业务流水表中的审查状态
	 * @param map
	 * @return
	 * @throws Throwable
	 */
	public Result queryAuditAll(Map<String, Object> map) throws Throwable{
		return new AutoCommonService().queryAuditAll(map);
	}
	
	@DisLoggedIn
	public Map<String, Object> ocresult(Map<String, Object> map) throws Throwable{
		return new ShowTurnsService().getOrdersCR(map);
	}
	
	@DisLoggedIn
	public Map<String, Object> getDrug(Map<String, Object> map) throws Throwable{
		return new DictdrugService().getDrugByCode(map);
	}
	
	@DisLoggedIn
	public String queryTpn(Map<String, Object> map) throws Exception {
		String rtnStr = "";
		try {
			TPNService tpnPieService = new TPNService();
			return tpnPieService.queryTpn(map);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return rtnStr;
	}
	
	@DisLoggedIn
	public Map<String, Object> indexinfo(Map<String, Object> map) throws Exception{
		return new TPNService().getTPNData(map);
	}
	
	@DisLoggedIn
	public Map<String, Object> checkPrint(Map<String, Object> map) throws Throwable{
		return new ShowTurnsService().checkPrint(map);
	}
	
	@DisLoggedIn
	public Map<String, Object> print(Map<String, Object> map) throws Throwable{
		return new ShowTurnsService().print(map);
	}
	
}