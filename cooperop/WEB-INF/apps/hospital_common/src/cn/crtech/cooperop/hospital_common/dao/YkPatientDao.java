package cn.crtech.cooperop.hospital_common.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class YkPatientDao extends BaseDao{
		
	public Result search(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		                                                                                                
		sql.append("select distinct(b.PATIENT_ID),b.PATIENT_NAME ,v.name as doctor_name ");
		sql.append("from hospital_common..v_auto_common_use ac (nolock)  inner join               ");
		sql.append("V_his_in_patientvisit_all b (nolock)  ");
		sql.append("on ac.patient_id=b.patient_id and ac.VISIT_ID=b.VISIT_ID   ");
		sql.append("left join IADSCP..v_system_user v (nolock) on ac.doctor_no =v.no   where 1=1      ");
		if(!CommonFun.isNe(params.get("data"))){
			params.put("patient_name", "%"+params.get("data")+"%");
			sql.append(" and b.patient_name like :patient_name  ");
		}
		sql.append("order by b.PATIENT_ID             ");
		return executeQuery(sql.toString(), params);
	}

	public Result searchCheck(Map<String, Object> params) throws Exception {
		String code = "'"+params.get("code").toString()+"'";
		params.put("code", code.replaceAll(",", "','"));
		StringBuffer sql = new StringBuffer();
		sql.append("select distinct(b.PATIENT_ID),b.PATIENT_NAME ,v.name as doctor_name ");
		sql.append("from hospital_common..v_auto_common_use ac (nolock)  inner join               ");
		sql.append("V_his_in_patientvisit_all b (nolock)  ");
		sql.append("on ac.patient_id=b.patient_id and ac.VISIT_ID=b.VISIT_ID   ");
		sql.append("left join IADSCP..v_system_user v (nolock)  on ac.doctor_no =v.no  where 1=1     ");
		if(!CommonFun.isNe(params.get("data"))){
			params.put("patient_name", "%"+params.get("data")+"%");
			sql.append(" and b.patient_name like :patient_name  ");
		}
		sql.append(" and b.PATIENT_ID in("+params.get("code")+") ");
		sql.append("order by b.PATIENT_ID             ");
		return executeQuery(sql.toString(), params);
	}
}
