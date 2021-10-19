package cn.crtech.cooperop.hospital_common.dao.dictsys;

import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class DgDao extends BaseDao {
	public static final String DICT_SYS_DGTYPE = "DICT_SYS_DGTYPE";
	public static final String DICT_SYS_DG = "DICT_SYS_DG";
	public static final String DICT_HIS_DIAGNOSIS = "dict_his_diagnosis";
	
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT a.*,b.dg_type_name ");
		sql.append(" FROM " + DICT_SYS_DG + " (nolock) a ");
		sql.append(" LEFT JOIN "+DICT_SYS_DGTYPE+" (nolock) b ");
		sql.append(" ON a.dg_type = b.dg_type_code ");
		sql.append(" where 1=1 ");		
		if (!CommonFun.isNe(params.get("name"))) {
			params.put("name", "%" + params.get("name") + "%");
			sql.append(" and (a.dg_icd LIKE :name or a.dg_name LIKE :name) ");
		}
		if (!CommonFun.isNe(params.get("filter"))) {
			params.put("condition", "%" + params.get("filter") + "%");
			sql.append(" and (a.dg_icd LIKE :condition  or a.dg_name LIKE :condition or a.dg_remark  LIKE :condition)");
		}
		return executeQueryLimit(sql.toString(), params);
	}
	
	public Result queryDictHisDiagnosis(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT * ");
		sql.append(" FROM "+DICT_HIS_DIAGNOSIS+" (nolock) ");
		sql.append(" where 1=1 ");		
		if (!CommonFun.isNe(params.get("filter"))) {
			params.put("condition", "%" + params.get("filter") + "%");
			sql.append(" and (diagnosis_code LIKE :condition  or diagnosis_name LIKE :condition )");
		}
		return executeQueryLimit(sql.toString(), params);
	}
	
	
	public Result queryByIcdAndRemark(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT * ");
		sql.append(" FROM " + DICT_SYS_DG + " (nolock) ");
		sql.append(" where dg_icd= :dg_icd ");
		sql.append(" AND dg_remark= :dg_remark ");	
		return executeQueryLimit(sql.toString(), params);
	}

	public int insert(Map<String, Object> params) throws Exception {
		return executeInsert(DICT_SYS_DG, params);
	}

	public int update(Map<String, Object> params) throws Exception {
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("id", params.remove("id"));
		return executeUpdate(DICT_SYS_DG, params, conditions);
	}

	public int delete(Map<String, Object> params) throws Exception {
		Map<String, Object> conditions = new HashMap<String, Object>();
		/* 根据表实际主键删除 */
		conditions.put("id", params.get("id"));
		return executeDelete(DICT_SYS_DG, conditions);

	}

	// 进入页面时获取数据
	public Record get(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT a.*,b.dg_type_name ");
		sql.append(" FROM " + DICT_SYS_DG + " (nolock) a ");
		sql.append(" LEFT JOIN "+DICT_SYS_DGTYPE+" (nolock) b ");
		sql.append(" ON a.dg_type = b.dg_type_code ");
		sql.append(" WHERE a.id=:id ");
		return executeQuerySingleRecord(sql.toString(), params);
	}

}
