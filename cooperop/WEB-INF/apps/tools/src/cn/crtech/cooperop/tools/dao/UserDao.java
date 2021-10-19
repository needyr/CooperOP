package cn.crtech.cooperop.tools.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class UserDao extends BaseDao{
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from "+params.get("table_name")+" where ");
		if(!CommonFun.isNe(params.get("condition"))){
			sql.append(params.get("condition"));
		}else{
			sql.append("1=1");
		}
		params.put("start", "1");
		params.put("limit", "-1");
		return executeQueryLimit(sql.toString(), params);
	}
	public int update(String table_name,Map<String, Object> params,Map<String, Object> conditions) throws Exception {
		return executeUpdate(table_name, params, conditions);
	}
}
