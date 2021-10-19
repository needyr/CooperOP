package cn.crtech.cooperop.hospital_common.dao.dictsys;

import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class DgplevelDao extends BaseDao {

public static final String DICT_SYS_DGPLEVEL = "dict_sys_dgplevel";

	public Result query(Map<String, Object> params) throws Exception {
		
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT  * ");
		sql.append(" FROM " + DICT_SYS_DGPLEVEL + " (nolock)");
		sql.append(" WHERE 1 = 1 ");

		if (!CommonFun.isNe(params.get("name"))) {
			params.put("name", "%" + params.get("name") + "%");
			sql.append(" and (DG_PLEVEL_CODE LIKE :name or DG_PLEVEL_NAME LIKE :name) ");
		}
			
		return executeQueryLimit(sql.toString(), params);
	}

	public int insert(Map<String, Object> params) throws Exception {
		return executeInsert(DICT_SYS_DGPLEVEL, params);
	}

	public int update(Map<String, Object> params) throws Exception {
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("id", params.remove("id"));
		return executeUpdate(DICT_SYS_DGPLEVEL, params, conditions);
	}

	public int delete(Map<String, Object> params) throws Exception {
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("id", params.get("id"));
		return executeDelete(DICT_SYS_DGPLEVEL, conditions);

	}

	//进入页面时获取数据
	public Record get(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT  * ");
		sql.append(" FROM " + DICT_SYS_DGPLEVEL + " (nolock)");
		sql.append(" WHERE id=:id ");
		return executeQuerySingleRecord(sql.toString(), params);
	}



}
