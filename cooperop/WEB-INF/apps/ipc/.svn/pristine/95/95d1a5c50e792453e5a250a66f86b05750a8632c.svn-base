package cn.crtech.cooperop.ipc.dao;

import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class CommentDao extends BaseDao{

	public void saveSetHLLS(Map<String, Object> map) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("EXEC pharmacist_audit_result ");
		sql.append(" @auto_audit_id= :auto_audit_id, ");
		sql.append(" @check_results= :check_results ");
		execute(sql.toString(), map);
	}

	public Result queryRealYizu(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select PATIENT_ID,VISIT_ID,order_no INTO #AAA from V_YS_DETAILS_ORDER where   ");
		if(params.get("p_keys") instanceof String) {
			sql.append("(p_key = :p_keys )                                                                  ");
		}else {
			sql.append("(p_key in (:p_keys))                                                                  ");
		}
		//sql.append("or (patient_id= :patient_id and visit_id= :visit_id and order_no= :order_no)   ");
		sql.append(" GROUP BY patient_id,visit_id,order_no ");
		sql.append("select a.*,    ");
		sql.append(" dbo.get_inorders_orderclassNAME_YWLSB(a.patient_id,a.visit_id,a.order_no,a.order_sub_no) yp ");
		sql.append(" from V_YS_DETAILS_ORDER a inner join #AAA b ");
		sql.append("on a.patient_id=b.patient_id                                                  ");
		sql.append("and a.visit_id=b.visit_id                                                     ");
		sql.append("and a.order_no= b.order_no order by a.patient_id,a.visit_id,a.ENTER_DATE_TIME          ");
		sql.append("drop table #AAA                                                               ");
		return executeQuery(sql.toString(), params);
	}
	
	public int insertPharmacistCheckPass(Map<String, Object> params) throws Exception {
		return executeInsert("pharmacist_check_pass", params);
	}
	
	public Record queryPass(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		if (!CommonFun.isNe(params.get("auto_audit_id"))) {
			sql.append("select a.* from pharmacist_check_pass(nolock) a ");
			//sql.append("where a.description = (select top 1 description from ywlsb_audit_orders_check_result(nolock) ");
			sql.append("where replace(replace(replace(replace(replace(replace(replace(a.description,CHAR(13),''), CHAR (10),''),'[',''),'<p>',''),'</p>',''),'<br>',''),' ','') = (select top 1 replace(replace(replace(replace(replace(replace(replace(description,CHAR(13),''), CHAR (10),''),'[',''),'<p>',''),'</p>',''),'<br>',''),' ','') from ywlsb_audit_orders_check_result(nolock) ");
			sql.append(" where auto_audit_id= :auto_audit_id and  ");
			sql.append(" check_result_info_id= :check_result_info_id)");
		}
		return executeQuerySingleRecord(sql.toString(),params);
	}
	
	public int updatePharmacistCheckPass(Map<String, Object> params) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("select top 1 description from ywlsb_audit_orders_check_result(nolock) ");
		sql.append(" where auto_audit_id= :auto_audit_id and  ");
		sql.append(" check_result_info_id= :check_result_info_id");
		Record record = executeQuerySingleRecord(sql.toString(), params);
		params.remove("auto_audit_id");
		params.remove("check_result_info_id");
		map.put("description", record.get("description"));
		return executeUpdate("pharmacist_check_pass", params, map);
	}
}
