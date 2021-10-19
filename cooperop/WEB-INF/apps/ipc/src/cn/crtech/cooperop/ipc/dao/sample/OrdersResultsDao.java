package cn.crtech.cooperop.ipc.dao.sample;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class OrdersResultsDao extends BaseDao{
	
	private final static String COMMENT_SAMPLE_ORDERS = "comment_sample_orders";
	private final static String TMP_COMMENT_SAMPLE_ORDERS = "TMP_comment_sample_orders";
	
	public int insert(Map<String, Object> params) throws Exception {
		return executeInsert(COMMENT_SAMPLE_ORDERS, params);
	}
	
	public void insertTMP(Map<String, Object> params) throws Exception {
		executeInsert(TMP_COMMENT_SAMPLE_ORDERS, params);
	}
	
	public void delete(Map<String, Object> params) throws Exception {
		Record r = new Record();
		r.put("sample_id", params.get("sample_id"));
		if (!CommonFun.isNe(params.get("patient_id"))) {
			r.put("patient_id", params.get("patient_id"));
		}
		executeDelete(COMMENT_SAMPLE_ORDERS, r);
	}
	
	public void deleteTMP(Map<String, Object> params) throws Exception {
		executeDelete(TMP_COMMENT_SAMPLE_ORDERS, params);
	}

	public void updateByPS(Map<String, Object> params) throws Exception {
		Record r = new Record();
		Record p = new Record();
		r.put("sample_id", params.get("sample_id"));
		r.put("patient_id", params.get("patient_id"));
		p.put("is_active", params.get("is_active"));
		executeUpdate(COMMENT_SAMPLE_ORDERS, p,r);
	}

	public void deleteNotActive(Map<String, Object> params) throws Exception {
		params.put("is_active", "0");
		executeDelete(COMMENT_SAMPLE_ORDERS, params);
	}
	
	public int update(Map<String, Object> params) throws Exception {
		Record r =new Record();
		r.put("id", params.remove("id"));
		return executeUpdate(COMMENT_SAMPLE_ORDERS, params,r);
	}
	
	public int updateBySamId(Map<String, Object> params) throws Exception {
		Record r = new Record();
		Record p = new Record();
		r.put("sample_id", params.get("sample_id"));
		p.put("is_active", params.get("is_active"));
		return executeUpdate(COMMENT_SAMPLE_ORDERS, p, r);
	}
	
	public int updateMRHL(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" update comment_sample_orders set comment_result = 1 ,comment_content = :comment_content  ");
		sql.append(" 		where sample_id= :sample_id and patient_id= :patient_id           ");
		sql.append(" 		and visit_id = :visit_id and comment_result is null                     ");
		return executeUpdate(sql.toString(), params);
	}
	
	public String updateByOrderNo(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" update " + COMMENT_SAMPLE_ORDERS + " set comment_content = :comment_content, ");
		sql.append(" comment_result =:comment_result where 1 = 1");
		sql.append(" and id in (:sample_orders_id_set) and sample_patients_id = :sample_patients_id");
		return execute(sql.toString(), params);
	}
	
	
	public Result queryOrders(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select yo.tag zu,");
		sql.append("  dbo.get_inorders_ENTERDATETIME_YWLSB(yo.patient_id,yo.visit_id,yo.group_id,yo.order_no,yo.order_sub_no) stime,");
		sql.append("  dbo.get_inorders_ADMINISTRATION_NAME_YWLSB(yo.patient_id,yo.visit_id,yo.group_id,yo.order_no,yo.order_sub_no) gy,");
		sql.append("  dbo.get_inorders_REPEATINDICATOR_YWLSB(yo.patient_id,yo.visit_id,yo.group_id,yo.order_no,yo.order_sub_no) lc,");
		sql.append("  dbo.get_inorders_orderclassNAME_YWLSB(yo.patient_id,yo.visit_id,yo.group_id,yo.order_no,yo.order_sub_no) yp,");
		sql.append(" yo.*,cso.id order_result_id,dg.property_toxi,dg.jixing,cso.sample_id,cso.comment_result,ss.level_name as sys_check_level_name,cso.sample_patients_id,  ");
		sql.append(" dhu.user_name kz_doctor, ");
		sql.append(" (select ORDERTAG_SHOW +':' + ORDERTAGNAME + ',' from           ");
		sql.append(" 	YWLSB_ORDERS_TAG                       ");
		sql.append(" 	where patient_id = cso.patient_id      ");
		sql.append(" 	and visit_id = cso.visit_id            ");
		sql.append(" 	and group_id = cso.group_id            ");
		sql.append(" 	and order_no = cso.order_no            ");
		sql.append(" 	GROUP BY ORDERTAG_SHOW,ORDERTAGNAME    ");
		sql.append(" 	for xml path('')                       ");
		sql.append(" ) as tags                                 ");
		sql.append(" ,dg.is_gwyp ");
		sql.append(" from comment_sample_orders(nolock) cso ");
		sql.append(" left join ywlsb_orders(nolock) yo on ");
		sql.append(" (cso.patient_id  = yo.patient_id and cso.visit_id = yo.visit_id and cso.group_id = yo.group_id and cso.order_no = yo.order_no ) ");
		sql.append(" left join sys_check_level(nolock) ss on ss.level_code = yo.auto_audit_level and ss.product_code = 'ipc' ");
		sql.append(" left join dict_his_users(nolock) dhu on dhu.p_key=cso.doctor_no ");
		sql.append(" left join v_dict_drug (nolock) dg on yo.order_code = dg.drug_code ");
		sql.append(" where cso.sample_id = :sample_id and cso.patient_id = :patient_id               ");
		sql.append(" order by yo.enter_date_time desc, yo.group_id, yo.ORDER_NO ,cast ( yo.order_sub_no AS INT )");
		return executeQuery(sql.toString(),params);
	}
	
	public Result queryCRorders(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select *,dbo.get_patient_xx ( a.patient_id, a.visit_id ) patient_name  from v_comment_order a (nolock)                                              ");
		sql.append(" where exists(select 1 from ywlsb_orders_comment b                             ");
		sql.append(" where a.patient_id=b.patient_id and a.visit_id=b.visit_id                     ");
		sql.append(" and a.group_id=b.group_id and a.order_no=b.order_no and b.comment_result='0') ");
		sql.append(" and a.doctor_no= :doctor_no                                                        ");
		sql.append(" order by                                                                      ");
		sql.append(" 	a.comment_datetime desc,                                                   ");
		sql.append(" 	a.dept_name,                                                               ");
		sql.append(" 	a.patient_id,                                                              ");
		sql.append(" 	a.visit_id,                                                                ");
		sql.append(" 	a.group_id,                                                                ");
		sql.append(" 	a.order_no,                                                                ");
		sql.append(" 	a.order_sub_no                                                             ");
		return executeQuery(sql.toString(),params);                                                                     
	}
	
	public Record getNullNum (Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) num from ");
		sql.append(COMMENT_SAMPLE_ORDERS);
		sql.append(" (nolock) where sample_id = :sample_id ");
		sql.append(" and patient_id = :patient_id ");
		sql.append(" and comment_result is null");
		return executeQuerySingleRecord(sql.toString(),params);
	}
	
	public Record get(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from "+COMMENT_SAMPLE_ORDERS+"(nolock) where id = :id");
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	public Record getByNo(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from "+COMMENT_SAMPLE_ORDERS+"(nolock) ");
		sql.append(" where id = :sample_orders_id and sample_id = :sample_id");
		sql.append(" and patient_id = :patient_id and visit_id = :visit_id");
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	public Result getHistoryByNo(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from ywlsb_orders_comment (nolock) ");
		sql.append(" where group_id = :group_id and order_no = :order_no ");
		sql.append(" and patient_id = :patient_id and visit_id = :visit_id");
		return executeQuery(sql.toString(), params);
	}

	
	public Result queryLastResult(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from ywlsb_orders_comment  (nolock)              ");
		sql.append(" where patient_id = :patient_id and visit_id = :visit_id   ");
		sql.append(" and group_id = :group_id and order_no =:order_no          ");
		return executeQuery(sql.toString(), params);
	}
	
	public Result queryQZResult(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select comment_id pharmacist_comment_id from ywlsb_audit_orders_comment  (nolock)               ");
		sql.append(" where patient_id= :patient_id and  ");
		sql.append(" visit_id = :visit_id and order_no = :order_no and group_id = :group_id ");
		return executeQuery(sql.toString(), params);
	}	
	
	public Result queryTmp(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from "+TMP_COMMENT_SAMPLE_ORDERS);
		sql.append(" (nolock) where GZID= :GZID");
		return executeQuery(sql.toString(), params);
	}

	public Result queryTags(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select ordertagbh,ordertag_shuom,ordertag_show "); 
		sql.append(" from hospital_common..DICT_SYS_ORDERS_TAG (nolock) where beactive = '是' "); 
		return executeQuery(sql.toString(), params);
	}	
}
