package cn.crtech.cooperop.hospital_common.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.dao.HisInDiagnosisDao;

public class InsertDiagnosisService extends BaseService {
	
	/**
	 * @author wangsen
	 * @date 2019年1月31日 上午10:40:22 
	 * @param params 
	 * @throws Exception  
	 * @function 插入诊断信息
	 */
	public void insert(Map<String, Object> params) throws Exception {

		try {

			Map<String, Object> patient = CommonFun.object2Map(params.get("patient"));
			Map<String, Object> diagnosisInfo = CommonFun.object2Map(params.get("diagnosis_info"));

			if (CommonFun.isNe(diagnosisInfo)) {
				return;
			}

			Object diagObj = diagnosisInfo.get("diagnosis");

			if (CommonFun.isNe(diagObj)) {
				return;
			}

			String type = diagObj.getClass().toString();
			
			List<Map<String, Object>> diagnosisList;

			if ("class java.util.ArrayList".equals(type)) {
				diagnosisList = (List<Map<String, Object>>) diagObj;
			}

			else {
				diagnosisList = new ArrayList<Map<String, Object>>();
				diagnosisList.add((Map<String, Object>) diagObj);
			}

			connect("hospital_common");

			for (Map<String, Object> diagnosis : diagnosisList) {
				Map<String, Object> content = new HashMap<String, Object>();
				content.put("patient_id", patient.get("id"));
				content.put("visit_id", patient.get("visitid"));
				content.put("diagnosis_date", diagnosis.get("date"));
				content.put("diagnosis_type", diagnosis.get("type"));
				content.put("diagnosis_no", diagnosis.get("no"));
				content.put("diagnosis_code", diagnosis.get("code"));
				content.put("diagnosis_desc", diagnosis.get("desc"));
				StringBuffer p_key = new StringBuffer();
				p_key.append("new");
				p_key.append("|" + patient.get("id"));
				p_key.append("|" + patient.get("visitid"));
				p_key.append("|" + diagnosis.get("type"));
				p_key.append("|" + diagnosis.get("no"));
				content.put("p_key", p_key.toString());
				try{
					new HisInDiagnosisDao().insert(content);
				}catch(Exception es1){
					continue;
				}
				
			}

		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}
	}

}
