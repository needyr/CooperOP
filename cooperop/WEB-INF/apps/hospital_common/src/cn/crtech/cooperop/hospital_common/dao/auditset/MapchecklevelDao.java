package cn.crtech.cooperop.hospital_common.dao.auditset;

import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class MapchecklevelDao extends BaseDao {

	public static final String TABLE_NAME = "map_check_level";
	public static final String SYS_CHECK_LEVEL = "sys_check_level";
	
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT a.p_key AS m_p_key, ");
		sql.append(" thirdt_code, ");
		sql.append(" thirdt_name, ");
		sql.append(" sys_p_key, ");
		sql.append(" b.p_key AS s_p_key, ");
		sql.append(" level_code, ");
		sql.append(" level_name, ");
		sql.append(" check_state ");
		sql.append(" FROM " + TABLE_NAME + " (nolock) a ");
		sql.append(" LEFT JOIN " + SYS_CHECK_LEVEL + " (nolock) b ");
		sql.append(" ON a.sys_p_key=b.p_key ");
		sql.append(" and b.product_code=:product_code ");
		sql.append(" WHERE 1 =1 ");

		/* 模糊查询 */
		if (!CommonFun.isNe(params.get("query_name"))) {
			params.put("condition", "%" + params.get("query_name") + "%");
			sql.append(" and level_name LIKE :condition ");
		}
		/* 根据product_code过滤 */
		if (!CommonFun.isNe(params.get("product_code"))) {
			sql.append(" and a.product_code=:product_code ");
		}
		return executeQueryLimit(sql.toString(), params);

	}
	
	public int insert(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT p_key  FROM " + TABLE_NAME + " (nolock) WHERE 1=1 ");
		setParameter(params, "thirdt_code", " and thirdt_code=:thirdt_code ", sql);
		setParameter(params, "product_code", " and product_code=:product_code ", sql);
		Record re = executeQuerySingleRecord(sql.toString(), params);
		if (!CommonFun.isNe(re)) {
			return 0;
		}
		return executeInsert(TABLE_NAME, params);

	}

	public int update(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT p_key  FROM " + TABLE_NAME + " (nolock) WHERE 1=1 ");
		/* 根据thirdt_code,product_code查询 */
		setParameter(params, "thirdt_code", " and thirdt_code=:thirdt_code ", sql);
		setParameter(params, "product_code", " and product_code=:product_code ", sql);
		Record re = executeQuerySingleRecord(sql.toString(), params);
		if (!CommonFun.isNe(re)) {
			if (!re.get("p_key").equals(params.get("m_p_key"))) {
				return 0;
			}
		}

		Map<String, Object> conditions = new HashMap<String, Object>();
		/* 添加条件，通过TABLE_NAME表实际主键插入 */
		conditions.put("p_key", params.remove("m_p_key"));
		return executeUpdate(TABLE_NAME, params, conditions);

	}

	public int delete(Map<String, Object> params) throws Exception {
		Map<String, Object> conditions = new HashMap<String, Object>();
		/* 根据表实际主键删除 */
		conditions.put("p_key", params.get("m_p_key"));
		return executeDelete(TABLE_NAME, conditions);

	}

	public Record get(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT a.p_key AS m_p_key, ");
		sql.append(" thirdt_code, ");
		sql.append(" thirdt_name, ");
		sql.append(" sys_p_key, ");
		sql.append(" b.p_key AS s_p_key, ");
		sql.append(" level_code, ");
		sql.append(" level_name, ");
		sql.append(" check_state ");
		sql.append(" FROM " + TABLE_NAME + " (nolock) a ");
		sql.append(" LEFT JOIN " + SYS_CHECK_LEVEL + " (nolock) b ");
		sql.append(" ON a.sys_p_key=b.p_key ");
		sql.append(" WHERE 1 =1 ");
		setParameter(params, "m_p_key", " and a.p_key=:m_p_key ", sql);
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
}
