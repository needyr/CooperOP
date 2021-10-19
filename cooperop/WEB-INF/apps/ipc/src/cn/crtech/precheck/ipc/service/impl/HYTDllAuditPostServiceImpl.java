package cn.crtech.precheck.ipc.service.impl;

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
import cn.crtech.cooperop.bus.util.GlobalVar;
import cn.crtech.cooperop.bus.util.HttpClient;
import cn.crtech.cooperop.ipc.dao.AutoAuditDao;
import cn.crtech.cooperop.ipc.dao.AutoAuditOrdersDao;
import cn.crtech.cooperop.ipc.dao.AutoAuditPatienDao;
import cn.crtech.cooperop.ipc.dao.CheckDataParmsDao;
import cn.crtech.cooperop.ipc.dao.CheckResultDao;
import cn.crtech.cooperop.ipc.dao.CheckResultInfoDao;
import cn.crtech.cooperop.ipc.service.AutoAuditPatientService;
import cn.crtech.cooperop.ipc.service.AutoAuditService;
import cn.crtech.cooperop.ipc.service.CheckDataParmsService;
import cn.crtech.precheck.ipc.dao.DataDao;
import cn.crtech.precheck.ipc.service.AuditService;
import cn.crtech.precheck.ipc.service.EngineHLYY;
import cn.crtech.precheck.ipc.service.HttpUtil;
import cn.crtech.precheck.ipc.ws.client.Client;

public abstract class HYTDllAuditPostServiceImpl extends BaseService implements AuditService{
	
	/** 合理用药审查最大等到时间 */
	private static int maxtime = 10 * Integer.parseInt(SystemConfig.getSystemConfigValue("ipc", "time_waitsckeck", "6")) + 1; 
	public static final String HYT_ADDRESS = SystemConfig.getSystemConfigValue("ipc", "hyt_dll_audit_address");
	public static final String HYT_JGDM = SystemConfig.getSystemConfigValue("ipc", "hyt_jgdm");
	public static final String HYT_TIMEOUT = SystemConfig.getSystemConfigValue("ipc", "hyt_timeout");
	public static final String HYT_POST_ADDRESS = SystemConfig.getSystemConfigValue("ipc", "hyt_post_address");
	
