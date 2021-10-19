package cn.crtech.cooperop.bus.cache.dao;

import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Result;

public class SystemConfigDao extends BaseDao {
	public Result load() throws Exception {
		return executeQuery("select * from system_config(nolock)");
	}
	
	public int save(String productCode,String code ,String value) throws Exception {
		Map<String,Object> params = new HashMap<String,Object>();
		String sql = "update system_config set value = :value where code = :code and system_product_code = :productCode";
		params.put("code", code);
		params.put("productCode", productCode);
		params.put("value", value);
		
		return executeUpdate(sql, params);
	}
	
	/**
	 * 通过主键获取信息
	 * @param code 主键 
	 * @return 获取结果  Result
	 * @throws Exception 
	 */
	public Result get(String productCode,String code) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("code", code);
		param.put("productCode", productCode);
		StringBuffer sql = new StringBuffer(" select * from system_config(nolock) where 1=1 ");
		sql.append(" and code=:code");
		sql.append(" and system_product_code=:productCode");
		return executeQuery(sql.toString(), param);
	}
}
