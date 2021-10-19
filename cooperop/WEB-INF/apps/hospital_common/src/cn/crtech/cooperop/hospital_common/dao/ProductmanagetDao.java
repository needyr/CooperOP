package cn.crtech.cooperop.hospital_common.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class ProductmanagetDao extends BaseDao {
	
	private final static String TABLE_NAME = "system_product";

	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from " + TABLE_NAME+" (nolock) ");
		sql.append(" where 1 = 1 ");
		if(!CommonFun.isNe(params.get("filter"))){
			params.put("key", "%"+params.get("filter")+"%");
			sql.append(" and name like :key");
		}
		if(!CommonFun.isNe(params.get("is_check_server"))) {
			sql.append(" and is_check_server = :is_check_server ");
		}
		if(!CommonFun.isNe(params.get("is_active"))) {
			sql.append(" and is_active = :is_active ");
		}
		return executeQueryLimit(sql.toString(), params);
	}
	
	public Record get(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from " + TABLE_NAME + " (nolock) where 1=1");
		setParameter(params, "code"," and code = :code", sql);
		return executeQuerySingleRecord(sql.toString(), params);
	}

	public Result queryHas(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from " + TABLE_NAME + " (nolock) where code = :code");
		return executeQuery(sql.toString(), params);
	}

	public int insert(Map<String, Object> params) throws Exception {
		return executeInsert(TABLE_NAME, params);
	}

	public int update(Map<String, Object> params) throws Exception {
		Record record = new Record();
		record.put("code", params.remove("code"));
		return executeUpdate(TABLE_NAME, params, record);
	}

	public int delete(Map<String, Object> params) throws Exception {
		return executeDelete(TABLE_NAME, params);
	}


}