	@Override
	public Map<String, Object> joinParams(Map<String, Object> params, String common_id) throws Exception {
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
			reMap.put("doctor_no", doctormap.get("code"));
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
			dp.put("patienttype", patientmap.get("chargetype"));//患者类别，如自费，医保，绿色通道等
			dp.put("birthday", patientmap.get("birthday"));
			dp.put("gender", patientmap.get("sex"));
			dp.put("height", CommonFun.isNe(patientmap.get("height"))?0:Integer.parseInt(String.valueOf(patientmap.get("height"))));
			dp.put("weight", CommonFun.isNe(patientmap.get("weight"))?0:Double.parseDouble(String.valueOf(patientmap.get("weight"))));
			dp.put("opsid", "");//空
			dp.put("opsname", "");//空
			dp.put("qktype", "");//切口类型
			dp.put("wardcode", "");//病区代码
			dp.put("bednum", patientmap.get("bedno"));
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
			
			//插入his传输的患者信息进行插入auto_audit_patient表中  2019-07-23 wrh
			/*Map<String, Object> patient_info = new HashMap<String, Object>();
			patient_info.put("patient_id", patientmap.get("id"));
			patient_info.put("visit_id", patientmap.get("visitid"));
			patient_info.put("patient_name", patientmap.get("name"));
			patient_info.put("sex", patientmap.get("sex"));
			patient_info.put("dept_code", patientmap.get("departcode"));
			patient_info.put("dept_name", patientmap.get("departname"));
			patient_info.put("admissiondate", patientmap.get("admissiondate"));
			patient_info.put("idcard_no", patientmap.get("idcardno"));
			patient_info.put("bed_no", patientmap.get("bedno"));
			patient_info.put("birthday", patientmap.get("birthday"));
			patient_info.put("weight", patientmap.get("weight"));*/
			
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
			//插入his传输的患者信息进行插入auto_audit_patient表中  2019-07-23 wrh
			/*patient_info.put("auto_audit_id", autoaudit_id);
			new AutoAuditPatienDao().insert(patient_info);*/
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
			
			
			Result diagnosis = dd.queryDiagnosisForHLYY(p);
			Result opsInfo = dd.queryOpsInfoForHLYY(p);
			Result allergyInfo = dd.queryAllergyInfoForHLYY(p);
			Result physiologyInfo = dd.queryPhysiologyInfoForHLYY(p);
			
			reMap.put("auto_audit_id", autoaudit_id);
			//orders.put("medicineinfoext", item);
			all_hyt.put("medicineinfoext", item);
			List<Record> res = new ArrayList<Record>();
			//Map<String, Object> diagnosisMap = new HashMap<String, Object>();
			for(Record d : diagnosis.getResultset()){
				Record r = new Record();
				r.put("diagnosecode", d.get("sys_diagnosis_code"));
				r.put("diagnosename", d.get("sys_diagnosis_name"));
				if(!CommonFun.isNe(d.get("sys_diagnosis_code"))) {
					res.add(r);
				}
			}
			//diagnosisMap.put("diagnoseinfo", res);
			all_hyt.put("diagnoseinfo", res);
			//Map<String, Object> opsInfoMap = new HashMap<String, Object>();
			res = new ArrayList<Record>();
			for(Record d : opsInfo.getResultset()){
				Record r = new Record();
				r.put("opsid", d.get("sys_operation_code"));
				r.put("opsname", d.get("sys_operation_name"));
				r.put("qktype", d.get("sys_woundgrade_code"));//跟慧药通确认，切口配对，传入切口code还是name
				r.put("begintime", d.get("start_date_time"));
				r.put("endtime", d.get("end_date_time"));
				res.add(r);
			}
			//opsInfoMap.put("opsinfo", res);
			all_hyt.put("opsinfo", res);
			//Map<String, Object> allergyInfoMap = new HashMap<String, Object>();
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
			//allergyInfoMap.put("allergyinfo", res);
			all_hyt.put("allergyinfo", res);
			//Map<String, Object> physiologyInfoMap = new HashMap<String, Object>();
			res = new ArrayList<Record>();
			for(Record d : physiologyInfo.getResultset()){
				Record r = new Record();
				r.put("physiologycode", d.get("sys_physiologyinfo_code"));
				r.put("physiologyname", d.get("sys_physiologyinfo_name"));
				res.add(r);
			}
			//physiologyInfoMap.put("physiologyinfo", res);
			all_hyt.put("physiologyinfo", res);
			all_hyt.put("PrescriptionInfoExt", dp);
			/*reMap.put("request", getResponse_HYT(dp, orders
					,diagnosisMap, opsInfoMap, allergyInfoMap, physiologyInfoMap));*/
			reMap.put("request", all_hyt);
			//Map<String,Object> maphyt=new HashMap<String,Object>();
			//maphyt.put("id", autoaudit_id);
			/* maphyt.put("request_hlyy", reMap.get("request")); */
			//maphyt.put("thirdt_request", reMap.get("request"));
			/* new AutoAuditDao().update(maphyt); */
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
		try {
				//String str = new EngineHLYY().HYTAudit((Map<String, Object>)request);
			    Map<String, Object> ttt = (Map<String, Object>) request;
			    ttt.put("hyt_address", HYT_ADDRESS);
			    ttt.put("hyt_jgdm", HYT_JGDM);
			    ttt.put("hyt_timeout", HYT_TIMEOUT);
				//String str = HttpClient.post("http://127.0.0.1:8085/HYTDllAudit/AuditDllServlet",ttt);
			    String str = "";
			   /* Map<String, Object> result_map = new HashMap<String, Object>();
		    	HYTPostDllAudit hytPostDllAudit = new HYTPostDllAudit(CommonFun.object2Json(ttt),result_map);
		    	hytPostDllAudit.start();
			    for (int i = 0; i < 61; i++) {
			    	if (result_map.get("re") != null) {
			    		str = (String)result_map.get("re");
						break;
					}
					Thread.sleep(100);
				}*/
			    try {
			    	str = HttpUtil.sendPost(HYT_POST_ADDRESS, CommonFun.object2Json(ttt),2000,10000);
			    	String is_succes = getResult(str, map);
				} catch (Exception e) {
					log.error("POST访问HYT审查报错!!");
				}
			    long cost_time = System.currentTimeMillis() - st;
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
		} catch (Throwable e) {
			e.printStackTrace();
			Client.sendHLYYError("admin_jq", new HashMap<String, Object>());
		}
	}

	@Override
	public String getResult(String xmlStr, Map<String, Object> map) throws Exception {
		try {
			connect("ipc");
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
	
	public static class HYTPostDllAudit extends Thread{
		public static int MAX_THREAD_NUM = 0;
		String xml = "";
		Map<String, Object> map = new HashMap<String, Object>();
		public HYTPostDllAudit(String xml, Map<String, Object> map){
			this.map = map;
			this.xml = xml;
		}
		@Override
		public void run() {
			try {
				boolean b = true;
				synchronized (HYTPostDllAudit.class) {
					if(MAX_THREAD_NUM>50) {
						b = false;
					}
					MAX_THREAD_NUM++;
					setName("HYTPostDllAudit_Thread_"+MAX_THREAD_NUM);
				}
				if (b) {
					map.put("re", HttpUtil.sendPost(HYT_POST_ADDRESS, xml,2000,10000));
				}
			} catch (Exception e) {
				log.error("POST访问HYT审查报错!!");
			}finally {
				synchronized (HYTPostDllAudit.class) {
					MAX_THREAD_NUM--;
				}
			}
		}
	}

	@Override
	public String pr_callAudit(Object request) {
		return null;
	}
}
