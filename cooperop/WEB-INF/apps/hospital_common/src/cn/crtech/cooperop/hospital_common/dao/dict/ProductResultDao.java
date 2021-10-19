package cn.crtech.cooperop.hospital_common.dao.dict;

import java.util.Map;
import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;

public class ProductResultDao extends BaseDao{
	/** 各产品审查结果字典 */
	private final static String TABLE_NAME = "system_result_state";
	/** 系统产品 */
	private final static String TABLE_SYS_PRODUCT = "system_product";
	
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append(" select a.*,b.name from "+TABLE_NAME+" a (nolock)");
		sql.append(" left join "+TABLE_SYS_PRODUCT+" b (nolock) on a.product_code=b.code");
		sql.append(" where 1=1");
		setParameter(params, "usefor_manage", " and usefor_manage='1'", sql);//是否用于管理问题等级
		setParameter(params, "filter", " and (manage_state like '%"+params.get("filter")+"%' or product_code like '%"+params.get("filter")+"%')", sql);
		setParameter(params, "filterProduct", " and a.product_code in (:filterProduct)", sql);
		params.put("sort", "product_code,priority desc");
		return executeQueryLimit(sql.toString(), params);
	}
	
	public Record get(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select a.*,b.name from "+TABLE_NAME+" a (nolock)");
		sql.append(" left join "+TABLE_SYS_PRODUCT+" b (nolock) on a.product_code=b.code");
		sql.append(" where 1 = 1");
		setParameter(params, "p_key", " and p_key = :p_key", sql);
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	public int update(Map<String, Object> params) throws Exception {
		Record r=new Record();
		r.put("p_key", params.remove("p_key"));
		return executeUpdate(TABLE_NAME, params, r);
	}
	
	public int insert(Map<String, Object> params) throws Exception {
		return executeInsert(TABLE_NAME, params);
	}
	
	public int delete(Map<String, Object> params) throws Exception {
		return executeDelete(TABLE_NAME, params);
	}
}
