package cn.crtech.cooperop.hospital_common.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class Dict_sys_drug_tagDao extends BaseDao {

	public Result searchCheck(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * ");
		sql.append("FROM dict_sys_drug_tag(NOLOCK) ");
		sql.append(" where drugtagbh in(:code)");
		params.put("sort", "drugtagbh");
		return executeQuery(sql.toString(), params);
	}
	
	public Result search(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * ");
		sql.append("FROM dict_sys_drug_tag(NOLOCK) ");
		if (!CommonFun.isNe(params.get("data"))) {
			params.put("filter", "%"+params.get("data")+"%");
			sql.append(" where drugtagbh = :data or drugtagname like :filter or drugtag_show like :filter");
		}
		params.put("sort", "drugtagbh");
		return executeQuery(sql.toString(), params);
	}
}
