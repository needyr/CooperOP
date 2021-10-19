package cn.crtech.precheck.ipc.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

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
import cn.crtech.cooperop.ipc.service.AutoAuditService;
import cn.crtech.cooperop.ipc.service.CheckDataParmsService;
import cn.crtech.precheck.ipc.dao.DataDao;
import cn.crtech.precheck.ipc.huiyaotong.EngineHYT;
import cn.crtech.precheck.ipc.service.AuditService;
import cn.crtech.precheck.ipc.ws.client.Client;

public class HYTWSAuditServiceImpl extends BaseService  implements AuditService {
	
	/** 合理用药审查最大等到时间 */
	private static int maxtime = 10 * Integer.parseInt(SystemConfig.getSystemConfigValue("ipc", "time_waitsckeck", "6")) + 1; 

	@Override
	public Map<String, Object> joinParams(Map<String, Object> params, String common_id) throws Exception {
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
			start();//将当前新开的药品插入审查表 事务
			DataDao dd = new DataDao();
			try {
				AutoAuditOrdersDao aad = new AutoAuditOrdersDao();
				for(Map<String, Object> m : rows){
					m.put("auto_audit_id", autoaudit_id);
					// -- 处理 万宁保留位数达到28位的情况 【万宁已处理，可删除】
					/*if(!CommonFun.isNe(params.get("amount"))) {
						m.put("amount",((String)m.get("amount")).substring(0, 10));
					}*/
					// -- end
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
					aad.insert(m);
				}
				if(!"1".equals(params.get("is_after"))){//事前审查才需要联合在用药品进行审查
					Result zyOrders = dd.queryUseOrders(p);//病人在用药
					for(Record r: zyOrders.getResultset()){//将历史的在用药插入到审查明细表中，方便后续查询
						r.put("auto_audit_id", autoaudit_id);
						r.remove("rowno");
						r.remove("shengccj");
						aad.insert(r);
					}
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
			reMap.put("request", getResponse_HYT(dp, orders
					,diagnosisMap, opsInfoMap, allergyInfoMap, physiologyInfoMap));
			//Map<String,Object> maphyt=new HashMap<String,Object>();
			//maphyt.put("id", autoaudit_id);
			/* maphyt.put("request_hlyy", reMap.get("request")); */
			//maphyt.put("thirdt_request", reMap.get("request"));
			/* new AutoAuditDao().update(maphyt); */
			CheckDataParmsDao cdp = new CheckDataParmsDao();
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
		// TODO Auto-generated method stub

		//map.put("tt4", CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss:SSS"));
		long st = System.currentTimeMillis();
		try {
				String str = (String) new EngineHYT().invokeMethod((String)request);
				long cost_time = System.currentTimeMillis() - st;
				String is_succes = getResult(str, map);
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

	public String getResponse_HYT(Map<String, Object> dp, Object orders, Object diagnoseinfos, Object opsinfos, Object allergyinfos, Object physiologyinfos) {
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
	
	/** 审查结果解析 */
	public String getResult(String xmlStr, Map<String, Object> map) throws Exception {
		try {
			connect("ipc");
			Object auto_audit_id = map.get("auto_audit_id");
			Document doc = DocumentHelper.parseText(xmlStr);
	        Element root = doc.getRootElement();
	        if(Integer.parseInt(root.elementText("RetMessage")) < 0){
	        	//Client.sendHLYYError("admin_jq", new HashMap<String, Object>());
	        	return "HL_F";
	        }
	        List<Element> items = root.element("RetContent").elements("RetContent");
	        CheckResultDao crd = new CheckResultDao();
	        if(items.size() > 0){
	        	CheckResultInfoDao crid = new CheckResultInfoDao();
	        	Map<String, String> hassave = new HashMap<String, String>();
	        	for(Element item: items){
	        		String rid;
	        		if(CommonFun.isNe(hassave.get(item.elementText("Type")))){
	        			Map<String, Object> resultMap = new HashMap<String, Object>();
		        		resultMap.put("auto_audit_id", auto_audit_id);
		        		resultMap.put("keyword", item.elementText("Type"));
		        		resultMap.put("keyTitle", item.elementText("keyTitle"));
		        		resultMap.put("type", "1");
		        		rid = crd.insert(resultMap);
		        		hassave.put(item.elementText("Type"), rid);
	        		}else{
	        			rid = hassave.get(item.elementText("Type"));
	        		}
	        		Map<String, Object> InfoMap = new HashMap<String, Object>();
        			InfoMap.put("auto_audit_id", auto_audit_id);
        			InfoMap.put("parent_id", rid);
        			InfoMap.put("level", item.elementText("Severity"));
        			InfoMap.put("warning", item.elementText("Holdup"));
        			InfoMap.put("reference", item.elementText("Reference"));
        			InfoMap.put("description", item.elementText("Message"));
        			String order_ids = item.elementText("Precodeitem1");
        			if(!CommonFun.isNe(item.elementText("Precodeitem2"))){
        				order_ids = order_ids+","+item.elementText("Precodeitem2");
        			}
        			InfoMap.put("order_id", order_ids.replace("~", ","));
        			crid.insert(InfoMap);
	        	}
	        }
	        return "Y"; 
		}catch(Exception exc) {
			exc.printStackTrace();
			throw exc;
		} finally {
			disconnect();
		}
	}

	@Override
	public String pr_callAudit(Object request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void getDrugINS() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, Object> get_audit_def_data(String auto_audit_id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
