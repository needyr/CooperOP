package cn.crtech.cooperop.application.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Result;

public class SystemProcessDao extends BaseDao {
	private final static String TABLE_NAME = "system_product_process";
	private final static String TABLE_NAME_NODE = "system_product_process_node";
	
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select t.*, sp.name as system_product_name, vsu.name as last_modifier_name from " + TABLE_NAME + " t ");
		sql.append("  left join system_product sp on sp.code = t.system_product_code ");
		sql.append("  left join v_system_user vsu on vsu.id = t.last_modifier ");
		sql.append(" where 1 = 1 ");
		setParameter(params, "filter", " and upper(t.id + ',' + t.name+','+t.system_product_code) like '%' + upper(:filter) + '%' ", sql);
		setParameter(params, "state", " and t.state = :state ", sql);
		setParameter(params, "system_product_code", " and t.system_product_code = :system_product_code ", sql);
		return executeQueryLimit(sql.toString(), params);
	}
	public Result queryNode(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from " + TABLE_NAME_NODE + " t ");
		sql.append(" where 1 = 1 and system_product_process_id=:system_product_process_id and type in('task','auto','countersign')");
		setParameter(params, "filter", " and upper(t.id + ',' + t.name) like '%' + upper(:filter) + '%' ", sql);
		return executeQueryLimit(sql.toString(), params);
	}
}
