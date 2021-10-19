package cn.crtech.precheck.ipc.dao;

import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;

public class DataDao extends BaseDao {
	
	private static final String V_USE_ORDERS_OUTP_PROC="v_use_orders_outp_proc";
	
	//院内自定义审查的存储过程运行环境
	private static final String SCR_SHENGFANG_EXEPROC="SCR_SHENGFANG_EXEPROC";
	//系统自定义审查的存储过程运行环境
	private static final String SYS_SHENGFANG_EXEPROC="SYS_SHENGFANG_EXEPROC";
	
	//院内自定义审查的存储过程
	public static final String proc1="SCR_SHENGFANG_EXEPROC1";
	public static final String proc2="SCR_SHENGFANG_EXEPROC2";
	public static final String proc3="SCR_SHENGFANG_EXEPROC3";
	public static final String proc4="SCR_SHENGFANG_EXEPROC4";
	public static final String proc5="SCR_SHENGFANG_EXEPROC5";
	//public static final String proc7="SCR_SHENGFANG_EXEPROC7";
	
	//点评调用, 院内自定义审查的存储过程
	public static final String pr_proc1="SCR_SHENGFANG_EXEPROC1";
	public static final String pr_proc2="SCR_SHENGFANG_EXEPROC2";
	public static final String pr_proc3="SCR_SHENGFANG_EXEPROC3";
	public static final String pr_proc4="SCR_SHENGFANG_EXEPROC4";
	public static final String pr_proc5="SCR_SHENGFANG_EXEPROC5";
	
	//系统自定义审查的存储过程
	public static final String sproc1="SYS_SHENGFANG_EXEPROC1";
	public static final String sproc2="SYS_SHENGFANG_EXEPROC2";
	public static final String sproc3="SYS_SHENGFANG_EXEPROC3";
	public static final String sproc4="SYS_SHENGFANG_EXEPROC4";
	public static final String sproc5="SYS_SHENGFANG_EXEPROC5";
	
