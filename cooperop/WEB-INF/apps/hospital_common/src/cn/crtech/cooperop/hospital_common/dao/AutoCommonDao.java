package cn.crtech.cooperop.hospital_common.dao;

import java.util.Map;
import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class AutoCommonDao extends BaseDao{

	private final static String TABLE_NAME = "auto_common";
	private final static String TABLE_NAME_MX = "auto_common_mx";
	private final static String V_TABLE_NAME = "hospital_common..v_auto_common_use";
	
	private final static String DOCTOR_AUDIT_PROC = "DOCTOR_AUDIT_PROC";
	
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("select b.*,a.create_time as common_create_time,datediff(ss, b.create_time,GETDATE()) cost_time,");
		sql.append(" dbo.get_patient_xx(b.patient_id, b.visit_id) patient_name, c.name doctor_name, ");
		sql.append(" a.cost_time z_cost_time from "+V_TABLE_NAME+"(nolock) a " );
		sql.append(" inner join auto_audit(nolock) b on a.id = b.common_id " );
		sql.append(" left join system_user_cooperop(nolock) c on b.doctor_no = c.no " );
		//sql.append(" where a.doctor_on = :doctor_no " );
		sql.append("where b.is_after is null and b.id is not null ");
		sql.append("and b.d_type in (1,2,3) ");
		
		
		if(!CommonFun.isNe(params.get("start_time"))) {
			sql.append("and a.create_time >= :start_time ");
		}
		if(!CommonFun.isNe(params.get("end_time"))) {
			sql.append("and a.create_time <= :end_time ");
		}
		/*if(!CommonFun.isNe(params.get("doctor_no"))) {
			sql.append("and b.doctor_no = :doctor_no ");
		}
		if(!CommonFun.isNe(params.get("doctor"))) {
			params.put("doctor", "%" + params.get("doctor")+ "%");
			sql.append("and ( b.doctor_no like :doctor or c.name like :doctor )");
		}*/
		if(!CommonFun.isNe(params.get("doctor"))) {
			String[] split = ((String) params.get("doctor")).split(",");
			params.put("doctor", split);
			sql.append("and b.doctor_no in (:doctor)");
		}
		if(!CommonFun.isNe(params.get("dept_code"))) {
			String[] split = ((String) params.get("dept_code")).split(",");
			params.put("dept_code", split);
			sql.append("and b.patient_dept_code in (:dept_code) ");
		}
		if (!CommonFun.isNe(params.get("d_type_query"))) {
			if (params.get("d_type_query") instanceof String) {
				sql.append("and b.d_type=:d_type_query ");
			}else {
				sql.append("and b.d_type in (:d_type_query) ");
			}
		}
		if(!CommonFun.isNe(params.get("is_deal"))) {
			sql.append(" and (b.state='D' or (b.state='N' and b.is_sure=0)) ");
		}
		if(CommonFun.isNe(params.get("sort"))) {
			params.put("sort", " a.create_time desc ");
		}
		if (!CommonFun.isNe(params.get("state_query"))) {
			if (params.get("state_query") instanceof String) {
				sql.append("and b.state=:state_query ");
			}else {
				sql.append("and b.state in (:state_query) ");
			}
		}
		if (!CommonFun.isNe(params.get("patient"))) {
			params.put("patient", "%" + params.get("patient")+ "%");
			sql.append("and ( b.patient_id like :patient or dbo.get_patient_xx(b.patient_id, b.visit_id) like :patient )");
		}
		if(!CommonFun.isNe(params.get("yz_cf_no"))) {
			params.put("yz_cf_no", ((String)params.get("yz_cf_no")).split(","));
			sql.append("and exists (select 1 from hospital_autopa..auto_audit_orders (nolock) where b.id=auto_audit_id and group_id in (:yz_cf_no) ) ");
		}
		return executeQueryLimit(sql.toString(), params);
	}
	
	/**
	 * HYT测试审查结果查询
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Result hyt_audit_test_result(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("select b.*,datediff(ss, b.create_time,GETDATE()) cost_time, dbo.get_patient_xx(b.patient_id, b.visit_id) patient_name, c.name doctor_name, a.cost_time z_cost_time from "+V_TABLE_NAME+"(nolock) a " );
		sql.append(" left join auto_audit(nolock) b on a.id = b.common_id " );
		sql.append(" left join system_user_cooperop(nolock) c on b.doctor_no = c.no " );
		//sql.append(" where a.doctor_on = :doctor_no " );
		sql.append("where b.doctor_no='test_hyt' ");
		
		params.put("sort", "create_time desc");
		return executeQueryLimit(sql.toString(), params);
	}
	
	
	public Result queryAuditAll(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("select a.*,cast (a.cost_time as int) costtime, ");
		sql.append(" dbo.get_patient_xx(a.patient_id, a.visit_id) patient_name, c.name doctor_name ");
		sql.append(" from "+V_TABLE_NAME+"(nolock) a ");
		sql.append(" left join system_user_cooperop(nolock) c on a.doctor_no = c.no " );
		sql.append("where 1=1  ");
		if(!CommonFun.isNe(params.get("start_time"))) {
			sql.append("and a.create_time >= :start_time ");
		}
		if(!CommonFun.isNe(params.get("end_time"))) {
			sql.append("and a.create_time <= :end_time ");
		}
		if(!CommonFun.isNe(params.get("str_consumingTime"))) {
			int str_consumingTime = Integer.parseInt(params.get("str_consumingTime").toString())*1000;
			sql.append("and a.cost_time >= "+str_consumingTime);
		}
		if(!CommonFun.isNe(params.get("end_consumingTime"))) {
			int end_consumingTime = Integer.parseInt(params.get("end_consumingTime").toString())*1000;
			sql.append("and a.cost_time <= "+end_consumingTime);
		}
		if(!CommonFun.isNe(params.get("doctor_no"))) {
			sql.append("and a.doctor_no = :doctor_no ");
		}
		if(!CommonFun.isNe(params.get("audit_type"))) {
			if(params.get("audit_type").equals("1")) {
				sql.append("and a.is_after = 1 ");
			}
			if(params.get("audit_type").equals("0")) {
				sql.append("and a.is_after is null  ");
			}
		}
		if (!CommonFun.isNe(params.get("d_type_query"))) {
			if (params.get("d_type_query") instanceof String) {
				sql.append("and b.d_type=:d_type_query ");
			}else {
				sql.append("and b.d_type in (:d_type_query) ");
			}
		}
		if(!CommonFun.isNe(params.get("auditResult"))) {
			if(params.get("auditResult").equals("Y")) {
				sql.append("and a.state = 'Y' ");
			}
			if(params.get("auditResult").equals("T")) {
				sql.append("and a.state = 'T' ");
			}
			if(params.get("auditResult").equals("N")) {
				sql.append("and a.state = 'N' ");
			}
			if(params.get("auditResult").equals("Q")) {
				sql.append("and a.state = 'Q' ");
			}
			
		}
		if(!CommonFun.isNe(params.get("doctor"))) {
			params.put("doctor", "%" + params.get("doctor")+ "%");
			sql.append("and ( a.doctor_no like :doctor or c.name like :doctor )");
		}
		if(!CommonFun.isNe(params.get("deptname"))) {
			params.put("dept_code", "%" + params.get("deptname")+ "%");
			sql.append("and ( a.dept_code like :dept_code )");
		}
		if (!CommonFun.isNe(params.get("patient"))) {
			params.put("patient", "%" + params.get("patient")+ "%");
			sql.append("and ( b.patient_id like :patient or dbo.get_patient_xx(b.patient_id, b.visit_id) like :patient )");
		}
		if(CommonFun.isNe(params.get("sort"))) {
			params.put("sort", " a.create_time desc ");
		}
		return executeQueryLimit(sql.toString(), params);
	}
	
	public Record getAutoCommonByID(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select id,state,end_time,patient_id,visit_id,cost_time,demo_resp,his_req from "+V_TABLE_NAME+"(nolock) where id = :id ");
		return executeQuerySingleRecord(sql.toString(), params);
	}
	public Record getAutoAuditByCommonID(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select id,create_time,state,zdy_cost_time,hyt_cost_time from auto_audit(nolock) where common_id = :id");
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	public Result queryImic(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select a.ip, vhip.interface_type_name, vhip.claim_type_name, a.cost_time costs, a.create_time create_time_common, b.*,datediff(ss, b.create_time,GETDATE()) cost_time, dbo.get_patient_xx(b.patient_id, b.visit_id) ");
		sql.append(" patient_name_s, c.name doctor_name, s.remark from "+V_TABLE_NAME+"(nolock) a " );
		sql.append(" inner join hospital_imic..imic_audit(nolock) b on a.id = b.common_id " );
		sql.append(" left join system_user_cooperop(nolock) c on b.doctor_no = c.no " );
		sql.append(" LEFT JOIN IADSCP..system_department sd ON b.deptment_code = sd.code");
		sql.append(" left join v_hisyb_in_patientvisit_all vhip on vhip.patient_id = a.patient_id and vhip.visit_id = a.visit_id ");
		sql.append(" left join system_result_state s on s.product_code = 'hospital_imic' and s.manage_state = b.state ");
		//sql.append(" where a.doctor_on = :doctor_no " );
		sql.append(" where b.is_after is null and b.id is not null ");
		if(!CommonFun.isNe(params.get("start_time"))) {
			sql.append(" and a.create_time >= :start_time ");
		}
		if(!CommonFun.isNe(params.get("end_time"))) {
			sql.append(" and a.create_time <= :end_time ");
		}
		if(!CommonFun.isNe(params.get("doctor_no"))) {
			sql.append(" and b.doctor_no = :doctor_no ");
		}
		if(!CommonFun.isNe(params.get("doctor"))) {
			params.put("doctor", "%" + params.get("doctor")+ "%");
			sql.append(" and ( b.doctor_no like :doctor or c.name like :doctor )");
		}
		if(!CommonFun.isNe(params.get("patient"))) {
			params.put("patient", "%" + params.get("patient")+ "%");
			sql.append(" and ( b.patient_id like :patient or b.patient_name like :patient )");
		}

		if(!CommonFun.isNe(params.get("dept"))) {
			params.put("dept", "%" + params.get("dept")+ "%");
			sql.append(" and ( b.deptment_code like :dept or b.deptment_name like :dept )");
		}
		if(!CommonFun.isNe(params.get("remark"))) {
			params.put("remark", "%" + params.get("remark")+ "%");
			sql.append(" and s.remark like :remark ");
		}
		if(!CommonFun.isNe(params.get("d_type"))) {
			if(params.get("d_type") instanceof java.lang.String) {
				sql.append(" and b.d_type = :d_type ");
			}else {
				sql.append(" and b.d_type in (:d_type) ");
			}
		}
		if(!CommonFun.isNe(params.get("sys_audit_result"))) {
			if(params.get("sys_audit_result") instanceof java.lang.String) {
				sql.append(" and b.sys_audit_result = :sys_audit_result ");
			}else {
				sql.append(" and b.sys_audit_result in (:sys_audit_result) ");
			}
		}
		//当前用户机构信息为空  并且患者部门Id不为空
		if (!CommonFun.isNe(params.get("jigou_id"))){
			sql.append(" and ( sd.jigid = :jigou_id");
			sql.append(" or  b.deptment_code is null or b.deptment_code='' ) ");
		}
		if(CommonFun.isNe(params.get("sort"))) {
			params.put("sort", " a.create_time desc ");
		}
		return executeQueryLimit(sql.toString(), params);
	}
	
	
	
	public Record getDept(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM system_department (nolock) ");
		sql.append("WHERE id = :department          ");
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	public Result queryImicByDept(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append(" select a.ip, vhip.interface_type_name, vhip.claim_type_name, a.cost_time costs, a.create_time create_time_common, b.*,datediff(ss, b.create_time,GETDATE()) cost_time, dbo.get_patient_xx(b.patient_id, b.visit_id) ");
		sql.append(" patient_name_s, c.name doctor_name, s.remark from "+V_TABLE_NAME+"(nolock) a " );
		sql.append(" inner join hospital_imic..imic_audit(nolock) b on a.id = b.common_id " );
		sql.append(" left join system_user_cooperop(nolock) c on b.doctor_no = c.no " );
		sql.append(" LEFT JOIN IADSCP..system_department sd ON b.deptment_code = sd.code");
		sql.append(" left join v_hisyb_in_patientvisit_all vhip on vhip.patient_id = a.patient_id and vhip.visit_id = a.visit_id ");
		sql.append(" left join system_result_state s on s.product_code = 'hospital_imic' and s.manage_state = b.state ");
		sql.append(" where b.is_after is null and b.id is not null ");
		sql.append(" and  b.deptment_code = :dept_code ");
		if(!CommonFun.isNe(params.get("start_time"))) {
			sql.append("and a.create_time >= :start_time ");
		}
		if(!CommonFun.isNe(params.get("end_time"))) {
			sql.append("and a.create_time <= :end_time ");
		}
		if(!CommonFun.isNe(params.get("doctor_no"))) {
			sql.append("and b.doctor_no = :doctor_no ");
		}
		if(!CommonFun.isNe(params.get("doctor"))) {
			params.put("doctor", "%" + params.get("doctor")+ "%");
			sql.append("and ( b.doctor_no like :doctor or c.name like :doctor )");
		}
		if(!CommonFun.isNe(params.get("patient"))) {
			params.put("patient", "%" + params.get("patient")+ "%");
			sql.append("and ( b.patient_id like :patient or b.patient_name like :patient )");
		}
		
		if(!CommonFun.isNe(params.get("remark"))) {
			params.put("remark", "%" + params.get("remark")+ "%");
			sql.append(" and s.remark like :remark ");
		}
		if(!CommonFun.isNe(params.get("d_type"))) {
			if(params.get("d_type") instanceof java.lang.String) {
				sql.append(" and b.d_type = :d_type ");
			}else {
				sql.append(" and b.d_type in (:d_type) ");
			}
		}
		if(!CommonFun.isNe(params.get("sys_audit_result"))) {
			if(params.get("sys_audit_result") instanceof java.lang.String) {
				sql.append(" and b.sys_audit_result = :sys_audit_result ");
			}else {
				sql.append(" and b.sys_audit_result in (:sys_audit_result) ");
			}
		}
		//当前用户机构信息不为空  并且患者部门Id不为空
		if (!CommonFun.isNe(params.get("jigou_id"))){
			sql.append(" and (sd.jigid = :jigou_id or b.deptment_code is null)");
		}
		if(CommonFun.isNe(params.get("sort"))) {
			params.put("sort", " a.create_time desc ");
		}
		return executeQueryLimit(sql.toString(), params);
	}
	
	
	
	
	public Result queryYwlsb(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("select b.*,datediff(ss, b.create_time,GETDATE()) cost_time, " );
		sql.append(" dbo.get_patient_xx(b.patient_id, b.visit_id) patient_name, c.name doctor_name from  " );
		sql.append(" hospital_autopa..ywlsb_auto_audit(nolock) b  " );
		sql.append(" left join system_user_cooperop(nolock) c on b.doctor_no = c.no " );
		sql.append(" inner join his_patient(nolock) d on b.patient_id = d.patient_id " );
		sql.append("where b.is_after is null ");
		if(!CommonFun.isNe(params.get("start_time"))) {
			sql.append("and b.create_time >= :start_time ");
		}
		if(!CommonFun.isNe(params.get("end_time"))) {
			sql.append("and b.create_time <= :end_time ");
		}
		
		if(!CommonFun.isNe(params.get("doctor"))) {
			String[] split = ((String) params.get("doctor")).split(",");
			params.put("doctor", split);
			sql.append("and b.doctor_no in (:doctor)");
		}
		if(!CommonFun.isNe(params.get("dept_code"))) {
			String[] split = ((String) params.get("dept_code")).split(",");
			params.put("dept_code", split);
			sql.append("and b.dept_code in (:dept_code) ");
		}
		//患者
		if(!CommonFun.isNe(params.get("patientid"))) {
			params.put("patientid", "%" + params.get("patientid")+ "%");
			sql.append("and (d.patient_id like :patientid or d.patient_no like :patientid or  d.patient_name like :patientid )");
		}
		if (!CommonFun.isNe(params.get("state_query"))) {
			if (params.get("state_query") instanceof String) {
				sql.append("and b.state=:state_query ");
			}else {
				sql.append("and b.state in (:state_query) ");
			}
		}
		/*if(!CommonFun.isNe(params.get("yz_cf_no"))) {
			params.put("yz_cf_no", ((String)params.get("yz_cf_no")).split(","));
			sql.append("and exists (select 1 from hospital_autopa..YWLSB_AUDIT_ORDERS (nolock) where b.id=auto_audit_id and group_id in (:yz_cf_no) ) ");
		}*/
		
		if(!CommonFun.isNe(params.get("yz_cf_no"))) {
			params.put("yz_cf_no", ((String)params.get("yz_cf_no")).split("、"));
			sql.append("and exists (select 1 from hospital_autopa..YWLSB_AUDIT_ORDERS (nolock) where b.id=auto_audit_id and (group_id in (:yz_cf_no) )) ");
		}
		
		if(!CommonFun.isNe(params.get("yaoshi_name"))) {
			params.put("yaoshi_name", "%" + params.get("yaoshi_name")+ "%");
			sql.append("and (b.yaoshi_name like :yaoshi_name OR b.yaoshi_id like :yaoshi_name )");
		}
		
		if (!CommonFun.isNe(params.get("d_type_query"))) {
			if (params.get("d_type_query") instanceof String) {
				sql.append("and b.d_type=:d_type_query ");
			}else {
				sql.append("and b.d_type in (:d_type_query) ");
			}
		}
		
		params.put("sort", " create_time desc ");
		return executeQueryLimit(sql.toString(), params);
	}
	
	public String insert(Map<String, Object> params) throws Exception {
		String id = CommonFun.getITEMID();
		params.put("id", id);
		executeInsert(TABLE_NAME, params);
		return id;
	}
	
	public String insertByProc(Map<String, Object> params) throws Exception {
		String id = CommonFun.getITEMID();
		params.put("id", id);
		execute("exec scr_insert_auto_common :d_type, :dept_code, :create_time, :patient_id, :audit_source_fk, :ip, :im_version, :visit_id, :doctor_no, :id, :is_after", params);
		return id;
	}
	
	/*
	 * public void update(Map<String, Object> params) throws Exception { Record
	 * record = new Record(); record.put("id", params.remove("id"));
	 * executeUpdate(TABLE_NAME, params, record); }
	 */
	
	public Record get(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		/*sql.append(" select a.* from " + TABLE_NAME + " (nolock) a ");
		sql.append(" where a.id = :id ");*/
		sql.append("select a.*,c.thirdt_request,c.thirdt_response ");
		sql.append("from "+V_TABLE_NAME+" (nolock) a                 ");
		sql.append("left join auto_audit (nolock) b                ");
		sql.append("on a.id = b.common_id                          ");
		sql.append("left join hospital_autopa..check_data_parms (nolock) c ");
		sql.append("on b.id=c.auto_audit_id ");
		sql.append(" where a.id = :id ");
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	public Record queryVisitid(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		StringBuffer sql2 = new StringBuffer();
		sql.append(" SELECT visit_id FROM his_in_patientvisit(nolock) where patient_id =:patient_id AND PATINDEX('%[^0-9.]%', visit_id) = 0 ");
		Record record = executeQuerySingleRecord(sql.toString(), params);
		if (record == null) {
			sql2.append("select max(cast(visit_id as int)) visit_id from his_in_patientvisit(nolock) where patient_id =:patient_id ");
		}else {
			sql2.append("select max(visit_id) visit_id from his_in_patientvisit(nolock) where patient_id =:patient_id ");
		}
		return executeQuerySingleRecord(sql2.toString(), params);
	}
	
	public Result hadSubmit(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("exec " + DOCTOR_AUDIT_PROC + " :auto_audit_id");
		return executeQuery(sql.toString(), params);
	}
	
	public void insertMx(Map<String, Object> params) throws Exception {
		executeInsert(TABLE_NAME_MX, params);
	}
}
