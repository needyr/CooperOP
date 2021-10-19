package cn.crtech.cooperop.hospital_common.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Result;
	
public class HisInDiagnosisDao extends BaseDao{
	
	private final static String TABLE_NAME="TMP_his_in_diagnosis";
	
	public int insert(Map<String,Object> params) throws Exception {
		return executeInsert(TABLE_NAME, params);
	}
	
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("select  ");
		sql.append("a.DIAGNOSIS_CODE, ");
		sql.append("a.DIAGNOSIS_DESC, ");
		sql.append("a.DIAGNOSIS_DATE, ");
		sql.append("c.diagnosisclass_CODE, ");
		sql.append("c.diagnosisclass_NAME  ");
		sql.append("from his_in_diagnosis(nolock) a ");
		sql.append("left join dict_his_diagnosisclass (nolock) b ");
		sql.append("on a.DIAGNOSIS_TYPE=b.diagnosisclass_CODE  ");
		sql.append("left join dict_sys_diagnosisclass(nolock) c  ");
		sql.append("on b.SYS_P_KEY = c.p_key  where 1=1 ");
		setParameter(params, "patient_id", "and a.patient_id=:patient_id ", sql);
		setParameter(params, "visit_id", "and a.patient_id=:visit_id ", sql);
		return executeQuery(sql.toString(), params);
	}
}
