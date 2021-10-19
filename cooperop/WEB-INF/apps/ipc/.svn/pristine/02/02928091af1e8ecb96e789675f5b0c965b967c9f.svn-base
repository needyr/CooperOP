package cn.crtech.cooperop.ipc.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class CheckAndCommentDao extends BaseDao{

	public Result queryCheckAndCommentInfo(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select a.*,dbo.GET_PATIENT_XX(a.patient_id,a.visit_id) as patient_name ");
		sql.append(" from V_YS_DETAILST_ORDER_issue(nolock)  a  ");
		sql.append(" where a.patient_id = :patient_id and a.visit_id = :visit_id");
		sql.append(" order by a.check_datetime desc ");
		return executeQuery(sql.toString(), params);
	}
	
	public Result queryCommentAdvice(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select                                       ");
		sql.append(" top 1 a.comment_content,                     ");
		sql.append(" '['+stuff(                                   ");
		sql.append(" (SELECT                                      ");
		sql.append(" 	'['+comment_name+'] '                     ");
		sql.append(" FROM                                         ");
		sql.append(" 	V_COMMENT_ORDER_CHECK_COMMENT(nolock)             ");
		sql.append(" WHERE                                        ");
		sql.append(" 	patient_id = a.patient_id                 ");
		sql.append(" 	AND visit_id = a.visit_id                 ");
		sql.append(" 	AND order_no = a.order_no                 ");
		sql.append(" 	and auto_audit_id = a.auto_audit_id       ");
		sql.append(" 	for xml path('')),1,1,'') as content      ");
		sql.append(" from V_COMMENT_ORDER_CHECK_COMMENT a  (nolock)       ");
		sql.append(" WHERE                                        ");
		sql.append(" 	patient_id = :patient_id                  ");
		sql.append(" 	AND visit_id = :visit_id                  ");
		sql.append(" 	AND order_no = :order_no                  ");
		//sql.append(" 	and auto_audit_id = :auto_audit_id        ");
		return executeQuery(sql.toString(), params);
	}
	
	public Result queryRealYizu(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select PATIENT_ID,VISIT_ID,order_no INTO #AAA from V_YS_DETAILS_ORDER (nolock) where   ");
		if(params.get("p_keys") instanceof String) {
			sql.append("(p_key = :p_keys)                                                                  ");
		}else {
			String[] p = (String[])params.get("p_keys");
			if (p.length == 1 && CommonFun.isNe(p[0])) {
				sql.append("PATIENT_ID= :patient_id and visit_id= :visit_id and order_no= :order_no and group_id= :group_id ");
			}else {
				sql.append("(p_key in (:p_keys))                                                                  ");
			}
		}
		sql.append(" GROUP BY patient_id,visit_id,order_no ");
		sql.append("select a.* from V_YS_DETAILS_ORDER(nolock) a inner join #AAA b                          ");
		sql.append("on a.patient_id=b.patient_id                                                  ");
		sql.append("and a.visit_id=b.visit_id       ");
		sql.append("and a.order_no= b.order_no order by a.order_no,a.order_sub_no,a.ENTER_DATE_TIME          ");
		sql.append("drop table #AAA                                                               ");
		return executeQuery(sql.toString(), params);
	}
}
