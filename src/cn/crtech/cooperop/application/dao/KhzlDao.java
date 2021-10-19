package cn.crtech.cooperop.application.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class KhzlDao extends BaseDao {
	
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select  *                                                         ");
		sql.append(" from    khzl ");
		sql.append(" where  app_ip_port is not null and app_ip_port <> '' ");
		if(!CommonFun.isNe(params.get("filter"))){
			params.put("key", "%"+params.get("filter")+"%");
		}
		setParameter(params, "filter", " and (kehumingcheng like :key or kehuid like :key or PYM like :key) ", sql);
	    return executeQuery(sql.toString(), params);
	}
}
