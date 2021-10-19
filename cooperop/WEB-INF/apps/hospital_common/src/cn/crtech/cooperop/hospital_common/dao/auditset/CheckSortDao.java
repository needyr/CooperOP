package cn.crtech.cooperop.hospital_common.dao.auditset;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.service.ProductmanagetService;

public class CheckSortDao extends BaseDao {

	public static final String TABLE_NAME = "sys_common_regulation";
	public static final String SYS_CHECK_LEVEL = "sys_check_level";
	// 自定义转自费
	public static final String AUDIT_RULE_CTRL_ZZF = "hospital_imic..audit_rule_ctrl_zzf";
	public static final String AUDIT_RULE_CTRL_ZZF_DEPT = "hospital_imic..audit_rule_ctrl_zzf_dept";
	public static final String AUDIT_RULE_CTRL_ZZF_USER = "hospital_imic..audit_rule_ctrl_zzf_user";
	public static final String AUDIT_RULE_CTRL_ZZF_ITEM = "hospital_imic..audit_rule_ctrl_zzf_item";

	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT a.p_key ");
		sql.append(" , sort_id, sort_name ");
		sql.append(" , interceptor_level, info_level,complain_level ");
		sql.append(" , outpatient_check, emergency_check, hospitalization_check ");
		sql.append(" , state, audit_explain, sort_num, a.product_code, create_time ");
		sql.append(" , update_time, oper_user  ");
		sql.append(" , interceptor_level_emergency, info_level_emergency,complain_level_emergency  ");
		sql.append(" , interceptor_level_outp, info_level_outp,complain_level_outp,is_zzf ");
		sql.append(" FROM " + TABLE_NAME + " (nolock) a ");
		sql.append(" WHERE 1 = 1 ");

		/* 模糊查询 */
		if (!CommonFun.isNe(params.get("query_name"))) {
			params.put("condition", "%" + params.get("query_name") + "%");
			sql.append(" and (sort_name LIKE :condition or sort_id like :condition)");
		}
		/* 根据product_code过滤 */
		if (!CommonFun.isNe(params.get("product_code"))) {
			sql.append(" and a.product_code=:product_code ");
		}

		/* 自动补全 */
		if (!CommonFun.isNe(params.get("filter"))) {
			params.put("condition", "%" + params.get("filter") + "%");
			sql.append(" and (sort_id LIKE :condition  or sort_name LIKE :condition)");
		}

