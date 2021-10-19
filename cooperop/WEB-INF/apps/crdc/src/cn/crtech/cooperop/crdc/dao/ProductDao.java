package cn.crtech.cooperop.crdc.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;

public class ProductDao extends BaseDao {
	private final static String TABLE_NAME = "system_product";
	
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select sp.*, p.names as popedom_names, sr.name as default_role_name from system_product sp ");
		sql.append("  left join system_popedom p on p.id = sp.popedom ");
		sql.append("  left join system_role sr on sr.id = sp.default_role ");
		sql.append(" where 1 = 1 ");
		return executeQueryLimit(sql.toString(), params);
	}

	public Record get(String code) throws Exception {
		Record conditions = new Record();
		conditions.put("code", code);
		StringBuffer sql = new StringBuffer();
		sql.append("select * from " + TABLE_NAME);
		sql.append(" where code = :code");
		return executeQuerySingleRecord(sql.toString(), conditions);
	}	
	
	public int insert(Map<String, Object> params) throws Exception {
		return executeInsert(TABLE_NAME, params);
	}
	
	public int update(String code, Map<String, Object> params) throws Exception {
		Record conditions = new Record();
		conditions.put("code", code);
		params.remove("code");
		return executeUpdate(TABLE_NAME, params, conditions);
	}

	public int delete(String code) throws Exception {
		Record conditions = new Record();
		conditions.put("code", code);
		return executeDelete(TABLE_NAME, conditions);
	}

}
