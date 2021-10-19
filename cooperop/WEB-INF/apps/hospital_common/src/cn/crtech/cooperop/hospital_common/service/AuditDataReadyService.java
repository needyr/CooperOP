package cn.crtech.cooperop.hospital_common.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.dao.AutoCommonDiagnosisDao;
import cn.crtech.cooperop.hospital_common.dao.AutoCommonPatientDao;

public class AuditDataReadyService extends BaseService {

	/**
	 * 审查服务前的数据准备工作
	 * @param params
	 * @throws Exception
	 */
	public void ready(Map<String, Object> params) throws Exception {
		Map<String, Object> p = (Map<String, Object>) params.get("req");
		Map<String, Object> patientmap = (Map<String, Object>) p.get("patient");
		try {
			Map<String, Object> patient_info = new HashMap<String, Object>();
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
			Double weight = CommonFun.isNe(patientmap.get("weight"))?0:Double.parseDouble(String.valueOf(patientmap.get("weight")));
			if(weight > 250) {
				weight = weight/1000;
			}
			patient_info.put("weight", weight);
			patient_info.put("common_id", params.get("common_id"));
			connect("hospital_common");
			//插入审查患者基本数据
			new AutoCommonPatientDao().insert(patient_info);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			connect("hospital_common");
			if(CommonFun.isNe(p.get("diagnosis_info"))) {
				return;
			}
			if(p.get("diagnosis_info") instanceof java.util.Map) {
				Map<String, Object>  info = (Map<String, Object>) p.get("diagnosis_info");
				Object diagnosis = info.get("diagnosis");
				if(diagnosis instanceof java.util.Map) {
					Map<String, Object> d = (Map<String, Object>) diagnosis;
					Map<String, Object> tmp = new HashMap<String, Object>();
					tmp.put("patient_id", patientmap.get("id"));
					tmp.put("visit_id", patientmap.get("visitid"));
					tmp.put("diagnosis_date", d.get("date"));
					tmp.put("diagnosis_type", d.get("type"));
					tmp.put("diagnosis_no", d.get("no"));
					tmp.put("diagnosis_desc", d.get("desc"));
					tmp.put("diagnosis_code", d.get("code"));
					tmp.put("common_id", params.get("common_id"));
					//插入审查患者诊断数据
					new AutoCommonDiagnosisDao().insert(tmp);
				}else {
					List<Map<String, Object>> d = (List<Map<String, Object>>) diagnosis;
					for (Map<String, Object> map : d) {
						Map<String, Object> tmp = new HashMap<String, Object>();
						tmp.put("patient_id", patientmap.get("id"));
						tmp.put("visit_id", patientmap.get("visitid"));
						tmp.put("diagnosis_date", map.get("date"));
						tmp.put("diagnosis_type", map.get("type"));
						tmp.put("diagnosis_no", map.get("no"));
						tmp.put("diagnosis_desc", map.get("desc"));
						tmp.put("diagnosis_code", map.get("code"));
						tmp.put("common_id", params.get("common_id"));
						//插入审查患者诊断数据
						new AutoCommonDiagnosisDao().insert(tmp);
					}
				}
			}else if(p.get("diagnosis_info") instanceof java.util.List) {
				List<Map<String, Object>> dlist = (List<Map<String, Object>>) p.get("diagnosis_info");
				if(dlist.size() > 0) {
					for (Map<String, Object> d : dlist) {
						Map<String, Object> tmp = new HashMap<String, Object>();
						tmp.put("patient_id", patientmap.get("id"));
						tmp.put("visit_id", patientmap.get("visitid"));
						tmp.put("diagnosis_date", d.get("date"));
						tmp.put("diagnosis_type", d.get("type"));
						tmp.put("diagnosis_no", d.get("no"));
						tmp.put("diagnosis_desc", d.get("desc"));
						tmp.put("diagnosis_code", d.get("code"));
						tmp.put("common_id", params.get("common_id"));
						//插入审查患者诊断数据
						new AutoCommonDiagnosisDao().insert(tmp);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			disconnect();
		}
	}
}
