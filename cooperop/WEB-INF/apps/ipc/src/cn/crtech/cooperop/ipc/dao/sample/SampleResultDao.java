package cn.crtech.cooperop.ipc.dao.sample;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;

public class SampleResultDao extends BaseDao{
	
	private final static String TABLE_NAME = "comment_sample_orders_result";
	
	public int insert(Map<String, Object> params) throws Exception {
		return executeInsert(TABLE_NAME, params);
	}
	
	public int update(Map<String, Object> params) throws Exception {
		Record r =new Record();
		r.put("id", params.remove("id"));
		return executeUpdate(TABLE_NAME, params,r);
	}
	
	public String delete(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" delete from " + TABLE_NAME + " where ");
		sql.append(" sample_orders_id in (:sample_orders_id_set) ");
		return execute(sql.toString(), params);
	}
	
	public Result queryByParentId(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select dsc.* , dsc.id pharmacist_comment_id from comment_sample_orders_result csor (nolock) ");
		sql.append(" left join dict_sys_comment dsc (nolock) on csor.pharmacist_comment_id = dsc.system_code ");
		sql.append("  where sample_orders_id = :sample_orders_id");
		sql.append(" and sample_patients_id = :sample_patients_id ");
		return executeQuery(sql.toString(), params);
	}
	
	public Result queryChecking(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select pharmacist_comment_id from ");
		sql.append(TABLE_NAME);
		sql.append(" (nolock) where order_no = :order_no");
		sql.append(" and sample_id = :sample_id");
		sql.append(" and patient_id = :patient_id ");
		sql.append(" and visit_id = :visit_id");
		return executeQuery(sql.toString(), params);
	}
	
}
