package cn.crtech.cooperop.hospital_common.dao.report;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class QueryhisDao extends BaseDao {

	public Result query_orders(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT a.* FROM V_his_in_orders_ALL  (nolock) a         ");
		sql.append(" LEFT JOIN  dict_his_drug  (nolock) b                    ");
		sql.append(" on a.ORDER_CODE = b.DRUG_CODE                           ");
		sql.append(" where a.patient_id = :patient_id	         				 ");
		sql.append(" and  a.visit_id = :visit_id            					 ");
	 
		if(!CommonFun.isNe(params.get("group_id"))) {
			sql.append("  and a.group_id = :group_id ");
		}
		
		if (!CommonFun.isNe(params.get("order_text"))) {
			params.put("order_text", "%" + params.get("order_text") + "%");
			sql.append("  and a.order_text like :order_text ");
		}
		if (!CommonFun.isNe(params.get("is_drug"))) {
			String is_drug=(String)params.get("is_drug");
			if(is_drug.equals("1")){
				sql.append("  and  b.DRUG_CODE is not null ");
			}			
		}		
		params.put("sort", " START_DATE_TIME,GROUP_ID,ORDER_NO,ORDER_SUB_NO ");
		return executeQueryLimit(sql.toString(),params);
	}
	
	
	public Record his_in_orders_list(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from V_his_in_patientvisit_all (nolock)  ");
		sql.append(" where patient_id = :patient_id	          ");
		sql.append(" and  visit_id = :visit_id             ");
		return executeQuerySingleRecord(sql.toString(),params);
	}
	
}
