package cn.crtech.cooperop.hospital_common.dao.dictsys;

import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class DgpgjeDao extends BaseDao {
	public static final String DICT_SYS_DGPGJE = "DICT_SYS_DGPGJE";
	public static final String DICT_SYS_INTERFACE = "DICT_SYS_INTERFACE";
	public static final String DICT_SYS_DGGRADE = "DICT_SYS_DGGRADE";
	public static final String DICT_SYS_DG = "dict_sys_dg";
	public static final String DICT_HIS_DIAGNOSIS = "dict_his_diagnosis";
	
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT a.*,  ");
		sql.append(" b.INTERFACE_TYPE_NAME,  ");
		sql.append(" c.DG_GRADE_NAME,  ");
		sql.append(" d.dg_name   ");	
		sql.append(" FROM " + DICT_SYS_DGPGJE + " (nolock)  a ");
		sql.append(" LEFT JOIN " + DICT_SYS_INTERFACE + " (nolock)  b  ");
		sql.append(" ON a.INTERFACE_TYPE =b.INTERFACE_TYPE  ");
		sql.append(" LEFT JOIN " + DICT_SYS_DGGRADE + " (nolock) c  ");
		sql.append(" ON a.DG_GRADE=c.DG_GRADE_CODE    ");
		sql.append(" INNER JOIN " + DICT_SYS_DG + " (nolock)  d ");
		sql.append(" ON a.DG_ICD=d.dg_icd AND a.dg_remark=d.dg_remark   ");
		sql.append(" WHERE 1=1 ");
	/*	if (!CommonFun.isNe(params.get("name"))) {
			params.put("name", "%" + params.get("name") + "%");
			sql.append(" and (b.INTERFACE_TYPE_NAME LIKE :name or c.DG_GRADE_NAME LIKE :name or d.dg_name  LIKE :name  )");
		}*/
		// 人员类别
		if (!CommonFun.isNe(params.get("dg_grade"))) {
			sql.append(" and a.DG_GRADE = :dg_grade ");
		}
		// 医保接口类型
		if (!CommonFun.isNe(params.get("interface_type"))) {
			sql.append(" and a.INTERFACE_TYPE = :interface_type ");
		}
		//病组分组	
		if (!CommonFun.isNe(params.get("dg_icd_remark"))) {
			String[] split = params.get("dg_icd_remark").toString().split(",");
			params.put("dg_icd",split[0]);
			params.put("dg_remark",split[1]);
			sql.append(" and a.dg_icd = :dg_icd  and a.dg_remark= :dg_remark");
		}
		if (!CommonFun.isNe(params.get("filter"))) {
			params.put("name", "%" + params.get("filter") + "%");
			sql.append(" and (b.INTERFACE_TYPE_NAME LIKE :name or c.DG_GRADE_NAME LIKE :name or d.dg_name  LIKE :name  )");
		}
		return executeQueryLimit(sql.toString(), params);
	}

	public Result queryInterfaceType(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT *                       ");
		sql.append(" FROM  " + DICT_SYS_INTERFACE + " (nolock)");
		sql.append(" WHERE 1=1                      ");

		if (!CommonFun.isNe(params.get("filter"))) {
			params.put("condition", "%" + params.get("filter") + "%");
			sql.append(" and (INTERFACE_TYPE LIKE :condition  or INTERFACE_TYPE_NAME LIKE :condition)");
		}
		return executeQueryLimit(sql.toString(), params);
	}

	public int insert(Map<String, Object> params) throws Exception {
		return executeInsert(DICT_SYS_DGPGJE, params);
	}

	public int update(Map<String, Object> params) throws Exception {
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("id", params.remove("id"));
		return executeUpdate(DICT_SYS_DGPGJE, params, conditions);
	}

	public int delete(Map<String, Object> params) throws Exception {
		Map<String, Object> conditions = new HashMap<String, Object>();
		/* 根据表实际主键删除 */
		conditions.put("id", params.get("id"));
		return executeDelete(DICT_SYS_DGPGJE, conditions);

	}

	// 进入页面时获取数据
	public Record get(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT a.*,  ");
		sql.append(" b.INTERFACE_TYPE_NAME,  ");
		sql.append(" c.DG_GRADE_NAME,  ");
		sql.append(" d.dg_name  ");
		sql.append(" FROM " + DICT_SYS_DGPGJE + " (nolock)  a ");
		sql.append(" LEFT JOIN " + DICT_SYS_INTERFACE + " (nolock)  b  ");
		sql.append(" ON a.INTERFACE_TYPE =b.INTERFACE_TYPE  ");
		sql.append(" LEFT JOIN " + DICT_SYS_DGGRADE + " (nolock) c  ");
		sql.append(" ON a.DG_GRADE=c.DG_GRADE_CODE    ");
		sql.append(" LEFT JOIN " + DICT_SYS_DG + " (nolock)  d ");
		sql.append(" ON a.DG_ICD=d.dg_icd AND a.dg_remark=d.dg_remark   ");
		sql.append(" WHERE a.id=:id ");
		return executeQuerySingleRecord(sql.toString(), params);

	}
}
