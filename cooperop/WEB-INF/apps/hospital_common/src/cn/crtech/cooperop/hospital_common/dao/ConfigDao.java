package cn.crtech.cooperop.hospital_common.dao;

import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

//system_config 系统配置管理
public class ConfigDao extends BaseDao{
	
	private final static String TABLE_NAME = "system_config";
	
	public final static String TABLE_PRODUCT = "system_product";
	
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("select * from "+TABLE_NAME+" (nolock) where 1=1");
		sql.append(" and is_open = 'Y' ");
		if(!CommonFun.isNe(params.get("filter"))){
			params.put("key", "%"+params.get("filter")+"%");
		}
		setParameter(params, "filter", " and (name like :key )", sql);
		if(!CommonFun.isNe(params.get("system_product_code"))) {
			sql.append(" and system_product_code = :system_product_code ");
		}
		Result executeQueryLimit = executeQueryLimit(sql.toString(), params);
		return executeQueryLimit;
	}
	
	public Result queryCodes(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("select * from "+TABLE_NAME+" (nolock) a where exists(select 1 from system_product(nolock) where a.system_product_code = code and is_active = '1' ) ");
		if(!CommonFun.isNe(params.get("filter"))){
			params.put("key", "%"+params.get("filter")+"%");
		}
		setParameter(params, "filter", " and (a.name like :key )", sql);
		if(!CommonFun.isNe(params.get("codes"))) {
			sql.append(" and ( ");
			String s = (String)params.get("codes");
			List list = CommonFun.json2Object(s, List.class);
			for (int i = 0; i < list.size(); i++) {
				if(i == 0) {
					sql.append("(a.code = '"+((Map<String, Object>)list.get(i)).get("code")+"' and a.system_product_code = '"+((Map<String, Object>)list.get(i)).get("system_product_code")+"') ");
				}else {
					sql.append("or (a.code = '"+((Map<String, Object>)list.get(i)).get("code")+"' and a.system_product_code = '"+((Map<String, Object>)list.get(i)).get("system_product_code")+"') ");
				}
			}
			sql.append(" ) ");
		}else {
			sql.append("and 1=0 ");
		}
		if(!CommonFun.isNe(params.get("system_product_code"))) {
			sql.append(" and a.system_product_code = :system_product_code ");
		}
		Result executeQueryLimit = executeQueryLimit(sql.toString(), params);
		return executeQueryLimit;
	}
	
	public int insert(Map<String, Object> params) throws Exception {
		return executeInsert(TABLE_NAME, params);
	}
	
	public int update(Map<String, Object> params) throws Exception {
		Record r=new Record();
		r.put("code", params.remove("code"));
		return executeUpdate(TABLE_NAME, params, r);
	}
	
	public int delete(Map<String, Object> params) throws Exception {
		return executeDelete(TABLE_NAME, params);
	}
	
	public Record get(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from "+TABLE_NAME+" (nolock) where 1 = 1");
		setParameter(params, "code", " and code =:code ", sql);
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	public Result queryProduct(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("select code product_code, name product_name from "+TABLE_PRODUCT+" (nolock) where 1=1");
		//sql.append(" and is_active = 1 ");
		return executeQueryLimit(sql.toString(), params);
	}
}
