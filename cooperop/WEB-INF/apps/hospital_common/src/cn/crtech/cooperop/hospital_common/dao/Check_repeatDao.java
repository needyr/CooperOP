package cn.crtech.cooperop.hospital_common.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;

public class Check_repeatDao extends BaseDao {
	
	public Result queryAutoAuditOrders(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("select a.PATIENT_ID,a.VISIT_ID,a.REPEAT_INDICATOR,a.ORDER_TEXT,a.ORDER_CODE,a.doctor_no, ");
		sql.append(" a.auto_audit_id,a.shl,a.DOSAGE,a.DOSAGE_UNITS,a.ADMINISTRATION,a.FREQUENCY,a.beizhu,a.group_id,a.ORDER_NO,a.p_type ");
		sql.append(" from auto_audit_orders (nolock) a ");
		sql.append(" inner join dict_his_drug(nolock) b on a.ORDER_CODE=b.drug_code ");
		sql.append(" inner join dict_his_orderstatus(nolock) dho on a.ORDER_STATUS=dho.orderstatus_CODE ");
		sql.append(" inner join dict_sys_orderstatus(nolock) dso on dho.SYS_P_KEY=dso.P_KEY ");
		sql.append(" where a.auto_audit_id=:auto_audit_id and dso.orderstatus_CODE in ('0','1') ");
		return executeQuery(sql.toString(), params);
	}
	
	public Record getCrCheckResultsCount(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("select count(1) count from CR_Check_results(nolock) where patient_id='"+params.get("patient_id")+"' ");
		sql.append("and visit_id='"+params.get("visit_id")+"'");
		sql.append("and group_id='"+params.get("group_id")+"'");
		sql.append("and (order_no='"+params.get("order_no")+"' or p_type = '2')");
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	public void insert_cr(Map<String, Object> params) throws Exception {
		executeInsert("cr_check_results", params);
	}
	
	public Result queryAutoAuditOrders_old(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("select a.PATIENT_ID,a.VISIT_ID,a.REPEAT_INDICATOR,a.ORDER_TEXT,a.ORDER_CODE,a.doctor_no, ");
		sql.append(" a.auto_audit_id,a.shl,a.DOSAGE,a.DOSAGE_UNITS,a.ADMINISTRATION,a.FREQUENCY,a.beizhu,a.group_id,a.ORDER_NO,a.p_type ");
		sql.append(" from auto_audit_orders (nolock) a ");
		sql.append(" inner join dict_his_drug(nolock) b on a.ORDER_CODE=b.drug_code ");
		sql.append(" inner join dict_his_orderstatus(nolock) dho on a.ORDER_STATUS=dho.orderstatus_CODE ");
		sql.append(" inner join dict_sys_orderstatus(nolock) dso on dho.SYS_P_KEY=dso.P_KEY ");
		sql.append(" where a.auto_audit_id = '"+params.get("id")+"' ");
		sql.append(" and dso.orderstatus_CODE in ('0','1') ");
		return executeQuery(sql.toString(), params);
	}
	
	public Record getRepetitionAudit(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("select top 1 id,state from auto_audit(nolock) where id<>:auto_audit_id and patient_id=:patient_id and visit_id=:visit_id and state in ('Y','D','DS','DQ') order by create_time desc");
		return executeQuerySingleRecord(sql.toString(), params);
	}
}
