package cn.crtech.cooperop.hospital_common.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Result;

public class TPNDao extends BaseDao {
	
	public Result queryTpnRadar(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from            ");
		sql.append(" cr_tmp_fenxitux_radarchart(nolock) ");
		sql.append(" where                    ");
		sql.append(" patient_id = :patient_id ");
		sql.append(" and visit_id= :visit_id  ");
		sql.append(" and group_id= :group_id  ");
		sql.append(" and order_no= :order_no  ");
		sql.append(" and auto_audit_id= :auto_audit_id  ");
		return executeQuery(sql.toString(), params);
	}

	public Map<String, Object> getTPNData(Map<String, Object> map) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select b.patient_id,                                  ");
		sql.append(" b.visit_id,                                           ");
		sql.append(" b.group_id,                                           ");
		sql.append(" b.order_no,a.auto_audit_id                            ");
		sql.append(" from check_result_info(nolock) a                              ");
		sql.append(" left join auto_audit_orders(nolock) b on ','+a.order_id+',' like '%,'+b.p_key+',%'   ");
		sql.append(" where                                                 ");
		sql.append(" a.id = :check_result_info_id                          ");
		sql.append(" and a.auto_audit_id = :auto_audit_id                  ");
		sql.append(" GROUP BY b.patient_id,                                ");
		sql.append(" b.visit_id,                                           ");
		sql.append(" b.group_id,                                           ");
		sql.append(" b.order_no,a.auto_audit_id                            ");
		return executeQuerySingleRecord(sql.toString(), map);
	}

	public Map<String, Object> getTPNDataHistory(Map<String, Object> map) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select b.patient_id,                                  ");
		sql.append(" b.visit_id,                                           ");
		sql.append(" b.group_id,                                           ");
		sql.append(" b.order_no,a.auto_audit_id                            ");
		sql.append(" from YWLSB_AUDIT_ORDERS_check_result (nolock)a        ");
		sql.append(" left join YWLSB_AUDIT_ORDERS(nolock) b on ','+ a.Related_drugs_PKEY+',' like '%,'+b.P_KEY+',%'   ");
		sql.append(" where                                                 ");
		sql.append(" a.id = :check_result_info_id        ");
		sql.append(" and a.auto_audit_id = :auto_audit_id                  ");
		sql.append(" GROUP BY b.patient_id,                                ");
		sql.append(" b.visit_id,                                           ");
		sql.append(" b.group_id,                                           ");
		sql.append(" b.order_no,a.auto_audit_id                            ");
		return executeQuerySingleRecord(sql.toString(), map);
	}
}
