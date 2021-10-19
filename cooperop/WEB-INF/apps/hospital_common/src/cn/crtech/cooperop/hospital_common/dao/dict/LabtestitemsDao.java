package cn.crtech.cooperop.hospital_common.dao.dict;

import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class LabtestitemsDao extends BaseDao {

	private final static String DICT_HIS_LAB_TEST_ITEMS = "dict_his_lab_test_items";

	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT * ");
		sql.append(" FROM " + DICT_HIS_LAB_TEST_ITEMS + " (nolock) ");
		sql.append(" WHERE 1 = 1 ");

		if (!CommonFun.isNe(params.get("name"))) {
			params.put("condition", "%" + params.get("name") + "%");
			sql.append(" and (item_code LIKE :condition  or item_name LIKE :condition)");
		}
		return executeQueryLimit(sql.toString(), params);
	}
	
	
	
	public Result queryLabReportItem(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT * ");
		sql.append(" FROM dict_his_lab_report_item  (nolock) ");
		sql.append(" WHERE 1 = 1 ");

		if (!CommonFun.isNe(params.get("filter"))) {
			params.put("condition", "%" + params.get("filter") + "%");
			sql.append(" and (item_code LIKE :condition  or item_name LIKE :condition)");
		}
		return executeQueryLimit(sql.toString(), params);
	}

	public int insert(Map<String, Object> params) throws Exception {
		
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT * ");
		sql.append(" FROM dict_his_lab_report_item  (nolock) ");
		sql.append(" WHERE item_code = :item_code ");
		Record record = executeQuerySingleRecord(sql.toString(), params);
		if (!CommonFun.isNe(record)) {
			params.put("item_name", record.get("item_name"));
		}else{
			params.put("item_name", "");
		}
		
		return executeInsert(DICT_HIS_LAB_TEST_ITEMS, params);
	}

	public int update(Map<String, Object> params) throws Exception {
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("pkey_id", params.remove("pkey_id"));
		return executeUpdate(DICT_HIS_LAB_TEST_ITEMS, params, conditions);
	}

	public int delete(Map<String, Object> params) throws Exception {
		Map<String, Object> conditions = new HashMap<String, Object>();
		/* 根据表实际主键删除 */
		conditions.put("pkey_id", params.get("pkey_id"));
		return executeDelete(DICT_HIS_LAB_TEST_ITEMS, conditions);

	}

	// 进入页面时获取数据
	public Record get(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT * ");
		sql.append(" FROM " + DICT_HIS_LAB_TEST_ITEMS + " (nolock) ");
		sql.append(" WHERE pkey_id= :pkey_id ");
		return executeQuerySingleRecord(sql.toString(), params);
	}

}
