package cn.crtech.cooperop.hospital_common.dao.dictsys;

import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class DgsfdjDao extends BaseDao {

public static final String DICT_SYS_DGSFDJ = "dict_sys_dgsfdj";

	public Result query(Map<String, Object> params) throws Exception {
		
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT  * ");
		sql.append(" FROM " + DICT_SYS_DGSFDJ + " (nolock)");
		sql.append(" WHERE 1 = 1 ");

		if (!CommonFun.isNe(params.get("name"))) {
			params.put("name", "%" + params.get("name") + "%");
			sql.append(" and (dgsfdj_name LIKE :name or dgsfdj_code LIKE :name) ");
		}
		return executeQueryLimit(sql.toString(), params);
	}

	public int insert(Map<String, Object> params) throws Exception {
		return executeInsert(DICT_SYS_DGSFDJ, params);
	}

	public int update(Map<String, Object> params) throws Exception {
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("id", params.remove("id"));
		return executeUpdate(DICT_SYS_DGSFDJ, params, conditions);
	}

	public int delete(Map<String, Object> params) throws Exception {
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("id", params.get("id"));
		return executeDelete(DICT_SYS_DGSFDJ, conditions);

	}

	//进入页面时获取数据
	public Record get(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT  * ");
		sql.append(" FROM " + DICT_SYS_DGSFDJ + " (nolock)");
		sql.append(" WHERE id=:id");
		return executeQuerySingleRecord(sql.toString(), params);
	}



}
