package cn.crtech.cooperop.ipc.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.dao.CommentManageDao;
import cn.crtech.cooperop.hospital_common.service.dict.SysSpcommentService;
import cn.crtech.cooperop.ipc.dao.SampleDao;
import cn.crtech.cooperop.ipc.dao.SystemUserDao;
import cn.crtech.cooperop.ipc.dao.sample.CommentSampleDao;
import cn.crtech.cooperop.ipc.dao.sample.CommentSamplePatientsDao;
import cn.crtech.cooperop.ipc.dao.sample.OrdersResultsDao;

public class SampleService extends BaseService{

	public Result query(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			Map<String, Object> map = new HashMap<String, Object>();
			Map<String, Object> sample = new HashMap<String, Object>();
			sample.put("sample_id", params.remove("sample_id"));
			if (!CommonFun.isNe(params.get("p_typeFifter"))) {
				String pd = (String) params.remove("p_typeFifter");
				if ("1".equals(pd)) {
					params.put("p_typeFifter", "2");
				}else if ("2".equals(pd)) {
					params.put("patient_state", "1");
					params.put("p_typeFifter", "1");
				}else if ("3".equals(pd)) {
					params.put("patient_state", "0");
					params.put("p_typeFifter", "1");
				}
			}
			params.put("is_getpatient",1);
			Result result = new SampleDao().query(params);
			if (result.getCount() != 0) {
				result.setCount(Long.parseLong((String)result.getResultset(0).get("count_num")));
				if (!CommonFun.isNe(sample.get("sample_id"))) {
					List<Record> resultset = new CommentSamplePatientsDao().queryBySampleIdGroup(sample).getResultset();
					for (Record record : resultset) {
						map.put(record.getString("patient_id")+"-"+record.getString("visit_id"), "is_has");
					}
					for (int i=0;i<result.getResultset().size();i++) {
						String eq = result.getResultset(i).getString("patient_id")+"-"+result.getResultset(i).getString("visit_id");
						if (!CommonFun.isNe(map.get(eq))) {
							result.getResultset(i).put("is_yichou", "1");
						}
					}
				}
			}
			return result;
		} catch (Exception ex){
			throw ex;
		}finally{
			disconnect();
		}
	}
	
	public Map<String, Object> queryAll(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			Record r = new Record();
			Map<String, Object> map = new HashMap<String, Object>();
			Map<String, Object> bj = new HashMap<String, Object>();
			String check = "";
			List<Record> patients = new SampleDao().queryComment(params).getResultset();
			String sample_id = (String)params.get("sample_id");
			for (Record patient : patients) {
				bj.put(patient.get("patient_id")+"-"+patient.get("visit_id")+"-"+sample_id, "1");
			}
			r.put("id", sample_id);
			Record record = new CommentSampleDao().get(r);
			params.put("deptFifter", record.get("dept_code"));
			params.put("p_typeFifter", record.get("p_type"));
			params.put("datasouce", record.get("d_type"));
			params.put("doctorFifter", record.get("doctor"));
			params.put("feibieFifter", record.get("feibie"));
			params.put("patient_state", record.get("patient_state"));
			params.put("drugfifter", record.get("drug_code"));
			params.put("is_getpatient",1);
			List<Record> resultset = new SampleDao().queryAll(params).getResultset();
			for (Record set : resultset) {
				check = set.get("patient_id")+"-"+set.get("visit_id")+"-"+sample_id;
				if (!bj.containsKey(check)) {
					map.putAll(set);
				}
			}
			return map;
		} catch (Exception ex){
			throw ex;
		}finally{
			disconnect();
		}
	}
	
	public Result queryComment(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			return new SampleDao().queryComment(params);
		} catch (Exception ex){
			throw ex;
		}finally{
			disconnect();
		}
	}

	public Result querydetail(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			return new SampleDao().querydetail(params);
		} catch (Exception ex){
			throw ex;
		}finally{
			disconnect();
		}
	}
	public Map<String, Object> saveSample(Map<String, Object> params) throws Exception {
		try {
			long start = System.currentTimeMillis();
			Map<String, Object> result=new HashMap<String, Object>();
			Map<String, Object> sample=new HashMap<String, Object>();
			if (!CommonFun.isNe(params.get("p_typeFifter"))) {
				String pd = (String) params.remove("p_typeFifter");
				if ("1".equals(pd)) {
					params.put("p_typeFifter", "2");
				}else if ("2".equals(pd)) {
					params.put("patient_state", "1");
					params.put("p_typeFifter", "1");
				}else if ("3".equals(pd)) {
					params.put("patient_state", "0");
					params.put("p_typeFifter", "1");
				}
			}
			String select = (String) params.remove("select");
			List<Map<String, Object>> samplePatients = new LinkedList<Map<String, Object>>();
			Map<String, Object> patient_visit=new HashMap<String, Object>();
			String pv = "";
			Map<String, Object> patient_all = new HashMap<String, Object>();
			params.put("mintime", CommonFun.formatDateStr((String)params.get("mintime"), "yyyy-MM-dd HH:mm:ss"));
			params.put("maxtime", CommonFun.formatDateStr(params.get("maxtime").toString().substring(0, 10)+" 23:59:59", "yyyy-MM-dd HH:mm:ss"));
			
			sample.put("dept_name",params.get("dept_name"));
			sample.put("create_user",params.get("create_user"));
			sample.put("create_time", CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
			sample.put("d_type",params.get("datasouce"));
			sample.put("sample_start_time",params.get("mintime"));
			sample.put("sample_end_time",params.get("maxtime"));
			sample.put("sample_type","1");
			sample.put("sample_num", 0);
			sample.put("state", "0");
			sample.put("comment_flag", "0");
			sample.put("sample_patient_idorname", params.get("patient"));
			sample.put("p_type",params.get("p_typeFifter"));
			sample.put("feibie",params.get("feibie_name"));
			sample.put("doctor",params.get("doctorfifter"));
			sample.put("doctor_name",params.get("doctor_name"));
			sample.put("dept_code",params.get("deptfifter"));
			sample.put("diagnosis_desc",params.get("diag"));
			sample.put("comment_way",params.get("comment_way"));
			sample.put("spcomment_unit",params.get("spcomment_unit"));
			sample.put("drug_type",params.get("drug_type"));
			if ("1".equals(params.get("datasouce"))) {
				sample.put("patient_state",params.get("patient_state"));
			}else{
				sample.put("patient_state",2);
			}
			sample.put("patient_num_sample", params.get("patient_num_sample"));
			sample.put("num_type", params.get("num_type"));
			sample.put("drug_code", params.get("drugfifter"));
			sample.put("drug_name", params.get("drug_name"));
			//拼接主题+++++++++++++++++++++++++
			StringBuffer motif = new StringBuffer();
			String datasouce = (String)sample.get("d_type");
			String sample_type = (String)sample.get("sample_type");
			motif.append(" 【 常规点评 】 ");
			switch (datasouce) {
			case "1":
				motif.append("住院 ");
				break;
			case "2":
				motif.append("急诊 ");			
				break;
			case "3":
				motif.append("门诊 ");
				break;
			case "-1":
				motif.append("门急诊 ");
				break;
			default:
				break;
			}
			motif.append(sample.get("sample_start_time")+"至"+sample.get("sample_end_time"));
			switch (sample_type) {
			case "1":
				motif.append(" 手动抽样");
				break;
			case "2":
				motif.append(" 随机抽样");
				break;
			default:
				break;
			}
			sample.put("motif", motif.toString());
			//-----------------------------
			connect("ipc");
			SampleDao sd = new SampleDao();
			CommentSampleDao csd = new CommentSampleDao();
			int sampleId = csd.insert(sample);
			sample.put("id", sampleId);
			int sample_num = 0;
			Map<String, Object> orderMap = new HashMap<String, Object>();
			Map<String, Object> patientMap = new HashMap<String, Object>();
			Map<String, Object> groupMap = new HashMap<String, Object>();
			
			StringBuffer row_ = new StringBuffer();
			
			CommentSamplePatientsDao cspd = new CommentSamplePatientsDao();
			OrdersResultsDao ord = new OrdersResultsDao();
			
			/*//去除多选的单引号
			params.put("feibiefifter", ((String)params.remove("feibie_name")).replace("'", ""));
			params.put("doctorfifter", ((String)params.remove("doctorfifter")).replace("'", ""));
			params.put("deptfifter", ((String)params.remove("deptfifter")).replace("'", ""));
			params.put("drugfifter", ((String)params.remove("drugfifter")).replace("'", ""));*/
			params.put("is_getpatient",0);
			
			//选取的患者
			List<Map<String, Object>> samplePatientsAll = (List<Map<String, Object>>) CommonFun.json2Object(select, List.class);
			row_.append("<rows>");
			for (Map<String, Object> map : samplePatientsAll) {
				patient_all = (Map<String, Object>) map.get("data");
				String patient_id = (String)patient_all.get("patient_id");
				String visit_id = (String)patient_all.get("visit_id");
				//------------添加patient_id
				row_.append("<row>");
				row_.append("<PATIENT_ID>");
				row_.append(patient_id);
				row_.append("</PATIENT_ID>");
				row_.append("<VISIT_ID>");
				row_.append(visit_id);
				row_.append("</VISIT_ID>");
				row_.append("</row>");
				//---------------------------------
				
				pv = patient_id+"-"+visit_id;
				if (CommonFun.isNe(patient_visit.get(pv))) {
					samplePatients.add(map);
					patient_visit.put(patient_id+"-"+visit_id, "1");
				}
			}
			row_.append("</rows>");
			params.put("patients_visits_xml", row_.toString());
			List<Record> resultset = sd.queryOrdersByPatient(params).getResultset();
			for (Map<String, Object> map : samplePatients) {
				Map<String, Object> map1 = (Map<String, Object>) map.get("data");
				Object patient_id = map1.get("patient_id");
				Object visit_id = map1.get("visit_id");
				//map1.putAll(params);
				patientMap.put("sample_id", sampleId);
				patientMap.put("patient_id", patient_id);
				patientMap.put("visit_id", visit_id);
				patientMap.put("state", 0);
				patientMap.put("is_active", 1);
				int sample_patients_id = cspd.insert(patientMap);
				sample_num++;
				for (int i = 0; i < resultset.size(); i++) {
					String pString = resultset.get(i).get("patient_id")+"-"+resultset.get(i).get("visit_id");
					String pvString = patient_id+"-"+visit_id;
					if (pvString.equals(pString)) {
						if (CommonFun.isNe(groupMap.get(resultset.get(i).get("group_id")+"-"+resultset.get(i).get("order_no")))) {
							orderMap.put("group_id", resultset.get(i).get("group_id"));
							orderMap.put("order_no", resultset.get(i).get("order_no"));
							orderMap.put("sample_id", sampleId);
							orderMap.put("sample_patients_id", sample_patients_id);
							orderMap.put("patient_id", resultset.get(i).get("patient_id"));
							orderMap.put("is_active", "1");
							orderMap.put("doctor_no", resultset.get(i).get("doctor_no"));
							orderMap.put("visit_id", resultset.get(i).get("visit_id"));
							groupMap.put(resultset.get(i).get("group_id")+"-"+resultset.get(i).get("order_no"), 1);
							ord.insert(orderMap);
							resultset.remove(i);
						}
					}
				}
				/*for (Map<String, Object> orders : resultset) {
					String pString = orders.get("patient_id")+"-"+orders.get("visit_id");
					String pvString = patient_id+"-"+visit_id;
					if (pvString.equals(pString)) {
						orderMap.put("group_id", orders.get("group_id"));
						orderMap.put("order_no", orders.get("order_no"));
						orderMap.put("sample_id", sampleId);
						orderMap.put("sample_patients_id", sample_patients_id);
						orderMap.put("patient_id", orders.get("patient_id"));
						orderMap.put("is_active", "1");
						orderMap.put("doctor_no", orders.get("doctor_no"));
						orderMap.put("visit_id", orders.get("visit_id"));
						groupMap.put(orders.get("group_id")+"-"+orders.get("order_no"), 1);
						ord.insert(orderMap);
					}
				}*/
			}
			result.put("sample_num", sample_num);
			result.put("sample_Id", sampleId);
			sample.put("sample_num", sample_num);
			sample.put("cost_time", System.currentTimeMillis() - start);
			csd.update(sample);
			//log.debug("抽样保存执行时间："+(System.currentTimeMillis()-start));
			return result;
		} catch (Exception ex){
			throw ex;
		}finally{
			disconnect();
		}
	}
	
	//手动抽样过后的添加抽样
	public Map<String, Object> saveSamplebyedit(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			Map<String, Object> result=new HashMap<String, Object>();
			Map<String, Object> sample=new HashMap<String, Object>();
			if (!CommonFun.isNe(params.get("p_typeFifter"))) {
				String pd = (String) params.remove("p_typeFifter");
				if ("1".equals(pd)) {
					params.put("p_typeFifter", "2");
				}else if ("2".equals(pd)) {
					params.put("patient_state", "1");
					params.put("p_typeFifter", "1");
				}else if ("3".equals(pd)) {
					params.put("patient_state", "0");
					params.put("p_typeFifter", "1");
				}
			}
			String select = (String) params.remove("select");
			SampleDao sd = new SampleDao();
			StringBuffer row_ = new StringBuffer();
			//选取的患者
			List<Map<String, Object>> samplePatientsAll = (List<Map<String, Object>>) CommonFun.json2Object(select, List.class);
			List<Map<String, Object>> samplePatients = new LinkedList<Map<String, Object>>();
			Map<String, Object> patient_visit=new HashMap<String, Object>();
			String pv = "";
			Map<String, Object> patient_all = new HashMap<String, Object>();
			row_.append("<rows>");
			for (Map<String, Object> map : samplePatientsAll) {
				patient_all = (Map<String, Object>) map.get("data");
				String patient_id = (String)patient_all.get("patient_id");
				String visit_id = (String)patient_all.get("visit_id");
				//------------添加patient_id
				row_.append("<row>");
				row_.append("<PATIENT_ID>");
				row_.append(patient_id);
				row_.append("</PATIENT_ID>");
				row_.append("<VISIT_ID>");
				row_.append(visit_id);
				row_.append("</VISIT_ID>");
				row_.append("</row>");
				//---------------------------------
				
				pv = patient_id+"-"+visit_id;
				if (CommonFun.isNe(patient_visit.get(pv))) {
					samplePatients.add(map);
					patient_visit.put(patient_id+"-"+visit_id, "1");
				}
			}
			row_.append("</rows>");
			params.put("mintime", CommonFun.formatDateStr((String)params.get("mintime"), "yyyy-MM-dd HH:mm:ss"));
			params.put("maxtime", CommonFun.formatDateStr(params.get("maxtime").toString().substring(0, 10)+" 23:59:59", "yyyy-MM-dd HH:mm:ss"));
			
			String sampleId =(String) params.get("sample_id");
			sample.put("id", sampleId);
			int sample_num = 0;
			Map<String, Object> orderMap = new HashMap<String, Object>();
			Map<String, Object> patientMap = new HashMap<String, Object>();
			Map<String, Object> groupMap = new HashMap<String, Object>();
			Map<String, Object> is_add = new HashMap<String, Object>();
			CommentSamplePatientsDao cspd = new CommentSamplePatientsDao();
			OrdersResultsDao ord = new OrdersResultsDao();
			
			params.put("is_getpatient",0);
			params.put("patients_visits_xml", row_.toString());
			List<Record> resultset = sd.queryOrdersByPatient(params).getResultset();
			List<Record> list_patient = cspd.queryBySampleId(params).getResultset();
			for (Record record : list_patient) {
				String patient_id = (String)record.get("patient_id");
				String visit_id = (String)record.get("visit_id");
				is_add.put(patient_id+"-"+visit_id, "1");
			}
			for (Map<String, Object> map : samplePatients) {
				Map<String, Object> map1 = (Map<String, Object>) map.get("data");
				Object patient_id = map1.get("patient_id");
				Object visit_id = map1.get("visit_id");
				if (CommonFun.isNe(is_add.get(patient_id+"-"+visit_id))) {
					//map1.putAll(params);
					is_add.put(patient_id+"-"+visit_id, 1);
					patientMap.put("sample_id", sampleId);
					patientMap.put("patient_id", patient_id);
					patientMap.put("visit_id", visit_id);
					patientMap.put("state", 0);
					patientMap.put("is_active", 1);
					int sample_patients_id = cspd.insert(patientMap);
					sample_num++;
					for (int i = 0; i < resultset.size(); i++) {
						String pString = resultset.get(i).get("patient_id")+"-"+resultset.get(i).get("visit_id");
						String pvString = patient_id+"-"+visit_id;
						if (pvString.equals(pString)) {
							if (CommonFun.isNe(groupMap.get(resultset.get(i).get("group_id")+"-"+resultset.get(i).get("order_no")))) {
								orderMap.put("group_id", resultset.get(i).get("group_id"));
								orderMap.put("order_no", resultset.get(i).get("order_no"));
								orderMap.put("sample_id", sampleId);
								orderMap.put("sample_patients_id", sample_patients_id);
								orderMap.put("patient_id", resultset.get(i).get("patient_id"));
								orderMap.put("is_active", "1");
								orderMap.put("doctor_no", resultset.get(i).get("doctor_no"));
								orderMap.put("visit_id", resultset.get(i).get("visit_id"));
								groupMap.put(resultset.get(i).get("group_id")+"-"+resultset.get(i).get("order_no"), 1);
								ord.insert(orderMap);
								resultset.remove(i);
							}
						}
					}
				}
			}
			result.put("sample_num", sample_num);
			result.put("sample_Id", sampleId);
			CommentSampleDao csd = new CommentSampleDao();
			int real_sample_num = Integer.parseInt((String) csd.get(sample).get("sample_num"));
			sample.put("sample_num", sample_num+real_sample_num);
			csd.update(sample);
			return result;
		} catch (Exception ex){
			throw ex;
		}finally{
			disconnect();
		}
	}
	
	
	//随机抽样
	public Map<String, Object> insertSample(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			Map<String, Object> result=new HashMap<String, Object>();
			Map<String, Object> sample=new HashMap<String, Object>();
			if (!CommonFun.isNe(params.get("p_typeFifter"))) {
				String pd = (String) params.remove("p_typeFifter");
				if ("1".equals(pd)) {
					params.put("p_typeFifter", "2");
				}else if ("2".equals(pd)) {
					params.put("patient_state", "1");
					params.put("p_typeFifter", "1");
				}else if ("3".equals(pd)) {
					params.put("patient_state", "0");
					params.put("p_typeFifter", "1");
				}
			}
			//生成GZID
			String gzid = CommonFun.getITEMID();
			sample.put("GZID", gzid);
			
			params.put("mintime", CommonFun.formatDateStr((String)params.get("mintime"), "yyyy-MM-dd HH:mm:ss"));
			params.put("maxtime", CommonFun.formatDateStr(params.get("maxtime").toString().substring(0, 10)+" 23:59:59", "yyyy-MM-dd HH:mm:ss"));
			//Record feibie = new SampleDao().getFeibie(params);
			sample.put("dept_name",params.get("dept_name"));
			sample.put("create_user",params.get("create_user"));
			sample.put("create_time", CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
			sample.put("d_type",params.get("datasouce"));
			sample.put("sample_start_time",params.get("mintime"));
			sample.put("sample_end_time",params.get("maxtime"));
			sample.put("sample_type","2");
			sample.put("sample_num", 0);
			sample.put("state", "0");
			sample.put("comment_flag", "0");
			sample.put("sample_patient_idorname", params.get("patient"));
			sample.put("p_type",params.get("p_typeFifter"));
			sample.put("feibie",params.get("feibie_name"));
			sample.put("doctor",params.get("doctorfifter"));
			sample.put("dept_code",params.get("deptfifter"));
			sample.put("doctor_name",params.get("doctor_name"));
			sample.put("comment_way",params.get("comment_way"));
			sample.put("drug_type",params.get("drug_type"));
			sample.put("diagnosis_desc",params.get("diag"));
			sample.put("spcomment_unit",params.get("spcomment_unit"));
			if ("1".equals(params.get("datasouce"))) {
				sample.put("patient_state",params.get("patient_state"));
			}else{
				sample.put("patient_state",2);
			}
			sample.put("patient_num_sample", params.get("patient_num_sample"));
			sample.put("num_type", params.get("num_type"));
			sample.put("drug_code", params.get("drugfifter"));
			sample.put("drug_name", params.get("drug_name"));
			CommentSampleDao csd = new CommentSampleDao();
			csd.insertTMP(sample);
			Result querydetail = new SampleDao().querydetail(params);
			Map<String, Object> groupMap = new HashMap<String, Object>();
			if (!CommonFun.isNe(querydetail)) {
				CommentSamplePatientsDao cspd = new CommentSamplePatientsDao();
				OrdersResultsDao ord = new OrdersResultsDao();
				Map<String, Object> orderMap = new HashMap<String, Object>();
				for (Record orders : querydetail.getResultset()) {
					if (CommonFun.isNe(groupMap.get(orders.get("group_id")+"-"+orders.get("order_no")))) {
						orderMap.put("group_id", orders.get("group_id"));
						orderMap.put("order_no", orders.get("order_no"));
						orderMap.put("GZID", gzid);
						orderMap.put("patient_id", orders.get("patient_id"));
						orderMap.put("is_active", "1");
						orderMap.put("doctor_no", orders.get("doctor_no"));
						orderMap.put("visit_id", orders.get("visit_id"));
						groupMap.put(orders.get("group_id")+"-"+orders.get("order_no"), 1);
						ord.insertTMP(orderMap);
					}
				}
			}
			result.put("gzid", gzid);
			return result;
		} catch (Exception ex){
			rollback();
			throw ex;
		}finally{
			disconnect();
		}
	}

	public Result querysample(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			params.put("user_id", user().getId());
			Result	result = new CommentSampleDao().query(params);
			return result;
		} catch (Exception ex){
			throw ex;
		}finally{
			disconnect();
		}
	}
	
	public Map<String, Object> queryIsOwn(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			params.put("user_id", user().getId());
			return  new CommentSampleDao().queryIsOwn(params);
		} catch (Exception ex){
			throw ex;
		}finally{
			disconnect();
		}
	}

	public int updateSample(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			//new CommentSamplePatientsDao().deleteNotActive(new HashMap<String, Object>());
			//new OrdersResultsDao().deleteNotActive(new HashMap<String, Object>());
			int result = new CommentSampleDao().update(params);
			return result;
		} catch (Exception ex){
			throw ex;
		}finally{
			disconnect();
		}
	}
	
	public Result querysystem(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			return new SystemUserDao().query(params);
		} catch (Exception ex){
			throw ex;
		}finally{
			disconnect();
		}
	}
	
	public Map<String, Object> getDefalutTime() {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Map<String, Object> map = new HashMap<String, Object>();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, -1);
        Date min = calendar.getTime();
        Date max = calendar.getTime();
        map.put("defalutmixtime", format.format(min));
        map.put("defalutmaxtime", format.format(max));
		return map;
	}
	
	public void updatesampledel(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			start();
			Record r = new Record();
			CommentSampleDao cs = new CommentSampleDao();
			r.put("id", params.get("sample_id"));
			r.put("sample_num", Integer.parseInt((String)cs.get(r).get("sample_num"))-1);
			cs.update(r);
			new CommentSamplePatientsDao().updateByPS(params);
			new OrdersResultsDao().updateByPS(params);
			commit();
		} catch (Exception ex){
			rollback();
			throw ex;
		}finally{
			disconnect();
		}
	}
	
	public void updatesampleAdd(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			start();
			Record r = new Record();
			CommentSampleDao cs = new CommentSampleDao();
			r.put("id", params.get("sample_id"));
			r.put("sample_num", Integer.parseInt((String)cs.get(r).get("sample_num"))+1);
			cs.update(r);
			new CommentSamplePatientsDao().updateByPS(params);
			new OrdersResultsDao().updateByPS(params);
			commit();
		} catch (Exception ex){
			rollback();
			throw ex;
		}finally{
			disconnect();
		}
	}
	
	public int remove(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			start();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", params.get("sample_id"));
			/*map.put("state", "-9");
			new CommentSampleDao().update(map);*/
			new CommentSampleDao().delete(map);
			new CommentSamplePatientsDao().delete(params);
			new OrdersResultsDao().delete(params);
			commit();
		} catch (Exception ex){
			rollback();
			throw ex;
		}finally{
			disconnect();
		}
		return 1;
	}

	public Result queryDept(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new SampleDao().queryDept(params);
		} catch (Exception ex){
			throw ex;
		}finally{
			disconnect();
		}
	}

	public Result queryFeibie(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new SampleDao().queryFeibie(params);
		} catch (Exception ex){
			throw ex;
		}finally{
			disconnect();
		}
	}
	
	public Result queryDrug(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new SampleDao().queryDrug(params);
		} catch (Exception ex){
			throw ex;
		}finally{
			disconnect();
		}
	}
	
	public Result queryDoctor(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new SampleDao().queryDoctor(params);
		} catch (Exception ex){
			throw ex;
		}finally{
			disconnect();
		}
	}

	public Map<String, Object> sampledetail(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			Record r = new Record();
			Map<String, Object> result = getDefalutTime();
			if (!CommonFun.isNe(params.get("sample_id"))) {
				r.put("id", params.get("sample_id"));
				result.putAll(new CommentSampleDao().getAll(r));
			}
			return result;
		} catch (Exception ex){
			throw ex;
		}finally{
			disconnect();
		}
	}

	public void addSample(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			start();
			if (!CommonFun.isNe(params)) {
				Map<String, Object> patientMap = new HashMap<String, Object>();
				Map<String, Object> orderMap = new HashMap<String, Object>();
				CommentSamplePatientsDao csp = new CommentSamplePatientsDao();
				SampleDao sampleDao = new SampleDao();
				OrdersResultsDao ord = new OrdersResultsDao();
				List<Map<String, Object>> select = CommonFun.json2Object((String)params.get("select"), List.class);
				for (Map<String, Object> map : select) {
					Map<String, Object> map1 = (Map<String, Object>) map.get("data");
					map1.put("mintime", params.get("mintime"));
					map1.put("maxtime", params.get("maxtime"));
					patientMap.put("sample_id",  params.get("sample_id"));
					patientMap.put("patient_id", map1.get("patient_id"));
					patientMap.put("visit_id", map1.get("visit_id"));
					patientMap.put("state", 0);
					patientMap.put("is_active", 1);
					csp.insert(patientMap);
					Record r = new Record();
					CommentSampleDao cs = new CommentSampleDao();
					r.put("id", params.get("sample_id"));
					r.put("sample_num", Integer.parseInt((String)cs.get(r).get("sample_num"))+1);
					cs.update(r);
					Result orders = sampleDao.queryOrders(map1);
					if (!CommonFun.isNe(orders)) {
						for (Map<String, Object> order : orders.getResultset()) {
							orderMap.put("p_key", order.get("p_key"));
							orderMap.put("order_no", order.get("order_no"));
							orderMap.put("sample_id", params.get("sample_id"));
							orderMap.put("sample_patients_id", order.get("patient_id"));
							orderMap.put("is_active", "1");
							orderMap.put("doctor_no", order.get("doctor_no"));
							orderMap.put("visit_id", order.get("visit_id"));
							ord.insert(orderMap);
						}
					}
				}
			}
			commit();
		} catch (Exception ex){
			rollback();
			throw ex;
		}finally{
			disconnect();
		}
	}

	public Result queryCommentWay(Map<String, Object> params) throws Exception  {
		try {
			connect("hospital_common");
			return new CommentManageDao().queryCommentWay(params);
		} catch (Exception ex){
			throw ex;
		}finally{
			disconnect();
		}
	}

	public Result queryStartSample(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			Result result = new CommentSamplePatientsDao().queryStartSample(params);
			return result;
		} catch (Exception ex){
			throw ex;
		}finally{
			disconnect();
		}
	}

	public Record realSaveSample(Map<String, Object> params) throws Exception  {
		try {
			connect("ipc");
			Record result = new Record();
			Map<String, Object> sample = new HashMap<String, Object>();
			CommentSampleDao sampleDao = new CommentSampleDao();
			int sample_id = sampleDao.TMPInsertFormality(params);
			sampleDao.deleteTMP(params);
			List<Record> resultset = new OrdersResultsDao().queryTmp(params).getResultset();
			int sample_num = 0;
			if (!CommonFun.isNe(resultset)) {
				Map<String, Object> patient = new HashMap<String, Object>();
				Map<String, Object> orderMap = new HashMap<String, Object>();
				OrdersResultsDao ord = new OrdersResultsDao();
				CommentSamplePatientsDao cspd = new CommentSamplePatientsDao();
				for (Record orders : resultset) {
					if(CommonFun.isNe(patient.get(orders.get("patient_id")+"_"+orders.get("visit_id")))){
						Map<String, Object> patientMap = new HashMap<String, Object>();
						patientMap.put("sample_id", sample_id);
						patientMap.put("patient_id", orders.get("patient_id"));
						patientMap.put("visit_id", orders.get("visit_id"));
						patientMap.put("state", 0);
						patientMap.put("is_active", "1");
						int sample_patients_id = cspd.insert(patientMap);
						patient.put(orders.get("patient_id")+"_"+orders.get("visit_id"), sample_patients_id);
						orderMap.put("group_id", orders.get("group_id"));
						orderMap.put("order_no", orders.get("order_no"));
						orderMap.put("sample_id", sample_id);
						orderMap.put("sample_patients_id", sample_patients_id);
						orderMap.put("patient_id", orders.get("patient_id"));
						orderMap.put("is_active", "1");
						orderMap.put("doctor_no", orders.get("doctor_no"));
						orderMap.put("visit_id", orders.get("visit_id"));
						ord.insert(orderMap);
						sample_num++;
					}else{
						orderMap.put("group_id", orders.get("group_id"));
						orderMap.put("order_no", orders.get("order_no"));
						orderMap.put("sample_id", sample_id);
						orderMap.put("sample_patients_id", patient.get(orders.get("patient_id")+"_"+orders.get("visit_id")));
						orderMap.put("patient_id", orders.get("patient_id"));
						orderMap.put("is_active", "1");
						orderMap.put("doctor_no", orders.get("doctor_no"));
						orderMap.put("visit_id", orders.get("visit_id"));
						ord.insert(orderMap);
					}
				}
				ord.deleteTMP(params);
			}
			sample.put("id", sample_id);
			sample.put("sample_num", sample_num);
			sampleDao.update(sample);
			result.put("sample_num", sample_num);
			result.put("sample_id", sample_id);
			return result;
		} catch (Exception ex){
			throw ex;
		}finally{
			disconnect();
		}
	}
	
	public void sampleAllTMPByGZID(Map<String, Object> params) throws Exception  {
		try {
			connect("ipc");
			start();
			new OrdersResultsDao().deleteTMP(params);
			new CommentSampleDao().deleteTMP(params);
			commit();
		} catch (Exception ex){
			rollback();
			throw ex;
		}finally{
			disconnect();
		}
	}

	public Map<String, Object> distribution(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("assign_users", CommonFun.object2Json(new CommentSampleDao().queryAssignUser(params).getResultset()));
			result.put("type", new CommentSampleDao().get(params).get("assign_type"));
			return result;
		} catch (Exception ex){
			rollback();
			throw ex;
		}finally{
			disconnect();
		}
	}

	/**
	 * 点评提交只之前,分配给药师
	 * @param params
	 * @throws Exception
	 * @author ruiheng
	 */
	public void assign(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			CommentSamplePatientsDao pa = new CommentSamplePatientsDao();
			Map<String,Object> user_map = CommonFun.json2Object((String)params.get("comment_user_mx"), Map.class);
			List<Map<String, Object>> users = (List<Map<String, Object>>) user_map.get("person");
			String type = (String)user_map.get("type");
			//type  1和3都为数量  3为百分比
			if (!type.equals("2")) {
				Object object = params.get("id");
				pa.cleanAssignPatient(params);
				for (Map<String, Object> map : users) {
					map.put("id", object);
					pa.updateAssignPatient(map);
				}
			}else {
				/*for (Map<String, Object> map : users) {
					pa.updateAssignPatient(map);
				}*/
			}
		} catch (Exception ex){
			rollback();
			ex.printStackTrace();
		}finally{
			disconnect();
		}
	}

	public List<Record> queryToHandlerGroup(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			return new CommentSamplePatientsDao().queryToHandlerGroup(params).getResultset();
		} catch (Exception ex){
			throw ex;
		}finally{
			disconnect();
		}
	}
	
}
