package cn.crtech.cooperop.hospital_common.dao.dictsys;

import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class DggradeDao extends BaseDao {

public static final String DICT_SYS_DGGRADE = "dict_sys_dggrade";
public static final String DICT_SYS_INTERFACE = "dict_sys_interface";

	public Result query(Map<String, Object> params) throws Exception {
		
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT  a.* ,b.interface_type_name");
		sql.append(" FROM " + DICT_SYS_DGGRADE + " (nolock) a");
		sql.append(" LEFT JOIN  " + DICT_SYS_INTERFACE + " (nolock) b");
		sql.append(" ON  a.interface_type=b.interface_type");	
		sql.append(" WHERE 1 = 1 ");

		if (!CommonFun.isNe(params.get("name"))) {
			params.put("name", "%" + params.get("name") + "%");
			sql.append(" and (DG_GRADE_CODE LIKE :name or DG_GRADE_NAME LIKE :name) ");
		}
		if (!CommonFun.isNe(params.get("filter"))) {
			params.put("condition", "%" + params.get("filter") + "%");
			sql.append(" and (dg_grade_code LIKE :condition  or dg_grade_name LIKE :condition)");
		}
		
		
		return executeQueryLimit(sql.toString(), params);
	}

	public int insert(Map<String, Object> params) throws Exception {
		return executeInsert(DICT_SYS_DGGRADE, params);
	}

	public int update(Map<String, Object> params) throws Exception {
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("id", params.remove("id"));
		return executeUpdate(DICT_SYS_DGGRADE, params, conditions);
	}

	public int delete(Map<String, Object> params) throws Exception {
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("id", params.get("id"));
		return executeDelete(DICT_SYS_DGGRADE, conditions);

	}

	//进入页面时获取数据
	public Record get(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT  * ");
		sql.append(" FROM " + DICT_SYS_DGGRADE + " (nolock)");
		sql.append(" WHERE id=:id ");
		return executeQuerySingleRecord(sql.toString(), params);
	}



}
