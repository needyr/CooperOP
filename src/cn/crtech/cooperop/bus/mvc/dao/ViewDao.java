package cn.crtech.cooperop.bus.mvc.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;

public class ViewDao extends BaseDao {
	private final static String TABLE_NAME = "system_view";
	
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from " + TABLE_NAME+"(nolock)");
		sql.append(" where 1 = 1 ");
		setParameter(params, "filter", " and upper(id + ',' + name + ',' + title) like '%' + upper(:filter) + '%' ", sql);
		setParameter(params, "id", " and id = :id ", sql);
		setParameter(params, "flag", " and flag = :flag ", sql);
		setParameter(params, "type", " and type = :type ", sql);
		setParameter(params, "system_product_code", " and system_product_code = :system_product_code ", sql);
		return executeQueryLimit(sql.toString(), params);
	}

	public Record get(String type, String flag, String id,String system_product_code) throws Exception {
		Record conditions = new Record();
		conditions.put("type", type);
		conditions.put("flag", flag);
		conditions.put("id", id);
		conditions.put("system_product_code", system_product_code);
		StringBuffer sql = new StringBuffer();
		sql.append("select * from " + TABLE_NAME+"(nolock)");
		sql.append(" where type = :type and id = :id and flag=:flag and system_product_code=:system_product_code");
		return executeQuerySingleRecord(sql.toString(), conditions);
	}
	
	public int update(String type, String flag, String id,String system_product_code, Map<String, Object> params) throws Exception {
		Record conditions = new Record();
		conditions.put("type", type);
		conditions.put("flag", flag);
		conditions.put("id", id);
		conditions.put("system_product_code", system_product_code);
		params.remove("system_product_code");
		params.remove("type");
		params.remove("flag");
		params.remove("id");
		return executeUpdate(TABLE_NAME, params, conditions);
	}
}
