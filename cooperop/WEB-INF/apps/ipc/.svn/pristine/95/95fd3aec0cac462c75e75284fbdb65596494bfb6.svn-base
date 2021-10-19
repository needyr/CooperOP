package cn.crtech.precheck.ipc.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.application.authenticate.DisLoggedIn;
import cn.crtech.cooperop.bus.cache.SystemConfig;
import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.ipc.dao.AutoAuditDao;
import cn.crtech.cooperop.ipc.dao.CheckResultDao;
import cn.crtech.cooperop.ipc.dao.CheckResultInfoDao;
import cn.crtech.cooperop.ipc.service.AutoAuditService;
import cn.crtech.cooperop.ipc.service.CheckDataParmsService;
import cn.crtech.cooperop.ipc.util.Util;
import cn.crtech.precheck.ipc.audit_def.TPNVerdict;
import cn.crtech.precheck.ipc.dao.DataDao;
import cn.crtech.precheck.ipc.dao.ThirdpartyDataDao;
import cn.crtech.precheck.ipc.service.AuditService;
import cn.crtech.precheck.ipc.service.EngineHLYY;
import cn.crtech.precheck.ipc.ws.client.Client;

public class HYTDllAuditServiceITHmpl extends BaseService implements AuditService{
	
	/** 合理用药审查最大等到时间 */
	private static int maxtime = 10 * Integer.parseInt(SystemConfig.getSystemConfigValue("ipc", "time_waitsckeck", "6")) + 1; 

