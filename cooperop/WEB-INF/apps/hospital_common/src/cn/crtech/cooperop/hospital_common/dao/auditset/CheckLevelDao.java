package cn.crtech.cooperop.hospital_common.dao.auditset;

import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.service.ProductmanagetService;

public class CheckLevelDao extends BaseDao {

	public static final String TABLE_NAME = "sys_check_level";

	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();

		sql.append(" SELECT ");
		sql.append(" TOP (100) PERCENT ");
		sql.append(" p_key, ");
		sql.append(" level_code, ");
		sql.append(" level_name, ");
		sql.append(" level_star, ");
		sql.append(" description ");
		sql.append(" FROM ");
		sql.append(TABLE_NAME + " ( nolock ) ");
		sql.append(" WHERE 1 = 1 ");

		if (!CommonFun.isNe(params.get("level_name"))) {
			params.put("level_name", "%" + params.get("level_name") + "%");
			sql.append(" and level_name LIKE :level_name ");
		}

		if (!CommonFun.isNe(params.get("product_code"))) {
			sql.append(" and product_code=:product_code ");
		}

		if (!CommonFun.isNe(params.get("filter"))) {
			params.put("condition", "%" + params.get("filter") + "%");
			sql.append(" and level_code LIKE :condition  OR level_name LIKE :condition");
		}
		sql.append(" ORDER BY level_code DESC");
		return executeQueryLimit(sql.toString(), params);
	}
	
	/**
	 * @author wangsen
	 * @date 2018年12月26日
	 * @param 
	 * @return 
	 * @function 用于自动补全
	 */
	public Result queryAllLevel(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();

		sql.append(" SELECT ");
		sql.append(" TOP (100) PERCENT ");
		sql.append(" p_key, ");
		sql.append(" level_code, ");
		sql.append(" level_name, ");
		sql.append(" level_star ");
		sql.append(" FROM ");
		sql.append(TABLE_NAME + " ( nolock ) ");
		sql.append(" WHERE 1 = 1 ");

		if (!CommonFun.isNe(params.get("level_name"))) {
			params.put("level_name", "%" + params.get("level_name") + "%");
			sql.append(" and level_name LIKE :level_name ");
		}

		if (!CommonFun.isNe(params.get("product_code"))) {
			sql.append(" and product_code=:product_code ");
		}

		if (!CommonFun.isNe(params.get("filter"))) {
			params.put("condition", "%" + params.get("filter") + "%");
			sql.append(" and level_code LIKE :condition  OR level_name LIKE :condition");
		}
		sql.append(" ORDER BY level_code ASC");
		return executeQuery(sql.toString(), params);
	}

	public int insert(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT p_key  FROM " + TABLE_NAME + " (nolock) WHERE 1=1 ");
		setParameter(params, "level_code", " and level_code=:level_code ", sql);
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

		setParameter(params, "level_code", " and level_code=:level_code ", sql);
		setParameter(params, "product_code", " and product_code=:product_code ", sql);
		Record re = executeQuerySingleRecord(sql.toString(), params);

		if (!CommonFun.isNe(re)) {
			if (!re.get("p_key").equals(params.get("p_key"))) {
				return 0;
			}
		}
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("p_key", params.remove("p_key"));
		return executeUpdate(TABLE_NAME, params, conditions);

	}

	public int delete(Map<String, Object> params) throws Exception {
		Map<String, Object> conditions = new HashMap<String, Object>();
		/* 根据表实际主键删除 */
		conditions.put("p_key", params.get("p_key"));
		return executeDelete(TABLE_NAME, conditions);

	}

	public Record get(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT ");
		sql.append(" p_key, ");
		sql.append(" level_code, ");
		sql.append(" level_name, ");
		sql.append(" level_star, ");
		sql.append(" description ");
		sql.append(" FROM ");
		sql.append(TABLE_NAME + " ( nolock ) ");
		sql.append(" WHERE 1 = 1 ");
		setParameter(params, "p_key", " and p_key=:p_key ", sql);
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	public Record getByCode(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT ");
		sql.append(" p_key, ");
		sql.append(" level_code, ");
		sql.append(" level_name, ");
		sql.append(" level_star, ");
		sql.append(" description ");
		sql.append(" FROM ");
		sql.append(TABLE_NAME + " ( nolock ) ");
		sql.append(" WHERE 1 = 1 ");
		setParameter(params, "product_code", " and product_code=:product_code ", sql);
		setParameter(params, "level_code", " and level_code=:level_code ", sql);
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	public Result queryAuto(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT * FROM "+TABLE_NAME+" (nolock) ");
		Result result = executeQueryLimit(sql.toString(),params);
		return result;
	}
	
	public Result queryListByIpc(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT level_code sys_check_level,level_name sys_check_level_name FROM "+TABLE_NAME+" (nolock) ");
		sql.append(" where product_code = '"+ProductmanagetService.IPC+"'");
		return executeQuery(sql.toString(),params);
	}
	
	public Result queryListByImic(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT thirdt_code sys_check_level,thirdt_name sys_check_level_name FROM map_check_level (nolock) ");
		sql.append(" where product_code = '"+ProductmanagetService.IMIC+"' order by p_key ");
		return executeQuery(sql.toString(),params);
	}
}
