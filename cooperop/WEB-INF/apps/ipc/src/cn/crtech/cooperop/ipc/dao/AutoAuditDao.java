package cn.crtech.cooperop.ipc.dao;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.precheck.EngineInterface;

public class AutoAuditDao extends BaseDao {
	private final static String TABLE_NAME = "auto_audit";//HIS患者信息
	private final static String V_TABLE_NAME = "V_auto_audit";//HIS患者信息
	
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select                                                         ");
		setParameter(params, "maxSendNum", " top "+ params.get("maxSendNum"), sql);
		sql.append(" t.id                      ,");
		sql.append(" t.title                   ,");
		sql.append(" t.create_time             ,");
		sql.append(" t.state                   ,");
		sql.append(" t.visit_id                ,");
		sql.append(" t.patient_id              ,");
		sql.append(" t.hospital_id             ,");
		sql.append(" t.advice                  ,");
		sql.append(" t.evaluate                ,");
		sql.append(" t.doctor_no               ,");
		sql.append(" t.yxk_advice              ,");
		sql.append(" t.p_type                  ,");
		sql.append(" t.yaoshi_name             ,");
		sql.append(" t.dianping_sort           ,");
		sql.append(" t.dianping_detail         ,");
		sql.append(" t.d_type                  ,");
		sql.append(" t.pharmacist_exam_time    ,");
		sql.append(" t.common_id               ,");
		sql.append(" t.pharmacist_id           ,");
		sql.append(" t.is_overtime             ,");
		sql.append(" t.is_notice               ,");
		sql.append(" t.is_backtohis            ,");
		sql.append(" t.is_sure                 ,");
		sql.append(" vsu.id as system_user_id, vsu.name doctor_name,hp.patient_name,vsu.department from "+ TABLE_NAME + "(nolock) t");
		sql.append(" left join v_system_user vsu (nolock) on vsu.no=t.doctor_no ");
		sql.append(" left join his_patient(nolock) hp on t.patient_id = hp.patient_id ");
		sql.append(" where 1=1 ");
		setParameter(params, "state", " and t.state=:state", sql);
		setParameter(params, "states", " and (t.state='Y' or t.state='N' or t.state='D')", sql);
		setParameter(params, "is_overtime", " and t.is_overtime=:is_overtime", sql);
		setParameter(params, "is_notice", " and t.is_notice=:is_notice", sql);
		setParameter(params, "is_backtohis", " and t.is_backtohis=:is_backtohis", sql);
		setParameter(params, "doctor_no", " and t.doctor_no=:doctor_no", sql);
		setParameter(params, "uid", " and vsu.id=:uid", sql);
		setParameter(params, "is_sure", " and t.is_sure=:is_sure", sql);
	    return executeQuery(sql.toString(), params);
	}
	
	public Record getYwlsb(String id) throws Exception {
		Record params = new Record();
		params.put("id", id);
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT aa.*,hp.patient_name from YWLSB_AUTO_AUDIT(nolock) aa ");
		sql.append(" left join his_patient(nolock) hp on aa.patient_id = hp.patient_id ");
		sql.append(" where aa.id = :id");
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	public Record get(String id) throws Exception {
		Record params = new Record();
		params.put("id", id);
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT aa.*,hp.patient_name from "+TABLE_NAME+"(nolock) aa ");
		sql.append(" left join his_patient(nolock) hp on aa.patient_id = hp.patient_id ");
		sql.append(" where aa.id = :id");
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	public Record get_audit_def_patient(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from v_audit_def_patient (nolock) ");
		sql.append(" where auto_audit_id = :auto_audit_id ");
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	/**
	 * 视图版
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Record get_view(String id) throws Exception {
		Record params = new Record();
		params.put("id", id);
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT aa.*,hp.patient_name from "+V_TABLE_NAME+"(nolock) aa ");
		sql.append(" left join his_patient(nolock) hp on aa.patient_id = hp.patient_id ");
		sql.append(" where aa.id = :id");
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	/**
	 * 得到传输到云端的auto_audit数据
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Record getTransfer(String id) throws Exception {
		Record params = new Record();
		params.put("id", id);
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT ");
		sql.append(" aa.id,                ");
		sql.append(" aa.title,             ");
		sql.append(" aa.create_time,       ");
		sql.append(" aa.state,             ");
		sql.append(" aa.visit_id,          ");
		sql.append(" aa.patient_id,        ");
		sql.append(" aa.hospital_id,       ");
		sql.append(" aa.advice,            ");
		sql.append(" aa.evaluate,          ");
		sql.append(" aa.doctor_no,         ");
		sql.append(" aa.yxk_advice,        ");
		sql.append(" aa.p_type,            ");
		sql.append(" aa.yaoshi_name,       ");
		sql.append(" aa.dianping_sort,     ");
		sql.append(" aa.dianping_detail,   ");
		sql.append(" aa.d_type,            ");
		sql.append(" aa.common_id,         ");
		sql.append(" aa.pharmacist_id,     ");
		sql.append(" aa.deptment_code,     ");
		sql.append(" aa.deptment_name,     ");
		sql.append(" aa.order_flag,        ");
		sql.append(" aa.auto_audit_time,   ");
		sql.append(" aa.patient_dept_code, ");
		sql.append(" aa.patient_dept_name  ");
		sql.append(" from "+TABLE_NAME+"(nolock) aa ");
		sql.append(" where aa.id = :id");
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	public Record getAuto_audit_id(String id) throws Exception {
		Record params = new Record();
		params.put("id", id);
		StringBuffer sql = new StringBuffer();
		sql.append("select id, state ,msg_alert_id from "+TABLE_NAME+"(nolock) where common_id = :id");
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	/**
	 * 视图版本嘲笑auto_audit_id
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Record getAuto_audit_id_view(String id) throws Exception {
		Record params = new Record();
		params.put("id", id);
		StringBuffer sql = new StringBuffer();
		sql.append("select id, state ,msg_alert_id,d_type,deptment_code,deptment_name from "+V_TABLE_NAME+"(nolock) where common_id = :id");
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	
	public String insert(Map<String, Object> params) throws Exception {
		params.remove("id");
		String id = CommonFun.getITEMID();
		params.put("id", id);
		//params.put("create_time", CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
		executeInsert(TABLE_NAME, params);
		StringBuffer sql = new StringBuffer();
		sql.append("{call pharmacist_audit_result  (:auto_audit_id, '-1')}");
		Record ins = new Record();
		ins.put("auto_audit_id", id);
		Map<String, Integer> outs = new HashMap<String, Integer>();
		executeCall(sql.toString(), ins, outs);
		return id;
	}
	
	public int update(Map<String, Object> params) throws Exception {
		if("Z".equals(params.get("state"))) {
			params.put("is_overtime", "1");
		}
		Record r = new Record();
		Object id = params.remove("id");
		r.put("id", id);
		String state = (String)params.get("state");
		executeUpdate(TABLE_NAME, params, r);
		if(!CommonFun.isNe(params.get("state"))) {
			Record ins = new Record();
			ins.put("auto_audit_id", id);
			Map<String, Object> ipcState= (Map<String, Object>)EngineInterface.mangerL.get("ipc" + state);
			String ipcflag = (String) ipcState.get("sql_record_flag");
			r.put("flag", ipcflag);
			/*if("HL_Y".equals(state)) {
				ins.put("flag", "0");
			}else if ("HL_N".equals(state)) {
				if ("1".equals(params.get("is_overtime"))) {
					ins.put("flag", "2");
				}else {
					ins.put("flag", "8");
				}
			}else if ("HL_T".equals(state)) {
				ins.put("flag", "7");
			}else if ("DB".equals(state)) {
				ins.put("flag", "1");
			}else if ("Y".equals(state)) {
				ins.put("flag", "3");
			}else if ("N".equals(state)) {
				ins.put("flag", "4");
			}else if ("D".equals(state)) {
				ins.put("flag", "9");
			}else if ("DN".equals(state)) {
				ins.put("flag", "6");
			}else if ("DQ".equals(state)) {
				ins.put("flag", "11");
			}else if ("DS".equals(state)) {
				ins.put("flag", "5");
			}else if ("Z".equals(state)) {
				ins.put("flag", "10");
			}else if ("HL_F".equals(state)) {
				ins.put("flag", "-2");
			}else if("DQ1".equals(state)) {
				ins.put("flag", "12");
			}else if("HL_B".equals(state)) {
				ins.put("flag", "13");
			}else{
				ins.put("flag", "-9");
			}*/
			StringBuffer sql = new StringBuffer();
			sql.append(" exec pharmacist_audit_result :id, :flag ");
			execute(sql.toString(), r);
		}
		
		return 1;
	}
	
	//查询病人信息 审查结果页面
	public Record getpatient1(String id) throws Exception {
		Record params = new Record();
		params.put("id", id);
		StringBuffer sql = new StringBuffer();
		sql.append("select ad.id,                                                                                        ");
		sql.append(" ad.PATIENT_ID ,                                                                                    ");
		sql.append(" ad.visit_id,                                                                                       ");
		sql.append(" vhipa.PATIENT_NAME,                                                                                   ");
		sql.append(" vhipa.SEX,                                                                                            ");
		sql.append(" hip.DIAGNOSIS,                                                                                     ");
		sql.append(" vhipa.age,                                  ");
		sql.append(" vhipa.weight,                                                                                       ");
		sql.append(" vhipa.height,                                                                                       ");
		sql.append(" vhipa.alergy_drugs                                                                                  ");
		sql.append(" from v_auto_audit(nolock) ad                                                                         ");
		/*sql.append( " inner join his_patient (nolock) hp on ad.PATIENT_ID=hp.PATIENT_ID                                  ");*/
		sql.append( " left join his_in_pats (nolock) hip on ad.patient_id=hip.PATIENT_ID and ad.VISIT_ID=hip.VISIT_ID    ");
		//sql.append(" left join dict_his_diagnosis dhdia on hip.diagnosis = dhdia.p_key ");
		/*sql.append( " left join  his_in_patientvisit hipt on ad.patient_id=hipt.PATIENT_ID and ad.VISIT_ID=hipt.VISIT_ID ");*/ 
		sql.append(" left join V_his_in_patientvisit_all(nolock) vhipa on vhipa.patient_id = ad.patient_id and vhipa.visit_id = ad.visit_id ");
		sql.append( " where ad.id = :id");
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	/**
	 * 视图版
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Record getpatient1_view(String id) throws Exception {
		Record params = new Record();
		params.put("id", id);
		StringBuffer sql = new StringBuffer();
		sql.append("select ad.id,                                                                                        ");
		sql.append(" ad.PATIENT_ID ,                                                                                    ");
		sql.append(" ad.visit_id,                                                                                       ");
		sql.append(" vhipa.PATIENT_NAME,                                                                                   ");
		sql.append(" vhipa.SEX,                                                                                            ");
		sql.append(" hip.DIAGNOSIS,                                                                                     ");
		sql.append(" vhipa.age,                                  ");
		sql.append(" vhipa.weight,                                                                                       ");
		sql.append(" vhipa.height,                                                                                       ");
		sql.append(" vhipa.alergy_drugs                                                                                  ");
		sql.append(" from v_auto_audit(nolock) ad                                                                         ");
		/*sql.append( " inner join his_patient (nolock) hp on ad.PATIENT_ID=hp.PATIENT_ID                                  ");*/
		sql.append( " left join his_in_pats (nolock) hip on ad.patient_id=hip.PATIENT_ID and ad.VISIT_ID=hip.VISIT_ID    ");
		//sql.append(" left join dict_his_diagnosis dhdia on hip.diagnosis = dhdia.p_key ");
		/*sql.append( " left join  his_in_patientvisit hipt on ad.patient_id=hipt.PATIENT_ID and ad.VISIT_ID=hipt.VISIT_ID ");*/ 
		sql.append(" left join V_his_in_patientvisit_all(nolock) vhipa on vhipa.patient_id = ad.patient_id and vhipa.visit_id = ad.visit_id ");
		sql.append( " where ad.id = :id");
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	//查询医嘱信息
	public Result getyzs(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		//更新为新的
		sql.append("SELECT ")
		    .append("dbo.get_auditorders_GROUPTAG ( :auto_audit_id, group_id,ORDER_NO, ORDER_SUB_NO ) AS zu,                  ")
		    .append("dbo.get_auditorders_REPEATINDICATOR ( :auto_audit_id, group_id,ORDER_NO, ORDER_SUB_NO ) AS REPEAT_INDICATOR,")
		    .append("dbo.get_auditorders_orderclass_NAME ( :auto_audit_id, group_id,ORDER_NO, ORDER_SUB_NO ) AS order_class,       ")
		    .append("dbo.get_auditorders_ENTERDATETIME ( :auto_audit_id, group_id,ORDER_NO, ORDER_SUB_NO ) AS ENTER_DATE_TIME,")
		    .append("dbo.get_auditorders_ADMINISTRATION_NAME ( :auto_audit_id, group_id,ORDER_NO, ORDER_SUB_NO ) AS ADMINISTRATION,")
		    .append("		ads.p_key,                                                                     ")
		    .append("		ads.dosage,                                                                    ")
		    .append("		ads.DOSAGE_UNITS,                                                              ")
		    .append("		ads.FREQUENCY,                                                                 ")
		    .append("		ads.doctor,                                                                    ")
		    .append("		ads.ORDER_TEXT,                                                                ")
		    .append("		ads.ORDER_CODE,                                                                ")
		    .append("		cri.id AS cid,                                                                 ")
		    .append("		cri.level,                                                                     ")
		    .append("		cr.keyword,                                                                    ")
		    .append("		ads.order_no,                                                                  ")
		    .append("		ads.sys_order_status,ads.sys_order_status_name,ads.start_date_time,ads.stop_date_time,ads.p_type ")
		    .append("	FROM                                                                               ")
		    .append("		v_use_orders_outp_lishi (nolock) ads                                                          ")
		    .append("		LEFT JOIN check_result_info(nolock) cri ON ads.auto_audit_id = cri.auto_audit_id       ")
		    .append("		AND cri.order_id+ ',' LIKE '%' + ads.P_KEY+ ',%'                               ")
		    .append("		AND cri.id= :check_result_info_id                                                              ")
		    .append("		LEFT JOIN check_result(nolock) cr ON cr.id = cri.parent_id                             ")
		    .append("	WHERE                                                                              ")
		    .append("		ads.auto_audit_id = :auto_audit_id                                                       ")
		    .append("	ORDER BY                                                                           ")
		    .append("		ads.sys_order_status,                                                              ")
		    .append("		ads.ORDER_NO ,                                                  ")
		    .append("		ads.ORDER_SUB_NO                                                              ");
		return executeQuery(sql.toString(), params);
	}
	
	/**
	 * 视图版
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Result getyzs_view(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		//更新为新的
		sql.append("SELECT ")
		.append("dbo.get_auditorders_GROUPTAG_lishi ( :auto_audit_id, group_id,ORDER_NO, ORDER_SUB_NO ) AS zu,                  ")
		.append("dbo.get_auditorders_REPEATINDICATOR_lishi ( :auto_audit_id, group_id,ORDER_NO, ORDER_SUB_NO ) AS REPEAT_INDICATOR,")
		.append("dbo.get_auditorders_orderclass_NAME_lishi ( :auto_audit_id, group_id,ORDER_NO, ORDER_SUB_NO ) AS order_class,       ")
		.append("dbo.get_auditorders_ENTERDATETIME_lishi ( :auto_audit_id, group_id,ORDER_NO, ORDER_SUB_NO ) AS ENTER_DATE_TIME,")
		.append("dbo.get_auditorders_ADMINISTRATION_NAME_lishi ( :auto_audit_id, group_id,ORDER_NO, ORDER_SUB_NO ) AS ADMINISTRATION,")
		.append("		ads.p_key,                                                                     ")
		.append("		ads.dosage,                                                                    ")
		.append("		ads.DOSAGE_UNITS,                                                              ")
		.append("		ads.FREQUENCY,                                                                 ")
		.append("		ads.doctor,                                                                    ")
		.append("		ads.ORDER_TEXT,                                                                ")
		.append("		ads.ORDER_CODE,                                                                ")
		.append("		cri.id AS cid,                                                                 ")
		.append("		cri.level,                                                                     ")
		.append("		cr.keyword,                                                                    ")
		.append("		ads.order_no,                                                                  ")
		.append("		ads.sys_order_status,ads.sys_order_status_name,ads.start_date_time,ads.stop_date_time,ads.p_type ")
		.append("	FROM                                                                               ")
		.append("		V_use_orders_outp_lishi(nolock) ads                                                          ")
		.append("		LEFT JOIN v_check_result_info(nolock) cri ON ads.auto_audit_id = cri.auto_audit_id       ")
		.append("		AND cri.order_id+ ',' LIKE '%' + ads.P_KEY+ ',%'                               ")
		.append("		AND cri.id= :check_result_info_id                                                              ")
		.append("		LEFT JOIN v_check_result(nolock) cr ON cr.id = cri.parent_id                             ")
		.append("	WHERE                                                                              ")
		.append("		ads.auto_audit_id = :auto_audit_id                                                       ")
		.append("	ORDER BY                                                                           ")
		.append("		ads.sys_order_status,                                                              ")
		.append("		ads.ORDER_NO ,                                                  ")
		.append("		ads.ORDER_SUB_NO                                                              ");
		return executeQuery(sql.toString(), params);
	}
	
	public Result getyzsAll(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ")
		    .append("dbo.get_auditorders_GROUPTAG ( :auto_audit_id,group_id, ORDER_NO, ORDER_SUB_NO ) AS zu,                  ")
		    .append("dbo.get_auditorders_REPEATINDICATOR ( :auto_audit_id,group_id, ORDER_NO, ORDER_SUB_NO ) AS REPEAT_INDICATOR,")
		    .append("dbo.get_auditorders_orderclass_NAME ( :auto_audit_id,group_id, ORDER_NO, ORDER_SUB_NO ) AS order_class,       ")
		    .append("dbo.get_auditorders_ENTERDATETIME ( :auto_audit_id,group_id, ORDER_NO, ORDER_SUB_NO ) AS ENTER_DATE_TIME,")
		    .append("dbo.get_auditorders_ADMINISTRATION_NAME ( :auto_audit_id,group_id, ORDER_NO, ORDER_SUB_NO ) AS ADMINISTRATION,")
		    .append("		ads.p_key,                                                                     ")
		    .append("		CONVERT(FLOAT,ads.dosage) dosage,                                                                    ")
		    .append("		ads.DOSAGE_UNITS,                                                              ")
		    .append("		ads.FREQUENCY,                                                                 ")
		    .append("		ads.doctor,                                                                    ")
		    .append("		ads.ORDER_TEXT,                                                                ")
		    .append("		ads.order_no,                                                                 ")
		    .append("		ads.sys_order_status,ads.order_code,vdd.xiangm,vdd.value xmz                                              ")
		    .append("	FROM                                                                               ")
		    .append("		v_use_orders_outp(nolock) ads                                                          ")
		    .append(" left join DICT_HIS_DRUG_MX vdd (nolock) on ads.order_code=vdd.drug_code and vdd.xiangm = '高危药品'  ")
		    .append("	WHERE                                                                              ")
		    .append("		ads.auto_audit_id = :auto_audit_id                                                       ")
		    .append("	ORDER BY                                                                           ")
		    .append("		ads.sys_order_status,                                                              ")
		    .append("		ads.ORDER_NO ,                                                  ")
		    .append("		ads.ORDER_SUB_NO                                                               ");
		return executeQuery(sql.toString(), params);
	}
	
	/*public int delete(Map<String, Object> params) throws Exception {
		Record r = new Record();
		r.put("id", params.get("id"));
		return executeDelete(TABLE_NAME, r);
	}*/
	
	//病人详情页
	public Record getpatient2(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select a.PATIENT_ID,a.VISIT_ID,vhipa.PATIENT_NAME,vhipa.SEX")                                                                                                                 
		   .append(" ,vhipa.BIRTHDAY, vhipa.age  ")                                                                     
		   .append(" ,vhipa.WEIGHT,vhipa.HEIGHT,dbo.fun_get_ts(vhipa.ADMISSION_DATETIME,vhipa.DISCHARGE_DATETIME) as ts ")                                                                     
		   .append(" ,d.dept_NAME,a.BED_NO,vhipa.CHARGE_TYPE,a.DIAGNOSIS,vhipa.INSURANCE_TYPE,vhipa.INSURANCE_NO" )
		   .append(" ,vhipa.ALERGY_DRUGS,vhipa.ADVERSE_REACTION_DRUGS,e.PatientAdmission_NAME" )
		   .append(" ,f.Adm_condition_NAME,a.ATTENDING_DOCTOR,a.DOCTOR_IN_CHARGE,vhipa.DIRECTOR " )
		   .append(" from ")                                                                     
		   .append(" his_in_pats (nolock) a ")
		   /*.append(" inner join his_in_patientvisit (nolock) b on a.PATIENT_ID=b.PATIENT_ID and a.VISIT_ID=b.VISIT_ID ")                                                                     
		   .append(" inner join his_patient (nolock) c on a.PATIENT_ID=c.PATIENT_ID                                                ")*/                                                                     
		   .append(" left join V_his_in_patientvisit_all(nolock) vhipa on vhipa.patient_id = a.patient_id and vhipa.visit_id = a.visit_id ")
		   .append(" left join hospital_common..dict_his_deptment (nolock) d on a.DEPT_CODE=d.dept_CODE                            ")                                                                     
		   .append(" left join hospital_common..dict_his_PatientAdmission (nolock) e on b.PATIENT_CLASS=e.PatientAdmission_CODE    ")                                                                     
		   .append(" left join hospital_common..dict_his_Adm_condition (nolock) f on b.PAT_ADM_CONDITION=f.Adm_condition_CODE      ")                                                                     
		   .append(" where a.PATIENT_ID = :patient_id and a.VISIT_ID= :visit_id                                                 ");
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	//医嘱信息 病人详情
	public Result queryenjoin(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT ") 
		   .append(" dbo.get_inorders_GROUPTAG (patient_id,VISIT_ID,group_id,ORDER_NO, ORDER_SUB_NO) AS tag,                    ")
		   .append(" dbo.get_inorders_REPEATINDICATOR (patient_id,VISIT_ID,group_id,ORDER_NO, ORDER_SUB_NO) AS REPEAT_INDICATOR,")
		   .append(" dbo.get_inorders_orderclass (patient_id,VISIT_ID,group_id,ORDER_NO, ORDER_SUB_NO) AS order_class,          ")
		   .append(" dbo.get_inorders_ENTERDATETIME (patient_id,VISIT_ID,group_id,ORDER_NO, ORDER_SUB_NO) AS ENTER_DATE_TIME,   ")
		   .append(" dbo.get_inorders_ADMINISTRATION (patient_id,VISIT_ID,group_id,ORDER_NO,ORDER_SUB_NO) AS ADMINISTRATION,    ")
		   .append(" a.START_DATE_TIME,                                                                                ")
		   .append(" a.STOP_DATE_TIME,                                                                                 ")
		   .append(" a.dosage,a.DOSAGE_UNITS, a.FREQUENCY,a.doctor,a.ORDER_TEXT,                                       ")
		   .append(" a.order_no,a.order_sub_no,                                                                        ")
		   .append(" a.sys_order_status,                                                                                   ")
		   .append(" a.FREQ_DETAIL,                                                                                    ")
		   .append(" a.DOCTOR,                                                                                         ")
		   .append(" a.STOP_DOCTOR,                                                                                    ")
		   .append(" a.NURSE                                                                                           ")
		   .append(" from his_in_orders(nolock) a                                                                              ")
		   .append(" where PATIENT_ID = :patient_id and VISIT_ID= :visit_id                                               ")
		   .append(" order by cast(a.ORDER_NO as int),a.ORDER_SUB_NO                                                   ");
		return executeQuery(sql.toString(), params);
	}
	
	//诊断信息  病人详情
	public Result querydiagnos(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select ")
		   .append(" a.DIAGNOSIS_DATE, ")
		   .append(" a.DIAGNOSIS_TYPE,")
		   .append(" b.diagnosisclass_NAME, ")
		   .append(" a.DIAGNOSIS_NO, ")
		   .append(" a.DIAGNOSIS_DESC, ")
		   .append(" a.TREAT_DAYS, ")
		   .append(" TREAT_RESULT ")
		   .append(" from his_in_diagnosis(nolock) a ")
		   .append(" left join hospital_common..dict_his_diagnosisclass (nolock) b on a.DIAGNOSIS_TYPE=b.diagnosisclass_CODE ")
		   .append(" where PATIENT_ID = :patient_id and VISIT_ID= :visit_id ")
		   .append(" order by a.DIAGNOSIS_DATE ");
		return executeQuery(sql.toString(), params);                                                            
	}
	
	//检查信息
	public Result queryexam(Map<String, Object> params) throws Exception {
			StringBuffer sql = new StringBuffer();
			sql.append(" select ")
			   .append(" a.EXAM_NO,")
			   .append(" convert(varchar(19),REQ_DATE_TIME,21) as REQ_DATE_TIME,")
			   .append(" REQ_PHYSICIAN,")
			   .append(" EXAM_CLASS,")
			   .append(" EXAM_SUB_CLASS,")
			   .append(" convert(varchar(19),EXAM_DATE_TIME,21) as EXAM_DATE_TIME,")
			   .append(" REPORTER,")
			   .append(" convert(varchar(19),REPORT_DATE_TIME,21) as REPORT_DATE_TIME")
			   .append(" from his_in_exam_master (nolock) a")
			   .append(" where a.PATIENT_ID = :patient_id and a.VISIT_ID= :visit_id ")
			   .append(" order by a.patient_id,a.visit_id,a.REQ_DATE_TIME,a.EXAM_NO");
			return executeQuery(sql.toString(), params);                                                            
		}

	//检查结果明细
	public Record getexamdetail(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select ")                                                                                                                 
		   .append(" case IS_ABNORMAL when 1 then '阳性' else '阴性' end as IS_ABNORMAL,")                                                                     
		   .append(" EXAM_NO,")                                                                     
		   .append(" DBO.GET_exam_items(EXAM_NO) AS EXAM_ITEM,")                                                                     
		   .append(" DESCRIPTION, ")                                                                     
		   .append(" IMPRESSION,")                                                                     
		   .append(" RECOMMENDATION,")                                                                     
		   .append(" USE_IMAGE")                                                                     
		   .append(" From his_in_exam_report (nolock) ")                                                                     
		   .append(" where EXAM_NO= :exam_no ");
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	//检验信息
	public Result queryrequesten(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select ")
		   .append(" SPECIMEN,")
		   .append(" DBO.GET_TEST_items(TEST_NO) AS ITEM_NAME,")
		   .append(" ORDERING_PROVIDER,")
		   .append(" convert(varchar(19),REQUESTED_DATE_TIME,21) as REQUESTED_DATE_TIME,")
		   .append(" convert(varchar(19),RESULTS_RPT_DATE_TIME,21) as RESULTS_RPT_DATE_TIME,")
		   .append(" a.TEST_NO ")
		   .append(" from his_in_lab_test_master (nolock) a")
		   .append(" where a.PATIENT_ID = :patient_id and a.VISIT_ID= :visit_id ")
		   .append(" order by a.patient_id,a.visit_id,a.REQUESTED_DATE_TIME,a.TEST_NO");
		return executeQuery(sql.toString(), params);                                                            
	}
	
	//检验明细信息
	public Result queryrequestendetail(Map<String, Object> params) throws Exception {
			StringBuffer sql = new StringBuffer();
			sql.append(" select  ")
			   .append(" case when a.ABNORMAL_INDICATOR='N' THEN ''  ")
			   .append("      WHEN a.ABNORMAL_INDICATOR='L' THEN '↓' ")
			   .append("      WHEN a.ABNORMAL_INDICATOR='H' THEN '↑' ")
			   .append("      ELSE '' END AS TAG, ")
			   .append(" a.REPORT_ITEM_NAME, ")
			   .append(" a.result, ")
			   .append(" a.units, ")
			   .append(" a.ABNORMAL_INDICATOR, ")
			   .append(" b.LOWER_LIMIT, ")
			   .append(" b.UPPER_LIMIT, ")
			   .append(" b.PRINT_CONTEXT ")
			   .append(" from his_in_lab_result (nolock) a ")
			   .append(" left join hospital_common..dict_his_lab_report_item (nolock) b ")
			   .append(" on a.REPORT_ITEM_CODE=b.ITEM_CODE ")
			   .append(" where TEST_NO= :test_no ")
			   .append(" order by TEST_NO,cast(ITEM_NO as int) ");
			                                                              
			return executeQuery(sql.toString(), params);       
		}
	
	//体征信息
	public Result queryvital(Map<String, Object> params) throws Exception {
			StringBuffer sql = new StringBuffer();
			sql.append(" select ")
			   .append(" a.VITAL_SIGNS")
			   .append(" from his_in_vital_signs_rec (nolock) a ")
			   .append(" where a.PATIENT_ID = :patient_id and a.VISIT_ID= :visit_id ")
			   .append(" group by a.VITAL_SIGNS ")
			   .append(" order by a.VITAL_SIGNS ");
			return executeQuery(sql.toString(), params);                                                            
	}
	
	//体征明细信息
	public Result queryvitaldetail(Map<String, Object> params) throws Exception {
			StringBuffer sql = new StringBuffer();
			sql.append(" select  ")
			   .append(" convert(varchar(19),RECORDING_DATE,21) as RECORDING_DATE, ")
			   .append(" convert(varchar(19),TIME_POINT,21) as TIME_POINT,  ")
			   .append(" VITAL_SIGNS, ")
			   .append(" VITAL_SIGNS_VALUES, ")
			   .append(" UNITS  ")
			   .append(" from his_in_vital_signs_rec (nolock) a ")
			   .append(" where a.PATIENT_ID = :patient_id and a.VISIT_ID= :visit_id and a.VITAL_SIGNS= :exam_no")		
			   .append(" order by a.patient_id,a.visit_id,a.TIME_POINT,a.VITAL_SIGNS ");
			return executeQuery(sql.toString(), params);       
	}
	
	//手术信息
	public Result queryOperation(Map<String, Object> params) throws Exception {
			StringBuffer sql = new StringBuffer();
			sql.append(" select  ")
			    .append("  OPER_ID, ")
				.append(" DEPT_STAYED, ")
				.append(" DIAG_AFTER_OPERATION, ")
				.append(" OPERATION_SCALE, ")
				.append(" FIRST_ASSISTANT, ")
				.append(" b.dept_NAME as dept, ")
				.append(" b1.dept_NAME as oper_dept, ")
				.append(" a.OPERATOR, ")
				.append(" a.START_DATE_TIME, ")
				.append(" a.END_DATE_TIME  ")
				.append(" from his_in_operation_master (nolock) a ")
				.append(" left join hospital_common..dict_his_deptment (nolock) b on a.DEPT_STAYED=b.dept_CODE ")
				.append(" left join hospital_common..dict_his_deptment (nolock) b1 on a.OPERATING_DEPT=b1.dept_CODE ")
				.append(" where PATIENT_ID = :patient_id and VISIT_ID = :visit_id ");
			return executeQuery(sql.toString(), params);       
	}
	
	//手术详情
	public Result queryOperDetil(Map<String, Object> params) throws Exception {
			StringBuffer sql = new StringBuffer();
			sql.append(" select  ")
				.append(" OPER_ID,OPERATION_NO,OPERATION,c.operation_NAME,a.OPERATION_SCALE,b.woundgrade_NAME ")
			    .append(" from his_in_operation_name (nolock) a ")
			    .append(" left join hospital_common..dict_his_wound_grade (nolock) b on a.WOUND_GRADE=b.woundgrade_CODE ")
			    .append(" left join hospital_common..dict_his_operation (nolock) c on a.OPERATION_CODE=c.operation_CODE ")
				.append(" where PATIENT_ID = :patient_id and VISIT_ID = :visit_id and a.OPER_ID = :oper_id ");
		    return executeQuery(sql.toString(), params);       
	}
	
	//censorship
	public Record censorship(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from sys_common_regulation (nolock) where sort_id=:sort_id and state='Y' and product_code = 'ipc' ") ;                                                                                                                
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	//查询等级给返回结果和查看详情
	public Result queryLevel() throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from DICT_SYS_CHECKLEVEL (nolock)");
		return executeQuery(sql.toString());
	}

	public int insertComment(Map<String, Object> params) throws Exception {
		return executeInsert("pharmacist_comment", params);
	}
	
	public String execFilterNotmind(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("exec check_result_proc :auto_audit_id");
		return execute(sql.toString(),params);
	}
	
	public Record getSingle(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from "+TABLE_NAME+" (nolock) where id = :id ");
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	public Record getAudit(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * , DATEDIFF(ms,create_time,auto_audit_time) as auditTime from "+TABLE_NAME+" (nolock) where common_id = :id ");
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	public Record isEnd(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select state from "+TABLE_NAME+" (nolock) where id = :id ");
		sql.append(" and state in ('Y','N','DN','DS','D') ");
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	//审查串
	public Record getAuditBunch(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * , DATEDIFF(ms,create_time,update_time) checkTime from check_data_parms where auto_audit_id = :auto_audit_id");
		return executeQuerySingleRecord(sql.toString(), params);
	}
	//审查结果
	public Result queryResult(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from v_audit_result_info_show where auto_audit_id = :auto_audit_id");
		return executeQuery(sql.toString(), params);
	}
	public Result procedureTime(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("exec calculate_audit_time_proc :id");
		return executeQuery(sql.toString(), params);
	}
	
	public Result querySendAuditTreah(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT")
		.append(" a.* ")
		.append(" FROM")
		.append(" auto_audit (nolock) a ")
		.append(" WHERE")
		.append(" a.state = :state ")
		.append(" AND (DATEDIFF(ss,a.send_yun_time, CONVERT(VARCHAR(100),getdate(),21)) > :await_time or a.send_yun_time is NULL)")
		.append(" and not exists(select 1 from system_send_error_message (nolock) b  ")
		.append(" where a.id=b.auto_audit_id and b.action='initiate' and b.state<>'1') ");
		return executeQuery(sql.toString(), params);                                                            
	}
	
	public void updateSendTime(Map<String, Object> params) throws Exception {
		execute("update "+TABLE_NAME+" set send_yun_time=CONVERT(VARCHAR(100),getdate(),21) where id=:auto_audit_id ", params);
	}
	
	public void updateMessageEndTime(Map<String, Object> params) throws Exception {
		Record conditions = new Record();
		Record p = new Record();
		conditions.put("id", params.remove("id"));
		conditions.put("hospital_id", params.remove("hospital_id"));
		p.put("message_end_time", CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
		executeUpdate(TABLE_NAME, p, conditions);
	}
}