	@Override
	public Map<String, Object> joinParams(Map<String, Object> params, String auto_audit_id) throws Exception {
		try {
			Map<String, Object> p = new HashMap<String, Object>();
			p.put("create_time", CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss:SSS"));
			Map<String, Object> reMap = new HashMap<String, Object>();
			//Map<String, Object> orders = new HashMap<String, Object>();
			Map<String, Object> all_hyt = new HashMap<String, Object>();
			List<Map<String, Object>> item = new ArrayList<Map<String, Object>>();
			Map<String, Object> dp = new HashMap<String, Object>();
			Map<String, Object> doctormap = (Map<String, Object>) params.get("doctor");
			Map<String, Object> patientmap = (Map<String, Object>) params.get("patient");
			reMap.put("patient_id", patientmap.get("id"));//患者id
			reMap.put("visit_id", patientmap.get("visitid"));//visitid
			dp.put("registerno", patientmap.get("id"));
			dp.put("prescriptionno", patientmap.get("id"));
			Object hyt_p_type = params.get("d_type");
			if("1".equals(hyt_p_type)){
				hyt_p_type = "2";
			}else if("2".equals(hyt_p_type)){
				hyt_p_type = "1";
			}
			dp.put("prescriptiontype", hyt_p_type);//处方类型：1 门诊2 住院3 急诊
			dp.put("prescriptiondate", CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
			dp.put("cost", "");
			dp.put("indate", "");
			dp.put("outdate", "");
			dp.put("doctorno", doctormap.get("code"));
			dp.put("doctorname", doctormap.get("name"));
			dp.put("officename", doctormap.get("departname"));
			dp.put("patientno", patientmap.get("id"));
			dp.put("patientname", patientmap.get("name"));
			dp.put("patienttype", "");//患者类别，如自费，医保，绿色通道等
			dp.put("birthday", patientmap.get("birthday"));
			dp.put("gender", patientmap.get("sex"));
			dp.put("height", CommonFun.isNe(patientmap.get("height"))?0:Integer.parseInt(String.valueOf(patientmap.get("height"))));
			Double weight = CommonFun.isNe(patientmap.get("weight"))?0:Double.parseDouble(String.valueOf(patientmap.get("weight")));
			if(weight > 250) {
				weight = weight/1000;
			}
			dp.put("weight", weight);
			dp.put("opsid", "");//空
			dp.put("opsname", "");//空
			dp.put("qktype", "");//切口类型
			dp.put("wardcode", "");//病区代码
			dp.put("bednum", "");
			dp.put("contactway", "");//患者联系方式
			dp.put("stickcount", "");//中草药贴数
			dp.put("s3", "");
			dp.put("s4", "");
			dp.put("s5", "");
			dp.put("s6", "");
			dp.put("s7", "");
			dp.put("s8", "");
			dp.put("s9", "");
			dp.put("s10", "");
			
			p.put("doctor_no", doctormap.get("code"));//医生编号
			p.put("patient_id", patientmap.get("id"));
			p.put("visit_id", patientmap.get("visitid"));
			p.put("p_type", params.get("p_type"));
			p.put("d_type", params.get("d_type"));
			p.put("hospital_id", Client.HOSPITAL_ID);
			p.put("deptment_code", doctormap.get("departcode"));
			p.put("deptment_name", doctormap.get("departname"));
			p.put("patient_dept_code", patientmap.get("departcode"));
			p.put("patient_dept_name", patientmap.get("departname"));
			List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
			if(!CommonFun.isNe(params.get("orders"))) {
				rows = (List<Map<String, Object>>)params.get("orders");
			}
			
			connect("autopa_thirdparty");
			// --
			String autoaudit_id = new AutoAuditDao().insert(p);
			
			List<Record> orders_list = new ArrayList<Record>();
			
			start();//将当前新开的药品插入审查表 事务
			DataDao dd = new DataDao();
			
			try {
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
					m.put("p_type", params.get("p_type"));
					m.put("id", CommonFun.getITEMID());
					m.put("p_key_th", m.get("p_key"));
					Record mm = new Record();
					mm.putAll(m);
					orders_list.add(mm);
				}
				if (orders_list.size() > 0) {
					Util.executeBatchInsert("auto_audit_orders", orders_list, this.getConnection());
				}
				commit();
			}catch(Exception ex1) {
				rollback();
				log.error(ex1);
				throw ex1;
			}
				
			Record rp = new Record();
			rp.put("auto_audit_id", autoaudit_id);
			Result scOrders = dd.queryOrdersForHLYY(rp);//查询审查的药品
			//+++TPN药品判断
			disconnect();
			new TPNVerdict().is_TPN(null, scOrders.getResultset());
			try {
				connect("autopa_thirdparty");
				ThirdpartyDataDao thirdpartyDataDao = new ThirdpartyDataDao();
				//患者数据
				Map<String, Object> add_pa = new HashMap<String, Object>();
				add_pa.put("id", CommonFun.getITEMID());
				add_pa.put("patient_id", patientmap.get("id"));
				add_pa.put("visit_id", patientmap.get("visitid"));
				add_pa.put("patient_name", patientmap.get("name"));
				add_pa.put("sex", patientmap.get("sex"));
				add_pa.put("dept_code", patientmap.get("departcode"));
				add_pa.put("dept_name", patientmap.get("departname"));
				add_pa.put("admissiondate", patientmap.get("admissiondate"));
				add_pa.put("birthday", patientmap.get("birthday"));
				add_pa.put("weight", patientmap.get("weight"));
				add_pa.put("height", patientmap.get("height"));
				add_pa.put("auto_audit_id", autoaudit_id);
				thirdpartyDataDao.insertPatient(add_pa);
				//诊断*
				if(!CommonFun.isNe(params.get("diagnosis"))) {
					List<Record> add_diagno_list = new ArrayList<Record>();
					List<Map<String, Object>> dia = (List<Map<String, Object>>)params.get("diagnosis");
					for (Map<String, Object> map : dia) {
						Record add_diagno = new Record();
						add_diagno.put("id", CommonFun.getITEMID());
						add_diagno.put("patient_id", patientmap.get("id"));
						add_diagno.put("visit_id", patientmap.get("visitid"));
						add_diagno.put("auto_audit_id", autoaudit_id);
						add_diagno.put("diagnosis_code", map.get("diagnosecode"));
						add_diagno.put("diagnosis_no", map.get("diagnosecode"));
						add_diagno.put("diagnosis_desc", map.get("diagnosename"));
						add_diagno.put("diagnosis_date", map.get("diagnosis_date"));
						add_diagno_list.add(add_diagno);
					}
					if (add_diagno_list.size() > 0) {
						Util.executeBatchInsert("auto_audit_diagnosis", add_diagno_list, this.getConnection());
					}
				}
				//手术*
				if(!CommonFun.isNe(params.get("oper"))) {
					List<Record> add_oper_name_list = new ArrayList<Record>();
					List<Map<String, Object>> oper = (List<Map<String, Object>>)params.get("oper");
					for (Map<String, Object> map : oper) {
						Record add_oper_name = new Record();
						add_oper_name.put("id", CommonFun.getITEMID());
						add_oper_name.put("patient_id", patientmap.get("id"));
						add_oper_name.put("visit_id", patientmap.get("visitid"));
						add_oper_name.put("auto_audit_id", autoaudit_id);
						add_oper_name.put("operation_code", map.get("operid"));
						add_oper_name.put("operation", map.get("operation"));
						add_oper_name.put("start_date_time", map.get("begintime"));
						add_oper_name.put("end_date_time", map.get("endtime"));
						add_oper_name.put("wound_grade", map.get("qktype"));
						add_oper_name_list.add(add_oper_name);
					}
					if (add_oper_name_list.size() > 0) {
						Util.executeBatchInsert("auto_audit_oper", add_oper_name_list, this.getConnection());
					}
				}
				//过敏*
				if(!CommonFun.isNe(params.get("allergy"))) {
					List<Record> add_allergy_list = new ArrayList<Record>();
					List<Map<String, Object>> allergy = (List<Map<String, Object>>)params.get("allergy");
					for (Map<String, Object> map : allergy) {
						Record add_allergy = new Record();
						add_allergy.put("id", CommonFun.getITEMID());
						add_allergy.put("patient_id", patientmap.get("id"));
						add_allergy.put("visit_id", patientmap.get("visitid"));
						add_allergy.put("auto_audit_id", autoaudit_id);
						add_allergy.put("alergy_drugs_code", map.get("allergycode"));
						add_allergy.put("alergy_drugs", map.get("allergyname"));
						add_allergy_list.add(add_allergy);
					}
					if (add_allergy_list.size() > 0) {
						Util.executeBatchInsert("auto_audit_ALERGY_DRUGs", add_allergy_list, this.getConnection());
					}
				}
				//病人生理状态*
				if(!CommonFun.isNe(params.get("physiologyinfo"))) {
					List<Record> add_phy_list = new ArrayList<Record>();
					List<Map<String, Object>> phy = (List<Map<String, Object>>)params.get("physiologyinfo");
					for (Map<String, Object> map : phy) {
						Record add_phy = new Record();
						add_phy.put("id", CommonFun.getITEMID());
						add_phy.put("patient_id", patientmap.get("id"));
						add_phy.put("visit_id", patientmap.get("visitid"));
						add_phy.put("auto_audit_id", autoaudit_id);
						add_phy.put("physiologyinfo_code", map.get("physiologyinfo_code"));
						add_phy.put("physiologyinfo_name", map.get("physiologyinfo_name"));
						add_phy_list.add(add_phy);
					}
					if (add_phy_list.size() > 0) {
						Util.executeBatchInsert("auto_audit_PhysiologyInfo", add_phy_list, this.getConnection());
					}
				}
				//处理数据
				thirdpartyDataDao.execDealData(rp);
			} catch (Exception e) {
				throw e;
			}finally {
				disconnect();
			}
			connect("autopa_thirdparty");
			//-------------
			for(Record r: scOrders.getResultset()){//拼接审查xml需要的对象
				Map<String, Object> order = new HashMap<String, Object>();
				order.put("medicinecode", r.get("drug_code_sys"));
				order.put("medicinename", r.get("drug_name_sys"));
				order.put("specification", r.get("shpgg_sys"));//规格：500mg:100ml(软袋)
				order.put("ordertype", r.get("repeat_indicator"));//是否长期医嘱：0 临时医嘱	1 长期医嘱。
				order.put("doseformname", r.get("administration_sys"));//药品用法，如口服，注射，静注
				order.put("frequencycode", r.get("frequency_sys"));//用药频次代码，qd等
				order.put("frequencyname", r.get("frequency_sys"));//用药频次名称，一天一次等
				order.put("unit", r.get("dw_sys"));//药品单位
				order.put("price", "");//单价
				order.put("count", r.get("shl"));//数量
				order.put("dosage", r.get("dosage"));
				order.put("dosagename", r.get("dosage_units"));
				order.put("groupno", r.get("group_id")+"|"+r.get("order_no"));
				order.put("beginusingtime", r.get("start_date_time"));
				order.put("endusingtime", r.get("stop_date_time"));
				order.put("medcinedepartment", "");//发药部门
				order.put("precodeitem", r.get("p_key"));
				order.put("s2", "");//预留字段
				order.put("s3", "");
				order.put("s4", "");
				order.put("s5", "");
				item.add(order);
			}
			
			ThirdpartyDataDao thdata = new ThirdpartyDataDao();
			Result diagnosis = thdata.queryDiagnosisForHLYY(rp);
			Result opsInfo = thdata.queryOpsInfoForHLYY(rp);
			Result allergyInfo = thdata.queryAllergyInfoForHLYY(rp);
			Result physiologyInfo = thdata.queryPhysiologyInfoForHLYY(rp);
			
			reMap.put("auto_audit_id", autoaudit_id);
			all_hyt.put("medicineinfoext", item);
			List<Record> res = new ArrayList<Record>();
			for(Record d : diagnosis.getResultset()){
				Record r = new Record();
				r.put("diagnosecode", d.get("diagnosis_code"));
				r.put("diagnosename", d.get("diagnosis_desc"));
				res.add(r);
			}
			all_hyt.put("diagnoseinfo", res);
			res = new ArrayList<Record>();
			for(Record d : opsInfo.getResultset()){
				Record r = new Record();
				r.put("opsid", d.get("operation_code"));
				r.put("opsname", d.get("operation"));
				r.put("qktype", d.get("wound_grade"));//跟慧药通确认，切口配对，传入切口code还是name
				r.put("begintime", d.get("start_date_time2"));
				r.put("endtime", d.get("end_date_time2"));
				res.add(r);
			}
			all_hyt.put("opsinfo", res);
			res = new ArrayList<Record>();
			//2019-04-25 王更改  没有过敏药物code所以不去拼接过敏信息,防止审查报错
			for(Record d : allergyInfo.getResultset()){
				Record r = new Record();
				r.put("Allergycode", d.get("sys_alergy_drugs_code"));
				r.put("Allergyname", d.get("sys_alergy_drugs"));
				if(!CommonFun.isNe(d.get("sys_alergy_drugs_code"))) {
					res.add(r);
				}
			}
			all_hyt.put("allergyinfo", res);
			res = new ArrayList<Record>();
			for(Record d : physiologyInfo.getResultset()){
				Record r = new Record();
				r.put("physiologycode", d.get("physiologyinfo_code"));
				r.put("physiologyname", d.get("physiologyinfo_name"));
				res.add(r);
			}
			all_hyt.put("physiologyinfo", res);
			reMap.put("request", all_hyt);
			all_hyt.put("PrescriptionInfoExt", dp);
			return reMap;
		} catch(Exception e){
			e.printStackTrace();
			throw e;
		} finally {
			disconnect();
		}
	}

	@Override
	public void callAudit(Map<String, Object> map, Object request) {
		long st = System.currentTimeMillis();
		String str = "";
		try {
			str = new EngineHLYY().HYTAudit((Map<String, Object>)request);
			if(!CommonFun.isNe(str)) {
				String is_succes = getResult(str, map);
			}
		}catch(Throwable ex1){
			ex1.printStackTrace();
		}
		long cost_time = System.currentTimeMillis() - st;
		try {
			Map<String, Object> insMap = new HashMap<String, Object>();
			insMap.put("auto_audit_id",  map.get("auto_audit_id"));
			insMap.put("cost_time", cost_time);
			insMap.put("thirdt_request", CommonFun.object2Json(request));
			insMap.put("thirdt_response", str);
	        new CheckDataParmsService().insert(insMap);
			//map.put("tt5", CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss:SSS"));
			long cost = System.currentTimeMillis()-st;
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("hyt_cost_time", cost);
			//如果审查超时，则需要执行标记isnew和计算结果，不然页面看不到审查问题
			if(cost >= maxtime*100) {
				params.put("is_deal", "1");
				params.put("auto_audit_id", map.get("auto_audit_id"));
			}
			params.put("id", map.get("auto_audit_id"));
			new AutoAuditService().dealResult(params);
			//new AutoAuditService().update(params);//已被dealResult取代
		} catch (Exception e) {
			e.printStackTrace();
			Client.sendHLYYError("admin_jq", new HashMap<String, Object>());
		}
	}

	@Override
	public String getResult(String xmlStr, Map<String, Object> map) throws Exception {
		try {
			connect("autopa_thirdparty");
			Object auto_audit_id = map.get("auto_audit_id");
	        Map doc = CommonFun.xml2Object(xmlStr, Map.class);
	        Map<String, Object> request =(Map<String, Object>) doc.get("result");
	        if(Integer.parseInt(String.valueOf(request.get("RetMessage"))) < 0){
	        	//Client.sendHLYYError("admin_jq", new HashMap<String, Object>());
	        	return "HL_F";
	        }
	        if(request.get("RetContent") instanceof List) {
	        	List<Map<String, Object>> items =(List<Map<String, Object>>) request.get("RetContent");
		        CheckResultDao crd = new CheckResultDao();
		        if(items != null && items.size() > 0){
		        	CheckResultInfoDao crid = new CheckResultInfoDao();
		        	Map<String, String> hassave = new HashMap<String, String>();
		        	for(Map<String, Object> item: items){
		        		String rid;
		        		if(CommonFun.isNe(hassave.get(item.get("Type")))){
		        			Map<String, Object> resultMap = new HashMap<String, Object>();
			        		resultMap.put("auto_audit_id", auto_audit_id);
			        		resultMap.put("keyword", item.get("Type"));
			        		resultMap.put("keyTitle", item.get("keyTitle"));
			        		resultMap.put("type", "1");
			        		rid = crd.insert(resultMap);
			        		hassave.put(String.valueOf(item.get("Type")), rid);
		        		}else{
		        			rid = hassave.get(item.get("Type"));
		        		}
		        		Map<String, Object> InfoMap = new HashMap<String, Object>();
	        			InfoMap.put("auto_audit_id", auto_audit_id);
	        			InfoMap.put("parent_id", rid);
	        			InfoMap.put("level", item.get("Severity"));
	        			InfoMap.put("warning", item.get("Holdup"));
	        			InfoMap.put("reference", item.get("Reference"));
	        			InfoMap.put("description", item.get("Message"));
	        			String order_ids = String.valueOf(item.get("Precodeitem1"));
	        			if(!CommonFun.isNe(item.get("Precodeitem2"))){
	        				order_ids = order_ids+","+item.get("Precodeitem2");
	        			}
	        			
	        			InfoMap.put("order_id", order_ids.replace("~", ","));
	        			crid.insert(InfoMap);
		        	}
		        }
	        }else {
	        	Map<String, Object> item = (Map<String, Object>)request.get("RetContent");
	        	if(!CommonFun.isNe(item)) {
	        		CheckResultDao crd = new CheckResultDao();
		        	CheckResultInfoDao crid = new CheckResultInfoDao();
	    			Map<String, Object> resultMap = new HashMap<String, Object>();
	    			String rid;
	        		resultMap.put("auto_audit_id", auto_audit_id);
	        		resultMap.put("keyword", item.get("Type"));
	        		resultMap.put("keyTitle", item.get("keyTitle"));
	        		resultMap.put("type", "1");
	        		rid = crd.insert(resultMap);
	        		Map<String, Object> InfoMap = new HashMap<String, Object>();
	    			InfoMap.put("auto_audit_id", auto_audit_id);
	    			InfoMap.put("parent_id", rid);
	    			InfoMap.put("level", item.get("Severity"));
	    			InfoMap.put("warning", item.get("Holdup"));
	    			InfoMap.put("reference", item.get("Reference"));
	    			InfoMap.put("description", item.get("Message"));
	    			String order_ids = String.valueOf(item.get("Precodeitem1"));
	    			if(!CommonFun.isNe(item.get("Precodeitem2"))){
	    				order_ids = order_ids+","+item.get("Precodeitem2");
	    			}
	    			InfoMap.put("order_id", order_ids.replace("~", ","));
	    			crid.insert(InfoMap);
	        	}
	        }
	        
	        //saveResult(idmap);
	        return "Y"; 
		}catch(Exception exc) {
			exc.printStackTrace();
			throw exc;
		} finally {
			disconnect();
		}
	}

	@Override
	public void getDrugINS() {
		// TODO Auto-generated method stub
		
	}

	public Map<String, Object> getResponse_HYT(Map<String, Object> dp, Object orders, Object diagnoseinfos, Object opsinfos, Object allergyinfos, Object physiologyinfos) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("PrescriptionInfoExt", dp);
		
		map.put("MedicineInfoExtS", orders);
		
		map.put("DiagnoseInfoS", diagnoseinfos);
		
		map.put("OpsInfoS", opsinfos);
		
		map.put("AllergyInfoS", allergyinfos);
		
		map.put("PhysiologyInfoS", physiologyinfos);
		
		return map;
	}
	
	@Override
	public String pr_callAudit(Object request) {
		String str = "";
		try {
			str = new EngineHLYY().HYTAudit((Map<String, Object>)request);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return str;
	}

	@Override
	public Map<String, Object> get_audit_def_data(String auto_audit_id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("auto_audit_id", auto_audit_id);
		try {
			connect("autopa_thirdparty");
			ThirdpartyDataDao thirdpartyDataDao = new ThirdpartyDataDao();
			map.put("orders", thirdpartyDataDao.get_audit_def_oredrs(param).getResultset());
			Record patient = thirdpartyDataDao.get_audit_def_patient(param);
			map.put("basicinfo", patient);
			map.put("auto_audit_id", auto_audit_id);
		} catch (Exception ex){
			throw ex;
		}finally{
			disconnect();
		}
		return map;
	}
}