	//点评调用, 系统自定义审查的存储过程
	public static final String pr_sproc1="SYS_SHENGFANG_EXEPROC1";
	public static final String pr_sproc2="SYS_SHENGFANG_EXEPROC2";
	public static final String pr_sproc3="SYS_SHENGFANG_EXEPROC3";
	public static final String pr_sproc4="SYS_SHENGFANG_EXEPROC4";
	public static final String pr_sproc5="SYS_SHENGFANG_EXEPROC5";
	
	
	/**
	 *	查询医生
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Record getDoctor(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select d.*,'' as duty, ");
		sql.append("p.dept_NAME as departName ,d.USER_DEPT as departCode from dict_his_users(nolock) d ");
		sql.append("left join dict_his_deptment(nolock) p on p.dept_CODE=d.USER_DEPT ");
		sql.append(" where 1=1 ");
		setParameter(params, "doctor_no", " and d.user_id=:doctor_no", sql);
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	/**
	 * 查询病人信息
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Record getPatientForHLYY(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select p.*,d.dept_NAME ");
		sql.append("from V_his_in_pats(nolock) p  ");
		sql.append("left join dict_his_deptment(nolock) d on d.dept_CODE = p.DEPT_CODE ");
		sql.append(" where 1=1 ");
		setParameter(params, "patient_id", " and p.PATIENT_ID=:patient_id", sql);
		setParameter(params, "visit_id", " and p.visit_id=:visit_id", sql);
		return executeQuerySingleRecord(sql.toString(), params);
	}
	/**
	 * 查询在用的医嘱和处方信息
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Result queryUseOrders(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select ");
		sql.append(" p_key,patient_id,visit_id,group_id,order_no,order_sub_no,repeat_indicator,          ");
		sql.append(" order_class,order_text,order_code,ordering_dept,doctor,order_status,sys_order_status,start_date_time,   ");
		sql.append(" stop_date_time,dosage,dosage_units,administration,frequency,                           ");
		sql.append(" freq_counter,freq_interval,freq_interval_unit,freq_detail,                             ");
		sql.append(" perform_schedule,stop_doctor,nurse,stop_nurse,enter_date_time,                         ");
		sql.append(" medicate_cause,dept_code,dept_name,doctor_no,                         ");
		sql.append(" p_type,ordering_deptname,shpgg,shl,dw,beizhu                                   ");
		sql.append(" from V_his_in_orders(nolock)   ");
		sql.append(" where 1=1 ");
		setParameter(params, "patient_id", " and PATIENT_ID=:patient_id", sql);
		setParameter(params, "visit_id", " and visit_id=:visit_id", sql);
		return executeQuery(sql.toString(), params);
	}

	/**
	 * 查询待审查的医嘱和处方信息
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Result queryOrdersForHLYY(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		//该存储过程会访问链接服务器，如没有链接服务器，请修改存储过程
		sql.append("{ call " + V_USE_ORDERS_OUTP_PROC + "(:auto_audit_id)}");
		Record ins = new Record();
		ins.put("auto_audit_id", params.get("auto_audit_id"));
		return executeCallQuery(sql.toString(), ins);
	}
	/**
	 * 诊断查询,为了减少智能审查时间，提高审查效率，
	 * 将审查诊断套表数据在发送药师审查的时候才初始化，合理用药审查的时候先不初始化
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Result queryDiagnosisForHLYY(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select p.* ");
		sql.append("from TMP_his_in_diagnosis(nolock) p  ");
		sql.append(" where 1=1 ");
		setParameter(params, "patient_id", " and p.patient_id=:patient_id", sql);
		setParameter(params, "visit_id", " and p.visit_id=:visit_id", sql);
		return executeQuery(sql.toString(), params);
	}
	/**
	 * 手术信息查询
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Result queryOpsInfoForHLYY(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select mx.*,                                           ");
		sql.append("isnull(CONVERT(varchar(19), hz.start_date_time, 20),'')"); 
		sql.append("as start_date_time,                                    ");
		sql.append("isnull(CONVERT(varchar(19), hz.end_date_time, 20),'')  ");
		sql.append("as end_date_time                                       ");
		sql.append(" from TMP_his_in_operation_name(nolock) mx ");
		sql.append(" left join TMP_his_in_operation_master(nolock) hz");
		sql.append(" on hz.oper_id = mx.oper_id and hz.patient_id=mx.patient_id and hz.visit_id=mx.visit_id");
		sql.append(" where mx.patient_id=:patient_id and mx.visit_id=:visit_id");
		return executeQuery(sql.toString(), params);
	}
	/**
	 * 过敏信息查询，查询病人表中his_in_patientvisit的数据
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Result queryAllergyInfoForHLYY(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select p.* ");
		sql.append("from TMP_his_in_alergy_drugs(nolock) p  ");
		sql.append(" where 1=1 ");
		setParameter(params, "patient_id", " and p.PATIENT_ID=:patient_id", sql);
		setParameter(params, "visit_id", " and p.visit_id=:visit_id", sql);
		return executeQuery(sql.toString(), params);
	}
	/**
	 * 病生理信息查询
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Result queryPhysiologyInfoForHLYY(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select p.* ");
		sql.append("from TMP_his_in_PhysiologyInfo(nolock) p  ");
		sql.append(" where 1=1 ");
		setParameter(params, "patient_id", " and p.PATIENT_ID=:patient_id", sql);
		setParameter(params, "visit_id", " and p.visit_id=:visit_id", sql);
		return executeQuery(sql.toString(), params);
	}
	public Record getPatient(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select  ");
		sql.append(" P_KEY             ,");
		sql.append(" PATIENT_ID        ,");
		sql.append(" PATIENT_NAME      ,");
		sql.append(" PYM               ,");
		sql.append(" SEX               ,");
		sql.append(" BIRTHDAY          ,");
		sql.append(" ID_NO             ,");
		sql.append(" [IDENTITY]          ,");
		sql.append(" CHARGE_TYPE       ,");
		sql.append(" PATIENT_NO        ,");
		sql.append(" BIRTH_PLACE       ,");
		sql.append(" NATION            ,");
		sql.append(" nationality       ,");
		sql.append(" Postaladdress     ,");
		sql.append(" ZIP_CODE          ,");
		sql.append(" PHONE_HOME        ,");
		sql.append(" PHONE_BUSINESS    ,");
		sql.append(" Contacts          ,");
		sql.append(" PHONE_Contacts    ,");
		sql.append(" RELATIONSHIP      ,");
		sql.append(" CREATETIME        ");
		sql.append(" from his_patient(nolock) where 1=1 ");
		setParameter(params, "patient_id", " and patient_id=:patient_id", sql);
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	public Record getInPatient(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select  ");
		sql.append(" P_KEY              , ");
		sql.append(" PATIENT_ID         , ");
		sql.append(" VISIT_ID           , ");
		sql.append(" ADMISSION_DATETIME , ");
		sql.append(" DIAGNOSIS          , ");
		sql.append(" WARD_CODE          , ");
		sql.append(" DEPT_CODE          , ");
		sql.append(" BED_NO             , ");
		sql.append(" ADM_WARD_DATETIME  , ");
		sql.append(" PATIENT_CONDITION  , ");
		sql.append(" NURSING_CLASS      , ");
		sql.append(" DOCTOR_IN_CHARGE   , ");
		sql.append(" ATTENDING_DOCTOR   , ");
		sql.append(" PREPAYMENTS         ");
		sql.append(" from his_in_pats(nolock) where 1=1 ");
		setParameter(params, "patient_id", " and patient_id=:patient_id", sql);
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	public Result queryPatientVisit(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select  ");
		sql.append(" P_KEY                   , ");
		sql.append(" FK                      , ");
		sql.append(" PATIENT_ID              , ");
		sql.append(" VISIT_ID                , ");
		sql.append(" DEPT_IN                 , ");
		sql.append(" ADMISSION_DATETIME      , ");
		sql.append(" [IDENTITY]                , ");
		sql.append(" CHARGE_TYPE             , ");
		sql.append(" DISCHARGE_DISPOSITION   , ");
		sql.append(" DEPT_DISCHARGE          , ");
		sql.append(" DISCHARGE_DATETIME      , ");
		sql.append(" OCCUPATION              , ");
		sql.append(" MARITAL_STATUS          , ");
		sql.append(" ARMED_SERVICES          , ");
		sql.append(" DUTY                    , ");
		sql.append(" WEIGHT                  , ");
		sql.append(" HEIGHT                  , ");
		sql.append(" INSURANCE_TYPE          , ");
		sql.append(" INSURANCE_NO            , ");
		sql.append(" Work_unit               , ");
		sql.append(" TOP_UNIT                , ");
		sql.append(" MAILING_ADDRESS         , ");
		sql.append(" ZIP_CODE                , ");
		sql.append(" PATIENT_CLASS           , ");
		sql.append(" ADMISSION_CAUSE         , ");
		sql.append(" PAT_ADM_CONDITION       , ");
		sql.append(" BLOOD_TYPE              , ");
		sql.append(" ALERGY_DRUGS            , ");
		sql.append(" ADVERSE_REACTION_DRUGS  , ");
		sql.append(" INFUSION_REACT_TIMES    , ");
		sql.append(" HBSAG_INDICATOR         , ");
		sql.append(" HCV_AB_INDICATOR        , ");
		sql.append(" HIV_AB_INDICATOR        , ");
		sql.append(" BLOOD_TRAN_TIMES        , ");
		sql.append(" BLOOD_TRAN_REACT_TIMES  , ");
		sql.append(" DIRECTOR                , ");
		sql.append(" ATTENDING_DOCTOR        , ");
		sql.append(" DOCTOR_IN_CHARGE        , ");
		sql.append(" INSURANCE_IDENTIFICATION, ");
		sql.append(" UNIT_IN_CONTRACT        , ");
		sql.append(" CREATETIME               ");
		sql.append(" from his_in_patientvisit(nolock) where 1=1 ");
		setParameter(params, "patient_id", " and patient_id=:patient_id", sql);
		return executeQuery(sql.toString(), params);
	}
	public Result queryDoctors(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from dict_his_users(nolock) where 1=1 ");
		return executeQuery(sql.toString(), params);
	}
	public Result queryDeps(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from dict_his_deptment(nolock) where 1=1 ");
		return executeQuery(sql.toString(), params);
	}
	public Record getUser(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from system_doctor(nolock) where 1=1 and id=:id");
		return executeQuerySingleRecord(sql.toString(), params);
	}
	public void updateUser(Map<String, Object> params) throws Exception {
		executeUpdate("system_doctor", params);
	}
	public void insertUser(Map<String, Object> params) throws Exception {
		executeInsert("system_doctor", params);
	}
	
	public Result queryDiagnosisForYXK(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		//sql.append("select * from auto_audit_diagnosis(nolock) where 1=1 ");
		sql.append("select ");
		sql.append("id,                  ");
		sql.append("PATIENT_ID,          ");
		sql.append("VISIT_ID,            ");
		sql.append("DIAGNOSIS_DATE,      ");
		sql.append("DIAGNOSIS_TYPE,      ");
		sql.append("diagnosisclass_NAME, ");
		sql.append("DIAGNOSIS_NO,        ");
		sql.append("DIAGNOSIS_DESC,      ");
		sql.append("CLIN_DIAG,           ");
		sql.append("TREAT_DAYS,          ");
		sql.append("TREAT_RESULT,        ");
		sql.append("lasttime,            ");
		sql.append("auto_audit_id        ");
		sql.append(" from auto_audit_diagnosis(nolock) where 1=1 ");
		setParameter(params, "auto_audit_id", " and auto_audit_id=:auto_audit_id", sql);
		return executeQuery(sql.toString(), params);
	}
	public Result queryLABYXK(Map<String, Object> params) throws Exception {
		StringBuffer sql2 = new StringBuffer();
		//sql2.append("select * from auto_audit_exam_master(nolock) p where 1=1 ");
		sql2.append("select ");
		sql2.append("id,              ");
		sql2.append("PATIENT_ID,      ");
		sql2.append("VISIT_ID,        ");
		sql2.append("EXAM_NO,         ");
		sql2.append("REQ_DATE_TIME,   ");
		sql2.append("REQ_PHYSICIAN,   ");
		sql2.append("EXAM_CLASS,      ");
		sql2.append("EXAM_SUB_CLASS,  ");
		sql2.append("CLIN_DIAG,       ");
		sql2.append("EXAM_DATE_TIME,  ");
		sql2.append("REPORTER,        ");
		sql2.append("REPORT_DATE_TIME,");
		sql2.append("LASTTIME,        ");
		sql2.append("auto_audit_id    ");
		sql2.append(" from auto_audit_exam_master(nolock) p where 1=1 ");
		setParameter(params, "auto_audit_id", " and auto_audit_id=:auto_audit_id", sql2);
		return executeQuery(sql2.toString(), params);
	}
	public Result queryLABResultYXK(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		//sql.append("select * from auto_audit_exam_report(nolock) where 1=1  ");
		sql.append("select ");
		sql.append("id,            ");
		sql.append("auto_audit_id, ");
		sql.append("EXAM_NO,       ");
		sql.append("IS_ABNORMAL,   ");
		sql.append("EXAM_ITEM,     ");
		sql.append("DESCRIPTION,   ");
		sql.append("IMPRESSION,    ");
		sql.append("RECOMMENDATION,");
		sql.append("USE_IMAGE,     ");
		sql.append("lasttime       ");
		sql.append(" from auto_audit_exam_report(nolock) where 1=1  ");
		setParameter(params, "auto_audit_id", " and auto_audit_id=:auto_audit_id", sql);
		return executeQuery(sql.toString(), params);
	}
	public Result queryEXAMYXK(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		//sql.append("select * from auto_audit_lab_test_master(nolock) a  ");
		sql.append("select ");
		sql.append("a.id,                   ");
		sql.append("a.PATIENT_ID,           ");
		sql.append("a.VISIT_ID,             ");
		sql.append("a.TEST_NO,              ");
		sql.append("a.SPECIMEN,             ");
		sql.append("a.ITEM_NAME,            ");
		sql.append("a.ORDERING_PROVIDER,    ");
		sql.append("a.REQUESTED_DATE_TIME,  ");
		sql.append("a.RESULTS_RPT_DATE_TIME,");
		sql.append("a.lasttime,             ");
		sql.append("a.auto_audit_id         ");
		sql.append(" from auto_audit_lab_test_master(nolock) a  ");
		sql.append(" where 1=1  ");
		setParameter(params, "auto_audit_id", " and auto_audit_id=:auto_audit_id", sql);
		return executeQuery(sql.toString(), params);
	}
	public Result queryEXAMResultYXK(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		//sql.append("select * from auto_audit_lab_result(nolock) ");
		sql.append("select ");
		sql.append("id,                "); 
		sql.append("TEST_NO,           "); 
		sql.append("report_item_code,  "); 
		sql.append("item_no,           "); 
		sql.append("print_order,       "); 
		sql.append("TAG,               "); 
		sql.append("REPORT_ITEM_NAME,  "); 
		sql.append("result,            "); 
		sql.append("units,             "); 
		sql.append("ABNORMAL_INDICATOR,"); 
		sql.append("LOWER_LIMIT,       "); 
		sql.append("UPPER_LIMIT,       "); 
		sql.append("PRINT_CONTEXT,     "); 
		sql.append("lasttime,          "); 
		sql.append("auto_audit_id,     "); 
		sql.append("patient_id,        "); 
		sql.append("visit_id,          "); 
		sql.append("result_date_time   "); 
		
		sql.append(" from auto_audit_lab_result(nolock) ");
		sql.append("		where 1=1 ");
		setParameter(params, "auto_audit_id", " and auto_audit_id=:auto_audit_id", sql);
		return executeQuery(sql.toString(), params);
	}
	public Result queryOperatorYXK(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from TMP_his_in_operation_master(nolock) a  ");
		sql.append(" where 1=1  ");
		setParameter(params, "auto_audit_id", " and auto_audit_id=:auto_audit_id", sql);
		return executeQuery(sql.toString(), params);
	}
	public Result queryOperatorResultYXK(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select a.* from TMP_his_in_operation_name(nolock) ");
		sql.append("		where 1=1 ");
		setParameter(params, "auto_audit_id", " and auto_audit_id=:auto_audit_id", sql);
		return executeQuery(sql.toString(), params);
	}
	public Result queryVitalYXK(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		//sql.append("select * from auto_audit_vital_signs_master(nolock) a  ");
		sql.append("select ");
		sql.append("a.id,          ");
		sql.append("a.PATIENT_ID,  ");
		sql.append("a.VISIT_ID,    ");
		sql.append("a.VITAL_SIGNS, ");
		sql.append("a.LASTTIME,    ");
		sql.append("a.auto_audit_id");
		sql.append(" from auto_audit_vital_signs_master(nolock) a  ");
		sql.append("		where 1=1 ");
		setParameter(params, "auto_audit_id", " and auto_audit_id=:auto_audit_id", sql);
		return executeQuery(sql.toString(), params);
	}
	public Result queryVitalResultYXK(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		//sql.append("select * from auto_audit_vital_signs_rec(nolock) a  ");
		sql.append("select  ");
		sql.append("a.id,                ");
		sql.append("a.PATIENT_ID,        ");
		sql.append("a.VISIT_ID,          ");
		sql.append("a.RECORDING_DATE,    ");
		sql.append("a.TIME_POINT,        ");
		sql.append("a.VITAL_SIGNS,       ");
		sql.append("a.VITAL_SIGNS_VALUES,");
		sql.append("a.UNITS,             ");
		sql.append("a.lasttime,          ");
		sql.append("a.auto_audit_id      ");
		sql.append(" from auto_audit_vital_signs_rec(nolock) a  ");
		sql.append("		where 1=1 ");
		setParameter(params, "auto_audit_id", " and auto_audit_id=:auto_audit_id", sql);
		return executeQuery(sql.toString(), params);
	}
	public Result queryPhysiologyInfoYXK(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		//sql.append("select * from auto_audit_PhysiologyInfo(nolock) a  ");
		sql.append("select  ");
		sql.append("a.id,                 ");
		sql.append("a.PATIENT_ID,         ");
		sql.append("a.VISIT_ID,           ");
		sql.append("a.PhysiologyInfo_CODE,");
		sql.append("a.PhysiologyInfo_NAME,");
		sql.append("a.LASTTIME,           ");
		sql.append("a.auto_audit_id       ");
		sql.append(" from auto_audit_PhysiologyInfo(nolock) a  ");
		sql.append("		where 1=1 ");
		setParameter(params, "auto_audit_id", " and auto_audit_id=:auto_audit_id", sql);
		return executeQuery(sql.toString(), params);
	}
	
	public Result queryALERGYDRUGSInfoYXK(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		//sql.append("select * from auto_audit_alergy_drugs(nolock) a  ");
		sql.append("select  ");
		sql.append("a.PATIENT_ID,           ");
		sql.append("a.VISIT_ID,             ");
		sql.append("a.ALERGY_DRUGS_CODE,    ");
		sql.append("a.ALERGY_DRUGS,         ");
		sql.append("a.SYS_ALERGY_DRUGS_CODE,");
		sql.append("a.SYS_ALERGY_DRUGS,     ");
		sql.append("a.lasttime,             ");
		sql.append("a.auto_audit_id,        ");
		sql.append("a.id                    ");
		sql.append(" from auto_audit_alergy_drugs(nolock) a  ");
		sql.append("		where 1=1 ");
		setParameter(params, "auto_audit_id", " and auto_audit_id=:auto_audit_id", sql);
		return executeQuery(sql.toString(), params);
	}
	
	public Result querypatientbasicinfoYXK(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select ");
		sql.append("a.d_type,                        ");
		sql.append("a.p_key,                         ");
		sql.append("a.patient_id,                    ");
		sql.append("a.visit_id,                      ");
		sql.append("b.dept_name dept_in,             ");
		sql.append("a.admission_datetime,            ");
		sql.append("a.[identity],                    ");
		sql.append("dhf.feibie_name charge_type,     ");
		sql.append("a.discharge_disposition,         ");
		sql.append("c.dept_name dept_discharge,      ");
		sql.append("a.discharge_datetime,            ");
		sql.append("a.occupation,                    ");
		sql.append("a.weight,                        ");
		sql.append("a.height,                        ");
		sql.append("dhy.ybzl_name insurance_type,    ");
		sql.append("a.mailing_address,               ");
		sql.append("a.zip_code,                      ");
		sql.append("dhp.patientadmission_name patient_class, ");
		sql.append("a.admission_cause,               ");
		sql.append("dhac.adm_condition_name pat_adm_condition, ");
		sql.append("a.director,                      ");
		sql.append("a.attending_doctor,              ");
		sql.append("a.doctor_in_charge,              ");
		sql.append("a.patient_name,                  ");
		sql.append("a.sex,                           ");
		sql.append("a.birthday,                      ");
		sql.append("a.INSURANCE_NO,                  ");
		sql.append("a.alergy_drugs,                  ");
		sql.append("a.age,                           ");
		sql.append("a.patient_no,                    ");
		sql.append("a.bed_no,                     ");
		sql.append("a.ts,                         ");
		sql.append("a.ccr                           ");
		sql.append("from V_his_in_patientvisit_all(nolock) a  ");
		sql.append("left join dict_his_deptment(nolock) b on a.dept_in=b.dept_code ");
		sql.append("left join dict_his_deptment(nolock) c on a.dept_discharge=c.dept_code ");
		sql.append(" left join hospital_common..dict_his_feibie(nolock) dhf on dhf.FEIBIE_CODE=a.charge_type ");
		sql.append(" left join hospital_common..dict_his_PatientAdmission (nolock) dhp on dhp.PatientAdmission_CODE =a.patient_class  ");
		sql.append(" left join hospital_common..dict_his_Adm_condition(nolock) dhac on dhac.adm_condition_code=a.pat_adm_condition ");
		sql.append(" left join hospital_common..DICT_HISYB_YBZL(nolock) dhy on dhy.ybzl_no=a.insurance_type ");
		sql.append("		where 1=1 ");
		setParameter(params, "patient_id", " and a.patient_id=:patient_id", sql);
		setParameter(params, "visit_id", " and a.visit_id=:visit_id", sql);
		return executeQuery(sql.toString(), params);
	}
	
	public Result queryEXAMAAOM(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select ");
		sql.append("a.id,               ");
		sql.append("a.PATIENT_ID,       ");
		sql.append("a.VISIT_ID,         ");
		sql.append("a.OPER_ID,          ");
		sql.append("a.patient_dept_name,");
		sql.append("a.oper_dept_name,   ");
		sql.append("a.OPERATOR,         ");
		sql.append("a.START_DATE_TIME,  ");
		sql.append("a.END_DATE_TIME,    ");
		sql.append("a.lasttime,         ");
		sql.append("a.auto_audit_id,    ");
		sql.append("a.OPERATOR_NAME,    ");
		sql.append("a.OPERATION         ");
		sql.append("from auto_audit_operation_master(nolock) a ");
		//sql.append("left join dict_his_deptment(nolock) b on b.dept_code=a.DEPT_STAYED ");
		sql.append("		where 1=1 ");
		setParameter(params, "auto_audit_id", " and a.auto_audit_id = :auto_audit_id", sql);
		return executeQuery(sql.toString(), params);
	}
	
	public Result queryEXAMAAON(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		//sql.append("select * from auto_audit_operation_name(nolock) ");
		sql.append("select ");
		sql.append("id,                   ");
		sql.append("lasttime,             ");
		sql.append("auto_audit_id,        ");
		sql.append("PATIENT_ID,           ");
		sql.append("VISIT_ID,             ");
		sql.append("OPER_ID,              ");
		sql.append("OPERATION_NO,         ");
		sql.append("OPERATION,            ");
		sql.append("operation_CODE,       ");
		sql.append("OPERATION_SCALE,      ");
		sql.append("woundgrade_CODE,      ");
		sql.append("WOUND_GRADE,          ");
		sql.append("START_DATE_TIME,      ");
		sql.append("END_DATE_TIME,        ");
		sql.append("SMOOTH_INDICATOR,     ");
		sql.append("OPERATING_ROOM,       ");
		sql.append("DIAG_BEFORE_OPERATION,");
		sql.append("PATIENT_CONDITION,    ");
		sql.append("DIAG_AFTER_OPERATION, ");
		sql.append("OPERATION_CLASS,      ");
		sql.append("FIRST_ASSISTANT,      ");
		sql.append("SECOND_ASSISTANT,     ");
		sql.append("ANESTHESIA_METHOD,    ");
		sql.append("ANESTHESIA_DOCTOR,    ");
		sql.append("patient_dept_name,    ");
		sql.append("oper_dept_name,       ");
		sql.append("OPERATOR              ");
		sql.append(" from auto_audit_operation_name(nolock) ");
		sql.append("		where 1=1 ");
		setParameter(params, "auto_audit_id", " and auto_audit_id=:auto_audit_id", sql);
		return executeQuery(sql.toString(), params);
	}
	
	
	//查询处方字典
	public Result queryComment(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from dict_sys_comment(nolock) ");
		return executeQuery(sql.toString(), params);
	}

	//查询说明书
	public Result queryShuoms(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT                                            ");
		sql.append(" 1 AS is_zdy,                                      ");
		sql.append(" zdy.SYS_DRUG_CODE,                                ");
		sql.append(" zdy.image,                                        ");
		sql.append(" zdy.name_cn,                                      ");
		sql.append(" zdy.name_en,                                      ");
		sql.append(" zdy.name_generic,                                 ");
		sql.append(" zdy.manufacturer,                                 ");
		sql.append(" zdy.otc_type,                                     ");
		sql.append(" zdy.medical_insurance_type,                       ");
		sql.append(" zdy.is_essential,                                 ");
		sql.append(" zdy.is_external,                                  ");
		sql.append(" zdy.is_gs1,                                       ");
		sql.append(" zdy.is_chinese_medicine,                          ");
		sql.append(" zdy.zb_type,                                      ");
		sql.append(" zdy.approval_number,                              ");
		sql.append(" zdy.approval_date,                                ");
		sql.append(" zdy.form,                                         ");
		sql.append(" zdy.spec,                                         ");
		sql.append(" zdy.indications,                                  ");
		sql.append(" zdy.effective_term,                               ");
		sql.append(" zdy.storage_condition,                            ");
		sql.append(" zdy.ingredients,                                  ");
		sql.append(" zdy.usage_dosage,                                 ");
		sql.append(" zdy.attending,                                    ");
		sql.append(" zdy.adverse_reaction,                             ");
		sql.append(" zdy.taboo,                                        ");
		sql.append(" zdy.attentions,                                   ");
		sql.append(" zdy.interaction,                                  ");
		sql.append(" zdy.revision_date,                                ");
		sql.append(" zdy.special_crowd,                                ");
		sql.append(" zdy.pharmacologic,                                ");
		sql.append(" zdy.manufacturer_short_name,                      ");
		sql.append(" zdy.manufacturer_address,                         ");
		sql.append(" zdy.manufacturer_tel,                             ");
		sql.append(" zdy.html,                                         ");
		sql.append(" zdy.WARN,                                         ");
		sql.append(" zdy.DURG_state,                                   ");
		sql.append(" zdy.Drug_pregnant_Lactation,                      ");
		sql.append(" zdy.Drug_children,                                ");
		sql.append(" zdy.Drug_elderly,                                 ");
		sql.append(" zdy.Drug_overdose,                                ");
		sql.append(" zdy.pharmacology_toxicology,                      ");
		sql.append(" zdy.Pharmacokinetics,                             ");
		sql.append(" zdy.Packing,                                      ");
		sql.append(" zdy.Standard_exe,                                 ");
		sql.append(" zdy.drugs_Registration_imported,                  ");
		sql.append(" zdy.products_Registration,                        ");
		sql.append(" zdy.Sub_manufacturer,                             ");
		sql.append(" zdy.INPUT_CODE,                                   ");
		sql.append(" zdy.Molecular_formula,                            ");
		sql.append(" zdy.Molecular_weight,                             ");
		sql.append(" zdy.other_msg,                                    ");
		sql.append(" zdy.FDANotes_ID,                                  ");
		sql.append(" zdy.owner_create,                                 ");
		sql.append(" (select max(dsd.shuoms_file) from dict_sys_drug(nolock) dsd ");
		sql.append(" where dsd.DRUG_CODE=zdy.SYS_DRUG_CODE)  as shuoms_file ");
		//sql.append(" ,'' as lasttime                                    ");
		sql.append(" FROM                                              ");
		sql.append(" 	spzl_shuoms_zdy ( nolock ) zdy                 ");
		sql.append(" 	INNER JOIN spzl_shuoms ( nolock ) shuoms ON    ");
		sql.append(" shuoms.SYS_DRUG_CODE= zdy.SYS_DRUG_CODE           ");
		sql.append(" UNION ALL                                         ");
		sql.append(" SELECT                                            ");
		sql.append(" 0 AS is_zdy,                                      ");
		sql.append(" s.SYS_DRUG_CODE,                                  ");
		sql.append(" s.image,                                          ");
		sql.append(" s.name_cn,                                        ");
		sql.append(" s.name_en,                                        ");
		sql.append(" s.name_generic,                                   ");
		sql.append(" s.manufacturer,                                   ");
		sql.append(" s.otc_type,                                       ");
		sql.append(" s.medical_insurance_type,                         ");
		sql.append(" s.is_essential,                                   ");
		sql.append(" s.is_external,                                    ");
		sql.append(" s.is_gs1,                                         ");
		sql.append(" s.is_chinese_medicine,                            ");
		sql.append(" s.zb_type,                                        ");
		sql.append(" s.approval_number,                                ");
		sql.append(" s.approval_date,                                  ");
		sql.append(" s.form,                                           ");
		sql.append(" s.spec,                                           ");
		sql.append(" s.indications,                                    ");
		sql.append(" s.effective_term,                                 ");
		sql.append(" s.storage_condition,                              ");
		sql.append(" s.ingredients,                                    ");
		sql.append(" s.usage_dosage,                                   ");
		sql.append(" s.attending,                                      ");
		sql.append(" s.adverse_reaction,                               ");
		sql.append(" s.taboo,                                          ");
		sql.append(" s.attentions,                                     ");
		sql.append(" s.interaction,                                    ");
		sql.append(" s.revision_date,                                  ");
		sql.append(" s.special_crowd,                                  ");
		sql.append(" s.pharmacologic,                                  ");
		sql.append(" s.manufacturer_short_name,                        ");
		sql.append(" s.manufacturer_address,                           ");
		sql.append(" s.manufacturer_tel,                               ");
		sql.append(" s.html,                                           ");
		sql.append(" s.WARN,                                           ");
		sql.append(" s.DURG_state,                                     ");
		sql.append(" s.Drug_pregnant_Lactation,                        ");
		sql.append(" s.Drug_children,                                  ");
		sql.append(" s.Drug_elderly,                                   ");
		sql.append(" s.Drug_overdose,                                  ");
		sql.append(" s.pharmacology_toxicology,                        ");
		sql.append(" s.Pharmacokinetics,                               ");
		sql.append(" s.Packing,                                        ");
		sql.append(" s.Standard_exe,                                   ");
		sql.append(" s.drugs_Registration_imported,                    ");
		sql.append(" s.products_Registration,                          ");
		sql.append(" s.Sub_manufacturer,                               ");
		sql.append(" s.INPUT_CODE,                                     ");
		sql.append(" s.Molecular_formula,                              ");
		sql.append(" s.Molecular_weight,                               ");
		sql.append(" s.other_msg,                                      ");
		sql.append(" s.FDANotes_ID,                                    ");
		sql.append(" s.owner_create,                                   ");
		sql.append(" (select max(dsd.shuoms_file) from dict_sys_drug(nolock) dsd ");
		sql.append(" where dsd.DRUG_CODE=s.SYS_DRUG_CODE)  as shuoms_file ");
		//sql.append(" ,s.lasttime                                        ");
		sql.append(" FROM                                              ");
		sql.append(" 	spzl_shuoms ( nolock ) s                       ");
		sql.append(" WHERE                                             ");
		sql.append(" NOT EXISTS ( SELECT zdy.sys_drug_code             ");
		sql.append(" FROM spzl_shuoms_zdy ( nolock ) zdy               ");
		sql.append(" WHERE s.SYS_DRUG_CODE= zdy.SYS_DRUG_CODE )        ");
		Result result = executeQuery(sql.toString(), params);
		return result;
	}
	
	public int audit_zdy_procBefore(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("{call "+SCR_SHENGFANG_EXEPROC+" (:auto_audit_id, '', '', '', '', :success)}");
		Record ins = new Record();
		ins.put("auto_audit_id", params.get("auto_audit_id"));
		Map<String, Integer> outs = new HashMap<String, Integer>();
		outs.put("success", java.sql.Types.INTEGER);
		Record rtn = executeCall(sql.toString(), ins, outs);
		int i = rtn.getInt("return_value");
		if (i != 0) {
			throw new Exception("执行存储过程出错");
		}
		return rtn.getInt("success");
	}
	
	//系统自定义审查运行环境存储过程
	public int audit_sys_procBefore(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("{call "+SYS_SHENGFANG_EXEPROC+" (:auto_audit_id, '', '', '', '', :success)}");
		Record ins = new Record();
		ins.put("auto_audit_id", params.get("auto_audit_id"));
		Map<String, Integer> outs = new HashMap<String, Integer>();
		outs.put("success", java.sql.Types.INTEGER);
		Record rtn = executeCall(sql.toString(), ins, outs);
		int i = rtn.getInt("return_value");
		if (i != 0) {
			throw new Exception("执行存储过程出错");
		}
		return rtn.getInt("success");
	}
	
	public void audit_zdy(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("{call SCR_SHENGFANG (:auto_audit_id, '', '')}");
		Record ins = new Record();
		ins.put("auto_audit_id", params.get("auto_audit_id"));
		Map<String, Integer> outs = new HashMap<String, Integer>();
		executeCall(sql.toString(), ins, outs);
	}
	public Record audit_zdy_proc(Map<String, Object> params, String proc) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("{call "+ proc +" (:auto_audit_id, '', '')}");
		Record ins = new Record();
		ins.put("auto_audit_id", params.get("auto_audit_id"));
		Map<String, Integer> outs = new HashMap<String, Integer>();
		return executeCall(sql.toString(), ins, outs);
	}
	
	public Record pr_audit_zdy_proc(Map<String, Object> params, String proc) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("{call "+ proc +" (:xml, '', '')}");
		Record ins = new Record();
		ins.put("xml", params.get("xml"));
		Map<String, Integer> outs = new HashMap<String, Integer>();
		return executeCall(sql.toString(), ins, outs);
	}
	
	public Result queryUsers(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select su.order_type, ");
		sql.append(" su.type,su.doctor_no,su.order_type,su.position,su.description,su.create_time,su.lastModifyTime,suc.no,suc.name,suc.password,suc.gender, ");
		sql.append(" suc.telephone,suc.birthday,suc.avatar,                             ");
		sql.append(" suc.state ");
		sql.append(" from                                                               ");
		sql.append(" system_users_center su (nolock)                                                   ");
		sql.append(" inner join system_user_cooperop suc (nolock) on su.cooperop_sys_no = suc.no ");
		sql.append(" left join system_department sd (nolock) on sd.id = suc.department           ");
		sql.append(" where su.zhuangt = :zhuangt and su.is_zx = 0 ");
		return executeQuery(sql.toString(),params);
	}

	public Result queryTPNLineYXK(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select                                   ");
		sql.append(" auto_audit_id,                           ");
		sql.append(" PATIENT_ID,                              ");
		sql.append(" VISIT_ID,                                ");
		sql.append(" GROUP_ID,                                ");
		sql.append(" ORDER_NO,                                ");
		sql.append(" FDNAME,                                  ");
		sql.append(" X_Value,                                 ");
		sql.append(" Y_Value,                                 ");
		sql.append(" current_X_value,                         ");
		sql.append(" current_Y_value,                         ");
		sql.append(" Beizhu                                   ");
		sql.append(" from cr_tmp_fenxitux_linechart(nolock)   ");
		sql.append(" where auto_audit_id=:auto_audit_id   ");
		return executeQuery(sql.toString(), params);
	}

	public Result queryTPNPieYXK(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select                                   ");
		sql.append(" auto_audit_id,                           ");
		sql.append(" PATIENT_ID,                              ");
		sql.append(" VISIT_ID,                                ");
		sql.append(" GROUP_ID,                                ");
		sql.append(" ORDER_NO,                                ");
		sql.append(" FDNAME,                                  ");
		sql.append(" Value,                                   ");
		sql.append(" Beizhu                                   ");
		sql.append(" from cr_tmp_fenxitux_piechart(nolock)    ");
		sql.append(" where auto_audit_id=:auto_audit_id   ");
		return executeQuery(sql.toString(), params);
	}

	public Result queryTPNRadarYXK(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select                                   ");
		sql.append(" auto_audit_id,                           ");
		sql.append(" patient_id,                              ");
		sql.append(" visit_id,                                ");
		sql.append(" group_id,                                ");
		sql.append(" order_no,                                ");
		sql.append(" FDNAME,                                  ");
		sql.append(" Value_current,                           ");
		sql.append(" Value_Highest,                           ");
		sql.append(" Value_lowest,                            ");
		sql.append(" Beizhu,                                  ");
		sql.append(" show_Value_current,                      ");
		sql.append(" show_Value_Highest,                      ");
		sql.append(" show_Value_lowest,                       ");
		sql.append(" show_FDNAME                              ");
		sql.append(" from cr_tmp_fenxitux_radarchart(nolock)  ");
		sql.append(" where auto_audit_id=:auto_audit_id   ");
		return executeQuery(sql.toString(), params);
	}	
}
