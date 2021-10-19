package cn.crtech.cooperop.crdc.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;

public class WorkFlowDesignerDao extends BaseDao {
	private final static String TABLE_NAME = "system_product_process";
	private final static String TABLE_NAME_NODE = "system_product_process_node";
	private final static String TABLE_NAME_ROUTE = "system_product_process_route";
	
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select t.*, sp.name as system_product_name, vsu.name as last_modifier_name from " + TABLE_NAME + " t ");
		sql.append("  left join system_product sp on sp.code = t.system_product_code ");
		sql.append("  left join v_system_user vsu on vsu.id = t.last_modifier ");
		sql.append(" where 1 = 1 ");
		setParameter(params, "filter", " and upper(t.id + ',' + t.name+','+t.system_product_code) like '%' + upper(:filter) + '%' ", sql);
		setParameter(params, "state", " and t.state = :state ", sql);
		setParameter(params, "type", " and t.type = :type ", sql);
		setParameter(params, "system_product_code", " and t.system_product_code = :system_product_code ", sql);
		return executeQueryLimit(sql.toString(), params);
	}
	
	public Result queryFlowCount(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select t.* from " + TABLE_NAME + " t ");
		sql.append(" where state = 1 and type=:type ");
		return executeQuery(sql.toString(), params);
	}
	
	public Record get(String system_product_code, String id) throws Exception {
		Record conditions = new Record();
		conditions.put("system_product_code", system_product_code);
		conditions.put("id", id);
		StringBuffer sql = new StringBuffer();
		sql.append("select * from " + TABLE_NAME);
		sql.append(" where system_product_code = :system_product_code and id = :id ");
		return executeQuerySingleRecord(sql.toString(), conditions);
	}	
	
	public int insert(Map<String, Object> params) throws Exception {
		return executeInsert(TABLE_NAME, params);
	}
	
	public int update(String system_product_code, String id, Map<String, Object> params) throws Exception {
		Record conditions = new Record();
		conditions.put("system_product_code", system_product_code);
		conditions.put("id", id);
		Record p = new Record(params);
		p.remove("system_product_code");
		p.remove("id");
		return executeUpdate(TABLE_NAME, p, conditions);
	}

	public int delete(String system_product_code, String id) throws Exception {
		Record conditions = new Record();
		conditions.put("system_product_code", system_product_code);
		conditions.put("id", id);
		return executeDelete(TABLE_NAME, conditions);
	}

	public int insertNode(Map<String, Object> params) throws Exception {
		return executeInsert(TABLE_NAME_NODE, params);
	}
	
	public int insertRoute(Map<String, Object> params) throws Exception {
		return executeInsert(TABLE_NAME_ROUTE, params);
	}
	

	public int deleteNodes(String system_product_code, String system_product_process_id) throws Exception {
		Record conditions = new Record();
		conditions.put("system_product_code", system_product_code);
		conditions.put("system_product_process_id", system_product_process_id);
		return executeDelete(TABLE_NAME_NODE, conditions);
	}

	public int deleteRoutes(String system_product_code, String system_product_process_id) throws Exception {
		Record conditions = new Record();
		conditions.put("system_product_code", system_product_code);
		conditions.put("system_product_process_id", system_product_process_id);
		return executeDelete(TABLE_NAME_ROUTE, conditions);
	}

}
