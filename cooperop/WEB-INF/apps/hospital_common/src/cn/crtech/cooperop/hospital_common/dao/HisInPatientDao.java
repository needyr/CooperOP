package cn.crtech.cooperop.hospital_common.dao;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;

import java.util.Map;

import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class HisInPatientDao extends BaseDao {
	
	//查询病人信息 审查结果页面
	public Record getpatient1(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select ad.id, ad.cost_time,ad.patient_id ,ad.visit_id,vhipa.patient_name,ad.doctor_no,");
		sql.append(" vhipa.sex, ad.dept_code, vhipa.dept_in, vhipa.patient_no, vhipa.bed_no, ");
		sql.append(" vhipa.age,vhipa.weight,vhipa.height,vhipa.alergy_drugs, dh.dept_name dept_in_name, ad.d_type ");
		sql.append(" from hospital_common..v_auto_common_use(nolock) ad ");
		/*sql.append(" left join his_in_pats(nolock) hip on ad.patient_id=hip.patient_id and ad.visit_id=hip.visit_id ");
		sql.append(" left join his_in_diagnosis(nolock) b on hip.diagnosis=b.diagnosis_code ");*/
		sql.append(" left join v_his_in_patientvisit_all vhipa (nolock) on vhipa.patient_id=ad.patient_id and ad.visit_id=vhipa.visit_id ");
		sql.append(" left join dict_his_deptment dh (nolock) on dh.dept_code = vhipa.dept_in ");
		sql.append(" where ad.id = :id ");
		return executeQuerySingleRecord(sql.toString(), params);
	}
	//病人详情页INTERFACE_TYPE_NAME
	public Record getpatient2(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select vh.ts,vh.blood_type,vh.alergy_drugs,dh.dept_code,dh.dept_name dept_in_name,dhc.dept_name dept_discharge_name ,vh.*,  ");
		sql.append(" vh.charge_type as  charge_type_name,dhp.patientadmission_name patient_class_name, ");
		sql.append(" dhac.adm_condition_name pat_adm_condition_name,dhy.ybzl_name insurance_type_name ");
		sql.append(" from v_his_in_patientvisit_all vh  (nolock)                                            ");
		sql.append(" left join dict_his_deptment dh (nolock) on dh.dept_code = vh.dept_in                    ");
		sql.append(" left join dict_his_deptment dhc (nolock) on dhc.dept_code = vh.dept_discharge           ");
//		sql.append(" left join dict_his_feibie(nolock) dhf on dhf.FEIBIE_CODE=vh.charge_type ");
		sql.append(" left join dict_his_PatientAdmission (nolock) dhp on dhp.PatientAdmission_CODE =vh.patient_class  ");
		sql.append(" left join dict_his_Adm_condition(nolock) dhac on dhac.adm_condition_code=vh.pat_adm_condition ");
		sql.append(" left join DICT_HISYB_YBZL(nolock) dhy on dhy.ybzl_no=vh.insurance_type ");
		//sql.append("select a.*,[dbo].[fun_get_ts](a.ADMISSION_DATETIME,a.DISCHARGE_DATETIME) tss from V_his_in_patientvisit_all(nolock) a ");
		sql.append(" where vh.patient_id = :patient_id and vh.visit_id = :visit_id ");
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	//医保结算页面使用
	public Record getpatient3(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select vh.interface_type_name, vh.claim_type_name, vh.ts,vh.blood_type,vh.alergy_drugs,dh.dept_code,dh.dept_name dept_in_name,dhc.dept_name dept_discharge_name ,vh.*,  ");
		sql.append(" vh.charge_type as  charge_type_name,dhp.patientadmission_name patient_class_name, ");
		sql.append(" dhac.adm_condition_name pat_adm_condition_name,dhy.ybzl_name insurance_type_name ");
		sql.append(" from v_hisyb_in_patientvisit_all vh  (nolock)                                            ");
		sql.append(" left join dict_his_deptment dh (nolock) on dh.dept_code = vh.dept_in                    ");
		sql.append(" left join dict_his_deptment dhc (nolock) on dhc.dept_code = vh.dept_discharge           ");
//			sql.append(" left join dict_his_feibie(nolock) dhf on dhf.FEIBIE_CODE=vh.charge_type ");
		sql.append(" left join dict_his_PatientAdmission (nolock) dhp on dhp.PatientAdmission_CODE =vh.patient_class  ");
		sql.append(" left join dict_his_Adm_condition(nolock) dhac on dhac.adm_condition_code=vh.pat_adm_condition ");
		sql.append(" left join DICT_HISYB_YBZL(nolock) dhy on dhy.ybzl_no=vh.insurance_type ");
		//sql.append("select a.*,[dbo].[fun_get_ts](a.ADMISSION_DATETIME,a.DISCHARGE_DATETIME) tss from V_his_in_patientvisit_all(nolock) a ");
		sql.append(" where vh.patient_id = :patient_id and vh.visit_id = :visit_id ");
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	//医保结算页面使用
	public Record getPatYB(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select interface_type_name, claim_type_name from v_hisyb_in_patientvisit_all ");
		sql.append(" where patient_id = :patient_id and visit_id = :visit_id ");
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	public Record getpatientByYwlsb(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select ad.id, ad.PATIENT_ID ,ad.visit_id,vhipa.PATIENT_NAME,ad.doctor_no,");
		sql.append(" vhipa.SEX,");
		sql.append(" vhipa.age,vhipa.weight,vhipa.height,vhipa.alergy_drugs ");
		sql.append(" from hospital_autopa..YWLSB_AUTO_AUDIT(nolock) ad ");
		/*sql.append(" left join his_in_pats(nolock) hip on ad.patient_id=hip.PATIENT_ID and ad.VISIT_ID=hip.VISIT_ID ");
		sql.append(" left join his_in_diagnosis(nolock) b on hip.diagnosis=b.diagnosis_code ");*/
		sql.append(" left join V_his_in_patientvisit_all vhipa (nolock) on vhipa.patient_id=ad.PATIENT_ID and ad.VISIT_ID=vhipa.VISIT_ID");
		sql.append("  where ad.id = :auto_audit_id");
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	//医嘱信息 病人详情
	public Result queryenjoin(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select a.*,dbo.clearzero(a.dosage) dosage2,c.sys_p_key as frequence from V_DETAILS_ORDER(nolock) a                 ");
		sql.append(" left join dict_his_freq(nolock) c on c.freq_desc = a.frequency ");
		sql.append(" where a.patient_id = :patient_id                   ");
		sql.append(" and a.visit_id = :visit_id                            ");
		//sql.append(" order by a.ORDER_NO,cast(a.order_sub_no as int)   ");
		sql.append(" order by a.enter_date_time desc,a.patient_id,a.order_no,a.order_sub_no,a.group_id ");
		return executeQuery(sql.toString(), params);
	}
	
	//医嘱信息 病人详情
	public Result queryBillType(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select class_code,class_name from dict_his_billitemclass order by class_code ");
		return executeQuery(sql.toString(), params);
	}
	
	// 新患者详情 医嘱 系统表格视图
	public Result queryenjoin2(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select a.*,dbo.clearzero(a.dosage) dosage2,c.sys_p_key as frequence,dhd.drug_code as is_drug from V_DETAILS_ORDER(nolock) a                 ");
		sql.append(" left join dict_his_freq(nolock) c on c.freq_desc = a.frequency ");
		sql.append(" left join dict_his_drug (nolock) dhd on dhd.drug_code=a.ORDER_CODE ");
		sql.append(" where a.patient_id = :patient_id                   ");
		sql.append(" and a.visit_id = :visit_id                            ");
		if(!CommonFun.isNe(params.get("s_time"))){
			sql.append(" and a.enter_date_time >= :s_time");
		}
		if(!CommonFun.isNe(params.get("e_time"))){
			sql.append(" and a.enter_date_time <= :e_time");
		}
		params.put("sort", "a.patient_id,a.order_no,a.order_sub_no,a.group_id,a.enter_date_time desc");
		return executeQueryLimit(sql.toString(), params);
	}
	
	//诊断信息  病人详情
	public Result querydiagnos(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from v_details_diagnosis(nolock) ");
		sql.append(" where patient_id = :patient_id       ");
		sql.append(" and visit_id = :visit_id                ");
		sql.append(" order by diagnosis_date desc ");
	return executeQuery(sql.toString(), params);
}
	
	//新 ygz 诊断信息  患者详情
	public Result queryDia(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from v_details_diagnosis(nolock) ");
		sql.append(" where patient_id = :patient_id       ");
		sql.append(" and visit_id = :visit_id                ");
		if(!CommonFun.isNe(params.get("s_time"))) {
			sql.append(" and diagnosis_date >= :s_time ");
		}
		if(!CommonFun.isNe(params.get("e_time"))) {
			sql.append(" and diagnosis_date <= :e_time ");
		}
		params.put("sort", "diagnosis_date desc");
	return executeQueryLimit(sql.toString(), params);
}
	
	public Result querydiagnosForGroup(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select diagnosis_desc from v_details_diagnosis(nolock) ");
		sql.append(" where patient_id = :patient_id       ");
		sql.append(" and visit_id = :visit_id                ");
		sql.append(" group by diagnosis_desc ");
	return executeQuery(sql.toString(), params);
	}
	
	//检查信息
	public Result queryexam(Map<String, Object> params) throws Exception {
			StringBuffer sql = new StringBuffer();
			sql.append(" select * from v_details_exam_master(nolock) ");
			sql.append(" where patient_id = :patient_id       ");
			sql.append(" and visit_id = :visit_id     ");
		return executeQuery(sql.toString(), params);
	}

	//检查结果明细
	public Result getexamdetail(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from v_details_exam_report(nolock) ");
		sql.append(" where exam_no= :exam_no");
			return executeQueryLimit(sql.toString(), params);
	}
	
	//手术明细
	public Result getOperdetail(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from v_his_in_operation_mx a  ");
		sql.append("where a.PATIENT_ID =:patient_id        ");
		sql.append("and a.VISIT_ID=:visit_id               ");
	    sql.append("and oper_id=:oper_id                   ");
		return executeQuery(sql.toString(), params);
	}
	
	//检验信息
	public Result queryrequesten(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from v_details_test_master(nolock) ");
		sql.append(" where patient_id = :patient_id       ");
		sql.append(" and visit_id = :visit_id                ");
		sql.append(" order by requested_date_time                ");
		return executeQuery(sql.toString(), params);
		                                                        
	}
	
	/**
	 * 检验图表
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Result insp_report(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select result_date_time,report_item_name,result,units"); 
		sql.append(" from his_in_lab_result(nolock)                       ");
		sql.append(" where report_item_code=:report_item_code             ");
		sql.append(" and patient_id=:patient_id and visit_id =:visit_id   ");
		sql.append(" and RESULT is not null ");
		sql.append(" and PATINDEX('%[^0-9.]%', RESULT) = 0 ");
		params.put("sort", "result_date_time");
		return executeQuery(sql.toString(), params);
	}
	
	
	//检验明细信息
	public Result queryrequestendetail(Map<String, Object> params) throws Exception {
			StringBuffer sql = new StringBuffer();
			sql.append(" select a.*,  ");
			sql.append("(select count(1)                          ");
			sql.append("from his_in_lab_result(nolock)            ");
			sql.append("where REPORT_ITEM_CODE=a.REPORT_ITEM_CODE ");
			sql.append("and PATIENT_ID=a.PATIENT_ID               ");
			sql.append("and RESULT is not null ");
			sql.append("and PATINDEX('%[^0-9.]%', RESULT) = 0 ");
			sql.append("and VISIT_ID =a.visit_id) jc_num          ");
			sql.append(" from v_details_test_result(nolock) a");
			sql.append(" where a.test_no = :test_no ");
			sql.append(" order by a.test_no,a.item_no  "); 
			return executeQuery(sql.toString(), params);
		}
	
	//体征信息
	public Result queryvital(Map<String, Object> params) throws Exception {
			StringBuffer sql = new StringBuffer();
			sql.append(" select vital_signs from v_details_vital_signs_rec(nolock) ");
			sql.append(" where patient_id = :patient_id       ");
			sql.append(" and visit_id = :visit_id group by vital_signs  ");
		return executeQuery(sql.toString(), params);
	}
	
	/**
	 * 体征图表
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Result sig_report(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from                            "); 
		sql.append("his_in_vital_signs_rec (nolock)          "); 
		sql.append("where PATIENT_ID = :patient_id           "); 
		sql.append("and visit_id = :visit_id                 "); 
		sql.append("and VITAL_SIGNS = :vital_signs           "); 
		sql.append("and PATINDEX('%[^0-9.]%', VITAL_SIGNS_VALUES) = 0 "); 
		sql.append("and VITAL_SIGNS_VALUES is not null       ");
		params.put("sort", "[recording_date],time_point");
		return executeQuery(sql.toString(), params);
	}
	
	//体征明细信息
	public Result queryvitaldetail(Map<String, Object> params) throws Exception {
			StringBuffer sql = new StringBuffer();
			sql.append(" select  ")
			   .append(" convert(varchar(19),a.recording_date,21) as recording_date, ")
			   .append(" convert(varchar(19),a.time_point,21) as time_point,  ")
			   .append(" a.vital_signs, ")
			   .append(" a.vital_signs_values, ")
			   .append(" a.units,  ")
			   .append(" (select count(1) from ")
			   .append(" his_in_vital_signs_rec (nolock)  ")
			   .append(" where PATIENT_ID = a.PATIENT_ID  ")
			   .append(" and visit_id = a.visit_id      ")
			   .append("and VITAL_SIGNS = a.vital_signs      ")
			   .append(" and PATINDEX('%[^0-9.]%', VITAL_SIGNS_VALUES) = 0  ")
			   .append(" and VITAL_SIGNS_VALUES is not null) tz_num  ")
			   .append(" from his_in_vital_signs_rec(nolock) a ")
			   .append(" where a.patient_id = :patient_id and a.visit_id= :visit_id and a.vital_signs= :exam_no");	
				params.put("sort", "[recording_date],time_point");
				return executeQueryLimit(sql.toString(), params);
	}
	
	//手术信息
	@Deprecated
	public Result queryOperation(Map<String, Object> params) throws Exception {
			StringBuffer sql = new StringBuffer();
			sql.append(" select * from v_details_operation_master(nolock) ");
			sql.append(" where patient_id = :patient_id       ");
			sql.append(" and visit_id = :visit_id                ");
		return executeQuery(sql.toString(), params);
	}
	
	//手术信息
	public Result queryOperation2(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from v_his_in_operation a ");
		sql.append("where a.PATIENT_ID =:patient_id    ");
		sql.append("and a.VISIT_ID=:visit_id           ");
		return executeQuery(sql.toString(), params);
	}
	
	//手术详情
	public Result queryOperDetil(Map<String, Object> params) throws Exception {
			StringBuffer sql = new StringBuffer();
			sql.append(" select * from v_details_operation_name (nolock)            ");
			sql.append(" where patient_id = :patient_id                    ");
			sql.append(" and visit_id = :visit_id and oper_id = :oper_id ");
		return executeQuery(sql.toString(), params);
	}
	
	
	//审查结果主界面手术信息
	public Result detailOpers(Map<String, Object> params) throws Exception {
			StringBuffer sql = new StringBuffer();
			sql.append(" select * from v_details_operation_name(nolock)            ");
			sql.append(" where patient_id = :patient_id                    ");
			sql.append(" and visit_id = :visit_id  ");
		    return executeQuery(sql.toString(), params);       
	}
	//censorship
	public Record censorship(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from apa_check_sorts(nolock) where sort_id=:sort_id and state='Y'") ;                                                                                                                
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	//censorship
	public Record wait_time(Map<String, Object> params) throws Exception {
			StringBuffer sql = new StringBuffer();
			sql.append("select * from system_config (nolock) where code = 'wait_time' and is_open = 'Y'  ") ;                                                                                                                 
			return executeQuerySingleRecord(sql.toString(), params);
	}
	//查询等级给返回结果和查看详情
	public Result queryLevel() throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from DICT_SYS_CHECKLEVEL (nolock)");
		return executeQuery(sql.toString());
	}
	
	/*
	 * 患者审查详情 模块——————————————————————————————————————————————————————————————
	 *
	 */
	public Result queryUseDrug(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select *,dbo.clearzero(dosage) dosage2 from v_ys_details_order(nolock)                   ");
		sql.append(" where patient_id = :patient_id and visit_id = :visit_id        ");
		sql.append(" order by ENTER_DATE_TIME desc,patient_id,visit_id,group_id,order_no,order_sub_no ");
		return executeQuery(sql.toString(), params);
	}
	
	public Result queryPatDiagnosis(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from v_ys_details_diagnosis(nolock)                   ");
		sql.append(" where patient_id = :patient_id and visit_id = :visit_id        ");
		return executeQuery(sql.toString(), params);
	}
	
	public Result queryPatDiagnosisGroup(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select diagnosis_desc from v_ys_details_diagnosis(nolock)              ");
		sql.append(" where patient_id = :patient_id and visit_id = :visit_id        ");
		sql.append(" group by  diagnosis_desc ");
		return executeQuery(sql.toString(), params);
	}
	
	public Result queryPatExam_Master(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from v_ys_details_exam_master(nolock)                   ");
		sql.append(" where patient_id = :patient_id and visit_id = :visit_id        ");
		return executeQuery(sql.toString(), params);
	}
	
	public Result queryPatRequesten(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from v_ys_details_test_master(nolock)                   ");
		sql.append(" where patient_id = :patient_id and visit_id = :visit_id        ");
		sql.append(" order by requested_date_time                ");
		return executeQuery(sql.toString(), params);
	}
	
	public Result queryPatVital(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select vital_signs from v_ys_details_vital_signs_rec(nolock)                   ");
		sql.append(" where patient_id = :patient_id and visit_id = :visit_id group by vital_signs       ");
		return executeQuery(sql.toString(), params);
	}
	
	public Result queryPatExam_Detail(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from v_ys_details_exam_report  (nolock)                 ");
		sql.append(" where exam_no= :exam_no");
		return executeQuery(sql.toString(), params);
	}
	
	public Result queryPatRequesten_Detail(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from v_ys_details_test_result      (nolock)             ");
		sql.append(" where test_no = :test_no ");
		sql.append(" order by test_no,item_no  "); 
		return executeQuery(sql.toString(), params);
	}
	
	public Result queryPatVital_Detail(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select  ");
		sql.append(" convert(varchar(19),recording_date,21) as recording_date, ");
		sql.append(" convert(varchar(19),time_point,21) as time_point,  ");
		sql.append(" vital_signs, ");
		sql.append(" vital_signs_values, ");
		sql.append(" units  ");
		sql.append(" from v_ys_details_vital_signs_rec(nolock) a ");
		sql.append(" where a.patient_id = :patient_id and a.visit_id= :visit_id and a.vital_signs= :exam_no");	
		sql.append(" order by [recording_date],time_point ");
		return executeQuery(sql.toString(), params);
	}
	
	public Result queryPatCheckRunInfo(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from v_ys_details_order_check  (nolock)  ");
		sql.append(" where patient_id= :patient_id and visit_id= :visit_id ");
		sql.append(" and order_no= :order_no                  ");
		return executeQuery(sql.toString(), params);
	}
	
	public Result queryPatCheckResult(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" 	select                                                                             ");  
		sql.append(" 	vyd.*,dsc.sys_check_level_name,dsc.star_level,crs.sort_name                        ");  
		sql.append(" from                                                                                  ");  
		sql.append(" 	v_ys_details_order_check_result vyd   (nolock)                                             ");  
		sql.append(" 	left join dict_sys_checklevel dsc (nolock) on dsc.sys_check_level = vyd.auto_audit_level    ");  
		sql.append(" 	left join check_result_sort crs  (nolock)                                                  ");  
		sql.append(" 	on ( crs.type = vyd.auto_audit_sort_id and crs.check_type = vyd.auto_audit_type )  ");  
		sql.append(" where patient_id= :patient_id and visit_id= :visit_id and order_no= :order_no ");
		sql.append(" order by check_datetime ");
		return executeQuery(sql.toString(), params);
	}
	
	public Result queryPatCommentResult(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from v_ys_details_order_check_comment (nolock) ");
		sql.append(" where patient_id= :patient_id and visit_id= :visit_id and order_no= :order_no ");
		sql.append(" order by comment_datetime,comment_username desc ");
		return executeQuery(sql.toString(), params);
	}
	
	public Record queryPatInfo(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select                                                                  ");
		sql.append(" 	vhipa.*,dhd.dept_name AS dep_in,                                     ");
		sql.append(" 	dhd.dept_name AS dep_discharge,                                     ");
		sql.append(" 	dhi.identity_name AS shenfen                                     ");
		sql.append(" from                                                                    ");
		sql.append(" 	v_his_in_patientvisit_all ( nolock ) vhipa                           ");
		sql.append(" 	left join dict_his_deptment(nolock) dhd on dhd.p_key= vhipa.dept_in          ");
		sql.append(" 	left join dict_his_deptment(nolock) dhdc on dhdc.p_key= vhipa.dept_discharge ");
		sql.append(" 	left join dict_his_identity(nolock) dhi on dhi.p_key= vhipa.[identity]       ");
		sql.append(" where                                                                   ");
		sql.append(" 	vhipa.patient_id  = :patient_id                                      ");
		sql.append(" 	and vhipa.visit_id = :visit_id                                       ");
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	//获取收费信息
	public Result queryBillItems(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select a.*,dhd.drug_code as is_drug from v_details_bill_detail  (nolock) a ");
		sql.append("left join dict_his_drug (nolock) dhd on a.ITEM_CODE=dhd.drug_code ");
		//visit_id采用明文方式，避免默认将visit_id理解为int型，造成排序后每次的显示顺序不一致
		sql.append(" where a.patient_id = :patient_id and a.visit_id = '"+params.get("visit_id")+"' ");
		if(!CommonFun.isNe(params.get("s_time"))) {
			sql.append(" and a.billing_date_time >= :s_time ");
		}
		if(!CommonFun.isNe(params.get("e_time"))) {
			sql.append(" and a.billing_date_time <= :e_time ");
		}
		if(!CommonFun.isNe(params.get("class_name"))) {
			sql.append(" and a.order_class_name = :class_name ");
		}
		if(!CommonFun.isNe(params.get("item_name"))) {
			params.put("item_name", "%" +params.get("item_name")+ "%");
			sql.append(" and a.item_name like :item_name ");
		}
		if(CommonFun.isNe(params.get("sort"))) {
			params.put("sort", " billing_date_time desc");
		}else {
			params.put("sort", " billing_date_time desc, " + params.get("sort"));
		}
		
		
		if(CommonFun.isNe(params.remove("qall"))) {
			return executeQueryLimit(sql.toString(), params);
		}else {
			return executeQuery(sql.toString(), params);
		}
		
	}
	
	//获取收费消息： 包含转自费标识
	public Result queryBillItemsSP(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select a.*, b.state zzf_state,dhd.drug_code as is_drug from v_details_bill_detail (nolock) a ");
		sql.append(" left join hospital_imic..YWLSB_ITEM_YBZZFJL (nolock) b ");
		sql.append(" on a.patient_id = b.patient_id and a.visit_id = b.visit_id ");
		sql.append(" and a.order_no = b.order_no and a.group_id = b.group_id ");
		sql.append(" and a.order_sub_no = b.order_sub_no ");
		sql.append(" left join dict_his_drug (nolock) dhd on a.ITEM_CODE=dhd.drug_code ");
		//visit_id采用明文方式，避免默认将visit_id理解为int型，造成排序后每次的显示顺序不一致
		sql.append(" where a.patient_id = :patient_id and a.visit_id = '"+params.get("visit_id")+"' ");
		if(!CommonFun.isNe(params.get("s_time"))) {
			sql.append(" and a.billing_date_time >= :s_time ");
		}
		if(!CommonFun.isNe(params.get("e_time"))) {
			sql.append(" and a.billing_date_time <= :e_time ");
		}
		if(!CommonFun.isNe(params.get("class_name"))) {
			sql.append(" and a.order_class_name = :class_name ");
		}
		if(!CommonFun.isNe(params.get("item_name"))) {
			params.put("item_name", "%" +params.get("item_name")+ "%");
			sql.append(" and a.item_name like :item_name ");
		}
		if(CommonFun.isNe(params.get("sort"))) {
			params.put("sort", " a.billing_date_time desc");
		}else {
			params.put("sort", " a.billing_date_time desc, " + params.get("sort"));
		}
		if(CommonFun.isNe(params.remove("qall"))) {
			return executeQueryLimit(sql.toString(), params);
		}else {
			return executeQuery(sql.toString(), params);
		}
		
	}
	
	//患者诊断信息
	public Result queryDiagnosis(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select diagnosis_desc,sys_diagnosis_type diagnosis_type from TMP_his_in_diagnosis (nolock)");
		sql.append(" where patient_id= :patient_id and visit_id= :visit_id");
		sql.append(" group by diagnosis_desc,sys_diagnosis_type ");
		return executeQuery(sql.toString(), params);
	}
	
	//患者诊断事后查看信息
	public Result queryDiagnosisAll(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select a.diagnosis_desc,c.diagnosisclass_CODE diagnosis_type from his_in_diagnosis (nolock) a ");
		sql.append("left join dict_his_diagnosisclass(nolock) b on a.diagnosis_type=b.diagnosisclass_CODE ");
		sql.append("left join dict_sys_diagnosisclass(nolock) c on c.P_KEY=b.SYS_P_KEY ");
		sql.append(" where patient_id= :patient_id and visit_id= :visit_id");
		sql.append(" group by a.diagnosis_desc,c.diagnosisclass_CODE ");
		return executeQuery(sql.toString(), params);
	}
	
	public Record getPatAllInfo(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from v_hisyb_in_patientvisit_all (nolock) where patient_id = :patient_id and visit_id= :visit_id ");
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	public void execPatInit(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" exec JCR_his_in_TMP '','',:patient_id,:visit_id ");
		execute(sql.toString(), params);
	}
}
