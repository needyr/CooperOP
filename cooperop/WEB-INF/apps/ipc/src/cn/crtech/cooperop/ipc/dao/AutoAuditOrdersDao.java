package cn.crtech.cooperop.ipc.dao;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;

import java.util.Map;

import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.rdms.Result;

public class AutoAuditOrdersDao extends BaseDao {
	private final static String TABLE_NAME = "auto_audit_orders";//HIS患者信息
	private final static String V_TABLE_NAME = "V_auto_audit_orders";
	
	// 云端auto_audit_orders 数据源
	private final static String V_YUN_AUTO_AUDIT_ORDERS = "v_yun_auto_audit_orders";
	
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		/*sql.append(" SELECT");
		sql.append(" 	dbo.get_auditorders_GROUPTAG ( :auto_audit_id, aas.group_id, aas.ORDER_NO, aas.ORDER_SUB_NO ) AS zu,                  ");
		sql.append(" 	dsd.drug_code sys_drug_code,                                                                  ");
		sql.append(" 	aas.* ,dhg.is_gwyp,dhg.drug_message ");
		sql.append(" FROM                                                                                             ");
		sql.append(" 	auto_audit_orders(nolock) aas                                                                         ");
		sql.append(" 	LEFT JOIN ( SELECT drug_code, sys_p_key,is_gwyp,drug_message FROM v_dict_drug(nolock) GROUP BY drug_code, sys_p_key, is_gwyp,drug_message ) dhg"); 
		sql.append(" 	ON aas.ORDER_CODE = dhg.drug_code                                                             ");
		sql.append(" 	LEFT JOIN dict_sys_drug(nolock) dsd ON dsd.p_key = dhg.sys_p_key                                      ");
		sql.append(" WHERE                                                                                            ");
		sql.append(" 	aas.auto_audit_id = :auto_audit_id                                                                      ");
		sql.append(" ORDER BY                                                                                         ");
		sql.append(" 	aas.sys_order_status,                                                                             ");
		sql.append(" 	aas.ORDER_NO,                                                                 ");
		sql.append(" 	aas.ORDER_SUB_NO                                                                              ");*/
		sql.append(" select ");
		sql.append(" 	dbo.get_auditorders_grouptag (:auto_audit_id, group_id, order_no, order_sub_no ) as zu, * ");
		sql.append(" from ");
		sql.append(V_YUN_AUTO_AUDIT_ORDERS);
		sql.append(" where ");
		sql.append(" auto_audit_id = :auto_audit_id ");
		sql.append(" order by ");
		sql.append(" order_status, order_no, order_sub_no");
		return executeQuery(sql.toString(), params);
	}
	
	public Result queryDiagnosisAll(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select diagnosis_desc,diagnosis_type from his_in_diagnosis (nolock)");
		sql.append(" where patient_id= :patient_id and visit_id= :visit_id");
		sql.append(" group by diagnosis_desc,diagnosis_type ");
		return executeQuery(sql.toString(), params);
	}
	


	//与上面一个方法相同 ，多了一个列，目的是为了显示审查界面的信息
	public Result queryOrders(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select ori.*,dso.orderstatus_CODE sys_order_status,CONVERT(FLOAT,ori.dosage) as dosage2, '高危药品' xiangm,case when CHARINDEX('是',concat(b.value, c.value)) > 0 then '是' else '否' end xmz from "+TABLE_NAME+"(nolock) ori ");
		sql.append(" left join dict_his_drug a on ori.ORDER_CODE = a.drug_code");
		sql.append(" left join dict_his_drug_mx b on a.DRUG_CODE = b.drug_code and b.XIANGM = '高危药品'");
		sql.append(" left join dict_sys_drug_mx c on a.SYS_P_KEY = c.SYS_P_KEY and c.XIANGM = '高危药品' ");
		sql.append(" left join dict_his_orderstatus (nolock) dho on dho.orderstatus_CODE=ori.order_status ");
		sql.append(" left join dict_sys_orderstatus (nolock) dso on dho.sys_p_key=dso.p_key ");
//		sql.append(" where b.XIANGM = '高危药品' or c.XIANGM = '高危药品' ");
//		setParameter(params, "auto_audit_id", " and ori.auto_audit_id=:auto_audit_id", sql);
		sql.append(" where  ");
		setParameter(params, "auto_audit_id", " ori.auto_audit_id=:auto_audit_id", sql);
		if(CommonFun.isNe(params.get("order"))){
			sql.append(" order by ori.group_id desc,ori.p_key, dso.orderstatus_CODE");
		}
		return executeQuery(sql.toString(), params);
	}
	
	/**
	 * 自定义审查获取审查医嘱数据
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Result get_audit_def_oredrs(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select ori.* from v_audit_def_oredrs(nolock) ori ");
		sql.append(" where ori.auto_audit_id=:auto_audit_id ");
		return executeQuery(sql.toString(), params);
	}
	
	/**
	 * 事后审查医嘱处方查看
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Result queryOrders_v(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select ori.*,dso.orderstatus_CODE sys_order_status,CONVERT(FLOAT,ori.dosage) as dosage2, '高危药品' xiangm,case when CHARINDEX('是',concat(b.value, c.value)) > 0 then '是' else '否' end xmz from "+V_TABLE_NAME+"(nolock) ori ");
		sql.append(" left join dict_his_drug a on ori.ORDER_CODE = a.drug_code");
		sql.append(" left join dict_his_drug_mx b on a.DRUG_CODE = b.drug_code and b.XIANGM = '高危药品'");
		sql.append(" left join dict_sys_drug_mx c on a.SYS_P_KEY = c.SYS_P_KEY and c.XIANGM = '高危药品' ");
		sql.append(" left join dict_his_orderstatus (nolock) dho on dho.orderstatus_CODE=ori.order_status ");
		sql.append(" left join dict_sys_orderstatus (nolock) dso on dho.sys_p_key=dso.p_key ");
//		sql.append(" where b.XIANGM = '高危药品' or c.XIANGM = '高危药品' ");
//		setParameter(params, "auto_audit_id", " and ori.auto_audit_id=:auto_audit_id", sql);
		sql.append(" where  ");
		setParameter(params, "auto_audit_id", " ori.auto_audit_id=:auto_audit_id", sql);
		if(CommonFun.isNe(params.get("order"))){
			sql.append(" order by ori.group_id desc,ori.p_key, dso.orderstatus_CODE");
		}
		return executeQuery(sql.toString(), params);
	}
	
	/**
	 * 查询审查药品使用信息{视图版}
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Result queryOrders_view(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select ori.*,dso.orderstatus_CODE sys_order_status,CONVERT(FLOAT,ori.dosage) as dosage2, '高危药品' xiangm,case when CHARINDEX('是',concat(b.value, c.value)) > 0 then '是' else '否' end xmz from "+V_TABLE_NAME+"(nolock) ori ");
		sql.append(" left join dict_his_drug a on ori.ORDER_CODE = a.drug_code");
		sql.append(" left join dict_his_drug_mx b on a.DRUG_CODE = b.drug_code and b.XIANGM = '高危药品'");
		sql.append(" left join dict_sys_drug_mx c on a.SYS_P_KEY = c.SYS_P_KEY and c.XIANGM = '高危药品' ");
		sql.append(" left join dict_his_orderstatus (nolock) dho on dho.orderstatus_CODE=ori.order_status ");
		sql.append(" left join dict_sys_orderstatus (nolock) dso on dho.sys_p_key=dso.p_key ");
//		sql.append(" where b.XIANGM = '高危药品' or c.XIANGM = '高危药品' ");
//		setParameter(params, "auto_audit_id", " and ori.auto_audit_id=:auto_audit_id", sql);
		sql.append(" where  ");
		setParameter(params, "auto_audit_id", " ori.auto_audit_id=:auto_audit_id", sql);
		if(CommonFun.isNe(params.get("order"))){
			sql.append(" order by ori.group_id desc,ori.p_key, dso.orderstatus_CODE");
		}
		return executeQuery(sql.toString(), params);
	}
	
	//查询医嘱列表
	public Result queryDrugList(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select ori.*,dso.orderstatus_CODE sys_order_status,c.drug_message,dbo.get_auditorders_GROUPTAG ( :auto_audit_id, ori.group_id, ori.ORDER_NO, ori.ORDER_SUB_NO ) AS zu,CONVERT(FLOAT,ori.dosage) as dosage2,mx.xiangm,mx.value xmz from ");
		if (CommonFun.isNe(params.get("is_ywlsb"))) {
			sql.append( TABLE_NAME+"(nolock) ori ");
		}else {
			sql.append(" YWLSB_AUDIT_ORDERS (nolock) ori ");
		}
		if(!CommonFun.isNe(params.get("start_time")) || !CommonFun.isNe(params.get("end_time"))) {
			sql.append(" inner join CR_Check_results ccr (nolock) on ccr.Auto_audit_id = ori.auto_audit_id ");
			sql.append(" and ccr.ORDER_NO = ori.ORDER_NO and ccr.group_id = ori.group_id and ccr.USE_FLAG = 0 ");
		}
		sql.append(" left join dict_his_drug_mx mx (nolock) on mx.drug_code= ori.order_code and xiangm = '高危药品' ");
		sql.append(" left join v_dict_drug c (nolock) on c.drug_code=ori.order_code ");
		sql.append(" left join dict_his_orderstatus (nolock) dho on dho.orderstatus_CODE=ori.order_status ");
		sql.append(" left join dict_sys_orderstatus (nolock) dso on dho.sys_p_key=dso.p_key ");
		sql.append(" where 1=1 ");
		setParameter(params, "auto_audit_id", " and ori.auto_audit_id=:auto_audit_id", sql);
		setParameter(params, "start_time", " and ori.ENTER_DATE_TIME>=:start_time", sql);
		setParameter(params, "end_time", " and ori.ENTER_DATE_TIME<=:end_time", sql);
		if(CommonFun.isNe(params.get("order"))){
			sql.append(" order by  dso.orderstatus_CODE, ori.group_id, ori.order_no, ori.order_sub_no ");
		}
		return executeQuery(sql.toString(), params);
	}
	
	/**
	 * 查找业务流水表orders内容
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Result queryOrdersByYwlsb(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select ori.*,dso.orderstatus_CODE sys_order_status,CONVERT(FLOAT,ori.dosage) as dosage2,mx.xiangm,mx.value xmz from YWLSB_AUDIT_ORDERS(nolock) ori ");
		sql.append(" left join dict_his_drug_mx mx (nolock) on mx.drug_code= ori.order_code and xiangm = '高危药品' ");
		sql.append(" left join dict_his_orderstatus (nolock) dho on dho.orderstatus_CODE=ori.order_status ");
		sql.append(" left join dict_sys_orderstatus (nolock) dso on dho.sys_p_key=dso.p_key ");
		sql.append(" where 1=1 ");
		setParameter(params, "auto_audit_id", " and ori.auto_audit_id=:auto_audit_id", sql);
		if(CommonFun.isNe(params.get("order"))){
			sql.append(" order by ori.group_id desc,ori.p_key, dso.orderstatus_CODE");
		}
		return executeQuery(sql.toString(), params);
	}
	
	public Result queryOrdersGroup(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select aao.patient_id,aao.visit_id,aao.order_no,pc.comment_result from "+TABLE_NAME+"(nolock) aao ");
		sql.append(" inner join pharmacist_comment pc (nolock) on pc.auto_audit_id = aao.auto_audit_id  ");
		sql.append(" where aao.auto_audit_id=:auto_audit_id and aao.order_no = pc.order_no ");
		sql.append(" group by aao.patient_id,aao.visit_id,aao.order_no,pc.comment_result");
		sql.append(" order by aao.order_no");
		return executeQuery(sql.toString(), params);
	}
	
	public Record get(String id) throws Exception {
		Record params = new Record();
		params.put("id", id);
		StringBuffer sql = new StringBuffer();
		sql.append("select * from "+TABLE_NAME+"(nolock) where id = :id");
		return executeQuerySingleRecord(sql.toString(), params);
	}
	public void insert(Map<String, Object> params) throws Exception {
		params.remove("id");
		String id = CommonFun.getITEMID();
		params.put("id", id);
		executeInsert(TABLE_NAME, params);
	}
	public int update(Map<String, Object> params) throws Exception {
		Record r = new Record();
		r.put("id", params.remove("id"));
		return executeUpdate(TABLE_NAME, params,r);
	}

	public Result queryDiagnosis(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select diagnosis_desc,diagnosis_type from hospital_common.dbo.TMP_his_in_diagnosis (nolock)");
		sql.append(" where patient_id= :patient_id and visit_id= :visit_id");
		sql.append(" group by diagnosis_desc,diagnosis_type ");
		return executeQuery(sql.toString(), params);
	}
}
