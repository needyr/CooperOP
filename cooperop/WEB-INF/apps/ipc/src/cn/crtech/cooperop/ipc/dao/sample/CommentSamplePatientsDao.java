package cn.crtech.cooperop.ipc.dao.sample;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class CommentSamplePatientsDao extends BaseDao{
	
	private final static String COMMENT_SAMPLE_PATIENTS = "comment_sample_patients";
	
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql =new StringBuffer();
		sql.append(" 	SELECT * FROM                                                                                                                        ");
		sql.append(" (SELECT                                                                                                                          ");
		sql.append(" 	csp.*,                                                                                                                       ");
		sql.append(" 	cs.id as cs_id,cs.comment_user,cs.create_user,                                                                                                                    ");
		sql.append(" 	dbo.GET_PATIENT_XX ( csp.patient_id, csp.visit_id ) AS patient_name,                                                         ");
		sql.append(" 	vhipa.discharge_datetime,                                                                                                     ");
		sql.append(" 	vhipa.admission_datetime,                                                                                                     ");
		sql.append(" 	dhd.dept_name AS dept_in,                                                                                                    ");
		sql.append(" 	dhdc.dept_name AS dept_discharge,                                                                                            ");
		sql.append(" 	vhipa.director, vhipa.age,                                                                                                              ");
		sql.append(" 	vhipa.attending_doctor,                                                                                                       ");
		sql.append(" 	vhipa.doctor_in_charge,                                                                                                       ");
		sql.append(" 	vhipa.pat_adm_condition,                                                                                                      ");
		sql.append(" 	vhipa.charge_type,vhipa.patient_no,                                                                                                            ");
		sql.append(" 	dhi.IDENTITY_NAME AS shenfen,vsu.name handler_name                                                                                                 ");
		sql.append(" FROM                                                                                                                            ");
		sql.append(" 	comment_sample ( nolock ) cs                                                                                                 ");
		sql.append(" 	INNER JOIN comment_sample_patients ( nolock ) csp ON cs.id = csp.sample_id                                                   ");
		sql.append(" left join IADSCP..v_system_user ( nolock ) vsu on vsu.id = csp.handler ");
		/*sql.append(" 	INNER JOIN his_patient ( nolock ) hp ON hp.patient_id = csp.patient_id                                                       ");
		sql.append(" 	INNER JOIN his_in_patientvisit ( nolock ) hipt ON ( hipt.patient_id = csp.patient_id AND hipt.visit_id = csp.visit_id )      ");*/
		sql.append(" left join V_his_in_patientvisit_all(nolock) vhipa on vhipa.patient_id = csp.patient_id and vhipa.visit_id = csp.visit_id ");
		sql.append(" 	left JOIN dict_his_deptment dhd ( nolock ) ON dhd.p_key = vhipa.dept_in                                                                 ");
		sql.append(" 	left JOIN dict_his_deptment dhdc ( nolock ) ON dhdc.p_key = vhipa.dept_discharge                                                        ");
		sql.append(" 	LEFT JOIN dict_his_identity dhi ( nolock ) ON dhi.p_key = vhipa.[identity]                                                               ");
		sql.append(" 	) X                                                                                                            ");
		if(CommonFun.isNe(params.get("djbh"))) {
			sql.append(" WHERE X.cs_id = :sample_id   ");
		}else {
			sql.append(" WHERE X.djbh = :djbh   ");
		}
		sql.append(" and (X.handler = :comment_user or X.create_user = :create_user )");
		setParameter(params, "patient_no_query", " and X.patient_no= :patient_no_query ", sql);
		if (!CommonFun.isNe(params.get("patient_id_query"))) {
			String patient_name = "%"+params.get("patient_id_query")+"%";
			params.put("patient_name", patient_name);
			sql.append(" and (X.patient_id= :patient_id_query or X.patient_name like :patient_name) ");
		}
		if (!"-1".equals(params.get("comment_state_query"))) {
			setParameter(params, "comment_state_query", " and X.state= :comment_state_query ", sql);
		}
		params.put("sort", " state ");
		return executeQueryLimit(sql.toString(), params);
	}
	
	public Result queryByCommentUser(Map<String, Object> params) throws Exception {
		StringBuffer sql =new StringBuffer();
		sql.append(" select                                                                                                           ");
		sql.append(" 	csp.*,                                                                                                       ");
		//sql.append(" 	cs.djbh,                                                                                                     ");
		sql.append(" 	vhipa.patient_name,vhipa.discharge_datetime,                                                                                            ");
		sql.append(" 	vhipa.sex,                                                                                                      ");
		sql.append(" 	vhipa.admission_datetime,                                                                                     ");
		sql.append(" 	vhipa.age,                               ");
		sql.append(" 	dhd.dept_name as dept_in,                                                                                                ");
		sql.append(" 	dhdc.dept_name as dept_discharge,                                                                                         ");
		sql.append(" 	vhipa.director,                                                                                               ");
		sql.append(" 	vhipa.attending_doctor,                                                                                       ");
		sql.append(" 	vhipa.doctor_in_charge,                                                                                       ");
		sql.append(" 	vhipa.pat_adm_condition,                                                                                      ");
		sql.append(" 	vhipa.charge_type,                                                                                            ");
		sql.append(" 	dhi.IDENTITY_NAME as shenfen                                                                                           ");
		sql.append(" from                                                                                                            ");
		sql.append(" 	comment_sample (nolock) cs                                                                                            ");
		sql.append(" 	inner join comment_sample_patients (nolock) csp on cs.id = csp.sample_id                                              ");
		/*sql.append(" 	inner join his_patient (nolock) hp on hp.patient_id = csp.patient_id                                                  ");
		sql.append(" 	inner join his_in_patientvisit (nolock) hipt on ( hipt.patient_id = csp.patient_id and hipt.visit_id = csp.visit_id ) ");*/
		sql.append(" left join V_his_in_patientvisit_all(nolock) vhipa on vhipa.patient_id = csp.patient_id and vhipa.visit_id = csp.visit_id ");
		sql.append(" left join dict_his_deptment dhd ( nolock ) on dhd.p_key=hipt.dept_in ");
		sql.append(" left join dict_his_deptment dhdc ( nolock ) on dhdc.p_key=hipt.dept_discharge ");
		sql.append(" left join dict_his_identity dhi ( nolock ) on dhi.p_key=hipt.[identity] ");
		sql.append(" where cs.id = :sample_id   ");
		sql.append(" and csp.handler = :comment_user ");
		return executeQueryLimit(sql.toString(), params);
	}
	
	public Result queryNextPat(Map<String, Object> params) throws Exception {
		StringBuffer sql =new StringBuffer();
		sql.append(" select                                                                                                           ");
		sql.append(" 	csp.*,                                                                                                       ");
		sql.append(" 	cs.djbh,                                                                                                     ");
		sql.append(" 	vhipa.patient_name,vhipa.discharge_datetime,                                                                                            ");
		sql.append(" 	vhipa.sex,                                                                                                      ");
		sql.append(" 	vhipa.admission_datetime,                                                                                     ");
		sql.append(" 	vhipa.age,                               ");
		sql.append(" 	dhd.dept_name as dept_in,                                                                                                ");
		sql.append(" 	dhdc.dept_name as dept_discharge,                                                                                         ");
		sql.append(" 	vhipa.director,                                                                                               ");
		sql.append(" 	vhipa.attending_doctor,                                                                                       ");
		sql.append(" 	vhipa.doctor_in_charge,                                                                                       ");
		sql.append(" 	vhipa.pat_adm_condition,                                                                                      ");
		sql.append(" 	vhipa.charge_type,                                                                                            ");
		sql.append(" 	dhi.IDENTITY_NAME as shenfen                                                                                           ");
		sql.append(" from                                                                                                            ");
		sql.append(" 	comment_sample (nolock) cs                                                                                            ");
		sql.append(" 	left join comment_sample_patients (nolock) csp on cs.id = csp.sample_id                                              ");
		/*sql.append(" 	left join his_patient (nolock) hp on hp.patient_id = csp.patient_id                                                  ");
		sql.append(" 	left join his_in_patientvisit (nolock) hipt on ( hipt.patient_id = csp.patient_id and hipt.visit_id = csp.visit_id ) ");*/
		sql.append(" left join V_his_in_patientvisit_all(nolock) vhipa on vhipa.patient_id = csp.patient_id and vhipa.visit_id = csp.visit_id ");
		sql.append(" left join dict_his_deptment dhd on dhd.p_key=vhipa.dept_in ");
		sql.append(" left join dict_his_deptment dhdc on dhdc.p_key=vhipa.dept_discharge ");
		sql.append(" left join dict_his_identity dhi on dhi.p_key=vhipa.[identity] ");
		sql.append(" where csp.djbh = :djbh   ");
		sql.append(" and csp.handler = :comment_user ");
		sql.append(" and csp.state = 0 ");
		return executeQuery(sql.toString(), params);
	}
	
	public Result queryNextPatOwn(Map<String, Object> params) throws Exception {
		StringBuffer sql =new StringBuffer();
		sql.append(" select                                                                                                           ");
		sql.append(" 	csp.*,                                                                                                       ");
		//sql.append(" 	cs.djbh,                                                                                                     ");
		sql.append(" 	vhipa.patient_name,vhipa.discharge_datetime,                                                                                            ");
		sql.append(" 	vhipa.sex,                                                                                                      ");
		sql.append(" 	vhipa.admission_datetime,                                                                                     ");
		sql.append(" 	vhipa.age,                               ");
		sql.append(" 	dhd.dept_name as dept_in,                                                                                                ");
		sql.append(" 	dhdc.dept_name as dept_discharge,                                                                                         ");
		sql.append(" 	vhipa.director,                                                                                               ");
		sql.append(" 	vhipa.attending_doctor,                                                                                       ");
		sql.append(" 	vhipa.doctor_in_charge,                                                                                       ");
		sql.append(" 	vhipa.pat_adm_condition,                                                                                      ");
		sql.append(" 	vhipa.charge_type,                                                                                            ");
		sql.append(" 	dhi.IDENTITY_NAME as shenfen                                                                                           ");
		sql.append(" from                                                                                                            ");
		sql.append(" 	comment_sample (nolock) cs                                                                                            ");
		sql.append(" 	left join comment_sample_patients (nolock) csp on cs.id = csp.sample_id                                              ");
		/*sql.append(" 	left join his_patient (nolock) hp on hp.patient_id = csp.patient_id                                                  ");
		sql.append(" 	left join his_in_patientvisit (nolock) hipt on ( hipt.patient_id = csp.patient_id and hipt.visit_id = csp.visit_id ) ");*/
		sql.append(" left join V_his_in_patientvisit_all(nolock) vhipa on vhipa.patient_id = csp.patient_id and vhipa.visit_id = csp.visit_id ");
		sql.append(" left join dict_his_deptment dhd on dhd.p_key=vhipa.dept_in ");
		sql.append(" left join dict_his_deptment dhdc on dhdc.p_key=vhipa.dept_discharge ");
		sql.append(" left join dict_his_identity dhi on dhi.p_key=vhipa.[identity] ");
		sql.append(" where cs.id = :sample_id   ");
		sql.append(" and csp.handler = :comment_user ");
		sql.append(" and csp.state = 0 ");
		return executeQueryLimit(sql.toString(), params);
	}
	
	public Result queryBySampleId(Map<String, Object> params) throws Exception {
		return executeQuery("select * from "+COMMENT_SAMPLE_PATIENTS+" where sample_id = :sample_id", params);
	}
	
	public Result queryBySampleIdGroup(Map<String, Object> params) throws Exception {
		return executeQuery("select patient_id,visit_id from "+COMMENT_SAMPLE_PATIENTS+" where sample_id = :sample_id group by patient_id,visit_id", params);
	}
	
	public Result queryFinishNum(Map<String, Object> params) throws Exception {
		StringBuffer sql =new StringBuffer();
		sql.append(" select                                                                                                           ");
		sql.append(" 	csp.*,                                                                                                       ");
		//sql.append(" 	cs.djbh,                                                                                                     ");
		sql.append(" 	vhipa.patient_name,vhipa.discharge_datetime,                                                                                            ");
		sql.append(" 	vhipa.sex,                                                                                                      ");
		sql.append(" 	vhipa.admission_datetime,                                                                                     ");
		sql.append(" 	vhipa.age,                               ");
		sql.append(" 	vhipa.dept_in,                                                                                                ");
		sql.append(" 	vhipa.dept_discharge,                                                                                         ");
		sql.append(" 	vhipa.director,                                                                                               ");
		sql.append(" 	vhipa.attending_doctor,                                                                                       ");
		sql.append(" 	vhipa.doctor_in_charge,                                                                                       ");
		sql.append(" 	vhipa.pat_adm_condition,                                                                                      ");
		sql.append(" 	vhipa.charge_type,                                                                                            ");
		sql.append(" 	vhipa.[identity] shenfen                                                                                           ");
		sql.append(" from                                                                                                            ");
		//sql.append(" 	comment_sample (nolock) cs                                                                                            ");
		//sql.append(" 	inner join comment_sample_patients (nolock) csp on cs.id = csp.sample_id                                              ");
		sql.append(" 	comment_sample_patients (nolock) csp                                               ");
		sql.append(" 	inner join comment_sample (nolock) cs on cs.id = csp.sample_id                                                                 ");
		/*sql.append(" 	inner join his_patient (nolock) hp on hp.patient_id = csp.patient_id                                                  ");
		sql.append(" 	inner join his_in_patientvisit (nolock) hipt on ( hipt.patient_id = csp.patient_id and hipt.visit_id = csp.visit_id ) ");*/
		sql.append(" left join V_his_in_patientvisit_all(nolock) vhipa on vhipa.patient_id = csp.patient_id and vhipa.visit_id = csp.visit_id ");
		sql.append(" where csp.djbh = :djbh and cs.state=1 and csp.state = 0 ");
		sql.append(" and csp.handler = :comment_user ");
		return executeQueryLimit(sql.toString(), params);
	}
	
	public Result queryFinishAllNum(Map<String, Object> params) throws Exception {
		StringBuffer sql =new StringBuffer();
		sql.append(" select count(1) as count from              ");
		sql.append(" comment_sample_patients (nolock)           ");
		sql.append(" where                                      ");
		sql.append(" sample_id =                                ");
		sql.append(" (select top 1 sample_id                    ");
		sql.append(" from comment_sample_patients (nolock)      ");
		sql.append(" where djbh= :djbh) and state = 0           ");
		return executeQuery(sql.toString(), params);
	}
	
	public Result queryFinishNumOwn(Map<String, Object> params) throws Exception {
		StringBuffer sql =new StringBuffer();
		sql.append(" select                                                                                                           ");
		sql.append(" 	csp.*,                                                                                                       ");
		//sql.append(" 	cs.djbh,                                                                                                     ");
		sql.append(" 	vhipa.patient_name,vhipa.discharge_datetime,                                                                                            ");
		sql.append(" 	vhipa.sex,                                                                                                      ");
		sql.append(" 	vhipa.admission_datetime,                                                                                     ");
		sql.append(" 	vhipa.age,                               ");
		sql.append(" 	vhipa.dept_in,                                                                                                ");
		sql.append(" 	vhipa.dept_discharge,                                                                                         ");
		sql.append(" 	vhipa.director,                                                                                               ");
		sql.append(" 	vhipa.attending_doctor,                                                                                       ");
		sql.append(" 	vhipa.doctor_in_charge,                                                                                       ");
		sql.append(" 	vhipa.pat_adm_condition,                                                                                      ");
		sql.append(" 	vhipa.charge_type,                                                                                            ");
		sql.append(" 	vhipa.[identity] shenfen                                                                                           ");
		sql.append(" from                                                                                                            ");
		sql.append(" 	comment_sample (nolock) cs                                                                                            ");
		sql.append(" 	inner join comment_sample_patients (nolock) csp on cs.id = csp.sample_id                                              ");
		/*sql.append(" 	inner join his_patient (nolock) hp on hp.patient_id = csp.patient_id                                                  ");
		sql.append(" 	inner join his_in_patientvisit (nolock) hipt on ( hipt.patient_id = csp.patient_id and hipt.visit_id = csp.visit_id ) ");*/
		sql.append(" left join V_his_in_patientvisit_all(nolock) vhipa on vhipa.patient_id = csp.patient_id and vhipa.visit_id = csp.visit_id ");
		sql.append(" where cs.id = :sample_id and cs.state=1 and csp.state = 0 ");
		sql.append(" and csp.handler = :comment_user ");
		return executeQueryLimit(sql.toString(), params);
	}
	
	public Record gePatientInfo(Map<String, Object> params) throws Exception {
		StringBuffer sql =new StringBuffer();
		sql.append(" select                                                                                                           ");
		sql.append(" 	csp.*,                                                                                                       ");
		sql.append(" 	vhipa.ccr,vhipa.ts,                                                                                             ");
		sql.append(" 	vhipa.patient_name,vhipa.discharge_datetime,                                                                                            ");
		sql.append(" 	vhipa.sex,                                                                                                      ");
		sql.append(" 	vhipa.admission_datetime,                                                                                     ");
		sql.append(" 	vhipa.age,                               ");
		sql.append(" 	dhd.dept_name as dept_in,                                                                                                ");
		sql.append(" 	dhd.dept_name as dept_discharge,vhipa.discharge_disposition, vhipa.weight,vhipa.height,vhipa.insurance_no,vhipa.patient_class,                                             ");
		sql.append(" 	vhipa.director,vhipa.alergy_drugs,vhipa.insurance_type,                                                                                           ");
		sql.append(" 	vhipa.attending_doctor,                                                                                       ");
		sql.append(" 	vhipa.doctor_in_charge,                                                                                       ");
		sql.append(" 	vhipa.pat_adm_condition,                                                                                      ");
		sql.append(" 	vhipa.charge_type,vhipa.patient_no,vhipa.d_type,                                                                                            ");
		sql.append(" 	dhi.IDENTITY_NAME as shenfen                                                                                   ");
		sql.append(" from                                                                                                            ");
		sql.append(" 	comment_sample (nolock) cs                                                                                            ");
		sql.append(" 	left join comment_sample_patients (nolock) csp on cs.id = csp.sample_id                                              ");
		/*sql.append(" 	left join his_patient (nolock) hp on hp.patient_id = csp.patient_id                                                  ");
		sql.append(" 	left join his_in_patientvisit (nolock) hipt on ( hipt.patient_id = csp.patient_id and hipt.visit_id = csp.visit_id ) ");*/
		sql.append(" left join V_his_in_patientvisit_all(nolock) vhipa on vhipa.patient_id = csp.patient_id and vhipa.visit_id = csp.visit_id ");
		//sql.append(" 	left join his_in_pats (nolock) hip on ( hip.patient_id = csp.patient_id and hip.visit_id = csp.visit_id ) ");
		sql.append(" left join dict_his_deptment dhd on dhd.p_key=vhipa.dept_in ");
		sql.append(" left join dict_his_deptment dhdc on dhdc.p_key=vhipa.dept_discharge ");
		sql.append(" left join dict_his_identity dhi on dhi.p_key=vhipa.[identity] ");
		//sql.append(" left join dict_his_diagnosis dhdia on hip.diagnosis = dhdia.p_key ");
		//sql.append(" where cs.state=1 and csp.state = 0 ");
		//sql.append(" where cs.state=1 ");
		sql.append(" where 1=1 ");
		if (!params.get("comment_user").equals("CRY0000root")) {
			sql.append(" and (csp.handler = :comment_user or cs.create_user = '"+user().getNo()+"')  ");
		}
		sql.append(" and cs.id = :sample_id ");
		sql.append(" and csp.patient_id = :patient_id ");
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	public int insert(Map<String, Object> params) throws Exception {
		executeInsert(COMMENT_SAMPLE_PATIENTS, params);
		return getSeqVal(COMMENT_SAMPLE_PATIENTS);
	}
	
	public void delete(Map<String, Object> params) throws Exception {
		Record r = new Record();
		r.put("sample_id", params.get("sample_id"));
		if (!CommonFun.isNe(params.get("patient_id"))) {
			r.put("patients_id", params.get("patient_id"));
		}
		executeDelete(COMMENT_SAMPLE_PATIENTS, r);
	}
	
	public void updateByPS(Map<String, Object> params) throws Exception {
		Record r = new Record();
		Record p = new Record();
		r.put("sample_id", params.get("sample_id"));
		r.put("patient_id", params.get("patient_id"));
		p.put("is_active", params.get("is_active"));
		executeUpdate(COMMENT_SAMPLE_PATIENTS, p,r);
	}
	
	public int updateBySamId(Map<String, Object> params) throws Exception {
		Record r = new Record();
		Record p = new Record();
		r.put("sample_id", params.get("sample_id"));
		p.put("is_active", params.get("is_active"));
		return executeUpdate(COMMENT_SAMPLE_PATIENTS, p,r);
	}
	
	public void deleteNotActive(Map<String, Object> params) throws Exception {
		params.put("is_active", "0");
		executeDelete(COMMENT_SAMPLE_PATIENTS, params);
	}
	
	public int update(Map<String, Object> params) throws Exception {
		Record r = new Record();
		r.put("id", params.remove("id"));
		return executeUpdate(COMMENT_SAMPLE_PATIENTS, params,r);
	}

	public Result queryStartSample(Map<String, Object> params) throws Exception {
		StringBuffer sql =new StringBuffer();
		sql.append(" SELECT                                                                                                                          ");
		sql.append(" 	csp.*,                                                                                                                       ");
		sql.append(" 	dbo.GET_PATIENT_XX ( csp.patient_id, csp.visit_id ) AS patient_name,                                                         ");
		sql.append(" 	vhipa.discharge_datetime,                                                                                                     ");
		sql.append(" 	vhipa.admission_datetime,                                                                                                     ");
		sql.append(" 	dhd.dept_name AS dept_in,                                                                                                    ");
		//sql.append(" 	dhdc.dept_name AS dept_discharge,                                                                                            ");
		sql.append(" 	vhipa.director, vhipa.age,                                                                                                              ");
		sql.append(" 	vhipa.attending_doctor,                                                                                                       ");
		sql.append(" 	vhipa.doctor_in_charge,                                                                                                       ");
		sql.append(" 	vhipa.pat_adm_condition,                                                                                                      ");
		sql.append(" 	vhipa.charge_type,vhipa.patient_no,                                                                                                            ");
		//sql.append(" 	dhi.IDENTITY_NAME AS shenfen,                                                                                                 ");
		sql.append(" (select stuff((select ','+DIAGNOSIS_DESC from V_comment_DIAGNOSIS where patient_id=csp.patient_id and visit_id=csp.visit_id group by DIAGNOSIS_DESC for xml path('')),1,1,'')) diagnosis_desc");
		sql.append(" FROM                                                                                                                            ");
		sql.append(" (select patient_id,visit_id from TMP_comment_sample_orders ( nolock ) ");
		sql.append(" where GZID= :GZID	group by patient_id,visit_id)  csp                             ");
		sql.append(" left join V_his_in_patientvisit_all(nolock) vhipa on vhipa.patient_id = csp.patient_id and vhipa.visit_id = csp.visit_id ");
		sql.append(" 	left JOIN dict_his_deptment dhd ON dhd.dept_code = vhipa.dept_in                                                                 ");
		//sql.append(" 	left JOIN dict_his_deptment dhdc ON dhdc.p_key = vhipa.dept_discharge                                                        ");
		//sql.append(" 	LEFT JOIN dict_his_identity dhi ON dhi.p_key = vhipa.[identity]                                                               ");
		return executeQueryLimit(sql.toString(), params);
	}

	/**
	 * 改变抽样中的  分配人
	 * 注意：储存的ID为 v_system_user 的ID
	 * @param map
	 * @author ruiheng
	 * @throws Exception
	 */
	public void updateAssignPatient(Map<String, Object> map) throws Exception  {
		StringBuffer sql = new StringBuffer();
		sql.append(" update                        ");
		sql.append(" a                             ");
		sql.append(" set handler = :comment_user   ");
		sql.append(" from                          ");
		sql.append(" (select top "+map.get("num")+" * from       ");
		sql.append(" comment_sample_patients       ");
		sql.append(" where sample_id = :id         ");
		sql.append(" and handler is null) a        ");
		execute(sql.toString(), map);
	}

	public int updateToDJBH(Map<String, Object> params) throws Exception {
		Record r = new Record();
		Record p = new Record();
		r.put("sample_id", params.remove("id"));
		r.put("handler", params.remove("handler"));
		p.put("djbh", params.remove("djbh"));
		return executeUpdate(COMMENT_SAMPLE_PATIENTS, p,r);
	}
	
	public void updateReject(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" update "+COMMENT_SAMPLE_PATIENTS+" set ");
		sql.append(" djbh= :new_djbh, handler=:comment_user ");
		sql.append(" where djbh=:djbh ");
		execute(sql.toString(), params);
	}

	public Result queryToHandlerGroup(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select handler from     ");
		sql.append(" comment_sample_patients(nolock) "); 
		sql.append(" where                   ");
		sql.append(" sample_id = :id         ");
		sql.append(" GROUP BY handler        ");
		return executeQuery(sql.toString(), params);
	}

	public Record getSampleId(String string) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select sample_id from comment_sample_patients(nolock) where djbh='"+string+"'");
		return executeQuerySingleRecord(sql.toString(), null);
	}

	public void cleanAssignPatient(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" update "+COMMENT_SAMPLE_PATIENTS+" set ");
		sql.append(" handler = null ");
		sql.append(" where sample_id=:id ");
		execute(sql.toString(), params);
	}
}