		/* 根据sort_num排序 */
		params.put("sort", "sort_num desc");
		return executeQueryLimit(sql.toString(), params);

	}

	public int insert(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT sort_id  FROM " + TABLE_NAME + " (nolock) WHERE 1=1 ");
		/* 根据sort_id,product_code查询 */
		setParameter(params, "sort_id", " and sort_id=:sort_id ", sql);
		setParameter(params, "product_code", " and product_code=:product_code ", sql);

		Record re = executeQuerySingleRecord(sql.toString(), params);
		/* 如果有re不为空代表sort_id重复，不能插入 */
		if (!CommonFun.isNe(re)) {
			return 0;
		}
		return executeInsert(TABLE_NAME, params);

	}

	public int update(Map<String, Object> params) throws Exception {
		/* 获取操作时间 */
		String time = CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
		params.put("update_time", time);
		Map<String, Object> conditions = new HashMap<String, Object>();
		/* 添加条件，通过主键插入 */
		conditions.put("p_key", params.get("p_key"));
		params.remove("p_key");
		return executeUpdate(TABLE_NAME, params, conditions);

	}

	public int delete(Map<String, Object> params) throws Exception {
		Map<String, Object> conditions = new HashMap<String, Object>();
		/* 根据主键删除 */
		conditions.put("p_key", params.get("p_key"));
		return executeDelete(TABLE_NAME, conditions);

	}

	public Record get(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("  SELECT p_key, sort_id, sort_name, complain_level,interceptor_level, info_level ");
		sql.append("  , outpatient_check, emergency_check, hospitalization_check, state ");
		sql.append("  , audit_explain, sort_num, product_code, create_time, update_time ");
		sql.append("  , oper_user ");
		sql.append("  ,  complain_level_outp,interceptor_level_outp,info_level_outp ");
		sql.append("  ,  complain_level_emergency,interceptor_level_emergency,info_level_emergency,is_zzf ");
		sql.append("  FROM " + TABLE_NAME + " (nolock) WHERE 1=1 ");
		/* 根据主键查询 */
		setParameter(params, "p_key", " and p_key=:p_key ", sql);
		return executeQuerySingleRecord(sql.toString(), params);
	}

	public Record getSortNumMax(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		/* 没有传入p_key时查询最大sort_num */
		sql.append("  SELECT MAX(sort_num) AS sort_num ");
		sql.append("  FROM " + TABLE_NAME + " (nolock) WHERE 1=1 ");
		/* 根据主键查询 */
		setParameter(params, "p_key", " and p_key=:p_key ", sql);
		return executeQuerySingleRecord(sql.toString(), params);

	}

	public Result queryListByIpc(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT sort_id,sort_name FROM " + TABLE_NAME + " (nolock) ");
		sql.append(" where product_code = '" + ProductmanagetService.IPC + "'");
		return executeQueryLimit(sql.toString(), params);
	}

	public Result queryListByImic(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT thirdt_code,thirdt_name FROM map_common_regulation (nolock)  where product_code = 'hospital_imic' and check_type = '2' ");
		return executeQueryLimit(sql.toString(), params);
	}

	/* ========================自定义转自费设置====================== */
	public Result queryCtrlZzf(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT  * ");
		sql.append(" FROM  " + AUDIT_RULE_CTRL_ZZF + "  (nolock)  ");
		sql.append(" WHERE sort_id = :sort_id");
		return executeQuery(sql.toString(), params);
	}

	public Record checkCtrlZzf(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT  id ");
		sql.append(" FROM  " + AUDIT_RULE_CTRL_ZZF + "  (nolock)  ");
		sql.append(" WHERE sort_id = :sort_id");
		sql.append(" AND zzf_type = :zzf_type");
		return executeQuerySingleRecord(sql.toString(), params);
	}

	public int updateCtrlZzf(Map<String, Object> params) throws Exception {
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("id", params.remove("id"));
		return executeUpdate(AUDIT_RULE_CTRL_ZZF, params, conditions);
	}

	public int insertCtrlZzf(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT id  FROM " + AUDIT_RULE_CTRL_ZZF + "  (nolock)  ");
		sql.append(" WHERE zzf_type = :zzf_type ");
		sql.append(" AND sort_id = :sort_id ");

		Record re = executeQuerySingleRecord(sql.toString(), params);
		/* 如果有re不为空代表sort_id重复，不能插入 */
		if (!CommonFun.isNe(re)) {
			return -1;
		}
		return executeInsert(AUDIT_RULE_CTRL_ZZF, params);
	}

	// 删除AUDIT_RULE_CTRL_ZZF表数据
	public int delCtrlZzfBySortId(Map<String, Object> params) throws Exception {
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("sort_id", params.get("sort_id"));
		return executeDelete(AUDIT_RULE_CTRL_ZZF, conditions);
	}

	/* ==============1.科室控制转自费================ */
	public Result queryDept(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("  	SELECT                                                                    ");
		sql.append("  	a.*,                                                                      ");
		sql.append("  	b.type,                                                                  ");
		sql.append("  	b.sort_id                                                                 ");
		sql.append("  FROM                                                                          ");
		sql.append("  	dict_his_deptment a  (nolock)    ");
		sql.append("  	LEFT JOIN " + AUDIT_RULE_CTRL_ZZF_DEPT + "  b  (nolock)       ");
		sql.append("    ON a.dept_code = b.dept_code                                                               ");
		sql.append("  	AND b.type	= :type                             ");
		sql.append("  	AND b.sort_id	= :sort_id                            ");
		sql.append("  	WHERE b.sort_id is NULL                         ");

		if (!CommonFun.isNe(params.get("filter"))) {
			params.put("condition", "%" + params.get("filter") + "%");
			sql.append(" and (a.dept_code LIKE :condition or a.dept_name like :condition or a.input_code like :condition )");
		}

		return executeQueryLimit(sql.toString(), params);
	}

	public Result queryDeptZzf(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("  SELECT *  FROM " + AUDIT_RULE_CTRL_ZZF_DEPT + "  (nolock)  ");
		sql.append("  WHERE   type	= :type                            ");
		sql.append("  AND     sort_id = :sort_id                        ");
		if (!CommonFun.isNe(params.get("filter"))) {
			params.put("condition", "%" + params.get("filter") + "%");
			sql.append(" and (dept_code LIKE :condition or dept_name like :condition or input_code like :condition)");
		}
		return executeQueryLimit(sql.toString(), params);
	}

	public int insertDeptCtrl(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT id  FROM " + AUDIT_RULE_CTRL_ZZF_DEPT + "  (nolock)  ");
		sql.append(" WHERE dept_code = :dept_code ");
		sql.append(" AND type = :type ");
		sql.append(" AND sort_id = :sort_id ");

		Record re = executeQuerySingleRecord(sql.toString(), params);
		// 如果有re不为空代表sort_id重复，不能插入
		if (!CommonFun.isNe(re)) {
			return -1;
		}
		return executeInsert(AUDIT_RULE_CTRL_ZZF_DEPT, params);
	}

	public int delDeptZzf(Map<String, Object> params) throws Exception {
		Map<String, Object> conditions = new HashMap<String, Object>();
		/* 根据主键删除 */
		conditions.put("id", params.get("id"));
		return executeDelete(AUDIT_RULE_CTRL_ZZF_DEPT, conditions);
	}

	public int delDeptZzfBySortId(Map<String, Object> params) throws Exception {
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("sort_id", params.get("sort_id"));
		return executeDelete(AUDIT_RULE_CTRL_ZZF_DEPT, conditions);
	}

	// "sort_id"=tdata.sort_id,"type"=tdata.type,"dept_codes"=dept_codes

	public String insertDeptCtrlBatch(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" INSERT INTO " + AUDIT_RULE_CTRL_ZZF_DEPT);
		sql.append(" (sort_id,type,dept_code,dept_name,input_code	)        ");
		sql.append(" SELECT " + params.get("sort_id") + "," + params.get("type") + ",dept_code,dept_name,input_code   ");
		sql.append(" FROM 	dict_his_deptment   ");
		sql.append(" WHERE dept_code IN " + params.get("dept_codes"));
		return execute(sql.toString(), params);
	}

	/* ==============2.人员控制转自费================ */
	public Result queryUser(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("  	SELECT                                                                    ");
		sql.append("  	a.*,                                                                      ");
		sql.append("  	b.type,                                                                  ");
		sql.append("  	b.sort_id,c.dept_name                                                                ");
		sql.append("  FROM                                                                          ");
		sql.append("  	dict_his_users    (nolock)  a             ");
		sql.append("  	LEFT JOIN " + AUDIT_RULE_CTRL_ZZF_USER + "  (nolock) b   ");
		sql.append("    ON a.user_id = b.user_id                                 ");
		sql.append("  	AND b.type	= :type                            ");
		sql.append("  	AND b.sort_id	= :sort_id                            ");
		sql.append("  	LEFT JOIN  dict_his_deptment  (nolock) c   ");
		sql.append("    ON a.user_dept = c.dept_code                                                   ");
		sql.append("  	WHERE b.sort_id is NULL                                                   ");

		if (!CommonFun.isNe(params.get("filter_info"))) {
			params.put("condition", "%" + params.get("filter_info") + "%");
			sql.append(" and (a.user_id LIKE :condition or a.user_name like :condition or a.input_code like :condition )");
		}

		if (!CommonFun.isNe(params.get("job_role"))) {
			params.put("job_role", "%" + params.get("job_role") + "%");
			sql.append(" and a.job_role LIKE :job_role  ");
		}

		if (!CommonFun.isNe(params.get("dept_code"))) {
			sql.append(" and a.user_dept = :dept_code ");
		}
		return executeQueryLimit(sql.toString(), params);
	}

	public Result queryDeptAll(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("  	SELECT   dept_code,dept_name                 ");
		sql.append("  	FROM    dict_his_deptment                              ");
		sql.append("  	WHERE 1=1                                                 ");

		if (!CommonFun.isNe(params.get("filter"))) {
			params.put("condition", "%" + params.get("filter") + "%");
			sql.append(" and (dept_code LIKE :condition or dept_name like :condition or input_code like :condition )");
		}
		return executeQueryLimit(sql.toString(), params);
	}

	public Result queryUserZzf(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("  SELECT a.*,b.dept_name  FROM " + AUDIT_RULE_CTRL_ZZF_USER + "  (nolock) a  ");
		sql.append("  LEFT JOIN  dict_his_deptment    (nolock)  b  ");
		sql.append("  ON a.user_dept = b.dept_code                      ");
		sql.append("  WHERE   type	= :type                            ");
		sql.append("  AND     sort_id = :sort_id                        ");
		if (!CommonFun.isNe(params.get("filter_info"))) {
			params.put("condition", "%" + params.get("filter_info") + "%");
			sql.append(" and (a.user_id LIKE :condition or a.user_name like :condition  or a.input_code like :condition  )");
		}

		if (!CommonFun.isNe(params.get("job_role"))) {
			params.put("job_role", "%" + params.get("job_role") + "%");
			sql.append(" and a.job_role LIKE :job_role  ");
		}

		if (!CommonFun.isNe(params.get("dept_code"))) {
			sql.append(" and a.user_dept = :dept_code ");
		}
		return executeQueryLimit(sql.toString(), params);
	}

	public int insertUserCtrl(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT id  FROM " + AUDIT_RULE_CTRL_ZZF_USER + "  (nolock)  ");
		sql.append(" WHERE user_id = :user_id ");
		sql.append(" AND type = :type ");
		sql.append(" AND sort_id = :sort_id ");

		Record re = executeQuerySingleRecord(sql.toString(), params);
		// 如果有re不为空代表sort_id重复，不能插入
		if (!CommonFun.isNe(re)) {
			return -1;
		}
		return executeInsert(AUDIT_RULE_CTRL_ZZF_USER, params);
	}

	public int delUserZzf(Map<String, Object> params) throws Exception {
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("id", params.get("id"));
		return executeDelete(AUDIT_RULE_CTRL_ZZF_USER, conditions);
	}

	public int delUserZzfBySortId(Map<String, Object> params) throws Exception {
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("sort_id", params.get("sort_id"));
		return executeDelete(AUDIT_RULE_CTRL_ZZF_USER, conditions);
	}

	public String insertUserCtrlBatch(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" INSERT INTO " + AUDIT_RULE_CTRL_ZZF_USER);
		sql.append(" (sort_id,type,user_id,user_name,user_dept,sex,job_role,input_code)        ");
		sql.append("  SELECT " + params.get("sort_id") + "," + params.get("type"));
		sql.append("  ,user_id,user_name,user_dept,sex,job_role,input_code   ");
		sql.append(" FROM 	dict_his_users       ");
		sql.append(" WHERE user_id IN " + params.get("user_ids"));
		return execute(sql.toString(), params);
	}

	/* ==============3.费用项目控制转自费================ */
	public Result queryPrice(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("  	SELECT                                                                    ");
		sql.append("  	a.*,                                                                      ");
		sql.append("  	b.type,                                                                  ");
		sql.append("  	b.sort_id , c.class_name                                              ");
		sql.append("  FROM                                                                          ");
		sql.append("  	dict_his_pricelist   (nolock)  a            ");
		sql.append("  	LEFT JOIN " + AUDIT_RULE_CTRL_ZZF_ITEM + "   (nolock) b   ");
		sql.append("    ON a.item_code = b.item_code                                                               ");
		sql.append("  	AND b.type	= :type                           ");
		sql.append("  	AND b.sort_id	= :sort_id                 ");
		sql.append("  	LEFT JOIN 	dict_his_billitemclass    (nolock) c   ");
		sql.append("    ON a.item_class = c.class_code                             ");
		sql.append("  	WHERE b.sort_id is NULL                                                         ");

		if (!CommonFun.isNe(params.get("filter_info"))) {
			params.put("condition", "%" + params.get("filter_info") + "%");
			sql.append(" and (a.item_code LIKE :condition or a.item_name like :condition or a.input_code like :condition )");
		}

		if (!CommonFun.isNe(params.get("units"))) {
			params.put("units", "%" + params.get("units") + "%");
			sql.append(" and a.units LIKE :units  ");
		}

		if (!CommonFun.isNe(params.get("class_code"))) {
			sql.append(" and a.item_class = :class_code ");
		}
		return executeQueryLimit(sql.toString(), params);
	}

	public Result queryBillItemClass(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("  	SELECT  class_code,class_name                       ");
		sql.append("  	FROM dict_his_billitemclass    (nolock)    ");
		sql.append("  	WHERE 1 = 1    ");

		if (!CommonFun.isNe(params.get("filter"))) {
			params.put("condition", "%" + params.get("filter") + "%");
			sql.append(" and (class_code LIKE :condition or class_name like :condition or input_code like :condition )");
		}

		return executeQueryLimit(sql.toString(), params);
	}

	public Result queryPriceZzf(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("  SELECT a.*,b.class_name  FROM " + AUDIT_RULE_CTRL_ZZF_ITEM + "  (nolock)  a ");
		sql.append("  LEFT JOIN 	dict_his_billitemclass    (nolock) b   ");
		sql.append("  ON a.item_class = b.class_code                    ");
		sql.append("  WHERE   type	= :type                            ");
		sql.append("  AND     sort_id = :sort_id                        ");
		if (!CommonFun.isNe(params.get("filter_info"))) {
			params.put("condition", "%" + params.get("filter_info") + "%");
			sql.append(" and (a.item_code LIKE :condition or a.item_name like :condition  or a.input_code like :condition )");
		}

		if (!CommonFun.isNe(params.get("units"))) {
			params.put("units", "%" + params.get("units") + "%");
			sql.append(" and a.units LIKE :units  ");
		}

		if (!CommonFun.isNe(params.get("class_code"))) {
			sql.append(" and a.item_class = :class_code ");
		}
		return executeQueryLimit(sql.toString(), params);
	}

	public int insertPriceCtrl(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT id  FROM " + AUDIT_RULE_CTRL_ZZF_ITEM + "  (nolock)  ");
		sql.append(" WHERE item_code = :item_code ");
		sql.append(" AND type = :type ");
		sql.append(" AND sort_id = :sort_id ");

		Record re = executeQuerySingleRecord(sql.toString(), params);
		// 如果有re不为空代表sort_id重复，不能插入
		if (!CommonFun.isNe(re)) {
			return -1;
		}
		return executeInsert(AUDIT_RULE_CTRL_ZZF_ITEM, params);
	}

	public int delPriceZzf(Map<String, Object> params) throws Exception {
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("id", params.get("id"));
		return executeDelete(AUDIT_RULE_CTRL_ZZF_ITEM, conditions);
	}

	public int delPriceZzfBySortId(Map<String, Object> params) throws Exception {
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("sort_id", params.get("sort_id"));
		return executeDelete(AUDIT_RULE_CTRL_ZZF_ITEM, conditions);
	}

	public String insertPriceCtrlBatch(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" INSERT INTO " + AUDIT_RULE_CTRL_ZZF_ITEM);
		sql.append(" (sort_id,type,item_code,item_name,units,price,item_class,input_code	)        ");
		sql.append(" SELECT " + params.get("sort_id") + "," + params.get("type"));
		sql.append(" ,item_code,item_name,units,price,item_class,input_code  ");
		sql.append(" FROM 	dict_his_pricelist   ");
		sql.append(" WHERE item_code IN " + params.get("item_codes"));
		return execute(sql.toString(), params);
	}

	/* ===============================自定义转自费END============================== */

	public Record getBySortId(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("  SELECT p_key, sort_id, sort_name, complain_level,interceptor_level, info_level ");
		sql.append("  , outpatient_check, emergency_check, hospitalization_check, state ");
		sql.append("  , audit_explain, sort_num, product_code, create_time, update_time ");
		sql.append("  , oper_user ");
		sql.append("  ,  complain_level_outp,interceptor_level_outp,info_level_outp ");
		sql.append("  ,  complain_level_emergency,interceptor_level_emergency,info_level_emergency,is_zzf ");
		sql.append("  FROM " + TABLE_NAME + " (nolock) WHERE 1=1 ");
		/* 根据主键查询 */
		setParameter(params, "sort_id", " and sort_id=:sort_id ", sql);
		setParameter(params, "product_code", " and product_code=:product_code ", sql);
		return executeQuerySingleRecord(sql.toString(), params);
	}
}
