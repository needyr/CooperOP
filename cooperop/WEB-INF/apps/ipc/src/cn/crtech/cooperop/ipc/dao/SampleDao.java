package cn.crtech.cooperop.ipc.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class SampleDao extends BaseDao{

	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("exec cr_comment_sample_UseOrders @IN_S_DATE=:mintime,@IN_E_DATE=:maxtime,");
		if (!CommonFun.isNe(params.get("patient"))) {
			sql.append("@PATIENT_ID = :patient,");
		}
		if (!CommonFun.isNe(params.get("p_typeFifter"))) {
			sql.append("@P_TYPE=:p_typeFifter,");
		}
		if (!CommonFun.isNe(params.get("datasouce"))) {
			sql.append("@D_TYPE =:datasouce,");
		}
		if (!CommonFun.isNe(params.get("deptfifter"))) {
			sql.append("@dept_code=:deptfifter,");
		}
		if (!CommonFun.isNe(params.get("patient"))) {
			sql.append("@PATIENT_NAME=:patient,");
		}
		if (!CommonFun.isNe(params.get("patient_state"))) {
			sql.append("@IN_hospital=:patient_state,");
		}
		if (!CommonFun.isNe(params.get("doctorfifter"))) {
			sql.append("@DOCTOR=:doctorfifter,");
		}
		if (!CommonFun.isNe(params.get("feibiefifter"))) {
			sql.append("@feibie_name=:feibiefifter,");
		}
		if (!CommonFun.isNe(params.get("drugfifter"))) {
			sql.append("@DRUG=:drugfifter,");
		}
		if (!CommonFun.isNe(params.get("is_getpatient"))) {
			sql.append("@IS_GETPATIENT=:is_getpatient,");
		}
		if (!CommonFun.isNe(params.get("limit"))) {
			sql.append("@limit=:limit,");
		}
		if (!CommonFun.isNe(params.get("start"))) {
			sql.append("@start=:start,");
		}
		if (!CommonFun.isNe(params.get("spcomment_unit"))) {
			sql.append("@sample_unit=:spcomment_unit,");
		}
		if (!CommonFun.isNe(params.get("drug_type"))) {
			sql.append("@sample_drug_leixing=:drug_type,");
		}
		if (!CommonFun.isNe(params.get("contain_sample"))) {
			sql.append("@is_bh_sample=:contain_sample,");
		}
		if (!CommonFun.isNe(params.get("diag"))) {
			sql.append("@diagnosis_desc=:diag,");
		}
		sql.append("@PATIENT_NUM=null,@PATIENT_PERCENT=100");
		return executeQuery(sql.toString(), params);
	}
	
	public Result queryAll(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("EXEC cr_comment_sample_UseOrders @IN_S_DATE=:mintime,@IN_E_DATE=:maxtime,");
		if (!CommonFun.isNe(params.get("patient"))) {
			sql.append("@PATIENT_ID = :patient,");
		}
		if (!CommonFun.isNe(params.get("p_typeFifter"))) {
			sql.append("@P_TYPE=:p_typeFifter,");
		}
		if (!CommonFun.isNe(params.get("datasouce"))) {
			sql.append("@D_TYPE =:datasouce,");
		}
		if (!CommonFun.isNe(params.get("deptfifter"))) {
			sql.append("@dept_code=:deptfifter,");
		}
		if (!CommonFun.isNe(params.get("patient"))) {
			sql.append("@PATIENT_NAME=:patient,");
		}
		if (!CommonFun.isNe(params.get("patient_state"))) {
			sql.append("@IN_hospital=:patient_state,");
		}
		if (!CommonFun.isNe(params.get("doctorfifter"))) {
			sql.append("@DOCTOR=:doctorfifter,");
		}
		if (!CommonFun.isNe(params.get("feibiefifter"))) {
			sql.append("@feibie_name=:feibiefifter,");
		}
		if (!CommonFun.isNe(params.get("drugfifter"))) {
			sql.append("@DRUG=:drugfifter,");
		}
		if (!CommonFun.isNe(params.get("is_getpatient"))) {
			sql.append("@IS_GETPATIENT=:is_getpatient,");
		}
		sql.append("@PATIENT_NUM=null,@PATIENT_PERCENT=100,");
		return executeQueryLimit(sql.toString(), params);
	}
	
	public Result queryComment(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select                                                                              ");
		sql.append("result.d_type,result.p_type,result.PATIENT_NAME,                                    ");
		sql.append("result.sex,result.is_active,result.age,result.patient_id,                           ");
		sql.append("result.DISCHARGE_DATETIME,result.charge_type,result.visit_id from                   ");
		sql.append("(select                                                                             ");
		sql.append("a.d_type,                                                                           ");
		sql.append("a.p_type,                                                                           ");
		sql.append("a.doctor,                                                                           ");
		sql.append("vhipa.PATIENT_NAME,                                                                    ");
		sql.append("vhipa.sex,                                                                             ");
		sql.append("csp.is_active,                                                                        ");
		sql.append("vhipa.age,      ");
		sql.append("csp.patient_id,                                                                     ");
		sql.append("vhipa.DISCHARGE_DATETIME,                                                             ");
		sql.append("vhipa.charge_type,                                                                     ");
		sql.append("csp.visit_id                                                                        ");
		sql.append("from YWLSB_ORDERS a (nolock)                                                                ");
		sql.append("LEFT JOIN comment_sample_patients ( nolock ) csp on a.patient_id = csp.patient_id   ");
		sql.append("AND csp.visit_id = a.visit_id                                                       ");
		/*sql.append("LEFT JOIN his_patient ( nolock ) hp ON csp.patient_id = hp.patient_id               ");
		sql.append("LEFT JOIN his_in_patientvisit ( nolock ) hip ON csp.patient_id = hip.patient_id     ");*/
		sql.append(" left join V_his_in_patientvisit_all(nolock) vhipa on vhipa.patient_id = csp.patient_id and vhipa.visit_id = csp.visit_id ");
		//sql.append("AND csp.visit_id = hip.visit_id                                                     ");
		sql.append("LEFT JOIN (                                                                         ");
		sql.append("SELECT                                                                              ");
		sql.append("	b.sample_patients_id,                                                           ");
		sql.append("	b.is_active,b.visit_id                                                          ");
		sql.append("FROM                                                                                ");
		sql.append("	comment_sample_orders ( nolock ) b                                              ");
		sql.append("WHERE                                                                               ");
		sql.append("	b.sample_id = :sample_id                                                        ");
		sql.append("GROUP BY                                                                            ");
		sql.append("	b.sample_patients_id,                                                           ");
		sql.append("	b.is_active,b.visit_id                                                          ");
		sql.append("	) b ON b.sample_patients_id = csp.id      ");
		sql.append("	where csp.sample_id = :sample_id) result                                        ");
		sql.append("	group by result.d_type,result.p_type,result.PATIENT_NAME,                       ");
		sql.append("result.sex,result.is_active,result.age,result.patient_id,                           ");
		sql.append("result.DISCHARGE_DATETIME,result.charge_type,result.visit_id                        ");
		return executeQueryLimit(sql.toString(), params);
	}
	
	public Result querydetail(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		params.put("is_getpatient",0);
		sql.append("EXEC cr_comment_sample_UseOrders @IN_S_DATE=:mintime,@IN_E_DATE=:maxtime,");
		if (!CommonFun.isNe(params.get("patient"))) {
			sql.append("@PATIENT_ID = :patient,");
		}
		if (!CommonFun.isNe(params.get("p_typeFifter"))) {
			sql.append("@P_TYPE=:p_typeFifter,");
		}
		if (!CommonFun.isNe(params.get("datasouce"))) {
			sql.append("@D_TYPE =:datasouce,");
		}
		if (!CommonFun.isNe(params.get("deptfifter"))) {
			sql.append("@dept_code=:deptfifter,");
		}
		if (!CommonFun.isNe(params.get("patient"))) {
			sql.append("@PATIENT_NAME=:patient,");
		}
		if (!CommonFun.isNe(params.get("patient_state"))) {
			sql.append("@IN_hospital=:patient_state,");
		}
		if (!CommonFun.isNe(params.get("doctorfifter"))) {
			sql.append("@DOCTOR=:doctorfifter,");
		}
		if (!CommonFun.isNe(params.get("feibiefifter"))) {
			sql.append("@feibie_name=:feibiefifter,");
		}
		if (!CommonFun.isNe(params.get("drugfifter"))) {
			sql.append("@DRUG=:drugfifter,");
		}
		if (!CommonFun.isNe(params.get("is_getpatient"))) {
			sql.append("@IS_GETPATIENT=:is_getpatient,");
		}
		if (!CommonFun.isNe(params.get("spcomment_unit"))) {
			sql.append("@sample_unit=:spcomment_unit,");
		}
		if (!CommonFun.isNe(params.get("drug_type"))) {
			sql.append("@sample_drug_leixing=:drug_type,");
		}
		if (!CommonFun.isNe(params.get("contain_sample"))) {
			sql.append("@is_bh_sample=:contain_sample,");
		}
		if (!CommonFun.isNe(params.get("diag"))) {
			sql.append("@diagnosis_desc=:diag,");
		}
		if ("1".equals(params.get("num_type")) && !CommonFun.isNe(params.get("patient_num_sample"))) {
			sql.append("@PATIENT_NUM=null,@PATIENT_PERCENT=:patient_num_sample");
		}else if("2".equals(params.get("num_type")) && !CommonFun.isNe(params.get("patient_num_sample"))){
			sql.append("@PATIENT_NUM=:patient_num_sample,@PATIENT_PERCENT=null");
		}else {
			sql.append("@PATIENT_NUM=null,@PATIENT_PERCENT=null");
		}
		return executeCallQuery(sql.toString(), params);
	}

	public Result queryDept(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select dept_code,dept_name from dict_his_deptment (nolock) ");
		sql.append(" WHERE 1=1 ");
		if (!CommonFun.isNe(params.get("filter"))) {
			params.put("key", "%"+params.get("filter")+"%");
		}
		if (!CommonFun.isNe(params.get("outp_or_inp")) && params.get("outp_or_inp").equals("1")) {
			sql.append(" and outp_or_inp= 0 ");//住院科室
		}else if (!CommonFun.isNe(params.get("outp_or_inp"))) {
			sql.append(" and outp_or_inp= 1 ");//急诊，门诊科室
		}
		setParameter(params, "filter", " and (dept_code like :key or dept_name like :key)", sql);
		params.put("sort","dept_code,dept_name");
		return executeQueryLimit(sql.toString(), params);
	}

	public Result queryFeibie(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select feibie_name,p_key from dict_his_feibie (nolock) ");
		sql.append(" where 1=1 ");
		if (!CommonFun.isNe(params.get("filter"))) {
			params.put("key", "%"+params.get("filter")+"%");
		}
		setParameter(params, "filter", " and (p_key like :key or feibie_name like :key)", sql);
		return executeQueryLimit(sql.toString(), params);
	}
	
	public Result queryDrug(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select p_key,drug_name,druggg,shengccj,pizhwh,jixing,drug_code from dict_his_drug (nolock) ");
		sql.append(" where 1=1 ");
		if (!CommonFun.isNe(params.get("filter"))) {
			params.put("key", "%"+params.get("filter")+"%");
		}
		setParameter(params, "filter", " and (p_key = :filter or drug_name like :key or input_code like :key )", sql);
		return executeQueryLimit(sql.toString(), params);
	}
	
	public Result queryDoctor(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select user_name,p_key from dict_his_users(nolock) ");
		sql.append(" where 1=1 ");
		if (!CommonFun.isNe(params.get("filter"))) {
			params.put("key", "%"+params.get("filter")+"%");
		}
		setParameter(params, "filter", " and (p_key like :key or user_name like :key)", sql);
		sql.append(" and (JOB_ROLE LIKE '%医生%' or JOB_ROLE LIKE '%医师%')");
		return executeQueryLimit(sql.toString(), params);
	}

	public Result queryOrders(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select ");
		sql.append("B.PATIENT_NAME,datediff(year,(CONVERT(datetime,B.birthday,101)),getdate()) age,                                          ");
		sql.append("B.sex,B.charge_type,A.*, ");
		sql.append("(case when C.DISCHARGE_DATETIME is null then '在院' when C.DISCHARGE_DATETIME is not null then '出院' end ) zaichu ");
		sql.append("from ywlsb_orders A(NOLOCK)                                                                                                   ");
		sql.append("inner join his_patient B(NOLOCK) on A.patient_id = B.patient_id                                                               ");
		sql.append("inner join his_in_patientvisit c(NOLOCK) on a.patient_id=c.patient_id and a.visit_id=c.visit_id                               ");
		sql.append("WHERE                                                                                                                         ");
		sql.append("((REPEAT_INDICATOR = 1 AND A.START_date_time BETWEEN CONVERT(varchar(10),:mintime,21) and CONVERT(varchar(10),:maxtime,21))                                               ");
		sql.append("OR                                                                                                                            ");
		sql.append("(REPEAT_INDICATOR = 1 AND A.START_date_time < CONVERT(varchar(10),:mintime,21) and isnull(A.STOP_DATE_TIME,'')='')                                        ");
		sql.append("OR                                                                                                                            ");
		sql.append("(REPEAT_INDICATOR = 1 AND A.START_date_time < CONVERT(varchar(10),:mintime,21) and A.STOP_DATE_TIME BETWEEN CONVERT(varchar(10),:mintime,21) and CONVERT(varchar(10),:maxtime,21))              ");
		sql.append("OR                                                                                                                            ");
		sql.append("(REPEAT_INDICATOR = 0 AND A.START_date_time BETWEEN CONVERT(varchar(10),:mintime,21) AND CONVERT(varchar(10),:maxtime,21))                                                ");
		sql.append(")                                                                                                                             ");
		sql.append(" and A.patient_id= :patient_id and A.visit_id= :visit_id");
		return executeQuery(sql.toString(), params);
	}
	
	public Result queryOrdersByPatient(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("EXEC cr_comment_sample_UseOrders @IN_S_DATE=:mintime,@IN_E_DATE=:maxtime,");
		if (!CommonFun.isNe(params.get("patient"))) {
			sql.append("@PATIENT_ID = :patient,");
		}
		if (!CommonFun.isNe(params.get("p_typeFifter"))) {
			sql.append("@P_TYPE=:p_typeFifter,");
		}
		if (!CommonFun.isNe(params.get("datasouce"))) {
			sql.append("@D_TYPE =:datasouce,");
		}
		if (!CommonFun.isNe(params.get("deptfifter"))) {
			sql.append("@dept_code=:deptfifter,");
		}
		if (!CommonFun.isNe(params.get("patient"))) {
			sql.append("@PATIENT_NAME=:patient,");
		}
		if (!CommonFun.isNe(params.get("patient_state"))) {
			sql.append("@IN_hospital=:patient_state,");
		}
		if (!CommonFun.isNe(params.get("doctorfifter"))) {
			sql.append("@DOCTOR=:doctorfifter,");
		}
		if (!CommonFun.isNe(params.get("feibiefifter"))) {
			sql.append("@feibie_name=:feibiefifter,");
		}
		if (!CommonFun.isNe(params.get("drugfifter"))) {
			sql.append("@DRUG=:drugfifter,");
		}
		if (!CommonFun.isNe(params.get("is_getpatient"))) {
			sql.append("@IS_GETPATIENT=:is_getpatient,");
		}
		if (!CommonFun.isNe(params.get("spcomment_unit"))) {
			sql.append("@sample_unit=:spcomment_unit,");
		}
		if (!CommonFun.isNe(params.get("drug_type"))) {
			sql.append("@sample_drug_leixing=:drug_type,");
		}
		if (!CommonFun.isNe(params.get("contain_sample"))) {
			sql.append("@is_bh_sample=:contain_sample,");
		}
		if (!CommonFun.isNe(params.get("diag"))) {
			sql.append("@diagnosis_desc=:diag,");
		}
		if (!CommonFun.isNe(params.get("patients_visits_xml"))) {
			sql.append("@patients_visits_xml=:patients_visits_xml,");
		}
		sql.append("@PATIENT_NUM=null,@PATIENT_PERCENT=100");
		return executeQuery(sql.toString(), params);
	}

	public Record getFeibie(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		if (!CommonFun.isNe(params.get("feibiefifter"))) {
			Record r = new Record();
			r.put("feibiefifter", params.get("feibiefifter"));
			sql.append("select feibie_name,p_key from dict_his_feibie (nolock) ");
			sql.append(" where  p_key= :feibiefifter");
			return executeQuerySingleRecord(sql.toString(), params);
		}
		return null;
	}

}
