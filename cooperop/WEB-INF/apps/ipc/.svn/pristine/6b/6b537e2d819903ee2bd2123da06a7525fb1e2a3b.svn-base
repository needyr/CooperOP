package cn.crtech.precheck.ipc.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.bus.cache.SystemConfig;
import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.ipc.dao.AutoAuditDao;
import cn.crtech.cooperop.ipc.dao.AutoAuditOrdersDao;
import cn.crtech.cooperop.ipc.dao.CheckDataParmsDao;
import cn.crtech.cooperop.ipc.dao.CheckResultDao;
import cn.crtech.cooperop.ipc.dao.CheckResultInfoDao;
import cn.crtech.cooperop.ipc.dao.DoctorDao;
import cn.crtech.cooperop.ipc.dao.TmpToAutoDao;
import cn.crtech.precheck.ipc.dao.DataDao;
import cn.crtech.precheck.ipc.ws.client.Client;

public class DataService extends BaseService {

	// 审查参数组装+++
	@Deprecated
	public Map<String, Object> save_hyt(Map<String, Object> params, String common_id) throws Exception {
		try {
			Map<String, Object> p = new HashMap<String, Object>();
			p.put("create_time", CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss:SSS"));
			Map<String, Object> reMap = new HashMap<String, Object>();
			Map<String, Object> orders = new HashMap<String, Object>();
			List<Map<String, Object>> item = new ArrayList<Map<String, Object>>();
			Map<String, Object> dp = new HashMap<String, Object>();
			Map<String, Object> doctormap = (Map<String, Object>) params.get("doctor");
			Map<String, Object> patientmap = (Map<String, Object>) params.get("patient");
			reMap.put("patient_id", patientmap.get("id"));//患者id
			reMap.put("visit_id", patientmap.get("visitid"));//visitid
			reMap.put("doctor_no", doctormap.get("code"));
			dp.put("Registerno", patientmap.get("id"));
			dp.put("Prescriptionno", patientmap.get("id"));
			Object hyt_p_type = params.get("d_type");
			if("1".equals(hyt_p_type)){
				hyt_p_type = "2";
			}else if("2".equals(hyt_p_type)){
				hyt_p_type = "1";
			}
			dp.put("Prescriptiontype", hyt_p_type);//处方类型：1 门诊2 住院3 急诊
			dp.put("Prescriptiondate", CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
			dp.put("Cost", "");
			dp.put("Indate", "");
			dp.put("Outdate", "");
			dp.put("Doctorno", doctormap.get("code"));
			dp.put("Doctorname", doctormap.get("name"));
			dp.put("Officename", doctormap.get("departname"));
			dp.put("Patientno", patientmap.get("id"));
			dp.put("Patientname", patientmap.get("name"));
			dp.put("Patienttype", patientmap.get("chargetype"));//患者类别，如自费，医保，绿色通道等
			dp.put("Birthday", patientmap.get("birthday"));
			dp.put("Gender", patientmap.get("sex"));
			dp.put("Height", 0);
			dp.put("Weight", 0);
			dp.put("Opsid", "");//空
			dp.put("Opsname", "");//空
			dp.put("Qktype", "");//切口类型
			dp.put("Wardcode", "");//病区代码
			dp.put("Bednum", patientmap.get("bedno"));
			dp.put("ContactWay", "");//患者联系方式
			dp.put("StickCount", "");//中草药贴数
			dp.put("S3", "");
			dp.put("S4", "");
			dp.put("S5", "");
			dp.put("S6", "");
			dp.put("S7", "");
			dp.put("S8", "");
			dp.put("S9", "");
			dp.put("S10", "");
			
			p.put("doctor_no", doctormap.get("code"));//医生编号
			Map<String, Object> mmp = new HashMap<String, Object>();
			mmp.put("request", params);
			Map<String,Object> temp=new HashMap<String,Object>();
			p.put("patient_id", patientmap.get("id"));
			p.put("visit_id", patientmap.get("visitid"));
			p.put("p_type", params.get("p_type"));
			p.put("d_type", params.get("d_type"));
			p.put("is_after", params.get("is_after"));
			p.put("hospital_id", Client.HOSPITAL_ID);
			p.put("deptment_code", doctormap.get("departcode"));
			p.put("deptment_name", doctormap.get("departname"));
			p.put("common_id", common_id);
			p.put("patient_dept_code", patientmap.get("departcode"));
			p.put("patient_dept_name", patientmap.get("departname"));
			List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
			if(!CommonFun.isNe(params.get("rows")) && params.get("rows") instanceof Map) {
				Map<String, Object> map = (Map<String, Object>) params.get("rows");
				if(map.get("row") instanceof Map) {
					rows.add((Map<String, Object>)map.get("row"));
				}else{
					rows = (List<Map<String, Object>>) map.get("row");
				}
				
			}
			connect("ipc");
			String autoaudit_id = new AutoAuditDao().insert(p);
			temp.put("auto_audit_id", autoaudit_id);
			String new_orders_insert_date;
			String use_orders_insert_date;
			start();//将当前新开的药品插入审查表 事务
			DataDao dd = new DataDao();
			try {
				AutoAuditOrdersDao aad = new AutoAuditOrdersDao();
				for(Map<String, Object> m : rows){
					m.put("auto_audit_id", autoaudit_id);
					if(CommonFun.isNe(m.get("group_id"))) {
						m.put("group_id", m.get("order_no"));
					}else {
						m.put("group_id", m.get("group_id"));
						m.put("order_no", m.get("order_no"));
					}
					m.put("patient_id", patientmap.get("id"));
					m.put("visit_id", patientmap.get("visitid"));
					m.put("dept_code", patientmap.get("departcode"));
					m.put("dept_name", patientmap.get("departname"));
					/*m.put("doctor_no", doctormap.get("code")); 无用的，单行药品有记载，这个不准确 */ 
					m.put("p_type", params.get("p_type"));
					m.remove("exam_place");
					m.remove("exam_methord");
					aad.insert(m);
				}
				new_orders_insert_date = CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss:SSS");
				if(!"1".equals(params.get("is_after"))){//事前审查才需要联合在用药品进行审查
					Result zyOrders = dd.queryUseOrders(p);//病人在用药
					for(Record r: zyOrders.getResultset()){//将历史的在用药插入到审查明细表中，方便后续查询
						r.put("auto_audit_id", autoaudit_id);
						r.remove("rowno");
						r.remove("shengccj");
						aad.insert(r);
					}
				}
				use_orders_insert_date = CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss:SSS");
				commit();
			}catch(Exception ex1) {
				rollback();
				log.error(ex1);
				throw ex1;
			}
				
			Record rp = new Record();
			rp.put("auto_audit_id", autoaudit_id);
			Result scOrders = dd.queryOrdersForHLYY(rp);//查询审查的药品
			for(Record r: scOrders.getResultset()){//拼接审查xml需要的对象
				Map<String, Object> order = new HashMap<String, Object>();
				order.put("Medicinecode", r.get("drug_code_sys"));
				order.put("Medicinename", r.get("drug_name_sys"));
				order.put("Specification", r.get("shpgg_sys"));//规格：500mg:100ml(软袋)
				order.put("Ordertype", r.get("repeat_indicator"));//是否长期医嘱：0 临时医嘱	1 长期医嘱。
				order.put("Doseformname", r.get("administration_sys"));//药品用法，如口服，注射，静注
				order.put("Frequencycode", r.get("frequency_sys"));//用药频次代码，qd等
				order.put("Frequencyname", r.get("frequency_sys"));//用药频次名称，一天一次等
				order.put("Unit", r.get("dw_sys"));//药品单位
				order.put("Price", "");//单价
				order.put("Count", r.get("shl"));//数量
				order.put("Dosage", r.get("dosage"));
				order.put("Dosagename", r.get("dosage_units"));
				order.put("Groupno", r.get("group_id")+"|"+r.get("order_no"));
				order.put("Beginusingtime", r.get("start_date_time"));
				order.put("Endusingtime", r.get("stop_date_time"));
				order.put("Medcinedepartment", "");//发药部门
				order.put("Precodeitem", r.get("p_key"));
				order.put("S2", "");//预留字段
				order.put("S3", "");
				order.put("S4", "");
				order.put("S5", "");
				item.add(order);
			}
			
			
			Result diagnosis = dd.queryDiagnosisForHLYY(p);
			Result opsInfo = dd.queryOpsInfoForHLYY(p);
			Result allergyInfo = dd.queryAllergyInfoForHLYY(p);
			Result physiologyInfo = dd.queryPhysiologyInfoForHLYY(p);
			
			reMap.put("auto_audit_id", autoaudit_id);
			orders.put("MedicineInfoExt", item);
			List<Record> res = new ArrayList<Record>();
			Map<String, Object> diagnosisMap = new HashMap<String, Object>();
			for(Record d : diagnosis.getResultset()){
				Record r = new Record();
				r.put("Diagnosecode", d.get("sys_diagnosis_code"));
				r.put("Diagnosename", d.get("sys_diagnosis_name"));
				res.add(r);
			}
			diagnosisMap.put("DiagnoseInfo", res);
			Map<String, Object> opsInfoMap = new HashMap<String, Object>();
			res = new ArrayList<Record>();
			for(Record d : opsInfo.getResultset()){
				Record r = new Record();
				r.put("Opsid", d.get("sys_operation_code"));
				r.put("Opsname", d.get("sys_operation_name"));
				r.put("Qktype", d.get("sys_woundgrade_code"));//跟慧药通确认，切口配对，传入切口code还是name
				r.put("Begintime", d.get("start_date_time"));
				r.put("Endtime", d.get("end_date_time"));
				res.add(r);
			}
			opsInfoMap.put("OpsInfo", res);
			Map<String, Object> allergyInfoMap = new HashMap<String, Object>();
			res = new ArrayList<Record>();
			//2019-04-25 王更改  没有过敏药物code所以不去拼接过敏信息,防止审查报错
			for(Record d : allergyInfo.getResultset()){
				Record r = new Record();
				r.put("Allergycode", d.get("sys_alergy_drugs_no"));
				r.put("Allergyname", d.get("sys_alergy_drugs"));
				if(!CommonFun.isNe(d.get("sys_alergy_drugs_no"))) {
					res.add(r);
				}
			}
			allergyInfoMap.put("AllergyInfo", res);
			Map<String, Object> physiologyInfoMap = new HashMap<String, Object>();
			res = new ArrayList<Record>();
			for(Record d : physiologyInfo.getResultset()){
				Record r = new Record();
				r.put("Physiologycode", d.get("sys_physiologyinfo_code"));
				r.put("Physiologyname", d.get("sys_physiologyinfo_name"));
				res.add(r);
			}
			physiologyInfoMap.put("PhysiologyInfo", res);
			reMap.put("request", getResponse_HYT_ws(dp, orders
					,diagnosisMap, opsInfoMap, allergyInfoMap, physiologyInfoMap));
			//Map<String,Object> maphyt=new HashMap<String,Object>();
			//maphyt.put("id", autoaudit_id);
			/* maphyt.put("request_hlyy", reMap.get("request")); */
			//maphyt.put("thirdt_request", reMap.get("request"));
			temp.put("thirdt_request", reMap.get("request"));
			/* new AutoAuditDao().update(maphyt); */
			CheckDataParmsDao cdp = new CheckDataParmsDao();
			cdp.insert(temp);
			return reMap;
		} catch(Exception e){
			e.printStackTrace();
			throw e;
		} finally {
			disconnect();
		}
	}
	
	@Deprecated
	public Map<String, Object> getResponse_HYT(Map<String, Object> dp, Object orders, Object diagnoseinfos, Object opsinfos, Object allergyinfos, Object physiologyinfos) {
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer xml = new StringBuffer();
		/*xml.append("<?xml version='1.0' encoding='gbk'?>");
		xml.append("\r\n<PrescriptionContent xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' xmlns:xsd='http://www.w3.org/2001/XMLSchema'>");*/
		map.put("PrescriptionInfoExt", dp);
		
		map.put("MedicineInfoExtS", orders);
		
		map.put("DiagnoseInfoS", diagnoseinfos);
		
		map.put("OpsInfoS", opsinfos);
		
		map.put("AllergyInfoS", allergyinfos);
		
		map.put("PhysiologyInfoS", physiologyinfos);
		
		/*xml.append("</PrescriptionContent>");*/
		
		return map;
	}
	
	public String getResponse_HYT_ws(Map<String, Object> dp, Object orders, Object diagnoseinfos, Object opsinfos, Object allergyinfos, Object physiologyinfos) {
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer xml = new StringBuffer();
		xml.append("<?xml version='1.0' encoding='gbk'?>");
		xml.append("\r\n<PrescriptionContent xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' xmlns:xsd='http://www.w3.org/2001/XMLSchema'>");
		map.put("PrescriptionInfoExt", dp);
		xml.append(CommonFun.object2Xml(map));
		
		map.clear();
		map.put("MedicineInfoExtS", orders);
		xml.append(CommonFun.object2Xml(map));
		
		map.clear();
		map.put("DiagnoseInfoS", diagnoseinfos);
		xml.append(CommonFun.object2Xml(map));
		
		map.clear();
		map.put("OpsInfoS", opsinfos);
		xml.append(CommonFun.object2Xml(map));
		
		map.clear();
		map.put("AllergyInfoS", allergyinfos);
		xml.append(CommonFun.object2Xml(map));
		
		map.clear();
		map.put("PhysiologyInfoS", physiologyinfos);
		xml.append(CommonFun.object2Xml(map));
		
		xml.append("</PrescriptionContent>");
		
		return xml.toString();
	}
	
	public void saveDoctorAdvices(Map<String, Object> params) throws Exception{
		try {
			connect("ipc");
			start();
			//审查结果分类表储存用药意见
			List<Map<String, Object>> cr=CommonFun.json2Object((String)params.get("advices"),List.class);
			if(!CommonFun.isNe(cr)){
				CheckResultDao crd = new CheckResultDao();
				for (Map<String, Object> map : cr) {
					map.put("auto_audit_id", params.get("id"));
					crd.updateCheckResult(map);
				}
			}
			if("0".equals(SystemConfig.getSystemConfigValue("hospital_common","wait_time","30"))) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", params.get("id"));
				map.put("is_overtime", "1");
				new AutoAuditDao().update(map);
			}
			commit();
		} catch(Exception e){
			rollback();
			throw e;
		} finally {
			disconnect();
		}
	}
	public void updateForYXK(Map<String, Object> params) throws Exception {
		try {
			/** 
			 * 判断state为null,调用处为mustdo 和 审查结果为通过，通过时已经记录状态，无需再记录，不加判断会报错
			 * @author yanguozhi 2019-01-22 
			 * */
			connect("ipc");
			if(!CommonFun.isNe(params.get("state"))) {
				params.put("auto_audit_id", params.get("id"));
				Object id = params.get("id");
				AutoAuditDao aad = new AutoAuditDao();
				Map<String, Object> p = new HashMap<String, Object>();
				p.put("id", id);
				Object state = params.remove("state");
				if(!CommonFun.isNe(state)){
					p.put("state", state);
				}
				if("0".equals(SystemConfig.getSystemConfigValue("ipc", "wait_time", "30"))){
					p.put("is_overtime", "1");
				}
				if (state != null) {
					if (state.equals("DQ")) {
						p.put("constraint_time", CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));//强制用药时间
					}
				}
				aad.update(p);
				if(CommonFun.isNe(params.get("auto_audit_id"))) {
					params.put("auto_audit_id", params.get("id"));
				}
				if (state != null && state.equals("DQ")) {
					insertTmpToAuto(params);
				}else if("1".equals(SystemConfig.getSystemConfigValue("ipc", "pass_toyun","0"))) {
					insertTmpToAuto(params);
				} 
			}
		} catch(Exception e){
			e.printStackTrace();
			throw e;
		} finally {
			disconnect();
		}
	}
	public Map<String, Object> queryForYXK(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			long start = System.currentTimeMillis();
			String id = (String)params.get("id");
			String hospital_id = Client.HOSPITAL_ID;
			Map<String, Object> reMap = new HashMap<String, Object>();
			AutoAuditDao aad = new AutoAuditDao();
			DataDao dd = new DataDao();
			Record autoRecord = aad.getTransfer(id);
			autoRecord.remove("rownumA");
			autoRecord.remove("rowno");
			
			//autoRecord.put("advice", params.get("qadvice"));
			reMap.put("auto_audit", autoRecord);
			reMap.put("auto_audit_orders", new AutoAuditOrdersDao().query(params).getResultset());
			reMap.put("check_result", new CheckResultDao().queryForYXK(params).getResultset());
			reMap.put("check_result_info", new CheckResultInfoDao().queryYXK(params).getResultset());
			params.put("patient_id", autoRecord.get("patient_id"));
			//reMap.put("his_patient", dd.getPatient(params));
			//reMap.put("his_in_pats", dd.getInPatient(params));
			//reMap.put("his_in_patientvisit", dd.queryPatientVisit(params).getResultset());
			params.put("visit_id", autoRecord.get("visit_id"));
			reMap.put("auto_audit_diagnosis", dd.queryDiagnosisForYXK(params).getResultset());
			reMap.put("auto_audit_exam_master", dd.queryLABYXK(params).getResultset());
			reMap.put("auto_audit_exam_report", dd.queryLABResultYXK(params).getResultset());
			//更改整体表结构
			reMap.put("auto_audit_lab_test_master", dd.queryEXAMYXK(params).getResultset());
			reMap.put("auto_audit_lab_result", dd.queryEXAMResultYXK(params).getResultset());
			reMap.put("auto_audit_operation_master", dd.queryEXAMAAOM(params).getResultset());
			reMap.put("auto_audit_operation_name", dd.queryEXAMAAON(params).getResultset());
			//---------------
			reMap.put("auto_audit_vital_signs_master", dd.queryVitalYXK(params).getResultset());
			reMap.put("auto_audit_vital_signs_rec", dd.queryVitalResultYXK(params).getResultset());
			reMap.put("auto_audit_PhysiologyInfo", dd.queryPhysiologyInfoYXK(params).getResultset());
			reMap.put("auto_audit_alergy_drugs", dd.queryALERGYDRUGSInfoYXK(params).getResultset());
			reMap.put("his_in_patient_basicinfo", dd.querypatientbasicinfoYXK(params).getResultset());
			//增加TPN数据
			reMap.put("cr_tmp_fenxitux_linechart", dd.queryTPNLineYXK(params).getResultset());
			reMap.put("cr_tmp_fenxitux_piechart", dd.queryTPNPieYXK(params).getResultset());
			reMap.put("cr_tmp_fenxitux_radarchart", dd.queryTPNRadarYXK(params).getResultset());
			//-------------
			reMap.put("auto_audit_id", params.get("auto_audit_id"));
			reMap.put("hospital_id", hospital_id);
			//处方病人表
			log.debug("[queryForYXK]#################################################插入auto_audit +查询套表数据耗时" + (System.currentTimeMillis()- start));
			return reMap;
			
		} catch(Exception e){
			e.printStackTrace();
			throw e;
		} finally {
			disconnect();
		}
	}
	
	public void insertTmpToAuto(Map<String, Object> params) throws Exception {
		try {
			TmpToAutoDao ttadao = new TmpToAutoDao();
			ttadao.insertAuditDiagnosis(params);
			ttadao.insertAuditExamMaster(params);
			ttadao.insertAuditExamReport(params);
			ttadao.insertAuditLabTestMaster(params);
			ttadao.insertAuditLabResult(params);
			ttadao.insertAuditOperationName(params);
			ttadao.insertAuditOperationMaster(params);
			ttadao.insertAuditVitalSignsMaster(params);
			ttadao.insertAuditVitalSignsRec(params);
			ttadao.insertAuditPhysiologyInfo(params);
			ttadao.insertAuditAlergyDrugs(params);
		} catch(Exception e){
			rollback();
			throw e;
		} 
	}
	
	public void update(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			new AutoAuditDao().update(params);
		} catch(Exception e){
			throw e;
		} finally {
			disconnect();
		}
	}
	
	public void updateResult(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			new AutoAuditDao().update(params);
			//TODO 调用一个存储过程，或者调用his的一个接口告知人工审方的结果
		} catch(Exception e){
			throw e;
		} finally {
			disconnect();
		}
	}
	
	public void updateDoctorResult(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			//将结果推送到云端
			Client.sendDoctorDeal((String)params.get("doctor_no"), params);
			new AutoAuditDao().update(params);
			 //TODO 调用存储过程回写回写标识,
			
		} catch(Exception e){
			throw e;
		} finally {
			disconnect();
		}
	}
	
	public Map<String, Object> queryUserInfo(Map<String, Object> params)throws Exception {
		try {
			connect("ipc");
			DataDao dd = new DataDao();
			Map<String, Object> re = new HashMap<String, Object>();
			re.put("doctors", dd.queryDoctors(params).getResultset());
			re.put("deps", dd.queryDeps(params).getResultset());
			re.put("hospital_id", SystemConfig.getSystemConfigValue("hospital_common", "hospital_id"));
			return re;
		} catch(Exception e){
			throw e;
		} finally {
			disconnect();
		}
	}
	
	public Map<String, Object> querySmsData(Map<String, Object> params)throws Exception {
		try {
			connect("ipc");
			DataDao dfy = new DataDao();
			Map<String, Object> rtnMap = new HashMap<String, Object>();
			rtnMap.put("sms", dfy.queryShuoms(new HashMap<String,Object>()).getResultset());
			return rtnMap;
		} catch(Exception e){
			throw e;
		} finally {
			disconnect();
		}
	}
	
	public void insertComment(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			new AutoAuditDao().insertComment(params);
		} catch(Exception e){
			throw e;
		} finally {
			disconnect();
		}
	}
	
	public Map<String, Object> queryCommentData(Map<String, Object> params)throws Exception {
		try {
			connect("ipc");
			DataDao dat = new DataDao();
			Map<String, Object> rtnMap = new HashMap<String, Object>();
			rtnMap.put("comments", dat.queryComment(new HashMap<String,Object>()).getResultset());
			return rtnMap;
		} catch(Exception e){
			throw e;
		} finally {
			disconnect();
		}
	}
	
	public Result queryUsers(Map<String, Object> params)throws Exception {
		try {
			connect("ipc");
			DataDao dat = new DataDao();
			return dat.queryUsers(params);
		} catch(Exception e){
			throw e;
		} finally {
			disconnect();
		}
	}
	
	public int updateIsSync()throws Exception {
		try {
			connect("ipc");
			Map<String, Object> map =new HashMap<String, Object>();
			map.put("users", new DoctorDao().querySync(new HashMap<String, Object>()).getResultset());
			map.put("hospital_id", Client.HOSPITAL_ID);
			try {
				Client.syncUsersBatch("admin_jq", map);
			} catch (Exception e) {
			}
			return 1;
		} catch(Exception e){
			throw e;
		} finally {
			disconnect();
		}
	}
	public void updateCheckResultInfo(Map<String, Object> m) throws Exception {
		try {
			connect("ipc");
			start();
			new CheckResultInfoDao().update(m);
			commit();
		} catch(Exception e){
			rollback();
			throw e;
		} finally {
			disconnect();
		}
	}
	
}
