package cn.crtech.cooperop.ipc.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class Audit_agingDao extends BaseDao {
	public Result list(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select a.state,a.doctor_no,                                            ");
		sql.append(" dbo.GET_PATIENT_XX(a.patient_id,a.visit_id) patient,       ");
		//sql.append(" dbo.fn_Get_Date_Diff_Info(a.auto_audit_time, CONVERT(varchar,GETDATE(),120)) as timeout, ");
		sql.append(" b.name doctor_name,                                        ");
		sql.append(" a.d_type,                                                  ");
		sql.append(" a.p_type,                                                  ");
		sql.append(" d.dept_NAME deptment_name,a.deptment_code,                                           ");
		sql.append(" a.create_time,a.common_id,                                 ");
		sql.append(" a.id,a.patient_id,a.visit_id                                 ");
		sql.append(" from auto_audit(nolock) a                                          ");
		sql.append(" left join IADSCP..[system_user](nolock) b on a.doctor_no=b.no      ");
		sql.append(" left join hospital_common..V_his_in_patientvisit_all allpa(nolock) on allpa.patient_id=a.patient_id and allpa.visit_id=a.visit_id ");
		sql.append(" left join dict_his_deptment(nolock) d on a.deptment_code=d.dept_CODE  ");
		sql.append(" where a.state = 'DQ1'                                       ");
		sql.append(" and (a.is_after != '1' or a.is_after is null)   ");
		if (!CommonFun.isNe(params.get("name"))) {
			String patient_name = "%"+params.get("name")+"%";
			params.put("pa_name", patient_name);
			sql.append(" and (a.patient_id= :name or allpa.patient_name like :pa_name) ");
		}
		if (!CommonFun.isNe(params.get("d_name"))) {
			String d_name = "%"+params.get("d_name")+"%";
			params.put("doc_name", d_name);
			sql.append(" and (a.doctor_no= :d_name or b.name like :doc_name) ");
		}
		if (!CommonFun.isNe(params.get("dept_name"))) {
			sql.append(" and a.deptment_code in (:deptfifter) ");
		}
		if (!CommonFun.isNe(params.get("d_type_query"))) {
			if (params.get("d_type_query") instanceof String) {
				sql.append("and a.d_type=:d_type_query ");
			}else {
				sql.append("and a.d_type in (:d_type_query) ");
			}
		}
		setParameter(params, "mintime", " and a.create_time>= :mintime ", sql);
		setParameter(params, "maxtime", " and a.create_time<= :maxtime ", sql);
		
		return executeQueryLimit(sql.toString(), params);
	}
}
