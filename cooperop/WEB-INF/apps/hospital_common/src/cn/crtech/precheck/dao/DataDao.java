package cn.crtech.precheck.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;

public class DataDao extends BaseDao {
	
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
		sql.append("from V_his_in_pats p  (nolock)");
		sql.append("left join dict_his_deptment d (nolock) on d.dept_CODE = p.DEPT_CODE ");
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
		sql.append("select p.* ");
		sql.append("from V_his_in_orders p (nolock) ");
		sql.append(" where 1=1 ");
		setParameter(params, "patient_id", " and p.PATIENT_ID=:patient_id", sql);
		setParameter(params, "visit_id", " and p.visit_id=:visit_id", sql);
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
		sql.append("select p.* ");
		sql.append("from V_use_orders_outp p (nolock) ");
		sql.append(" where 1=1 ");
//		setParameter(params, "patient_id", " and p.PATIENT_ID=:patient_id", sql);
//		setParameter(params, "visit_id", " and p.visit_id=:visit_id", sql);
		setParameter(params, "auto_audit_id", " and p.auto_audit_id=:auto_audit_id", sql);
		return executeQuery(sql.toString(), params);
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
		sql.append("from TMP_his_in_diagnosis p (nolock) ");
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
		sql.append("select mx.*,hz.start_date_time,hz.end_date_time from TMP_his_in_operation_name mx (nolock)");
		sql.append(" left join TMP_his_in_operation_master hz (nolock)");
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
		sql.append("from TMP_his_in_PhysiologyInfo p (nolock) ");
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
		sql.append("from TMP_his_in_PhysiologyInfo p (nolock) ");
		sql.append(" where 1=1 ");
		setParameter(params, "patient_id", " and p.PATIENT_ID=:patient_id", sql);
		setParameter(params, "visit_id", " and p.visit_id=:visit_id", sql);
		return executeQuery(sql.toString(), params);
	}
	public Record getPatient(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from his_patient(nolock) where 1=1 ");
		setParameter(params, "patient_id", " and patient_id=:patient_id", sql);
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	public Record getInPatient(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from his_in_pats(nolock) where 1=1 ");
		setParameter(params, "patient_id", " and patient_id=:patient_id", sql);
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	public Result queryPatientVisit(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from his_in_patientvisit(nolock) where 1=1 ");
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
		sql.append("select * from auto_audit_diagnosis(nolock) where 1=1 ");
		setParameter(params, "auto_audit_id", " and auto_audit_id=:auto_audit_id", sql);
		return executeQuery(sql.toString(), params);
	}
	public Result queryLABYXK(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from auto_audit_lab_test_master(nolock) p where 1=1 ");
		setParameter(params, "auto_audit_id", " and auto_audit_id=:auto_audit_id", sql);
		return executeQuery(sql.toString(), params);
	}
	public Result queryLABResultYXK(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from auto_audit_lab_result (nolock) where 1=1  ");
		setParameter(params, "auto_audit_id", " and auto_audit_id=:auto_audit_id", sql);
		return executeQuery(sql.toString(), params);
	}
	public Result queryEXAMYXK(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from auto_audit_exam_master a (nolock) ");
		sql.append(" where 1=1  ");
		setParameter(params, "auto_audit_id", " and auto_audit_id=:auto_audit_id", sql);
		return executeQuery(sql.toString(), params);
	}
	public Result queryEXAMResultYXK(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from auto_audit_exam_report (nolock) ");
		sql.append("		where 1=1 ");
		setParameter(params, "auto_audit_id", " and auto_audit_id=:auto_audit_id", sql);
		return executeQuery(sql.toString(), params);
	}
	public Result queryOperatorYXK(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from TMP_his_in_operation_master a (nolock) ");
		sql.append(" where 1=1  ");
		setParameter(params, "auto_audit_id", " and auto_audit_id=:auto_audit_id", sql);
		return executeQuery(sql.toString(), params);
	}
	public Result queryOperatorResultYXK(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select a.* from TMP_his_in_operation_name (nolock) ");
		sql.append("		where 1=1 ");
		setParameter(params, "auto_audit_id", " and auto_audit_id=:auto_audit_id", sql);
		return executeQuery(sql.toString(), params);
	}
	public Result queryVitalYXK(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from auto_audit_vital_signs_master a  (nolock) ");
		sql.append("		where 1=1 ");
		setParameter(params, "auto_audit_id", " and auto_audit_id=:auto_audit_id", sql);
		return executeQuery(sql.toString(), params);
	}
	public Result queryVitalResultYXK(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from auto_audit_vital_signs_rec a (nolock) ");
		sql.append("		where 1=1 ");
		setParameter(params, "auto_audit_id", " and auto_audit_id=:auto_audit_id", sql);
		return executeQuery(sql.toString(), params);
	}
	public Result queryPhysiologyInfoYXK(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from auto_audit_PhysiologyInfo a (nolock) ");
		sql.append("		where 1=1 ");
		setParameter(params, "auto_audit_id", " and auto_audit_id=:auto_audit_id", sql);
		return executeQuery(sql.toString(), params);
	}
}
