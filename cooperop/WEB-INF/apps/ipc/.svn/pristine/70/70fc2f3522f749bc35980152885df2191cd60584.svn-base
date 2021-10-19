package cn.crtech.cooperop.ipc.dao;

import java.util.Date;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.service.ProductmanagetService;



public class CommentFlowDao extends BaseDao{


private final static String TABLE_NAME = "comment_sample";//流程表
	
public Result query(Map<String, Object> params) throws Exception {
	StringBuffer sql = new StringBuffer();
	sql.append("select * from  ");
	sql.append(TABLE_NAME);
	sql.append("(nolock) where 1= 1");
	return executeQuery(sql.toString());
}

public Record get(String id) throws Exception {
	Record params = new Record();
	params.put("id", id);
	StringBuffer sql = new StringBuffer();
	sql.append("select * from "+TABLE_NAME+"(nolock) where id = :id");
	return executeQuerySingleRecord(sql.toString(), params);
}


public Record getAuto_audit_id(String id) throws Exception {
	Record params = new Record();
	params.put("auto_audit_id", id);
	StringBuffer sql = new StringBuffer();
	sql.append("select * from "+TABLE_NAME+"(nolock) where auto_audit_id = :auto_audit_id");
	return executeQuerySingleRecord(sql.toString(), params);
}

public String insert(Map<String, Object> params) throws Exception {
	params.remove("id");
	String id = CommonFun.getITEMID();
	params.put("id", id);
	params.put("create_time", CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
	executeInsert(TABLE_NAME, params);
	return id;
}

public int update(Map<String, Object> params) throws Exception {
	Record r = new Record();
	r.put("id", params.remove("id"));
	return executeUpdate(TABLE_NAME, params,r);
}

public void updateByPtientdjbh(Map<String, Object> params) throws Exception {
	StringBuffer sql = new StringBuffer();
	sql.append(" update                                        ");
	sql.append(" a                                             ");
	sql.append(" set                                           ");
	sql.append(" a.comment_finish_time = :comment_finish_time, ");
	sql.append(" a.state = :state                              ");
	sql.append(" from                                          ");
	sql.append(" (select * from                                ");
	sql.append(" comment_sample  (nolock)                              ");
	sql.append(" where id =                                    ");
	sql.append(" (select top 1 sample_id from                  ");
	sql.append(" comment_sample_patients (nolock)                      ");
	sql.append(" where djbh = :djbh                            ");
	sql.append(" )) a                                          ");
	execute(sql.toString(), params);
}

public void delete(Map<String, Object> params) throws Exception {
	Record r = new Record();
	r.put("id", params.remove("id"));
	executeDelete(TABLE_NAME, r);
}

public Result getQuestions(Map<String, Object> params) throws Exception {
	StringBuffer sql = new StringBuffer();
	sql.append(" select");
	sql.append(" 	ya.* ,scr.sort_name,ds.level_name sys_check_level_name,ds.level_star star_level");
	sql.append(" from                                                                                                          ");
	sql.append(" 	ywlsb_audit_orders_check_result (nolock) ya                                                                         ");
	sql.append(" 	left join sys_check_level (nolock) ds on ya.auto_audit_level = ds.level_code                               ");
	sql.append(" and ds.product_code = '"+ProductmanagetService.IPC+"' ");
	sql.append(" 	left join map_common_regulation (nolock) crs on ya.auto_audit_sort_id = crs.thirdt_code and ya.auto_audit_type = crs.check_type");
	sql.append(" left join sys_common_regulation (nolock) scr on crs.sys_p_key=scr.p_key ");
	sql.append(" and scr.product_code = '"+ProductmanagetService.IPC+"' ");
	sql.append(" where                                                         ");
	sql.append(" 	auto_audit_id = (                                          ");
	sql.append(" select                                                        ");
	sql.append(" 	top 1 t2.id                                                ");
	sql.append(" from                                                          ");
	sql.append(" 	ywlsb_audit_orders_check_result (nolock) t1                         ");
	sql.append(" 	inner join ywlsb_auto_audit (nolock) t2 on t2.id = t1.auto_audit_id "); 
	sql.append(" where                                                         ");
	sql.append(" 	t1.patient_id = :patient_id                                    ");
	sql.append(" 	and t1.visit_id = :visit_id                                      ");
	sql.append(" 	and t1.order_no = :order_no                                ");
	sql.append(" 	and t1.group_id = :group_id                                ");
	sql.append(" group by                                                      ");
	sql.append(" 	t2.id ,t2.create_time                                      ");
	sql.append(" 	order by t2.create_time  desc                                  ");
	sql.append(" 	)                                                          ");
	sql.append(" 	and patient_id = :patient_id                                   ");
	sql.append(" 	and visit_id = :visit_id                                        ");
	sql.append(" 	and order_no = :order_no                                 ");
	sql.append(" 	and group_id = :group_id                                 ");
	return executeQuery(sql.toString(), params);
}

	public Result getBeforeComment(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from v_comment_order_check_comment (nolock)");
		sql.append(" where patient_id = :patient_id and visit_id = :visit_id and order_no = :order_no and group_id = :group_id ");
		sql.append(" and auto_audit_id = :auto_audit_id");
		return executeQuery(sql.toString(), params);
	}

	public void updateCommentUser(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" update                                       ");
		sql.append(" comment_sample                               ");
		sql.append(" set                                          ");
		sql.append(" comment_user =                               ");
		sql.append(" Replace(Cast(comment_user as varchar(2000)), ");
		sql.append(" :old_comment_user, :new_comment_user )       ");
		sql.append(" where                                        ");
		sql.append(" id = :sample_id                              ");
		execute(sql.toString(),params);
	}
}
