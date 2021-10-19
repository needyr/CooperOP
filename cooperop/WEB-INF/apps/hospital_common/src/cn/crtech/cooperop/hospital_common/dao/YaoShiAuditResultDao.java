package cn.crtech.cooperop.hospital_common.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.service.ProductmanagetService;

/**
 * @className: YaoShiAuditResultDao   
 * @description: 药师审查结果
 * @author: 魏圣峰 
 * @date: 2019年1月28日 下午7:32:14
 */
public class YaoShiAuditResultDao extends BaseDao{
	/** ipc审查主表 */
	private final static String TABLE_AUTO_AUDIT = "auto_audit";
	/** ipc审查医嘱 */
	private final static String TABLE_AUTO_AUDIT_ORDERS = "auto_audit_orders";
	/** his患者 */
	private final static String TABLE_HIS_PATIENT = "his_patient";
	/** 药师点评 */
	private final static String TABLE_PHARMACIST_COMMENT = "pharmacist_comment";
	/** 药师点评字典 */
	private final static String TABLE_DICT_COMMENT = "dict_sys_comment";
	/** ipc审查结果明细 */
	private final static String TABLE_CHECK_RESULT_INFO = "check_result_info";
	/** ipc审查结果 */
	private final static String TABLE_CHECK_RESULT = "check_result";
	/** 产品审查规则 */
	private final static String TABLE_MAP_COMMONM_REGULATION = "map_common_regulation";
	/** 系统产品审查规则 */
	private final static String TABLE_SYS_COMMON_REGULATIN = "sys_common_regulation";
	/** 审查等级规则 */
	private final static String TABLE_MAP_CHECK_LEVEL = "map_check_level";
	/** 系统审查等级 */
	private final static String TABLE_SYS_CHECK_LEVEL = "sys_check_level";
	
	public Record get(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select aa.*,hp.patient_name from "+TABLE_AUTO_AUDIT+" aa(nolock) ");
		sql.append(" left join "+TABLE_HIS_PATIENT+" hp(nolock) on aa.patient_id = hp.patient_id ");
		sql.append(" where aa.id = :id");
		return executeQuerySingleRecord(sql.toString(), params);
	}
	public Result getyzsAll(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ")
		    .append("dbo.get_auditorders_GROUPTAG ( :auto_audit_id,group_id, ORDER_NO, ORDER_SUB_NO ) AS zu,")
		    .append("dbo.get_auditorders_REPEATINDICATOR ( :auto_audit_id,group_id, ORDER_NO, ORDER_SUB_NO ) AS REPEAT_INDICATOR,")
		    .append("dbo.get_auditorders_orderclass_NAME ( :auto_audit_id,group_id, ORDER_NO, ORDER_SUB_NO ) AS order_class,")
		    .append("dbo.get_auditorders_ENTERDATETIME ( :auto_audit_id,group_id, ORDER_NO, ORDER_SUB_NO ) AS ENTER_DATE_TIME,")
		    .append("dbo.get_auditorders_ADMINISTRATION_NAME ( :auto_audit_id,group_id, ORDER_NO, ORDER_SUB_NO ) AS ADMINISTRATION,")
		    .append("ads.p_key,")
		    .append("CONVERT(FLOAT,ads.dosage) dosage,")
		    .append("ads.DOSAGE_UNITS,ads.FREQUENCY,ads.doctor,ads.ORDER_TEXT,ads.order_no,ads.sys_order_status,ads.order_code,")
		    .append("vdd.xiangm,vdd.value xmz")
		    .append(" FROM v_use_orders_outp (nolock) ads")
		    .append(" left join DICT_HIS_DRUG_MX vdd (nolock) on ads.order_code=vdd.drug_code and vdd.xiangm = '高危药品'  ")
		    .append(" WHERE ads.auto_audit_id = :auto_audit_id")
		    .append(" ORDER BY ads.sys_order_status,ads.ORDER_NO,ads.ORDER_SUB_NO                                                               ");
		return executeQuery(sql.toString(), params);
	}
	/**
	 * @description: 获取药师的点评
	 * @param: params Map     
	 * @return: Result      
	 * @throws: Exception
	 */
	public Result queryByAuditId(Map<String, Object> params) throws Exception {
		Record r = new Record();
		StringBuffer sql = new StringBuffer();
		r.put("auto_audit_id", params.get("auto_audit_id"));
		sql.append("select a.*,b.comment_name");
		sql.append(" from "+TABLE_PHARMACIST_COMMENT+" a(nolock)");
		sql.append(" left join "+TABLE_DICT_COMMENT+" b(nolock) on a.comment_id = b.system_code");
		sql.append(" where a.auto_audit_id = :auto_audit_id ");
		return executeQuery(sql.toString(), r);
	}
	/**
	 * @description: 获取医嘱
	 * @param: params Map      
	 * @return: Result      
	 * @throws: Exception
	 */
	public Result queryOrdersGroup(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select aao.patient_id,aao.visit_id,aao.order_no,pc.comment_result");
		sql.append(" from "+TABLE_AUTO_AUDIT_ORDERS+" aao(nolock)");
		sql.append(" inner join "+TABLE_PHARMACIST_COMMENT+" pc(nolock) on pc.auto_audit_id = aao.auto_audit_id  ");
		sql.append(" where aao.auto_audit_id=:auto_audit_id and aao.order_no = pc.order_no ");
		sql.append(" group by aao.patient_id,aao.visit_id,aao.order_no,pc.comment_result");
		sql.append(" order by aao.order_no");
		return executeQuery(sql.toString(), params);
	}
	/**
	 * @description: 审查结果详情
	 * @param: params Map  
	 * @return: Result      
	 * @throws: Exception
	 */
	public Result queryCheckResultInfo(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();		                                                                                                                   
		sql.append(" select acs.sort_id,acs.sort_name,dsc.level_code as sys_check_level, cr.doc_advice,doc_other_advice,");
		sql.append(" dsc.level_name as sys_check_level_name,dsc.level_star as star_level,cri.*,cr.keyword");
		sql.append(" from "+TABLE_CHECK_RESULT_INFO+" cri(nolock)");
		sql.append(" inner join "+TABLE_CHECK_RESULT+" cr(nolock) on cr.id = cri.parent_id");
		sql.append(" inner join "+TABLE_MAP_COMMONM_REGULATION+" crs(nolock) on cr.keyword = crs.thirdt_code and cr.type = crs.check_type");
		sql.append(" and crs.product_code = '"+ProductmanagetService.IPC+"'");
		sql.append(" left join "+TABLE_SYS_COMMON_REGULATIN+" acs(nolock) on  acs.p_key = crs.sys_p_key");
		sql.append(" inner join "+TABLE_MAP_CHECK_LEVEL+" dcd(nolock) on cri.level = dcd.thirdt_code");
		sql.append(" and dcd.product_code = '"+ProductmanagetService.IPC+"'");
		sql.append(" inner join "+TABLE_SYS_CHECK_LEVEL+" dsc(nolock) on dcd.sys_p_key = dsc.p_key ");
		sql.append(" where cri.is_new = 1");
		sql.append(" and (cri.check_result_state = 'N'");
		sql.append(" or cri.check_result_state = 'T')");
		sql.append(" and cri.auto_audit_id = :auto_audit_id");
		sql.append(" order by acs.sort_id");
		return executeQuery(sql.toString(), params);                                           
	}
}
