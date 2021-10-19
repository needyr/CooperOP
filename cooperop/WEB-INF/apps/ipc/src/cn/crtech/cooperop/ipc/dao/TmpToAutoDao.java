package cn.crtech.cooperop.ipc.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class TmpToAutoDao extends BaseDao{
	
	public void insertAuditDiagnosis(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select                                               ");
		sql.append(" PATIENT_ID,VISIT_ID,                                    ");
		sql.append(" DIAGNOSIS_DATE,DIAGNOSIS_TYPE,                          ");
		sql.append(" diagnosisclass_NAME,DIAGNOSIS_NO,                       ");
		sql.append(" DIAGNOSIS_DESC,CLIN_DIAG,TREAT_DAYS,                    ");
		sql.append(" TREAT_RESULT,lasttime, :auto_audit_id  auto_audit_id    ");
		sql.append(" from TMP_his_in_diagnosis (nolock)                               ");
		sql.append(" where PATIENT_ID = :patient_id and VISIT_ID = :visit_id ");
		Result res = executeQuery(sql.toString(), params);
		if(res != null) {
			for(Record rec : res.getResultset()) {
				String id = CommonFun.getITEMID();
				rec.put("id", id);
				rec.remove("rowno");
				executeInsert("auto_audit_diagnosis", rec);
			}
		}
	}

	public void insertAuditExamMaster(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select  ");
		sql.append(" PATIENT_ID,VISIT_ID,EXAM_NO,                  ");
		sql.append(" REQ_DATE_TIME,REQ_PHYSICIAN,                  ");
		sql.append(" EXAM_CLASS,EXAM_SUB_CLASS,                    ");
		sql.append(" CLIN_DIAG,EXAM_DATE_TIME,REPORTER,            ");
		sql.append(" REPORT_DATE_TIME,LASTTIME,:auto_audit_id auto_audit_id  ");
		sql.append(" from TMP_his_in_exam_master (nolock)                   ");
		sql.append(" where patient_id = :patient_id and visit_id = :visit_id    ");
		Result res = executeQuery(sql.toString(), params);
		if(res != null) {
			for(Record rec : res.getResultset()) {
				String id = CommonFun.getITEMID();
				rec.put("id", id);
				rec.remove("rowno");
				executeInsert("auto_audit_exam_master", rec);
			}
		}
	}
	
	public void insertAuditExamReport(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select   ");
		sql.append(" EXAM_NO,IS_ABNORMAL,EXAM_ITEM,DESCRIPTION,        ");
		sql.append(" IMPRESSION,RECOMMENDATION,USE_IMAGE,lasttime,     ");
		sql.append(" :auto_audit_id auto_audit_id                                ");
		sql.append(" from TMP_his_in_exam_report (nolock)                      ");
		sql.append(" where exam_no in                                   ");
		sql.append(" (select exam_no from TMP_his_in_exam_master where patient_id = :patient_id and visit_id = :visit_id) ");
		Result res = executeQuery(sql.toString(), params);
		if(res != null) {
			for(Record rec : res.getResultset()) {
				rec.put("id", CommonFun.getITEMID());
				rec.remove("rowno");
				executeInsert("auto_audit_exam_report", rec);
			}
		}
	}
	
	public void insertAuditLabTestMaster(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select     ");
		sql.append(" PATIENT_ID,VISIT_ID,TEST_NO,SPECIMEN,ITEM_NAME,        ");
		sql.append(" ORDERING_PROVIDER,REQUESTED_DATE_TIME,RESULTS_RPT_DATE_TIME,        ");
		sql.append(" lasttime,:auto_audit_id auto_audit_id        ");
		sql.append(" from TMP_his_in_lab_test_master (nolock)        ");
		sql.append(" where patient_id = :patient_id and visit_id = :visit_id        ");
		Result res = executeQuery(sql.toString(), params);
		if(res != null) {
			for(Record rec : res.getResultset()) {
				rec.put("id", CommonFun.getITEMID());
				rec.remove("rowno");
				executeInsert("auto_audit_lab_test_master", rec);
			}
		}       
	}
	
	public void insertAuditLabResult(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select     ");
		sql.append(" TEST_NO,report_item_code,item_no,                    ");
		sql.append(" print_order,TAG,REPORT_ITEM_NAME,                    ");
		sql.append(" result,units,ABNORMAL_INDICATOR,                     ");
		sql.append(" LOWER_LIMIT,UPPER_LIMIT,PRINT_CONTEXT,result_date_time,");
		sql.append(" lasttime,:auto_audit_id auto_audit_id,patient_id,visit_id ");
		sql.append(" from TMP_his_in_lab_result   (nolock)                        ");
		sql.append(" where test_no in                                      ");
		sql.append(" (select test_no from TMP_his_in_lab_test_master where patient_id = :patient_id and visit_id = :visit_id) ");
		Result res = executeQuery(sql.toString(), params);
		if(res != null) {
			for(Record rec : res.getResultset()) {
				rec.put("id", CommonFun.getITEMID());
				rec.remove("rowno");
				executeInsert("auto_audit_lab_result", rec);
			}
		}               
	}
	
	public void insertAuditOperationName(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select          ");
		sql.append("PATIENT_ID,                          ");
		sql.append("VISIT_ID,                            ");
		sql.append("OPER_ID,                             ");
		sql.append("OPERATION_NO,                        ");
		sql.append("OPERATION,                           ");
		sql.append("operation_CODE,                      ");
		sql.append("OPERATION_SCALE,                     ");
		sql.append("woundgrade_CODE,                     ");
		sql.append("WOUND_GRADE,                         ");
		sql.append("START_DATE_TIME,                     ");
		sql.append("END_DATE_TIME,                       ");
		sql.append("SMOOTH_INDICATOR,                    ");
		sql.append("OPERATING_ROOM,                      ");
		sql.append("DIAG_BEFORE_OPERATION,               ");
		sql.append("PATIENT_CONDITION,                   ");
		sql.append("DIAG_AFTER_OPERATION,                ");
		sql.append("OPERATION_CLASS,                     ");
		sql.append("FIRST_ASSISTANT,                     ");
		sql.append("SECOND_ASSISTANT,                    ");
		sql.append("ANESTHESIA_METHOD,                   ");
		sql.append("ANESTHESIA_DOCTOR,                   ");
		sql.append("patient_dept_name,                   ");
		sql.append("oper_dept_name,                      ");
		sql.append("OPERATOR,                            ");
		sql.append("lasttime,:auto_audit_id auto_audit_id");
		sql.append(" from TMP_his_in_operation_name (nolock)                              ");
		sql.append(" where patient_id = :patient_id and visit_id = :visit_id                  ");
		Result res = executeQuery(sql.toString(), params);
		if(res != null) {
			for(Record rec : res.getResultset()) {
				rec.put("id", CommonFun.getITEMID());
				rec.remove("rowno");
				executeInsert("auto_audit_operation_name", rec);
			}
		}                
	}
	
	public void insertAuditOperationMaster(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select                                   ");
		sql.append(" PATIENT_ID,VISIT_ID,OPER_ID,   ");
		sql.append(" patient_dept_name,oper_dept_name,      ");
		sql.append(" CONVERT(varchar(32),START_DATE_TIME, 20) as START_DATE_TIME,  ");
		sql.append(" CONVERT(varchar(32),END_DATE_TIME, 20) as END_DATE_TIME, ");
		sql.append(" OPERATOR,OPERATOR_NAME,OPERATION,    ");
		sql.append(" lasttime,:auto_audit_id auto_audit_id                ");
		sql.append(" from TMP_his_in_operation_master (nolock)           ");
		sql.append(" where patient_id = :patient_id and visit_id = :visit_id ");
		Result res = executeQuery(sql.toString(), params);
		if(res != null) {
			for(Record rec : res.getResultset()) {
				rec.put("id", CommonFun.getITEMID());
				rec.remove("rowno");
				executeInsert("auto_audit_operation_master", rec);
			}
		}                 
	}
	
	public void insertAuditVitalSignsMaster(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select                                    ");
		sql.append(" PATIENT_ID,VISIT_ID,VITAL_SIGNS,           ");
		sql.append(" LASTTIME,:auto_audit_id auto_audit_id                ");
		sql.append(" from TMP_his_in_vital_signs_master (nolock)         ");
		sql.append(" where patient_id = :patient_id and visit_id = :visit_id ");
		Result res = executeQuery(sql.toString(), params);
		if(res != null) {
			for(Record rec : res.getResultset()) {
				rec.put("id", CommonFun.getITEMID());
				rec.remove("rowno");
				executeInsert("auto_audit_vital_signs_master", rec);
			}
		}               
	}
	
	public void insertAuditVitalSignsRec(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select  ");
		sql.append(" PATIENT_ID,VISIT_ID,RECORDING_DATE,TIME_POINT,");
		sql.append(" VITAL_SIGNS,VITAL_SIGNS_VALUES,UNITS,         ");
		sql.append(" lasttime,:auto_audit_id auto_audit_id                   ");
		sql.append(" from TMP_his_in_vital_signs_rec (nolock)               ");
		sql.append(" where patient_id = :patient_id and visit_id = :visit_id    ");
		Result res = executeQuery(sql.toString(), params);
		if(res != null) {
			for(Record rec : res.getResultset()) {
				rec.put("id", CommonFun.getITEMID());
				rec.remove("rowno");
				executeInsert("auto_audit_vital_signs_rec", rec);
			}
		}                
	} 
	
	public void insertAuditPhysiologyInfo(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select  ");
		sql.append(" PATIENT_ID,VISIT_ID,PhysiologyInfo_CODE,        ");
		sql.append(" PhysiologyInfo_NAME,LASTTIME,:auto_audit_id auto_audit_id ");
		sql.append(" from TMP_his_in_PhysiologyInfo (nolock)                  ");
		sql.append(" where patient_id = :patient_id and visit_id = :visit_id      ");
		Result res = executeQuery(sql.toString(), params);
		if(res != null) {
			for(Record rec : res.getResultset()) {
				rec.put("id", CommonFun.getITEMID());
				rec.remove("rowno");
				executeInsert("auto_audit_PhysiologyInfo", rec);
			}
		}                  
	} 
	
	public void insertAuditAlergyDrugs(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select  ");
		sql.append(" PATIENT_ID,VISIT_ID,ALERGY_DRUGS_CODE,ALERGY_DRUGS, ");
		sql.append(" SYS_ALERGY_DRUGS_CODE,SYS_ALERGY_DRUGS,lasttime,:auto_audit_id auto_audit_id ");
		sql.append(" from TMP_his_in_ALERGY_DRUGS (nolock)                  ");
		sql.append(" where patient_id = :patient_id and visit_id = :visit_id      ");
		Result res = executeQuery(sql.toString(), params);
		if(res != null) {
			for(Record rec : res.getResultset()) {
				rec.put("id", CommonFun.getITEMID());
				rec.remove("rowno");
				executeInsert("auto_audit_alergy_drugs", rec);
			}
		}                  
	} 
	
}
